package com.schoolmanagement.schoolmanagementwebsite.repository.Assessment;

import java.util.List;
import java.util.Optional;

import org.springframework.data.jpa.repository.JpaRepository;

import com.schoolmanagement.schoolmanagementwebsite.entity.Assessment.MarksAssessment;
import com.schoolmanagement.schoolmanagementwebsite.entity.Assessment.StudentAssessmentMarks;

public interface StudentAssessmentMarksRepository
        extends JpaRepository<StudentAssessmentMarks, Long> {

    Optional<StudentAssessmentMarks>
    findByMarksAssessmentIdAndStudentId(
            Long marksAssessmentId,
            Long studentId
    );

    List<StudentAssessmentMarks>
    findByMarksAssessmentId(Long marksAssessmentId);

    
}