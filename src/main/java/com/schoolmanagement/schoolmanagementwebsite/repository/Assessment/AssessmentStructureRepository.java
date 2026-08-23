package com.schoolmanagement.schoolmanagementwebsite.repository.Assessment;

import java.util.Optional;

import org.springframework.data.jpa.repository.JpaRepository;

import com.schoolmanagement.schoolmanagementwebsite.entity.Assessment.AssessmentStructure;
import com.schoolmanagement.schoolmanagementwebsite.enums.Assessment.ExamTermType;
import com.schoolmanagement.schoolmanagementwebsite.enums.Sessions;
import com.schoolmanagement.schoolmanagementwebsite.enums.Standard;

public interface AssessmentStructureRepository
        extends JpaRepository<AssessmentStructure, Long> {

    Optional<AssessmentStructure> findBySchoolIdAndSessionAndExamTermAndStudentClassAndSubjectId(
            Long schoolId,
            Sessions session,
            String examTerm,
            Standard studentClass,
            Long subjectId
    );

    Optional<AssessmentStructure> findBySchoolIdAndSessionAndStudentClassAndSubjectId(Long schoolId,
            Sessions session,
            Standard studentClass,
            Long subjectId);
}
