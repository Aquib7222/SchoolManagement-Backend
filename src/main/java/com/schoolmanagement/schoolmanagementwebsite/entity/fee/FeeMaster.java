package com.schoolmanagement.schoolmanagementwebsite.entity.fee;

import com.schoolmanagement.schoolmanagementwebsite.enums.Status;
import jakarta.persistence.*;

@Entity
@Table(name = "fee_master")
public class FeeMaster {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @Column(nullable = false, unique = true)
    private String feeName;

    @Enumerated(EnumType.STRING)
    private Status status;
    @Column(nullable = false, unique = true)
    private String feeCode;

    @Column(nullable = false)
    private String feeCategory;

    public FeeMaster() {
    }

    public Long getId() {
        return id;
    }

    public String getFeeName() {
        return feeName;
    }

    public void setFeeName(String feeName) {
        this.feeName = feeName;
    }

    public Status getStatus() {
        return status;
    }

    public void setStatus(Status status) {
        this.status = status;
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
}
