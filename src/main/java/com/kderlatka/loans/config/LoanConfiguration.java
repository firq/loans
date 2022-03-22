package com.kderlatka.loans.config;

import com.kderlatka.loans.mapping.LoanMapper;
import com.kderlatka.loans.repository.LoanRepository;
import com.kderlatka.loans.service.LoanApplicationValidator;
import com.kderlatka.loans.service.LoanExtender;
import com.kderlatka.loans.service.LoanService;
import lombok.RequiredArgsConstructor;
import org.springframework.boot.context.properties.EnableConfigurationProperties;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

import java.time.Clock;

@Configuration
@EnableConfigurationProperties(LoanProperties.class)
@RequiredArgsConstructor
public class LoanConfiguration {

    private final LoanProperties properties;

    @Bean
    public LoanService loanService(LoanRepository repository, LoanMapper mapper) {
        return new LoanService(repository, mapper, loanValidator(), loanExtender());
    }

    private LoanExtender loanExtender() {
        return new LoanExtender(properties.getExtensionTerm());
    }

    private LoanApplicationValidator loanValidator() {
        return new LoanApplicationValidator(properties.getMinTerm(), properties.getMaxTerm(),
                properties.getMinAmount(), properties.getMaxAmount(), Clock.systemDefaultZone());
    }
}
