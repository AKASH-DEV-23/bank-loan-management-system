package com.bankloan.backend.services.impl;

import com.bankloan.backend.dtos.request.ChangePasswordRequestDTO;
import com.bankloan.backend.dtos.request.LoginRequestDTO;
import com.bankloan.backend.dtos.request.RegisterRequestDTO;
import com.bankloan.backend.dtos.request.UpdateProfileRequestDTO;
import com.bankloan.backend.dtos.response.AuthResponseDTO;
import com.bankloan.backend.dtos.response.UserResponseDTO;
import com.bankloan.backend.entities.Customer;
import com.bankloan.backend.entities.User;
import com.bankloan.backend.enums.KycStatus;
import com.bankloan.backend.enums.UserRole;
import com.bankloan.backend.repositories.CustomerRepository;
import com.bankloan.backend.repositories.UserRepository;
import com.bankloan.backend.services.AuthService;
import com.bankloan.backend.utils.JwtUtil;
import jakarta.servlet.http.HttpServletResponse;
import jakarta.transaction.Transactional;
import lombok.RequiredArgsConstructor;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;
import jakarta.servlet.http.Cookie;


@Service
@RequiredArgsConstructor
@Transactional
public class AuthServiceImpl implements AuthService {

    private final UserRepository userRepository;
    private final CustomerRepository customerRepository;
    private final PasswordEncoder passwordEncoder;
    private final JwtUtil jwtUtil;

    @Override
    public AuthResponseDTO register(RegisterRequestDTO request, HttpServletResponse response) {

        if (userRepository.existsByEmail(request.getEmail())) {
            throw new RuntimeException("Email already exists");
        }

        User user = User.builder().name(request.getName()).email(request.getEmail()).password(passwordEncoder.encode(request.getPassword())).role(UserRole.CUSTOMER).build();

        userRepository.save(user);

        Customer customer = Customer.builder().phone(request.getPhone()).address(request.getAddress()).kycStatus(KycStatus.PENDING).user(user).build();

        customerRepository.save(customer);

        String token = jwtUtil.generateToken(user.getEmail());

        Cookie cookie = new Cookie("token", token);

        cookie.setHttpOnly(true);
        cookie.setSecure(false);
        cookie.setPath("/");
        cookie.setMaxAge(24 * 60 * 60);

        response.addCookie(cookie);

        return AuthResponseDTO.builder().id(user.getId()).name(user.getName()).email(user.getEmail()).role(user.getRole().name()).build();
    }

    @Override
    public AuthResponseDTO login(LoginRequestDTO request, HttpServletResponse response) {

        User user = userRepository.findByEmail(request.getEmail()).orElseThrow(() -> new RuntimeException("Invalid credentials"));

        if (!passwordEncoder.matches(request.getPassword(), user.getPassword())) {
            throw new RuntimeException("Invalid credentials");
        }

        String token = jwtUtil.generateToken(user.getEmail());

        Cookie cookie = new Cookie("token", token);

        cookie.setHttpOnly(true);
        cookie.setSecure(false);
        cookie.setPath("/");
        cookie.setMaxAge(24 * 60 * 60);

        response.addCookie(cookie);

        return AuthResponseDTO.builder().id(user.getId()).name(user.getName()).email(user.getEmail()).role(user.getRole().name()).build();
    }

    private void addJwtCookie(HttpServletResponse response, String token) {
        Cookie cookie = new Cookie("token", token);
        cookie.setHttpOnly(true);
        cookie.setSecure(false);
        cookie.setPath("/");
        cookie.setMaxAge(24 * 60 * 60);
        response.addCookie(cookie);
    }

    @Override
    public UserResponseDTO currentUser(String email) {

        User user = userRepository.findByEmail(email)
                .orElseThrow(() -> new RuntimeException("User not found"));

        return UserResponseDTO.builder()
                .id(user.getId())
                .name(user.getName())
                .email(user.getEmail())
                .role(user.getRole().name())
                .build();
    }

    @Override
    public UserResponseDTO updateProfile(
            String email,
            UpdateProfileRequestDTO request
    ) {

        User user = userRepository.findByEmail(email)
                .orElseThrow(() -> new RuntimeException("User not found"));

        user.setName(request.getName());

        Customer customer = user.getCustomer();

        customer.setPhone(request.getPhone());
        customer.setAddress(request.getAddress());

        userRepository.save(user);

        return UserResponseDTO.builder()
                .id(user.getId())
                .name(user.getName())
                .email(user.getEmail())
                .role(user.getRole().name())
                .build();
    }

    @Override
    public void changePassword(
            String email,
            ChangePasswordRequestDTO request
    ) {

        User user = userRepository.findByEmail(email)
                .orElseThrow(() -> new RuntimeException("User not found"));

        if(!passwordEncoder.matches(
                request.getOldPassword(),
                user.getPassword()
        )) {

            throw new RuntimeException("Old password is incorrect");
        }

        user.setPassword(
                passwordEncoder.encode(request.getNewPassword())
        );

        userRepository.save(user);
    }

    @Override
    public void logout(HttpServletResponse response) {

        Cookie cookie = new Cookie("token", null);

        cookie.setHttpOnly(true);
        cookie.setSecure(false);
        cookie.setPath("/");
        cookie.setMaxAge(0);

        response.addCookie(cookie);
    }
}