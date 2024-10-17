```mermaid
sequenceDiagram
    Note over User, Data_PlatForm: 상위 상품 조회
    User --> Data_PlatForm : 상위 상품 조회 요청
    Data_PlatForm --> User : 상위 상품 조회 응답 (상품 ID, 이름, 수량, 순위)의 List 반환
```