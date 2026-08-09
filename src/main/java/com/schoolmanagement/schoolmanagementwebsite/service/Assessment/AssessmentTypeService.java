// package com.schoolmanagement.schoolmanagementwebsite.service.Assessment;

// import java.time.LocalDateTime;
// import java.util.List;

// import org.springframework.stereotype.Service;
// import org.springframework.transaction.annotation.Transactional;

// import com.schoolmanagement.schoolmanagementwebsite.dto.Assessment.AssessmentTypeDTO;
// import com.schoolmanagement.schoolmanagementwebsite.dto.Assessment.AssessmentTypeResponse;
// import com.schoolmanagement.schoolmanagementwebsite.entity.Assessment.AssessmentCategory;
// import com.schoolmanagement.schoolmanagementwebsite.entity.Assessment.AssessmentType;
// import com.schoolmanagement.schoolmanagementwebsite.entity.Assessment.ExamTerm;
// import com.schoolmanagement.schoolmanagementwebsite.repository.Assessment.AssessmentCategoryRepository;
// import com.schoolmanagement.schoolmanagementwebsite.repository.Assessment.AssessmentTypeRepository;
// import com.schoolmanagement.schoolmanagementwebsite.repository.Assessment.ExamTermRepository;

// import lombok.RequiredArgsConstructor;

// @Service
// @RequiredArgsConstructor
// public class AssessmentTypeService {

//     private final AssessmentTypeRepository assessmentTypeRepository;

//     private final AssessmentCategoryRepository categoryRepository;

//     private final ExamTermRepository examTermRepository;


//     // =====================================================
//     // CREATE
//     // =====================================================

//   @Transactional
// public AssessmentTypeResponse save(AssessmentTypeDTO dto) {

//     if (dto.getSchoolId() == null) {
//         throw new RuntimeException("School ID is required.");
//     }

//     if (dto.getTypeName() == null || dto.getTypeName().isBlank()) {
//         throw new RuntimeException("Assessment type name is required.");
//     }

//     if (dto.getShortCode() == null || dto.getShortCode().isBlank()) {
//         throw new RuntimeException("Short code is required.");
//     }

//     // Duplicate name
//     if (assessmentTypeRepository.existsBySchoolIdAndTypeNameIgnoreCase(
//             dto.getSchoolId(),
//             dto.getTypeName())) {

//         throw new RuntimeException(
//                 "Assessment type name already exists."
//         );
//     }

//     // Duplicate short code
//     if (assessmentTypeRepository.existsBySchoolIdAndShortCodeIgnoreCase(
//             dto.getSchoolId(),
//             dto.getShortCode())) {

//         throw new RuntimeException(
//                 "Assessment type short code already exists."
//         );
//     }

//     AssessmentCategory category =
//             categoryRepository.findById(dto.getCategoryId())
//                     .orElseThrow(() ->
//                             new RuntimeException(
//                                     "Assessment category not found."
//                             )
//                     );

//     ExamTerm examTerm =
//             examTermRepository.findById(dto.getExamTermId())
//                     .orElseThrow(() ->
//                             new RuntimeException(
//                                     "Exam term not found."
//                             )
//                     );

//     AssessmentType entity = AssessmentType.builder()
//             .schoolId(dto.getSchoolId())
//             .typeName(dto.getTypeName())
//             .shortCode(dto.getShortCode())
//             .category(category)
//             .examTerm(examTerm)
//             .nature(dto.getNature())
//             .maxMarks(dto.getMaxMarks())
//             .passingMarks(dto.getPassingMarks())
//             .weightage(dto.getWeightage())
//             .description(dto.getDescription())
//             .displayOrder(dto.getDisplayOrder())
//             .status(dto.isStatus())
//             .createdAt(LocalDateTime.now())
//             .updatedAt(LocalDateTime.now())
//             .build();

//     AssessmentType saved =
//             assessmentTypeRepository.save(entity);

//     return convertToResponse(saved);
// }

//     // =====================================================
//     // GET ALL
//     // =====================================================

//     public List<AssessmentTypeResponse> getAll(Long schoolId) {

//         return assessmentTypeRepository
//                 .findBySchoolIdOrderByDisplayOrderAsc(schoolId)
//                 .stream()
//                 .map(this::convertToResponse)
//                 .toList();
//     }


