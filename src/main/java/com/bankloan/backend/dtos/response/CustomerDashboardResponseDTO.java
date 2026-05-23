package com.bankloan.backend.dtos.response;

import lombok.Builder;
import lombok.Getter;

@Getter
@Builder
public class CustomerDashboardResponseDTO {

    private Long totalApplications;

    private Long approvedLoans;

    private Long pendingLoans;

    private Long totalPendingEmis;

    private Long totalPaidEmis;
}