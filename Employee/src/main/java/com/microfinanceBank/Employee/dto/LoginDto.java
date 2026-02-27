package com.microfinanceBank.Employee.dto;

import lombok.Data;

import jakarta.validation.constraints.Email;
import jakarta.validation.constraints.NotNull;

@Data
public class LoginDto {

    @NotNull
    private String password;
    @Email(message = "Please enter a valid email address")
    @NotNull
    private String email;

}
