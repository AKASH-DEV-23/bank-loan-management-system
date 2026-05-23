package com.bankloan.backend.services;

import com.bankloan.backend.dtos.request.LoanProductRequestDTO;
import com.bankloan.backend.dtos.response.LoanProductResponseDTO;

import java.util.List;

public interface LoanProductService {

    LoanProductResponseDTO createLoanProduct(
            LoanProductRequestDTO request
    );

    List<LoanProductResponseDTO> getAllLoanProducts();

    LoanProductResponseDTO getLoanProductById(Long id);

    LoanProductResponseDTO updateLoanProduct(
            Long id,
            LoanProductRequestDTO request
    );

    void deleteLoanProduct(Long id);
}