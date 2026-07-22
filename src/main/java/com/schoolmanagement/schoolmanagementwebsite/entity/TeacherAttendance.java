package com.schoolmanagement.schoolmanagementwebsite.entity;

import java.time.LocalDate;
import com.schoolmanagement.schoolmanagementwebsite.enums.TeacherAttendanceStatus;

import jakarta.persistence.*;
import lombok.Getter;
import lombok.Setter;

@Entity
@Getter
@Setter
@Table(
    uniqueConstraints = @UniqueConstraint(
        columnNames = {"teacher_id", "attendanceDate"}
    )
)
public class TeacherAttendance {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @ManyToOne
    @JoinColumn(name = "teacher_id", nullable = false)
    private Teacher teacher;

    @ManyToOne
    @JoinColumn(name = "school_id", nullable = false)
    private School school;

    private LocalDate attendanceDate;

    @Enumerated(EnumType.STRING)
    private TeacherAttendanceStatus status;
}
