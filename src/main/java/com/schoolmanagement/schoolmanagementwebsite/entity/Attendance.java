package com.schoolmanagement.schoolmanagementwebsite.entity;

import jakarta.persistence.*;
import lombok.*;

import java.time.LocalDate;
import java.time.LocalDateTime;

import com.schoolmanagement.schoolmanagementwebsite.enums.AttendanceStatus;
import com.schoolmanagement.schoolmanagementwebsite.enums.Section;

@Entity
@Table(
        name = "student_attendance",
        uniqueConstraints = {
                @UniqueConstraint(columnNames = {
                        "school_id",
                        "student_id",
                        "attendance_date"
                })
        }
)
@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
@Builder
public class Attendance {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    // School Details
    @Column(name = "school_id", nullable = false)
    private Long schoolId;

    // Student Details
    @Column(name = "student_id", nullable = false)
    private Long studentId;

    @Column(nullable = false)
    private String admissionNumber;

    @Column(nullable = false)
    private String academicYear;

    @Column(nullable = false)
    private String studentClass;

    @Enumerated(EnumType.STRING)
@Column(nullable = false)
private Section section;

    // Attendance Details
    @Column(name = "attendance_date", nullable = false)
    private LocalDate attendanceDate;

    @Enumerated(EnumType.STRING)
    @Column(nullable = false)
    private AttendanceStatus status;

    private String remarks;

    // Audit Fields
    private Long createdByUserId;

    private String createdBy;

    private LocalDateTime createdAt;

    private Long updatedByUserId;

    private String updatedBy;

    private LocalDateTime updatedAt;

}