import http from 'k6/http';
import { check } from 'k6';

export let options = {
  stages: [
    { duration: '10s', target: 20 },   // Quick ramp up
    { duration: '30s', target: 60 },   // High load to trigger scaling FAST
    { duration: '20s', target: 0 },    // Quick ramp down
  ],
  thresholds: {
    http_req_duration: ['p(95)<2000'], // Relaxed for quick demo
    http_req_failed: ['rate<0.2'],     // Allow some failures under high load
  },
};

export default function() {
  let response = http.get('http://localhost:8080/health');

  check(response, {
    'status is 200': (r) => r.status === 200,
  });

  // No sleep - maximum request rate for fast CPU spike
}