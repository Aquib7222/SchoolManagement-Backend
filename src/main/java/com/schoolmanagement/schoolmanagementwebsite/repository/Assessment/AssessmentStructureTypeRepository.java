package com.schoolmanagement.schoolmanagementwebsite.repository.Assessment;



import java.util.List;

import org.springframework.data.jpa.repository.JpaRepository;

import com.schoolmanagement.schoolmanagementwebsite.entity.Assessment.AssessmentStructureType;

public interface AssessmentStructureTypeRepository
        extends JpaRepository<AssessmentStructureType, Long> {

    List<AssessmentStructureType> findByAssessmentStructureId(
            Long assessmentStructureId
    );

    void deleteByAssessmentStructureId(
            Long assessmentStructureId
    );
}
