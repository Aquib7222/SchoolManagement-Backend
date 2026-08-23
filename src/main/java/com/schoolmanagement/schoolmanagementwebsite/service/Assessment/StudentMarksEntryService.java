package com.schoolmanagement.schoolmanagementwebsite.service.Assessment;

import java.math.BigDecimal;
import java.math.RoundingMode;
import java.time.LocalDateTime;
import java.util.*;
import java.util.stream.Collectors;

import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import com.schoolmanagement.schoolmanagementwebsite.dto.Assessment.ComponentMarksRequest;
import com.schoolmanagement.schoolmanagementwebsite.dto.Assessment.ComponentMarksResponse;
import com.schoolmanagement.schoolmanagementwebsite.dto.Assessment.MarksEntryRequest;
import com.schoolmanagement.schoolmanagementwebsite.dto.Assessment.MarksEntryResponse;
import com.schoolmanagement.schoolmanagementwebsite.dto.Assessment.StudentMarksRequest;
import com.schoolmanagement.schoolmanagementwebsite.dto.Assessment.StudentMarksResponse;
import com.schoolmanagement.schoolmanagementwebsite.entity.Assessment.AssessmentStructure;
import com.schoolmanagement.schoolmanagementwebsite.entity.Assessment.AssessmentStructureType;
import com.schoolmanagement.schoolmanagementwebsite.entity.Assessment.GradeMaster;
import com.schoolmanagement.schoolmanagementwebsite.entity.Assessment.MarksAssessment;
import com.schoolmanagement.schoolmanagementwebsite.entity.Assessment.StudentAssessmentComponentMarks;
import com.schoolmanagement.schoolmanagementwebsite.entity.Assessment.StudentAssessmentMarks;
import com.schoolmanagement.schoolmanagementwebsite.entity.Assessment.SubjectManagement.SubjectMaster;
import com.schoolmanagement.schoolmanagementwebsite.entity.Student;
import com.schoolmanagement.schoolmanagementwebsite.enums.Assessment.MarksStatus;
import com.schoolmanagement.schoolmanagementwebsite.enums.Section;
import com.schoolmanagement.schoolmanagementwebsite.enums.Sessions;
import com.schoolmanagement.schoolmanagementwebsite.enums.Standard;
import com.schoolmanagement.schoolmanagementwebsite.repository.Assessment.AssessmentStructureRepository;
import com.schoolmanagement.schoolmanagementwebsite.repository.Assessment.GradeMasterRepository;
import com.schoolmanagement.schoolmanagementwebsite.repository.Assessment.MarksAssessmentRepository;
import com.schoolmanagement.schoolmanagementwebsite.repository.Assessment.StudentAssessmentComponentMarksRepository;
import com.schoolmanagement.schoolmanagementwebsite.repository.Assessment.StudentAssessmentMarksRepository;
import com.schoolmanagement.schoolmanagementwebsite.repository.Assessment.SubjectManagement.SubjectMasterRepository;
import com.schoolmanagement.schoolmanagementwebsite.repository.StudentRepository;

import lombok.RequiredArgsConstructor;

@Service
@RequiredArgsConstructor
@Transactional
public class StudentMarksEntryService {

    private final MarksAssessmentRepository marksAssessmentRepository;

    private final StudentAssessmentMarksRepository studentAssessmentMarksRepository;

    private final StudentAssessmentComponentMarksRepository componentMarksRepository;

    private final AssessmentStructureRepository assessmentStructureRepository;

    private final GradeMasterRepository gradeMasterRepository;

    private final StudentRepository studentRepository;

    private final SubjectMasterRepository subjectMasterRepository;

