package com.microfinanceBank.commondto.transaction;

import com.microfinanceBank.commondto.transaction.LocationDto;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;
import jakarta.validation.constraints.Positive;
import java.io.Serializable;
import java.math.BigDecimal;

@Data
@AllArgsConstructor
@NoArgsConstructor
@Builder
public class TransactionDto implements Serializable {

    @NotNull
    private Long sourceAccount;
    @NotNull
    @Positive
    private BigDecimal amount;
    @NotNull
    @NotBlank
    private String description;
    private LocationDto locationDto;
}
