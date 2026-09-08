package com.schoolmanagement.schoolmanagementwebsite.service;

import java.time.LocalDateTime;
import java.time.Year;
import java.util.List;

import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import com.schoolmanagement.schoolmanagementwebsite.dto.TransferCertificateRequest;
import com.schoolmanagement.schoolmanagementwebsite.dto.TransferCertificateResponse;
import com.schoolmanagement.schoolmanagementwebsite.entity.School;
import com.schoolmanagement.schoolmanagementwebsite.entity.Student;
import com.schoolmanagement.schoolmanagementwebsite.entity.TransferCertificate;
import com.schoolmanagement.schoolmanagementwebsite.entity.User;
import com.schoolmanagement.schoolmanagementwebsite.enums.StudentStatus;
import com.schoolmanagement.schoolmanagementwebsite.repository.StudentRepository;
import com.schoolmanagement.schoolmanagementwebsite.repository.TransferCertificateRepository;
import com.schoolmanagement.schoolmanagementwebsite.repository.UserRepository;

import lombok.RequiredArgsConstructor;

@Service
@RequiredArgsConstructor
public class TransferCertificateService {

    private final TransferCertificateRepository tcRepository;
    private final StudentRepository studentRepository;
    private final UserRepository userRepository;
    private final TransferCertificatePdfService transferCertificatePdfService;

    // =========================================================
// GENERATE / VIEW PDF
// =========================================================

@Transactional(readOnly = true)
public byte[] generatePdf(
        String email,
        Long tcId) {

    School school = getSchool(email);

    TransferCertificate tc =
            tcRepository
                    .findByIdAndSchool_Id(
                            tcId,
                            school.getId()
                    )
                    .orElseThrow(() ->
                            new RuntimeException(
                                    "Transfer Certificate not found"
                            )
                    );

    return transferCertificatePdfService.generatePdf(tc);
}
    // =========================================================
    // GET LOGGED-IN USER SCHOOL
    // =========================================================

    private School getSchool(String email) {

        User user = userRepository.findByEmail(email);

        if (user == null) {
            throw new RuntimeException("User not found");
        }

        if (user.getSchool() == null) {
            throw new RuntimeException("School is not assigned to this user");
        }

        return user.getSchool();
    }

    // =========================================================
    // GENERATE TC
    // =========================================================

    @Transactional
    public TransferCertificateResponse generateTC(
            String email,
            TransferCertificateRequest request) {

        School school = getSchool(email);

        if (request.getAdmissionNumber() == null ||
                request.getAdmissionNumber().trim().isEmpty()) {

            throw new RuntimeException("Admission number is required");
        }

        Student student = studentRepository
                .findBySchool_IdAndAdmissionNumber(
                        school.getId(),
                        request.getAdmissionNumber()
                )
                .orElseThrow(() ->
                        new RuntimeException(
                                "Student not found for this school"
                        )
                );

        // =====================================================
        // ONLY INACTIVE / DISCONTINUED STUDENT CAN GET TC
        // =====================================================

        if (student.getStatus() != StudentStatus.INACTIVE) {
            throw new RuntimeException(
                    "Transfer Certificate can only be generated for discontinued students"
            );
        }

        // =====================================================
        // CHECK ALREADY GENERATED
        // =====================================================

        if (tcRepository
                .findByStudent_IdAndSchool_Id(
                        student.getId(),
                        school.getId()
                )
                .isPresent()) {

            throw new RuntimeException(
                    "Transfer Certificate already generated for this student"
            );
        }

        // =====================================================
        // VALIDATION
        // =====================================================

        if (request.getDateOfLeaving() == null) {
            throw new RuntimeException("Date of leaving is required");
        }

        if (request.getReasonForLeaving() == null ||
                request.getReasonForLeaving().trim().isEmpty()) {

            throw new RuntimeException("Reason for leaving is required");
        }

        // =====================================================
        // CREATE TC NUMBER
        // =====================================================

        String tcNumber = generateTCNumber(school.getId());

        // =====================================================
        // CREATE TC
        // =====================================================

        TransferCertificate tc = TransferCertificate.builder()
                .tcNumber(tcNumber)
                .student(student)
                .school(school)
                .dateOfLeaving(request.getDateOfLeaving())
                .reasonForLeaving(request.getReasonForLeaving())
                .conduct(
                        request.getConduct() == null ||
                        request.getConduct().trim().isEmpty()
                                ? "Good"
                                : request.getConduct()
                )
                .remarks(request.getRemarks())
                .lastClass(student.getStudentClass())
                .lastSection(student.getSection())
                .generatedAt(LocalDateTime.now())
                .build();

        TransferCertificate saved = tcRepository.save(tc);

        return mapToResponse(saved);
    }

    // =========================================================
    // TC NUMBER
    // =========================================================

    private String generateTCNumber(Long schoolId) {

        long count = tcRepository
                .findBySchool_IdOrderByGeneratedAtDesc(schoolId)
                .size();

        int nextNumber = (int) count + 1;

        return String.format(
                "TC-%d-%05d",
                Year.now().getValue(),
                nextNumber
        );
    }

    // =========================================================
    // GET ALL GENERATED TCs
    // =========================================================

    public List<TransferCertificateResponse> getAllTCs(
            String email) {

        School school = getSchool(email);

        return tcRepository
                .findBySchool_IdOrderByGeneratedAtDesc(
                        school.getId()
                )
                .stream()
                .map(this::mapToResponse)
                .toList();
    }

    // =========================================================
    // GET SINGLE TC
    // =========================================================

    public TransferCertificate getTC(
            String email,
            Long tcId) {

        School school = getSchool(email);

        return tcRepository
                .findByIdAndSchool_Id(
                        tcId,
                        school.getId()
                )
                .orElseThrow(() ->
                        new RuntimeException(
                                "Transfer Certificate not found"
                        )
                );
    }

    // =========================================================
    // RESPONSE MAPPER
    // =========================================================

    private TransferCertificateResponse mapToResponse(
            TransferCertificate tc) {

        Student student = tc.getStudent();

        String studentName =
                ((student.getFirstName() == null ? "" : student.getFirstName())
                + " "
                + (student.getMiddleName() == null ? "" : student.getMiddleName())
                + " "
                + (student.getLastName() == null ? "" : student.getLastName()))
                .trim()
                .replaceAll("\\s+", " ");

        return TransferCertificateResponse.builder()
                .id(tc.getId())
                .tcNumber(tc.getTcNumber())
                .admissionNumber(student.getAdmissionNumber())
                .studentName(studentName)
                .studentClass(student.getStudentClass())
                .section(
                        student.getSection() != null
                                ? student.getSection().name()
                                : ""
                )
                .dateOfLeaving(tc.getDateOfLeaving())
                .reasonForLeaving(tc.getReasonForLeaving())
                .conduct(tc.getConduct())
                .remarks(tc.getRemarks())
                .generatedAt(tc.getGeneratedAt())
                .build();
    }
}