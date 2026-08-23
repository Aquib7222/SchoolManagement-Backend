package com.schoolmanagement.schoolmanagementwebsite.dto.Assessment;

import com.schoolmanagement.schoolmanagementwebsite.enums.Sessions;


public class GradeRequest {

    private Long schoolId;

    private Sessions session;

    private String grade;

    private Double minPercentage;

    private Double maxPercentage;

    private Double gradePoint;

    private String remarks;

    private String description;

    private Boolean status;

    public GradeRequest() {
       
    }

    public Long getSchoolId() {
        return schoolId;
    }

    public void setSchoolId(Long schoolId) {
        this.schoolId = schoolId;
    }

    public Sessions getSession() {
        return session;
    }

    public void setSession(Sessions session) {
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