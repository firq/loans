package com.kderlatka.loans.exception;

import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.ResponseStatus;

@ResponseStatus(HttpStatus.BAD_REQUEST)
public class InvalidLoanIdException extends RuntimeException {
    public InvalidLoanIdException(String s, IllegalArgumentException e) {
        super(s, e);
    }

    public InvalidLoanIdException(String s) {
        super(s);
    }
}
