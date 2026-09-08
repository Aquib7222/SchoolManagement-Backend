
package com.schoolmanagement.schoolmanagementwebsite.controller;

import java.util.List;

import org.springframework.http.HttpHeaders;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.security.core.Authentication;
import org.springframework.web.bind.annotation.*;

import com.schoolmanagement.schoolmanagementwebsite.dto.TransferCertificateRequest;
import com.schoolmanagement.schoolmanagementwebsite.dto.TransferCertificateResponse;
import com.schoolmanagement.schoolmanagementwebsite.entity.TransferCertificate;
import com.schoolmanagement.schoolmanagementwebsite.service.TransferCertificateService;

import lombok.RequiredArgsConstructor;

@RestController
@RequestMapping("/api/transfer-certificates")
@RequiredArgsConstructor
@CrossOrigin(origins = "http://localhost:5173")
public class TransferCertificateController {

    private final TransferCertificateService tcService;

    // =========================================================
    // GENERATE TC
    // =========================================================

    @PostMapping("/generate")
    public ResponseEntity<TransferCertificateResponse> generateTC(
            @RequestBody TransferCertificateRequest request,
            Authentication authentication) {

        String email = authentication.getName();

        return ResponseEntity.ok(
                tcService.generateTC(
                        email,
                        request
                )
        );
    }

    // =========================================================
    // GET ALL GENERATED TCs
    // =========================================================

    @GetMapping("/all")
    public ResponseEntity<List<TransferCertificateResponse>> getAllTCs(
            Authentication authentication) {

        String email = authentication.getName();

        return ResponseEntity.ok(
                tcService.getAllTCs(email)
        );
    }

    // =========================================================
    // VIEW TC DETAILS
    // =========================================================

    @GetMapping("/{id}")
    public ResponseEntity<TransferCertificateResponse> getTC(
            @PathVariable Long id,
            Authentication authentication) {

        String email = authentication.getName();

        TransferCertificate tc =
                tcService.getTC(
                        email,
                        id
                );

        String studentName =
                (
                        (tc.getStudent().getFirstName() == null
                                ? ""
                                : tc.getStudent().getFirstName())
                        + " "
                        + (tc.getStudent().getMiddleName() == null
                                ? ""
                                : tc.getStudent().getMiddleName())
                        + " "
                        + (tc.getStudent().getLastName() == null
                                ? ""
                                : tc.getStudent().getLastName())
                )
                .trim()
                .replaceAll("\\s+", " ");

        return ResponseEntity.ok(
                TransferCertificateResponse.builder()
                        .id(tc.getId())
                        .tcNumber(tc.getTcNumber())
                        .admissionNumber(
                                tc.getStudent()
                                        .getAdmissionNumber()
                        )
                        .studentName(studentName)
                        .studentClass(
                                tc.getStudent()
                                        .getStudentClass()
                        )
                        .section(
                                tc.getStudent().getSection() != null
                                        ? tc.getStudent()
                                                .getSection()
                                                .name()
                                        : ""
                        )
                        .dateOfLeaving(
                                tc.getDateOfLeaving()
                        )
                        .reasonForLeaving(
                                tc.getReasonForLeaving()
                        )
                        .conduct(
                                tc.getConduct()
                        )
                        .remarks(
                                tc.getRemarks()
                        )
                        .generatedAt(
                                tc.getGeneratedAt()
                        )
                        .build()
        );
    }

    // =========================================================
    // VIEW / DOWNLOAD TC PDF
    // =========================================================

    @GetMapping("/{id}/pdf")
    public ResponseEntity<byte[]> viewPdf(
            @PathVariable Long id,
            Authentication authentication) {

        String email = authentication.getName();

        byte[] pdf =
                tcService.generatePdf(
                        email,
                        id
                );

        return ResponseEntity.ok()
                .header(
                        HttpHeaders.CONTENT_DISPOSITION,
                        "inline; filename=\"TC-" + id + ".pdf\""
                )
                .contentType(
                        MediaType.APPLICATION_PDF
                )
                .body(pdf);
    }
}


