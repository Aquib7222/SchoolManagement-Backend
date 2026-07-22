package com.schoolmanagement.schoolmanagementwebsite.controller;

import java.time.LocalDate;
import java.util.List;

import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import com.schoolmanagement.schoolmanagementwebsite.dto.TeacherAttendanceRequestDTO;
import com.schoolmanagement.schoolmanagementwebsite.dto.TeacherMonthlyAttendanceDTO;
import com.schoolmanagement.schoolmanagementwebsite.entity.TeacherAttendance;
import com.schoolmanagement.schoolmanagementwebsite.service.TeacherAttendanceService;

import lombok.RequiredArgsConstructor;

@RestController
@RequestMapping("/api/teacher-attendance")
@RequiredArgsConstructor
public class TeacherAttendanceController {

    private final TeacherAttendanceService attendanceService;

    // ✅ SAVE (single / all / selected)
    @PostMapping
    public ResponseEntity<Void> saveAttendance(
            @RequestParam Long schoolId,
            @RequestParam String date,
            @RequestBody List<TeacherAttendanceRequestDTO> request) {

        attendanceService.saveAttendance(
                schoolId,
                LocalDate.parse(date),
                request
        );
        return ResponseEntity.ok().build();
    }

    // ✅ GET BY DATE
    @GetMapping
    public ResponseEntity<List<TeacherAttendance>> getByDate(
            @RequestParam Long schoolId,
            @RequestParam String date) {

        return ResponseEntity.ok(
                attendanceService.getAttendanceByDate(
                        schoolId, LocalDate.parse(date))
        );
    }

    // ✅ MONTHLY (Calendar)
    @GetMapping("/teacher/{teacherId}")
    public ResponseEntity<List<TeacherAttendance>> getByTeacher(
            @PathVariable Long teacherId) {

        return ResponseEntity.ok(
                attendanceService.getTeacherAttendance(teacherId)
        );
    }

    // ✅ MONTHLY SUMMARY REPORT
    @GetMapping("/monthly")
    public ResponseEntity<List<TeacherMonthlyAttendanceDTO>> getMonthlyReport(
            @RequestParam Long schoolId,
            @RequestParam String month) {

        // month = "2026-01"
        int year = Integer.parseInt(month.split("-")[0]);
        int monthValue = Integer.parseInt(month.split("-")[1]);

        return ResponseEntity.ok(
                attendanceService.getMonthlyReport(
                        schoolId, year, monthValue
                )
        );
    }

}
