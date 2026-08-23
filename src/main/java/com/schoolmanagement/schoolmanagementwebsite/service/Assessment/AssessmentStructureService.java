package com.schoolmanagement.schoolmanagementwebsite.service.Assessment;


import java.util.List;

import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import com.schoolmanagement.schoolmanagementwebsite.dto.Assessment.AssessmentStructureRequest;
import com.schoolmanagement.schoolmanagementwebsite.dto.Assessment.AssessmentStructureResponse;
import com.schoolmanagement.schoolmanagementwebsite.dto.Assessment.AssessmentStructureTypeRequest;
import com.schoolmanagement.schoolmanagementwebsite.dto.Assessment.AssessmentStructureTypeResponse;
import com.schoolmanagement.schoolmanagementwebsite.entity.School;
import com.schoolmanagement.schoolmanagementwebsite.entity.Assessment.AssessmentStructure;
import com.schoolmanagement.schoolmanagementwebsite.entity.Assessment.AssessmentStructureType;
import com.schoolmanagement.schoolmanagementwebsite.entity.Assessment.AssessmentType;
import com.schoolmanagement.schoolmanagementwebsite.entity.Assessment.SubjectManagement.SubjectMaster;
import com.schoolmanagement.schoolmanagementwebsite.enums.Sessions;
import com.schoolmanagement.schoolmanagementwebsite.enums.Standard;
import com.schoolmanagement.schoolmanagementwebsite.enums.Assessment.ExamTermType;
import com.schoolmanagement.schoolmanagementwebsite.repository.Assessment.AssessmentStructureRepository;
import com.schoolmanagement.schoolmanagementwebsite.repository.Assessment.AssessmentStructureTypeRepository;
import com.schoolmanagement.schoolmanagementwebsite.repository.Assessment.AssessmentTypeRepository;
import com.schoolmanagement.schoolmanagementwebsite.repository.Assessment.SubjectManagement.SubjectMasterRepository;
import com.schoolmanagement.schoolmanagementwebsite.repository.SchoolRepository;

@Service
@Transactional
public class AssessmentStructureService {

    private final AssessmentStructureRepository assessmentStructureRepository;
    private final AssessmentStructureTypeRepository assessmentStructureTypeRepository;

    private final SchoolRepository schoolRepository;
    private final SubjectMasterRepository subjectMasterRepository;
    private final AssessmentTypeRepository assessmentTypeRepository;

    public AssessmentStructureService(
            AssessmentStructureRepository assessmentStructureRepository,
            AssessmentStructureTypeRepository assessmentStructureTypeRepository,
            SchoolRepository schoolRepository,
            SubjectMasterRepository subjectMasterRepository,
            AssessmentTypeRepository assessmentTypeRepository) {

        this.assessmentStructureRepository =
                assessmentStructureRepository;

        this.assessmentStructureTypeRepository =
                assessmentStructureTypeRepository;

        this.schoolRepository =
                schoolRepository;

        this.subjectMasterRepository =
                subjectMasterRepository;

        this.assessmentTypeRepository =
                assessmentTypeRepository;
    }

    // =========================================================
    // SAVE ASSESSMENT STRUCTURE
    // =========================================================

