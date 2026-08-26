package com.schoolmanagement.schoolmanagementwebsite.controller;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import com.schoolmanagement.schoolmanagementwebsite.dto.OtpRequest;
import com.schoolmanagement.schoolmanagementwebsite.service.OtpService;

@RestController
@RequestMapping("/api/otp")
@CrossOrigin(origins = "http://localhost:5173")
public class OtpController {

    @Autowired
    private OtpService otpService;

    @PostMapping("/send-phone")
    public ResponseEntity<?> sendPhoneOtp(
            @RequestBody OtpRequest request) {

        try {

            return ResponseEntity.ok(
                    otpService.sendPhoneOtp(
                            request.getPhone()
                    )
            );

        } catch (RuntimeException e) {

            return ResponseEntity.badRequest()
                    .body(e.getMessage());
        }
    }

    @PostMapping("/verify-phone")
    public ResponseEntity<?> verifyPhoneOtp(
            @RequestBody OtpRequest request) {

        try {

            return ResponseEntity.ok(
                    otpService.verifyPhoneOtp(
                            request.getPhone(),
                            request.getOtp()
                    )
            );

        } catch (RuntimeException e) {

            return ResponseEntity.badRequest()
                    .body(e.getMessage());
        }
    }

    @PostMapping("/send-email")
    public ResponseEntity<?> sendEmailOtp(
            @RequestBody OtpRequest request) {

        try {

            return ResponseEntity.ok(
                    otpService.sendEmailOtp(
                            request.getEmail()
                    )
            );

        } catch (RuntimeException e) {

            return ResponseEntity.badRequest()
                    .body(e.getMessage());
        }
    }

    @PostMapping("/verify-email")
    public ResponseEntity<?> verifyEmailOtp(
            @RequestBody OtpRequest request) {

        try {

            return ResponseEntity.ok(
                    otpService.verifyEmailOtp(
                            request.getEmail(),
                            request.getOtp()
                    )
            );

        } catch (RuntimeException e) {

            return ResponseEntity.badRequest()
                    .body(e.getMessage());
        }
    }
}