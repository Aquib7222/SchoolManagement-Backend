// package com.schoolmanagement.schoolmanagementwebsite.repository.Assessment;

// import java.util.List;
// import java.util.Optional;

// import org.springframework.data.jpa.repository.JpaRepository;

// import com.schoolmanagement.schoolmanagementwebsite.entity.Assessment.AssessmentType;

// public interface AssessmentTypeRepository extends JpaRepository<AssessmentType, Long> {

//     // Get all assessment types of a school
//     List<AssessmentType> findBySchoolIdOrderByDisplayOrderAsc(Long schoolId);

//     // Get one assessment type by id and school
//     Optional<AssessmentType> findByIdAndSchoolId(
//             Long id,
//             Long schoolId
//     );

//     // Check duplicate type name
//     boolean existsBySchoolIdAndTypeNameIgnoreCase(
//             Long schoolId,
//             String typeName
//     );

//     // Check duplicate short code
//     boolean existsBySchoolIdAndShortCodeIgnoreCase(
//             Long schoolId,
//             String shortCode
//     );

//     // Filter by category
//     List<AssessmentType> findBySchoolIdAndCategoryIdOrderByDisplayOrderAsc(
//             Long schoolId,
//             Long categoryId
//     );

//     // Filter by exam term
//     List<AssessmentType> findBySchoolIdAndExamTermIdOrderByDisplayOrderAsc(
//             Long schoolId,
//             Long examTermId
//     );
// }

package com.schoolmanagement.schoolmanagementwebsite.repository.Assessment;

import java.util.List;
import java.util.Optional;

import org.springframework.data.jpa.repository.JpaRepository;

import com.schoolmanagement.schoolmanagementwebsite.entity.Assessment.AssessmentType;

public interface AssessmentTypeRepository
        extends JpaRepository<AssessmentType, Long> {

    // =====================================================
    // GET ALL
    // =====================================================

    List<AssessmentType> findBySchoolIdOrderByDisplayOrderAsc(
            Long schoolId
    );

    // =====================================================
    // GET BY ID
    // =====================================================

    Optional<AssessmentType> findByIdAndSchoolId(
            Long id,
            Long schoolId
    );

    // =====================================================
    // DUPLICATE TYPE NAME
    // =====================================================

    boolean existsBySchoolIdAndTypeNameIgnoreCase(
            Long schoolId,
            String typeName
    );

    // =====================================================
    // DUPLICATE SHORT CODE
    // =====================================================

    boolean existsBySchoolIdAndShortCodeIgnoreCase(
            Long schoolId,
            String shortCode
    );

    // =====================================================
    // GET BY CATEGORY
    // =====================================================

    List<AssessmentType>
    findBySchoolIdAndCategory_IdOrderByDisplayOrderAsc(
            Long schoolId,
            Long categoryId
    );

    // =====================================================
    // GET BY EXAM TERM
    // =====================================================

    List<AssessmentType>
    findBySchoolIdAndExamTerm_IdOrderByDisplayOrderAsc(
            Long schoolId,
            Long examTermId
    );
}

