# Build stage
FROM maven:3.8.7-eclipse-temurin-17-alpine AS builder

# Create app directory
WORKDIR /app

# Copy pom.xml and download dependencies
COPY pom.xml .
RUN mvn dependency:go-offline -B

# Copy source code and build
COPY src ./src
RUN mvn clean package -DskipTests && \
    mv target/*.jar target/app.jar

# Runtime stage
FROM eclipse-temurin:17-jre-alpine

# Add non-root user
RUN addgroup -g 1001 -S appgroup && \
    adduser -u 1001 -S appuser -G appgroup

# Create app directory
WORKDIR /app

# Copy jar from builder
COPY --from=builder --chown=appuser:appgroup /app/target/app.jar .

# Security: Don't run as root
USER appuser:appgroup

# Health check
HEALTHCHECK --interval=30s --timeout=3s --start-period=30s --retries=3 \
    CMD wget --no-verbose --tries=1 --spider http://localhost:8080/actuator/health || exit 1

# Expose port (informational)
EXPOSE 8080

# Use array form of ENTRYPOINT to prevent shell injection
ENTRYPOINT ["java", "-Xmx256m", "-Xms128m", "-jar", "app.jar"]
