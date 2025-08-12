import http from 'k6/http';
import { check } from 'k6';

export let options = {
  stages: [
    { duration: '10s', target: 20 },
    { duration: '30s', target: 80 },
    { duration: '20s', target: 0 },
  ],
};

export default function() {
  let response = http.get('http://localhost:8080/health');
  check(response, { 'status is 200': (r) => r.status === 200 });
}
