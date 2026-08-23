package com.schoolmanagement.schoolmanagementwebsite.repository.Assessment.SubjectManagement;

import java.util.List;
import java.util.Optional;

import org.springframework.data.jpa.repository.JpaRepository;

import com.schoolmanagement.schoolmanagementwebsite.entity.Assessment.SubjectManagement.SubjectMaster;

public interface SubjectMasterRepository
        extends JpaRepository<SubjectMaster, Long> {

    // Get all subjects of a school
    List<SubjectMaster> findBySchoolIdOrderByDisplayOrderAsc(
            Long schoolId
    );

    // Get subject by ID within school
    Optional<SubjectMaster> findByIdAndSchoolId(
            Long id,
            Long schoolId
    );

    // Duplicate subject name
    boolean existsBySchoolIdAndSubjectNameIgnoreCase(
            Long schoolId,
            String subjectName
    );

    // Duplicate short code
    boolean existsBySchoolIdAndShortCodeIgnoreCase(
            Long schoolId,
            String shortCode
    );
}