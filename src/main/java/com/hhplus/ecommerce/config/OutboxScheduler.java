package com.hhplus.ecommerce.config;

import com.hhplus.ecommerce.domain.outbox.OutboxEvent;
import com.hhplus.ecommerce.infrastructure.OutBoxEventRepository;
import com.hhplus.ecommerce.infrastructure.kafka.KafkaProducer;
import lombok.RequiredArgsConstructor;
import org.springframework.scheduling.annotation.Scheduled;
import org.springframework.stereotype.Component;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;

@Component
@RequiredArgsConstructor
public class OutboxScheduler {
    private final OutBoxEventRepository outBoxEventRepository;
    private final KafkaProducer kafkaProducer;

    @Transactional
    @Scheduled(fixedRate = 5000)
    public void processOutboxEvent() {
        List<OutboxEvent> events = outBoxEventRepository.findAllByStatus(OutboxEvent.OutboxStatus.INIT);

        for (OutboxEvent event : events) {
            try {
                kafkaProducer.sendMessage("order-group", "order", event.getPayload());
                event.successStatus();
                outBoxEventRepository.save(event);
            } catch (Exception e) {
                event.failedStatus();
                outBoxEventRepository.save(event);
            }
        }
    }

}
