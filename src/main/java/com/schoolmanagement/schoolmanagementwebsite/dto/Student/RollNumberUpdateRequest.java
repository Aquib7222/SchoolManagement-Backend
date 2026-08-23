package com.schoolmanagement.schoolmanagementwebsite.dto.Student;

import java.util.List;

public class RollNumberUpdateRequest {

    private Long schoolId;

    private String academicYear;

    private String studentClass;

    private String section;

    private List<RollNumberItemRequest> students;

    public RollNumberUpdateRequest() {
    }

    public Long getSchoolId() {
        return schoolId;
    }

    public void setSchoolId(Long schoolId) {
        this.schoolId = schoolId;
    }

    public String getAcademicYear() {
        return academicYear;
    }

    public void setAcademicYear(String academicYear) {
        this.academicYear = academicYear;
    }

    public String getStudentClass() {
        return studentClass;
    }

    public void setStudentClass(String studentClass) {
        this.studentClass = studentClass;
    }

    public String getSection() {
        return section;
    }

    public void setSection(String section) {
        this.section = section;
    }

    public List<RollNumberItemRequest> getStudents() {
        return students;
    }

    public void setStudents(
            List<RollNumberItemRequest> students) {
        this.students = students;
    }
}