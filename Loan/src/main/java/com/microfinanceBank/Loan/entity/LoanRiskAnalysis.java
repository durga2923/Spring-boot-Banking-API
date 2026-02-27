package com.microfinanceBank.Loan.entity;

import lombok.Data;
import lombok.NoArgsConstructor;

import jakarta.persistence.Entity;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import java.io.Serializable;
import java.math.BigDecimal;

@Entity
@Data
@NoArgsConstructor
public class LoanRiskAnalysis implements Serializable {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;
    private boolean loanWorthinessAnalysis;
    private BigDecimal monthlyPayment;
    private BigDecimal amountLeftAfterExpenses;
    private int creditScoreRating;

}
