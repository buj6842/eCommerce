```mermaid
sequenceDiagram
%% 4.상품 조회
    Note over User, Product: 상품 조회
    User --> Product : 상품 조회 요청 (요청을 보낸 시간을 기준으로)
    Product --> User : 상품 정보 ( ID, 이름, 가격, 잔여수량 ) 조회 응답
```