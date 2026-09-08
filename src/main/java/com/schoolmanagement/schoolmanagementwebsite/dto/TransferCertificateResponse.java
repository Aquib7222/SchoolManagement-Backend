package com.schoolmanagement.schoolmanagementwebsite.dto;

import java.time.LocalDate;
import java.time.LocalDateTime;

import lombok.*;

@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
@Builder
public class TransferCertificateResponse {

    private Long id;

    private String tcNumber;

    private String admissionNumber;

    private String studentName;

    private String studentClass;

    private String section;

    private LocalDate dateOfLeaving;

    private String reasonForLeaving;

    private String conduct;

    private String remarks;

    private LocalDateTime generatedAt;
}