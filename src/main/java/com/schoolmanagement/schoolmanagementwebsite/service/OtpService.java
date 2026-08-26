package com.schoolmanagement.schoolmanagementwebsite.service;

import java.security.SecureRandom;
import java.time.LocalDateTime;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.schoolmanagement.schoolmanagementwebsite.entity.OtpVerification;
import com.schoolmanagement.schoolmanagementwebsite.repository.OtpVerificationRepository;

@Service
public class OtpService {

    @Autowired
    private OtpVerificationRepository otpRepository;

    private final SecureRandom secureRandom = new SecureRandom();

    private String generateOtp() {
        return String.format(
                "%06d",
                secureRandom.nextInt(1_000_000)
        );
    }

    public String sendPhoneOtp(String phone) {

        if (phone == null || !phone.matches("\\d{10}")) {
            throw new RuntimeException(
                    "Please enter valid 10 digit phone number"
            );
        }

        String otp = generateOtp();

        OtpVerification verification =
                otpRepository.findTopByPhoneOrderByIdDesc(phone)
                        .orElse(new OtpVerification());

        verification.setPhone(phone);
        verification.setPhoneOtp(otp);
        verification.setPhoneOtpExpiry(
                LocalDateTime.now().plusMinutes(5)
        );
        verification.setPhoneVerified(false);
        verification.setPhoneAttempts(0);

        otpRepository.save(verification);

        // TESTING ONLY
        System.out.println(
                "===================================="
        );
        System.out.println(
                "PHONE OTP : " + otp
        );
        System.out.println(
                "PHONE     : " + phone
        );
        System.out.println(
                "EXPIRES   : 5 MINUTES"
        );
        System.out.println(
                "===================================="
        );

        return "Phone OTP sent successfully";
    }

    public String verifyPhoneOtp(String phone, String otp) {

        OtpVerification verification =
                otpRepository.findTopByPhoneOrderByIdDesc(phone)
                        .orElseThrow(() ->
                                new RuntimeException(
                                        "OTP not found. Please send OTP first."
                                ));

        if (verification.isPhoneVerified()) {
            return "Phone already verified";
        }

        if (verification.getPhoneAttempts() >= 5) {
            throw new RuntimeException(
                    "Too many wrong attempts. Please resend OTP."
            );
        }

        if (verification.getPhoneOtpExpiry() == null ||
                LocalDateTime.now()
                        .isAfter(verification.getPhoneOtpExpiry())) {

            throw new RuntimeException(
                    "OTP expired. Please resend OTP."
            );
        }

        if (!verification.getPhoneOtp().equals(otp)) {

            verification.setPhoneAttempts(
                    verification.getPhoneAttempts() + 1
            );

            otpRepository.save(verification);

            throw new RuntimeException("Invalid OTP");
        }

        verification.setPhoneVerified(true);
        verification.setPhoneOtp(null);

        otpRepository.save(verification);

        return "Phone verified successfully";
    }

    public String sendEmailOtp(String email) {

        if (email == null || email.isBlank()) {
            throw new RuntimeException(
                    "Email is required"
            );
        }

        String otp = generateOtp();

        OtpVerification verification =
                otpRepository.findTopByEmailOrderByIdDesc(email)
                        .orElse(new OtpVerification());

        verification.setEmail(email);
        verification.setEmailOtp(otp);
        verification.setEmailOtpExpiry(
                LocalDateTime.now().plusMinutes(5)
        );
        verification.setEmailVerified(false);
        verification.setEmailAttempts(0);

        otpRepository.save(verification);

        // TESTING ONLY
        System.out.println(
                "===================================="
        );
        System.out.println(
                "EMAIL OTP : " + otp
        );
        System.out.println(
                "EMAIL     : " + email
        );
        System.out.println(
                "EXPIRES   : 5 MINUTES"
        );
        System.out.println(
                "===================================="
        );

        return "Email OTP sent successfully";
    }

    public String verifyEmailOtp(String email, String otp) {

        OtpVerification verification =
                otpRepository.findTopByEmailOrderByIdDesc(email)
                        .orElseThrow(() ->
                                new RuntimeException(
                                        "OTP not found. Please send OTP first."
                                ));

        if (verification.isEmailVerified()) {
            return "Email already verified";
        }

        if (verification.getEmailAttempts() >= 5) {
            throw new RuntimeException(
                    "Too many wrong attempts. Please resend OTP."
            );
        }

        if (verification.getEmailOtpExpiry() == null ||
                LocalDateTime.now()
                        .isAfter(verification.getEmailOtpExpiry())) {

            throw new RuntimeException(
                    "OTP expired. Please resend OTP."
            );
        }

        if (!verification.getEmailOtp().equals(otp)) {

            verification.setEmailAttempts(
                    verification.getEmailAttempts() + 1
            );

            otpRepository.save(verification);

            throw new RuntimeException("Invalid OTP");
        }

        verification.setEmailVerified(true);
        verification.setEmailOtp(null);

        otpRepository.save(verification);

        return "Email verified successfully";
    }

    public boolean isPhoneVerified(String phone) {

        return otpRepository
                .findTopByPhoneOrderByIdDesc(phone)
                .map(OtpVerification::isPhoneVerified)
                .orElse(false);
    }

    public boolean isEmailVerified(String email) {

        return otpRepository
                .findTopByEmailOrderByIdDesc(email)
                .map(OtpVerification::isEmailVerified)
                .orElse(false);
    }
}