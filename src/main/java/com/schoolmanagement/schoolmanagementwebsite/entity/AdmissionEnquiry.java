package com.schoolmanagement.schoolmanagementwebsite.entity;

import java.time.LocalDate;
import java.time.LocalDateTime;

import com.schoolmanagement.schoolmanagementwebsite.enums.Enquiry.EnquirySource;
import com.schoolmanagement.schoolmanagementwebsite.enums.Enquiry.EnquiryStatus;

import jakarta.persistence.*;
import lombok.*;

@Entity
@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
@Builder
@Table(
    name = "admission_enquiry",
    indexes = {
        @Index(name = "idx_enquiry_school", columnList = "school_id"),
        @Index(name = "idx_enquiry_phone", columnList = "phone"),
        @Index(name = "idx_enquiry_status", columnList = "status"),
        @Index(name = "idx_enquiry_date", columnList = "enquiry_date")
    }
)
public class AdmissionEnquiry {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    // =========================
    // SCHOOL
    // =========================

    @Column(name = "school_id", nullable = false)
    private Long schoolId;


    // =========================
    // ENQUIRY
    // =========================

    @Column(name = "enquiry_number", unique = true, nullable = false)
    private String enquiryNumber;

    @Column(name = "enquiry_date", nullable = false)
    private LocalDate enquiryDate;

    @Enumerated(EnumType.STRING)
    @Column(nullable = false)
    private EnquiryStatus status;


    // =========================
    // STUDENT DETAILS
    // =========================

    @Column(nullable = false)
    private String studentName;

    private LocalDate dob;

    
    private String gender;

    @Column(name = "student_class")
    private String studentClass;

    private String academicYear;


    // =========================
    // PARENT DETAILS
    // =========================

    private String fatherName;

    private String motherName;

    private String guardianName;

    // // @Column(nullable = false)
    private String phone;

    private String alternatePhone;

    private String email;


    // =========================
    // ADDRESS
    // =========================

    private String address;

    private String city;

    private String state;

    private String country;

    private String pincode;


    // =========================
    // PREVIOUS SCHOOL
    // =========================

    private String previousSchool;

    private String previousClass;


    // =========================
    // FOLLOW UP
    // =========================

    private LocalDate followUpDate;

    @Enumerated(EnumType.STRING)
    @Column(nullable = false)
    private EnquirySource enquirySource;

    @Column(columnDefinition = "TEXT")
    private String remarks;


    // =========================
    // AUDIT
    // =========================

    @Column(nullable = false, updatable = false)
    private LocalDateTime createdAt;

    private LocalDateTime updatedAt;


    @PrePersist
    protected void onCreate() {
        createdAt = LocalDateTime.now();

        if (enquiryDate == null) {
            enquiryDate = LocalDate.now();
        }

        if (status == null) {
            status = EnquiryStatus.NEW;
        }
    }

    @PreUpdate
    protected void onUpdate() {
        updatedAt = LocalDateTime.now();
    }
}