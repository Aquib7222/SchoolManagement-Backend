package com.schoolmanagement.schoolmanagementwebsite.controller.fee;

import java.util.List;

import org.springframework.http.ResponseEntity;
import org.springframework.security.core.Authentication;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.schoolmanagement.schoolmanagementwebsite.dto.fee.AssignFeeRequest;
import com.schoolmanagement.schoolmanagementwebsite.entity.fee.StudentFee;
import com.schoolmanagement.schoolmanagementwebsite.service.fee.StudentFeeService;

import lombok.RequiredArgsConstructor;

@RestController
@RequestMapping("/api/student-fee")
@RequiredArgsConstructor
@CrossOrigin(origins = "http://localhost:5173")
public class StudentFeeController {

    private final StudentFeeService studentFeeService;

    
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

     @GetMapping("/school/{schoolId}")
    public ResponseEntity<List<StudentFee>> getFeesBySchool(
            @PathVariable Long schoolId) {

        return ResponseEntity.ok(
                studentFeeService.getFeesBySchoolId(schoolId)
        );
    }

    @GetMapping("/all-Fee")
    public ResponseEntity<List<StudentFee>> getAllFees() {

        return ResponseEntity.ok(
                studentFeeService.getAllFees()
        );
    }

}
