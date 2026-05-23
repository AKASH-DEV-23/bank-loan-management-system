package com.bankloan.backend.dtos.response;

import lombok.Builder;
import lombok.Getter;

@Getter
@Builder
public class EmployeeDashboardResponseDTO {

    private Long underReviewApplications;

    private Long pendingApplications;

    private Long approvedLoans;
}