    // =========================================================
    // SAVE DRAFT
    // =========================================================
    public MarksEntryResponse saveDraft(MarksEntryRequest request) {

        validateRequest(request);

        /*
         * -----------------------------------------------------
         * Find Assessment Structure
         * -----------------------------------------------------
         */
        AssessmentStructure structure
                = assessmentStructureRepository
                        .findBySchoolIdAndSessionAndStudentClassAndSubjectId(
                                request.getSchoolId(),
                                request.getSession(),
                                request.getStudentClass(),
                                request.getSubjectId()
                        )
                        .orElseThrow(()
                                -> new RuntimeException(
                                "Assessment Structure not found"
                        )
                        );

        if (!Boolean.TRUE.equals(structure.getStatus())) {
            throw new RuntimeException(
                    "Assessment Structure is inactive"
            );
        }


        /*
         * -----------------------------------------------------
         * Find existing MarksAssessment
         * -----------------------------------------------------
         */
        MarksAssessment assessment
                = marksAssessmentRepository
                        .findBySchoolIdAndSessionAndExamTermIdAndStudentClassAndSectionAndSubjectId(
                                request.getSchoolId(),
                                request.getSession(),
                                request.getExamTermId(),
                                request.getStudentClass(),
                                request.getSection(),
                                request.getSubjectId()
                        )
                        .orElse(null);


        /*
         * -----------------------------------------------------
         * Create MarksAssessment if not exists
         * -----------------------------------------------------
         */
        if (assessment == null) {

            assessment = MarksAssessment.builder()
                    .schoolId(request.getSchoolId())
                    .session(request.getSession())
                    .examTermId(request.getExamTermId())
                    .studentClass(request.getStudentClass())
                    .section(request.getSection())
                    .subjectId(request.getSubjectId())
                    .assessmentStructure(structure)
                    .status(MarksStatus.DRAFT)
                    .build();

            assessment
                    = marksAssessmentRepository.save(assessment);

        } else {

            /*
             * Published assessment ko dobara edit/save
             * nahi karne denge.
             */
            if (assessment.getStatus() == MarksStatus.GENERATED) {

                throw new RuntimeException(
                        "Marks are already published. Published marks cannot be modified."
                );
            }
        }


        /*
         * -----------------------------------------------------
         * Active Assessment Components
         * -----------------------------------------------------
         */
        List<AssessmentStructureType> components
                = structure.getAssessmentTypes()
                        .stream()
                        .filter(Objects::nonNull)
                        .filter(component
                                -> Boolean.TRUE.equals(
                                component.getStatus()
                        )
                        )
                        .sorted(
                                Comparator.comparing(
                                        component
                                        -> Optional.ofNullable(
                                                component.getDisplayOrder()
                                        ).orElse(Integer.MAX_VALUE)
                                )
                        )
                        .toList();

        if (components.isEmpty()) {

            throw new RuntimeException(
                    "No active assessment components found"
            );
        }

        Map<Long, AssessmentStructureType> componentMap
                = components.stream()
                        .collect(
                                Collectors.toMap(
                                        AssessmentStructureType::getId,
                                        component -> component
                                )
                        );


        /*
         * -----------------------------------------------------
         * Save every student
         * -----------------------------------------------------
         */
        for (StudentMarksRequest studentRequest
                : request.getStudents()) {

            saveStudentDraft(
                    assessment,
                    studentRequest,
                    componentMap,
                    request.getSchoolId(),
                    request.getSession()
            );
        }


        /*
         * -----------------------------------------------------
         * Make sure status remains DRAFT
         * -----------------------------------------------------
         */
        assessment.setStatus(MarksStatus.DRAFT);
        assessment.setPublishedAt(null);

        marksAssessmentRepository.save(assessment);

        return buildResponse(
                assessment,
                components
        );
    }

