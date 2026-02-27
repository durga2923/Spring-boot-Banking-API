package com.microfinanceBank.Loan.dto;

import lombok.Data;
import lombok.NoArgsConstructor;

import jakarta.validation.constraints.NotNull;
import jakarta.validation.constraints.Positive;
import java.io.Serializable;
import java.math.BigDecimal;
import java.time.Duration;
import java.time.LocalDate;
import java.time.temporal.ChronoUnit;

@Data
@NoArgsConstructor
public class MakePaymentDto  implements Serializable {
    private static final long serialVersionUID= 1L;

    @NotNull
    private String loanId;
    @Positive
    @NotNull
    private BigDecimal paymentAmount;

}
