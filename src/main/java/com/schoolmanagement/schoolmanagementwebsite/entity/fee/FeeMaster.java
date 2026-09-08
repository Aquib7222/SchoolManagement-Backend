package com.schoolmanagement.schoolmanagementwebsite.entity.fee;

import com.fasterxml.jackson.annotation.JsonIgnore;
import com.schoolmanagement.schoolmanagementwebsite.entity.School;
import com.schoolmanagement.schoolmanagementwebsite.enums.Status;

import jakarta.persistence.*;

@Entity
@Table(
    name = "fee_master",
    uniqueConstraints = {
        @UniqueConstraint(
            name = "uk_fee_master_school_name",
            columnNames = {"school_id", "fee_name"}
        ),
        @UniqueConstraint(
            name = "uk_fee_master_school_code",
            columnNames = {"school_id", "fee_code"}
        )
    }
)
public class FeeMaster {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @ManyToOne(fetch = FetchType.LAZY, optional = false)
    @JoinColumn(name = "school_id", nullable = false)
    @JsonIgnore
    private School school;

    @Column(name = "fee_name", nullable = false)
    private String feeName;

    @Enumerated(EnumType.STRING)
    @Column(nullable = false)
    private Status status;

    @Column(name = "fee_code", nullable = false)
    private String feeCode;

    @Column(name = "fee_category", nullable = false)
    private String feeCategory;

    public FeeMaster() {
    }

    public Long getId() {
        return id;
    }

    public School getSchool() {
        return school;
    }

    public void setSchool(School school) {
        this.school = school;
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