package com.schoolmanagement.schoolmanagementwebsite.entity.fee;

import jakarta.persistence.*;

import com.fasterxml.jackson.annotation.JsonBackReference;
import com.schoolmanagement.schoolmanagementwebsite.entity.fee.FeeStructure;

@Entity
@Table(name = "fee_structure_details")
public class FeeStructureDetails {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @ManyToOne
    @JoinColumn(name = "fee_structure_id")
    @JsonBackReference
    private FeeStructure feeStructure;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "fee_master_id")
    private FeeMaster feeMaster;

    @Column(nullable = false)
    private Double amount;

    public FeeStructureDetails() {
    }

    public Long getId() {
        return id;
    }

    public FeeStructure getFeeStructure() {
        return feeStructure;
    }

    public void setFeeStructure(FeeStructure feeStructure) {
        this.feeStructure = feeStructure;
    }

    public FeeMaster getFeeMaster() {
        return feeMaster;
    }

    public void setFeeMaster(FeeMaster feeMaster) {
        this.feeMaster = feeMaster;
    }

    public Double getAmount() {
        return amount;
    }

    public void setAmount(Double amount) {
        this.amount = amount;
    }
}
