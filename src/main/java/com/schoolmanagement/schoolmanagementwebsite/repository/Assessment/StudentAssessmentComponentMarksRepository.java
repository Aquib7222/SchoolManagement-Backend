package com.schoolmanagement.schoolmanagementwebsite.repository.Assessment;

import java.util.List;
import java.util.Optional;

import org.springframework.data.jpa.repository.JpaRepository;

import com.schoolmanagement.schoolmanagementwebsite.entity.Assessment.StudentAssessmentComponentMarks;

public interface StudentAssessmentComponentMarksRepository
        extends JpaRepository<StudentAssessmentComponentMarks, Long> {

    Optional<StudentAssessmentComponentMarks>
    findByStudentAssessmentMarksIdAndAssessmentStructureTypeId(
            Long studentAssessmentMarksId,
            Long assessmentStructureTypeId
    );

     List<StudentAssessmentComponentMarks> findByStudentAssessmentMarksId(
            Long studentAssessmentMarksId
    );
}