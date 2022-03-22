package com.kderlatka.loans.service;

import com.kderlatka.loans.domain.LoanApplication;
import com.kderlatka.loans.exception.LoanRejectedException;
import lombok.RequiredArgsConstructor;

import java.math.BigDecimal;
import java.time.Clock;
import java.time.LocalDateTime;

@RequiredArgsConstructor
public class LoanApplicationValidator {

    private final int minTerm;
    private final int maxTerm;
    private final BigDecimal minAmount;
    private final BigDecimal maxAmount;
    private final Clock clock;

    void validate(LoanApplication loanApplication) {
        checkTerm(loanApplication);
        checkAmount(loanApplication);
        checkMaxAmountAtNighttime(loanApplication);
    }

    private void checkTerm(LoanApplication loanApplication) {
        var term = loanApplication.getTerm();
        if (term < minTerm || term > maxTerm)
            throw new LoanRejectedException("Invalid term of loan: " + term + ".\nSpecify term in between: " + minTerm + " and " + maxTerm);
    }

    private void checkAmount(LoanApplication loanApplication) {
        BigDecimal amount = loanApplication.getAmount();
        if (amount.compareTo(minAmount) < 0 || amount.compareTo(maxAmount) > 0)
            throw new LoanRejectedException("Invalid amount of loan: " + amount + ".\nSpecify amount in between: " + minAmount + " and " + maxAmount);
    }

    private void checkMaxAmountAtNighttime(LoanApplication loanApplication) {
        if (maxAmountRequested(loanApplication) && isNighttime())
            throw new LoanRejectedException("Requested for max loan at the nighttime");
    }

    private boolean isNighttime() {
        var currentHour = LocalDateTime.now(clock).getHour();
        return currentHour <= 5;
    }

    private boolean maxAmountRequested(LoanApplication loanApplication) {
        return loanApplication.getAmount().equals(maxAmount);
    }
}
