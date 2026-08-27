package com.schoolmanagement.schoolmanagementwebsite.controller;

import java.io.IOException;
import java.util.List;

import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.security.core.Authentication;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.PatchMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RequestPart;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.multipart.MultipartFile;

import com.schoolmanagement.schoolmanagementwebsite.entity.Student;
import com.schoolmanagement.schoolmanagementwebsite.enums.Section;
import com.schoolmanagement.schoolmanagementwebsite.repository.StudentRepository;
import com.schoolmanagement.schoolmanagementwebsite.service.StudentService;
import com.schoolmanagement.schoolmanagementwebsite.dto.SectionShufflingDTO;
import com.schoolmanagement.schoolmanagementwebsite.dto.Student.RollNumberUpdateRequest;

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
            @RequestParam(required = false) Section section,
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

    @PutMapping(
            value = "/{admissionNumber}",
            consumes = MediaType.MULTIPART_FORM_DATA_VALUE
    )
    public ResponseEntity<Student> updateStudent(
            @PathVariable String admissionNumber,
            @RequestPart("student") Student request,
            @RequestPart(value = "photo", required = false) MultipartFile photo,
            Authentication authentication
    ) {
        

        String email = authentication.getName();

        Student student = null;
        try {
            student = studentService.updateStudent(
                    email,
                    admissionNumber,
                    request,
                    photo
            );
            
        } catch (IOException ex) {
            System.getLogger(StudentController.class.getName()).log(System.Logger.Level.ERROR, (String) null, ex);
        }

        return ResponseEntity.ok(student);
    }

    @GetMapping("/session-admission")
    public ResponseEntity<Student> getStudentBySessionAndAdmissionNo(
            @RequestParam String academicYear,
            @RequestParam String admissionNumber,
            Authentication authentication
    ) {

        String email = authentication.getName();

        return ResponseEntity.ok(
                studentService.getStudentBySessionAndAdmissionNo(
                        email,
                        academicYear,
                        admissionNumber
                )
        );
    }

    @GetMapping("/count")
    public long getStudentCount(
            @RequestParam(required = false) Long schoolId
    ) {
        if (schoolId != null) {
            return studentRepo.countBySchool_Id(schoolId);
        }
        return studentRepo.count();
    }

    // get active students count 
    @GetMapping("/count/active")
    public ResponseEntity<Long> getActiveStudentCount(
            @RequestParam Long schoolId
    ) {
        return ResponseEntity.ok(
                studentService.getActiveStudents(schoolId)
        );
    }

    // get student by admission Number 
    @GetMapping("/{admissionNumber}")
    public ResponseEntity<Student> getStudent(
            @PathVariable String admissionNumber,
            Authentication authentication) {

        String email = authentication.getName();

        return ResponseEntity.ok(
                studentService.getStudentByAdmissionNumber(email, admissionNumber)
        );
    }

    // get student by many parameter 
    @GetMapping("/all")
    public List<Student> searchStudentDetails(
            @RequestParam String academicYear,
            @RequestParam(required = false) String admissionNumber,
            @RequestParam(required = false) String studentName,
            @RequestParam(required = false) String fatherName,
            @RequestParam(required = false) String motherName,
            @RequestParam(required = false) String mobile,
            @RequestParam(required = false) String studentClass,
            @RequestParam(required = false) String section,
            Authentication authentication
    ) {

        return studentService.searchStudentDetails(
                authentication.getName(),
                academicYear,
                admissionNumber,
                studentName,
                fatherName,
                motherName,
                mobile,
                studentClass,
                section
        );
    }

   @GetMapping("/school")
public ResponseEntity<List<Student>> getStudentsBySchool(
        @RequestParam Long schoolId
) {

    System.out.println("School ID received: " + schoolId);

    List<Student> students = studentRepo.findBySchool_Id(schoolId);

    System.out.println("Students found: " + students.size());

    return ResponseEntity.ok(students);
}


    @PatchMapping("section-shuffling")
    public ResponseEntity<String> sectionShuffling(@RequestBody SectionShufflingDTO request){

        studentService.sectionShuffling(request);
        return ResponseEntity.ok("Students Section Updated Successfully");
    }

    @PutMapping("/roll-numbers")
public ResponseEntity<?> updateRollNumbers(
        @RequestBody RollNumberUpdateRequest request) {

    try {

        studentService.updateRollNumbers(request);

        return ResponseEntity.ok(
                "Roll numbers saved successfully"
        );

    } catch (RuntimeException e) {

        e.printStackTrace();

        return ResponseEntity
                .badRequest()
                .body(e.getMessage());

    } catch (Exception e) {

        e.printStackTrace();

        return ResponseEntity
                .internalServerError()
                .body("Failed to save roll numbers");
    }
}
}
