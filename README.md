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

##
- [상품 주문](docs/OrderDiagram.md)
- [포인트 충전](docs/PointChargeDiagram.md)
- [사용자 잔액 조회](docs/PointCheckDiagram.md)
- [상위 상품 조회](docs/RankingDiagram.md)
- [장바구니 추가/삭제](docs/CartDiagram.md)
- [상품 조회](docs/ProductDiagram.md)

ERD는 다음과 같습니다.
```mermaid
erDiagram
    USER { 
        Long userId PK
        String username
        Integer point
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
        Integer totalPrice
    }

    ORDER_ITEM {
        Long orderItemId PK
        Long orderId FK
        Long productId
        Integer quantity
        Integer price
    }
    
    CART {
        Long cartId PK
        Long userId
        Long productId
        Integer quantity
    }
```

우선적으로 패키지 구조는


Split by Layer, Package by Feature 원칙으로 패키지를 구성하여 채워나갈 예정입니다.

기술 스택은 다음과 같습니다.
- Spring Boot
- JPA
- MYSQL
- Gradle
- Junit5
- Mockito
- REDIS
- Swagger
- Kafka(사용 예정)

Swagger 사용관련 문서입니다.
- [Swagger](docs/Swagger.md)

Cache 사용으로 성능 개선 보고서입니다.
- [Cache 사용](docs/report.md)

인덱스 적용 및 분석 보고서입니다.
-[인덱스 적용 및 분석](docs/indexReport.md)

트랜잭션 분리에 관한 설계서입니다.
-[트랜잭션 분리](docs/TransactionReport.md)