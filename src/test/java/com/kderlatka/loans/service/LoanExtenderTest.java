package com.kderlatka.loans.service;

import com.kderlatka.loans.repository.model.LoanEntity;
import org.junit.jupiter.api.Test;

import java.math.BigDecimal;
import java.time.LocalDate;

import static com.kderlatka.loans.common.TestsCommons.ID;
import static org.junit.jupiter.api.Assertions.assertTrue;

class LoanExtenderTest {

    private static final int EXTENSION_TERM = 30;
    private static final LoanExtender LOAN_EXTENDER = new LoanExtender(EXTENSION_TERM);

    @Test
    void canExtendLoan() {
        var term = LocalDate.now().minusDays(10);
        LoanEntity loan = loan(term);

        var extended = LOAN_EXTENDER.extend(loan);

        assertTrue(term.isBefore(extended.getDueTo()));
    }

    private LoanEntity loan(LocalDate term) {
        return new LoanEntity(ID, term, new BigDecimal("100.00"));
    }

}