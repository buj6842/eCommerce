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
설계는 이렇게 되어있는데
트랜잭션을 나누게된다면 어떻게해야 할지 고민을 해봤습니다.
Event를 활용한 방식을 생각했을 때 하위 트랜잭션에서 오류가 발생하여도
상위 트랜잭션은 롤백을 해야한다 생각했습니다. 해당부분을
TransactionalEventListener 를 사용하여 구현을 하면 어떨까? 라는 생각이 있습니다.

이번 STEP 16에는 spring event를 통해 우선적으로 OrderEvent만 구현해서
현재 주문되는 부분들을 수집하는 목적으로 구현할 예정입니다.