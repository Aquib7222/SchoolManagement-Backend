package com.schoolmanagement.schoolmanagementwebsite.repository;

import java.util.List;

import org.springframework.data.jpa.repository.JpaRepository;

import com.schoolmanagement.schoolmanagementwebsite.entity.Admission;
import com.schoolmanagement.schoolmanagementwebsite.entity.AdmissionFeePayment;

public interface AdmissionFeePaymentRepository
        extends JpaRepository<AdmissionFeePayment, Long> {

    boolean existsByAdmissionAndSessionAndStandard(Admission admission, String session, String standard);

    List<AdmissionFeePayment> findByAdmission_SchoolId(Long schoolId);
}
