package com.schoolmanagement.schoolmanagementwebsite.dto.Assessment;



public class AssessmentStructureTypeResponse {

    private Long id;

    private Long assessmentTypeId;

    private String assessmentTypeName;

    private Double maxMarks;

    private Double passingMarks;

    private Double weightage;

    private Integer displayOrder;

    public AssessmentStructureTypeResponse() {
    }

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public Long getAssessmentTypeId() {
        return assessmentTypeId;
    }

    public void setAssessmentTypeId(Long assessmentTypeId) {
        this.assessmentTypeId = assessmentTypeId;
    }

    public String getAssessmentTypeName() {
        return assessmentTypeName;
    }

    public void setAssessmentTypeName(String assessmentTypeName) {
        this.assessmentTypeName = assessmentTypeName;
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