package com.schoolmanagement.schoolmanagementwebsite.controller;

import java.util.List;

import org.springframework.http.ResponseEntity;
import org.springframework.security.core.Authentication;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import com.schoolmanagement.schoolmanagementwebsite.entity.Student;
import com.schoolmanagement.schoolmanagementwebsite.repository.StudentRepository;
import com.schoolmanagement.schoolmanagementwebsite.service.StudentService;

import lombok.RequiredArgsConstructor;

@RestController
@RequestMapping("/api/students")
@RequiredArgsConstructor
@CrossOrigin(origins = "http://localhost:5173")
public class StudentController {

    private final StudentService studentService;
    private final StudentRepository studentRepo;

//     @GetMapping
// public List<Student> getAllStudents() {
//     return studentRepo.findAll();
// }
    @GetMapping
    public List<Student> getAllStudents(Authentication authentication) {

        String email = authentication.getName();

        return studentService.getAllStudents(email);
    }

    @GetMapping("/search")
    public List<Student> searchStudents(
            @RequestParam(required = false) String academicYear,
            @RequestParam(required = false) String studentClass,
            @RequestParam(required = false) String section,
            @RequestParam(required = false) String search,
            Authentication authentication
    ) {
        String email = authentication.getName();

        return studentService.searchStudents(
                email,
                academicYear,
                studentClass,
                section,
                search
        );
    }

    // @GetMapping("/count")
    // public ResponseEntity<Long> getStudentCount(
    //         @RequestParam Long schoolId
    // ) {
    //     return ResponseEntity.ok(
    //             studentService.getTotalStudents(schoolId)
    //     );
    // }
    @GetMapping("/count")
    public long getStudentCount(
            @RequestParam(required = false) Long schoolId
    ) {
        if (schoolId != null) {
            return studentRepo.countBySchool_Id(schoolId);
        }
        return studentRepo.count();
    }

    @GetMapping("/count/active")
    public ResponseEntity<Long> getActiveStudentCount(
            @RequestParam Long schoolId
    ) {
        return ResponseEntity.ok(
                studentService.getActiveStudents(schoolId)
        );
    }
    // // ✅ Total students (ALL)
    // @GetMapping("/counts")
    // public ResponseEntity<Long> getTotalStudents() {
    //     return ResponseEntity.ok(studentService.getTotalStudents());
    // }

    @GetMapping("/{admissionNumber}")
    public ResponseEntity<Student> getStudent(
            @PathVariable String admissionNumber,
            Authentication authentication) {

        String email = authentication.getName();

        return ResponseEntity.ok(
                studentService.getStudentByAdmissionNumber(email, admissionNumber)
        );
    }
}
