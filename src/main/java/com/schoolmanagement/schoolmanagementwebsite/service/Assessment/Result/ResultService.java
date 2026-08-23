// // // package com.schoolmanagement.schoolmanagementwebsite.service.Assessment.Result;

// // // import java.math.BigDecimal;
// // // import java.math.RoundingMode;
// // // import java.time.LocalDateTime;
// // // import java.util.ArrayList;
// // // import java.util.Comparator;
// // // import java.util.HashMap;
// // // import java.util.List;
// // // import java.util.Map;
// // // import java.util.Objects;
// // // import java.util.Optional;

// // // import org.springframework.stereotype.Service;
// // // import org.springframework.transaction.annotation.Transactional;

// // // import com.schoolmanagement.schoolmanagementwebsite.dto.Assessment.Result.ResultResponse;
// // // import com.schoolmanagement.schoolmanagementwebsite.dto.Assessment.Result.ResultSubjectResponse;
// // // import com.schoolmanagement.schoolmanagementwebsite.entity.Assessment.Result.ResultComponent;
// // // import com.schoolmanagement.schoolmanagementwebsite.dto.Assessment.Result.ResultComponentResponse;
// // // import com.schoolmanagement.schoolmanagementwebsite.entity.Assessment.AssessmentStructureType;
// // // import com.schoolmanagement.schoolmanagementwebsite.entity.Assessment.GradeMaster;
// // // import com.schoolmanagement.schoolmanagementwebsite.entity.Assessment.MarksAssessment;
// // // import com.schoolmanagement.schoolmanagementwebsite.entity.Assessment.Result.Result;
// // // import com.schoolmanagement.schoolmanagementwebsite.entity.Assessment.Result.ResultSubject;
// // // import com.schoolmanagement.schoolmanagementwebsite.entity.Assessment.StudentAssessmentMarks;
// // // import com.schoolmanagement.schoolmanagementwebsite.entity.Assessment.SubjectManagement.SubjectMaster;
// // // import com.schoolmanagement.schoolmanagementwebsite.entity.Student;
// // // import com.schoolmanagement.schoolmanagementwebsite.enums.Assessment.MarksStatus;
// // // import com.schoolmanagement.schoolmanagementwebsite.enums.Assessment.ResultStatus;
// // // import com.schoolmanagement.schoolmanagementwebsite.enums.Section;
// // // import com.schoolmanagement.schoolmanagementwebsite.enums.Sessions;
// // // import com.schoolmanagement.schoolmanagementwebsite.enums.Standard;
// // // import com.schoolmanagement.schoolmanagementwebsite.repository.Assessment.GradeMasterRepository;
// // // import com.schoolmanagement.schoolmanagementwebsite.repository.Assessment.MarksAssessmentRepository;
// // // import com.schoolmanagement.schoolmanagementwebsite.repository.Assessment.Result.ResultRepository;
// // // import com.schoolmanagement.schoolmanagementwebsite.repository.Assessment.Result.ResultSubjectRepository;
// // // import com.schoolmanagement.schoolmanagementwebsite.repository.Assessment.StudentAssessmentMarksRepository;
// // // import com.schoolmanagement.schoolmanagementwebsite.repository.Assessment.SubjectManagement.SubjectMasterRepository;
// // // import com.schoolmanagement.schoolmanagementwebsite.repository.StudentRepository;

// // // import lombok.RequiredArgsConstructor;

// // // @Service
// // // @RequiredArgsConstructor
// // // @Transactional
// // // public class ResultService {

// // //     private final ResultRepository resultRepository;

// // //     private final ResultSubjectRepository resultSubjectRepository;

// // //     private final MarksAssessmentRepository marksAssessmentRepository;

// // //     private final StudentAssessmentMarksRepository studentAssessmentMarksRepository;

// // //     private final StudentRepository studentRepository;

// // //     private final SubjectMasterRepository subjectMasterRepository;

// // //     private final GradeMasterRepository gradeMasterRepository;


// // //     // =========================================================
// // //     // PUBLISH SINGLE STUDENT RESULT
// // //     // =========================================================

// // //     public ResultResponse publishStudentResult(

// // //             Long schoolId,

// // //             Sessions session,

// // //             Long examTermId,

// // //             Standard studentClass,

// // //             Section section,

// // //             Long studentId) {


// // //         // -----------------------------------------------------
// // //         // FIND STUDENT
// // //         // -----------------------------------------------------

// // //         Student student =
// // //                 studentRepository.findById(studentId)
// // //                         .orElseThrow(() ->
// // //                                 new RuntimeException(
// // //                                         "Student not found: "
// // //                                                 + studentId
// // //                                 )
// // //                         );


// // //         // -----------------------------------------------------
// // //         // FIND ALL SUBJECT ASSESSMENTS
// // //         // -----------------------------------------------------

// // //         List<MarksAssessment> assessments =
// // //                 marksAssessmentRepository
// // //                         .findBySchoolIdAndSessionAndExamTermIdAndStudentClassAndSection(
// // //                                 schoolId,
// // //                                 session,
// // //                                 examTermId,
// // //                                 studentClass,
// // //                                 section
// // //                         );


// // //         if (assessments == null
// // //                 || assessments.isEmpty()) {

// // //             throw new RuntimeException(
// // //                     "No marks found for selected class and section"
// // //             );
// // //         }


// // //         // -----------------------------------------------------
// // //         // ALL SUBJECTS MUST BE VERIFIED
// // //         // -----------------------------------------------------

// // //         for (MarksAssessment assessment : assessments) {

// // //             if (assessment.getStatus()
// // //                     != MarksStatus.VERIFIED) {

// // //                 throw new RuntimeException(
// // //                         "Result cannot be published. Subject ID "
// // //                                 + assessment.getSubjectId()
// // //                                 + " is not VERIFIED. Current status: "
// // //                                 + assessment.getStatus()
// // //                 );
// // //             }
// // //         }


// // //         // -----------------------------------------------------
// // //         // EXISTING RESULT
// // //         // -----------------------------------------------------

// // //         Result result =
// // //                 resultRepository
// // //                         .findBySchoolIdAndStudentIdAndSessionAndExamTermId(
// // //                                 schoolId,
// // //                                 studentId,
// // //                                 session,
// // //                                 examTermId
// // //                         )
// // //                         .orElse(null);


// // //         // -----------------------------------------------------
// // //         // IF ALREADY PUBLISHED
// // //         // -----------------------------------------------------

// // //         if (result != null
// // //                 && result.getStatus()
// // //                 == ResultStatus.PUBLISHED) {

// // //             throw new RuntimeException(
// // //                     "Result is already published for this student"
// // //             );
// // //         }


// // //         // -----------------------------------------------------
// // //         // CREATE RESULT
// // //         // -----------------------------------------------------

// // //         if (result == null) {

// // //             result = Result.builder()
// // //                     .schoolId(schoolId)
// // //                     .student(student)
// // //                     .admissionNumber(
// // //                             student.getAdmissionNumber()
// // //                     )
// // //                     .studentName(
// // //                             getStudentName(student)
// // //                     )
// // //                     .session(session)
// // //                     .examTermId(examTermId)
// // //                     .studentClass(studentClass)
// // //                     .section(section)
// // //                     .status(ResultStatus.PUBLISHED)
// // //                     .publishedAt(LocalDateTime.now())
// // //                     .build();

// // //         } else {

// // //             // Existing unpublished result
// // //             resultSubjectRepository
// // //                     .deleteByResultId(result.getId());

// // //             result.setStudent(student);
// // //             result.setAdmissionNumber(
// // //                     student.getAdmissionNumber()
// // //             );
// // //             result.setStudentName(
// // //                     getStudentName(student)
// // //             );
// // //             result.setSession(session);
// // //             result.setExamTermId(examTermId);
// // //             result.setStudentClass(studentClass);
// // //             result.setSection(section);
// // //             result.setStatus(
// // //                     ResultStatus.PUBLISHED
// // //             );
// // //             result.setPublishedAt(
// // //                     LocalDateTime.now()
// // //             );
// // //         }


// // //         // -----------------------------------------------------
// // //         // SAVE RESULT FIRST
// // //         // -----------------------------------------------------

// // //         result = resultRepository.save(result);


// // //         // -----------------------------------------------------
// // //         // SUBJECT RESULTS
// // //         // -----------------------------------------------------

// // //         BigDecimal totalMarks =
// // //                 BigDecimal.ZERO;

// // //         BigDecimal totalMaxMarks =
// // //                 BigDecimal.ZERO;


// // //         List<ResultSubject> resultSubjects =
// // //                 new ArrayList<>();


// // //         for (MarksAssessment assessment
// // //                 : assessments) {


// // //             // -------------------------------------------------
// // //             // FIND STUDENT MARKS
// // //             // -------------------------------------------------

// // //             StudentAssessmentMarks studentMarks =
// // //                     studentAssessmentMarksRepository
// // //                             .findByMarksAssessmentIdAndStudentId(
// // //                                     assessment.getId(),
// // //                                     studentId
// // //                             )
// // //                             .orElseThrow(() ->
// // //                                     new RuntimeException(
// // //                                             "Marks not found for student ID "
// // //                                                     + studentId
// // //                                                     + " for subject ID "
// // //                                                     + assessment.getSubjectId()
// // //                                     )
// // //                             );


// // //             // -------------------------------------------------
// // //             // COMPONENTS
// // //             // -------------------------------------------------

// // //             List<AssessmentStructureType> components =
// // //                     assessment
// // //                             .getAssessmentStructure()
// // //                             .getAssessmentTypes()
// // //                             .stream()
// // //                             .filter(Objects::nonNull)
// // //                             .filter(component ->
// // //                                     Boolean.TRUE.equals(
// // //                                             component.getStatus()
// // //                                     )
// // //                             )
// // //                             .toList();


// // //             BigDecimal maxMarks =
// // //                     components.stream()
// // //                             .map(component ->
// // //                                     BigDecimal.valueOf(
// // //                                             component.getMaxMarks()
// // //                                     )
// // //                             )
// // //                             .reduce(
// // //                                     BigDecimal.ZERO,
// // //                                     BigDecimal::add
// // //                             );


// // //             BigDecimal obtainedMarks =
// // //                     Optional.ofNullable(
// // //                             studentMarks.getTotalMarks()
// // //                     )
// // //                     .orElse(BigDecimal.ZERO);


// // //             BigDecimal percentage =
// // //                     calculatePercentage(
// // //                             obtainedMarks,
// // //                             maxMarks
// // //                     );


// // //             // -------------------------------------------------
// // //             // SUBJECT
// // //             // -------------------------------------------------

// // //             SubjectMaster subject =
// // //                     subjectMasterRepository
// // //                             .findById(
// // //                                     assessment.getSubjectId()
// // //                             )
// // //                             .orElse(null);


// // //             // -------------------------------------------------
// // //             // RESULT SUBJECT
// // //             // -------------------------------------------------

// // //             ResultSubject resultSubject =
// // //                     ResultSubject.builder()
// // //                             .result(result)
// // //                             .subjectId(
// // //                                     assessment.getSubjectId()
// // //                             )
// // //                             .subjectName(
// // //                                     subject != null
// // //                                             ? subject.getSubjectName()
// // //                                             : "Unknown Subject"
// // //                             )
// // //                             .totalMarks(
// // //                                     obtainedMarks
// // //                             )
// // //                             .maxMarks(
// // //                                     maxMarks
// // //                             )
// // //                             .percentage(
// // //                                     percentage
// // //                             )
// // //                             .grade(
// // //                                     studentMarks.getGrade()
// // //                             )
// // //                             .gradePoint(
// // //                                     studentMarks.getGradePoint()
// // //                             )
// // //                             .remark(
// // //                                     studentMarks.getRemark()
// // //                             )
// // //                             .build();


// // //             resultSubjects.add(resultSubject);


// // //             totalMarks =
// // //                     totalMarks.add(obtainedMarks);

// // //             totalMaxMarks =
// // //                     totalMaxMarks.add(maxMarks);
// // //         }


// // //         // -----------------------------------------------------
// // //         // FINAL RESULT CALCULATION
// // //         // -----------------------------------------------------

// // //         BigDecimal finalPercentage =
// // //                 calculatePercentage(
// // //                         totalMarks,
// // //                         totalMaxMarks
// // //                 );


// // //         GradeMaster grade =
// // //                 findGrade(
// // //                         schoolId,
// // //                         session,
// // //                         finalPercentage.doubleValue()
// // //                 );


// // //         if (grade == null) {

// // //             throw new RuntimeException(
// // //                     "Grade not defined for final percentage "
// // //                             + finalPercentage
// // //             );
// // //         }


// // //         // -----------------------------------------------------
// // //         // FINAL RESULT DATA
// // //         // -----------------------------------------------------

// // //         result.setTotalMarks(
// // //                 totalMarks
// // //         );

