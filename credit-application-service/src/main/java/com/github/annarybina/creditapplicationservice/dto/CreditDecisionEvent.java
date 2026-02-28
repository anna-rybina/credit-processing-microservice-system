package com.github.annarybina.creditapplicationservice.dto;

import lombok.Data;

@Data
public class CreditDecisionEvent {
    private Long applicationId;
    private String decision;
    private String message;
}