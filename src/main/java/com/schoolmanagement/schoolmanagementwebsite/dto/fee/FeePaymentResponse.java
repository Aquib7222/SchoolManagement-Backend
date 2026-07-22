package com.schoolmanagement.schoolmanagementwebsite.dto.fee;

import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
public class FeePaymentResponse {

    private String receiptNo;

    private Double paidAmount;

    private Double fineAmount;

    private Double discountAmount;

    private Double balanceAmount;

    private String paymentMode;

    private String paymentDate;

    private String paymentTime;

    private String collectedBy;
}