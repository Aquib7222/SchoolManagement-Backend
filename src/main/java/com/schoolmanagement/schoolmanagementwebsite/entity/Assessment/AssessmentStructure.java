package com.schoolmanagement.schoolmanagementwebsite.entity.Assessment;

import java.util.ArrayList;
import java.util.List;

import com.fasterxml.jackson.annotation.JsonManagedReference;
import com.schoolmanagement.schoolmanagementwebsite.entity.Assessment.SubjectManagement.SubjectMaster;
import com.schoolmanagement.schoolmanagementwebsite.entity.School;

import com.schoolmanagement.schoolmanagementwebsite.enums.Sessions;
import com.schoolmanagement.schoolmanagementwebsite.enums.Assessment.ExamTermType;
import com.schoolmanagement.schoolmanagementwebsite.enums.Standard;

import jakarta.persistence.*;

@Entity
@Table(
    name = "assessment_structure",
    uniqueConstraints = {
        @UniqueConstraint(
            columnNames = {
                "school_id",
                "session",
                "exam_term",
                "student_class",
                "subject_id"
            }
        )
    }
)
public class AssessmentStructure {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    // School
    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "school_id", nullable = false)
    private School school;

    // Session Enum
    @Enumerated(EnumType.STRING)
    @Column(name = "session", nullable = false)
    private Sessions session;

    
    private String examTerm;

    // Class Enum
    @Enumerated(EnumType.STRING)
    @Column(name = "student_class", nullable = false)
    private Standard studentClass;

    // Subject
    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "subject_id", nullable = false)
    private SubjectMaster subject;

    // Status
    @Column(nullable = false)
    private Boolean status = true;

    // Assessment Components
    @OneToMany(
        mappedBy = "assessmentStructure",
        cascade = CascadeType.ALL,
        orphanRemoval = true
    )
    @JsonManagedReference
    private List<AssessmentStructureType> assessmentTypes =
            new ArrayList<>();

    public AssessmentStructure() {
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

    public String getExamTerm() {
        return examTerm;
    }

    public void setExamTerm(String examTerm) {
        this.examTerm = examTerm;
    }

    public Standard getStudentClass() {
        return studentClass;
    }

    public void setStudentClass(Standard studentClass) {
        this.studentClass = studentClass;
    }

    public SubjectMaster getSubject() {
        return subject;
    }

    public void setSubject(SubjectMaster subject) {
        this.subject = subject;
    }

    public Boolean getStatus() {
        return status;
    }

    public void setStatus(Boolean status) {
        this.status = status;
    }

    public List<AssessmentStructureType> getAssessmentTypes() {
        return assessmentTypes;
    }

    public void setAssessmentTypes(
            List<AssessmentStructureType> assessmentTypes) {
        this.assessmentTypes = assessmentTypes;
    }
}