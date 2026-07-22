package com.schoolmanagement.schoolmanagementwebsite.repository;

import org.springframework.data.jpa.repository.JpaRepository;

import com.schoolmanagement.schoolmanagementwebsite.entity.StudentAdmissionFee;


public interface StudentAdmissionFeeRepository
        extends JpaRepository<StudentAdmissionFee, Long> {
}