    public void saveStructure(
            AssessmentStructureRequest request) {

        // -----------------------------------------------------
        // 1. Validate School
        // -----------------------------------------------------

        School school = schoolRepository
                .findById(request.getSchoolId())
                .orElseThrow(() ->
                        new RuntimeException(
                                "School not found"));

        // -----------------------------------------------------
        // 2. Validate Subject
        // -----------------------------------------------------

        SubjectMaster subject = subjectMasterRepository
                .findById(request.getSubjectId())
                .orElseThrow(() ->
                        new RuntimeException(
                                "Subject not found"));

        // -----------------------------------------------------
        // 3. Validate Assessment Components
        // -----------------------------------------------------

        if (request.getAssessmentTypes() == null
                || request.getAssessmentTypes().isEmpty()) {

            throw new RuntimeException(
                    "Please add at least one assessment component");
        }

        // -----------------------------------------------------
        // 4. Check Total Weightage
        // -----------------------------------------------------

        double totalWeightage =
                request.getAssessmentTypes()
                        .stream()
                        .mapToDouble(type ->
                                type.getWeightage() != null
                                        ? type.getWeightage()
                                        : 0
                        )
                        .sum();

        if (totalWeightage != 100) {

            throw new RuntimeException(
                    "Total weightage must be 100%. Current weightage: "
                            + totalWeightage);
        }

        // -----------------------------------------------------
        // 5. Check Existing Structure
        // -----------------------------------------------------

        AssessmentStructure structure =
                assessmentStructureRepository
                        .findBySchoolIdAndSessionAndExamTermAndStudentClassAndSubjectId(
                                request.getSchoolId(),
                                request.getSession(),
                                request.getExamTerm(),
                                request.getStudentClass(),
                                request.getSubjectId()
                        )
                        .orElse(null);

        // -----------------------------------------------------
        // 6. If Structure Already Exists
        // -----------------------------------------------------

        if (structure != null) {

            // Existing components delete
            assessmentStructureTypeRepository
                    .deleteByAssessmentStructureId(
                            structure.getId()
                    );

        } else {

            // -------------------------------------------------
            // Create New Structure
            // -------------------------------------------------

            structure = new AssessmentStructure();

            structure.setSchool(school);
            structure.setSession(request.getSession());
            structure.setExamTerm(request.getExamTerm());
            structure.setStudentClass(request.getStudentClass());
            structure.setSubject(subject);
            structure.setStatus(true);
        }

        // -----------------------------------------------------
        // 7. Save Structure
        // -----------------------------------------------------

        structure =
                assessmentStructureRepository.save(structure);

        // -----------------------------------------------------
        // 8. Add Assessment Components
        // -----------------------------------------------------

        for (AssessmentStructureTypeRequest component
                : request.getAssessmentTypes()) {

            // ---------------------------------------------
            // Validate Assessment Type
            // ---------------------------------------------

            AssessmentType assessmentType =
                    assessmentTypeRepository
                            .findById(
                                    component.getAssessmentTypeId()
                            )
                            .orElseThrow(() ->
                                    new RuntimeException(
                                            "Assessment Type not found: "
                                                    + component
                                                    .getAssessmentTypeId()
                                    ));

            // ---------------------------------------------
            // Validate Marks
            // ---------------------------------------------

            if (component.getMaxMarks() == null
                    || component.getMaxMarks() <= 0) {

                throw new RuntimeException(
                        "Maximum marks must be greater than 0");
            }

            if (component.getPassingMarks() == null
                    || component.getPassingMarks() < 0) {

                throw new RuntimeException(
                        "Passing marks cannot be negative");
            }

            if (component.getPassingMarks()
                    > component.getMaxMarks()) {

                throw new RuntimeException(
                        "Passing marks cannot be greater than maximum marks");
            }

            // ---------------------------------------------
            // Create Component Mapping
            // ---------------------------------------------

            AssessmentStructureType structureType =
                    new AssessmentStructureType();

            structureType.setAssessmentStructure(structure);
            structureType.setAssessmentType(assessmentType);

            structureType.setMaxMarks(
                    component.getMaxMarks());

            structureType.setPassingMarks(
                    component.getPassingMarks());

            structureType.setWeightage(
                    component.getWeightage());

            structureType.setDisplayOrder(
                    component.getDisplayOrder());

            structureType.setStatus(true);

            // ---------------------------------------------
            // Save Component
            // ---------------------------------------------

            assessmentStructureTypeRepository
                    .save(structureType);
        }
    }

    @Transactional(readOnly = true)
public AssessmentStructureResponse getStructure(
        Long schoolId,
        Sessions session,
        String examTerm,
        Standard studentClass,
        Long subjectId) {

    AssessmentStructure structure =
            assessmentStructureRepository
                    .findBySchoolIdAndSessionAndExamTermAndStudentClassAndSubjectId(
                            schoolId,
                            session,
                            examTerm,
                            studentClass,
                            subjectId
                    )
                    .orElse(null);

    if (structure == null) {
        return null;
    }

    AssessmentStructureResponse response =
            new AssessmentStructureResponse();

    response.setId(structure.getId());

    response.setSchoolId(
            structure.getSchool().getId()
    );

    response.setSession(
            structure.getSession()
    );

    response.setExamTerm(
            structure.getExamTerm()
    );

    response.setStudentClass(
            structure.getStudentClass()
    );

    response.setSubjectId(
            structure.getSubject().getId()
    );

    List<AssessmentStructureTypeResponse> components =
            structure.getAssessmentTypes()
                    .stream()
                    .map(component -> {

                        AssessmentStructureTypeResponse dto =
                                new AssessmentStructureTypeResponse();

                        dto.setId(component.getId());

                        dto.setAssessmentTypeId(
                                component.getAssessmentType().getId()
                        );

                        dto.setAssessmentTypeName(
                                component.getAssessmentType()
                                        .getTypeName()
                        );

                        dto.setMaxMarks(
                                component.getMaxMarks()
                        );

                        dto.setPassingMarks(
                                component.getPassingMarks()
                        );

                        dto.setWeightage(
                                component.getWeightage()
                        );

                        dto.setDisplayOrder(
                                component.getDisplayOrder()
                        );

                        return dto;
                    })
                    .toList();

    response.setAssessmentTypes(components);

    return response;
}
}