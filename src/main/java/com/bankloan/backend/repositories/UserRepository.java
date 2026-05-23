package com.bankloan.backend.repositories;

import com.bankloan.backend.entities.User;
import com.bankloan.backend.enums.UserRole;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.Optional;

public interface UserRepository extends JpaRepository<User, Long> {

    Optional<User> findByEmail(String email);

    boolean existsByEmail(String email);

    Long countByRole(UserRole role);
}