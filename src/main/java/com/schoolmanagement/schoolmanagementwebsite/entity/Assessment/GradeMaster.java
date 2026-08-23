package com.schoolmanagement.schoolmanagementwebsite.entity.Assessment;

import com.schoolmanagement.schoolmanagementwebsite.entity.School;
import com.schoolmanagement.schoolmanagementwebsite.enums.Sessions;

import jakarta.persistence.*;

@Entity
@Table(
    name = "grade_master",
    uniqueConstraints = {
        @UniqueConstraint(
            name = "uk_grade_school_session_grade",
            columnNames = {"school_id", "session", "grade"}
        )
    }
)
public class GradeMaster {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "school_id", nullable = false)
    private School school;

    @Enumerated(EnumType.STRING)
    @Column(nullable = false)
    private Sessions session;

    @Column(nullable = false, length = 10)
    private String grade;

    @Column(nullable = false)
    private Double minPercentage;

    @Column(nullable = false)
    private Double maxPercentage;

    private Double gradePoint;

    private String remarks;

   

    @Column(length = 255)
    private String description;

    @Column(nullable = false)
    private Boolean status = true;

    public GradeMaster() {
    }

     public void setId(Long id) {
        this.id = id;
    }

    public String getRemarks() {
        return remarks;
    }

    public void setRemarks(String remarks) {
        this.remarks = remarks;
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