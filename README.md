#
시나리오는 이커머스 프로젝트를 선정하였스며

MileStone 의 구조는 다음과 같습니다.

```mermaid
gantt
    title Project MileStone
    dateFormat  YYYY-MM-DD
    section 1. 프로젝트 세팅 및
        요구사항 분석            :active, a1, 2024-10-05, 1d
        개발 환경 세팅         :active, a1, 2024-10-06, 1d
        Milestone 작성         :active, a1, 2024-10-07, 1d
        Sequence Diagram 작성  :active, a1, 2024-10-08, 1d
        ERD 작성               :active, a1, 2024-10-09, 1d
        API 명세서 작성        :active, a1, 2024-10-10, 1d
        Mock API 작성          :active, a1, 2024-10-10, 1d

    section 2. TDD 작성, 개발 환경 구축 및 API 작성
        단위테스트 TDD 작성     :active, b1, 2024-10-12, 1d
        사용자 잔액 조회 API    :active, b2, 2024-10-13, 1d
        포인트 충전 API :active, b3, 2024-10-13, 1d
        상품 조회 API   :active, b4, 2024-10-14, 1d
        상위 상품 조회 API        :active, b5, 2024-10-15, 2d
        장바구니 추가/삭제 API       :active, b6, 2024-10-16, 1d
        통합 테스트 작성       :active, b7, 2024-10-18, 1d
        
    section 3. 프로젝트 고도화
        Data Platform 사용처 고도화     :active, c1, 2024-10-19, 7d
```
3주간의 일정을 어느정도 작성했으며

해당 프로젝트를 설계하며 작성한 Sequence Diagram은 다음과 같습니다.
(파일별로도 분류를 해두었습니다)

```mermaid
sequenceDiagram
    actor  User
    participant Oauth
    participant Point
    participant Product
    participant Order
    participant Cart
    participant Data
    
    %% 1.사용자 로그인
    Note over User: 사용자 로그인
    User --> Oauth: 로그인 요청 (사용자 ID, 비밀번호)
    alt 로그인 성공
        Oauth --> User: 로그인 성공 응답
    else 로그인 실패
        Oauth --> User: 로그인 실패 (메세지 포함 전달)
    end

    %% 2.사용자 잔액 조회
    Note over User: 사용자 잔액 조회
    User --> Point : 잔액 조회 요청 (조회할 user의 Id)
    Point --> User : 잔액 조회 응답 (조회한 user의 point)

    %% 3.포인트 충전
    Note over User : 포인트 충전
    User --> Point : 포인트 충전 요청 (충전할 user의 Id, 충전할 포인트)
    Point --> User : 포인트 충전 실행 결과 응답
    
    %% 4.상품 조회
    Note over User, Product: 상품 조회
    User --> Product : 상품 조회 요청 (요청을 보낸 시간을 기준으로)
    Product --> User : 상품 정보 ( ID, 이름, 가격, 잔여수량 ) 조회 응답

    %% 5.상품 주문
    Note over User: 상품 주문
    User --> Point : 상품 주문 요청 (상품ID, 주문수량, 상품 가격,주문 일자) 의 목록으로 요청(상품 요청의 총 가격과 사용자의 잔액을 비교)
    alt 잔액이 충분한 경우
        Point --> Product : 잔액이 충분한 경우 상품 재고 확인
        alt 상품 재고가 충분할 경우
            Product --> Order : 상품 재고 확인 후 상품 주문 요청 (상품ID, 주문수량, 상품가격, 주문일자 정보 저장)
            Order --> Point : 주문 정보 저장 후 사용자의 잔액 갱신
            Order --> Data_PlatForm : 주문 정보 외부 데이터 플랫폼에 저장
        else 상품 재고가 부족한 경우
            Product --> User : 재고 부족으로 인한 실패 응답과 메세지 전달
        end
    else 잔액이 부족한 경우
        Point --> User : 잔액이 부족한 경우 실패 응답과 메세지 전달
    end

    %% 6.상위 상품 조회
    Note over User, Data_PlatForm: 상위 상품 조회
    User --> Data_PlatForm : 상위 상품 조회 요청
    Data_PlatForm --> User : 상위 상품 조회 응답 (상품 ID, 이름, 수량, 순위)의 List 반환

    %% 7.장바구니 추가/삭제 기능
    Note over User, Cart: 장바구니 추가/삭제 기능
    User --> Cart : 장바구니 추가/삭제 기능 요청 (상품ID, 상품 수량)
    Cart --> User : 장바구니에 상품 추가/삭제 완료 응답
```

ERD는 다음과 같습니다.
연관관계를 피하기 위해 다음과 같이 설계하였습니다.
```mermaid
erDiagram
    USER { 
        Long userId PK
        String username
        Double balance
    }

    POINT {
        Long pointId PK
        Long userId
        Integer amount
        Date updateDate
    }

    PRODUCT {
        Long productId PK
        String productName
        Integer price
        Integer productQuantity
    }

    ORDER {
        Long orderId PK
        Long userId
        Date orderDate
        Double totalAmount
    }

    ORDER_ITEM {
        Long orderItemId PK
        Long orderId
        Long productId
        Integer quantity
        Integer price
    }

    CART_ITEM {
        Long cartItemId PK
        Long cartId
        Long userId
        Long productId
        Integer quantity
    }
```

우선적으로 패키지 구조는

├── application
│ └── dto
│     ├── CartRequest.java
│     ├── OrderItemDTO.java
│     ├── OrderRequest.java
│     ├── PointChargeRequest.java
│     └── ProductDto.java
├── controller
│ ├── CartController.java
│ ├── OrderController.java
│ ├── ProductController.java
│ └── UserController.java
├── domain
├── infrastructure
└── structure.txt

형식으로 잡게되어 있습니다.
Split by Layer, Package by Feature 원칙으로 패키지를 구성하여 채워나갈 예정입니다.

기술 스택은 다음과 같습니다.
- Spring Boot
- JPA
- MYSQL(사용 예정)
- Gradle
- Junit5
- Mockito
- Swagger
- Kafka(사용 예정)