package com.schoolmanagement.schoolmanagementwebsite.entity.Assessment;


import com.fasterxml.jackson.annotation.JsonBackReference;

import jakarta.persistence.*;

@Entity
@Table(
    name = "assessment_structure_type",
    uniqueConstraints = {
        @UniqueConstraint(
            columnNames = {
                "assessment_structure_id",
                "assessment_type_id"
            }
        )
    }
)
public class AssessmentStructureType {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    // Parent Assessment Structure
    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(
        name = "assessment_structure_id",
        nullable = false
    )
    @JsonBackReference
    private AssessmentStructure assessmentStructure;

    // Assessment Type Master
    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(
        name = "assessment_type_id",
        nullable = false
    )
    private AssessmentType assessmentType;

    // Maximum Marks
    @Column(name = "max_marks", nullable = false)
    private Double maxMarks;

    // Passing Marks
    @Column(name = "passing_marks", nullable = false)
    private Double passingMarks;

    // Weightage Percentage
    @Column(nullable = false)
    private Double weightage;

    // Order in which component will appear
    @Column(name = "display_order")
    private Integer displayOrder;

    // Active / Inactive
    @Column(nullable = false)
    private Boolean status = true;

    public AssessmentStructureType() {
    }

    public Long getId() {
        return id;
    }

    public AssessmentStructure getAssessmentStructure() {
        return assessmentStructure;
    }

    public void setAssessmentStructure(
            AssessmentStructure assessmentStructure) {
        this.assessmentStructure = assessmentStructure;
    }

    public AssessmentType getAssessmentType() {
        return assessmentType;
    }

    public void setAssessmentType(AssessmentType assessmentType) {
        this.assessmentType = assessmentType;
    }

    public Double getMaxMarks() {
        return maxMarks;
    }

    public void setMaxMarks(Double maxMarks) {
        this.maxMarks = maxMarks;
    }

    public Double getPassingMarks() {
        return passingMarks;
    }

    public void setPassingMarks(Double passingMarks) {
        this.passingMarks = passingMarks;
    }

    public Double getWeightage() {
        return weightage;
    }

    public void setWeightage(Double weightage) {
        this.weightage = weightage;
    }

    public Integer getDisplayOrder() {
        return displayOrder;
    }

    public void setDisplayOrder(Integer displayOrder) {
        this.displayOrder = displayOrder;
    }

    public Boolean getStatus() {
        return status;
    }

    public void setStatus(Boolean status) {
        this.status = status;
    }
}