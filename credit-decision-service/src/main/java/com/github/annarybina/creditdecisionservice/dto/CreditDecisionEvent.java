package com.github.annarybina.creditdecisionservice.dto;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@NoArgsConstructor
@AllArgsConstructor
public class CreditDecisionEvent {
    private Long applicationId;
    private String decision;
    private String message;
}