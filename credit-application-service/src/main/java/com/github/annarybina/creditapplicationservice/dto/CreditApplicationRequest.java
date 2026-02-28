package com.github.annarybina.creditapplicationservice.dto;

import lombok.Data;
import java.math.BigDecimal;

@Data
public class CreditApplicationRequest {
    private BigDecimal amount;
    private Integer termMonths;
    private BigDecimal monthlyIncome;
    private BigDecimal currentCreditLoad;
    private Integer creditRating;
}
