package com.schoolmanagement.schoolmanagementwebsite.controller;

import java.util.Map;

import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import com.schoolmanagement.schoolmanagementwebsite.dto.AdmissionFeePaymentRequest;
import com.schoolmanagement.schoolmanagementwebsite.entity.Admission;
import com.schoolmanagement.schoolmanagementwebsite.repository.AdmissionFeePaymentRepository;
import com.schoolmanagement.schoolmanagementwebsite.repository.AdmissionRepository;
import com.schoolmanagement.schoolmanagementwebsite.service.AdmissionFeePaymentService;

import lombok.RequiredArgsConstructor;

@RestController
@RequestMapping("/api/admission-fee")
@RequiredArgsConstructor
public class AdmissionFeePaymentController {

    private final AdmissionFeePaymentService service;
    private final AdmissionRepository admissionRepo;
    private final AdmissionFeePaymentRepository admissionFeeRepo;

    @PostMapping("/pay")
    public ResponseEntity<?> payAdmissionFee(
            @RequestBody AdmissionFeePaymentRequest request
    ) throws Exception {

        service.payAdmissionFee(request);
        return ResponseEntity.ok("Admission Fee Paid Successfully");
    }

    @GetMapping("/check")
public Map<String, Boolean> checkFeePaid(@RequestParam String admissionNumber,
                                         @RequestParam String session,
                                         @RequestParam String standard,
                                         @RequestParam Long schoolId) {
    Admission admission = admissionRepo.findByAdmissionNumberAndSchoolId(admissionNumber, schoolId)
        .orElseThrow(() -> new RuntimeException("Admission not found"));
    
    boolean alreadyPaid = admissionFeeRepo.existsByAdmissionAndSessionAndStandard(admission, session, standard);
    return Map.of("alreadyPaid", alreadyPaid);
}

@GetMapping("/school")
public ResponseEntity<?> getAdmissionFeePaymentsBySchool(
        @RequestParam Long schoolId
) {

    return ResponseEntity.ok(
            admissionFeeRepo.findByAdmission_SchoolId(schoolId)
    );
}
}

