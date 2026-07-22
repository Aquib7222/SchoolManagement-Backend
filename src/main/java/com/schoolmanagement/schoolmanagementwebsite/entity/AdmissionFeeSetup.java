package com.schoolmanagement.schoolmanagementwebsite.entity;

import jakarta.persistence.*;
import lombok.*;

@Entity
@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
@Builder
@Table(
    name = "admission_fee_setup",
    uniqueConstraints = {
        @UniqueConstraint(columnNames = {"school_id", "session", "standard"})
    }
)
public class AdmissionFeeSetup {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    // 🔑 School mapping
    @ManyToOne
    @JoinColumn(name = "school_id", nullable = false)
    private School school;

    @Column(nullable = false)
    private String session;

    @Column(nullable = false)
    private String standard;

    private Double annualCharges;
    private Double examCharges;
    private Double tuitionFee;
    private Double sportsFee;
    private Double photoCardFee;
    private Double libraryLabFee;
    private Double transportFee;
    private Double miscCharges;
    private Double registrationFee;
    private Double securityMoney;
}
