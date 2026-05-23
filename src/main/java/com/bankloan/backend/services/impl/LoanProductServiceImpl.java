package com.bankloan.backend.services.impl;

import com.bankloan.backend.dtos.request.LoanProductRequestDTO;
import com.bankloan.backend.dtos.response.LoanProductResponseDTO;

import com.bankloan.backend.entities.LoanProduct;

import com.bankloan.backend.repositories.LoanProductRepository;

import com.bankloan.backend.services.LoanProductService;

import lombok.RequiredArgsConstructor;

import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;

@Service
@RequiredArgsConstructor
@Transactional
public class LoanProductServiceImpl
        implements LoanProductService {

    private final LoanProductRepository loanProductRepository;

    @Override
    public LoanProductResponseDTO createLoanProduct(
            LoanProductRequestDTO request
    ) {

        LoanProduct loanProduct = LoanProduct.builder()
                .productName(request.getProductName())
                .interestRate(request.getInterestRate())
                .minAmount(request.getMinAmount())
                .maxAmount(request.getMaxAmount())
                .tenure(request.getTenure())
                .build();

        loanProductRepository.save(loanProduct);

        return mapToResponse(loanProduct);
    }

    @Override
    public List<LoanProductResponseDTO> getAllLoanProducts() {

        return loanProductRepository.findAll()
                .stream()
                .map(this::mapToResponse)
                .toList();
    }

    @Override
    public LoanProductResponseDTO getLoanProductById(Long id) {

        LoanProduct loanProduct =
                loanProductRepository.findById(id)
                        .orElseThrow(() ->
                                new RuntimeException(
                                        "Loan product not found"
                                ));

        return mapToResponse(loanProduct);
    }

    @Override
    public LoanProductResponseDTO updateLoanProduct(
            Long id,
            LoanProductRequestDTO request
    ) {

        LoanProduct loanProduct =
                loanProductRepository.findById(id)
                        .orElseThrow(() ->
                                new RuntimeException(
                                        "Loan product not found"
                                ));

        loanProduct.setProductName(request.getProductName());
        loanProduct.setInterestRate(request.getInterestRate());
        loanProduct.setMinAmount(request.getMinAmount());
        loanProduct.setMaxAmount(request.getMaxAmount());
        loanProduct.setTenure(request.getTenure());

        loanProductRepository.save(loanProduct);

        return mapToResponse(loanProduct);
    }

    @Override
    public void deleteLoanProduct(Long id) {

        LoanProduct loanProduct =
                loanProductRepository.findById(id)
                        .orElseThrow(() ->
                                new RuntimeException(
                                        "Loan product not found"
                                ));

        loanProductRepository.delete(loanProduct);
    }

    private LoanProductResponseDTO mapToResponse(
            LoanProduct loanProduct
    ) {

        return LoanProductResponseDTO.builder()
                .loanProductId(loanProduct.getLoanProductId())
                .productName(loanProduct.getProductName())
                .interestRate(loanProduct.getInterestRate())
                .minAmount(loanProduct.getMinAmount())
                .maxAmount(loanProduct.getMaxAmount())
                .tenure(loanProduct.getTenure())
                .build();
    }
}