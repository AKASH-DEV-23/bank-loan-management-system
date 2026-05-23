package com.bankloan.backend.dtos.request;

import com.bankloan.backend.enums.PaymentMethod;

import jakarta.validation.constraints.NotNull;

import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
public class PayRepaymentRequestDTO {

    @NotNull
    private PaymentMethod paymentMethod;
}