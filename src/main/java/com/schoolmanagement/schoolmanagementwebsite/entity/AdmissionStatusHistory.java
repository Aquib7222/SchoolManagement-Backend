package com.schoolmanagement.schoolmanagementwebsite.entity;

import jakarta.persistence.*;
import lombok.*;

import java.time.LocalDateTime;

import com.schoolmanagement.schoolmanagementwebsite.enums.AdmissionStatus;

@Entity
@Data
@NoArgsConstructor
@AllArgsConstructor

public class AdmissionStatusHistory {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @ManyToOne
    @JoinColumn(name = "admission_id")
    private Admission admission;

    
    @Enumerated(EnumType.STRING)
    private AdmissionStatus oldStatus;

    
    @Enumerated(EnumType.STRING)
    private AdmissionStatus newStatus;

    private String changedBy;
    private LocalDateTime changedAt;
}
