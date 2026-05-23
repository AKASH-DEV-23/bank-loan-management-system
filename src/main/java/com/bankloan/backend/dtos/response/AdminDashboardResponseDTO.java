package com.bankloan.backend.dtos.response;

import lombok.Builder;
import lombok.Getter;

@Getter
@Builder
public class AdminDashboardResponseDTO {

    private Long totalCustomers;

    private Long totalEmployees;

    private Long totalLoans;

    private Long approvedLoans;

    private Long pendingLoans;

    private Long rejectedLoans;

    private Long totalRepayments;

    private Long paidRepayments;
}