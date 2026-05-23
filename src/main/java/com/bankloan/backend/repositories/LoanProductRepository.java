package com.bankloan.backend.repositories;

import com.bankloan.backend.entities.LoanProduct;

import org.springframework.data.jpa.repository.JpaRepository;

public interface LoanProductRepository
        extends JpaRepository<LoanProduct, Long> {
}