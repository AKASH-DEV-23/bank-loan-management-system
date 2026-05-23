package com.bankloan.backend.services;

import com.bankloan.backend.dtos.request.ChangePasswordRequestDTO;
import com.bankloan.backend.dtos.request.LoginRequestDTO;
import com.bankloan.backend.dtos.request.RegisterRequestDTO;
import com.bankloan.backend.dtos.request.UpdateProfileRequestDTO;
import com.bankloan.backend.dtos.response.AuthResponseDTO;
import com.bankloan.backend.dtos.response.UserResponseDTO;
import jakarta.servlet.http.HttpServletResponse;

public interface AuthService {

    AuthResponseDTO register(
            RegisterRequestDTO request,
            HttpServletResponse response
    );

    AuthResponseDTO login(
            LoginRequestDTO request,
            HttpServletResponse response
    );
    UserResponseDTO currentUser(String email);

    UserResponseDTO updateProfile(
            String email,
            UpdateProfileRequestDTO request
    );

    void changePassword(
            String email,
            ChangePasswordRequestDTO request
    );

    void logout(HttpServletResponse response);
}