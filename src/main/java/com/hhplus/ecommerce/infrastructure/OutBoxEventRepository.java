package com.hhplus.ecommerce.infrastructure;

import com.hhplus.ecommerce.domain.outbox.OutboxEvent;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;

public interface OutBoxEventRepository extends JpaRepository<OutboxEvent, Long> {
    List<OutboxEvent> findAllByStatus(OutboxEvent.OutboxStatus status);
}
