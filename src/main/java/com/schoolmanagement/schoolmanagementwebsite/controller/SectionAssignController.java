package com.schoolmanagement.schoolmanagementwebsite.controller;

import java.util.List;

import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import com.schoolmanagement.schoolmanagementwebsite.dto.AssignSectionRequest;
import com.schoolmanagement.schoolmanagementwebsite.entity.Student;
import com.schoolmanagement.schoolmanagementwebsite.repository.StudentRepository;
import com.schoolmanagement.schoolmanagementwebsite.service.SectionAssignService;

import lombok.RequiredArgsConstructor;

@RestController
@RequestMapping("/api/sections")
@RequiredArgsConstructor
public class SectionAssignController {

    private final SectionAssignService sectionService;
    private final StudentRepository studentRepo;

    @GetMapping("/students")
    public List<Student> getStudents(
            @RequestParam Long schoolId,
            @RequestParam(required = false) String studentClass
    ) {
        return studentClass == null
                ? studentRepo.findBySchool_Id(schoolId)
                : studentRepo.findBySchool_IdAndStudentClass(schoolId, studentClass);
    }

    @PostMapping("/assign")
    public ResponseEntity<?> assignSection(
            @RequestParam Long schoolId,
            @RequestBody AssignSectionRequest request
    ) {
        sectionService.assignSection(schoolId, request);
        return ResponseEntity.ok("Section assigned successfully");
    }
}
