package com.schoolmanagement.schoolmanagementwebsite.dto;

import java.util.*;

import lombok.*;

@Getter
@Setter
public class AdmissionFeePaymentRequest {

    private String admission;
    private Long schoolId;

    private String session;
    private String standard;

    private Map<String, Double> tuitionFee;
    private List<String> paidMonths;

    private Map<String, FeeAmount> fixedFees;

    private Double totalAmount;
    private String paymentMode;

    @Getter @Setter
    public static class FeeAmount {
        private Double amount;
        private Double discount;
    }
}
