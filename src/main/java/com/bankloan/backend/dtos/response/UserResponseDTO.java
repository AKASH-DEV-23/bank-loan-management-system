package com.bankloan.backend.dtos.response;

import lombok.Builder;
import lombok.Getter;

@Getter
@Builder
public class UserResponseDTO {

    private Long id;
    private String name;
    private String email;
    private String role;
}