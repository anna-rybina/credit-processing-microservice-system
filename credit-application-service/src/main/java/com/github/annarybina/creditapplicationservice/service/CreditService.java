package com.github.annarybina.creditapplicationservice.service;

import com.github.annarybina.creditapplicationservice.dto.CreditApplicationEvent;
import com.github.annarybina.creditapplicationservice.dto.CreditApplicationRequest;
import com.github.annarybina.creditapplicationservice.entity.CreditApplication;
import com.github.annarybina.creditapplicationservice.repository.CreditApplicationRepository;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.kafka.core.KafkaTemplate;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import java.time.LocalDateTime;

@Service
@RequiredArgsConstructor
@Slf4j
public class CreditService {

    private final CreditApplicationRepository repository;
    private final KafkaTemplate<String, CreditApplicationEvent> kafkaTemplate;

    @Value("${credit.kafka.topic}")
    private String kafkaTopic;

    @Transactional
    public CreditApplication submitApplication(CreditApplicationRequest request) {
        CreditApplication application = new CreditApplication();
        application.setAmount(request.getAmount());
        application.setTermMonths(request.getTermMonths());
        application.setMonthlyIncome(request.getMonthlyIncome());
        application.setCurrentCreditLoad(request.getCurrentCreditLoad());
        application.setCreditRating(request.getCreditRating());
        application.setStatus("PENDING");
        application.setCreatedAt(LocalDateTime.now());

        CreditApplication savedApplication = repository.save(application);
        log.info("Saved application with ID: {}", savedApplication.getId());

        CreditApplicationEvent event = new CreditApplicationEvent();
        event.setApplicationId(savedApplication.getId());
        event.setAmount(savedApplication.getAmount());
        event.setTermMonths(savedApplication.getTermMonths());
        event.setMonthlyIncome(savedApplication.getMonthlyIncome());
        event.setCurrentCreditLoad(savedApplication.getCurrentCreditLoad());
        event.setCreditRating(savedApplication.getCreditRating());

        kafkaTemplate.send(kafkaTopic, String.valueOf(event.getApplicationId()), event);
        log.info("Sent to Kafka: {}", event.getApplicationId());

        return savedApplication;
    }

    public String getApplicationStatus(Long id) {
        return repository.findById(id)
                .map(CreditApplication::getStatus)
                .orElseThrow(() -> new RuntimeException("Application not found"));
    }
}