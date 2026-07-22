package com.schoolmanagement.schoolmanagementwebsite.service;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;

import com.schoolmanagement.schoolmanagementwebsite.entity.School;
import com.schoolmanagement.schoolmanagementwebsite.entity.SuperAdmin;
import com.schoolmanagement.schoolmanagementwebsite.entity.User;
import com.schoolmanagement.schoolmanagementwebsite.repository.SchoolRepository;
import com.schoolmanagement.schoolmanagementwebsite.repository.SuperAdminRepository;
import com.schoolmanagement.schoolmanagementwebsite.repository.UserRepository;

@Service
public class SuperAdminService {

    @Autowired
    private UserRepository userRepository;

    @Autowired
    private SuperAdminRepository superAdminRepository;

    @Autowired
    private SchoolRepository schoolRepository;

    @Autowired
    private PasswordEncoder passwordEncoder;

    public SuperAdmin createSuperAdmin(
            String name,
            String email,
            String password,
            String phone,
            String role,
            Long schoolId
    ) {

        // 2️⃣ Create SuperAdmin profile
        School school = schoolRepository.findById(schoolId)
                .orElseThrow(() -> new RuntimeException("School not found"));

        // 1️⃣ Create login user (BCrypt)
        User user = new User();
        user.setEmail(email);
        user.setPassword(passwordEncoder.encode(password)); // 🔐 BCrypt
        user.setName(name);
        user.setPhone(phone);
        user.setRole(role);
        user.setStatus("Active");
        user.setSchool(school);

        userRepository.save(user);

        SuperAdmin admin = new SuperAdmin();
        admin.setFullName(name);
        admin.setEmail(email);
        admin.setPhone(phone);
        admin.setRole(role);
        admin.setSchool(school);

        return superAdminRepository.save(admin);
    }

    public List<SuperAdmin> getAll() {
        return superAdminRepository.findAll();
    }

    public void delete(Long id) {
        superAdminRepository.deleteById(id);
    }

    public SuperAdmin toggleStatus(Long id) {
        SuperAdmin admin = superAdminRepository.findById(id)
                .orElseThrow();

        admin.setStatus(
                admin.getStatus().equals("Active") ? "Inactive" : "Active"
        );

        return superAdminRepository.save(admin);
    }
}
