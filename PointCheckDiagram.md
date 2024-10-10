```mermaid
sequenceDiagram
%% 2.사용자 잔액 조회
    Note over User: 사용자 잔액 조회
    User --> Point : 잔액 조회 요청 (조회할 user의 Id)
    Point --> User : 잔액 조회 응답 (조회한 user의 point)
```