package com.schoolmanagement.schoolmanagementwebsite.entity.fee;

import java.time.LocalDate;
import java.time.LocalDateTime;

import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.persistence.Table;
import lombok.Getter;
import lombok.Setter;

@Entity
@Table(name = "student_fee_payment")
@Getter
@Setter
public class StudentFeePayment {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    // =========================
    // School
    // =========================

    // Schedule Reference
    private Long scheduleId;

    private Long schoolId;

    // =========================
    // Student
    // =========================

    private Long studentId;

    private String admissionNumber;

    private String studentName;

    private String studentClass;

    private String section;

    private String session;

    private String mobileNumber;

    // =========================
    // Schedule
    // =========================

    private Long studentFeeScheduleId;
    private Long feeStructureId;

    private Long feeMasterId;

    private String feeCode;

    private String feeName;

    
    private String feeCategory;
    private String feeBatch;

    private String month;

    // =========================
    // Amount
    // =========================

    private Double amount;

    private Double paidAmount;

    private Double dueAmount;

    private Double discountAmount;

    private Double fineAmount;

    // =========================
    // Payment
    // =========================

    private String paymentMode;

    private String transactionId;

    private String chequeNo;

    private String bankName;

    private String remarks;

    // =========================
    // Receipt
    // =========================

    
    private String receiptNo;

    private LocalDate paymentDate;

    private LocalDateTime paymentTime;

    // =========================
    // User
    // =========================

    private String collectedBy;

    // =========================
    // Status
    // =========================

    private String status;

    private Boolean deleted = false;

private String deletedBy;

private LocalDateTime deletedAt;
}

// package com.schoolmanagement.schoolmanagementwebsite.entity.fee;

// import java.time.LocalDate;
// import java.time.LocalTime;

// import jakarta.persistence.*;
// import lombok.*;

// @Entity
// @Table(name = "student_fee_payment")
// @Getter
// @Setter
// @NoArgsConstructor
// @AllArgsConstructor
// public class StudentFeePayment {

//     @Id
//     @GeneratedValue(strategy = GenerationType.IDENTITY)
//     private Long id;

//     // Schedule Reference
//     private Long scheduleId;

//     // Student
//     private Long schoolId;
//     private Long studentId;

//     private String admissionNumber;
//     private String studentName;
//     private String studentClass;
//     private String section;
//     private String session;

//     // Fee
//     private Long feeStructureId;
//     private Long feeMasterId;

//     private String feeCode;
//     private String feeName;
//     private String feeCategory;
//     private String feeBatch;

//     private String month;

//     // Receipt
//     @Column(unique = true)
//     private String receiptNo;

//     // Amount
//     private Double amount;
//     private Double paidAmount;
//     private Double fineAmount;
//     private Double discountAmount;

//     // Payment
//     private String paymentMode;
//     private String transactionId;
//     private String bankName;
//     private String chequeNo;

//     @Column(length = 1000)
//     private String remarks;

//     private String collectedBy;

//     private LocalDate paymentDate;
//     private LocalTime paymentTime;

//     private String status;
// }