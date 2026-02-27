package com.microfinanceBank.Customer.dto;

import com.microfinanceBank.Customer.Config.filters.annotation.XssFilter;
import com.microfinanceBank.Customer.enums.AccountType;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import jakarta.validation.Valid;
import jakarta.validation.constraints.NotNull;
import java.io.Serializable;

@Data
@Builder
@NoArgsConstructor
@AllArgsConstructor
public class Register {

    @Valid
    private CustomerDto customer;
    @NotNull
    private AccountType accountType;
}
