package com.schoolmanagement.schoolmanagementwebsite.dto;

import java.time.DayOfWeek;
import java.util.List;

public class PeriodBulkRequest {

    private Long schoolId;

    private String academicYear;

    private DayOfWeek dayOfWeek;

    private List<PeriodItemRequest> periods;

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

    public DayOfWeek getDayOfWeek() {
        return dayOfWeek;
    }

    public void setDayOfWeek(DayOfWeek dayOfWeek) {
        this.dayOfWeek = dayOfWeek;
    }

    public List<PeriodItemRequest> getPeriods() {
        return periods;
    }

    public void setPeriods(List<PeriodItemRequest> periods) {
        this.periods = periods;
    }
}