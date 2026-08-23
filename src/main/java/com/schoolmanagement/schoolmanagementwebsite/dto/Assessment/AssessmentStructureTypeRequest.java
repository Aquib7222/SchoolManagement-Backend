package com.schoolmanagement.schoolmanagementwebsite.dto.Assessment;


public class AssessmentStructureTypeRequest {

    private Long assessmentTypeId;

    private Double maxMarks;

    private Double passingMarks;

    private Double weightage;

    private Integer displayOrder;

    public AssessmentStructureTypeRequest() {
    }

    public Long getAssessmentTypeId() {
        return assessmentTypeId;
    }

    public void setAssessmentTypeId(Long assessmentTypeId) {
        this.assessmentTypeId = assessmentTypeId;
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
}