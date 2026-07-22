package com.schoolmanagement.schoolmanagementwebsite.entity.fee;

import java.time.LocalDate;

import jakarta.persistence.Entity;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.persistence.Table;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@Entity
@Table(name = "student_fee")
@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
public class StudentFee {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    // ==========================
    // School Details
    // ==========================

    private Long schoolId;

    // ==========================
    // Student Details
    // ==========================

    private Long studentId;

    private String admissionNumber;

    private String studentName;

    private String studentClass;

    private String section;

    private String mobileNumber;

    private String session;

    // ==========================
    // Fee Structure Details
    // ==========================

    private Long feeStructureId;

    private Long feeMasterId;

    private String feeCode;

    private String feeName;

    private String feeCategory;

    private String feeBatch;

    private Long feeStructureDetailId;

    // ==========================
    // Amount
    // ==========================

    private Double amount;

    private Double paidAmount = 0.0;

    private Double dueAmount;

    // ==========================
    // Status
    // ==========================

    private String status; // UNPAID / PARTIAL / PAID

    private LocalDate assignDate;

    private LocalDate paidDate;

}
