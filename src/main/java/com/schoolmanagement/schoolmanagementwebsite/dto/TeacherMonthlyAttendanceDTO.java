package com.schoolmanagement.schoolmanagementwebsite.dto;

import lombok.AllArgsConstructor;
import lombok.Data;

@Data
@AllArgsConstructor
public class TeacherMonthlyAttendanceDTO {

    private Long teacherId;
    private String teacherName;

    private long present;
    private long absent;
    private long leave;
    private long halfDay;
}