//     // =====================================================
//     // GET BY ID
//     // =====================================================

//     public AssessmentTypeResponse getById(
//             Long id,
//             Long schoolId
//     ) {

//         AssessmentType assessmentType =
//                 assessmentTypeRepository
//                         .findByIdAndSchoolId(id, schoolId)
//                         .orElseThrow(() ->
//                                 new RuntimeException(
//                                         "Assessment type not found."
//                                 )
//                         );

//         return convertToResponse(assessmentType);
//     }


//     // =====================================================
//     // GET BY CATEGORY
//     // =====================================================

//     public List<AssessmentTypeResponse> getByCategory(
//             Long schoolId,
//             Long categoryId
//     ) {

//         return assessmentTypeRepository
//                 .findBySchoolIdAndCategoryIdOrderByDisplayOrderAsc(
//                         schoolId,
//                         categoryId
//                 )
//                 .stream()
//                 .map(this::convertToResponse)
//                 .toList();
//     }


//     // =====================================================
//     // GET BY EXAM TERM
//     // =====================================================

//     public List<AssessmentTypeResponse> getByExamTerm(
//             Long schoolId,
//             Long examTermId
//     ) {

//         return assessmentTypeRepository
//                 .findBySchoolIdAndExamTermIdOrderByDisplayOrderAsc(
//                         schoolId,
//                         examTermId
//                 )
//                 .stream()
//                 .map(this::convertToResponse)
//                 .toList();
//     }


//     // =====================================================
//     // UPDATE
//     // =====================================================

//     @Transactional
//     public AssessmentTypeResponse update(
//             Long id,
//             AssessmentType request
//     ) {

//         AssessmentType existing =
//                 assessmentTypeRepository
//                         .findByIdAndSchoolId(
//                                 id,
//                                 request.getSchoolId()
//                         )
//                         .orElseThrow(() ->
//                                 new RuntimeException(
//                                         "Assessment type not found."
//                                 )
//                         );


//         // Check type name only if changed
//         if (!existing.getTypeName()
//                 .equalsIgnoreCase(request.getTypeName())) {

//             if (assessmentTypeRepository
//                     .existsBySchoolIdAndTypeNameIgnoreCase(
//                             request.getSchoolId(),
//                             request.getTypeName())) {

//                 throw new RuntimeException(
//                         "Assessment type name already exists."
//                 );
//             }
//         }


//         // Check short code only if changed
//         if (!existing.getShortCode()
//                 .equalsIgnoreCase(request.getShortCode())) {

//             if (assessmentTypeRepository
//                     .existsBySchoolIdAndShortCodeIgnoreCase(
//                             request.getSchoolId(),
//                             request.getShortCode())) {

//                 throw new RuntimeException(
//                         "Assessment type short code already exists."
//                 );
//             }
//         }


//         existing.setTypeName(request.getTypeName());
//         existing.setShortCode(request.getShortCode());

//         existing.setCategoryId(request.getCategoryId());

//         existing.setNature(request.getNature());

//         existing.setExamTermId(request.getExamTermId());

//         existing.setMaxMarks(request.getMaxMarks());

//         existing.setPassingMarks(request.getPassingMarks());

//         existing.setDisplayOrder(request.getDisplayOrder());

//         existing.setWeightage(request.getWeightage());

//         existing.setDescription(request.getDescription());

//         existing.setStatus(request.isStatus());

//         existing.setUpdatedAt(LocalDateTime.now());


//         AssessmentType updated =
//                 assessmentTypeRepository.save(existing);

//         return convertToResponse(updated);
//     }


//     // =====================================================
//     // DELETE
//     // =====================================================

//     @Transactional
//     public void delete(
//             Long id,
//             Long schoolId
//     ) {

//         AssessmentType assessmentType =
//                 assessmentTypeRepository
//                         .findByIdAndSchoolId(id, schoolId)
//                         .orElseThrow(() ->
//                                 new RuntimeException(
//                                         "Assessment type not found."
//                                 )
//                         );

//         assessmentTypeRepository.delete(assessmentType);
//     }


//     // =====================================================
//     // CONVERT ENTITY -> RESPONSE
//     // =====================================================

