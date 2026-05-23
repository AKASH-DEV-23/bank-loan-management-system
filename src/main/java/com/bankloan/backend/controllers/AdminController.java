package com.bankloan.backend.controllers;

import com.bankloan.backend.dtos.request.CreateEmployeeRequestDTO;

import com.bankloan.backend.dtos.response.ApiResponse;
import com.bankloan.backend.dtos.response.EmployeeResponseDTO;

import com.bankloan.backend.services.AdminService;

import jakarta.validation.Valid;

import lombok.RequiredArgsConstructor;

import org.springframework.http.ResponseEntity;

import org.springframework.security.access.prepost.PreAuthorize;

import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/api/admin")
@RequiredArgsConstructor
@PreAuthorize("hasRole('ADMIN')")
public class AdminController {

    private final AdminService adminService;

    @PostMapping("/employees")
    public ResponseEntity<ApiResponse<EmployeeResponseDTO>>
    createEmployee(

            @Valid @RequestBody
            CreateEmployeeRequestDTO request
    ) {

        EmployeeResponseDTO response =
                adminService.createEmployee(request);

        return ResponseEntity.ok(
                ApiResponse.<EmployeeResponseDTO>builder()
                        .success(true)
                        .message("Employee created successfully")
                        .data(response)
                        .build()
        );
    }

    @GetMapping("/employees")
    public ResponseEntity<ApiResponse<List<EmployeeResponseDTO>>>
    getAllEmployees() {

        List<EmployeeResponseDTO> response =
                adminService.getAllEmployees();

        return ResponseEntity.ok(
                ApiResponse
                        .<List<EmployeeResponseDTO>>builder()
                        .success(true)
                        .message("Employees fetched successfully")
                        .data(response)
                        .build()
        );
    }

    @DeleteMapping("/employees/{id}")
    public ResponseEntity<ApiResponse<Object>>
    deleteEmployee(@PathVariable Long id) {

        adminService.deleteEmployee(id);

        return ResponseEntity.ok(
                ApiResponse.builder()
                        .success(true)
                        .message("Employee deleted successfully")
                        .data(null)
                        .build()
        );
    }
}