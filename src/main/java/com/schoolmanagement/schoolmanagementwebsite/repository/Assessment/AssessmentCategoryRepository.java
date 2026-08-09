package com.schoolmanagement.schoolmanagementwebsite.repository.Assessment;

import java.util.List;
import java.util.Optional;

import org.springframework.data.jpa.repository.JpaRepository;

import com.schoolmanagement.schoolmanagementwebsite.entity.Assessment.AssessmentCategory;

public interface AssessmentCategoryRepository extends JpaRepository<AssessmentCategory, Long> {

    // Get all categories of a school
    List<AssessmentCategory> findBySchoolId(Long schoolId);

    // Get single category
    Optional<AssessmentCategory> findByIdAndSchoolId(Long id, Long schoolId);

    // Validation
    boolean existsBySchoolIdAndCategoryNameIgnoreCase(
            Long schoolId,
            String categoryName
    );

    boolean existsBySchoolIdAndShortCodeIgnoreCase(
            Long schoolId,
            String shortCode
    );

    // Update ke time duplicate check
    boolean existsBySchoolIdAndCategoryNameIgnoreCaseAndIdNot(
            Long schoolId,
            String categoryName,
            Long id
    );

    boolean existsBySchoolIdAndShortCodeIgnoreCaseAndIdNot(
            Long schoolId,
            String shortCode,
            Long id
    );

    // Filter
    List<AssessmentCategory> findBySchoolIdAndStatus(
            Long schoolId,
            boolean status
    );
}