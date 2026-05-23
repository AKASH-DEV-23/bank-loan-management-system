package com.bankloan.backend.dtos.response;

import lombok.Builder;
import lombok.Getter;

@Getter
@Builder
public class EmployeeResponseDTO {

    private Long id;

    private String name;

    private String email;

    private String role;
}