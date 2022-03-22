package com.kderlatka.loans.service;

import com.kderlatka.loans.repository.model.LoanEntity;
import lombok.RequiredArgsConstructor;

@RequiredArgsConstructor
public class LoanExtender {

    private final int extensionTerm;

    public LoanEntity extend(LoanEntity loan) {
        var dueTo = loan.getDueTo();
        var newTerm = dueTo.plusDays(extensionTerm);
        return new LoanEntity(loan.getId(), newTerm, loan.getAmount());
    }
}
