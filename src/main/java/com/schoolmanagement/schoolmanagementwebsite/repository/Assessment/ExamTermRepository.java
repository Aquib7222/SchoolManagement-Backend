package com.schoolmanagement.schoolmanagementwebsite.repository.Assessment;

import java.util.List;
import java.util.Optional;

import org.springframework.data.jpa.repository.JpaRepository;

import com.schoolmanagement.schoolmanagementwebsite.entity.Assessment.ExamTerm;
import com.schoolmanagement.schoolmanagementwebsite.enums.Sessions;

public interface ExamTermRepository extends JpaRepository<ExamTerm, Long> {

    List<ExamTerm> findBySchoolIdAndSessionOrderByDisplayOrderAsc(
            Long schoolId,
            Sessions session
    );

    Optional<ExamTerm> findByIdAndSchoolIdAndSession(
            Long id,
            Long schoolId,
            Sessions session
    );

    boolean existsBySchoolIdAndSessionAndExamTermIgnoreCase(
            Long schoolId,
            Sessions session,
            String examTerm
    );

    boolean existsBySchoolIdAndSessionAndShortCodeIgnoreCase(
            Long schoolId,
            Sessions session,
            String shortCode
    );
}