// // //         result.setTotalMaxMarks(
// // //                 totalMaxMarks
// // //         );

// // //         result.setPercentage(
// // //                 finalPercentage
// // //         );

// // //         result.setGrade(
// // //                 grade.getGrade()
// // //         );

// // //         if (grade.getGradePoint() != null) {

// // //             result.setGradePoint(
// // //                     BigDecimal.valueOf(
// // //                             grade.getGradePoint()
// // //                     )
// // //             );
// // //         }

// // //         result.setRemark(
// // //                 grade.getRemarks()
// // //         );

// // //         // -----------------------------------------------------
// // //         // RANK
// // //         // -----------------------------------------------------

// // //         Integer rank =
// // //                 calculateRank(
// // //                         schoolId,
// // //                         session,
// // //                         examTermId,
// // //                         studentClass,
// // //                         section,
// // //                         studentId,
// // //                         finalPercentage
// // //                 );

// // //         result.setRank(rank);


// // //         // -----------------------------------------------------
// // //         // SAVE SUBJECTS
// // //         // -----------------------------------------------------

// // //         resultSubjects.forEach(
// // //                 resultSubjectRepository::save
// // //         );


// // //         // -----------------------------------------------------
// // //         // SAVE FINAL RESULT
// // //         // -----------------------------------------------------

// // //         result =
// // //                 resultRepository.save(result);


// // //         return buildResponse(result);
// // //     }


// // //     // =========================================================
// // //     // CALCULATE PERCENTAGE
// // //     // =========================================================

// // //     private BigDecimal calculatePercentage(

// // //             BigDecimal obtained,

// // //             BigDecimal maximum) {

// // //         if (maximum == null
// // //                 || maximum.compareTo(
// // //                         BigDecimal.ZERO
// // //                 ) <= 0) {

// // //             return BigDecimal.ZERO;
// // //         }

// // //         return obtained
// // //                 .multiply(
// // //                         BigDecimal.valueOf(100)
// // //                 )
// // //                 .divide(
// // //                         maximum,
// // //                         2,
// // //                         RoundingMode.HALF_UP
// // //                 );
// // //     }


// // //     // =========================================================
// // //     // FIND GRADE
// // //     // =========================================================

// // //     private GradeMaster findGrade(

// // //             Long schoolId,

// // //             Sessions session,

// // //             double percentage) {

// // //         List<GradeMaster> grades =
// // //                 gradeMasterRepository
// // //                         .findBySchoolIdAndSessionAndStatusTrueOrderByMinPercentageDesc(
// // //                                 schoolId,
// // //                                 session
// // //                         );

// // //         return grades.stream()
// // //                 .filter(grade ->
// // //                         percentage >=
// // //                                 grade.getMinPercentage()

// // //                         &&

// // //                         percentage <=
// // //                                 grade.getMaxPercentage()
// // //                 )
// // //                 .findFirst()
// // //                 .orElse(null);
// // //     }


// // //     // =========================================================
// // //     // CALCULATE RANK
// // //     // =========================================================

// // //     private Integer calculateRank(

// // //             Long schoolId,

// // //             Sessions session,

// // //             Long examTermId,

// // //             Standard studentClass,

// // //             Section section,

// // //             Long targetStudentId,

// // //             BigDecimal targetPercentage) {


// // //         List<Result> existingResults =
// // //                 resultRepository
// // //                         .findBySchoolIdAndSessionAndExamTermIdAndStudentClassAndSection(
// // //                                 schoolId,
// // //                                 session,
// // //                                 examTermId,
// // //                                 studentClass,
// // //                                 section
// // //                         );


// // //         /*
// // //          * Only already published results are included.
// // //          *
// // //          * If this is the first published result,
// // //          * rank will temporarily be 1.
// // //          *
// // //          * We will recalculate ranks when other students
// // //          * are published.
// // //          */

// // //         int rank = 1;

// // //         for (Result result : existingResults) {

// // //             if (result.getStudent() == null) {
// // //                 continue;
// // //             }

// // //             if (result.getStudent()
// // //                     .getId()
// // //                     .equals(targetStudentId)) {

// // //                 continue;
// // //             }

// // //             if (result.getPercentage() != null
// // //                     && result.getPercentage()
// // //                     .compareTo(
// // //                             targetPercentage
// // //                     ) > 0) {

// // //                 rank++;
// // //             }
// // //         }

// // //         return rank;
// // //     }


// // //     // =========================================================
// // //     // GET PUBLISHED STUDENT RESULT
// // //     // =========================================================

// // //     @Transactional(readOnly = true)
// // //     public ResultResponse getStudentResult(

// // //             Long schoolId,

// // //             Sessions session,

// // //             Long examTermId,

// // //             Long studentId) {


// // //         Result result =
// // //                 resultRepository
// // //                         .findBySchoolIdAndStudentIdAndSessionAndExamTermId(
// // //                                 schoolId,
// // //                                 studentId,
// // //                                 session,
// // //                                 examTermId
// // //                         )
// // //                         .orElseThrow(() ->
// // //                                 new RuntimeException(
// // //                                         "Published result not found"
// // //                                 )
// // //                         );


// // //         if (result.getStatus()
// // //                 != ResultStatus.PUBLISHED) {

// // //             throw new RuntimeException(
// // //                     "Result is not published"
// // //             );
// // //         }


// // //         return buildResponse(result);
// // //     }


// // //     // =========================================================
// // //     // GET CLASS RESULTS
// // //     // =========================================================

// // //     @Transactional(readOnly = true)
// // //     public List<ResultResponse> getClassResults(

// // //             Long schoolId,

// // //             Sessions session,

// // //             Long examTermId,

// // //             Standard studentClass,

// // //             Section section) {


// // //         List<Result> results =
// // //                 resultRepository
// // //                         .findBySchoolIdAndSessionAndExamTermIdAndStudentClassAndSectionAndStatus(
// // //                                 schoolId,
// // //                                 session,
// // //                                 examTermId,
// // //                                 studentClass,
// // //                                 section,
// // //                                 ResultStatus.PUBLISHED
// // //                         );


// // //         return results.stream()
// // //                 .sorted(
// // //                         Comparator.comparing(
// // //                                 Result::getRank,
// // //                                 Comparator.nullsLast(
// // //                                         Integer::compareTo
// // //                                 )
// // //                         )
// // //                 )
// // //                 .map(this::buildResponse)
// // //                 .toList();
// // //     }


// // //     // =========================================================
// // //     // BUILD RESPONSE
// // //     // =========================================================

// // //     private ResultResponse buildResponse(
// // //             Result result) {


// // //         List<ResultSubjectResponse> subjects =
// // //                 resultSubjectRepository
// // //                         .findByResultId(
// // //                                 result.getId()
// // //                         )
// // //                         .stream()
// // //                         .map(subject ->
// // //                                 ResultSubjectResponse
// // //                                         .builder()
// // //                                         .id(subject.getId())
// // //                                         .subjectId(
// // //                                                 subject.getSubjectId()
// // //                                         )
// // //                                         .subjectName(
// // //                                                 subject.getSubjectName()
// // //                                         )
// // //                                         .totalMarks(
// // //                                                 subject.getTotalMarks()
// // //                                         )
// // //                                         .maxMarks(
// // //                                                 subject.getMaxMarks()
// // //                                         )
// // //                                         .percentage(
// // //                                                 subject.getPercentage()
// // //                                         )
// // //                                         .grade(
// // //                                                 subject.getGrade()
// // //                                         )
// // //                                         .gradePoint(
// // //                                                 subject.getGradePoint()
// // //                                         )
// // //                                         .remark(
// // //                                                 subject.getRemark()
// // //                                         )
// // //                                         .build()
// // //                         )
// // //                         .toList();


// // //         return ResultResponse.builder()
// // //                 .id(result.getId())
// // //                 .schoolId(result.getSchoolId())
// // //                 .studentId(
// // //                         result.getStudent()
// // //                                 != null
// // //                                 ? result.getStudent().getId()
// // //                                 : null
// // //                 )
// // //                 .admissionNumber(
// // //                         result.getAdmissionNumber()
// // //                 )
// // //                 .studentName(
// // //                         result.getStudentName()
// // //                 )
// // //                 .session(
// // //                         result.getSession()
// // //                 )
// // //                 .examTermId(
// // //                         result.getExamTermId()
// // //                 )
// // //                 .studentClass(
// // //                         result.getStudentClass()
// // //                 )
// // //                 .section(
// // //                         result.getSection()
// // //                 )
// // //                 .totalMarks(
// // //                         result.getTotalMarks()
// // //                 )
// // //                 .totalMaxMarks(
// // //                         result.getTotalMaxMarks()
// // //                 )
// // //                 .percentage(
// // //                         result.getPercentage()
// // //                 )
// // //                 .grade(
// // //                         result.getGrade()
// // //                 )
// // //                 .gradePoint(
// // //                         result.getGradePoint()
// // //                 )
// // //                 .remark(
// // //                         result.getRemark()
// // //                 )
// // //                 .rank(
// // //                         result.getRank()
// // //                 )
// // //                 .status(
// // //                         result.getStatus()
// // //                 )
// // //                 .publishedAt(
// // //                         result.getPublishedAt()
// // //                 )
// // //                 .createdAt(
// // //                         result.getCreatedAt()
// // //                 )
// // //                 .updatedAt(
// // //                         result.getUpdatedAt()
// // //                 )
// // //                 .subjects(
// // //                         subjects
// // //                 )
// // //                 .build();
// // //     }


// // //     // =========================================================
// // //     // STUDENT NAME
// // //     // =========================================================

// // //     private String getStudentName(Student student) {

// // //         String firstName =
// // //                 student.getFirstName() != null
// // //                         ? student.getFirstName()
// // //                         : "";

// // //         String lastName =
// // //                 student.getLastName() != null
// // //                         ? student.getLastName()
// // //                         : "";

// // //         return (
// // //                 firstName
// // //                         + " "
// // //                         + lastName
// // //         ).trim();
// // //     }
// // // }



// // package com.schoolmanagement.schoolmanagementwebsite.service.Assessment.Result;

// // import java.math.BigDecimal;
// // import java.math.RoundingMode;
// // import java.time.LocalDateTime;
// // import java.util.ArrayList;
// // import java.util.Comparator;
// // import java.util.List;
// // import java.util.Objects;
// // import java.util.Optional;

// // import org.springframework.stereotype.Service;
// // import org.springframework.transaction.annotation.Transactional;

// // import com.schoolmanagement.schoolmanagementwebsite.dto.Assessment.Result.ResultResponse;
// // import com.schoolmanagement.schoolmanagementwebsite.dto.Assessment.Result.ResultSubjectResponse;
// // import com.schoolmanagement.schoolmanagementwebsite.entity.Assessment.AssessmentStructureType;
// // import com.schoolmanagement.schoolmanagementwebsite.entity.Assessment.GradeMaster;
// // import com.schoolmanagement.schoolmanagementwebsite.entity.Assessment.MarksAssessment;
// // import com.schoolmanagement.schoolmanagementwebsite.entity.Assessment.Result.Result;
// // import com.schoolmanagement.schoolmanagementwebsite.entity.Assessment.Result.ResultSubject;
// // import com.schoolmanagement.schoolmanagementwebsite.entity.Assessment.Result.ResultComponent;
// // import com.schoolmanagement.schoolmanagementwebsite.entity.Assessment.StudentAssessmentComponentMarks;
// // import com.schoolmanagement.schoolmanagementwebsite.entity.Assessment.StudentAssessmentMarks;
// // import com.schoolmanagement.schoolmanagementwebsite.entity.Assessment.SubjectManagement.SubjectMaster;
// // import com.schoolmanagement.schoolmanagementwebsite.entity.Student;
// // import com.schoolmanagement.schoolmanagementwebsite.enums.Assessment.MarksStatus;
// // import com.schoolmanagement.schoolmanagementwebsite.enums.Assessment.ResultStatus;
// // import com.schoolmanagement.schoolmanagementwebsite.enums.Section;
// // import com.schoolmanagement.schoolmanagementwebsite.enums.Sessions;
// // import com.schoolmanagement.schoolmanagementwebsite.enums.Standard;
// // import com.schoolmanagement.schoolmanagementwebsite.repository.Assessment.GradeMasterRepository;
// // import com.schoolmanagement.schoolmanagementwebsite.repository.Assessment.MarksAssessmentRepository;
// // import com.schoolmanagement.schoolmanagementwebsite.repository.Assessment.Result.ResultRepository;
// // import com.schoolmanagement.schoolmanagementwebsite.repository.Assessment.Result.ResultComponentRepository;
// // import com.schoolmanagement.schoolmanagementwebsite.repository.Assessment.Result.ResultSubjectRepository;
// // import com.schoolmanagement.schoolmanagementwebsite.repository.Assessment.StudentAssessmentComponentMarksRepository;
// // import com.schoolmanagement.schoolmanagementwebsite.repository.Assessment.StudentAssessmentMarksRepository;
// // import com.schoolmanagement.schoolmanagementwebsite.repository.Assessment.SubjectManagement.SubjectMasterRepository;
// // import com.schoolmanagement.schoolmanagementwebsite.repository.StudentRepository;

