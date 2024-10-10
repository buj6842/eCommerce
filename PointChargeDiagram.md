```mermaid
sequenceDiagram
%% 3.포인트 충전
    Note over Actor : 포인트 충전
    Actor --> Point : 포인트 충전 요청
    SERVER --> Point : 포인트 충전 실행 (충전할 user의 Id, 충전할 포인트)
    Point --> SERVER : 사용자가 요청한 포인트 실행 결과 응답
    SERVER --> User : 포인트 충전 실행 결과 응답
```