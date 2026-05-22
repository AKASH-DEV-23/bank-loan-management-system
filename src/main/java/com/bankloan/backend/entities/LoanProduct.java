package com.bankloan.backend.entities;

import jakarta.persistence.*;
import lombok.*;

import java.time.LocalDateTime;
import java.util.List;

@Entity
@Table(name = "loan_products")
@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
@Builder
public class LoanProduct {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long loanProductId;

    @Column(nullable = false)
    private String productName;

    @Column(nullable = false)
    private Double interestRate;

    @Column(nullable = false)
    private Double minAmount;

    @Column(nullable = false)
    private Double maxAmount;

    @Column(nullable = false)
    private Integer tenure;

    @OneToMany(mappedBy = "loanProduct")
    private List<LoanApplication> loanApplications;

    @Column(updatable = false)
    private LocalDateTime createdAt;

    private LocalDateTime updatedAt;

    @PrePersist
    protected void onCreate() {
        createdAt = LocalDateTime.now();
    }

    @PreUpdate
    protected void onUpdate() {
        updatedAt = LocalDateTime.now();
    }
}