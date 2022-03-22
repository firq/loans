package com.kderlatka.loans.service;

import com.kderlatka.loans.domain.LoanApplication;
import com.kderlatka.loans.exception.LoanRejectedException;
import org.junit.jupiter.params.ParameterizedTest;
import org.junit.jupiter.params.provider.Arguments;
import org.junit.jupiter.params.provider.MethodSource;
import org.junit.jupiter.params.provider.ValueSource;

import java.math.BigDecimal;
import java.time.Clock;
import java.time.Instant;
import java.time.ZoneOffset;
import java.util.stream.Stream;

import static org.junit.jupiter.api.Assertions.assertThrowsExactly;

class LoanApplicationValidatorTest {

    public static final int MIN_TERM = 30;
    public static final int MAX_TERM = 7200;
    public static final BigDecimal MIN_AMOUNT = new BigDecimal("1000.00");
    public static final BigDecimal MAX_AMOUNT = new BigDecimal("99999.99");

    private static Stream<Arguments> validArgs() {
        return Stream.of(
                Arguments.of(MIN_AMOUNT, MAX_TERM),
                Arguments.of(MIN_AMOUNT, MIN_TERM),
                Arguments.of(MAX_AMOUNT, MIN_TERM),
                Arguments.of(MAX_AMOUNT, MAX_TERM)
        );
    }

    private static Stream<Arguments> invalidArgs() {
        return Stream.of(
                Arguments.of(MIN_AMOUNT, MAX_TERM + 1),
                Arguments.of(MIN_AMOUNT, MIN_TERM - 1),
                Arguments.of(MAX_AMOUNT, MAX_TERM + 1),
                Arguments.of(MAX_AMOUNT, MIN_TERM - 1),

                Arguments.of(MIN_AMOUNT.subtract(new BigDecimal(1)), MAX_TERM),
                Arguments.of(MIN_AMOUNT.subtract(new BigDecimal(1)), MIN_TERM),
                Arguments.of(MAX_AMOUNT.add(new BigDecimal(1)), MAX_TERM),
                Arguments.of(MAX_AMOUNT.add(new BigDecimal(1)), MIN_TERM)
        );
    }

    @ParameterizedTest
    @MethodSource("validArgs")
    void validate(BigDecimal amount, long term) {

        Clock fixedClock = Clock.fixed(Instant.parse("2022-03-22T06:00:01.00Z"), ZoneOffset.UTC);
        LoanApplicationValidator validator = new LoanApplicationValidator(MIN_TERM, MAX_TERM, MIN_AMOUNT, MAX_AMOUNT, fixedClock);
        LoanApplication application = new LoanApplication(amount, term);

        validator.validate(application);
    }

    @ParameterizedTest
    @MethodSource("invalidArgs")
    void validateInvalidApplication(BigDecimal amount, long term) {

        Clock fixedClock = Clock.fixed(Instant.parse("2022-03-22T06:00:01.00Z"), ZoneOffset.UTC);
        LoanApplicationValidator validator = new LoanApplicationValidator(MIN_TERM, MAX_TERM, MIN_AMOUNT, MAX_AMOUNT, fixedClock);
        LoanApplication application = new LoanApplication(amount, term);

        assertThrowsExactly(LoanRejectedException.class, () -> validator.validate(application));
    }

    @ParameterizedTest
    @ValueSource(strings = {"2022-03-22T05:59:59.00Z", "2022-03-22T00:00:00.00Z"})
    void maxAmountDuringNighttimeShouldThrow(String clockCornerCases) {

        Clock fixedClock = Clock.fixed(Instant.parse(clockCornerCases), ZoneOffset.UTC);
        LoanApplicationValidator validator = new LoanApplicationValidator(MIN_TERM, MAX_TERM, MIN_AMOUNT, MAX_AMOUNT, fixedClock);
        LoanApplication application = new LoanApplication(MAX_AMOUNT, MAX_TERM);

        assertThrowsExactly(LoanRejectedException.class, () -> validator.validate(application));
    }
}