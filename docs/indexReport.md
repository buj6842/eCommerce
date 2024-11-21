### INDEX 적용 보고서

##### 1.어떤 쿼리를 기준으로 INDEX를 적용할 것인가?

지난 과제에서 캐시를 이용하여 잦은 쿼리사용을 줄였엇지만,
쿼리 성능을 개선하기위해 상위 상품 조회 쿼리를 여전히 index 처리를 해야겠다라고 선택하였습니다.

해당쿼리는 다음과 같습니다.

```sql
SELECT oi.product_id, p.product_name, SUM(oi.quantity) AS quantity
FROM ECommerce.order_item oi
         JOIN ECommerce.product p ON oi.product_id = p.product_id
WHERE oi.order_date BETWEEN NOW() - INTERVAL 3 DAY AND NOW()
GROUP BY oi.product_id, p.product_name
ORDER BY SUM(oi.quantity) DESC
Limit 5;
```
조회시점에서 3일간 판매량이 높은 상품 5개를 조회하는 쿼리인데,
해당쿼리의 결과를 프론트엔드와의 연동을 생각하였을때
product_id(상품아이디), product_name(상품명), quantity(판매량)을 조회하는 쿼리입니다.
상품명을 사용하고싶어 join 쿼리를 사용하게되었고, 이를 30만건기준으로 조회하였을 때의 속도와
실행계획은 각각 이렇습니다

테스트 환경은 이렇습니다.
Jmeter<br>
사용자수: 50<br>
시간 :10초
<br>
테스트환경을 이렇게 설정한 이유는 기존 캐쉬를 이용한 로직을 쿼리 튜닝을 위해 잠시 캐쉬를 사용하지 않게 변경해두었고
극한의 환경에서 테스트 진행시 max connection 이슈가 있어 조정하였습니다.



##### 인덱스 적용 전 쿼리 실행 속도
![img.png](/docs/NoIndexoQuerySpeed.png)
##### 쿼리 실행 계획
![img.png](/docs/noIndexExplain.png)

평균적으로 1440 정도의 성능을보이며 쿼리 실행 계획을 보았을 때
index 적용 전에는 Full Table Scan을 하고있는것을 확인할 수 있습니다(type : All)

##### 2. 어떤 INDEX를 적용할 것인가?

처음 계획을 했던 Index는 다음과 같습니다.

```sql
CREATE INDEX idx_order_item_product_date ON ECommerce.order_item(product_id, order_date);
CREATE INDEX idx_product_id_name ON ECommerce.product(product_id, product_name);
```
이렇게 선정한 이유는 order_item 테이블에서 product_id와 order_date를 조인조건으로 사용하고 있고,
product 테이블에서는 product_id와 product_name을 사용하고 있기 때문에 이 컬럼들을
카디널리티가 높을 것 이라는 생각을 하였습니다.

##### 3. INDEX 적용 후 결과

##### 인덱스 적용 후 쿼리 실행 속도
![img.png](/docs/IndexQuerySpeed.png)
##### 인덱스 적용 후 쿼리 실행 계획
![img.png](/docs/firstIndexExplain.png)


분명 속도적으로 개선이 되었으나(1440 > 440) 실행계획을 보았을 때
아직 index 조회를 이루어지지 않은 부분이 있다보니 완벽한 개선이라고 하기에는 조금 모자란 부분이있었습니다.
이를 해결하기위해 인덱스를 추가로 적용하였습니다.

```sql
CREATE INDEX idx_order_item_product_date_quantity
    ON ECommerce.order_item(product_id, order_date, quantity);
```
해당 인덱스를 통해 order_item 의 index 를 조회하도록 유도하였고 성능 조회는 다음과 같습니다.

##### 추가 인덱스 적용 후 쿼리 실행 속도
![img.png](/docs/lastIndexQuerySpeed.png)
##### 추가 인덱스 적용 후 쿼리 실행 계획
![img.png](/docs/lastIndexoExplain.png)

해당 쿼리가 실행되는 과정에서 index를 조회하도록 변경하니
성능이 조금 더 개선이 된 부분을 확인할수 있습니다. (440 > 289)