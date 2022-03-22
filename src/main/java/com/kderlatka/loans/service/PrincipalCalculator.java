package com.kderlatka.loans.service;

import com.kderlatka.loans.domain.LoanApplication;
import com.kderlatka.loans.repository.model.LoanEntity;

import java.math.BigDecimal;
import java.time.LocalDate;

public enum PrincipalCalculator {
    ;

    private static final BigDecimal MULTIPLICAND = new BigDecimal("0.1");

    static LoanEntity toEntityWithPrincipal(LoanApplication loanApplication) {
        return LoanEntity.builder()
                .amount(addPrincipal(loanApplication))
                .dueTo(LocalDate.now().plusDays(loanApplication.getTerm()))
                .build();
    }

    private static BigDecimal addPrincipal(LoanApplication loanApplication) {
        BigDecimal amount = loanApplication.getAmount();
        return amount.add(amount.multiply(MULTIPLICAND));
    }
}
