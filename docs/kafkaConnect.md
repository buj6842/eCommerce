### 카프카 연동 보고서

카프카 연동을위해 application.yml 파일 설정변경한 부분입니다.

```yaml
  kafka:
    consumer:
      bootstrap-servers: localhost:9092
      group-id: order-group
      auto-offset-reset: earliest
      key-deserializer: org.apache.kafka.common.serialization.StringDeserializer
      value-deserializer: org.apache.kafka.common.serialization.StringDeserializer
    producer:
      bootstrap-servers: localhost:9092
      key-serializer: org.apache.kafka.common.serialization.StringSerializer
      value-serializer: org.apache.kafka.common.serialization.StringSerializer
```

카프가 연동을위해 docker-compose.yml 파일을 추가하여 실행시켰습니다.
    
```yaml
version: '3.8'
services:
  zookeeper:
    image: confluentinc/cp-zookeeper:7.4.1
    container_name: zookeeper
    ports:
      - "2181:2181"
    environment:
      ZOOKEEPER_CLIENT_PORT: 2181
      ZOOKEEPER_TICK_TIME: 2000

  kafka:
    image: confluentinc/cp-kafka:7.4.1
    container_name: kafka
    ports:
      - "9092:9092"
    environment:
      KAFKA_BROKER_ID: 1
      KAFKA_ZOOKEEPER_CONNECT: zookeeper:2181
      KAFKA_ADVERTISED_LISTENERS: PLAINTEXT://localhost:9092
      KAFKA_OFFSETS_TOPIC_REPLICATION_FACTOR: 1
    depends_on:
      - zookeeper
```

테스트를 위해 인기상품 조회시 test-topic을 생성하도록 변경하였습니다(임시)
    
```java
public List<TopOrderProduct> getTopOrderProduct() {
        kafkaProducer.sendMessage("product-topic", "PRODUCT_SUCCESS");
        return orderService.getTopOrderProduct();
    }
```
api 실행 후 topic을 조회한 사진입니다.

![img.png](/docs/kafkaconnect.png)

test-topic이 나온것으로 연동까지 완료하였습니다.