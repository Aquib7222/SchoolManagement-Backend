package com.schoolmanagement.schoolmanagementwebsite.controller.fee;

import java.util.List;

import org.springframework.http.ResponseEntity;
import org.springframework.security.core.Authentication;
import org.springframework.web.bind.annotation.*;

import com.schoolmanagement.schoolmanagementwebsite.dto.fee.AssignFeeRequest;
import com.schoolmanagement.schoolmanagementwebsite.entity.fee.StudentFee;

import lombok.RequiredArgsConstructor;

import com.schoolmanagement.schoolmanagementwebsite.service.fee.StudentFeeService;

@RestController
@RequestMapping("/api/student-fee")
@RequiredArgsConstructor
@CrossOrigin(origins = "http://localhost:5173")
public class StudentFeeController {

    private final StudentFeeService studentFeeService;

    // ===============================
    // Assign Fee To Students
    // ===============================
    @PostMapping("/assign")
    public ResponseEntity<String> assignFee(
            @RequestBody AssignFeeRequest request) {

        studentFeeService.assignFee(request);

        return ResponseEntity.ok("Fee Assigned Successfully.");
    }

    @GetMapping("/{admissionNumber}")
    public ResponseEntity<List<StudentFee>> getStudentFee(
            @PathVariable String admissionNumber,
            Authentication authentication
    ) {

        String email = authentication.getName();

        return ResponseEntity.ok(
                studentFeeService.getStudentFee(email, admissionNumber)
        );

    }

    @GetMapping
    public ResponseEntity<List<StudentFee>> getAllStudentFees(
            Authentication authentication
    ) {

        return ResponseEntity.ok(
                studentFeeService.getAllStudentFees(
                        authentication.getName()
                )
        );
    }

}