    // =========================================================
    // SAVE STUDENT DRAFT
    // =========================================================
    private void saveStudentDraft(
            MarksAssessment assessment,
            StudentMarksRequest request,
            Map<Long, AssessmentStructureType> componentMap,
            Long schoolId,
            Sessions session) {

        if (request.getStudentId() == null) {

            throw new RuntimeException(
                    "Student ID is required"
            );
        }


        /*
         * -----------------------------------------------------
         * Find Student
         * -----------------------------------------------------
         */
        Student student
                = studentRepository.findById(
                        request.getStudentId()
                ).orElseThrow(()
                        -> new RuntimeException(
                                "Student not found: "
                                + request.getStudentId()
                        )
                );


        /*
         * -----------------------------------------------------
         * Find existing StudentAssessmentMarks
         * -----------------------------------------------------
         */
        StudentAssessmentMarks studentMarks
                = studentAssessmentMarksRepository
                        .findByMarksAssessmentIdAndStudentId(
                                assessment.getId(),
                                student.getId()
                        )
                        .orElse(null);

        if (studentMarks == null) {

            studentMarks
                    = StudentAssessmentMarks.builder()
                            .marksAssessment(assessment)
                            .student(student)
                            .build();

            studentMarks
                    = studentAssessmentMarksRepository.save(
                            studentMarks
                    );
        }

        if (request.getComponents() == null) {

            throw new RuntimeException(
                    "Components are required for student: "
                    + student.getId()
            );
        }


        /*
         * -----------------------------------------------------
         * Existing component marks
         * -----------------------------------------------------
         */
        List<StudentAssessmentComponentMarks> existingMarks
                = componentMarksRepository
                        .findByStudentAssessmentMarksId(
                                studentMarks.getId()
                        );

        Map<Long, StudentAssessmentComponentMarks> existingMap
                = existingMarks.stream()
                        .collect(
                                Collectors.toMap(
                                        mark
                                        -> mark.getAssessmentStructureType()
                                                .getId(),
                                        mark -> mark
                                )
                        );


        /*
         * -----------------------------------------------------
         * Duplicate component validation
         * -----------------------------------------------------
         */
        Set<Long> componentIds = new HashSet<>();

        BigDecimal totalMarks
                = BigDecimal.ZERO;

        BigDecimal totalMaxMarks
                = BigDecimal.ZERO;


        /*
         * -----------------------------------------------------
         * Process components
         * -----------------------------------------------------
         */
        for (ComponentMarksRequest componentRequest
                : request.getComponents()) {

            if (componentRequest.getComponentId() == null) {

                throw new RuntimeException(
                        "Component ID is required"
                );
            }

            if (!componentIds.add(
                    componentRequest.getComponentId()
            )) {

                throw new RuntimeException(
                        "Duplicate component ID: "
                        + componentRequest.getComponentId()
                );
            }

            AssessmentStructureType component
                    = componentMap.get(
                            componentRequest.getComponentId()
                    );

            if (component == null) {

                throw new RuntimeException(
                        "Invalid assessment component: "
                        + componentRequest.getComponentId()
                );
            }


            /*
             * Empty field = 0 marks
             */
            BigDecimal obtainedMarks
                    = Optional.ofNullable(
                            componentRequest.getObtainedMarks()
                    ).orElse(BigDecimal.ZERO);


            /*
             * Negative marks
             */
            if (obtainedMarks.compareTo(
                    BigDecimal.ZERO
            ) < 0) {

                throw new RuntimeException(
                        "Obtained marks cannot be negative"
                );
            }

            BigDecimal maxMarks
                    = BigDecimal.valueOf(
                            component.getMaxMarks()
                    );


            /*
             * Obtained > Maximum
             */
            if (obtainedMarks.compareTo(
                    maxMarks
            ) > 0) {

                throw new RuntimeException(
                        "Obtained marks cannot be greater than "
                        + component.getMaxMarks()
                        + " for component "
                        + component.getId()
                );
            }


            /*
             * -------------------------------------------------
             * Create / Update Component Marks
             * -------------------------------------------------
             */
            StudentAssessmentComponentMarks componentMarks
                    = existingMap.get(
                            component.getId()
                    );

            if (componentMarks == null) {

                componentMarks
                        = StudentAssessmentComponentMarks.builder()
                                .studentAssessmentMarks(studentMarks)
                                .assessmentStructureType(component)
                                .obtainedMarks(obtainedMarks)
                                .build();

            } else {

                componentMarks.setObtainedMarks(
                        obtainedMarks
                );
            }

            componentMarksRepository.save(
                    componentMarks
            );


            /*
             * -------------------------------------------------
             * Total
             * -------------------------------------------------
             */
            totalMarks
                    = totalMarks.add(obtainedMarks);

            totalMaxMarks
                    = totalMaxMarks.add(maxMarks);
        }


        /*
         * -----------------------------------------------------
         * Percentage
         * -----------------------------------------------------
         */
        BigDecimal percentage
                = BigDecimal.ZERO;

        if (totalMaxMarks.compareTo(
                BigDecimal.ZERO
        ) > 0) {

            percentage
                    = totalMarks
                            .multiply(
                                    BigDecimal.valueOf(100)
                            )
                            .divide(
                                    totalMaxMarks,
                                    2,
                                    RoundingMode.HALF_UP
                            );
        }


        /*
         * -----------------------------------------------------
         * Grade Master
         * -----------------------------------------------------
         */
        GradeMaster grade
                = findGrade(
                        schoolId,
                        session,
                        percentage.doubleValue()
                );


        /*
         * -----------------------------------------------------
         * Save calculated result
         * -----------------------------------------------------
         */
        studentMarks.setTotalMarks(
                totalMarks
        );

        studentMarks.setPercentage(
                percentage
        );

        if (grade != null) {

            studentMarks.setGrade(
                    grade.getGrade()
            );

            if (grade.getGradePoint() != null) {

                studentMarks.setGradePoint(
                        BigDecimal.valueOf(
                                grade.getGradePoint()
                        )
                );
            }

            studentMarks.setRemark(
                    grade.getRemarks()
            );
        }

        studentAssessmentMarksRepository.save(
                studentMarks
        );
    }