// // import lombok.RequiredArgsConstructor;

// // @Service
// // @RequiredArgsConstructor
// // @Transactional
// // public class ResultService {

// //     private final ResultRepository resultRepository;

// //     private final ResultSubjectRepository resultSubjectRepository;

// //     private final ResultComponentRepository resultSubjectComponentRepository;

// //     private final MarksAssessmentRepository marksAssessmentRepository;

// //     private final StudentAssessmentMarksRepository studentAssessmentMarksRepository;

// //     private final StudentAssessmentComponentMarksRepository
// //             studentAssessmentComponentMarksRepository;

// //     private final StudentRepository studentRepository;

// //     private final SubjectMasterRepository subjectMasterRepository;

// //     private final GradeMasterRepository gradeMasterRepository;


// //     // =========================================================
// //     // PUBLISH SINGLE STUDENT RESULT
// //     // =========================================================

// //     public ResultResponse publishStudentResult(

// //             Long schoolId,
// //             Sessions session,
// //             Long examTermId,
// //             Standard studentClass,
// //             Section section,
// //             Long studentId) {

// //         // -----------------------------------------------------
// //         // STUDENT
// //         // -----------------------------------------------------

// //         Student student =
// //                 studentRepository.findById(studentId)
// //                         .orElseThrow(() ->
// //                                 new RuntimeException(
// //                                         "Student not found: " + studentId
// //                                 )
// //                         );


// //         // -----------------------------------------------------
// //         // SUBJECT ASSESSMENTS
// //         // -----------------------------------------------------

// //         List<MarksAssessment> assessments =
// //                 marksAssessmentRepository
// //                         .findBySchoolIdAndSessionAndExamTermIdAndStudentClassAndSection(
// //                                 schoolId,
// //                                 session,
// //                                 examTermId,
// //                                 studentClass,
// //                                 section
// //                         );


// //         if (assessments == null || assessments.isEmpty()) {

// //             throw new RuntimeException(
// //                     "No marks found for selected class and section"
// //             );
// //         }


// //         // -----------------------------------------------------
// //         // VERIFY ALL SUBJECTS
// //         // -----------------------------------------------------

// //         for (MarksAssessment assessment : assessments) {

// //             if (assessment.getStatus() != MarksStatus.VERIFIED) {

// //                 throw new RuntimeException(
// //                         "Result cannot be published. Subject ID "
// //                                 + assessment.getSubjectId()
// //                                 + " is not VERIFIED. Current status: "
// //                                 + assessment.getStatus()
// //                 );
// //             }
// //         }


// //         // -----------------------------------------------------
// //         // EXISTING RESULT
// //         // -----------------------------------------------------

// //         Result result =
// //                 resultRepository
// //                         .findBySchoolIdAndStudentIdAndSessionAndExamTermId(
// //                                 schoolId,
// //                                 studentId,
// //                                 session,
// //                                 examTermId
// //                         )
// //                         .orElse(null);


// //         // -----------------------------------------------------
// //         // ALREADY PUBLISHED
// //         // -----------------------------------------------------

// //         if (result != null
// //                 && result.getStatus() == ResultStatus.PUBLISHED) {

// //             throw new RuntimeException(
// //                     "Result is already published for this student"
// //             );
// //         }


// //         // -----------------------------------------------------
// //         // CREATE / UPDATE RESULT
// //         // -----------------------------------------------------

// //         if (result == null) {

// //             result = Result.builder()
// //                     .schoolId(schoolId)
// //                     .student(student)
// //                     .admissionNumber(
// //                             student.getAdmissionNumber()
// //                     )
// //                     .studentName(
// //                             getStudentName(student)
// //                     )
// //                     .session(session)
// //                     .examTermId(examTermId)
// //                     .studentClass(studentClass)
// //                     .section(section)
// //                     .status(ResultStatus.PUBLISHED)
// //                     .publishedAt(LocalDateTime.now())
// //                     .build();

// //         } else {

// //             // Remove old subject data
// //             List<ResultSubject> oldSubjects =
// //                     resultSubjectRepository.findByResultId(
// //                             result.getId()
// //                     );

// //             for (ResultSubject oldSubject : oldSubjects) {

// //                 resultComponentRepository
// //                         .deleteByResultSubjectId(
// //                                 oldSubject.getId()
// //                         );
// //             }

// //             resultSubjectRepository
// //                     .deleteByResultId(result.getId());


// //             result.setStudent(student);

// //             result.setAdmissionNumber(
// //                     student.getAdmissionNumber()
// //             );

// //             result.setStudentName(
// //                     getStudentName(student)
// //             );

// //             result.setSession(session);

// //             result.setExamTermId(examTermId);

// //             result.setStudentClass(studentClass);

// //             result.setSection(section);

// //             result.setStatus(
// //                     ResultStatus.PUBLISHED
// //             );

// //             result.setPublishedAt(
// //                     LocalDateTime.now()
// //             );
// //         }


// //         // -----------------------------------------------------
// //         // SAVE RESULT FIRST
// //         // -----------------------------------------------------

// //         result = resultRepository.save(result);


// //         // -----------------------------------------------------
// //         // TOTALS
// //         // -----------------------------------------------------

// //         BigDecimal totalMarks =
// //                 BigDecimal.ZERO;

// //         BigDecimal totalMaxMarks =
// //                 BigDecimal.ZERO;


// //         List<ResultSubject> resultSubjects =
// //                 new ArrayList<>();


// //         // =====================================================
// //         // SUBJECT LOOP
// //         // =====================================================

// //         for (MarksAssessment assessment : assessments) {

// //             // -------------------------------------------------
// //             // STUDENT SUBJECT MARKS
// //             // -------------------------------------------------

// //             StudentAssessmentMarks studentMarks =
// //                     studentAssessmentMarksRepository
// //                             .findByMarksAssessmentIdAndStudentId(
// //                                     assessment.getId(),
// //                                     studentId
// //                             )
// //                             .orElseThrow(() ->
// //                                     new RuntimeException(
// //                                             "Marks not found for student ID "
// //                                                     + studentId
// //                                                     + " for subject ID "
// //                                                     + assessment.getSubjectId()
// //                                     )
// //                             );


// //             // -------------------------------------------------
// //             // COMPONENT DEFINITIONS
// //             // -------------------------------------------------

// //             List<AssessmentStructureType> components =
// //                     assessment
// //                             .getAssessmentStructure()
// //                             .getAssessmentTypes()
// //                             .stream()
// //                             .filter(Objects::nonNull)
// //                             .filter(component ->
// //                                     Boolean.TRUE.equals(
// //                                             component.getStatus()
// //                                     )
// //                             )
// //                             .toList();


// //             // -------------------------------------------------
// //             // MAX MARKS
// //             // -------------------------------------------------

// //             BigDecimal maxMarks =
// //                     components.stream()
// //                             .map(component ->
// //                                     BigDecimal.valueOf(
// //                                             component.getMaxMarks()
// //                                     )
// //                             )
// //                             .reduce(
// //                                     BigDecimal.ZERO,
// //                                     BigDecimal::add
// //                             );


// //             // -------------------------------------------------
// //             // OBTAINED MARKS
// //             // -------------------------------------------------

// //             BigDecimal obtainedMarks =
// //                     Optional.ofNullable(
// //                             studentMarks.getTotalMarks()
// //                     ).orElse(BigDecimal.ZERO);


// //             // -------------------------------------------------
// //             // PERCENTAGE
// //             // -------------------------------------------------

// //             BigDecimal percentage =
// //                     calculatePercentage(
// //                             obtainedMarks,
// //                             maxMarks
// //                     );


// //             // -------------------------------------------------
// //             // SUBJECT MASTER
// //             // -------------------------------------------------

// //             SubjectMaster subject =
// //                     subjectMasterRepository
// //                             .findById(
// //                                     assessment.getSubjectId()
// //                             )
// //                             .orElse(null);


// //             // -------------------------------------------------
// //             // CREATE RESULT SUBJECT
// //             // -------------------------------------------------

// //             ResultSubject resultSubject =
// //                     ResultSubject.builder()
// //                             .result(result)
// //                             .subjectId(
// //                                     assessment.getSubjectId()
// //                             )
// //                             .subjectName(
// //                                     subject != null
// //                                             ? subject.getSubjectName()
// //                                             : "Unknown Subject"
// //                             )
// //                             .totalMarks(
// //                                     obtainedMarks
// //                             )
// //                             .maxMarks(
// //                                     maxMarks
// //                             )
// //                             .percentage(
// //                                     percentage
// //                             )
// //                             .grade(
// //                                     studentMarks.getGrade()
// //                             )
// //                             .gradePoint(
// //                                     studentMarks.getGradePoint()
// //                             )
// //                             .remark(
// //                                     studentMarks.getRemark()
// //                             )
// //                             .build();


// //             // -------------------------------------------------
// //             // SAVE SUBJECT FIRST
// //             // -------------------------------------------------

// //             resultSubject =
// //                     resultSubjectRepository.save(
// //                             resultSubject
// //                     );


// //             // -------------------------------------------------
// //             // STUDENT COMPONENT MARKS
// //             // -------------------------------------------------

// //             List<StudentAssessmentComponentMarks>
// //                     componentMarks =
// //                     studentAssessmentComponentMarksRepository
// //                             .findByStudentAssessmentMarksId(
// //                                     studentMarks.getId()
// //                             );


// //             // =================================================
// //             // SAVE COMPONENT RESULT
// //             // =================================================

// //             for (AssessmentStructureType component
// //                     : components) {

// //                 StudentAssessmentComponentMarks
// //                         componentMark =
// //                         componentMarks.stream()
// //                                 .filter(item ->
// //                                         item.getAssessmentStructureType()
// //                                                 != null
// //                                         &&
// //                                         item.getAssessmentStructureType()
// //                                                 .getId()
// //                                                 .equals(
// //                                                         component.getId()
// //                                                 )
// //                                 )
// //                                 .findFirst()
// //                                 .orElse(null);


// //                 BigDecimal componentObtained =
// //                         componentMark != null
// //                                 && componentMark.getObtainedMarks() != null
// //                                         ? componentMark
// //                                                 .getObtainedMarks()
// //                                         : BigDecimal.ZERO;


// //                 BigDecimal componentMax =
// //                         BigDecimal.valueOf(
// //                                 component.getMaxMarks()
// //                         );


// //                 // -------------------------------------------------
// //                 // COMPONENT NAME
// //                 // -------------------------------------------------

// //                 String componentName =
// //                         getComponentName(component);


// //                 // -------------------------------------------------
// //                 // RESULT SUBJECT COMPONENT
// //                 // -------------------------------------------------

// //                 ResultComponent
// //                         resultComponent =
// //                         ResultComponent.builder()
// //                                 .resultSubject(
// //                                         resultSubject
// //                                 )
// //                                 .componentId(
// //                                         component.getId()
// //                                 )
// //                                 .componentName(
// //                                         componentName
// //                                 )
// //                                 .maxMarks(
// //                                         componentMax
// //                                 )
// //                                 .obtainedMarks(
// //                                         componentObtained
// //                                 )
// //                                 .build();


// //                 resultSubjectComponentRepository.save(
// //                         resultSubjectComponent
// //                 );
// //             }


// //             // -------------------------------------------------
// //             // ADD TOTAL
// //             // -------------------------------------------------

// //             totalMarks =
// //                     totalMarks.add(
// //                             obtainedMarks
// //                     );

// //             totalMaxMarks =
// //                     totalMaxMarks.add(
// //                             maxMarks
// //                     );
// //         }


// //         // =====================================================
// //         // FINAL PERCENTAGE
// //         // =====================================================

// //         BigDecimal finalPercentage =
// //                 calculatePercentage(
// //                         totalMarks,
// //                         totalMaxMarks
// //                 );


// //         // =====================================================
// //         // FINAL GRADE
// //         // =====================================================

// //         GradeMaster grade =
// //                 findGrade(
// //                         schoolId,
// //                         session,
// //                         finalPercentage.doubleValue()
// //                 );


// //         if (grade == null) {

// //             throw new RuntimeException(
// //                     "Grade not defined for final percentage "
// //                             + finalPercentage
// //             );
// //         }


// //         // =====================================================
// //         // FINAL RESULT DATA
// //         // =====================================================

// //         result.setTotalMarks(
// //                 totalMarks
// //         );

// //         result.setTotalMaxMarks(
// //                 totalMaxMarks
// //         );

// //         result.setPercentage(
// //                 finalPercentage
// //         );

// //         result.setGrade(
// //                 grade.getGrade()
// //         );


// //         if (grade.getGradePoint() != null) {