//     private AssessmentTypeResponse convertToResponse(
//             AssessmentType entity
//     ) {

//         String categoryName = null;

//         String examTermName = null;


//         // Category name
//         if (entity.getCategoryId() != null) {

//             categoryName =
//                     categoryRepository
//                             .findById(entity.getCategoryId())
//                             .map(AssessmentCategory::getCategoryName)
//                             .orElse(null);
//         }


//         // Exam Term name
//         if (entity.getExamTermId() != null) {

//             examTermName =
//                     examTermRepository
//                             .findById(entity.getExamTermId())
//                             .map(ExamTerm::getExamTerm)
//                             .orElse(null);
//         }


//         String natureName = null;
//         String natureDisplayName = null;

//         if (entity.getNature() != null) {

//             natureName = entity.getNature().name();

//             natureDisplayName =
//                     entity.getNature().getDisplayName();
//         }


//         return new AssessmentTypeResponse(

//                 entity.getId(),

//                 entity.getSchoolId(),

//                 entity.getTypeName(),

//                 entity.getShortCode(),

//                 entity.getCategoryId(),

//                 categoryName,

//                 natureName,

//                 natureDisplayName,

//                 entity.getExamTermId(),

//                 examTermName,

//                 entity.getMaxMarks(),

//                 entity.getPassingMarks(),

//                 entity.getDisplayOrder(),

//                 entity.getWeightage(),

//                 entity.getDescription(),

//                 entity.isStatus(),

//                 entity.getCreatedAt(),

//                 entity.getUpdatedAt()
//         );
//     }
// }

package com.schoolmanagement.schoolmanagementwebsite.service.Assessment;

import java.time.LocalDateTime;
import java.util.List;

import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import com.schoolmanagement.schoolmanagementwebsite.dto.Assessment.AssessmentTypeDTO;
import com.schoolmanagement.schoolmanagementwebsite.dto.Assessment.AssessmentTypeResponse;
import com.schoolmanagement.schoolmanagementwebsite.entity.Assessment.AssessmentCategory;
import com.schoolmanagement.schoolmanagementwebsite.entity.Assessment.AssessmentType;
import com.schoolmanagement.schoolmanagementwebsite.entity.Assessment.ExamTerm;
import com.schoolmanagement.schoolmanagementwebsite.repository.Assessment.AssessmentCategoryRepository;
import com.schoolmanagement.schoolmanagementwebsite.repository.Assessment.AssessmentTypeRepository;
import com.schoolmanagement.schoolmanagementwebsite.repository.Assessment.ExamTermRepository;

import lombok.RequiredArgsConstructor;

@Service
@RequiredArgsConstructor
public class AssessmentTypeService {

    private final AssessmentTypeRepository assessmentTypeRepository;

    private final AssessmentCategoryRepository categoryRepository;

    private final ExamTermRepository examTermRepository;


    // =====================================================
    // CREATE
    // =====================================================

