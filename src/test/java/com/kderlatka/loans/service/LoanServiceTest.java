package com.kderlatka.loans.service;

import com.kderlatka.loans.exception.LoanNotFoundException;
import com.kderlatka.loans.mapping.LoanMapper;
import com.kderlatka.loans.repository.LoanRepository;
import com.kderlatka.loans.repository.model.LoanEntity;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import java.util.Optional;

import static com.kderlatka.loans.common.TestsCommons.*;
import static org.junit.jupiter.api.Assertions.assertThrowsExactly;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.Mockito.*;

class LoanServiceTest {

    private LoanService service;
    private LoanRepository repository;
    private LoanMapper mapper;
    private LoanApplicationValidator loanApplicationValidator;
    private LoanExtender loanExtender;

    @BeforeEach
    void setup() {
        repository = mock(LoanRepository.class);
        mapper = mock(LoanMapper.class);
        loanApplicationValidator = mock(LoanApplicationValidator.class);
        loanExtender = mock(LoanExtender.class);
        service = new LoanService(repository, mapper, loanApplicationValidator, loanExtender);
    }

    @Test
    void apply() {
        doReturn(LOAN_ENTITY).when(repository).save(any(LoanEntity.class));
        doReturn(LOAN).when(mapper).toLoan(LOAN_ENTITY);

        service.apply(LOAN_APPLICATION);

        verify(loanApplicationValidator, times(1)).validate(LOAN_APPLICATION);
    }

    @Test
    void extend() {
        fetchSuccessMock();

        service.extend(ID);

        verify(loanExtender, times(1)).extend(LOAN_ENTITY);
    }

    @Test
    void fetch() {
        fetchSuccessMock();

        var result = service.fetch(ID);

        Assertions.assertSame(result, LOAN);
    }

    @Test
    void fetchFailed() {
        doReturn(Optional.empty()).when(repository).findById(ID);

        assertThrowsExactly(LoanNotFoundException.class, () -> service.fetch(ID));
    }

    private void fetchSuccessMock() {
        doReturn(Optional.of(LOAN_ENTITY)).when(repository).findById(ID);
        doReturn(LOAN).when(mapper).toLoan(LOAN_ENTITY);
    }

}