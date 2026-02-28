package com.github.annarybina.creditdecisionservice.dto;

import lombok.Data;
import java.math.BigDecimal;

@Data
public class CreditApplicationEvent {
    private Long applicationId;
    private BigDecimal amount;
    private Integer termMonths;
    private BigDecimal monthlyIncome;
    private BigDecimal currentCreditLoad;
    private Integer creditRating;
}
