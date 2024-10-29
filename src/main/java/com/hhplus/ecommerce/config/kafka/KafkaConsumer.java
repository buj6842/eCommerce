package com.hhplus.ecommerce.config.kafka;

import org.springframework.kafka.annotation.KafkaListener;

public class KafkaConsumer {
    @KafkaListener(topics = "order-group",groupId = "prod-group")
    public void consumeMessage(String message) {
        System.out.println("Received Message: " + message);
    }
}
