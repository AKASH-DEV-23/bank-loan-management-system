package com.bankloan.backend.dtos.request;

import jakarta.validation.constraints.*;

import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
public class LoanProductRequestDTO {

    @NotBlank
    private String productName;

    @NotNull
    @Positive
    private Double interestRate;

    @NotNull
    @Positive
    private Double minAmount;

    @NotNull
    @Positive
    private Double maxAmount;

    @NotNull
    @Positive
    private Integer tenure;
}