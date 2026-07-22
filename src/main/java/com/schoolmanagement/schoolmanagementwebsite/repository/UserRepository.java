package com.schoolmanagement.schoolmanagementwebsite.repository;



import org.springframework.data.jpa.repository.JpaRepository;

import com.schoolmanagement.schoolmanagementwebsite.entity.User;

public interface UserRepository extends JpaRepository<User, Long>{
    User findByEmail(String email);
    // Optional<User> findByEmail(String email);

     // ✅ Prevent duplicate login accounts
     boolean existsByEmailAndRole(String email, String role);
}
