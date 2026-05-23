package com.bankloan.backend.dtos.response;

import lombok.Builder;
import lombok.Getter;

@Getter
@Builder
public class LoanProductResponseDTO {

    private Long loanProductId;

    private String productName;

    private Double interestRate;

    private Double minAmount;

    private Double maxAmount;

    private Integer tenure;
}