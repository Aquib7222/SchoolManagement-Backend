package com.schoolmanagement.schoolmanagementwebsite.controller;


import java.util.List;
import com.schoolmanagement.schoolmanagementwebsite.dto.AttendanceRequest;
import com.schoolmanagement.schoolmanagementwebsite.dto.AttendanceReportDTO;
import com.schoolmanagement.schoolmanagementwebsite.entity.Attendance;
import com.schoolmanagement.schoolmanagementwebsite.service.AttendanceService;
import java.time.Month;
import lombok.RequiredArgsConstructor;

import org.springframework.http.ResponseEntity;
import org.springframework.security.core.Authentication;
import org.springframework.web.bind.annotation.*;

import com.schoolmanagement.schoolmanagementwebsite.enums.Section;

@RestController
@RequestMapping("/api/student/attendance")
@RequiredArgsConstructor
public class AttendanceController {

    private final AttendanceService attendanceService;

    @PostMapping("/save")
    public ResponseEntity<String> saveAttendance(
            @RequestBody AttendanceRequest request,
            Authentication authentication) {

        attendanceService.saveAttendance(request, authentication);

        return ResponseEntity.ok("Attendance Saved Successfully");
    }

    @GetMapping("/class")
    public ResponseEntity<?> getClassAttendance(
            @RequestParam String academicYear,
            @RequestParam String studentClass,
            @RequestParam Section section,
            @RequestParam String attendanceDate,
            Authentication authentication) {

        return ResponseEntity.ok(
                attendanceService.getClassAttendance(
                        academicYear,
                        studentClass,
                        section,
                        attendanceDate,
                        authentication
                )
        );
    }
    @GetMapping("/monthly")
public ResponseEntity<List<AttendanceReportDTO>> getMonthlyAttendance(
        @RequestParam String academicYear,
        @RequestParam String studentClass,
        @RequestParam Section section,
        @RequestParam Month month,
        Authentication authentication
) {
    return ResponseEntity.ok(
            attendanceService.getMonthlyAttendance(
                    academicYear,
                    studentClass,
                    section,
                    month,
                    authentication
            )
    );
}
}