// //             result.setGradePoint(
// //                     BigDecimal.valueOf(
// //                             grade.getGradePoint()
// //                     )
// //             );
// //         }


// //         result.setRemark(
// //                 grade.getRemarks()
// //         );


// //         // =====================================================
// //         // RANK
// //         // =====================================================

// //         Integer rank =
// //                 calculateRank(
// //                         schoolId,
// //                         session,
// //                         examTermId,
// //                         studentClass,
// //                         section,
// //                         studentId,
// //                         finalPercentage
// //                 );


// //         result.setRank(rank);


// //         // =====================================================
// //         // SAVE FINAL RESULT
// //         // =====================================================

// //         result =
// //                 resultRepository.save(result);


// //         // =====================================================
// //         // RECALCULATE CLASS RANKS
// //         // =====================================================

// //         recalculateClassRanks(
// //                 schoolId,
// //                 session,
// //                 examTermId,
// //                 studentClass,
// //                 section
// //         );


// //         // =====================================================
// //         // RETURN RESPONSE
// //         // =====================================================

// //         return buildResponse(result);
// //     }


// //     // =========================================================
// //     // CALCULATE PERCENTAGE
// //     // =========================================================

// //     private BigDecimal calculatePercentage(
// //             BigDecimal obtained,
// //             BigDecimal maximum) {

// //         if (maximum == null
// //                 || maximum.compareTo(
// //                         BigDecimal.ZERO
// //                 ) <= 0) {

// //             return BigDecimal.ZERO;
// //         }

// //         return obtained
// //                 .multiply(
// //                         BigDecimal.valueOf(100)
// //                 )
// //                 .divide(
// //                         maximum,
// //                         2,
// //                         RoundingMode.HALF_UP
// //                 );
// //     }


// //     // =========================================================
// //     // FIND GRADE
// //     // =========================================================

// //     private GradeMaster findGrade(
// //             Long schoolId,
// //             Sessions session,
// //             double percentage) {

// //         List<GradeMaster> grades =
// //                 gradeMasterRepository
// //                         .findBySchoolIdAndSessionAndStatusTrueOrderByMinPercentageDesc(
// //                                 schoolId,
// //                                 session
// //                         );

// //         return grades.stream()
// //                 .filter(grade ->
// //                         percentage >=
// //                                 grade.getMinPercentage()
// //                                 &&
// //                         percentage <=
// //                                 grade.getMaxPercentage()
// //                 )
// //                 .findFirst()
// //                 .orElse(null);
// //     }


// //     // =========================================================
// //     // CALCULATE RANK
// //     // =========================================================

// //     private Integer calculateRank(
// //             Long schoolId,
// //             Sessions session,
// //             Long examTermId,
// //             Standard studentClass,
// //             Section section,
// //             Long targetStudentId,
// //             BigDecimal targetPercentage) {

// //         List<Result> existingResults =
// //                 resultRepository
// //                         .findBySchoolIdAndSessionAndExamTermIdAndStudentClassAndSection(
// //                                 schoolId,
// //                                 session,
// //                                 examTermId,
// //                                 studentClass,
// //                                 section
// //                         );


// //         int rank = 1;


// //         for (Result result : existingResults) {

// //             if (result.getStudent() == null) {
// //                 continue;
// //             }


// //             if (result.getStudent()
// //                     .getId()
// //                     .equals(targetStudentId)) {

// //                 continue;
// //             }


// //             if (result.getStatus()
// //                     != ResultStatus.PUBLISHED) {

// //                 continue;
// //             }


// //             if (result.getPercentage() != null
// //                     &&
// //                     result.getPercentage()
// //                             .compareTo(
// //                                     targetPercentage
// //                             ) > 0) {

// //                 rank++;
// //             }
// //         }


// //         return rank;
// //     }


// //     // =========================================================
// //     // RECALCULATE CLASS RANK
// //     // =========================================================

// //     private void recalculateClassRanks(

// //             Long schoolId,
// //             Sessions session,
// //             Long examTermId,
// //             Standard studentClass,
// //             Section section) {


// //         List<Result> results =
// //                 resultRepository
// //                         .findBySchoolIdAndSessionAndExamTermIdAndStudentClassAndSection(
// //                                 schoolId,
// //                                 session,
// //                                 examTermId,
// //                                 studentClass,
// //                                 section
// //                         );


// //         List<Result> publishedResults =
// //                 results.stream()
// //                         .filter(result ->
// //                                 result.getStatus()
// //                                         == ResultStatus.PUBLISHED
// //                         )
// //                         .filter(result ->
// //                                 result.getPercentage()
// //                                         != null
// //                         )
// //                         .sorted(
// //                                 Comparator.comparing(
// //                                         Result::getPercentage
// //                                 ).reversed()
// //                         )
// //                         .toList();


// //         BigDecimal previousPercentage = null;

// //         int currentRank = 0;

// //         for (int index = 0;
// //              index < publishedResults.size();
// //              index++) {

// //             Result result =
// //                     publishedResults.get(index);


// //             if (previousPercentage != null
// //                     &&
// //                     previousPercentage.compareTo(
// //                             result.getPercentage()
// //                     ) == 0) {

// //                 // Same marks = same rank

// //             } else {

// //                 currentRank = index + 1;
// //             }


// //             result.setRank(currentRank);

// //             previousPercentage =
// //                     result.getPercentage();


// //             resultRepository.save(result);
// //         }
// //     }


// //     // =========================================================
// //     // GET STUDENT PUBLISHED RESULT
// //     // =========================================================

// //     @Transactional(readOnly = true)
// //     public ResultResponse getStudentResult(

// //             Long schoolId,
// //             Sessions session,
// //             Long examTermId,
// //             Long studentId) {


// //         Result result =
// //                 resultRepository
// //                         .findBySchoolIdAndStudentIdAndSessionAndExamTermId(
// //                                 schoolId,
// //                                 studentId,
// //                                 session,
// //                                 examTermId
// //                         )
// //                         .orElseThrow(() ->
// //                                 new RuntimeException(
// //                                         "Published result not found"
// //                                 )
// //                         );


// //         if (result.getStatus()
// //                 != ResultStatus.PUBLISHED) {

// //             throw new RuntimeException(
// //                     "Result is not published"
// //             );
// //         }


// //         return buildResponse(result);
// //     }


// //     // =========================================================
// //     // GET CLASS RESULTS
// //     // =========================================================

// //     @Transactional(readOnly = true)
// //     public List<ResultResponse> getClassResults(

// //             Long schoolId,
// //             Sessions session,
// //             Long examTermId,
// //             Standard studentClass,
// //             Section section) {


// //         List<Result> results =
// //                 resultRepository
// //                         .findBySchoolIdAndSessionAndExamTermIdAndStudentClassAndSectionAndStatus(
// //                                 schoolId,
// //                                 session,
// //                                 examTermId,
// //                                 studentClass,
// //                                 section,
// //                                 ResultStatus.PUBLISHED
// //                         );


// //         return results.stream()
// //                 .sorted(
// //                         Comparator.comparing(
// //                                 Result::getRank,
// //                                 Comparator.nullsLast(
// //                                         Integer::compareTo
// //                                 )
// //                         )
// //                 )
// //                 .map(this::buildResponse)
// //                 .toList();
// //     }


// //     // =========================================================
// //     // BUILD RESPONSE
// //     // =========================================================

// //     private ResultResponse buildResponse(
// //             Result result) {


// //         List<ResultSubjectResponse> subjects =
// //                 resultSubjectRepository
// //                         .findByResultId(
// //                                 result.getId()
// //                         )
// //                         .stream()
// //                         .map(subject -> {

// //                             /*
// //                              * Components are loaded separately
// //                              * inside ResultSubjectResponse if your
// //                              * DTO supports them.
// //                              */

// //                             return ResultSubjectResponse
// //                                     .builder()
// //                                     .id(
// //                                             subject.getId()
// //                                     )
// //                                     .subjectId(
// //                                             subject.getSubjectId()
// //                                     )
// //                                     .subjectName(
// //                                             subject.getSubjectName()
// //                                     )
// //                                     .totalMarks(
// //                                             subject.getTotalMarks()
// //                                     )
// //                                     .maxMarks(
// //                                             subject.getMaxMarks()
// //                                     )
// //                                     .percentage(
// //                                             subject.getPercentage()
// //                                     )
// //                                     .grade(
// //                                             subject.getGrade()
// //                                     )
// //                                     .gradePoint(
// //                                             subject.getGradePoint()
// //                                     )
// //                                     .remark(
// //                                             subject.getRemark()
// //                                     )
// //                                     .build();
// //                         })
// //                         .toList();


// //         return ResultResponse.builder()
// //                 .id(
// //                         result.getId()
// //                 )
// //                 .schoolId(
// //                         result.getSchoolId()
// //                 )
// //                 .studentId(
// //                         result.getStudent() != null
// //                                 ? result.getStudent().getId()
// //                                 : null
// //                 )
// //                 .admissionNumber(
// //                         result.getAdmissionNumber()
// //                 )
// //                 .studentName(
// //                         result.getStudentName()
// //                 )
// //                 .session(
// //                         result.getSession()
// //                 )
// //                 .examTermId(
// //                         result.getExamTermId()
// //                 )
// //                 .studentClass(
// //                         result.getStudentClass()
// //                 )
// //                 .section(
// //                         result.getSection()
// //                 )
// //                 .totalMarks(
// //                         result.getTotalMarks()
// //                 )
// //                 .totalMaxMarks(
// //                         result.getTotalMaxMarks()
// //                 )
// //                 .percentage(
// //                         result.getPercentage()
// //                 )
// //                 .grade(
// //                         result.getGrade()
// //                 )
// //                 .gradePoint(
// //                         result.getGradePoint()
// //                 )
// //                 .remark(
// //                         result.getRemark()
// //                 )
// //                 .rank(
// //                         result.getRank()
// //                 )
// //                 .status(
// //                         result.getStatus()
// //                 )
// //                 .publishedAt(
// //                         result.getPublishedAt()
// //                 )
// //                 .createdAt(
// //                         result.getCreatedAt()
// //                 )
// //                 .updatedAt(
// //                         result.getUpdatedAt()
// //                 )
// //                 .subjects(
// //                         subjects
// //                 )
// //                 .build();
// //     }


// //     // =========================================================
// //     // COMPONENT NAME
// //     // =========================================================

// //     private String getComponentName(
// //             AssessmentStructureType component) {

// //         /*
// //          * IMPORTANT:
// //          *
// //          * Yahan apne AssessmentStructureType ke actual
// //          * name field ka getter lagana hai.
// //          *
// //          * Example:
// //          *
// //          * return component.getComponentName();
// //          *
// //          * ya
// //          *
// //          * return component.getAssessmentType();
// //          *
// //          * Tumhari entity me exact field jo hai use karo.
// //          */

// //         return "Component " + component.getId();
// //     }


// //     // =========================================================
// //     // STUDENT NAME
// //     // =========================================================

// //     private String getStudentName(
// //             Student student) {

// //         String firstName =
// //                 student.getFirstName() != null
// //                         ? student.getFirstName()
// //                         : "";


// //         String lastName =
// //                 student.getLastName() != null
// //                         ? student.getLastName()
// //                         : "";


// //         return (
// //                 firstName
// //                         + " "
// //                         + lastName
// //         ).trim();
// //     }
// // }



// package com.schoolmanagement.schoolmanagementwebsite.service.Assessment.Result;

// import java.math.BigDecimal;
// import java.math.RoundingMode;
// import java.time.LocalDateTime;
// import java.util.ArrayList;
// import java.util.Comparator;
// import java.util.List;
// import java.util.Objects;
// import java.util.Optional;

// import org.springframework.stereotype.Service;
// import org.springframework.transaction.annotation.Transactional;

// import com.schoolmanagement.schoolmanagementwebsite.dto.Assessment.Result.ResultResponse;
// import com.schoolmanagement.schoolmanagementwebsite.dto.Assessment.Result.ResultSubjectResponse;

// import com.schoolmanagement.schoolmanagementwebsite.entity.Assessment.AssessmentStructureType;
// import com.schoolmanagement.schoolmanagementwebsite.entity.Assessment.GradeMaster;
// import com.schoolmanagement.schoolmanagementwebsite.entity.Assessment.MarksAssessment;
// import com.schoolmanagement.schoolmanagementwebsite.entity.Assessment.Result.Result;
// import com.schoolmanagement.schoolmanagementwebsite.entity.Assessment.Result.ResultComponent;
// import com.schoolmanagement.schoolmanagementwebsite.entity.Assessment.Result.ResultSubject;
// import com.schoolmanagement.schoolmanagementwebsite.entity.Assessment.StudentAssessmentComponentMarks;
// import com.schoolmanagement.schoolmanagementwebsite.entity.Assessment.StudentAssessmentMarks;
// import com.schoolmanagement.schoolmanagementwebsite.entity.Assessment.SubjectManagement.SubjectMaster;
// import com.schoolmanagement.schoolmanagementwebsite.entity.Student;

