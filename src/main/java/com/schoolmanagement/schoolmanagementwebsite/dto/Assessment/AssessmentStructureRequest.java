package com.schoolmanagement.schoolmanagementwebsite.dto.Assessment;



import java.util.List;


import com.schoolmanagement.schoolmanagementwebsite.enums.Sessions;
import com.schoolmanagement.schoolmanagementwebsite.enums.Standard;

public class AssessmentStructureRequest {

    private Long schoolId;

    private Sessions session;

   private String examTerm;

    private Standard studentClass;

    private Long subjectId;

    private List<AssessmentStructureTypeRequest> assessmentTypes;

    public AssessmentStructureRequest() {
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

    public List<AssessmentStructureTypeRequest> getAssessmentTypes() {
        return assessmentTypes;
    }

    public void setAssessmentTypes(
            List<AssessmentStructureTypeRequest> assessmentTypes) {
        this.assessmentTypes = assessmentTypes;
    }
}