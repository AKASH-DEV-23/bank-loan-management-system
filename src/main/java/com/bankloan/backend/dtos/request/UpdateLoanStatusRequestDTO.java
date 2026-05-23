package com.bankloan.backend.dtos.request;

import com.bankloan.backend.enums.LoanStatus;

import jakarta.validation.constraints.NotNull;

import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
public class UpdateLoanStatusRequestDTO {

    @NotNull
    private LoanStatus status;
}