    // =========================================================
    // FIND GRADE
    // =========================================================
    private GradeMaster findGrade(
            Long schoolId,
            Sessions session,
            double percentage) {

        List<GradeMaster> grades
                = gradeMasterRepository
                        .findBySchoolIdAndSessionAndStatusTrueOrderByMinPercentageDesc(
                                schoolId,
                                session);

        return grades.stream()
                .filter(grade
                        -> percentage >= grade.getMinPercentage()
                && percentage <= grade.getMaxPercentage()
                )
                .findFirst()
                .orElse(null);
    }

    // =========================================================
    // PUBLISH MARKS
    // =========================================================
    public MarksEntryResponse publishMarks(
            Long schoolId,
            Sessions session,
            Long examTermId,
            Standard studentClass,
            Section section,
            Long subjectId) {


        /*
         * -----------------------------------------------------
         * Find Assessment
         * -----------------------------------------------------
         */
        MarksAssessment assessment
                = marksAssessmentRepository
                        .findBySchoolIdAndSessionAndExamTermIdAndStudentClassAndSectionAndSubjectId(
                                schoolId,
                                session,
                                examTermId,
                                studentClass,
                                section,
                                subjectId
                        )
                        .orElseThrow(()
                                -> new RuntimeException(
                                "Marks assessment not found"
                        )
                        );


        /*
         * -----------------------------------------------------
         * Already Published
         * -----------------------------------------------------
         */
        if (assessment.getStatus() == MarksStatus.GENERATED) {

            throw new RuntimeException(
                    "Marks are already published"
            );
        }


        /*
         * -----------------------------------------------------
         * Get students of this assessment
         * -----------------------------------------------------
         */
        List<StudentAssessmentMarks> studentMarks
                = studentAssessmentMarksRepository
                        .findByMarksAssessmentId(
                                assessment.getId()
                        );

        if (studentMarks == null
                || studentMarks.isEmpty()) {

            throw new RuntimeException(
                    "No student marks found. Please save marks first."
            );
        }


        /*
         * -----------------------------------------------------
         * Assessment Components
         * -----------------------------------------------------
         */
        AssessmentStructure structure
                = assessment.getAssessmentStructure();

        List<AssessmentStructureType> components
                = structure.getAssessmentTypes()
                        .stream()
                        .filter(component
                                -> Boolean.TRUE.equals(
                                component.getStatus()
                        )
                        )
                        .toList();

        if (components.isEmpty()) {

            throw new RuntimeException(
                    "No active assessment components found"
            );
        }


        /*
         * -----------------------------------------------------
         * Validate every student's marks
         * -----------------------------------------------------
         */
        for (StudentAssessmentMarks studentMarksEntity
                : studentMarks) {

            List<StudentAssessmentComponentMarks> componentMarks
                    = componentMarksRepository
                            .findByStudentAssessmentMarksId(
                                    studentMarksEntity.getId()
                            );


            /*
             * Every component must have marks
             */
            Set<Long> enteredComponentIds
                    = componentMarks.stream()
                            .map(mark
                                    -> mark.getAssessmentStructureType()
                                    .getId()
                            )
                            .collect(Collectors.toSet());

            for (AssessmentStructureType component
                    : components) {

                if (!enteredComponentIds.contains(
                        component.getId()
                )) {

                    throw new RuntimeException(
                            "Marks not entered for student ID "
                            + studentMarksEntity
                                    .getStudent()
                                    .getId()
                            + " for component "
                            + component.getId()
                    );
                }
            }


            /*
             * Recalculate result before publish
             */
            BigDecimal totalMarks
                    = componentMarks.stream()
                            .map(
                                    StudentAssessmentComponentMarks::getObtainedMarks
                            )
                            .filter(Objects::nonNull)
                            .reduce(
                                    BigDecimal.ZERO,
                                    BigDecimal::add
                            );

            BigDecimal totalMaxMarks
                    = components.stream()
                            .map(component
                                    -> BigDecimal.valueOf(
                                    component.getMaxMarks()
                            )
                            )
                            .reduce(
                                    BigDecimal.ZERO,
                                    BigDecimal::add
                            );

            BigDecimal percentage
                    = BigDecimal.ZERO;

            if (totalMaxMarks.compareTo(
                    BigDecimal.ZERO
            ) > 0) {

                percentage
                        = totalMarks
                                .multiply(
                                        BigDecimal.valueOf(100)
                                )
                                .divide(
                                        totalMaxMarks,
                                        2,
                                        RoundingMode.HALF_UP
                                );
            }


            /*
             * Grade Master
             */
            GradeMaster grade
                    = findGrade(
                            schoolId,
                            session,
                            percentage.doubleValue()
                    );

            if (grade == null) {

                throw new RuntimeException(
                        "Grade not defined for percentage "
                        + percentage
                );
            }

            studentMarksEntity.setTotalMarks(
                    totalMarks
            );

            studentMarksEntity.setPercentage(
                    percentage
            );

            studentMarksEntity.setGrade(
                    grade.getGrade()
            );

            if (grade.getGradePoint() != null) {

                studentMarksEntity.setGradePoint(
                        BigDecimal.valueOf(
                                grade.getGradePoint()
                        )
                );
            }

            studentMarksEntity.setRemark(
                    grade.getRemarks()
            );

            studentAssessmentMarksRepository.save(
                    studentMarksEntity
            );
        }


        /*
         * -----------------------------------------------------
         * Publish Assessment
         * -----------------------------------------------------
         */
        assessment.setStatus(
                MarksStatus.GENERATED
        );

        assessment.setPublishedAt(
                LocalDateTime.now()
        );

        marksAssessmentRepository.save(
                assessment
        );


        /*
         * -----------------------------------------------------
         * Response
         * -----------------------------------------------------
         */
        return buildResponse(
                assessment,
                components
        );
    }

