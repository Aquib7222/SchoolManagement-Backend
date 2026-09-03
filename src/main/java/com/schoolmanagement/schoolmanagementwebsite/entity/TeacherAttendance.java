// package com.schoolmanagement.schoolmanagementwebsite.entity;

// import java.time.LocalDate;
// import java.time.LocalDateTime;

// import com.schoolmanagement.schoolmanagementwebsite.enums.TeacherAttendanceStatus;

// import jakarta.persistence.*;
// import lombok.Getter;
// import lombok.Setter;

// @Entity
// @Getter
// @Setter
// @Table(
//     uniqueConstraints = @UniqueConstraint(
//         columnNames = {"teacher_id", "attendanceDate"}
//     )
// )
// public class TeacherAttendance {

//     @Id
//     @GeneratedValue(strategy = GenerationType.IDENTITY)
//     private Long id;

//     @ManyToOne
//     @JoinColumn(name = "teacher_id", nullable = false)
//     private Teacher teacher;

//     @ManyToOne
//     @JoinColumn(name = "school_id", nullable = false)
//     private School school;

//     private LocalDate attendanceDate;

//     @Enumerated(EnumType.STRING)
//     private TeacherAttendanceStatus status;

//     @Column(name = "check_in_time")
//     private LocalDateTime checkInTime;

//     @Column(name = "check_out_time")
//     private LocalDateTime checkOutTime;
// }


package com.schoolmanagement.schoolmanagementwebsite.entity;

import java.time.LocalDate;
import java.time.LocalDateTime;

import com.schoolmanagement.schoolmanagementwebsite.enums.TeacherAttendanceStatus;

import jakarta.persistence.*;
import lombok.Getter;
import lombok.Setter;

@Entity
@Getter
@Setter
@Table(
    name = "teacher_attendance",
    uniqueConstraints = {
        @UniqueConstraint(
            name = "uk_teacher_attendance_daily",
            columnNames = {"teacher_id", "attendance_date"}
        )
    }
)
public class TeacherAttendance {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @ManyToOne(fetch = FetchType.LAZY, optional = false)
    @JoinColumn(
        name = "teacher_id",
        nullable = false
    )
    private Teacher teacher;

    @ManyToOne(fetch = FetchType.LAZY, optional = false)
    @JoinColumn(
        name = "school_id",
        nullable = false
    )
    private School school;

    @Column(name = "attendance_date", nullable = false)
    private LocalDate attendanceDate;

    @Enumerated(EnumType.STRING)
    @Column(nullable = false)
    private TeacherAttendanceStatus status;

    @Column(name = "check_in_time")
    private LocalDateTime checkInTime;

    @Column(name = "check_out_time")
    private LocalDateTime checkOutTime;
}