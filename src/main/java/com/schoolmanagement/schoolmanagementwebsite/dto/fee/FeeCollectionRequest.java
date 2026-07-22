package com.schoolmanagement.schoolmanagementwebsite.dto.fee;

import lombok.Getter;
import lombok.Setter;

import java.util.List;

@Getter
@Setter
public class FeeCollectionRequest {

    private String admissionNumber;

    private List<Long> scheduleIds;

    private Double discountAmount;

    private Double fineAmount;

    private String paymentMode;

    private String transactionId;

    private String chequeNo;

    private String bankName;

    private String remarks;

    private String collectedBy;

}
