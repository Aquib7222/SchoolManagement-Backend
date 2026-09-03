package com.schoolmanagement.schoolmanagementwebsite.controller;

import java.util.List;

import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import com.schoolmanagement.schoolmanagementwebsite.dto.AdmissionEnquiry.AdmissionEnquiryRequest;
import com.schoolmanagement.schoolmanagementwebsite.dto.AdmissionEnquiry.AdmissionEnquiryResponse;
import com.schoolmanagement.schoolmanagementwebsite.enums.Enquiry.EnquiryStatus;
import com.schoolmanagement.schoolmanagementwebsite.service.AdmissionEnquiryService;

import lombok.RequiredArgsConstructor;



@RestController
@RequestMapping("/api/admission-enquiry")
@RequiredArgsConstructor
public class AdmissionEnquiryController {

    private final AdmissionEnquiryService admissionEnquiryService;


    // =========================
    // CREATE
    // =========================

    @PostMapping
    public ResponseEntity<AdmissionEnquiryResponse> createEnquiry(
             @RequestBody AdmissionEnquiryRequest request) {

        AdmissionEnquiryResponse response =
                admissionEnquiryService.createEnquiry(request);

        return ResponseEntity
                .status(HttpStatus.CREATED)
                .body(response);
    }


    // =========================
    // GET ALL
    // =========================

    @GetMapping("/school")
    public ResponseEntity<List<AdmissionEnquiryResponse>> getAllEnquiries(
            @RequestParam Long schoolId) {

        return ResponseEntity.ok(
                admissionEnquiryService
                        .getAllEnquiries(schoolId)
        );
    }


    // =========================
    // GET BY ID
    // =========================

    @GetMapping("/{id}")
    public ResponseEntity<AdmissionEnquiryResponse> getEnquiryById(
            @PathVariable Long id,
            @RequestParam Long schoolId) {

        return ResponseEntity.ok(
                admissionEnquiryService
                        .getEnquiryById(id, schoolId)
        );
    }


    // =========================
    // UPDATE
    // =========================

    @PutMapping("/{id}")
    public ResponseEntity<AdmissionEnquiryResponse> updateEnquiry(
            @PathVariable Long id,
            @RequestParam Long schoolId,
             @RequestBody AdmissionEnquiryRequest request) {

       return ResponseEntity.ok(
                admissionEnquiryService.updateEnquiry(
                        id,
                        schoolId,
                        request
                )
        );
    }


    


    // =========================
    // DELETE
    // =========================

    @DeleteMapping("/{id}")
    public ResponseEntity<Void> deleteEnquiry(
            @PathVariable Long id,
            @RequestParam Long schoolId) {

        admissionEnquiryService.deleteEnquiry(
                id,
                schoolId
        );

        return ResponseEntity.noContent().build();
    }


    // =========================
    // UPDATE STATUS
    // =========================

    @PatchMapping("/{id}/status")
    public ResponseEntity<AdmissionEnquiryResponse> updateStatus(
            @PathVariable Long id,
            @RequestParam Long schoolId,
            @RequestParam EnquiryStatus status) {

        return ResponseEntity.ok(
                admissionEnquiryService.updateStatus(
                        id,
                        schoolId,
                        status
                )
        );
    }
}