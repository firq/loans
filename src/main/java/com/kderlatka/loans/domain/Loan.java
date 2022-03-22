package com.kderlatka.loans.domain;

import lombok.AllArgsConstructor;
import lombok.Data;

import java.math.BigDecimal;
import java.time.LocalDate;

@Data
@AllArgsConstructor
public class Loan {

    private String id;
    private LocalDate dueTo;
    private BigDecimal amount;

}
