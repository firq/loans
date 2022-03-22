package com.kderlatka.loans.mapping;

import com.kderlatka.loans.domain.Loan;
import com.kderlatka.loans.repository.model.LoanEntity;
import org.mapstruct.Mapper;

@Mapper(componentModel = "spring")
public interface LoanMapper {

    Loan toLoan(LoanEntity loanEntity);

    LoanEntity toEntity(Loan loan);

}
