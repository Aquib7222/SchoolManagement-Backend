package com.schoolmanagement.schoolmanagementwebsite.dto;

import java.time.LocalDate;

import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
public class TransferCertificateRequest {

    private String admissionNumber;

    private LocalDate dateOfLeaving;

    private String reasonForLeaving;

    private String conduct;

    private String remarks;
}