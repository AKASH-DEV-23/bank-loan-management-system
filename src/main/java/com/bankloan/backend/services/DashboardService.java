package com.bankloan.backend.services;

import com.bankloan.backend.dtos.response.AdminDashboardResponseDTO;
import com.bankloan.backend.dtos.response.CustomerDashboardResponseDTO;
import com.bankloan.backend.dtos.response.EmployeeDashboardResponseDTO;

public interface DashboardService {

    AdminDashboardResponseDTO getAdminDashboard();

    CustomerDashboardResponseDTO getCustomerDashboard(
            String email
    );

    EmployeeDashboardResponseDTO getEmployeeDashboard();
}