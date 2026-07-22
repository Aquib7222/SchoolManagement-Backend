package com.schoolmanagement.schoolmanagementwebsite.dto.fee;

import java.util.List;

public class FeeStructureDto {

    private String session;
    private String standard;
    private String feeCategory;
    private String batch;

    private List<FeeStructureDetailsDto> fees;

    public FeeStructureDto() {
    }

    public String getSession() {
        return session;
    }

    public void setSession(String session) {
        this.session = session;
    }

    public String getStandard() {
        return standard;
    }

    public void setStandard(String standard) {
        this.standard = standard;
    }

    public String getFeeCategory() {
        return feeCategory;
    }

    public void setFeeCategory(String feeCategory) {
        this.feeCategory = feeCategory;
    }

    public String getBatch() {
        return batch;
    }

    public void setBatch(String batch) {
        this.batch = batch;
    }

    public List<FeeStructureDetailsDto> getFees() {
        return fees;
    }

    public void setFees(List<FeeStructureDetailsDto> fees) {
        this.fees = fees;
    }
}