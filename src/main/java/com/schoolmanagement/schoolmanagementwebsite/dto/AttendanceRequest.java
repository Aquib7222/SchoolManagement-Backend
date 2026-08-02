package com.schoolmanagement.schoolmanagementwebsite.dto;

import com.schoolmanagement.schoolmanagementwebsite.dto.AttendanceItem;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import java.time.LocalDate;
import java.util.List;

import com.schoolmanagement.schoolmanagementwebsite.enums.Section;

@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
@Builder
public class AttendanceRequest {

    private LocalDate attendanceDate;

    private String academicYear;

    private String studentClass;

    private Section section;

    private List<AttendanceItem> attendance;

}