package com.schoolmanagement.schoolmanagementwebsite.controller;

import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import com.schoolmanagement.schoolmanagementwebsite.entity.AdmissionFeeSetup;
import com.schoolmanagement.schoolmanagementwebsite.service.AdmissionFeeSetupService;

import lombok.RequiredArgsConstructor;

@RestController
@RequestMapping("/api/admission-fee")
@RequiredArgsConstructor
@CrossOrigin(origins = "http://localhost:5173")
public class AdmissionFeeSetupController {

    private final AdmissionFeeSetupService service;

    // ✅ SAVE / UPDATE
    @PostMapping("/save")
    public ResponseEntity<?> saveFee(
            @RequestParam Long schoolId,
            @RequestBody AdmissionFeeSetup feeSetup
    ) {
        return ResponseEntity.ok(
                service.saveOrUpdate(schoolId, feeSetup)
        );
    }

    // ✅ FETCH (auto-load on session/class change)
    @GetMapping("/get")
    public ResponseEntity<?> getFee(
            @RequestParam Long schoolId,
            @RequestParam String session,
            @RequestParam String standard
    ) {
        return ResponseEntity.ok(
                service.getFee(schoolId, session, standard)
        );
    }
}
