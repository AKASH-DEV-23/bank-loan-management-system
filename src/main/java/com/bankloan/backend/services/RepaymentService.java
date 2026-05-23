package com.bankloan.backend.services;

import com.bankloan.backend.dtos.request.PayRepaymentRequestDTO;
import com.bankloan.backend.dtos.response.RepaymentResponseDTO;

import java.util.List;

public interface RepaymentService {

    void generateRepaymentSchedule(Long applicationId);

    List<RepaymentResponseDTO> getMyRepayments(
            String email
    );

    List<RepaymentResponseDTO> getRepaymentsByApplication(
            Long applicationId
    );

    RepaymentResponseDTO payRepayment(
            Long repaymentId,
            PayRepaymentRequestDTO request
    );


    List<RepaymentResponseDTO> getAllRepayments();
}