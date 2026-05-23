package com.bankloan.backend.controllers;

import com.bankloan.backend.dtos.request.LoanProductRequestDTO;
import com.bankloan.backend.dtos.response.ApiResponse;
import com.bankloan.backend.dtos.response.LoanProductResponseDTO;

import com.bankloan.backend.services.LoanProductService;

import jakarta.validation.Valid;

import lombok.RequiredArgsConstructor;

import org.springframework.http.ResponseEntity;

import org.springframework.security.access.prepost.PreAuthorize;

import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/api/loan-products")
@RequiredArgsConstructor
public class LoanProductController {

    private final LoanProductService loanProductService;

    @PostMapping
    @PreAuthorize("hasRole('ADMIN')")
    public ResponseEntity<ApiResponse<LoanProductResponseDTO>>
    createLoanProduct(

            @Valid @RequestBody
            LoanProductRequestDTO request
    ) {

        LoanProductResponseDTO loanProduct =
                loanProductService.createLoanProduct(request);

        return ResponseEntity.ok(
                ApiResponse.<LoanProductResponseDTO>builder()
                        .success(true)
                        .message("Loan product created successfully")
                        .data(loanProduct)
                        .build()
        );
    }

    @GetMapping
    public ResponseEntity<ApiResponse<List<LoanProductResponseDTO>>>
    getAllLoanProducts() {

        List<LoanProductResponseDTO> loanProducts =
                loanProductService.getAllLoanProducts();

        return ResponseEntity.ok(
                ApiResponse.<List<LoanProductResponseDTO>>builder()
                        .success(true)
                        .message("Loan products fetched successfully")
                        .data(loanProducts)
                        .build()
        );
    }

    @GetMapping("/{id}")
    public ResponseEntity<ApiResponse<LoanProductResponseDTO>>
    getLoanProductById(@PathVariable Long id) {

        LoanProductResponseDTO loanProduct =
                loanProductService.getLoanProductById(id);

        return ResponseEntity.ok(
                ApiResponse.<LoanProductResponseDTO>builder()
                        .success(true)
                        .message("Loan product fetched successfully")
                        .data(loanProduct)
                        .build()
        );
    }

    @PutMapping("/{id}")
    @PreAuthorize("hasRole('ADMIN')")
    public ResponseEntity<ApiResponse<LoanProductResponseDTO>>
    updateLoanProduct(

            @PathVariable Long id,

            @Valid @RequestBody
            LoanProductRequestDTO request
    ) {

        LoanProductResponseDTO loanProduct =
                loanProductService.updateLoanProduct(id, request);

        return ResponseEntity.ok(
                ApiResponse.<LoanProductResponseDTO>builder()
                        .success(true)
                        .message("Loan product updated successfully")
                        .data(loanProduct)
                        .build()
        );
    }

    @DeleteMapping("/{id}")
    @PreAuthorize("hasRole('ADMIN')")
    public ResponseEntity<ApiResponse<Object>>
    deleteLoanProduct(@PathVariable Long id) {

        loanProductService.deleteLoanProduct(id);

        return ResponseEntity.ok(
                ApiResponse.builder()
                        .success(true)
                        .message("Loan product deleted successfully")
                        .data(null)
                        .build()
        );
    }
}