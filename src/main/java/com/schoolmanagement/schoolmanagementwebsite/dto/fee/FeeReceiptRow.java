package com.schoolmanagement.schoolmanagementwebsite.dto.fee;

import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
public class FeeReceiptRow {

    private String month;

    private String feeCode;

    private String feeName;

    private Double amount;

    private Double paidAmount;

    private Double dueAmount;
}