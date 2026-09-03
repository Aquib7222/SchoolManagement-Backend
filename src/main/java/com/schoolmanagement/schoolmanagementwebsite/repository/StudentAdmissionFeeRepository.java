package com.schoolmanagement.schoolmanagementwebsite.repository;

import java.util.List;

import org.springframework.data.jpa.repository.JpaRepository;

import com.schoolmanagement.schoolmanagementwebsite.entity.StudentAdmissionFee;


public interface StudentAdmissionFeeRepository
        extends JpaRepository<StudentAdmissionFee, Long> {

               
    List<StudentAdmissionFee> findByAdmission_IdAndFeeType(
            Long admissionId,
            String feeType
    );

    List<StudentAdmissionFee> findByAdmissionAdmissionNumberAndSchoolIdAndFeeType(
            String admissionNumber,
            Long schoolId,
            String feeType
    );
}