    // =========================================================
    // GET MARKS
    // =========================================================
    @Transactional(readOnly = true)
    public MarksEntryResponse getMarks(
            Long schoolId,
            Sessions session,
            Long examTermId,
            Standard studentClass,
            Section section,
            Long subjectId) {

        MarksAssessment assessment
                = marksAssessmentRepository
                        .findBySchoolIdAndSessionAndExamTermIdAndStudentClassAndSectionAndSubjectId(
                                schoolId,
                                session,
                                examTermId,
                                studentClass,
                                section,
                                subjectId
                        )
                        .orElseThrow(()
                                -> new RuntimeException(
                                "Marks assessment not found"
                        )
                        );

        List<AssessmentStructureType> components
                = assessment
                        .getAssessmentStructure()
                        .getAssessmentTypes()
                        .stream()
                        .filter(component
                                -> Boolean.TRUE.equals(
                                component.getStatus()
                        )
                        )
                        .sorted(
                                Comparator.comparing(
                                        component
                                        -> Optional.ofNullable(
                                                component.getDisplayOrder()
                                        ).orElse(Integer.MAX_VALUE)
                                )
                        )
                        .toList();

        return buildResponse(
                assessment,
                components
        );
    }

    // =========================================================
// GET ALL SUBJECT MARKS FOR CLASS + SECTION
// =========================================================
    @Transactional(readOnly = true)
    public List<MarksEntryResponse> getClassMarks(
            Long schoolId,
            Sessions session,
            Long examTermId,
            Standard studentClass,
            Section section) {

        List<MarksAssessment> assessments
                = marksAssessmentRepository
                        .findBySchoolIdAndSessionAndExamTermIdAndStudentClassAndSection(
                                schoolId,
                                session,
                                examTermId,
                                studentClass,
                                section
                        );

        return assessments.stream()
                .map(assessment -> {

                    List<AssessmentStructureType> components
                            = assessment
                                    .getAssessmentStructure()
                                    .getAssessmentTypes()
                                    .stream()
                                    .filter(component
                                            -> Boolean.TRUE.equals(
                                            component.getStatus()
                                    )
                                    )
                                    .sorted(
                                            Comparator.comparing(
                                                    component
                                                    -> Optional.ofNullable(
                                                            component.getDisplayOrder()
                                                    ).orElse(Integer.MAX_VALUE)
                                            )
                                    )
                                    .toList();

                    return buildResponse(
                            assessment,
                            components
                    );
                })
                .toList();
    }

