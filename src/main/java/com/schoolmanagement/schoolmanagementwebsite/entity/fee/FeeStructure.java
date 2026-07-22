package com.schoolmanagement.schoolmanagementwebsite.entity.fee;

import jakarta.persistence.*;
import java.util.ArrayList;
import java.util.List;

import com.fasterxml.jackson.annotation.JsonManagedReference;

@Entity
@Table(name = "fee_structure")
public class FeeStructure {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @Column(nullable = false)
    private String session;

    @Column(nullable = false)
    private String standard;

    @Column(nullable = false)
    private String feeCategory;

    @Column(nullable = false)
    private String batch;

@OneToMany(
    mappedBy = "feeStructure",
    cascade = CascadeType.ALL,
    orphanRemoval = true
)
@JsonManagedReference
private List<FeeStructureDetails> feeDetails = new ArrayList<>();

    public FeeStructure() {
    }

    public Long getId() {
        return id;
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

    public List<FeeStructureDetails> getFeeDetails() {
        return feeDetails;
    }

    public void setFeeDetails(List<FeeStructureDetails> feeDetails) {
        this.feeDetails = feeDetails;
    }
}
