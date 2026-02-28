package com.github.annarybina.creditapplicationservice.listener;

import com.github.annarybina.creditapplicationservice.dto.CreditDecisionEvent;
import com.github.annarybina.creditapplicationservice.entity.CreditApplication;
import com.github.annarybina.creditapplicationservice.repository.CreditApplicationRepository;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.amqp.rabbit.annotation.RabbitListener;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

@Service
@RequiredArgsConstructor
@Slf4j
public class RabbitMQListener {

    private final CreditApplicationRepository repository;

    @RabbitListener(queues = "${credit.rabbitmq.queue}")
    @Transactional
    public void handleDecision(CreditDecisionEvent decision) {
        log.info("Received decision: {}", decision);

        CreditApplication application = repository.findById(decision.getApplicationId())
                .orElseThrow(() -> new RuntimeException("Application not found"));

        application.setStatus(decision.getDecision());
        repository.save(application);

        log.info("Updated application {} status to: {}", decision.getApplicationId(), decision.getDecision());
    }
}