package com.schoolmanagement.schoolmanagementwebsite.repository;

import java.util.List;
import java.util.Optional;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import com.schoolmanagement.schoolmanagementwebsite.entity.AdmissionEnquiry;

@Repository
public interface AdmissionEnquiryRepository
        extends JpaRepository<AdmissionEnquiry, Long> {

    List<AdmissionEnquiry> findBySchoolId(Long schoolId);

    Optional<AdmissionEnquiry> findByIdAndSchoolId(
            Long id,
            Long schoolId
    );

    boolean existsByEnquiryNumber(String enquiryNumber);

    boolean existsByIdAndSchoolId(
            Long id,
            Long schoolId
    );

    
}