package com.schoolmanagement.schoolmanagementwebsite.dto;

import com.schoolmanagement.schoolmanagementwebsite.enums.AttendanceStatus;
import lombok.Data;

import java.util.HashMap;
import java.util.Map;

@Data
public class AttendanceReportDTO {

    private Long studentId;

    private String admissionNumber;

    private String studentName;

    // Day -> Attendance Status
    // Example:
    // 1 -> PRESENT
    // 2 -> ABSENT
    // 3 -> LEAVE
    private Map<Integer, AttendanceStatus> attendance = new HashMap<>();

    private int present;

    private int absent;

    private int leave;

    private int halfDay;
}