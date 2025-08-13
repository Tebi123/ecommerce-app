import http from 'k6/http';
import { check } from 'k6';

export let options = {
  stages: [
    { duration: '10s', target: 50 },
    { duration: '30s', target: 90 },
    { duration: '20s', target: 0 },
  ],
};

export default function() {
  let response = http.get('http://localhost:8081/health');
  check(response, { 'status is 200': (r) => r.status === 200 });
}
