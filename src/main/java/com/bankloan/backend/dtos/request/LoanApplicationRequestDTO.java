package com.bankloan.backend.dtos.request;

import jakarta.validation.constraints.NotNull;
import jakarta.validation.constraints.Positive;

import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
public class LoanApplicationRequestDTO {

    @NotNull
    private Long loanProductId;

    @NotNull
    @Positive
    private Double loanAmount;
}