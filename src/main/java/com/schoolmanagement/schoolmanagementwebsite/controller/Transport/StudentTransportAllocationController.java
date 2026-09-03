package com.schoolmanagement.schoolmanagementwebsite.controller.Transport;

import java.util.List;

import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import com.schoolmanagement.schoolmanagementwebsite.dto.Transport.StudentTransportAllocationRequest;
import com.schoolmanagement.schoolmanagementwebsite.dto.Transport.StudentTransportAllocationResponse;
import com.schoolmanagement.schoolmanagementwebsite.dto.Transport.TransportStudentResponse;
import com.schoolmanagement.schoolmanagementwebsite.entity.Transport.RouteManagement;
import com.schoolmanagement.schoolmanagementwebsite.service.Transport.StudentTransportAllocationService;

import lombok.RequiredArgsConstructor;

@RestController
@RequestMapping("/api/transport/student-allocation")
@RequiredArgsConstructor
public class StudentTransportAllocationController {

    private final StudentTransportAllocationService
            allocationService;


    // =====================================================
    // GET STUDENTS
    // =====================================================

    @GetMapping("/students")
    public ResponseEntity<List<TransportStudentResponse>>
    getStudents(

            @RequestParam Long schoolId,

            @RequestParam String academicYear,

            @RequestParam String studentClass,

            @RequestParam String section) {

        return ResponseEntity.ok(
                allocationService.getStudents(
                        schoolId,
                        academicYear,
                        studentClass,
                        section
                )
        );
    }


    // =====================================================
    // GET ROUTES
    // =====================================================

    @GetMapping("/routes")
    public ResponseEntity<List<RouteManagement>>
    getRoutes(
            @RequestParam Long schoolId) {

        return ResponseEntity.ok(
                allocationService.getRoutes(
                        schoolId
                )
        );
    }


    // =====================================================
    // GET ALL ALLOCATIONS
    // =====================================================

    @GetMapping
    public ResponseEntity<
            List<StudentTransportAllocationResponse>>
    getAll(

            @RequestParam Long schoolId,

            @RequestParam String academicYear) {

        return ResponseEntity.ok(
                allocationService.getAllAllocations(
                        schoolId,
                        academicYear
                )
        );
    }


    // =====================================================
    // ASSIGN
    // =====================================================

    @PostMapping("/assign")
    public ResponseEntity<
            List<StudentTransportAllocationResponse>>
    assignStudents(

            @RequestBody
            StudentTransportAllocationRequest request) {

        return ResponseEntity.ok(
                allocationService.assignStudents(
                        request
                )
        );
    }


    // =====================================================
    // DELETE
    // =====================================================

    @DeleteMapping("/{id}")
    public ResponseEntity<Void> delete(

            @PathVariable Long id,

            @RequestParam Long schoolId) {

        allocationService.deleteAllocation(
                schoolId,
                id
        );

        return ResponseEntity.noContent().build();
    }
}