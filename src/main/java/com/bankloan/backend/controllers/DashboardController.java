package com.bankloan.backend.controllers;

import com.bankloan.backend.dtos.response.AdminDashboardResponseDTO;
import com.bankloan.backend.dtos.response.ApiResponse;
import com.bankloan.backend.dtos.response.CustomerDashboardResponseDTO;
import com.bankloan.backend.dtos.response.EmployeeDashboardResponseDTO;

import com.bankloan.backend.services.DashboardService;

import lombok.RequiredArgsConstructor;

import org.springframework.http.ResponseEntity;

import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.security.core.Authentication;

import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("/api/dashboard")
@RequiredArgsConstructor
public class DashboardController {

    private final DashboardService dashboardService;

    @GetMapping("/admin")
    @PreAuthorize("hasRole('ADMIN')")
    public ResponseEntity<ApiResponse<AdminDashboardResponseDTO>>
    getAdminDashboard() {

        AdminDashboardResponseDTO response =
                dashboardService.getAdminDashboard();

        return ResponseEntity.ok(
                ApiResponse
                        .<AdminDashboardResponseDTO>builder()
                        .success(true)
                        .message("Admin dashboard fetched")
                        .data(response)
                        .build()
        );
    }

    @GetMapping("/customer")
    @PreAuthorize("hasRole('CUSTOMER')")
    public ResponseEntity<ApiResponse<CustomerDashboardResponseDTO>>
    getCustomerDashboard(
            Authentication authentication
    ) {

        CustomerDashboardResponseDTO response =
                dashboardService.getCustomerDashboard(
                        authentication.getName()
                );

        return ResponseEntity.ok(
                ApiResponse
                        .<CustomerDashboardResponseDTO>builder()
                        .success(true)
                        .message("Customer dashboard fetched")
                        .data(response)
                        .build()
        );
    }

    @GetMapping("/employee")
    @PreAuthorize("hasRole('EMPLOYEE')")
    public ResponseEntity<ApiResponse<EmployeeDashboardResponseDTO>>
    getEmployeeDashboard() {

        EmployeeDashboardResponseDTO response =
                dashboardService.getEmployeeDashboard();

        return ResponseEntity.ok(
                ApiResponse
                        .<EmployeeDashboardResponseDTO>builder()
                        .success(true)
                        .message("Employee dashboard fetched")
                        .data(response)
                        .build()
        );
    }
}