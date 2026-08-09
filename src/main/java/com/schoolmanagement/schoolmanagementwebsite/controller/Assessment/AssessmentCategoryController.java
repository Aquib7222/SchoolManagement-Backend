package com.schoolmanagement.schoolmanagementwebsite.controller.Assessment;

import java.util.List;

import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import com.schoolmanagement.schoolmanagementwebsite.dto.Assessment.AssessmentCategoryDTO;
import com.schoolmanagement.schoolmanagementwebsite.entity.Assessment.AssessmentCategory;
import com.schoolmanagement.schoolmanagementwebsite.service.Assessment.AssessmentCategoryService;

@RestController
@RequestMapping("/api/assessment/category")
@CrossOrigin(origins = "http://localhost:5173")
public class AssessmentCategoryController {

    private final AssessmentCategoryService assessmentCategoryService;

    public AssessmentCategoryController(AssessmentCategoryService assessmentCategoryService) {
        this.assessmentCategoryService = assessmentCategoryService;
    }

    // Add Category
    @PostMapping
    public ResponseEntity<?> addCategory(@RequestBody AssessmentCategoryDTO request) {
        try {
            String message = assessmentCategoryService.addCategory(request);
            return ResponseEntity.status(HttpStatus.CREATED).body(message);
        } catch (RuntimeException e) {
            return ResponseEntity.status(HttpStatus.CONFLICT).body(e.getMessage());
        }
    }

    // Get All Categories
    @GetMapping
    public ResponseEntity<List<AssessmentCategory>> getAllCategory(
            @RequestParam Long schoolId) {

        List<AssessmentCategory> categories =
                assessmentCategoryService.getAllCategory(schoolId);

        return ResponseEntity.ok(categories);
    }

    // Get Category By Id
    @GetMapping("/{id}")
    public ResponseEntity<?> getCategory(
            @PathVariable Long id,
            @RequestParam Long schoolId) {

        try {
            AssessmentCategory category =
                    assessmentCategoryService.getCategory(schoolId, id);

            return ResponseEntity.ok(category);

        } catch (RuntimeException e) {
            return ResponseEntity.status(HttpStatus.NOT_FOUND)
                    .body(e.getMessage());
        }
    }

    // Update Category
    @PutMapping("/{id}")
    public ResponseEntity<?> updateCategory(
            @PathVariable Long id,
            @RequestBody AssessmentCategoryDTO request) {

        try {
            String message =
                    assessmentCategoryService.updateCategory(id, request);

            return ResponseEntity.ok(message);

        } catch (RuntimeException e) {
            return ResponseEntity.status(HttpStatus.CONFLICT)
                    .body(e.getMessage());
        }
    }

    // Delete Category
    @DeleteMapping("/{id}")
    public ResponseEntity<?> deleteCategory(
            @PathVariable Long id,
            @RequestParam Long schoolId) {

        try {
            String message =
                    assessmentCategoryService.deleteCategory(schoolId, id);

            return ResponseEntity.ok(message);

        } catch (RuntimeException e) {
            return ResponseEntity.status(HttpStatus.NOT_FOUND)
                    .body(e.getMessage());
        }
    }
}