    @Transactional
    public AssessmentTypeResponse save(AssessmentTypeDTO dto) {

        // -------------------------------------------------
        // Basic validation
        // -------------------------------------------------

        if (dto.getSchoolId() == null) {
            throw new RuntimeException("School ID is required.");
        }

        if (dto.getTypeName() == null ||
                dto.getTypeName().trim().isEmpty()) {

            throw new RuntimeException(
                    "Assessment type name is required."
            );
        }

        if (dto.getShortCode() == null ||
                dto.getShortCode().trim().isEmpty()) {

            throw new RuntimeException(
                    "Assessment type short code is required."
            );
        }

        if (dto.getCategoryId() == null) {

            throw new RuntimeException(
                    "Assessment category is required."
            );
        }

        if (dto.getExamTermId() == null) {

            throw new RuntimeException(
                    "Exam term is required."
            );
        }

        if (dto.getNature() == null) {

            throw new RuntimeException(
                    "Assessment nature is required."
            );
        }

        if (dto.getMaxMarks() == null ||
                dto.getMaxMarks() <= 0) {

            throw new RuntimeException(
                    "Maximum marks must be greater than 0."
            );
        }

        // -------------------------------------------------
        // Duplicate type name
        // -------------------------------------------------

        if (assessmentTypeRepository
                .existsBySchoolIdAndTypeNameIgnoreCase(
                        dto.getSchoolId(),
                        dto.getTypeName().trim()
                )) {

            throw new RuntimeException(
                    "Assessment type name already exists."
            );
        }

        // -------------------------------------------------
        // Duplicate short code
        // -------------------------------------------------

        if (assessmentTypeRepository
                .existsBySchoolIdAndShortCodeIgnoreCase(
                        dto.getSchoolId(),
                        dto.getShortCode().trim()
                )) {

            throw new RuntimeException(
                    "Assessment type short code already exists."
            );
        }

        // -------------------------------------------------
        // Find Category
        // -------------------------------------------------

        AssessmentCategory category =
                categoryRepository.findById(dto.getCategoryId())
                        .orElseThrow(() ->
                                new RuntimeException(
                                        "Assessment category not found."
                                )
                        );

        // -------------------------------------------------
        // Find Exam Term
        // -------------------------------------------------

        ExamTerm examTerm =
                examTermRepository.findById(dto.getExamTermId())
                        .orElseThrow(() ->
                                new RuntimeException(
                                        "Exam term not found."
                                )
                        );

        // -------------------------------------------------
        // Create Entity
        // -------------------------------------------------

        LocalDateTime now = LocalDateTime.now();

        AssessmentType entity =
                AssessmentType.builder()
                        .schoolId(dto.getSchoolId())
                        .typeName(dto.getTypeName().trim())
                        .shortCode(dto.getShortCode().trim().toUpperCase())
                        .category(category)
                        .examTerm(examTerm)
                        .nature(dto.getNature())
                        .maxMarks(dto.getMaxMarks())
                        .passingMarks(dto.getPassingMarks())
                        .weightage(dto.getWeightage())
                        .description(dto.getDescription())
                        .displayOrder(dto.getDisplayOrder())
                        .status(dto.isStatus())
                        .createdAt(now)
                        .updatedAt(now)
                        .build();

        // -------------------------------------------------
        // Save
        // -------------------------------------------------

        AssessmentType saved =
                assessmentTypeRepository.save(entity);

        return convertToResponse(saved);
    }


    // =====================================================
    // GET ALL
    // =====================================================

    public List<AssessmentTypeResponse> getAll(
            Long schoolId) {

        return assessmentTypeRepository
                .findBySchoolIdOrderByDisplayOrderAsc(schoolId)
                .stream()
                .map(this::convertToResponse)
                .toList();
    }


    // =====================================================
    // GET BY ID
    // =====================================================

    public AssessmentTypeResponse getById(
            Long id,
            Long schoolId) {

        AssessmentType entity =
                assessmentTypeRepository
                        .findByIdAndSchoolId(id, schoolId)
                        .orElseThrow(() ->
                                new RuntimeException(
                                        "Assessment type not found."
                                )
                        );

        return convertToResponse(entity);
    }


    // =====================================================
    // GET BY CATEGORY
    // =====================================================

    public List<AssessmentTypeResponse> getByCategory(
            Long schoolId,
            Long categoryId) {

        return assessmentTypeRepository
                .findBySchoolIdAndCategory_IdOrderByDisplayOrderAsc(
                        schoolId,
                        categoryId
                )
                .stream()
                .map(this::convertToResponse)
                .toList();
    }


    // =====================================================
    // GET BY EXAM TERM
    // =====================================================

    public List<AssessmentTypeResponse> getByExamTerm(
            Long schoolId,
            Long examTermId) {

        return assessmentTypeRepository
                .findBySchoolIdAndExamTerm_IdOrderByDisplayOrderAsc(
                        schoolId,
                        examTermId
                )
                .stream()
                .map(this::convertToResponse)
                .toList();
    }


    // =====================================================
    // UPDATE
    // =====================================================

