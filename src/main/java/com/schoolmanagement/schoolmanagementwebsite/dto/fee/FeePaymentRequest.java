package com.schoolmanagement.schoolmanagementwebsite.dto.fee;

import java.util.List;

import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
public class FeePaymentRequest {

    private List<Long> scheduleIds;

    private Double paidAmount;

    private Double fineAmount;

    private Double discountAmount;
    private Double payingAmount;

    private String paymentMode;

    private String transactionId;

    private String bankName;

    private String chequeNo;

    private String remarks;

    private String collectedBy;
}
