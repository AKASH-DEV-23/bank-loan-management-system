package com.bankloan.backend.services.impl;

import com.bankloan.backend.dtos.response.AdminDashboardResponseDTO;
import com.bankloan.backend.dtos.response.CustomerDashboardResponseDTO;
import com.bankloan.backend.dtos.response.EmployeeDashboardResponseDTO;

import com.bankloan.backend.entities.Customer;
import com.bankloan.backend.entities.User;

import com.bankloan.backend.enums.LoanStatus;
import com.bankloan.backend.enums.PaymentStatus;
import com.bankloan.backend.enums.UserRole;

import com.bankloan.backend.repositories.LoanApplicationRepository;
import com.bankloan.backend.repositories.RepaymentRepository;
import com.bankloan.backend.repositories.UserRepository;

import com.bankloan.backend.services.DashboardService;

import lombok.RequiredArgsConstructor;

import org.springframework.stereotype.Service;

@Service
@RequiredArgsConstructor
public class DashboardServiceImpl
        implements DashboardService {

    private final UserRepository userRepository;

    private final LoanApplicationRepository
            loanApplicationRepository;

    private final RepaymentRepository repaymentRepository;

    @Override
    public AdminDashboardResponseDTO
    getAdminDashboard() {

        return AdminDashboardResponseDTO.builder()

                .totalCustomers(
                        userRepository.countByRole(
                                UserRole.CUSTOMER
                        )
                )

                .totalEmployees(
                        userRepository.countByRole(
                                UserRole.EMPLOYEE
                        )
                )

                .totalLoans(
                        loanApplicationRepository.count()
                )

                .approvedLoans(
                        loanApplicationRepository
                                .countByApprovalStatus(
                                        LoanStatus.APPROVED
                                )
                )

                .pendingLoans(
                        loanApplicationRepository
                                .countByApprovalStatus(
                                        LoanStatus.PENDING
                                )
                )

                .rejectedLoans(
                        loanApplicationRepository
                                .countByApprovalStatus(
                                        LoanStatus.REJECTED
                                )
                )

                .totalRepayments(
                        repaymentRepository.count()
                )

                .paidRepayments(
                        repaymentRepository
                                .countByPaymentStatus(
                                        PaymentStatus.PAID
                                )
                )

                .build();
    }

    @Override
    public CustomerDashboardResponseDTO
    getCustomerDashboard(String email) {

        User user = userRepository.findByEmail(email)
                .orElseThrow(() ->
                        new RuntimeException(
                                "User not found"
                        ));

        Customer customer = user.getCustomer();

        Long totalApplications =
                (long) customer.getLoanApplications()
                        .size();

        Long approvedLoans =
                customer.getLoanApplications()
                        .stream()
                        .filter(app ->
                                app.getApprovalStatus()
                                        == LoanStatus.APPROVED
                        )
                        .count();

        Long pendingLoans =
                customer.getLoanApplications()
                        .stream()
                        .filter(app ->
                                app.getApprovalStatus()
                                        == LoanStatus.PENDING
                        )
                        .count();

        Long totalPendingEmis =
                customer.getLoanApplications()
                        .stream()
                        .flatMap(app ->
                                app.getRepayments().stream()
                        )
                        .filter(rep ->
                                rep.getPaymentStatus()
                                        == PaymentStatus.PENDING
                        )
                        .count();

        Long totalPaidEmis =
                customer.getLoanApplications()
                        .stream()
                        .flatMap(app ->
                                app.getRepayments().stream()
                        )
                        .filter(rep ->
                                rep.getPaymentStatus()
                                        == PaymentStatus.PAID
                        )
                        .count();

        return CustomerDashboardResponseDTO.builder()
                .totalApplications(totalApplications)
                .approvedLoans(approvedLoans)
                .pendingLoans(pendingLoans)
                .totalPendingEmis(totalPendingEmis)
                .totalPaidEmis(totalPaidEmis)
                .build();
    }

    @Override
    public EmployeeDashboardResponseDTO
    getEmployeeDashboard() {

        return EmployeeDashboardResponseDTO.builder()

                .underReviewApplications(
                        loanApplicationRepository
                                .countByApprovalStatus(
                                        LoanStatus.UNDER_REVIEW
                                )
                )

                .pendingApplications(
                        loanApplicationRepository
                                .countByApprovalStatus(
                                        LoanStatus.PENDING
                                )
                )

                .approvedLoans(
                        loanApplicationRepository
                                .countByApprovalStatus(
                                        LoanStatus.APPROVED
                                )
                )

                .build();
    }
}