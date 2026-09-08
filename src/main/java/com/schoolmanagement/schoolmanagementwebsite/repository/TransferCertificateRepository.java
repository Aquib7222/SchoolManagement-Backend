package com.schoolmanagement.schoolmanagementwebsite.repository;

import java.util.List;
import java.util.Optional;

import org.springframework.data.jpa.repository.JpaRepository;

import com.schoolmanagement.schoolmanagementwebsite.entity.TransferCertificate;

public interface TransferCertificateRepository
        extends JpaRepository<TransferCertificate, Long> {

    Optional<TransferCertificate> findByStudent_IdAndSchool_Id(
            Long studentId,
            Long schoolId
    );

    Optional<TransferCertificate> findByIdAndSchool_Id(
            Long id,
            Long schoolId
    );

    List<TransferCertificate> findBySchool_IdOrderByGeneratedAtDesc(
            Long schoolId
    );
}