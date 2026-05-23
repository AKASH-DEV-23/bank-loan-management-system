package com.bankloan.backend.dtos.response;

import lombok.Builder;
import lombok.Getter;

import java.time.LocalDate;

@Getter
@Builder
public class LoanApplicationResponseDTO {

    private Long applicationId;

    private String customerName;

    private String loanProductName;

    private Double loanAmount;

    private LocalDate applicationDate;

    private String approvalStatus;
}