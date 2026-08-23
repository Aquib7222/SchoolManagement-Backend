package com.schoolmanagement.schoolmanagementwebsite.repository.Assessment.Result;

import java.util.List;
import java.util.Optional;

import org.springframework.data.jpa.repository.JpaRepository;

import com.schoolmanagement.schoolmanagementwebsite.entity.Assessment.Result.Result;
import com.schoolmanagement.schoolmanagementwebsite.enums.Assessment.ResultStatus;
import com.schoolmanagement.schoolmanagementwebsite.enums.Section;
import com.schoolmanagement.schoolmanagementwebsite.enums.Sessions;
import com.schoolmanagement.schoolmanagementwebsite.enums.Standard;

public interface ResultRepository
        extends JpaRepository<Result, Long> {

    Optional<Result>
    findBySchoolIdAndStudentIdAndSessionAndExamTermId(
            Long schoolId,
            Long studentId,
            Sessions session,
            Long examTermId
    );

    List<Result>
    findBySchoolIdAndSessionAndExamTermIdAndStudentClassAndSection(
            Long schoolId,
            Sessions session,
            Long examTermId,
            Standard studentClass,
            Section section
    );

    List<Result>
    findBySchoolIdAndSessionAndExamTermIdAndStudentClassAndSectionAndStatus(
            Long schoolId,
            Sessions session,
            Long examTermId,
            Standard studentClass,
            Section section,
            ResultStatus status
    );

    Optional<Result> findBySchoolIdAndSessionAndExamTermIdAndAdmissionNumber(
            Long schoolId,
            Sessions session,
            Long examTermId,
            String admissionNumber
    );
}