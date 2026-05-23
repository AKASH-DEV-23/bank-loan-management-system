package com.bankloan.backend.controllers;

import com.bankloan.backend.dtos.request.PayRepaymentRequestDTO;
import com.bankloan.backend.dtos.response.ApiResponse;
import com.bankloan.backend.dtos.response.RepaymentResponseDTO;

import com.bankloan.backend.services.RepaymentService;

import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;

import org.springframework.http.ResponseEntity;

import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.security.core.Authentication;

import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/api/repayments")
@RequiredArgsConstructor
public class RepaymentController {

    private final RepaymentService repaymentService;

    @GetMapping("/my")
    @PreAuthorize("hasRole('CUSTOMER')")
    public ResponseEntity<ApiResponse<List<RepaymentResponseDTO>>> getMyRepayments(Authentication authentication) {

        List<RepaymentResponseDTO> response = repaymentService.getMyRepayments(authentication.getName());

        return ResponseEntity.ok(ApiResponse.<List<RepaymentResponseDTO>>builder().success(true).message("Repayments fetched successfully").data(response).build());
    }

    @GetMapping("/application/{id}")
    @PreAuthorize("hasRole('CUSTOMER')")
    public ResponseEntity<ApiResponse<List<RepaymentResponseDTO>>> getRepaymentsByApplication(@PathVariable Long id) {

        List<RepaymentResponseDTO> response = repaymentService.getRepaymentsByApplication(id);

        return ResponseEntity.ok(ApiResponse.<List<RepaymentResponseDTO>>builder().success(true).message("Application repayments fetched").data(response).build());
    }

    @PutMapping("/{id}/pay")
    @PreAuthorize("hasRole('CUSTOMER')")
    public ResponseEntity<ApiResponse<RepaymentResponseDTO>> payRepayment(

            @PathVariable Long id,

            @Valid @RequestBody PayRepaymentRequestDTO request) {

        RepaymentResponseDTO response = repaymentService.payRepayment(id,request);

        return ResponseEntity.ok(ApiResponse.<RepaymentResponseDTO>builder().success(true).message("Repayment successful").data(response).build());
    }

    @GetMapping
    @PreAuthorize("hasAnyRole('ADMIN', 'EMPLOYEE')")
    public ResponseEntity<ApiResponse<List<RepaymentResponseDTO>>> getAllRepayments() {

        List<RepaymentResponseDTO> response = repaymentService.getAllRepayments();

        return ResponseEntity.ok(ApiResponse.<List<RepaymentResponseDTO>>builder().success(true).message("All repayments fetched").data(response).build());
    }
}