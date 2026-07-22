package com.schoolmanagement.schoolmanagementwebsite.service;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.schoolmanagement.schoolmanagementwebsite.entity.School;
import com.schoolmanagement.schoolmanagementwebsite.entity.User;
import com.schoolmanagement.schoolmanagementwebsite.repository.SchoolRepository;
import com.schoolmanagement.schoolmanagementwebsite.repository.UserRepository;

@Service
public class UserService {

    @Autowired
    private UserRepository userRepo;

    @Autowired
    private SchoolRepository schoolRepo;

    public User createSuperAdmin(User user, Long schoolId) {
        School school = schoolRepo.findById(schoolId)
                .orElseThrow(() -> new RuntimeException("School not found"));
        
        user.setSchool(school);
        user.setRole("Superadmin");
        user.setStatus("Active");
        return userRepo.save(user);
    }

    public List<User> getSuperAdmins() {
        return userRepo.findAll();
    }

    public void deleteSuperAdmin(Long id) {
        userRepo.deleteById(id);
    }

    public User toggleStatus(Long id) {
        User u = userRepo.findById(id).orElseThrow();
        u.setStatus(u.getStatus().equals("Active") ? "Inactive" : "Active");
        return userRepo.save(u);
    }
}
