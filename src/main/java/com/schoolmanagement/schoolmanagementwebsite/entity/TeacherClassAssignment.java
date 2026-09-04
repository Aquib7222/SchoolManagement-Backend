package com.schoolmanagement.schoolmanagementwebsite.entity;
import java.time.LocalTime;

import com.schoolmanagement.schoolmanagementwebsite.enums.Subject;

import jakarta.persistence.*;

@Entity
@Table(
    name = "teacher_class_assignment",
    indexes = {
        @Index(
            name = "idx_teacher_assignment_school_session_day",
            columnList = "school_id, academic_year, day_of_week"
        ),
        @Index(
            name = "idx_teacher_assignment_teacher_period",
            columnList = "teacher_id, period_id"
        ),
        @Index(
            name = "idx_teacher_assignment_class_section_period",
            columnList = "student_class, section, period_id"
        )
    }
)
public class TeacherClassAssignment {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @Column(name = "school_id", nullable = false)
    private Long schoolId;

    @Column(name = "academic_year", nullable = false)
    private String academicYear;

    @Column(name = "teacher_id", nullable = false)
    private Long teacherId;

    @Enumerated(EnumType.STRING)
    @Column(name = "day_of_week", nullable = false)
    private java.time.DayOfWeek dayOfWeek;

    @Column(name = "period_id", nullable = false)
    private Long periodId;

    @Column(name = "period_name", nullable = false)
private String periodName;

    public String getPeriodName() {
        return periodName;
    }

    public void setPeriodName(String periodName) {
        this.periodName = periodName;
    }

    @Enumerated(EnumType.STRING)
    @Column(name = "subject", nullable = false)
    private Subject subject;

    @Column(name = "student_class", nullable = false)
    private String studentClass;

    @Column(name = "section", nullable = false)
    private String section;

    @Column(name = "room")
    private String room;

    @Column(nullable = false)
    private Boolean active = true;


    @Column(name = "start_time", nullable = false)
private LocalTime startTime;

@Column(name = "end_time", nullable = false)
private LocalTime endTime;

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

    public TeacherClassAssignment() {
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

    public String getAcademicYear() {
        return academicYear;
    }

    public void setAcademicYear(String academicYear) {
        this.academicYear = academicYear;
    }

    public Long getTeacherId() {
        return teacherId;
    }

    public void setTeacherId(Long teacherId) {
        this.teacherId = teacherId;
    }

    public java.time.DayOfWeek getDayOfWeek() {
        return dayOfWeek;
    }

    public void setDayOfWeek(java.time.DayOfWeek dayOfWeek) {
        this.dayOfWeek = dayOfWeek;
    }

    public Long getPeriodId() {
        return periodId;
    }

    public void setPeriodId(Long periodId) {
        this.periodId = periodId;
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