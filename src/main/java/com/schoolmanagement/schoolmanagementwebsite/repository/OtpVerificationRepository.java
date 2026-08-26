package com.schoolmanagement.schoolmanagementwebsite.repository;

import java.util.Optional;

import org.springframework.data.jpa.repository.JpaRepository;

import com.schoolmanagement.schoolmanagementwebsite.entity.OtpVerification;

public interface OtpVerificationRepository
        extends JpaRepository<OtpVerification, Long> {

    Optional<OtpVerification> findTopByPhoneOrderByIdDesc(String phone);

    Optional<OtpVerification> findTopByEmailOrderByIdDesc(String email);
}