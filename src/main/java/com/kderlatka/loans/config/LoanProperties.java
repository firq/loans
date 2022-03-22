package com.kderlatka.loans.config;

import lombok.Value;
import lombok.experimental.NonFinal;
import org.springframework.boot.context.properties.ConfigurationProperties;
import org.springframework.boot.context.properties.ConstructorBinding;
import org.springframework.validation.annotation.Validated;

import java.math.BigDecimal;

@ConfigurationProperties(prefix = "loan")
@Value
@NonFinal
@Validated
@ConstructorBinding
public class LoanProperties {

    BigDecimal minAmount;
    BigDecimal maxAmount;
    int minTerm;
    int maxTerm;
    int extensionTerm;

}