    @Transactional
    public AssessmentTypeResponse update(
            Long id,
            AssessmentTypeDTO dto) {

        if (dto.getSchoolId() == null) {

            throw new RuntimeException(
                    "School ID is required."
            );
        }

        // -------------------------------------------------
        // Find existing
        // -------------------------------------------------

        AssessmentType existing =
                assessmentTypeRepository
                        .findByIdAndSchoolId(
                                id,
                                dto.getSchoolId()
                        )
                        .orElseThrow(() ->
                                new RuntimeException(
                                        "Assessment type not found."
                                )
                        );

        // -------------------------------------------------
        // Duplicate Type Name
        // -------------------------------------------------

        if (!existing.getTypeName()
                .equalsIgnoreCase(dto.getTypeName())) {

            if (assessmentTypeRepository
                    .existsBySchoolIdAndTypeNameIgnoreCase(
                            dto.getSchoolId(),
                            dto.getTypeName()
                    )) {

                throw new RuntimeException(
                        "Assessment type name already exists."
                );
            }
        }

        // -------------------------------------------------
        // Duplicate Short Code
        // -------------------------------------------------

        if (!existing.getShortCode()
                .equalsIgnoreCase(dto.getShortCode())) {

            if (assessmentTypeRepository
                    .existsBySchoolIdAndShortCodeIgnoreCase(
                            dto.getSchoolId(),
                            dto.getShortCode()
                    )) {

                throw new RuntimeException(
                        "Assessment type short code already exists."
                );
            }
        }

        // -------------------------------------------------
        // Find Category
        // -------------------------------------------------

        AssessmentCategory category =
                categoryRepository.findById(dto.getCategoryId())
                        .orElseThrow(() ->
                                new RuntimeException(
                                        "Assessment category not found."
                                )
                        );

        // -------------------------------------------------
        // Find Exam Term
        // -------------------------------------------------

        ExamTerm examTerm =
                examTermRepository.findById(dto.getExamTermId())
                        .orElseThrow(() ->
                                new RuntimeException(
                                        "Exam term not found."
                                )
                        );

        // -------------------------------------------------
        // Update fields
        // -------------------------------------------------

        existing.setTypeName(
                dto.getTypeName().trim()
        );

        existing.setShortCode(
                dto.getShortCode().trim().toUpperCase()
        );

        existing.setCategory(category);

        existing.setExamTerm(examTerm);

        existing.setNature(dto.getNature());

        existing.setMaxMarks(dto.getMaxMarks());

        existing.setPassingMarks(dto.getPassingMarks());

        existing.setWeightage(dto.getWeightage());

        existing.setDescription(dto.getDescription());

        existing.setDisplayOrder(dto.getDisplayOrder());

        existing.setStatus(dto.isStatus());

        existing.setUpdatedAt(
                LocalDateTime.now()
        );

        // -------------------------------------------------
        // Save
        // -------------------------------------------------

        AssessmentType updated =
                assessmentTypeRepository.save(existing);

        return convertToResponse(updated);
    }


    // =====================================================
    // DELETE
    // =====================================================

    @Transactional
    public void delete(
            Long id,
            Long schoolId) {

        AssessmentType entity =
                assessmentTypeRepository
                        .findByIdAndSchoolId(id, schoolId)
                        .orElseThrow(() ->
                                new RuntimeException(
                                        "Assessment type not found."
                                )
                        );

        assessmentTypeRepository.delete(entity);
    }


    // =====================================================
    // ENTITY -> RESPONSE
    // =====================================================

    private AssessmentTypeResponse convertToResponse(
            AssessmentType entity) {

        Long categoryId = null;
        String categoryName = null;

        if (entity.getCategory() != null) {

            categoryId =
                    entity.getCategory().getId();

            categoryName =
                    entity.getCategory().getCategoryName();
        }


        Long examTermId = null;
        String examTermName = null;

        if (entity.getExamTerm() != null) {

            examTermId =
                    entity.getExamTerm().getId();

            examTermName =
                    entity.getExamTerm().getExamTerm();
        }


        return new AssessmentTypeResponse(

                entity.getId(),

                entity.getSchoolId(),

                entity.getTypeName(),

                entity.getShortCode(),

                categoryId,

                categoryName,

                entity.getNature(),

                entity.getNature() != null
                        ? entity.getNature().getDisplayName()
                        : null,

                examTermId,

                examTermName,

                entity.getMaxMarks(),

                entity.getPassingMarks(),

                entity.getDisplayOrder(),

                entity.getWeightage(),

                entity.getDescription(),

                entity.isStatus(),

                entity.getCreatedAt(),

                entity.getUpdatedAt()
        );
    }
}