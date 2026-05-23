package com.bankloan.backend.repositories;

import com.bankloan.backend.entities.LoanApplication;
import com.bankloan.backend.entities.Repayment;

import com.bankloan.backend.enums.PaymentStatus;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;

public interface RepaymentRepository
        extends JpaRepository<Repayment, Long> {

    List<Repayment> findByLoanApplication(
            LoanApplication loanApplication
    );
    Long countByPaymentStatus(PaymentStatus status);
}