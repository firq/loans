package com.kderlatka.loans.service;

import com.kderlatka.loans.domain.LoanApplication;
import org.junit.jupiter.api.Test;

import java.math.BigDecimal;
import java.time.LocalDate;

import static org.junit.jupiter.api.Assertions.assertTrue;

class PrincipalCalculatorTest {

    @Test
    void toEntityWithPrincipal() {

        BigDecimal requestedAmount = new BigDecimal("1000.00");
        var application = new LoanApplication(requestedAmount, 60);

        var result = PrincipalCalculator.toEntityWithPrincipal(application);

        assertTrue(result.getAmount().compareTo(requestedAmount) > 0);
        assertTrue(result.getDueTo().isAfter(LocalDate.now()));
    }
}