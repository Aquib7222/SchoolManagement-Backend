package com.schoolmanagement.schoolmanagementwebsite.dto.AdmissionEnquiry;


import java.time.LocalDate;
import java.time.LocalDateTime;

import com.schoolmanagement.schoolmanagementwebsite.enums.Enquiry.EnquirySource;
import com.schoolmanagement.schoolmanagementwebsite.enums.Enquiry.EnquiryStatus;

import lombok.*;

@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
@Builder
public class AdmissionEnquiryResponse {

    // =========================
    // ID
    // =========================

    private Long id;

    private Long schoolId;

    private String enquiryNumber;


    // =========================
    // ENQUIRY
    // =========================

    private LocalDate enquiryDate;

    private LocalDate followUpDate;

    private EnquirySource enquirySource;

    private EnquiryStatus status;

    private String studentName;

    private LocalDate dob;

    private String gender;

    private String studentClass;

    private String academicYear;

    private String fatherName;

    private String motherName;

    private String guardianName;

    private String phone;

    private String alternatePhone;

    private String email;

    private String address;

    private String city;

    private String state;

    private String country;

    private String pincode;

    private String previousSchool;

    private String previousClass;


    private String remarks;


    private LocalDateTime createdAt;

    private LocalDateTime updatedAt;
}