package com.schoolmanagement.schoolmanagementwebsite.repository;

import java.util.List;
import java.util.Optional;

import org.springframework.data.jpa.repository.JpaRepository;

import com.schoolmanagement.schoolmanagementwebsite.entity.Admission;

public interface AdmissionRepository extends JpaRepository<Admission, Long> {

    boolean existsByAdmissionNumber(String admissionNumber);
    List<Admission> findBySchool_Id(Long schoolId);
    long countBySchool_Id(Long schoolId);

    Optional<Admission> findByAdmissionNumberAndSchoolId(String admissionNumber, Long schoolId);



}
