package com.schoolmanagement.schoolmanagementwebsite.dto;

import java.util.List;

public class TeacherClassAssignmentBulkRequest {

    private Long schoolId;

    private String academicYear;

    private String dayOfWeek;

    private List<TeacherClassAssignmentItemRequest> assignments;

    public TeacherClassAssignmentBulkRequest() {
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

    public String getDayOfWeek() {
        return dayOfWeek;
    }

    public void setDayOfWeek(String dayOfWeek) {
        this.dayOfWeek = dayOfWeek;
    }

    public List<TeacherClassAssignmentItemRequest> getAssignments() {
        return assignments;
    }

    public void setAssignments(
            List<TeacherClassAssignmentItemRequest> assignments
    ) {
        this.assignments = assignments;
    }
}


