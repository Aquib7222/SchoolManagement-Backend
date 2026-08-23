package com.schoolmanagement.schoolmanagementwebsite.repository.Assessment;

import java.util.List;
import java.util.Optional;

import org.springframework.data.jpa.repository.JpaRepository;

import com.schoolmanagement.schoolmanagementwebsite.entity.Assessment.MarksAssessment;
import com.schoolmanagement.schoolmanagementwebsite.enums.Section;
import com.schoolmanagement.schoolmanagementwebsite.enums.Sessions;
import com.schoolmanagement.schoolmanagementwebsite.enums.Standard;
import com.schoolmanagement.schoolmanagementwebsite.enums.Assessment.MarksStatus;

public interface MarksAssessmentRepository
        extends JpaRepository<MarksAssessment, Long> {

    Optional<MarksAssessment>
    findBySchoolIdAndSessionAndExamTermIdAndStudentClassAndSectionAndSubjectId(
            Long schoolId,
            Sessions session,
            Long examTermId,
            Standard studentClass,
            Section section,
            Long subjectId
    );

    List<MarksAssessment>
findBySchoolIdAndSessionAndExamTermIdAndStudentClassAndSection(
        Long schoolId,
        Sessions session,
        Long examTermId,
        Standard studentClass,
        Section section
);

List<MarksAssessment>
findBySchoolIdAndSessionAndExamTermIdAndStudentClassAndSectionAndStatus(
        Long schoolId,
        Sessions session,
        Long examTermId,
        Standard studentClass,
        Section section,
        MarksStatus status
);
}