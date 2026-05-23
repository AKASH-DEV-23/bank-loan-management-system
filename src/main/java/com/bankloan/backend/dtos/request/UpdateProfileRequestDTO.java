package com.bankloan.backend.dtos.request;

import jakarta.validation.constraints.NotBlank;
import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
public class UpdateProfileRequestDTO {

    @NotBlank
    private String name;

    @NotBlank
    private String phone;

    @NotBlank
    private String address;
}