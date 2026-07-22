package com.schoolmanagement.schoolmanagementwebsite.controller;

import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import com.schoolmanagement.schoolmanagementwebsite.dto.ApiResponse;
import com.schoolmanagement.schoolmanagementwebsite.dto.CreateStudentRequest;
import com.schoolmanagement.schoolmanagementwebsite.service.StudentCreationService;

import lombok.RequiredArgsConstructor;

@RestController
@RequestMapping("/api/students")
@RequiredArgsConstructor
@CrossOrigin(origins = "http://localhost:3000")
public class StudentCreationController {

    private final StudentCreationService studentCreationService;

    /**
     * Create Student + Login Account after Admission Fee Payment
     */
    @PostMapping("/create")
    public ResponseEntity<?> createStudent(@RequestBody CreateStudentRequest request) {

        try {
            studentCreationService.createStudentAndAccount(request);

            return ResponseEntity.ok(
                    new ApiResponse(true, "Student created & account generated successfully")
            );

        } catch (RuntimeException ex) {
            return ResponseEntity
                    .status(HttpStatus.BAD_REQUEST)
                    .body(new ApiResponse(false, ex.getMessage()));
        }
    }
}
