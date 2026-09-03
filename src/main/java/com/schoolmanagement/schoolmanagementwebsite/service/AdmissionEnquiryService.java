package com.schoolmanagement.schoolmanagementwebsite.service;

import java.util.List;

import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import com.schoolmanagement.schoolmanagementwebsite.dto.AdmissionEnquiry.AdmissionEnquiryRequest;
import com.schoolmanagement.schoolmanagementwebsite.dto.AdmissionEnquiry.AdmissionEnquiryResponse;
import com.schoolmanagement.schoolmanagementwebsite.entity.AdmissionEnquiry;
import com.schoolmanagement.schoolmanagementwebsite.enums.Enquiry.EnquiryStatus;
import com.schoolmanagement.schoolmanagementwebsite.repository.AdmissionEnquiryRepository;

import lombok.RequiredArgsConstructor;

@Service
@RequiredArgsConstructor
public class AdmissionEnquiryService {

    private final AdmissionEnquiryRepository admissionEnquiryRepository;


    // =========================
    // CREATE
    // =========================

    @Transactional
    public AdmissionEnquiryResponse createEnquiry(
            AdmissionEnquiryRequest request) {

        AdmissionEnquiry enquiry = AdmissionEnquiry.builder()

                .schoolId(request.getSchoolId())

                .studentName(request.getStudentName())
                .dob(request.getDob())
                .gender(request.getGender())
                .studentClass(request.getStudentClass())
                .academicYear(request.getAcademicYear())

                .fatherName(request.getFatherName())
                .motherName(request.getMotherName())
                .guardianName(request.getGuardianName())

                .phone(request.getPhone())
                .alternatePhone(request.getAlternatePhone())
                .email(request.getEmail())

                .address(request.getAddress())
                .city(request.getCity())
                .state(request.getState())
                .country(request.getCountry())
                .pincode(request.getPincode())

                .previousSchool(request.getPreviousSchool())
                .previousClass(request.getPreviousClass())

                .enquiryDate(request.getEnquiryDate())
                .followUpDate(request.getFollowUpDate())
                .enquirySource(request.getEnquirySource())

                .remarks(request.getRemarks())

                .status(EnquiryStatus.NEW)

                .build();

        // Generate enquiry number
        String enquiryNumber = generateEnquiryNumber();

        enquiry.setEnquiryNumber(enquiryNumber);

        AdmissionEnquiry saved =
                admissionEnquiryRepository.save(enquiry);

        return mapToResponse(saved);
    }


    // =========================
    // GET ALL BY SCHOOL
    // =========================

    public List<AdmissionEnquiryResponse> getAllEnquiries(
            Long schoolId) {

        return admissionEnquiryRepository
                .findBySchoolId(schoolId)
                .stream()
                .map(this::mapToResponse)
                .toList();
    }


    // =========================
    // GET BY ID
    // =========================

    public AdmissionEnquiryResponse getEnquiryById(
            Long id,
            Long schoolId) {

        AdmissionEnquiry enquiry =
                admissionEnquiryRepository
                        .findByIdAndSchoolId(id, schoolId)
                        .orElseThrow(() ->
                                new RuntimeException(
                                        "Admission enquiry not found"
                                ));

        return mapToResponse(enquiry);
    }


    // =========================
    // UPDATE
    // =========================

