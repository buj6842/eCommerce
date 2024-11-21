### 트랜잭션 처리 보고서

현재 트랜잭션 범위를 분리해야할 부분은 주문 부분에 있습니다.

현재 주문 부분은 USER, PRODUCT, ORDER , ORDER_ITEM 4가지의 테이블을
사용하고 있습니다. 이부분이 확장이된다면 
각 부분들을 분리하여 트랜잭션을 처리하는게 좋지않을까? 라는 생각이 들었습니다.

현재 제가작성한 주문 API는 이렇습니다.

```java
public void orderProduct(OrderRequest orderRequest) {
        // 상품 재고 검증 후 차감
        productService.validateProduct(orderRequest);
        // 사용자 포인트 검증 후 차감
        userService.validateAndUsePoint(orderRequest);
        // 주문 생성
        orderService.orderProduct(orderRequest);
        // Data 플랫폼에 데이터 전송
        orderService.sendData(orderRequest);
    }
```
현재 설계는 Facade 패턴을 통해 호출되며, 단일 트랜잭션 내에서 처리됩니다.
메소드 진행중 하나라도 실패할 경우 롤백되는 방식입니다.
데드락, 또는 리소스 관련 이슈가 서비스의 규모가 커질수록 발생할 여지가 있습니다.

### 어떻게 해결할 것인가?
제가 생각한 해결방법은 다음과 같습니다.
1. 트랜잭션 처리를 분리한다.
2. 이벤트를 통해 처리한다.
3. Saga Pattern을 적용한다.

이 3가지를 Choreography 방식으로 구현할 예정입니다.
이유는 서비스간의 의존도를 낮추고, 각 서비스가 독립적으로 동작하되 이벤트를 통한 흐름으로 제어를 하기위함입니다.
이벤트를 통한 흐름으로 제어를 하며 분산트랜잭션환경에서 일부 커밋되거나 롤백되는 상황을 줄이며 에러 발생시 보상 트랜잭션까지 application 에서 구현하여 관리하는 부분까지 기대하고있습니다.

이번 STEP 16에는 spring event를 통해 우선적으로 OrderEvent만 구현해서
현재 주문되는 부분들을 수집하는 목적으로 구현할 예정입니다. (이후 주차에서 보상트랜잭션 및 kafka 연동을 하기 전 event 발행 및 수집을 구현,테스트 해볼 예정입니다.)