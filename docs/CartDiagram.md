```mermaid
sequenceDiagram
%% 7.장바구니 추가/삭제 기능
    Note over User, Cart: 장바구니 추가/삭제 기능
    User --> Cart : 장바구니 추가/삭제 기능 요청 (상품ID, 상품 수량)
    Cart --> User : 장바구니에 상품 추가/삭제 완료 응답
```