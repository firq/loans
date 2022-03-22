package com.kderlatka.loans.domain;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.math.BigDecimal;

@Data
@AllArgsConstructor
@NoArgsConstructor
public class LoanApplication {

    private BigDecimal amount;
    private long term;
}
