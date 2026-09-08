package com.schoolmanagement.schoolmanagementwebsite.entity;

import java.time.LocalDate;
import java.time.LocalDateTime;

import com.schoolmanagement.schoolmanagementwebsite.enums.Section;

import jakarta.persistence.*;
import lombok.*;

@Entity
@Table(
    name = "transfer_certificates",
    uniqueConstraints = {
        @UniqueConstraint(
            name = "uk_tc_student_school",
            columnNames = {"student_id", "school_id"}
        ),
        @UniqueConstraint(
            name = "uk_tc_number_school",
            columnNames = {"tc_number", "school_id"}
        )
    }
)
@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
@Builder
public class TransferCertificate {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @Column(name = "tc_number", nullable = false)
    private String tcNumber;

    @ManyToOne(fetch = FetchType.LAZY, optional = false)
    @JoinColumn(name = "student_id", nullable = false)
    private Student student;

    @ManyToOne(fetch = FetchType.LAZY, optional = false)
    @JoinColumn(name = "school_id", nullable = false)
    private School school;

    @Column(name = "date_of_leaving", nullable = false)
    private LocalDate dateOfLeaving;

    @Column(name = "reason_for_leaving", nullable = false)
    private String reasonForLeaving;

    private String conduct;

    @Column(columnDefinition = "TEXT")
    private String remarks;

    private String lastClass;

    @Enumerated(EnumType.STRING)
    private Section lastSection;

    private LocalDateTime generatedAt;
}