    // =========================================================
// VERIFY ALL SUBJECT MARKS FOR CLASS + SECTION
// =========================================================
@Transactional
public List<MarksEntryResponse> verifyClassMarks(
        Long schoolId,
        Sessions session,
        Long examTermId,
        Standard studentClass,
        Section section) {

    /*
     * -----------------------------------------------------
     * Find all subject assessments
     * -----------------------------------------------------
     */
    List<MarksAssessment> assessments =
            marksAssessmentRepository
                    .findBySchoolIdAndSessionAndExamTermIdAndStudentClassAndSection(
                            schoolId,
                            session,
                            examTermId,
                            studentClass,
                            section
                    );

    if (assessments == null || assessments.isEmpty()) {

        throw new RuntimeException(
                "No marks found for selected class and section"
        );
    }

    /*
     * -----------------------------------------------------
     * Validate and verify every subject
     * -----------------------------------------------------
     */
    for (MarksAssessment assessment : assessments) {

        /*
         * Already verified
         */
        if (assessment.getStatus() == MarksStatus.VERIFIED) {
            continue;
        }

        /*
         * Only GENERATED marks can be verified
         */
        if (assessment.getStatus() != MarksStatus.GENERATED) {

            throw new RuntimeException(
                    "Marks are not generated for subject ID: "
                    + assessment.getSubjectId()
            );
        }

        assessment.setStatus(MarksStatus.VERIFIED);
        assessment.setUpdatedAt(LocalDateTime.now());

        marksAssessmentRepository.save(assessment);
    }

    /*
     * -----------------------------------------------------
     * Return updated data
     * -----------------------------------------------------
     */
    return assessments.stream()
            .map(assessment -> {

                List<AssessmentStructureType> components =
                        assessment
                                .getAssessmentStructure()
                                .getAssessmentTypes()
                                .stream()
                                .filter(Objects::nonNull)
                                .filter(component ->
                                        Boolean.TRUE.equals(
                                                component.getStatus()
                                        )
                                )
                                .sorted(
                                        Comparator.comparing(
                                                component ->
                                                        Optional.ofNullable(
                                                                component.getDisplayOrder()
                                                        ).orElse(
                                                                Integer.MAX_VALUE
                                                        )
                                        )
                                )
                                .toList();

                return buildResponse(
                        assessment,
                        components
                );
            })
            .toList();
}
    // =========================================================
    // BUILD RESPONSE
    // =========================================================
    private MarksEntryResponse buildResponse(
            MarksAssessment assessment,
            List<AssessmentStructureType> components) {

        List<StudentAssessmentMarks> studentMarks
                = studentAssessmentMarksRepository
                        .findByMarksAssessmentId(
                                assessment.getId()
                        );

        List<StudentMarksResponse> students
                = studentMarks.stream()
                        .map(studentMarksEntity
                                -> buildStudentResponse(
                                studentMarksEntity,
                                components
                        )
                        )
                        .toList();

        double totalMaxMarks
                = components.stream()
                        .mapToDouble(
                                AssessmentStructureType::getMaxMarks
                        )
                        .sum();

        SubjectMaster subject
                = subjectMasterRepository
                        .findById(assessment.getSubjectId())
                        .orElse(null);

        return MarksEntryResponse.builder()
                .assessmentId(
                        assessment.getId()
                )
                .subjectId(assessment.getSubjectId())
                .subjectName(
                        subject != null
                                ? subject.getSubjectName()
                                : null
                )
                .status(
                        assessment.getStatus() != null
                        ? assessment.getStatus().name()
                        : null
                )
                .totalMaxMarks(
                        totalMaxMarks
                )
                .students(
                        students
                )
                .build();
    }

