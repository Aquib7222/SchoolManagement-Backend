package com.schoolmanagement.schoolmanagementwebsite.repository;

import java.util.List;

import org.springframework.data.jpa.repository.JpaRepository;

import com.schoolmanagement.schoolmanagementwebsite.entity.AdmissionStatusHistory;

public interface AdmissionStatusHistoryRepository
        extends JpaRepository<AdmissionStatusHistory, Long> {

    List<AdmissionStatusHistory>
            findByAdmission_IdOrderByChangedAtDesc(Long admissionId);
}
