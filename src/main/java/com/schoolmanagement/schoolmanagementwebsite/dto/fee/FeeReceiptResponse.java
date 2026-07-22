package com.schoolmanagement.schoolmanagementwebsite.dto.fee;

import java.util.List;

import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
public class FeeReceiptResponse {

    private String receiptNo;

    private String admissionNumber;

    private String studentName;

    private String studentClass;

    private String section;

    private String session;

    private String paymentDate;

    private String paymentTime;

    private String paymentMode;

    private String collectedBy;

    private Double totalAmount;

    private Double paidAmount;

    private Double fineAmount;

    private Double discountAmount;

    private Double dueAmount;

    private List<FeeReceiptRow> feeDetails;
}