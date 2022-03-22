package com.kderlatka.loans.service;

import com.kderlatka.loans.domain.Loan;
import com.kderlatka.loans.domain.LoanApplication;
import com.kderlatka.loans.exception.LoanNotFoundException;
import com.kderlatka.loans.mapping.LoanMapper;
import com.kderlatka.loans.repository.LoanRepository;
import com.kderlatka.loans.repository.model.LoanEntity;
import lombok.RequiredArgsConstructor;

import static com.kderlatka.loans.service.PrincipalCalculator.toEntityWithPrincipal;

@RequiredArgsConstructor
public class LoanService {

    private final LoanRepository repository;
    private final LoanMapper mapper;
    private final LoanApplicationValidator loanApplicationValidator;
    private final LoanExtender loanExtender;

    public Loan apply(LoanApplication loanApplication) {
        loanApplicationValidator.validate(loanApplication);
        return mapper.toLoan(repository.save(toEntityWithPrincipal(loanApplication)));
    }

    public Loan extend(String loanId) {
        LoanEntity loan = loanEntityById(loanId);
        loanExtender.extend(loan);
        return mapper.toLoan(repository.save(loan));
    }

    public Loan fetch(String loanId) {
        return mapper.toLoan(loanEntityById(loanId));
    }

    private LoanEntity loanEntityById(String loanId) {
        return repository.findById(loanId)
                .orElseThrow(() -> new LoanNotFoundException("Loan with id: " + loanId + " not found"));
    }

}
