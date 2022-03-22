package com.kderlatka.loans.common;

import com.kderlatka.loans.domain.Loan;
import com.kderlatka.loans.domain.LoanApplication;
import com.kderlatka.loans.repository.model.LoanEntity;

import java.math.BigDecimal;
import java.time.LocalDate;

public enum TestsCommons {
    ;

    public static final LoanApplication LOAN_APPLICATION = new LoanApplication(new BigDecimal("100.00"), 20);
    public static final String ID = "id";
    public static final Loan LOAN = new Loan(ID, LocalDate.now().plusDays(10), new BigDecimal("100.00"));
    public static final LoanEntity LOAN_ENTITY = new LoanEntity(ID, LocalDate.now().plusDays(10), new BigDecimal("100.00"));

}
