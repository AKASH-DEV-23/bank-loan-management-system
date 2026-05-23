package com.bankloan.backend.services.impl;

import com.bankloan.backend.dtos.request.LoanApplicationRequestDTO;
import com.bankloan.backend.dtos.request.UpdateLoanStatusRequestDTO;

import com.bankloan.backend.dtos.response.LoanApplicationResponseDTO;

import com.bankloan.backend.entities.Customer;
import com.bankloan.backend.entities.LoanApplication;
import com.bankloan.backend.entities.LoanProduct;
import com.bankloan.backend.entities.User;

import com.bankloan.backend.enums.LoanStatus;

import com.bankloan.backend.repositories.CustomerRepository;
import com.bankloan.backend.repositories.LoanApplicationRepository;
import com.bankloan.backend.repositories.LoanProductRepository;
import com.bankloan.backend.repositories.UserRepository;

import com.bankloan.backend.services.LoanApplicationService;

import com.bankloan.backend.services.RepaymentService;
import lombok.RequiredArgsConstructor;

import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.time.LocalDate;
import java.util.List;

@Service
@RequiredArgsConstructor
@Transactional
public class LoanApplicationServiceImpl
        implements LoanApplicationService {

    private final LoanApplicationRepository loanApplicationRepository;
    private final RepaymentService repaymentService;

    private final UserRepository userRepository;

    private final CustomerRepository customerRepository;

    private final LoanProductRepository loanProductRepository;

    @Override
    public LoanApplicationResponseDTO applyLoan(
            String email,
            LoanApplicationRequestDTO request
    ) {

        User user = userRepository.findByEmail(email)
                .orElseThrow(() ->
                        new RuntimeException("User not found"));

        Customer customer = user.getCustomer();

        LoanProduct loanProduct =
                loanProductRepository.findById(
                        request.getLoanProductId()
                ).orElseThrow(() ->
                        new RuntimeException(
                                "Loan product not found"
                        ));

        LoanApplication loanApplication =
                LoanApplication.builder()
                        .customer(customer)
                        .loanProduct(loanProduct)
                        .loanAmount(request.getLoanAmount())
                        .applicationDate(LocalDate.now())
                        .approvalStatus(LoanStatus.PENDING)
                        .build();

        loanApplicationRepository.save(loanApplication);

        return mapToResponse(loanApplication);
    }

    @Override
    public List<LoanApplicationResponseDTO>
    getMyApplications(String email) {

        User user = userRepository.findByEmail(email)
                .orElseThrow(() ->
                        new RuntimeException("User not found"));

        Customer customer = user.getCustomer();

        return loanApplicationRepository
                .findByCustomer(customer)
                .stream()
                .map(this::mapToResponse)
                .toList();
    }

    @Override
    public List<LoanApplicationResponseDTO>
    getAllApplications() {

        return loanApplicationRepository.findAll()
                .stream()
                .map(this::mapToResponse)
                .toList();
    }

    @Override
    public LoanApplicationResponseDTO getApplicationById(
            Long id
    ) {

        LoanApplication loanApplication =
                loanApplicationRepository.findById(id)
                        .orElseThrow(() ->
                                new RuntimeException(
                                        "Loan application not found"
                                ));

        return mapToResponse(loanApplication);
    }

    @Override
    public LoanApplicationResponseDTO updateLoanStatus(
            Long id,
            UpdateLoanStatusRequestDTO request
    ) {

        LoanApplication loanApplication =
                loanApplicationRepository.findById(id)
                        .orElseThrow(() ->
                                new RuntimeException(
                                        "Loan application not found"
                                ));

        loanApplication.setApprovalStatus(
                request.getStatus()
        );
        if(request.getStatus().name().equals("APPROVED")) {

            repaymentService.generateRepaymentSchedule(
                    loanApplication.getApplicationId()
            );
        }

        loanApplicationRepository.save(loanApplication);

        return mapToResponse(loanApplication);
    }

    private LoanApplicationResponseDTO mapToResponse(
            LoanApplication loanApplication
    ) {

        return LoanApplicationResponseDTO.builder()
                .applicationId(
                        loanApplication.getApplicationId()
                )
                .customerName(
                        loanApplication.getCustomer()
                                .getUser()
                                .getName()
                )
                .loanProductName(
                        loanApplication.getLoanProduct()
                                .getProductName()
                )
                .loanAmount(
                        loanApplication.getLoanAmount()
                )
                .applicationDate(
                        loanApplication.getApplicationDate()
                )
                .approvalStatus(
                        loanApplication.getApprovalStatus()
                                .name()
                )
                .build();
    }
}