package com.kderlatka.loans.domain;

import lombok.AllArgsConstructor;
import lombok.Data;

import java.math.BigDecimal;

@Data
@AllArgsConstructor
public class LoanApplication {

    private BigDecimal amount;
    private long term;
}
