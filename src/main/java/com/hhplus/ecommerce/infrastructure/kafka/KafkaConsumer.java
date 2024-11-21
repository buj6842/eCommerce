package com.hhplus.ecommerce.infrastructure.kafka;

import org.springframework.kafka.annotation.KafkaListener;

public class KafkaConsumer {
    @KafkaListener(topics = "order-group",groupId = "order-group")
    public void consumeMessage(String message) {
        System.out.println("테스트 전달받은 메시지: " + message);
    }
}
