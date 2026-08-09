package com.schoolmanagement.schoolmanagementwebsite.service.Assessment;

import java.time.LocalDateTime;
import java.util.List;

import org.springframework.stereotype.Service;

import com.schoolmanagement.schoolmanagementwebsite.dto.Assessment.AssessmentCategoryDTO;
import com.schoolmanagement.schoolmanagementwebsite.entity.Assessment.AssessmentCategory;
import com.schoolmanagement.schoolmanagementwebsite.repository.Assessment.AssessmentCategoryRepository;

@Service
public class AssessmentCategoryService {

    private final AssessmentCategoryRepository repository;

    public AssessmentCategoryService(AssessmentCategoryRepository repository) {
        this.repository = repository;
    }

    // Add Category
    public String addCategory(AssessmentCategoryDTO request) {

        // Duplicate Category Name
        if (repository.existsBySchoolIdAndCategoryNameIgnoreCase(
                request.getSchoolId(),
                request.getCategoryName())) {

            throw new RuntimeException("Category name already exists.");
        }

        // Duplicate Short Code
        if (repository.existsBySchoolIdAndShortCodeIgnoreCase(
                request.getSchoolId(),
                request.getShortCode())) {

            throw new RuntimeException("Short code already exists.");
        }

        AssessmentCategory category = AssessmentCategory.builder()
                .schoolId(request.getSchoolId())
                .categoryName(request.getCategoryName())
                .shortCode(request.getShortCode())
                .nature(request.getNature())
                .weightage(request.getWeightage())
                .description(request.getDescription())
                .status(request.isStatus())
                .displayOrder(request.getDisplayOrder())
                .createdAt(LocalDateTime.now())
                .updatedAt(LocalDateTime.now())
                .build();

        repository.save(category);

        return "Assessment Category Added Successfully";
    }

    // Get All Categories
    public List<AssessmentCategory> getAllCategory(Long schoolId) {
        return repository.findBySchoolId(schoolId);
    }

    // Get Category By Id
    public AssessmentCategory getCategory(Long schoolId, Long id) {

        return repository.findByIdAndSchoolId(id, schoolId)
                .orElseThrow(() -> new RuntimeException("Category not found"));
    }

    // Update Category
    public String updateCategory(Long id, AssessmentCategoryDTO request) {

        AssessmentCategory category = repository.findByIdAndSchoolId(id, request.getSchoolId())
                .orElseThrow(() -> new RuntimeException("Category not found"));

        if (repository.existsBySchoolIdAndCategoryNameIgnoreCaseAndIdNot(
                request.getSchoolId(),
                request.getCategoryName(),
                id)) {

            throw new RuntimeException("Category name already exists.");
        }

        if (repository.existsBySchoolIdAndShortCodeIgnoreCaseAndIdNot(
                request.getSchoolId(),
                request.getShortCode(),
                id)) {

            throw new RuntimeException("Short code already exists.");
        }

        category.setCategoryName(request.getCategoryName());
        category.setShortCode(request.getShortCode());
        category.setNature(request.getNature());
        category.setWeightage(request.getWeightage());
        category.setDescription(request.getDescription());
        category.setStatus(request.isStatus());
        category.setDisplayOrder(request.getDisplayOrder());
        category.setUpdatedAt(LocalDateTime.now());

        repository.save(category);

        return "Assessment Category Updated Successfully";
    }

    // Delete Category
    public String deleteCategory(Long schoolId, Long id) {

        AssessmentCategory category = repository.findByIdAndSchoolId(id, schoolId)
                .orElseThrow(() -> new RuntimeException("Category not found"));

        repository.delete(category);

        return "Assessment Category Deleted Successfully";
    }
}