// import com.schoolmanagement.schoolmanagementwebsite.enums.Assessment.MarksStatus;
// import com.schoolmanagement.schoolmanagementwebsite.enums.Assessment.ResultStatus;
// import com.schoolmanagement.schoolmanagementwebsite.enums.Section;
// import com.schoolmanagement.schoolmanagementwebsite.enums.Sessions;
// import com.schoolmanagement.schoolmanagementwebsite.enums.Standard;

// import com.schoolmanagement.schoolmanagementwebsite.repository.Assessment.GradeMasterRepository;
// import com.schoolmanagement.schoolmanagementwebsite.repository.Assessment.MarksAssessmentRepository;
// import com.schoolmanagement.schoolmanagementwebsite.repository.Assessment.Result.ResultComponentRepository;
// import com.schoolmanagement.schoolmanagementwebsite.repository.Assessment.Result.ResultRepository;
// import com.schoolmanagement.schoolmanagementwebsite.repository.Assessment.Result.ResultSubjectRepository;
// import com.schoolmanagement.schoolmanagementwebsite.repository.Assessment.StudentAssessmentComponentMarksRepository;
// import com.schoolmanagement.schoolmanagementwebsite.repository.Assessment.StudentAssessmentMarksRepository;
// import com.schoolmanagement.schoolmanagementwebsite.repository.Assessment.SubjectManagement.SubjectMasterRepository;
// import com.schoolmanagement.schoolmanagementwebsite.repository.StudentRepository;

// import lombok.RequiredArgsConstructor;

// @Service
// @RequiredArgsConstructor
// @Transactional
// public class ResultService {

//     // =========================================================
//     // REPOSITORIES
//     // =========================================================

//     private final ResultRepository resultRepository;

//     private final ResultSubjectRepository resultSubjectRepository;

//     /*
//      * IMPORTANT:
//      * Tumhare project me entity/repository ResultComponent hai.
//      * Isliye yahan ResultComponentRepository hi use hoga.
//      */
//     private final ResultComponentRepository resultComponentRepository;

//     private final MarksAssessmentRepository marksAssessmentRepository;

//     private final StudentAssessmentMarksRepository studentAssessmentMarksRepository;

//     private final StudentAssessmentComponentMarksRepository
//             studentAssessmentComponentMarksRepository;

//     private final StudentRepository studentRepository;

//     private final SubjectMasterRepository subjectMasterRepository;

//     private final GradeMasterRepository gradeMasterRepository;


//     // =========================================================
//     // PUBLISH SINGLE STUDENT RESULT
//     // =========================================================

//     public ResultResponse publishStudentResult(
//             Long schoolId,
//             Sessions session,
//             Long examTermId,
//             Standard studentClass,
//             Section section,
//             Long studentId) {

//         // =====================================================
//         // 1. FIND STUDENT
//         // =====================================================

//         Student student =
//                 studentRepository
//                         .findById(studentId)
//                         .orElseThrow(() ->
//                                 new RuntimeException(
//                                         "Student not found: " + studentId
//                                 )
//                         );


//         // =====================================================
//         // 2. FIND ALL SUBJECT ASSESSMENTS
//         // =====================================================

//         List<MarksAssessment> assessments =
//                 marksAssessmentRepository
//                         .findBySchoolIdAndSessionAndExamTermIdAndStudentClassAndSection(
//                                 schoolId,
//                                 session,
//                                 examTermId,
//                                 studentClass,
//                                 section
//                         );

//         if (assessments == null || assessments.isEmpty()) {

//             throw new RuntimeException(
//                     "No marks found for selected class and section"
//             );
//         }


//         // =====================================================
//         // 3. VERIFY ALL SUBJECTS
//         // =====================================================

//         for (MarksAssessment assessment : assessments) {

//             if (assessment.getStatus() != MarksStatus.VERIFIED) {

//                 throw new RuntimeException(
//                         "Result cannot be published. Subject ID "
//                                 + assessment.getSubjectId()
//                                 + " is not VERIFIED. Current status: "
//                                 + assessment.getStatus()
//                 );
//             }
//         }


//         // =====================================================
//         // 4. FIND EXISTING RESULT
//         // =====================================================

//         Result result =
//                 resultRepository
//                         .findBySchoolIdAndStudentIdAndSessionAndExamTermId(
//                                 schoolId,
//                                 studentId,
//                                 session,
//                                 examTermId
//                         )
//                         .orElse(null);


//         // =====================================================
//         // 5. CHECK ALREADY PUBLISHED
//         // =====================================================

//         if (result != null
//                 && result.getStatus() == ResultStatus.PUBLISHED) {

//             throw new RuntimeException(
//                     "Result is already published for this student"
//             );
//         }


//         // =====================================================
//         // 6. CREATE / UPDATE RESULT
//         // =====================================================

//         if (result == null) {

//             result =
//                     Result.builder()
//                             .schoolId(schoolId)
//                             .student(student)
//                             .admissionNumber(
//                                     student.getAdmissionNumber()
//                             )
//                             .studentName(
//                                     getStudentName(student)
//                             )
//                             .session(session)
//                             .examTermId(examTermId)
//                             .studentClass(studentClass)
//                             .section(section)
//                             .status(ResultStatus.PUBLISHED)
//                             .publishedAt(
//                                     LocalDateTime.now()
//                             )
//                             .build();

//         } else {

//             // =================================================
//             // DELETE OLD RESULT COMPONENTS
//             // =================================================

//             List<ResultSubject> oldSubjects =
//                     resultSubjectRepository
//                             .findByResultId(
//                                     result.getId()
//                             );

//             for (ResultSubject oldSubject : oldSubjects) {

//                 /*
//                  * IMPORTANT:
//                  * ResultComponentRepository use ho raha hai.
//                  */
//                 resultComponentRepository
//                         .deleteByResultSubjectId(
//                                 oldSubject.getId()
//                         );
//             }


//             // =================================================
//             // DELETE OLD RESULT SUBJECTS
//             // =================================================

//             resultSubjectRepository
//                     .deleteByResultId(
//                             result.getId()
//                     );


//             // =================================================
//             // UPDATE RESULT
//             // =================================================

//             result.setStudent(student);

//             result.setAdmissionNumber(
//                     student.getAdmissionNumber()
//             );

//             result.setStudentName(
//                     getStudentName(student)
//             );

//             result.setSession(session);

//             result.setExamTermId(examTermId);

//             result.setStudentClass(studentClass);

//             result.setSection(section);

//             result.setStatus(
//                     ResultStatus.PUBLISHED
//             );

//             result.setPublishedAt(
//                     LocalDateTime.now()
//             );
//         }


//         // =====================================================
//         // 7. SAVE RESULT
//         // =====================================================

//         result =
//                 resultRepository.save(result);


//         // =====================================================
//         // 8. TOTAL VARIABLES
//         // =====================================================

//         BigDecimal totalMarks =
//                 BigDecimal.ZERO;

//         BigDecimal totalMaxMarks =
//                 BigDecimal.ZERO;


//         // =====================================================
//         // 9. SUBJECT LOOP
//         // =====================================================

//         for (MarksAssessment assessment : assessments) {

//             // =================================================
//             // FIND STUDENT SUBJECT MARKS
//             // =================================================

//             StudentAssessmentMarks studentMarks =
//                     studentAssessmentMarksRepository
//                             .findByMarksAssessmentIdAndStudentId(
//                                     assessment.getId(),
//                                     studentId
//                             )
//                             .orElseThrow(() ->
//                                     new RuntimeException(
//                                             "Marks not found for student ID "
//                                                     + studentId
//                                                     + " for subject ID "
//                                                     + assessment.getSubjectId()
//                                     )
//                             );


//             // =================================================
//             // CHECK ASSESSMENT STRUCTURE
//             // =================================================

//             if (assessment.getAssessmentStructure() == null) {

//                 throw new RuntimeException(
//                         "Assessment structure not found for subject ID "
//                                 + assessment.getSubjectId()
//                 );
//             }


//             // =================================================
//             // GET COMPONENTS
//             // =================================================

//             List<AssessmentStructureType> components =
//                     assessment
//                             .getAssessmentStructure()
//                             .getAssessmentTypes()
//                             .stream()
//                             .filter(Objects::nonNull)
//                             .filter(component ->
//                                     Boolean.TRUE.equals(
//                                             component.getStatus()
//                                     )
//                             )
//                             .toList();


//             // =================================================
//             // CALCULATE SUBJECT MAX MARKS
//             // =================================================

//             BigDecimal maxMarks =
//                     components
//                             .stream()
//                             .map(component ->
//                                     BigDecimal.valueOf(
//                                             component.getMaxMarks()
//                                     )
//                             )
//                             .reduce(
//                                     BigDecimal.ZERO,
//                                     BigDecimal::add
//                             );


//             // =================================================
//             // GET SUBJECT OBTAINED MARKS
//             // =================================================

//             BigDecimal obtainedMarks =
//                     Optional
//                             .ofNullable(
//                                     studentMarks.getTotalMarks()
//                             )
//                             .orElse(
//                                     BigDecimal.ZERO
//                             );


//             // =================================================
//             // SUBJECT PERCENTAGE
//             // =================================================

//             BigDecimal percentage =
//                     calculatePercentage(
//                             obtainedMarks,
//                             maxMarks
//                     );


//             // =================================================
//             // FIND SUBJECT
//             // =================================================

//             SubjectMaster subject =
//                     subjectMasterRepository
//                             .findById(
//                                     assessment.getSubjectId()
//                             )
//                             .orElse(null);


//             // =================================================
//             // CREATE RESULT SUBJECT
//             // =================================================

//             ResultSubject resultSubject =
//                     ResultSubject
//                             .builder()
//                             .result(result)
//                             .subjectId(
//                                     assessment.getSubjectId()
//                             )
//                             .subjectName(
//                                     subject != null
//                                             ? subject.getSubjectName()
//                                             : "Unknown Subject"
//                             )
//                             .totalMarks(
//                                     obtainedMarks
//                             )
//                             .maxMarks(
//                                     maxMarks
//                             )
//                             .percentage(
//                                     percentage
//                             )
//                             .grade(
//                                     studentMarks.getGrade()
//                             )
//                             .gradePoint(
//                                     studentMarks.getGradePoint()
//                             )
//                             .remark(
//                                     studentMarks.getRemark()
//                             )
//                             .build();


//             // =================================================
//             // SAVE RESULT SUBJECT
//             // =================================================

//             resultSubject =
//                     resultSubjectRepository.save(
//                             resultSubject
//                     );


//             // =================================================
//             // FIND STUDENT COMPONENT MARKS
//             // =================================================

//             List<StudentAssessmentComponentMarks> componentMarks =
//                     studentAssessmentComponentMarksRepository
//                             .findByStudentAssessmentMarksId(
//                                     studentMarks.getId()
//                             );


//             // =================================================
//             // SAVE RESULT COMPONENTS
//             // =================================================

//             for (AssessmentStructureType component : components) {

//                 // =============================================
//                 // FIND COMPONENT MARK
//                 // =============================================

//                 StudentAssessmentComponentMarks componentMark =
//                         componentMarks
//                                 .stream()
//                                 .filter(item ->
//                                         item.getAssessmentStructureType() != null
//                                                 &&
//                                         item.getAssessmentStructureType()
//                                                 .getId()
//                                                 .equals(
//                                                         component.getId()
//                                                 )
//                                 )
//                                 .findFirst()
//                                 .orElse(null);


//                 // =============================================
//                 // COMPONENT OBTAINED MARKS
//                 // =============================================

//                 BigDecimal componentObtained =
//                         componentMark != null
//                                 &&
//                                 componentMark.getObtainedMarks() != null
//                                         ? componentMark
//                                                 .getObtainedMarks()
//                                         : BigDecimal.ZERO;


//                 // =============================================
//                 // COMPONENT MAX MARKS
//                 // =============================================

//                 BigDecimal componentMax =
//                         BigDecimal.valueOf(
//                                 component.getMaxMarks()
//                         );


//                 // =============================================
//                 // COMPONENT PERCENTAGE
//                 // =============================================

//                 BigDecimal componentPercentage =
//                         calculatePercentage(
//                                 componentObtained,
//                                 componentMax
//                         );


//                 // =============================================
//                 // COMPONENT NAME
//                 // =============================================

//                 String componentName =
//                         getComponentName(component);


//                 // =============================================
//                 // CREATE RESULT COMPONENT
//                 // =============================================

