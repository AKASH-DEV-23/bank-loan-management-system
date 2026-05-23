package com.bankloan.backend.services;

import com.bankloan.backend.dtos.request.CreateEmployeeRequestDTO;
import com.bankloan.backend.dtos.response.EmployeeResponseDTO;

import java.util.List;

public interface AdminService {

    EmployeeResponseDTO createEmployee(
            CreateEmployeeRequestDTO request
    );

    List<EmployeeResponseDTO> getAllEmployees();

    void deleteEmployee(Long employeeId);
}