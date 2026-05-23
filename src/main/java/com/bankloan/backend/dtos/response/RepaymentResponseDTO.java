package com.bankloan.backend.dtos.response;

import lombok.Builder;
import lombok.Getter;

import java.time.LocalDate;

@Getter
@Builder
public class RepaymentResponseDTO {

    private Long repaymentId;

    private Long applicationId;

    private Double amountDue;
    
    private Double paidAmount;

    private String paymentMethod;

    private String transactionId;

    private LocalDate dueDate;

    private LocalDate paymentDate;

    private String paymentStatus;
}