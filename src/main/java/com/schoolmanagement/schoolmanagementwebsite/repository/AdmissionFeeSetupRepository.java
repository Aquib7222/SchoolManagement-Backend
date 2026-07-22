package com.schoolmanagement.schoolmanagementwebsite.repository;

import java.util.Optional;

import org.springframework.data.jpa.repository.JpaRepository;

import com.schoolmanagement.schoolmanagementwebsite.entity.AdmissionFeeSetup;

public interface AdmissionFeeSetupRepository
        extends JpaRepository<AdmissionFeeSetup, Long> {

    Optional<AdmissionFeeSetup> findBySchool_IdAndSessionAndStandard(
            Long schoolId, String session, String standard
    );
}
