package com.bankloan.backend.repositories;

import com.bankloan.backend.entities.Customer;
import com.bankloan.backend.entities.LoanApplication;

import com.bankloan.backend.enums.LoanStatus;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;

public interface LoanApplicationRepository
        extends JpaRepository<LoanApplication, Long> {

    List<LoanApplication> findByCustomer(Customer customer);

    Long countByApprovalStatus(LoanStatus status);
}