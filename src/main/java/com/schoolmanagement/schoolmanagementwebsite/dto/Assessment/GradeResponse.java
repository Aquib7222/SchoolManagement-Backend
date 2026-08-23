package com.schoolmanagement.schoolmanagementwebsite.dto.Assessment;

public class GradeResponse {

    private Long id;

    private Long schoolId;

    private String session;

    private String grade;

    private Double minPercentage;

    private Double maxPercentage;

    private Double gradePoint;

    private String remarks;

    private String description;

    private Boolean status;

    public GradeResponse() {
    }
    
    public GradeResponse(Long id, Long schoolId, String session, String grade, Double minPercentage,
            Double maxPercentage, Double gradePoint, String remarks, String description, Boolean status) {
        this.id = id;
        this.schoolId = schoolId;
        this.session = session;
        this.grade = grade;
        this.minPercentage = minPercentage;
        this.maxPercentage = maxPercentage;
        this.gradePoint = gradePoint;
        this.remarks = remarks;
        this.description = description;
        this.status = status;
    }

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public Long getSchoolId() {
        return schoolId;
    }

    public void setSchoolId(Long schoolId) {
        this.schoolId = schoolId;
    }

    public String getSession() {
        return session;
    }

    public void setSession(String session) {
        this.session = session;
    }

    public String getGrade() {
        return grade;
    }

    public void setGrade(String grade) {
        this.grade = grade;
    }

    public Double getMinPercentage() {
        return minPercentage;
    }

    public void setMinPercentage(Double minPercentage) {
        this.minPercentage = minPercentage;
    }

    public Double getMaxPercentage() {
        return maxPercentage;
    }

    public void setMaxPercentage(Double maxPercentage) {
        this.maxPercentage = maxPercentage;
    }

    public Double getGradePoint() {
        return gradePoint;
    }

    public void setGradePoint(Double gradePoint) {
        this.gradePoint = gradePoint;
    }

    public String getRemarks() {
        return remarks;
    }

    public void setRemarks(String remarks) {
        this.remarks = remarks;
    }

    public String getDescription() {
        return description;
    }

    public void setDescription(String description) {
        this.description = description;
    }
 
   public Boolean getStatus() {
        return status;
    }

    public void setStatus(Boolean status) {
        this.status = status;
    }

    
}