package com.schoolmanagement.schoolmanagementwebsite.dto.Assessment;

import com.schoolmanagement.schoolmanagementwebsite.enums.Sessions;
import com.schoolmanagement.schoolmanagementwebsite.enums.Standard;
import com.schoolmanagement.schoolmanagementwebsite.enums.Assessment.ExamTermType;




import java.util.ArrayList;
import java.util.List;

public class AssessmentStructureResponse {

    private Long id;

    private Long schoolId;

    private Sessions session;

    private String examTerm;

    private Standard studentClass;

    private Long subjectId;

    private List<AssessmentStructureTypeResponse> assessmentTypes =
            new ArrayList<>();

    public AssessmentStructureResponse() {
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

    public Long getSubjectId() {
        return subjectId;
    }

    public void setSubjectId(Long subjectId) {
        this.subjectId = subjectId;
    }

    public List<AssessmentStructureTypeResponse> getAssessmentTypes() {
        return assessmentTypes;
    }

    public void setAssessmentTypes(
            List<AssessmentStructureTypeResponse> assessmentTypes) {
        this.assessmentTypes = assessmentTypes;
    }
}