//                 ResultComponent resultComponent =
//                         ResultComponent
//                                 .builder()
//                                 .resultSubject(
//                                         resultSubject
//                                 )
//                                 .componentId(
//                                         component.getId()
//                                 )
//                                 .componentName(
//                                         componentName
//                                 )
//                                 .maxMarks(
//                                         componentMax
//                                 )
//                                 .obtainedMarks(
//                                         componentObtained
//                                 )
//                                 .percentage(
//                                         componentPercentage
//                                 )
//                                 .grade(
//                                         null
//                                 )
//                                 .gradePoint(
//                                         null
//                                 )
//                                 .status(
//                                         "PUBLISHED"
//                                 )
//                                 .build();


//                 // =============================================
//                 // SAVE RESULT COMPONENT
//                 // =============================================

//                 resultComponentRepository.save(
//                         resultComponent
//                 );
//             }


//             // =================================================
//             // ADD SUBJECT TOTAL
//             // =================================================

//             totalMarks =
//                     totalMarks.add(
//                             obtainedMarks
//                     );

//             totalMaxMarks =
//                     totalMaxMarks.add(
//                             maxMarks
//                     );
//         }


//         // =====================================================
//         // 10. FINAL PERCENTAGE
//         // =====================================================

//         BigDecimal finalPercentage =
//                 calculatePercentage(
//                         totalMarks,
//                         totalMaxMarks
//                 );


//         // =====================================================
//         // 11. FINAL GRADE
//         // =====================================================

//         GradeMaster grade =
//                 findGrade(
//                         schoolId,
//                         session,
//                         finalPercentage.doubleValue()
//                 );

//         if (grade == null) {

//             throw new RuntimeException(
//                     "Grade not defined for final percentage "
//                             + finalPercentage
//             );
//         }


//         // =====================================================
//         // 12. SET FINAL RESULT DATA
//         // =====================================================

//         result.setTotalMarks(
//                 totalMarks
//         );

//         result.setTotalMaxMarks(
//                 totalMaxMarks
//         );

//         result.setPercentage(
//                 finalPercentage
//         );

//         result.setGrade(
//                 grade.getGrade()
//         );


//         if (grade.getGradePoint() != null) {

//             result.setGradePoint(
//                     BigDecimal.valueOf(
//                             grade.getGradePoint()
//                     )
//             );
//         } else {

//             result.setGradePoint(null);
//         }


//         result.setRemark(
//                 grade.getRemarks()
//         );


//         // =====================================================
//         // 13. CALCULATE RANK
//         // =====================================================

//         Integer rank =
//                 calculateRank(
//                         schoolId,
//                         session,
//                         examTermId,
//                         studentClass,
//                         section,
//                         studentId,
//                         finalPercentage
//                 );

//         result.setRank(rank);


//         // =====================================================
//         // 14. SAVE FINAL RESULT
//         // =====================================================

//         result =
//                 resultRepository.save(result);


//         // =====================================================
//         // 15. RECALCULATE CLASS RANKS
//         // =====================================================

//         recalculateClassRanks(
//                 schoolId,
//                 session,
//                 examTermId,
//                 studentClass,
//                 section
//         );


//         // =====================================================
//         // 16. RETURN RESPONSE
//         // =====================================================

//         return buildResponse(result);
//     }


//     // =========================================================
//     // CALCULATE PERCENTAGE
//     // =========================================================

//     private BigDecimal calculatePercentage(
//             BigDecimal obtained,
//             BigDecimal maximum) {

//         if (obtained == null) {
//             obtained = BigDecimal.ZERO;
//         }

//         if (maximum == null
//                 ||
//                 maximum.compareTo(
//                         BigDecimal.ZERO
//                 ) <= 0) {

//             return BigDecimal.ZERO;
//         }

//         return obtained
//                 .multiply(
//                         BigDecimal.valueOf(100)
//                 )
//                 .divide(
//                         maximum,
//                         2,
//                         RoundingMode.HALF_UP
//                 );
//     }


//     // =========================================================
//     // FIND GRADE
//     // =========================================================

//     private GradeMaster findGrade(
//             Long schoolId,
//             Sessions session,
//             double percentage) {

//         List<GradeMaster> grades =
//                 gradeMasterRepository
//                         .findBySchoolIdAndSessionAndStatusTrueOrderByMinPercentageDesc(
//                                 schoolId,
//                                 session
//                         );

//         return grades
//                 .stream()
//                 .filter(grade ->
//                         percentage >=
//                                 grade.getMinPercentage()
//                                 &&
//                         percentage <=
//                                 grade.getMaxPercentage()
//                 )
//                 .findFirst()
//                 .orElse(null);
//     }


//     // =========================================================
//     // CALCULATE RANK
//     // =========================================================

//     private Integer calculateRank(
//             Long schoolId,
//             Sessions session,
//             Long examTermId,
//             Standard studentClass,
//             Section section,
//             Long targetStudentId,
//             BigDecimal targetPercentage) {

//         List<Result> existingResults =
//                 resultRepository
//                         .findBySchoolIdAndSessionAndExamTermIdAndStudentClassAndSection(
//                                 schoolId,
//                                 session,
//                                 examTermId,
//                                 studentClass,
//                                 section
//                         );

//         int rank = 1;


//         for (Result existingResult : existingResults) {

//             if (existingResult.getStudent() == null) {
//                 continue;
//             }


//             if (existingResult
//                     .getStudent()
//                     .getId()
//                     .equals(targetStudentId)) {

//                 continue;
//             }


//             if (existingResult.getStatus()
//                     != ResultStatus.PUBLISHED) {

//                 continue;
//             }


//             if (existingResult.getPercentage() != null
//                     &&
//                     existingResult.getPercentage()
//                             .compareTo(
//                                     targetPercentage
//                             ) > 0) {

//                 rank++;
//             }
//         }


//         return rank;
//     }


//     // =========================================================
//     // RECALCULATE CLASS RANKS
//     // =========================================================

//     private void recalculateClassRanks(
//             Long schoolId,
//             Sessions session,
//             Long examTermId,
//             Standard studentClass,
//             Section section) {

//         List<Result> results =
//                 resultRepository
//                         .findBySchoolIdAndSessionAndExamTermIdAndStudentClassAndSection(
//                                 schoolId,
//                                 session,
//                                 examTermId,
//                                 studentClass,
//                                 section
//                         );


//         List<Result> publishedResults =
//                 results
//                         .stream()
//                         .filter(result ->
//                                 result.getStatus()
//                                         == ResultStatus.PUBLISHED
//                         )
//                         .filter(result ->
//                                 result.getPercentage() != null
//                         )
//                         .sorted(
//                                 Comparator
//                                         .comparing(
//                                                 Result::getPercentage
//                                         )
//                                         .reversed()
//                         )
//                         .toList();


//         BigDecimal previousPercentage = null;

//         int currentRank = 0;


//         for (int index = 0;
//              index < publishedResults.size();
//              index++) {

//             Result result =
//                     publishedResults.get(index);


//             if (previousPercentage != null
//                     &&
//                     previousPercentage.compareTo(
//                             result.getPercentage()
//                     ) == 0) {

//                 /*
//                  * Same percentage:
//                  * Same rank
//                  */

//             } else {

//                 currentRank =
//                         index + 1;
//             }


//             result.setRank(
//                     currentRank
//             );

//             previousPercentage =
//                     result.getPercentage();

//             resultRepository.save(
//                     result
//             );
//         }
//     }


//     // =========================================================
//     // GET STUDENT PUBLISHED RESULT
//     // =========================================================

//     @Transactional(readOnly = true)
//     public ResultResponse getStudentResult(
//             Long schoolId,
//             Sessions session,
//             Long examTermId,
//             Long studentId) {

//         Result result =
//                 resultRepository
//                         .findBySchoolIdAndStudentIdAndSessionAndExamTermId(
//                                 schoolId,
//                                 studentId,
//                                 session,
//                                 examTermId
//                         )
//                         .orElseThrow(() ->
//                                 new RuntimeException(
//                                         "Published result not found"
//                                 )
//                         );


//         if (result.getStatus()
//                 != ResultStatus.PUBLISHED) {

//             throw new RuntimeException(
//                     "Result is not published"
//             );
//         }


//         return buildResponse(result);
//     }


//     // =========================================================
//     // GET CLASS RESULTS
//     // =========================================================

//     @Transactional(readOnly = true)
//     public List<ResultResponse> getClassResults(
//             Long schoolId,
//             Sessions session,
//             Long examTermId,
//             Standard studentClass,
//             Section section) {

//         List<Result> results =
//                 resultRepository
//                         .findBySchoolIdAndSessionAndExamTermIdAndStudentClassAndSectionAndStatus(
//                                 schoolId,
//                                 session,
//                                 examTermId,
//                                 studentClass,
//                                 section,
//                                 ResultStatus.PUBLISHED
//                         );


//         return results
//                 .stream()
//                 .sorted(
//                         Comparator.comparing(
//                                 Result::getRank,
//                                 Comparator.nullsLast(
//                                         Integer::compareTo
//                                 )
//                         )
//                 )
//                 .map(this::buildResponse)
//                 .toList();
//     }


//     // =========================================================
//     // BUILD RESPONSE
//     // =========================================================

//     private ResultResponse buildResponse(
//             Result result) {

//         List<ResultSubjectResponse> subjects =
//                 resultSubjectRepository
//                         .findByResultId(
//                                 result.getId()
//                         )
//                         .stream()
//                         .map(subject ->

//                                 ResultSubjectResponse
//                                         .builder()
//                                         .id(
//                                                 subject.getId()
//                                         )
//                                         .subjectId(
//                                                 subject.getSubjectId()
//                                         )
//                                         .subjectName(
//                                                 subject.getSubjectName()
//                                         )
//                                         .totalMarks(
//                                                 subject.getTotalMarks()
//                                         )
//                                         .maxMarks(
//                                                 subject.getMaxMarks()
//                                         )
//                                         .percentage(
//                                                 subject.getPercentage()
//                                         )
//                                         .grade(
//                                                 subject.getGrade()
//                                         )
//                                         .gradePoint(
//                                                 subject.getGradePoint()
//                                         )
//                                         .remark(
//                                                 subject.getRemark()
//                                         )
//                                         .build()

//                         )
//                         .toList();


//         return ResultResponse
//                 .builder()
//                 .id(
//                         result.getId()
//                 )
//                 .schoolId(
//                         result.getSchoolId()
//                 )
//                 .studentId(
//                         result.getStudent() != null
//                                 ? result.getStudent().getId()
//                                 : null
//                 )
//                 .admissionNumber(
//                         result.getAdmissionNumber()
//                 )
//                 .studentName(
//                         result.getStudentName()
//                 )
//                 .session(
//                         result.getSession()
//                 )
//                 .examTermId(
//                         result.getExamTermId()
//                 )
//                 .studentClass(
//                         result.getStudentClass()
//                 )
//                 .section(
//                         result.getSection()
//                 )
//                 .totalMarks(
//                         result.getTotalMarks()
//                 )
//                 .totalMaxMarks(
//                         result.getTotalMaxMarks()
//                 )
//                 .percentage(
//                         result.getPercentage()
//                 )
//                 .grade(
//                         result.getGrade()
//                 )
//                 .gradePoint(
//                         result.getGradePoint()
//                 )
//                 .remark(
//                         result.getRemark()
//                 )
//                 .rank(
//                         result.getRank()
//                 )
//                 .status(
//                         result.getStatus()
//                 )
//                 .publishedAt(
//                         result.getPublishedAt()
//                 )
//                 .createdAt(
//                         result.getCreatedAt()
//                 )
//                 .updatedAt(
//                         result.getUpdatedAt()
//                 )
//                 .subjects(
//                         subjects
//                 )
//                 .build();
//     }


//     // =========================================================
//     // COMPONENT NAME
//     // =========================================================

//     private String getComponentName(
//             AssessmentStructureType component) {

//         /*
//          * Agar AssessmentStructureType me component name
//          * ka actual getter available hai to yahan use kar sakte ho.
//          *
//          * Filhaal safe fallback.
//          */

//         return "Component " + component.getId();
//     }


//     // =========================================================
//     // STUDENT NAME
//     // =========================================================

//     private String getStudentName(
//             Student student) {

//         String firstName =
//                 student.getFirstName() != null
//                         ? student.getFirstName()
//                         : "";

//         String lastName =
//                 student.getLastName() != null
//                         ? student.getLastName()
//                         : "";

//         return (
//                 firstName
//                         + " "
//                         + lastName
//         ).trim();
//     }
// }


package com.schoolmanagement.schoolmanagementwebsite.service.Assessment.Result;

import java.math.BigDecimal;
import java.math.RoundingMode;
import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.Comparator;
import java.util.List;
import java.util.Objects;
import java.util.Optional;

