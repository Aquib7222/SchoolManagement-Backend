package com.schoolmanagement.schoolmanagementwebsite.dto;

import lombok.Data;

@Data
public class TeacherAttendanceRequestDTO {
    private Long teacherId;
    private String status; // PRESENT, ABSENT, HALF_DAY, LEAVE
}
