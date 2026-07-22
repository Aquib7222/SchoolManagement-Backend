package com.schoolmanagement.schoolmanagementwebsite.entity;
import java.time.LocalDateTime;

import jakarta.persistence.Entity;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.persistence.JoinColumn;
import jakarta.persistence.ManyToOne;
import jakarta.persistence.Table;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@Entity
@Table(name = "student_Admissionfee")
@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
public class StudentAdmissionFee {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    /* -------- RELATIONS -------- */
    @ManyToOne
    @JoinColumn(name = "admission_id")
    private Admission admission;

    private Long schoolId;

    /* -------- FEE TYPE -------- */
    private String feeType; 
    // ADMISSION | MONTHLY | EXAM etc.

    private String month; // April, May etc (nullable)

    private Double amount;
    private Double discount;
    private Double paidAmount;

    private String paymentMode;
    private LocalDateTime paymentDate;
}
