package com.schoolmanagement.schoolmanagementwebsite.dto.fee;

// public class FeeMasterDto {

//     private String feeName;
//     private Boolean status;
//     private String feeCode;
//     private String feeCategory;

//     public String getFeeName() {
//         return feeName;
//     }

//     public void setFeeName(String feeName) {
//         this.feeName = feeName;
//     }
//     public  String getFeeCode() {
//         return feeCode;
//     }
//     public String getFeeCategory() {
//         return feeCategory;
//     }

//     public Boolean getStatus() {
//         return status;
//     }

//     public void setStatus(Boolean status) {
//         this.status = status;
//     }
// }
import com.schoolmanagement.schoolmanagementwebsite.enums.Status;

public class FeeMasterDto {

    private String feeName;
    private String feeCode;
    private String feeCategory;
    private Status status;

    public String getFeeName() {
        return feeName;
    }

    public void setFeeName(String feeName) {
        this.feeName = feeName;
    }

    public String getFeeCode() {
        return feeCode;
    }

    public void setFeeCode(String feeCode) {
        this.feeCode = feeCode;
    }

    public String getFeeCategory() {
        return feeCategory;
    }

    public void setFeeCategory(String feeCategory) {
        this.feeCategory = feeCategory;
    }

    public Status getStatus() {
        return status;
    }

    public void setStatus(Status status) {
        this.status = status;
    }
}