import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import com.schoolmanagement.schoolmanagementwebsite.dto.Assessment.Result.ResultComponentResponse;
import com.schoolmanagement.schoolmanagementwebsite.dto.Assessment.Result.ResultResponse;
import com.schoolmanagement.schoolmanagementwebsite.dto.Assessment.Result.ResultSubjectResponse;
import com.schoolmanagement.schoolmanagementwebsite.entity.Assessment.AssessmentStructureType;
import com.schoolmanagement.schoolmanagementwebsite.entity.Assessment.GradeMaster;
import com.schoolmanagement.schoolmanagementwebsite.entity.Assessment.MarksAssessment;
import com.schoolmanagement.schoolmanagementwebsite.entity.Assessment.Result.Result;
import com.schoolmanagement.schoolmanagementwebsite.entity.Assessment.Result.ResultComponent;
import com.schoolmanagement.schoolmanagementwebsite.entity.Assessment.Result.ResultSubject;
import com.schoolmanagement.schoolmanagementwebsite.entity.Assessment.StudentAssessmentComponentMarks;
import com.schoolmanagement.schoolmanagementwebsite.entity.Assessment.StudentAssessmentMarks;
import com.schoolmanagement.schoolmanagementwebsite.entity.Assessment.SubjectManagement.SubjectMaster;
import com.schoolmanagement.schoolmanagementwebsite.entity.Student;
import com.schoolmanagement.schoolmanagementwebsite.enums.Assessment.MarksStatus;
import com.schoolmanagement.schoolmanagementwebsite.enums.Assessment.ResultStatus;
import com.schoolmanagement.schoolmanagementwebsite.enums.Section;
import com.schoolmanagement.schoolmanagementwebsite.enums.Sessions;
import com.schoolmanagement.schoolmanagementwebsite.enums.Standard;
import com.schoolmanagement.schoolmanagementwebsite.repository.Assessment.GradeMasterRepository;
import com.schoolmanagement.schoolmanagementwebsite.repository.Assessment.MarksAssessmentRepository;
import com.schoolmanagement.schoolmanagementwebsite.repository.Assessment.Result.ResultComponentRepository;
import com.schoolmanagement.schoolmanagementwebsite.repository.Assessment.Result.ResultRepository;
import com.schoolmanagement.schoolmanagementwebsite.repository.Assessment.Result.ResultSubjectRepository;
import com.schoolmanagement.schoolmanagementwebsite.repository.Assessment.StudentAssessmentComponentMarksRepository;
import com.schoolmanagement.schoolmanagementwebsite.repository.Assessment.StudentAssessmentMarksRepository;
import com.schoolmanagement.schoolmanagementwebsite.repository.Assessment.SubjectManagement.SubjectMasterRepository;
import com.schoolmanagement.schoolmanagementwebsite.repository.StudentRepository;

import lombok.RequiredArgsConstructor;

@Service
@RequiredArgsConstructor
@Transactional
public class ResultService {

    private final ResultRepository resultRepository;

    private final ResultSubjectRepository resultSubjectRepository;

    private final ResultComponentRepository resultComponentRepository;

    private final MarksAssessmentRepository marksAssessmentRepository;

    private final StudentAssessmentMarksRepository studentAssessmentMarksRepository;

    private final StudentAssessmentComponentMarksRepository
            studentAssessmentComponentMarksRepository;

    private final StudentRepository studentRepository;

    private final SubjectMasterRepository subjectMasterRepository;

    private final GradeMasterRepository gradeMasterRepository;


    // =========================================================
    // PUBLISH SINGLE STUDENT RESULT
    // =========================================================

    public ResultResponse publishStudentResult(

            Long schoolId,
            Sessions session,
            Long examTermId,
            Standard studentClass,
            Section section,
            Long studentId) {

        // =====================================================
        // STUDENT
        // =====================================================

        Student student = studentRepository
                .findById(studentId)
                .orElseThrow(() ->
                        new RuntimeException(
                                "Student not found: " + studentId
                        )
                );


        // =====================================================
        // SUBJECT ASSESSMENTS
        // =====================================================

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
                    "No marks assessment found for selected class and section"
            );
        }


        // =====================================================
        // VERIFY ALL SUBJECTS
        // =====================================================

        for (MarksAssessment assessment : assessments) {

            if (assessment.getStatus() != MarksStatus.VERIFIED) {

                throw new RuntimeException(
                        "Result cannot be published. Subject ID "
                                + assessment.getSubjectId()
                                + " is not VERIFIED. Current status: "
                                + assessment.getStatus()
                );
            }
        }


        // =====================================================
        // EXISTING RESULT
        // =====================================================

        Result result =
                resultRepository
                        .findBySchoolIdAndStudentIdAndSessionAndExamTermId(
                                schoolId,
                                studentId,
                                session,
                                examTermId
                        )
                        .orElse(null);


        // =====================================================
        // ALREADY PUBLISHED
        // =====================================================

        if (result != null
                && result.getStatus() == ResultStatus.PUBLISHED) {

            throw new RuntimeException(
                    "Result is already published for this student"
            );
        }


        // =====================================================
        // CREATE / UPDATE RESULT
        // =====================================================

        if (result == null) {

            result = Result.builder()
                    .schoolId(schoolId)
                    .student(student)
                    .admissionNumber(
                            student.getAdmissionNumber()
                    )
                    .studentName(
                            getStudentName(student)
                    )
                    .session(session)
                    .examTermId(examTermId)
                    .studentClass(studentClass)
                    .section(section)
                    .status(ResultStatus.PUBLISHED)
                    .publishedAt(LocalDateTime.now())
                    .build();

        } else {

            // -------------------------------------------------
            // DELETE OLD RESULT COMPONENTS
            // -------------------------------------------------

            List<ResultSubject> oldSubjects =
                    resultSubjectRepository.findByResultId(
                            result.getId()
                    );

            for (ResultSubject oldSubject : oldSubjects) {

                resultComponentRepository
                        .deleteByResultSubjectId(
                                oldSubject.getId()
                        );
            }

            // -------------------------------------------------
            // DELETE OLD RESULT SUBJECTS
            // -------------------------------------------------

            resultSubjectRepository
                    .deleteByResultId(result.getId());


            // -------------------------------------------------
            // UPDATE RESULT
            // -------------------------------------------------

            result.setStudent(student);

            result.setAdmissionNumber(
                    student.getAdmissionNumber()
            );

            result.setStudentName(
                    getStudentName(student)
            );

            result.setSession(session);

            result.setExamTermId(examTermId);

            result.setStudentClass(studentClass);

            result.setSection(section);

            result.setStatus(
                    ResultStatus.PUBLISHED
            );

            result.setPublishedAt(
                    LocalDateTime.now()
            );
        }


        // =====================================================
        // SAVE RESULT FIRST
        // =====================================================

        result = resultRepository.save(result);


        // =====================================================
        // FINAL TOTALS
        // =====================================================

        BigDecimal totalMarks =
                BigDecimal.ZERO;

        BigDecimal totalMaxMarks =
                BigDecimal.ZERO;


        List<ResultSubject> resultSubjects =
                new ArrayList<>();


        // =====================================================
        // SUBJECT LOOP
        // =====================================================

        for (MarksAssessment assessment : assessments) {

            // -------------------------------------------------
            // STUDENT SUBJECT MARKS
            // -------------------------------------------------

            StudentAssessmentMarks studentMarks =
                    studentAssessmentMarksRepository
                            .findByMarksAssessmentIdAndStudentId(
                                    assessment.getId(),
                                    studentId
                            )
                            .orElseThrow(() ->
                                    new RuntimeException(
                                            "Marks not found for student ID "
                                                    + studentId
                                                    + " for subject ID "
                                                    + assessment.getSubjectId()
                                    )
                            );


            // -------------------------------------------------
            // ASSESSMENT STRUCTURE
            // -------------------------------------------------

            if (assessment.getAssessmentStructure() == null) {

                throw new RuntimeException(
                        "Assessment structure not found for subject ID "
                                + assessment.getSubjectId()
                );
            }


            // -------------------------------------------------
            // COMPONENTS
            // -------------------------------------------------

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
                                            AssessmentStructureType::getDisplayOrder,
                                            Comparator.nullsLast(
                                                    Integer::compareTo
                                            )
                                    )
                            )
                            .toList();


            // -------------------------------------------------
            // SUBJECT MAX MARKS
            // -------------------------------------------------

            BigDecimal maxMarks =
                    components.stream()
                            .map(component ->
                                    BigDecimal.valueOf(
                                            component.getMaxMarks()
                                    )
                            )
                            .reduce(
                                    BigDecimal.ZERO,
                                    BigDecimal::add
                            );


            // -------------------------------------------------
            // STUDENT TOTAL MARKS
            // -------------------------------------------------

            BigDecimal obtainedMarks =
                    Optional.ofNullable(
                            studentMarks.getTotalMarks()
                    ).orElse(BigDecimal.ZERO);


            // -------------------------------------------------
            // SUBJECT PERCENTAGE
            // -------------------------------------------------

            BigDecimal percentage =
                    calculatePercentage(
                            obtainedMarks,
                            maxMarks
                    );


            // -------------------------------------------------
            // SUBJECT MASTER
            // -------------------------------------------------

            SubjectMaster subject =
                    subjectMasterRepository
                            .findById(
                                    assessment.getSubjectId()
                            )
                            .orElse(null);


            // -------------------------------------------------
            // CREATE RESULT SUBJECT
            // -------------------------------------------------

            ResultSubject resultSubject =
                    ResultSubject.builder()
                            .result(result)
                            .subjectId(
                                    assessment.getSubjectId()
                            )
                            .subjectName(
                                    subject != null
                                            ? subject.getSubjectName()
                                            : "Unknown Subject"
                            )
                            .totalMarks(
                                    obtainedMarks
                            )
                            .maxMarks(
                                    maxMarks
                            )
                            .percentage(
                                    percentage
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


            // -------------------------------------------------
            // SAVE RESULT SUBJECT
            // -------------------------------------------------

            resultSubject =
                    resultSubjectRepository.save(
                            resultSubject
                    );

            resultSubjects.add(resultSubject);


            // -------------------------------------------------
            // STUDENT COMPONENT MARKS
            // -------------------------------------------------

            List<StudentAssessmentComponentMarks>
                    componentMarks =
                    studentAssessmentComponentMarksRepository
                            .findByStudentAssessmentMarksId(
                                    studentMarks.getId()
                            );


            // =================================================
            // COMPONENT LOOP
            // =================================================

            for (AssessmentStructureType component
                    : components) {

                // -------------------------------------------------
                // FIND STUDENT COMPONENT MARK
                // -------------------------------------------------

                StudentAssessmentComponentMarks componentMark =
                        componentMarks.stream()
                                .filter(item ->
                                        item.getAssessmentStructureType()
                                                != null
                                        &&
                                        item.getAssessmentStructureType()
                                                .getId()
                                                .equals(
                                                        component.getId()
                                                )
                                )
                                .findFirst()
                                .orElse(null);


                // -------------------------------------------------
                // OBTAINED MARKS
                // -------------------------------------------------

                BigDecimal componentObtained =
                        componentMark != null
                                && componentMark.getObtainedMarks() != null
                                        ? componentMark
                                                .getObtainedMarks()
                                        : BigDecimal.ZERO;


                // -------------------------------------------------
                // MAX MARKS
                // -------------------------------------------------

                BigDecimal componentMax =
                        component.getMaxMarks() != null
                                ? BigDecimal.valueOf(
                                        component.getMaxMarks()
                                )
                                : BigDecimal.ZERO;


                // -------------------------------------------------
                // COMPONENT PERCENTAGE
                // -------------------------------------------------

                BigDecimal componentPercentage =
                        calculatePercentage(
                                componentObtained,
                                componentMax
                        );


                // -------------------------------------------------
                // COMPONENT GRADE
                // -------------------------------------------------

                GradeMaster componentGrade =
                        findGrade(
                                schoolId,
                                session,
                                componentPercentage.doubleValue()
                        );


                // -------------------------------------------------
                // COMPONENT GRADE VALUE
                // -------------------------------------------------

                String componentGradeValue = null;

                BigDecimal componentGradePoint = null;

                if (componentGrade != null) {

                    componentGradeValue =
                            componentGrade.getGrade();

                    if (componentGrade.getGradePoint() != null) {

                        componentGradePoint =
                                BigDecimal.valueOf(
                                        componentGrade.getGradePoint()
                                );
                    }
                }


                // -------------------------------------------------
                // COMPONENT NAME
                // -------------------------------------------------

                String componentName =
                        getComponentName(component);


                // -------------------------------------------------
                // RESULT COMPONENT
                // -------------------------------------------------

                ResultComponent resultComponent =
                        ResultComponent.builder()
                                .resultSubject(
                                        resultSubject
                                )
                                .componentId(
                                        component.getId()
                                )
                                .componentName(
                                        componentName
                                )
                                .maxMarks(
                                        componentMax
                                )
                                .obtainedMarks(
                                        componentObtained
                                )
                                .percentage(
                                        componentPercentage
                                )
                                .grade(
                                        componentGradeValue
                                )
                                .gradePoint(
                                        componentGradePoint
                                )
                                .status(
                                        "PUBLISHED"
                                )
                                .build();


                // -------------------------------------------------
                // SAVE RESULT COMPONENT
                // -------------------------------------------------

                resultComponentRepository.save(
                        resultComponent
                );
            }


            // =================================================
            // ADD SUBJECT TOTAL
            // =================================================

            totalMarks =
                    totalMarks.add(
                            obtainedMarks
                    );

            totalMaxMarks =
                    totalMaxMarks.add(
                            maxMarks
                    );
        }


        // =====================================================
        // FINAL PERCENTAGE
        // =====================================================

        BigDecimal finalPercentage =
                calculatePercentage(
                        totalMarks,
                        totalMaxMarks
                );


        // =====================================================
        // FINAL GRADE
        // =====================================================

        GradeMaster finalGrade =
                findGrade(
                        schoolId,
                        session,
                        finalPercentage.doubleValue()
                );


        if (finalGrade == null) {

            throw new RuntimeException(
                    "Grade not defined for final percentage "
                            + finalPercentage
            );
        }


        // =====================================================
        // FINAL RESULT DATA
        // =====================================================

        result.setTotalMarks(
                totalMarks
        );

        result.setTotalMaxMarks(
                totalMaxMarks
        );

        result.setPercentage(
                finalPercentage
        );

        result.setGrade(
                finalGrade.getGrade()
        );


        if (finalGrade.getGradePoint() != null) {

            result.setGradePoint(
                    BigDecimal.valueOf(
                            finalGrade.getGradePoint()
                    )
            );
        }

        result.setRemark(
                finalGrade.getRemarks()
        );


        // =====================================================
        // INITIAL RANK
        // =====================================================

        Integer rank =
                calculateRank(
                        schoolId,
                        session,
                        examTermId,
                        studentClass,
                        section,
                        studentId,
                        finalPercentage
                );

        result.setRank(rank);


        // =====================================================
        // SAVE FINAL RESULT
        // =====================================================

        result =
                resultRepository.save(
                        result
                );


        // =====================================================
        // RECALCULATE CLASS RANK
        // =====================================================

        recalculateClassRanks(
                schoolId,
                session,
                examTermId,
                studentClass,
                section
        );


        // =====================================================
        // RELOAD RESULT
        // =====================================================

        result =
                resultRepository
                        .findById(result.getId())
                        .orElse(result);


        // =====================================================
        // RETURN
        // =====================================================

        return buildResponse(result);
    }


    // =========================================================
    // CALCULATE PERCENTAGE
    // =========================================================

    private BigDecimal calculatePercentage(
            BigDecimal obtained,
            BigDecimal maximum) {

        if (obtained == null) {
            obtained = BigDecimal.ZERO;
        }

        if (maximum == null
                || maximum.compareTo(BigDecimal.ZERO) <= 0) {

            return BigDecimal.ZERO;
        }

        return obtained
                .multiply(
                        BigDecimal.valueOf(100)
                )
                .divide(
                        maximum,
                        2,
                        RoundingMode.HALF_UP
                );
    }


    // =========================================================
    // FIND GRADE
    // =========================================================

    private GradeMaster findGrade(
            Long schoolId,
            Sessions session,
            double percentage) {

        List<GradeMaster> grades =
                gradeMasterRepository
                        .findBySchoolIdAndSessionAndStatusTrueOrderByMinPercentageDesc(
                                schoolId,
                                session
                        );

        if (grades == null || grades.isEmpty()) {
            return null;
        }

        return grades.stream()
                .filter(Objects::nonNull)
                .filter(grade ->
                        percentage >=
                                grade.getMinPercentage()
                                &&
                        percentage <=
                                grade.getMaxPercentage()
                )
                .findFirst()
                .orElse(null);
    }


    // =========================================================
    // CALCULATE RANK
    // =========================================================

    private Integer calculateRank(
            Long schoolId,
            Sessions session,
            Long examTermId,
            Standard studentClass,
            Section section,
            Long targetStudentId,
            BigDecimal targetPercentage) {

        List<Result> existingResults =
                resultRepository
                        .findBySchoolIdAndSessionAndExamTermIdAndStudentClassAndSection(
                                schoolId,
                                session,
                                examTermId,
                                studentClass,
                                section
                        );

        int rank = 1;

        for (Result result : existingResults) {

            if (result.getStudent() == null) {
                continue;
            }

            if (result.getStudent()
                    .getId()
                    .equals(targetStudentId)) {

                continue;
            }

            if (result.getStatus()
                    != ResultStatus.PUBLISHED) {

                continue;
            }

            if (result.getPercentage() != null
                    &&
                    result.getPercentage()
                            .compareTo(
                                    targetPercentage
                            ) > 0) {

                rank++;
            }
        }

        return rank;
    }


    // =========================================================
    // RECALCULATE CLASS RANK
    // =========================================================

    private void recalculateClassRanks(

            Long schoolId,
            Sessions session,
            Long examTermId,
            Standard studentClass,
            Section section) {

        List<Result> results =
                resultRepository
                        .findBySchoolIdAndSessionAndExamTermIdAndStudentClassAndSection(
                                schoolId,
                                session,
                                examTermId,
                                studentClass,
                                section
                        );

        List<Result> publishedResults =
                results.stream()
                        .filter(Objects::nonNull)
                        .filter(result ->
                                result.getStatus()
                                        == ResultStatus.PUBLISHED
                        )
                        .filter(result ->
                                result.getPercentage()
                                        != null
                        )
                        .sorted(
                                Comparator.comparing(
                                        Result::getPercentage
                                ).reversed()
                        )
                        .toList();


        BigDecimal previousPercentage = null;

        int currentRank = 0;


        for (int index = 0;
             index < publishedResults.size();
             index++) {

            Result result =
                    publishedResults.get(index);


            if (previousPercentage != null
                    &&
                    previousPercentage.compareTo(
                            result.getPercentage()
                    ) == 0) {

                // Same percentage = same rank

            } else {

                currentRank =
                        index + 1;
            }


            result.setRank(
                    currentRank
            );

            previousPercentage =
                    result.getPercentage();

            resultRepository.save(
                    result
            );
        }
    }


    // =========================================================
    // GET STUDENT PUBLISHED RESULT
    // =========================================================

    @Transactional(readOnly = true)
    public ResultResponse getStudentResult(

            Long schoolId,
            Sessions session,
            Long examTermId,
            Long studentId) {

        Result result =
                resultRepository
                        .findBySchoolIdAndStudentIdAndSessionAndExamTermId(
                                schoolId,
                                studentId,
                                session,
                                examTermId
                        )
                        .orElseThrow(() ->
                                new RuntimeException(
                                        "Published result not found"
                                )
                        );


        if (result.getStatus()
                != ResultStatus.PUBLISHED) {

            throw new RuntimeException(
                    "Result is not published"
            );
        }


        return buildResponse(result);
    }


    // =========================================================
    // GET CLASS RESULTS
    // =========================================================

    @Transactional(readOnly = true)
    public List<ResultResponse> getClassResults(

            Long schoolId,
            Sessions session,
            Long examTermId,
            Standard studentClass,
            Section section) {

        List<Result> results =
                resultRepository
                        .findBySchoolIdAndSessionAndExamTermIdAndStudentClassAndSectionAndStatus(
                                schoolId,
                                session,
                                examTermId,
                                studentClass,
                                section,
                                ResultStatus.PUBLISHED
                        );


        return results.stream()
                .sorted(
                        Comparator.comparing(
                                Result::getRank,
                                Comparator.nullsLast(
                                        Integer::compareTo
                                )
                        )
                )
                .map(this::buildResponse)
                .toList();
    }


    // =========================================================
    // BUILD RESPONSE
    // =========================================================

