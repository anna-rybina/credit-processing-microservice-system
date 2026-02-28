package com.github.annarybina.creditdecisionservice.listener;

import com.github.annarybina.creditdecisionservice.dto.CreditApplicationEvent;
import com.github.annarybina.creditdecisionservice.dto.CreditDecisionEvent;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.amqp.rabbit.core.RabbitTemplate;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.kafka.annotation.KafkaListener;
import org.springframework.stereotype.Service;
import java.math.BigDecimal;
import java.math.RoundingMode;

@Service
@Slf4j
@RequiredArgsConstructor
public class CreditDecisionListener {  // ← ИЗМЕНИЛ ЗДЕСЬ

    private final RabbitTemplate rabbitTemplate;

    @Value("${credit.rabbitmq.exchange}")
    private String exchange;

    @Value("${credit.rabbitmq.routing-key}")
    private String routingKey;

    @KafkaListener(topics = "${credit.kafka.topic}")
    public void handleApplication(CreditApplicationEvent event) {
        log.info("Received from Kafka: {}", event);

        CreditDecisionEvent decision = makeDecision(event);

        rabbitTemplate.convertAndSend(exchange, routingKey, decision);
        log.info("Sent to RabbitMQ: {}", decision);
    }

    private CreditDecisionEvent makeDecision(CreditApplicationEvent event) {
        BigDecimal monthlyPayment = event.getAmount()
                .divide(BigDecimal.valueOf(event.getTermMonths()), 2, RoundingMode.HALF_UP);

        BigDecimal totalObligation = monthlyPayment.add(event.getCurrentCreditLoad());
        BigDecimal threshold = event.getMonthlyIncome().multiply(BigDecimal.valueOf(0.5));

        if (totalObligation.compareTo(threshold) <= 0) {
            return new CreditDecisionEvent(event.getApplicationId(), "APPROVED", "Approved");
        } else {
            return new CreditDecisionEvent(event.getApplicationId(), "REJECTED", "Monthly payment exceeds 50% of income");
        }
    }
}