    // =========================================================
    // BUILD STUDENT RESPONSE
    // =========================================================
    private StudentMarksResponse buildStudentResponse(
            StudentAssessmentMarks studentMarks,
            List<AssessmentStructureType> components) {

        List<StudentAssessmentComponentMarks> savedComponents
                = componentMarksRepository
                        .findByStudentAssessmentMarksId(
                                studentMarks.getId()
                        );

        Map<Long, StudentAssessmentComponentMarks> marksMap
                = savedComponents.stream()
                        .collect(
                                Collectors.toMap(
                                        mark
                                        -> mark.getAssessmentStructureType()
                                                .getId(),
                                        mark -> mark
                                )
                        );

        List<ComponentMarksResponse> componentResponses
                = components.stream()
                        .map(component -> {

                            StudentAssessmentComponentMarks saved
                                    = marksMap.get(
                                            component.getId()
                                    );

                            return ComponentMarksResponse.builder()
                                    .componentId(
                                            component.getId()
                                    )
                                    .componentName(
                                            component
                                                    .getAssessmentType()
                                                    .getTypeName()
                                    )
                                    .maxMarks(
                                            component.getMaxMarks()
                                    )
                                    .passingMarks(
                                            component.getPassingMarks()
                                    )
                                    .obtainedMarks(
                                            saved != null
                                                    ? saved.getObtainedMarks()
                                                    : BigDecimal.ZERO
                                    )
                                    .build();

                        })
                        .toList();

        return StudentMarksResponse.builder()
                .studentId(
                        studentMarks
                                .getStudent()
                                .getId()
                )
                .admissionNumber(
                        studentMarks
                                .getStudent()
                                .getAdmissionNumber()
                )
                .studentName(
                        studentMarks.getStudent().getFirstName()
                        + " "
                        + studentMarks.getStudent().getLastName()
                )
                .components(
                        componentResponses
                )
                .totalMarks(
                        studentMarks.getTotalMarks()
                )
                .percentage(
                        studentMarks.getPercentage()
                )
                .grade(
                        studentMarks.getGrade()
                )
                .gradePoint(
                        studentMarks.getGradePoint()
                )
                .remark(
                        studentMarks.getRemark()
                )
                .build();
    }

    // =========================================================
    // VALIDATION
    // =========================================================
    private void validateRequest(
            MarksEntryRequest request) {

        if (request == null) {
            throw new RuntimeException(
                    "Request cannot be null"
            );
        }

        if (request.getSchoolId() == null) {
            throw new RuntimeException(
                    "School ID is required"
            );
        }

        if (request.getSession() == null) {
            throw new RuntimeException(
                    "Session is required"
            );
        }

        if (request.getExamTermId() == null) {
            throw new RuntimeException(
                    "Exam Term ID is required"
            );
        }

        if (request.getStudentClass() == null) {
            throw new RuntimeException(
                    "Student class is required"
            );
        }

        if (request.getSection() == null) {
            throw new RuntimeException(
                    "Section is required"
            );
        }

        if (request.getSubjectId() == null) {
            throw new RuntimeException(
                    "Subject ID is required"
            );
        }

        if (request.getStudents() == null
                || request.getStudents().isEmpty()) {

            throw new RuntimeException(
                    "At least one student is required"
            );
        }
    }

