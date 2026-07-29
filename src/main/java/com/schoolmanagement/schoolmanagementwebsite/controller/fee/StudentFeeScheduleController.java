package com.schoolmanagement.schoolmanagementwebsite.controller.fee;

import java.util.List;

import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.schoolmanagement.schoolmanagementwebsite.dto.fee.GenerateFeeRequest;
import com.schoolmanagement.schoolmanagementwebsite.entity.fee.StudentFee;
import com.schoolmanagement.schoolmanagementwebsite.entity.fee.StudentFeeSchedule;
import com.schoolmanagement.schoolmanagementwebsite.service.fee.StudentFeeScheduleService;

import lombok.RequiredArgsConstructor;

@RestController
@RequestMapping("/api/student-fee")
@RequiredArgsConstructor
public class StudentFeeScheduleController {

    private final StudentFeeScheduleService studentFeeScheduleService;

    // =====================================
    // Generate Fee Schedule
    // =====================================
    @PostMapping("/generate")
    public ResponseEntity<String> generateFee(
            @RequestBody GenerateFeeRequest request) {

        studentFeeScheduleService.generateFee(request);

        return ResponseEntity.ok("Fee Generated Successfully");
    }
    // =====================================
    // Get Fee Schedule 
    // =====================================

    @GetMapping("/all")
    public ResponseEntity<List<StudentFeeSchedule>> getAll(){
        return ResponseEntity.ok(studentFeeScheduleService.getAll());
    }


    // =====================================
    // Student Current Schedule
    // =====================================
    @GetMapping("/schedule/{admissionNumber}")
    public ResponseEntity<List<StudentFeeSchedule>> getStudentSchedule(
            @PathVariable String admissionNumber) {

        return ResponseEntity.ok(
                studentFeeScheduleService.getStudentSchedule(admissionNumber));
    }

    // =====================================
    // Undo Generated Fee
    // =====================================
    @DeleteMapping("/undo")
    public ResponseEntity<String> undoFee(
            @RequestBody List<Long> ids) {

        studentFeeScheduleService.undoFee(ids);

        return ResponseEntity.ok("Undo Successfully");
    }

    // =====================================
    // New Schedule (Assigned Fee)
    // =====================================
    @GetMapping("/new-schedule/{admissionNumber}")
    public ResponseEntity<List<StudentFee>> getNewSchedule(
            @PathVariable String admissionNumber) {

        return ResponseEntity.ok(
                studentFeeScheduleService.getNewSchedule(admissionNumber));
    }

}
