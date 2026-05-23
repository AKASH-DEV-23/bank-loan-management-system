package com.bankloan.backend.controllers;

import com.bankloan.backend.dtos.request.ChangePasswordRequestDTO;
import com.bankloan.backend.dtos.request.LoginRequestDTO;
import com.bankloan.backend.dtos.request.RegisterRequestDTO;
import com.bankloan.backend.dtos.request.UpdateProfileRequestDTO;
import com.bankloan.backend.dtos.response.ApiResponse;
import com.bankloan.backend.dtos.response.AuthResponseDTO;
import com.bankloan.backend.dtos.response.UserResponseDTO;
import com.bankloan.backend.services.AuthService;

import jakarta.servlet.http.HttpServletResponse;
import jakarta.validation.Valid;

import lombok.RequiredArgsConstructor;

import org.springframework.http.ResponseEntity;
import org.springframework.security.core.Authentication;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/api/auth")
@RequiredArgsConstructor
public class AuthController {

    private final AuthService authService;

    @PostMapping("/register")
    public ResponseEntity<ApiResponse<AuthResponseDTO>> register(@Valid @RequestBody RegisterRequestDTO request, HttpServletResponse response) {
        AuthResponseDTO authResponse = authService.register(request, response);
        ApiResponse<AuthResponseDTO> apiResponse = ApiResponse.<AuthResponseDTO>builder().success(true).message("User registered successfully").data(authResponse).build();
        return ResponseEntity.ok(apiResponse);
    }

    @PostMapping("/login")
    public ResponseEntity<ApiResponse<AuthResponseDTO>> login(@Valid @RequestBody LoginRequestDTO request, HttpServletResponse response) {
        AuthResponseDTO authResponse = authService.login(request, response);
        ApiResponse<AuthResponseDTO> apiResponse = ApiResponse.<AuthResponseDTO>builder().success(true).message("Login successful").data(authResponse).build();
        return ResponseEntity.ok(apiResponse);
    }

    @GetMapping("/me")
    public ResponseEntity<ApiResponse<UserResponseDTO>> currentUser(
            Authentication authentication
    ) {

        UserResponseDTO user =
                authService.currentUser(authentication.getName());

        return ResponseEntity.ok(
                ApiResponse.<UserResponseDTO>builder()
                        .success(true)
                        .message("Current user fetched successfully")
                        .data(user)
                        .build()
        );
    }

    @PutMapping("/profile")
    public ResponseEntity<ApiResponse<UserResponseDTO>> updateProfile(

            Authentication authentication,

            @Valid @RequestBody
            UpdateProfileRequestDTO request
    ) {

        UserResponseDTO user =
                authService.updateProfile(
                        authentication.getName(),
                        request
                );

        return ResponseEntity.ok(
                ApiResponse.<UserResponseDTO>builder()
                        .success(true)
                        .message("Profile updated successfully")
                        .data(user)
                        .build()
        );
    }
    @PutMapping("/change-password")
    public ResponseEntity<ApiResponse<Object>> changePassword(

            Authentication authentication,

            @Valid @RequestBody
            ChangePasswordRequestDTO request
    ) {

        authService.changePassword(
                authentication.getName(),
                request
        );

        return ResponseEntity.ok(
                ApiResponse.builder()
                        .success(true)
                        .message("Password changed successfully")
                        .data(null)
                        .build()
        );
    }

    @PostMapping("/logout")
    public ResponseEntity<ApiResponse<Object>> logout(
            HttpServletResponse response
    ) {

        authService.logout(response);

        return ResponseEntity.ok(
                ApiResponse.builder()
                        .success(true)
                        .message("Logout successful")
                        .data(null)
                        .build()
        );
    }
}