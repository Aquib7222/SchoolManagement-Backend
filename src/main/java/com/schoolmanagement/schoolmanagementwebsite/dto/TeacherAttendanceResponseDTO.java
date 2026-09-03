package com.schoolmanagement.schoolmanagementwebsite.dto;

import java.time.LocalDate;
import java.time.LocalDateTime;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
@AllArgsConstructor
public class TeacherAttendanceResponseDTO {

   private Long id;

    private Long teacherId;

    private String teacherName;

    private Long schoolId;

    private LocalDate attendanceDate;

    private String status;

    private LocalDateTime checkInTime;

    private LocalDateTime checkOutTime;
}