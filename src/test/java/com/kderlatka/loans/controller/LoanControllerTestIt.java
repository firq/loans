package com.kderlatka.loans.controller;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.kderlatka.loans.config.LoanProperties;
import com.kderlatka.loans.domain.Loan;
import com.kderlatka.loans.domain.LoanApplication;
import com.kderlatka.loans.repository.LoanRepository;
import com.kderlatka.loans.repository.model.LoanEntity;
import lombok.SneakyThrows;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.reactive.AutoConfigureWebTestClient;
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.http.MediaType;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.MvcResult;

import java.math.BigDecimal;
import java.time.LocalDate;
import java.util.Optional;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertTrue;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.post;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

@SpringBootTest
@AutoConfigureMockMvc
@AutoConfigureWebTestClient
class LoanControllerTestIt {

    @Autowired
    private MockMvc mvc;

    @Autowired
    private LoanProperties properties;

    @Autowired
    private LoanRepository repository;

    @Autowired
    private ObjectMapper objectMapper;

    @Test
    void applyForLoan() throws Exception {
        LoanApplication request = new LoanApplication(properties.getMinAmount(), properties.getMinTerm());

        MvcResult result = mvc.perform(post("/loan/apply")
                        .contentType(MediaType.APPLICATION_JSON)
                        .content(toJson(request)))
                .andExpect(status().isOk())
                .andReturn();
        String responseStr = result.getResponse().getContentAsString();
        Loan loan = objectMapper.readValue(responseStr, Loan.class);

        assertEquals(new BigDecimal("110.00"), loan.getAmount());
        assertTrue(loan.getDueTo().isAfter(LocalDate.now()));
        Optional<LoanEntity> stored = repository.findById(loan.getId());
        assertTrue(stored.isPresent());
    }

    @SneakyThrows
    private String toJson(LoanApplication request) {
        return objectMapper.writeValueAsString(request);
    }
}