package com.schoolmanagement.schoolmanagementwebsite.service;

import java.util.List;

import org.springframework.stereotype.Service;

import com.schoolmanagement.schoolmanagementwebsite.entity.School;
import com.schoolmanagement.schoolmanagementwebsite.entity.Student;
import com.schoolmanagement.schoolmanagementwebsite.entity.User;
import com.schoolmanagement.schoolmanagementwebsite.enums.StudentStatus;
import com.schoolmanagement.schoolmanagementwebsite.repository.StudentRepository;
import com.schoolmanagement.schoolmanagementwebsite.repository.UserRepository;
import com.schoolmanagement.schoolmanagementwebsite.repository.SchoolRepository;

import lombok.RequiredArgsConstructor;

@Service
@RequiredArgsConstructor
public class StudentService {

    private final StudentRepository studentRepository;
    private final UserRepository userRepository;
    private final SchoolRepository schoolRepository;

    public List<Student> searchStudents(
            String email,
            String academicYear,
            String studentClass,
            String section,
            String search
    ) {

        User user = userRepository.findByEmail(email);
        if (user == null || user.getSchool() == null) {
            throw new RuntimeException("User or School not found");
        }

        Long schoolId = user.getSchool().getId();

        return studentRepository.searchStudents(
                schoolId,
                academicYear,
                studentClass,
                section,
                search
        );

    }

    public long getTotalStudents(Long schoolId) {
        return studentRepository.countBySchool_Id(schoolId);
    }

    public long getActiveStudents(Long schoolId) {
        return studentRepository.countBySchool_IdAndStatus(
                schoolId, StudentStatus.ACTIVE
        );
    }

    // ✅ Total students in DB
    public long getTotalStudents() {
        return studentRepository.count();
    }

    public List<Student> getAllStudents(String email) {

        User user = userRepository.findByEmail(email);

        if (user == null || user.getSchool() == null) {
            throw new RuntimeException("User or School not found");
        }

        Long schoolId = user.getSchool().getId();

        return studentRepository.findBySchool_Id(schoolId);
    }

    public Student getStudentByAdmissionNumber(String email, String admissionNumber) {

        User user = userRepository.findByEmail(email);
        System.out.println("Logged in email = " + email);

        if (user == null || user.getSchool() == null) {
            throw new RuntimeException("User or School not found");
        }

        Long schoolId = user.getSchool().getId();

        return studentRepository
                .findBySchool_IdAndAdmissionNumber(schoolId, admissionNumber)
                .orElseThrow(() -> new RuntimeException("Student Not Found"));
    }

    public Student getStudentBySessionAndAdmissionNo(
        String email,
        String academicYear,
        String admissionNumber) {

    System.out.println("Logged in email = " + email);

    User user = userRepository.findByEmail(email);
        System.out.println("Logged in email = " + email);

        if (user == null) {
            throw new RuntimeException("User or School not found");
        }

    Long schoolId = user.getSchool().getId();

    return studentRepository
            .findBySchool_IdAndAcademicYearAndAdmissionNumber(
                    schoolId,
                    academicYear,
                    admissionNumber
            )
            .orElseThrow(() -> new RuntimeException("Student not found"));
}
}
