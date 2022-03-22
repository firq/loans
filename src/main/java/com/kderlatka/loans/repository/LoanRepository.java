package com.kderlatka.loans.repository;

import com.kderlatka.loans.repository.model.LoanEntity;
import org.springframework.data.repository.CrudRepository;

public interface LoanRepository extends CrudRepository<LoanEntity, String> {

}
