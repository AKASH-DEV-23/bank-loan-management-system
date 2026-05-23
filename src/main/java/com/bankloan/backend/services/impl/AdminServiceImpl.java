package com.bankloan.backend.services.impl;

import com.bankloan.backend.dtos.request.CreateEmployeeRequestDTO;
import com.bankloan.backend.dtos.response.EmployeeResponseDTO;

import com.bankloan.backend.entities.User;

import com.bankloan.backend.enums.UserRole;

import com.bankloan.backend.repositories.UserRepository;

import com.bankloan.backend.services.AdminService;

import lombok.RequiredArgsConstructor;

import org.springframework.security.crypto.password.PasswordEncoder;

import org.springframework.stereotype.Service;

import java.util.List;

@Service
@RequiredArgsConstructor
public class AdminServiceImpl
        implements AdminService {

    private final UserRepository userRepository;

    private final PasswordEncoder passwordEncoder;

    @Override
    public EmployeeResponseDTO createEmployee(
            CreateEmployeeRequestDTO request
    ) {

        if(userRepository.existsByEmail(
                request.getEmail()
        )) {

            throw new RuntimeException(
                    "Email already exists"
            );
        }

        User employee = User.builder()
                .name(request.getName())
                .email(request.getEmail())
                .password(
                        passwordEncoder.encode(
                                request.getPassword()
                        )
                )
                .role(UserRole.EMPLOYEE)
                .build();

        userRepository.save(employee);

        return mapToResponse(employee);
    }

    @Override
    public List<EmployeeResponseDTO>
    getAllEmployees() {

        return userRepository.findAll()
                .stream()
                .filter(user ->
                        user.getRole()
                                == UserRole.EMPLOYEE
                )
                .map(this::mapToResponse)
                .toList();
    }

    @Override
    public void deleteEmployee(Long employeeId) {

        User employee = userRepository.findById(employeeId)
                .orElseThrow(() ->
                        new RuntimeException(
                                "Employee not found"
                        ));

        if(employee.getRole() != UserRole.EMPLOYEE) {

            throw new RuntimeException(
                    "User is not employee"
            );
        }

        userRepository.delete(employee);
    }

    private EmployeeResponseDTO mapToResponse(
            User user
    ) {

        return EmployeeResponseDTO.builder()
                .id(user.getId())
                .name(user.getName())
                .email(user.getEmail())
                .role(user.getRole().name())
                .build();
    }
}