package com.schoolmanagement.schoolmanagementwebsite.repository.Assessment;

import java.math.BigDecimal;
import java.util.List;
import java.util.Optional;

import org.springframework.data.jpa.repository.JpaRepository;

import com.schoolmanagement.schoolmanagementwebsite.entity.Assessment.GradeMaster;
import com.schoolmanagement.schoolmanagementwebsite.enums.Sessions;

public interface GradeMasterRepository
        extends JpaRepository<GradeMaster, Long> {

    List<GradeMaster> findBySchoolId(Long schoolId);

    List<GradeMaster> findBySchoolIdAndSession(
            Long schoolId,
            Sessions session);

    Optional<GradeMaster> findByIdAndSchoolId(
            Long id,
            Long schoolId);

    Optional<GradeMaster> findBySchoolIdAndSessionAndGrade(
            Long schoolId,
            Sessions session,
            String grade);

    List<GradeMaster> findBySchoolIdAndSessionAndStatusTrueOrderByMinPercentageDesc(
            Long schoolId,
            Sessions session);

List<GradeMaster>
    findBySchoolIdAndStatusTrueAndMinPercentageLessThanEqualAndMaxPercentageGreaterThanEqual(
            Long schoolId,
            BigDecimal percentage1,
            BigDecimal percentage2
    );
}