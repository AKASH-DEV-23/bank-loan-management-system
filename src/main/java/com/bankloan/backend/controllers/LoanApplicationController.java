package com.bankloan.backend.controllers;

import com.bankloan.backend.dtos.request.LoanApplicationRequestDTO;
import com.bankloan.backend.dtos.request.UpdateLoanStatusRequestDTO;

import com.bankloan.backend.dtos.response.ApiResponse;
import com.bankloan.backend.dtos.response.LoanApplicationResponseDTO;

import com.bankloan.backend.services.LoanApplicationService;

import jakarta.validation.Valid;

import lombok.RequiredArgsConstructor;

import org.springframework.http.ResponseEntity;

import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.security.core.Authentication;

import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/api/loan-applications")
@RequiredArgsConstructor
public class LoanApplicationController {

    private final LoanApplicationService loanApplicationService;

    @PostMapping
    @PreAuthorize("hasRole('CUSTOMER')")
    public ResponseEntity<ApiResponse<LoanApplicationResponseDTO>>
    applyLoan(

            Authentication authentication,

            @Valid @RequestBody
            LoanApplicationRequestDTO request
    ) {

        LoanApplicationResponseDTO response =
                loanApplicationService.applyLoan(
                        authentication.getName(),
                        request
                );

        return ResponseEntity.ok(
                ApiResponse.<LoanApplicationResponseDTO>builder()
                        .success(true)
                        .message("Loan application submitted")
                        .data(response)
                        .build()
        );
    }

    @GetMapping("/my")
    @PreAuthorize("hasRole('CUSTOMER')")
    public ResponseEntity<ApiResponse<List<LoanApplicationResponseDTO>>>
    getMyApplications(Authentication authentication) {

        List<LoanApplicationResponseDTO> response =
                loanApplicationService.getMyApplications(
                        authentication.getName()
                );

        return ResponseEntity.ok(
                ApiResponse
                        .<List<LoanApplicationResponseDTO>>builder()
                        .success(true)
                        .message("Applications fetched successfully")
                        .data(response)
                        .build()
        );
    }

    @GetMapping
    @PreAuthorize("hasAnyRole('ADMIN', 'EMPLOYEE')")
    public ResponseEntity<ApiResponse<List<LoanApplicationResponseDTO>>>
    getAllApplications() {

        List<LoanApplicationResponseDTO> response =
                loanApplicationService.getAllApplications();

        return ResponseEntity.ok(
                ApiResponse
                        .<List<LoanApplicationResponseDTO>>builder()
                        .success(true)
                        .message("All applications fetched")
                        .data(response)
                        .build()
        );
    }

    @GetMapping("/{id}")
    public ResponseEntity<ApiResponse<LoanApplicationResponseDTO>>
    getApplicationById(@PathVariable Long id) {

        LoanApplicationResponseDTO response =
                loanApplicationService.getApplicationById(id);

        return ResponseEntity.ok(
                ApiResponse.<LoanApplicationResponseDTO>builder()
                        .success(true)
                        .message("Application fetched successfully")
                        .data(response)
                        .build()
        );
    }

    @PutMapping("/{id}/status")
    @PreAuthorize("hasAnyRole('ADMIN', 'EMPLOYEE')")
    public ResponseEntity<ApiResponse<LoanApplicationResponseDTO>>
    updateLoanStatus(

            @PathVariable Long id,

            @Valid @RequestBody
            UpdateLoanStatusRequestDTO request
    ) {

        LoanApplicationResponseDTO response =
                loanApplicationService.updateLoanStatus(
                        id,
                        request
                );

        return ResponseEntity.ok(
                ApiResponse.<LoanApplicationResponseDTO>builder()
                        .success(true)
                        .message("Loan status updated")
                        .data(response)
                        .build()
        );
    }
}