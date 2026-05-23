package com.bankloan.backend.services;

import com.bankloan.backend.dtos.request.LoanApplicationRequestDTO;
import com.bankloan.backend.dtos.request.UpdateLoanStatusRequestDTO;

import com.bankloan.backend.dtos.response.LoanApplicationResponseDTO;

import java.util.List;

public interface LoanApplicationService {

    LoanApplicationResponseDTO applyLoan(
            String email,
            LoanApplicationRequestDTO request
    );

    List<LoanApplicationResponseDTO> getMyApplications(
            String email
    );

    List<LoanApplicationResponseDTO> getAllApplications();

    LoanApplicationResponseDTO getApplicationById(Long id);

    LoanApplicationResponseDTO updateLoanStatus(
            Long id,
            UpdateLoanStatusRequestDTO request
    );
}