    @Transactional
    public AdmissionEnquiryResponse updateEnquiry(
            Long id,
            Long schoolId,
            AdmissionEnquiryRequest request) {

        AdmissionEnquiry enquiry =
                admissionEnquiryRepository
                        .findByIdAndSchoolId(id, schoolId)
                        .orElseThrow(() ->
                                new RuntimeException(
                                        "Admission enquiry not found"
                                ));

        enquiry.setStudentName(request.getStudentName());
        enquiry.setDob(request.getDob());
        enquiry.setGender(request.getGender());
        enquiry.setStudentClass(request.getStudentClass());
        enquiry.setAcademicYear(request.getAcademicYear());

        enquiry.setFatherName(request.getFatherName());
        enquiry.setMotherName(request.getMotherName());
        enquiry.setGuardianName(request.getGuardianName());

        enquiry.setPhone(request.getPhone());
        enquiry.setAlternatePhone(request.getAlternatePhone());
        enquiry.setEmail(request.getEmail());

        enquiry.setAddress(request.getAddress());
        enquiry.setCity(request.getCity());
        enquiry.setState(request.getState());
        enquiry.setCountry(request.getCountry());
        enquiry.setPincode(request.getPincode());

        enquiry.setPreviousSchool(request.getPreviousSchool());
        enquiry.setPreviousClass(request.getPreviousClass());

        enquiry.setEnquiryDate(request.getEnquiryDate());
        enquiry.setFollowUpDate(request.getFollowUpDate());
        enquiry.setEnquirySource(request.getEnquirySource());

        enquiry.setRemarks(request.getRemarks());

        AdmissionEnquiry updated =
                admissionEnquiryRepository.save(enquiry);

        return mapToResponse(updated);
    }


    // =========================
    // DELETE
    // =========================

    @Transactional
    public void deleteEnquiry(
            Long id,
            Long schoolId) {

        AdmissionEnquiry enquiry =
                admissionEnquiryRepository
                        .findByIdAndSchoolId(id, schoolId)
                        .orElseThrow(() ->
                                new RuntimeException(
                                        "Admission enquiry not found"
                                ));

        admissionEnquiryRepository.delete(enquiry);
    }


    // =========================
    // UPDATE STATUS
    // =========================

    @Transactional
    public AdmissionEnquiryResponse updateStatus(
            Long id,
            Long schoolId,
            EnquiryStatus status) {

        AdmissionEnquiry enquiry =
                admissionEnquiryRepository
                        .findByIdAndSchoolId(id, schoolId)
                        .orElseThrow(() ->
                                new RuntimeException(
                                        "Admission enquiry not found"
                                ));

        enquiry.setStatus(status);

        AdmissionEnquiry updated =
                admissionEnquiryRepository.save(enquiry);

        return mapToResponse(updated);
    }

    


    // =========================
    // GENERATE ENQUIRY NUMBER
    // =========================

    private String generateEnquiryNumber() {

        long count =
                admissionEnquiryRepository.count() + 1;

        String enquiryNumber =
                String.format("ENQ%05d", count);

        while (admissionEnquiryRepository
                .existsByEnquiryNumber(enquiryNumber)) {

            count++;

            enquiryNumber =
                    String.format("ENQ%05d", count);
        }

        return enquiryNumber;
    }


    // =========================
    // ENTITY → RESPONSE
    // =========================

    private AdmissionEnquiryResponse mapToResponse(
            AdmissionEnquiry enquiry) {

        return AdmissionEnquiryResponse.builder()

                .id(enquiry.getId())
                .schoolId(enquiry.getSchoolId())
                .enquiryNumber(enquiry.getEnquiryNumber())

                .enquiryDate(enquiry.getEnquiryDate())
                .followUpDate(enquiry.getFollowUpDate())
                .enquirySource(enquiry.getEnquirySource())
                .status(enquiry.getStatus())

                .studentName(enquiry.getStudentName())
                .dob(enquiry.getDob())
                .gender(enquiry.getGender())
                .studentClass(enquiry.getStudentClass())
                .academicYear(enquiry.getAcademicYear())

                .fatherName(enquiry.getFatherName())
                .motherName(enquiry.getMotherName())
                .guardianName(enquiry.getGuardianName())

                .phone(enquiry.getPhone())
                .alternatePhone(enquiry.getAlternatePhone())
                .email(enquiry.getEmail())

                .address(enquiry.getAddress())
                .city(enquiry.getCity())
                .state(enquiry.getState())
                .country(enquiry.getCountry())
                .pincode(enquiry.getPincode())

                .previousSchool(enquiry.getPreviousSchool())
                .previousClass(enquiry.getPreviousClass())

                .remarks(enquiry.getRemarks())

                .createdAt(enquiry.getCreatedAt())
                .updatedAt(enquiry.getUpdatedAt())

                .build();
    }
}