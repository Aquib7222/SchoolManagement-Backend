package com.schoolmanagement.schoolmanagementwebsite.controller.Assessment.SubjectManagement;

import java.util.List;

import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import com.schoolmanagement.schoolmanagementwebsite.dto.Assessment.SubjectManagement.ClassSubjectMappingRequest;
import com.schoolmanagement.schoolmanagementwebsite.dto.Assessment.SubjectManagement.ClassSubjectMappingResponse;
import com.schoolmanagement.schoolmanagementwebsite.entity.Assessment.SubjectManagement.ClassSubjectMapping;
import com.schoolmanagement.schoolmanagementwebsite.service.Assessment.SubjectManagement.ClassSubjectMappingService;

import lombok.RequiredArgsConstructor;

@RestController
@RequestMapping("/api/assessment/class-subject")
@RequiredArgsConstructor
@CrossOrigin(origins = "http://localhost:5173")
public class ClassSubjectMappingController {

    private final ClassSubjectMappingService mappingService;

    // =====================================================
    // MAP SUBJECTS TO CLASS
    // =====================================================

    @PostMapping("/map")
    public ResponseEntity<?> mapSubjects(
            @RequestBody ClassSubjectMappingRequest request) {

        try {

            List<ClassSubjectMapping> response =
                    mappingService.mapSubjects(request);

            return ResponseEntity
                    .status(HttpStatus.CREATED)
                    .body(response);

        } catch (RuntimeException e) {

            return ResponseEntity
                    .badRequest()
                    .body(e.getMessage());
        }
    }

    // =====================================================
    // GET MAPPED SUBJECTS
    // =====================================================

    @GetMapping("/mapped")
    public ResponseEntity<?> getMappedSubjects(
            @RequestParam Long schoolId,
            @RequestParam String academicYear,
            @RequestParam String studentClass) {

        try {

            List<ClassSubjectMappingResponse> response =
                    mappingService.getMappedSubjects(
                            schoolId,
                            academicYear,
                            studentClass
                    );

            return ResponseEntity.ok(response);

        } catch (RuntimeException e) {

            return ResponseEntity
                    .badRequest()
                    .body(e.getMessage());
        }
    }
}