    // =========================================================
// PUBLISH SINGLE SUBJECT RESULT
// =========================================================
public MarksEntryResponse publishResult(
        Long schoolId,
        Sessions session,
        Long examTermId,
        Standard studentClass,
        Section section,
        Long subjectId) {

    MarksAssessment assessment =
            marksAssessmentRepository
                    .findBySchoolIdAndSessionAndExamTermIdAndStudentClassAndSectionAndSubjectId(
                            schoolId,
                            session,
                            examTermId,
                            studentClass,
                            section,
                            subjectId
                    )
                    .orElseThrow(() ->
                            new RuntimeException(
                                    "Marks assessment not found"
                            )
                    );

    // ---------------------------------------------------------
    // Only VERIFIED marks can be published
    // ---------------------------------------------------------
    if (assessment.getStatus() != MarksStatus.VERIFIED) {

        throw new RuntimeException(
                "Only VERIFIED marks can be published. Current status: "
                        + assessment.getStatus()
        );
    }

    // ---------------------------------------------------------
    // Student marks check
    // ---------------------------------------------------------
    List<StudentAssessmentMarks> studentMarks =
            studentAssessmentMarksRepository
                    .findByMarksAssessmentId(
                            assessment.getId()
                    );

    if (studentMarks == null || studentMarks.isEmpty()) {

        throw new RuntimeException(
                "No student marks found for this subject"
        );
    }

    // ---------------------------------------------------------
    // Publish
    // ---------------------------------------------------------
    assessment.setStatus(MarksStatus.PUBLISHED);

    assessment.setPublishedAt(
            LocalDateTime.now()
    );

    marksAssessmentRepository.save(assessment);

    // ---------------------------------------------------------
    // Response
    // ---------------------------------------------------------
    List<AssessmentStructureType> components =
            assessment
                    .getAssessmentStructure()
                    .getAssessmentTypes()
                    .stream()
                    .filter(Objects::nonNull)
                    .filter(component ->
                            Boolean.TRUE.equals(
                                    component.getStatus()
                            )
                    )
                    .sorted(
                            Comparator.comparing(
                                    component ->
                                            Optional.ofNullable(
                                                    component.getDisplayOrder()
                                            ).orElse(Integer.MAX_VALUE)
                            )
                    )
                    .toList();

    return buildResponse(
            assessment,
            components
    );
}

// =========================================================
// PUBLISH ALL VERIFIED SUBJECT RESULTS
// =========================================================
public List<MarksEntryResponse> publishAllResults(
        Long schoolId,
        Sessions session,
        Long examTermId,
        Standard studentClass,
        Section section) {

    // ---------------------------------------------------------
    // Find all VERIFIED assessments
    // ---------------------------------------------------------
    List<MarksAssessment> assessments =
            marksAssessmentRepository
                    .findBySchoolIdAndSessionAndExamTermIdAndStudentClassAndSectionAndStatus(
                            schoolId,
                            session,
                            examTermId,
                            studentClass,
                            section,
                            MarksStatus.VERIFIED
                    );

    if (assessments == null || assessments.isEmpty()) {

        throw new RuntimeException(
                "No VERIFIED subject marks found to publish"
        );
    }

    List<MarksEntryResponse> responses =
            new ArrayList<>();

    // ---------------------------------------------------------
    // Publish every verified subject
    // ---------------------------------------------------------
    for (MarksAssessment assessment : assessments) {

        List<StudentAssessmentMarks> studentMarks =
                studentAssessmentMarksRepository
                        .findByMarksAssessmentId(
                                assessment.getId()
                        );

        if (studentMarks == null || studentMarks.isEmpty()) {

            throw new RuntimeException(
                    "No student marks found for subject ID "
                            + assessment.getSubjectId()
            );
        }

        assessment.setStatus(
                MarksStatus.PUBLISHED
        );

        assessment.setPublishedAt(
                LocalDateTime.now()
        );

        marksAssessmentRepository.save(
                assessment
        );

        // -----------------------------------------------------
        // Components
        // -----------------------------------------------------
        List<AssessmentStructureType> components =
                assessment
                        .getAssessmentStructure()
                        .getAssessmentTypes()
                        .stream()
                        .filter(Objects::nonNull)
                        .filter(component ->
                                Boolean.TRUE.equals(
                                        component.getStatus()
                                )
                        )
                        .sorted(
                                Comparator.comparing(
                                        component ->
                                                Optional.ofNullable(
                                                        component.getDisplayOrder()
                                                ).orElse(Integer.MAX_VALUE)
                                )
                        )
                        .toList();

        responses.add(
                buildResponse(
                        assessment,
                        components
                )
        );
    }

    return responses;
}
}
