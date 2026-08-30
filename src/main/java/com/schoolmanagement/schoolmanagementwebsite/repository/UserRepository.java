package com.schoolmanagement.schoolmanagementwebsite.repository;

import java.util.Optional;

import org.springframework.data.jpa.repository.JpaRepository;

import com.schoolmanagement.schoolmanagementwebsite.entity.User;

public interface UserRepository extends JpaRepository<User, Long> {

    User findByEmail(String email);
    // Optional<User> findByEmail(String email);

    // ✅ Prevent duplicate login accounts
    boolean existsByEmailAndRole(String email, String role);

    Optional<User> findByUsername(String username);

    boolean existsByUsername(String username);

    boolean existsByEmail(String email);

    boolean existsByPhone(String phone);

    void deleteByEmail(String email);
}
