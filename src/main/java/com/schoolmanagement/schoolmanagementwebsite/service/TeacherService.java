package com.schoolmanagement.schoolmanagementwebsite.service;

import java.util.List;

import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;

import com.schoolmanagement.schoolmanagementwebsite.entity.School;
import com.schoolmanagement.schoolmanagementwebsite.entity.Teacher;
import com.schoolmanagement.schoolmanagementwebsite.entity.User;
import com.schoolmanagement.schoolmanagementwebsite.repository.TeacherRepository;
import com.schoolmanagement.schoolmanagementwebsite.repository.UserRepository;

import lombok.RequiredArgsConstructor;

@Service
@RequiredArgsConstructor
public class TeacherService {

    private final TeacherRepository teacherRepo;
    private final UserRepository userRepo;
    private final PasswordEncoder passwordEncoder;

    // ================= ADD =================
    public Teacher addTeacher(Teacher teacher, School school) {

        // 🔢 Generate Employee ID
        long count = teacherRepo.count() + 1000;
        String employeeId = "EMP" + count;

        // 👤 Create User
        User user = new User();
        user.setName(teacher.getFirstName() + " " + teacher.getLastName());
        user.setEmail(teacher.getEmail());
        user.setPhone(teacher.getPhoneNumber());
        user.setRole("TEACHER");

        // 🔐 password = phone number
        // 🔑 VERY IMPORTANT
        user.setSchool(school);
        user.setPassword(passwordEncoder.encode(teacher.getPhoneNumber()));
        user.setStatus("Active");

        userRepo.save(user);

        // 👨‍🏫 Teacher
        teacher.setEmployeeId(employeeId);
        teacher.setSchool(school);
        teacher.setUser(user);
        teacher.setActive(true);

        return teacherRepo.save(teacher);
    }

    // ================= LIST =================
    public List<Teacher> getAllTeachers(Long schoolId) {
        return teacherRepo.findAll();
    }

    public List<Teacher> getTeachersByStatus(Long schoolId, String status) {
        return teacherRepo.findBySchoolIdAndStatus(schoolId, status);
    }

    // ================= UPDATE =================
        public Teacher updateTeacher(Long id, Teacher updated) {
    
        Teacher existing = teacherRepo.findById(id);

        existing.setFirstName(updated.getFirstName());
        existing.setLastName(updated.getLastName());
        existing.setDepartment(updated.getDepartment());
        existing.setDesignation(updated.getDesignation());
        existing.setStatus(updated.getStatus());
        existing.setMobileNumber(updated.getMobileNumber());

        return teacherRepo.save(existing);
    }

    // ================= DELETE =================
    public void deleteTeacher(Long id) {

        Teacher teacher = teacherRepo.findById(id)
                .orElseThrow(() -> new RuntimeException("Teacher not found"));

        if (teacher.getUser() != null) {
            userRepo.delete(teacher.getUser());
        }

        teacherRepo.delete(teacher);
    }

    // ================= FETCH BY EMPLOYEE ID =================
    public Teacher getTeacherByEmployeeId(String employeeId, Long schoolId) {

        return teacherRepo
                .findByEmployeeIdAndSchoolId(employeeId, schoolId)
                .orElseThrow(() -> new RuntimeException("Teacher not found with EmployeeId: " + employeeId));
    }

    // ================= ACTIVE / INACTIVE =================
    public void toggleStatus(Long id, boolean active) {

        Teacher teacher = teacherRepo.findById(id)
                .orElseThrow(() -> new RuntimeException("Teacher not found"));

        teacher.setActive(active);

        if (teacher.getUser() != null) {
            teacher.getUser().setStatus(active ? "Active" : "Inactive");
            userRepo.save(teacher.getUser());
        }

        teacherRepo.save(teacher);
    }
}
