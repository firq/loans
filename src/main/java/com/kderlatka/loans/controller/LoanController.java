package com.kderlatka.loans.controller;

import com.kderlatka.loans.domain.Loan;
import com.kderlatka.loans.domain.LoanApplication;
import com.kderlatka.loans.service.LoanService;
import lombok.RequiredArgsConstructor;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/loan")
@RequiredArgsConstructor
public class LoanController {

    private final LoanService service;

    @PostMapping("/apply")
    public Loan applyForLoan(@RequestBody LoanApplication loanApplication) {
        return service.apply(loanApplication);
    }

    @PostMapping("/extend/{loanId}")
    public Loan extendLoan(@PathVariable String loanId) {
        return service.extend(loanId);
    }

    @GetMapping("/{loanId}")
    public Loan fetchLoan(@PathVariable String loanId) {
        return service.fetch(loanId);
    }

}
