package com.schoolmanagement.schoolmanagementwebsite.dto;

import com.schoolmanagement.schoolmanagementwebsite.enums.AttendanceStatus;
import com.schoolmanagement.schoolmanagementwebsite.enums.StudentStatus;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
@Builder
public class DailyAttendanceReportDTO {
     private Long studentId;
    private String admissionNumber;
    private String studentName;
    private AttendanceStatus status;
}
