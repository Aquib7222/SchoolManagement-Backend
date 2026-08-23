package com.schoolmanagement.schoolmanagementwebsite.controller.Assessment;

import java.util.List;

import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import com.schoolmanagement.schoolmanagementwebsite.dto.Assessment.GradeRequest;
import com.schoolmanagement.schoolmanagementwebsite.dto.Assessment.GradeResponse;
import com.schoolmanagement.schoolmanagementwebsite.enums.Sessions;
import com.schoolmanagement.schoolmanagementwebsite.service.Assessment.GradeMasterService;

@RestController
@RequestMapping("/api/assessment/grade")
public class GradeMasterController {

    private final GradeMasterService gradeMasterService;

    public GradeMasterController(
            GradeMasterService gradeMasterService) {

        this.gradeMasterService = gradeMasterService;
    }

    // =====================================================
    // GET ALL GRADES
    // =====================================================

    @GetMapping
    public ResponseEntity<?> getAll(
            @RequestParam Long schoolId) {

        try {

            return ResponseEntity.ok(
                    gradeMasterService.getAll(schoolId));

        } catch (Exception e) {

            e.printStackTrace();

            return ResponseEntity
                    .badRequest()
                    .body(e.getMessage());
        }
    }

    // =====================================================
    // GET GRADES BY SESSION
    // =====================================================

    @GetMapping("/session")
    public ResponseEntity<?> getBySession(
            @RequestParam Long schoolId,
            @RequestParam Sessions session) {

        try {

            return ResponseEntity.ok(
                    gradeMasterService.getBySession(
                            schoolId,
                            session));

        } catch (Exception e) {

            e.printStackTrace();

            return ResponseEntity
                    .badRequest()
                    .body(e.getMessage());
        }
    }

    // =====================================================
    // GET ACTIVE GRADES
    // Used later during marks entry
    // =====================================================

    @GetMapping("/active")
    public ResponseEntity<?> getActiveGrades(
            @RequestParam Long schoolId,
            @RequestParam Sessions session) {

        try {

            return ResponseEntity.ok(
                    gradeMasterService.getActiveGrades(
                            schoolId,
                            session));

        } catch (Exception e) {

            e.printStackTrace();

            return ResponseEntity
                    .badRequest()
                    .body(e.getMessage());
        }
    }

    // =====================================================
    // ADD GRADE
    // =====================================================

    @PostMapping
    public ResponseEntity<?> save(
            @RequestBody GradeRequest request) {

        try {

            return ResponseEntity.ok(
                    gradeMasterService.save(request));

        } catch (Exception e) {

            e.printStackTrace();

            return ResponseEntity
                    .badRequest()
                    .body(e.getMessage());
        }
    }

    // =====================================================
    // UPDATE GRADE
    // =====================================================

    @PutMapping("/{id}")
    public ResponseEntity<?> update(
            @PathVariable Long id,
            @RequestBody GradeRequest request) {

        try {

            return ResponseEntity.ok(
                    gradeMasterService.update(
                            id,
                            request));

        } catch (Exception e) {

            e.printStackTrace();

            return ResponseEntity
                    .badRequest()
                    .body(e.getMessage());
        }
    }

    // =====================================================
    // DELETE GRADE
    // =====================================================

    @DeleteMapping("/{id}")
    public ResponseEntity<?> delete(
            @PathVariable Long id,
            @RequestParam Long schoolId) {

        try {

            gradeMasterService.delete(
                    id,
                    schoolId);

            return ResponseEntity.ok(
                    "Grade deleted successfully");

        } catch (Exception e) {

            e.printStackTrace();

            return ResponseEntity
                    .badRequest()
                    .body(e.getMessage());
        }
    }
}