private ResultResponse buildResponse(Result result) {

    List<ResultSubjectResponse> subjects =
            resultSubjectRepository
                    .findByResultId(result.getId())
                    .stream()
                    .map(subject -> {

                        List<ResultComponentResponse> components =
                                resultComponentRepository
                                        .findByResultSubjectId(
                                                subject.getId()
                                        )
                                        .stream()
                                        .map(component ->
                                                ResultComponentResponse
                                                        .builder()
                                                        .id(
                                                                component.getId()
                                                        )
                                                        .componentId(
                                                                component.getComponentId()
                                                        )
                                                        .componentName(
                                                                component.getComponentName()
                                                        )
                                                        .maxMarks(
                                                                component.getMaxMarks()
                                                        )
                                                        .obtainedMarks(
                                                                component.getObtainedMarks()
                                                        )
                                                        .percentage(
                                                                component.getPercentage()
                                                        )
                                                        .grade(
                                                                component.getGrade()
                                                        )
                                                        .gradePoint(
                                                                component.getGradePoint()
                                                        )
                                                        .status(
                                                                component.getStatus()
                                                        )
                                                        .build()
                                        )
                                        .toList();

                        return ResultSubjectResponse
                                .builder()
                                .id(
                                        subject.getId()
                                )
                                .subjectId(
                                        subject.getSubjectId()
                                )
                                .subjectName(
                                        subject.getSubjectName()
                                )
                                .totalMarks(
                                        subject.getTotalMarks()
                                )
                                .maxMarks(
                                        subject.getMaxMarks()
                                )
                                .percentage(
                                        subject.getPercentage()
                                )
                                .grade(
                                        subject.getGrade()
                                )
                                .gradePoint(
                                        subject.getGradePoint()
                                )
                                .remark(
                                        subject.getRemark()
                                )
                                .components(
                                        components
                                )
                                .build();
                    })
                    .toList();

    return ResultResponse
            .builder()
            .id(
                    result.getId()
            )
            .schoolId(
                    result.getSchoolId()
            )
            .studentId(
                    result.getStudent() != null
                            ? result.getStudent().getId()
                            : null
            )
            .admissionNumber(
                    result.getAdmissionNumber()
            )
            .studentName(
                    result.getStudentName()
            )
            .session(
                    result.getSession()
            )
            .examTermId(
                    result.getExamTermId()
            )
            .studentClass(
                    result.getStudentClass()
            )
            .section(
                    result.getSection()
            )
            .totalMarks(
                    result.getTotalMarks()
            )
            .totalMaxMarks(
                    result.getTotalMaxMarks()
            )
            .percentage(
                    result.getPercentage()
            )
            .grade(
                    result.getGrade()
            )
            .gradePoint(
                    result.getGradePoint()
            )
            .remark(
                    result.getRemark()
            )
            .rank(
                    result.getRank()
            )
            .status(
                    result.getStatus()
            )
            .publishedAt(
                    result.getPublishedAt()
            )
            .createdAt(
                    result.getCreatedAt()
            )
            .updatedAt(
                    result.getUpdatedAt()
            )
            .subjects(
                    subjects
            )
            .build();
}


    // =========================================================
    // GET COMPONENT NAME
    // =========================================================

    private String getComponentName(
            AssessmentStructureType component) {

        if (component == null) {
            return "Unknown Component";
        }

        if (component.getAssessmentType() == null) {
            return "Component " + component.getId();
        }

        /*
         * AssessmentStructureType me actual component name
         * AssessmentType entity ke andar hai.
         *
         * Hum yahan reflection use kar rahe hain taaki
         * AssessmentType ke exact getter ka naam guess na karna pade.
         */

        Object assessmentType =
                component.getAssessmentType();

        String[] possibleGetters = {
                "getAssessmentTypeName",
                "getTypeName",
                "getName",
                "getAssessmentName",
                "getTitle",
                "getDisplayName"
        };

        for (String getterName : possibleGetters) {

            try {

                Object value =
                        assessmentType
                                .getClass()
                                .getMethod(getterName)
                                .invoke(assessmentType);

                if (value != null
                        && !value.toString().trim().isEmpty()) {

                    return value.toString().trim();
                }

            } catch (Exception ignored) {
                // Try next possible getter
            }
        }

        /*
         * Last fallback.
         */
        return assessmentType.toString();
    }


    // =========================================================
    // STUDENT NAME
    // =========================================================

    private String getStudentName(
            Student student) {

        String firstName =
                student.getFirstName() != null
                        ? student.getFirstName()
                        : "";

        String lastName =
                student.getLastName() != null
                        ? student.getLastName()
                        : "";

        return (
                firstName
                        + " "
                        + lastName
        ).trim();
    }

   public ResultResponse getStudentResultByAdmissionNumber(
        Long schoolId,
        String session,
        Long examTermId,
        String admissionNumber) {

    Sessions sessions = Sessions.fromValue(session);

    Result result =
            resultRepository
                    .findBySchoolIdAndSessionAndExamTermIdAndAdmissionNumber(
                            schoolId,
                            sessions,
                            examTermId,
                            admissionNumber
                    )
                    .orElseThrow(() ->
                            new RuntimeException(
                                    "Result not found for admission number: "
                                            + admissionNumber
                            )
                    );

    return buildResponse(result);
}
}