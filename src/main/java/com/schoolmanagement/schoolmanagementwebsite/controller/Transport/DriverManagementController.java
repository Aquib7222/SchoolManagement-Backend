package com.schoolmanagement.schoolmanagementwebsite.controller.Transport;

import java.util.List;

import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import com.schoolmanagement.schoolmanagementwebsite.dto.Transport.DriverManagementRequest;
import com.schoolmanagement.schoolmanagementwebsite.dto.Transport.DriverManagementResponse;
import com.schoolmanagement.schoolmanagementwebsite.service.Transport.DriverManagementService;

import lombok.RequiredArgsConstructor;

@RestController
@RequestMapping("/api/transport/drivers")
@RequiredArgsConstructor
public class DriverManagementController {

    private final DriverManagementService driverService;


    // =====================================================
    // CREATE
    // =====================================================

    @PostMapping
    public ResponseEntity<DriverManagementResponse> createDriver(
            @RequestBody DriverManagementRequest request) {

        DriverManagementResponse response =
                driverService.createDriver(request);

        return ResponseEntity
                .status(HttpStatus.CREATED)
                .body(response);
    }


    // =====================================================
    // GET ALL
    // =====================================================

    @GetMapping
    public ResponseEntity<List<DriverManagementResponse>> getDrivers(
            @RequestParam Long schoolId) {

        return ResponseEntity.ok(
                driverService.getDriversBySchool(
                        schoolId
                )
        );
    }


    // =====================================================
    // GET BY ID
    // =====================================================

    @GetMapping("/{id}")
    public ResponseEntity<DriverManagementResponse> getDriverById(
            @PathVariable Long id,
            @RequestParam Long schoolId) {

        return ResponseEntity.ok(
                driverService.getDriverById(
                        schoolId,
                        id
                )
        );
    }


    // =====================================================
    // UPDATE
    // =====================================================

    @PutMapping("/{id}")
    public ResponseEntity<DriverManagementResponse> updateDriver(
            @PathVariable Long id,
            @RequestParam Long schoolId,
            @RequestBody DriverManagementRequest request) {

        return ResponseEntity.ok(
                driverService.updateDriver(
                        schoolId,
                        id,
                        request
                )
        );
    }


    // =====================================================
    // TOGGLE STATUS
    // =====================================================

    @PatchMapping("/{id}/status")
    public ResponseEntity<DriverManagementResponse> toggleStatus(
            @PathVariable Long id,
            @RequestParam Long schoolId) {

        return ResponseEntity.ok(
                driverService.toggleStatus(
                        schoolId,
                        id
                )
        );
    }


    // =====================================================
    // DELETE
    // =====================================================

    @DeleteMapping("/{id}")
    public ResponseEntity<Void> deleteDriver(
            @PathVariable Long id,
            @RequestParam Long schoolId) {

        driverService.deleteDriver(
                schoolId,
                id
        );

        return ResponseEntity
                .noContent()
                .build();
    }
}