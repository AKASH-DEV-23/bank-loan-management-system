package com.bankloan.backend.services.impl;

import com.bankloan.backend.dtos.request.PayRepaymentRequestDTO;
import com.bankloan.backend.dtos.response.RepaymentResponseDTO;

import com.bankloan.backend.entities.Customer;
import com.bankloan.backend.entities.LoanApplication;
import com.bankloan.backend.entities.LoanProduct;
import com.bankloan.backend.entities.Repayment;
import com.bankloan.backend.entities.User;

import com.bankloan.backend.enums.PaymentStatus;

import com.bankloan.backend.repositories.LoanApplicationRepository;
import com.bankloan.backend.repositories.RepaymentRepository;
import com.bankloan.backend.repositories.UserRepository;

import com.bankloan.backend.services.RepaymentService;

import lombok.RequiredArgsConstructor;

import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.time.LocalDate;
import java.util.ArrayList;
import java.util.List;
import java.util.UUID;

@Service
@RequiredArgsConstructor
@Transactional
public class RepaymentServiceImpl implements RepaymentService {

    private final RepaymentRepository repaymentRepository;

    private final LoanApplicationRepository loanApplicationRepository;

    private final UserRepository userRepository;

    @Override
    public void generateRepaymentSchedule(Long applicationId) {

        LoanApplication loanApplication = loanApplicationRepository.findById(applicationId).orElseThrow(() -> new RuntimeException("Loan application not found"));

        LoanProduct loanProduct = loanApplication.getLoanProduct();

        Double principal = loanApplication.getLoanAmount();

        Double annualInterestRate = loanProduct.getInterestRate();

        Integer tenure = loanProduct.getTenure();

        double monthlyRate = annualInterestRate / 12 / 100;

        double emi = (principal * monthlyRate * Math.pow(1 + monthlyRate, tenure)) / (Math.pow(1 + monthlyRate, tenure) - 1);

        List<Repayment> repayments = new ArrayList<>();

        for (int i = 1; i <= tenure; i++) {

            Repayment repayment = Repayment.builder().loanApplication(loanApplication).amountDue(emi).dueDate(LocalDate.now().plusMonths(i)).paymentStatus(PaymentStatus.PENDING).build();

            repayments.add(repayment);
        }

        repaymentRepository.saveAll(repayments);
    }

    @Override
    public List<RepaymentResponseDTO> getMyRepayments(String email) {

        User user = userRepository.findByEmail(email).orElseThrow(() -> new RuntimeException("User not found"));

        Customer customer = user.getCustomer();

        List<Repayment> repayments = customer.getLoanApplications().stream().flatMap(app -> repaymentRepository.findByLoanApplication(app).stream()).toList();

        return repayments.stream().map(this::mapToResponse).toList();
    }

    @Override
    public List<RepaymentResponseDTO> getRepaymentsByApplication(Long applicationId) {

        LoanApplication loanApplication = loanApplicationRepository.findById(applicationId).orElseThrow(() -> new RuntimeException("Loan application not found"));

        return repaymentRepository.findByLoanApplication(loanApplication).stream().map(this::mapToResponse).toList();
    }

    @Override
    public RepaymentResponseDTO payRepayment(Long repaymentId, PayRepaymentRequestDTO request) {

        Repayment repayment = repaymentRepository.findById(repaymentId).orElseThrow(() -> new RuntimeException("Repayment not found"));

        if (repayment.getPaymentStatus() == PaymentStatus.PAID) {

            throw new RuntimeException("Repayment already paid");
        }

        repayment.setPaymentStatus(PaymentStatus.PAID);

        repayment.setPaymentDate(LocalDate.now());

        repayment.setPaidAmount(repayment.getAmountDue());

        repayment.setTransactionId(UUID.randomUUID().toString());

        repayment.setPaymentMethod(request.getPaymentMethod());

        repaymentRepository.save(repayment);

        return mapToResponse(repayment);
    }

    @Override
    public List<RepaymentResponseDTO> getAllRepayments() {

        return repaymentRepository.findAll().stream().map(this::mapToResponse).toList();
    }

    private RepaymentResponseDTO mapToResponse(Repayment repayment) {

        return RepaymentResponseDTO.builder().repaymentId(repayment.getRepaymentId()).applicationId(repayment.getLoanApplication().getApplicationId()).amountDue(repayment.getAmountDue()).dueDate(repayment.getDueDate()).paymentDate(repayment.getPaymentDate()).paymentStatus(repayment.getPaymentStatus().name()).paidAmount(repayment.getPaidAmount())

                .paymentMethod(repayment.getPaymentMethod().name())

                .transactionId(repayment.getTransactionId()).build();
    }
}