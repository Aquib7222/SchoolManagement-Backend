package com.schoolmanagement.schoolmanagementwebsite.dto;

import java.time.LocalTime;

import com.schoolmanagement.schoolmanagementwebsite.enums.Subject;

public class TeacherClassAssignmentItemRequest {

    private Long id;

    private Long periodId;

   private String periodName;

    private LocalTime startTime;

private LocalTime endTime;

    public String getPeriodName() {
    return periodName;
}

public void setPeriodName(String periodName) {
    this.periodName = periodName;
}

    private Long teacherId;

    private Subject subject;

    private String studentClass;

    private String section;

    private String room;

    private Boolean active;

    public TeacherClassAssignmentItemRequest() {
    }

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public Long getPeriodId() {
        return periodId;
    }

    public void setPeriodId(Long periodId) {
        this.periodId = periodId;
    }

     public LocalTime getStartTime() {
        return startTime;
    }

    public void setStartTime(LocalTime startTime) {
        this.startTime = startTime;
    }

    public LocalTime getEndTime() {
        return endTime;
    }

    public void setEndTime(LocalTime endTime) {
        this.endTime = endTime;
    }

    public Long getTeacherId() {
        return teacherId;
    }

    public void setTeacherId(Long teacherId) {
        this.teacherId = teacherId;
    }

    public Subject getSubject() {
        return subject;
    }

    public void setSubject(Subject subject) {
        this.subject = subject;
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

    public String getRoom() {
        return room;
    }

    public void setRoom(String room) {
        this.room = room;
    }

    public Boolean getActive() {
        return active;
    }

    public void setActive(Boolean active) {
        this.active = active;
    }
}