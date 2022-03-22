package com.kderlatka.loans.service;

import com.kderlatka.loans.repository.model.LoanEntity;
import lombok.RequiredArgsConstructor;

@RequiredArgsConstructor
public class LoanExtender {

    private final int extensionTerm;

    public void extend(LoanEntity loan) {
        var dueTo = loan.getDueTo();
        var newTerm = dueTo.plusDays(extensionTerm);
        loan.setDueTo(newTerm);
    }
}
