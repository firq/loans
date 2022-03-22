package com.kderlatka.loans.exception;

import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.ResponseStatus;

@ResponseStatus(HttpStatus.UNPROCESSABLE_ENTITY)
public class LoanRejectedException extends RuntimeException {
    public LoanRejectedException(String s) {
        super(s);
    }
}
