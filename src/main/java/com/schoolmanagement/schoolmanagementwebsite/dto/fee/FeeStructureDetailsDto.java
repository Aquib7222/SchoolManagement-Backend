package com.schoolmanagement.schoolmanagementwebsite.dto.fee;

public class FeeStructureDetailsDto {

    private Long feeMasterId;
    private Double amount;

    public FeeStructureDetailsDto() {
    }

    public Long getFeeMasterId() {
        return feeMasterId;
    }

    public void setFeeMasterId(Long feeMasterId) {
        this.feeMasterId = feeMasterId;
    }

    public Double getAmount() {
        return amount;
    }

    public void setAmount(Double amount) {
        this.amount = amount;
    }
}