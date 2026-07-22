package com.schoolmanagement.schoolmanagementwebsite.entity;
import java.time.LocalDateTime;

import jakarta.persistence.*;
import lombok.*;

@Entity
@Table(name = "admission_fee_payment")
@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
public class AdmissionFeePayment {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    /* -------- RELATIONS -------- */
    @ManyToOne
    @JoinColumn(name = "admission_id", nullable = false)
    private Admission admission;

    private Long schoolId;

    /* -------- ACADEMIC -------- */
    private String session;
    private String standard;

    /* -------- TUITION (JSON) -------- */
    @Column(columnDefinition = "TEXT")
    private String tuitionFee; // JSON (month -> amount)

    @Column(columnDefinition = "TEXT")
    private String paidMonths; // JSON Array

    /* -------- FIXED FEES -------- */
    private Double annualCharges;
    private Double examCharges;
    private Double sportsFee;
    private Double photoCardFee;
    private Double libraryLabFee;
    private Double transportFee;
    private Double miscCharges;
    private Double registrationFee;
    private Double securityMoney;

    /* -------- DISCOUNT -------- */
    private Double totalDiscount;

    /* -------- PAYMENT -------- */
    private Double totalAmount;
    private String paymentMode;
    private LocalDateTime paymentDate;

}
