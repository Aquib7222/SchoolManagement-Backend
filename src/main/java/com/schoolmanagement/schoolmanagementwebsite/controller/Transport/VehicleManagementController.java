package com.schoolmanagement.schoolmanagementwebsite.controller.Transport;

import java.util.List;

import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import com.schoolmanagement.schoolmanagementwebsite.dto.Transport.VehicleManagementRequest;
import com.schoolmanagement.schoolmanagementwebsite.dto.Transport.VehicleManagementResponse;
import com.schoolmanagement.schoolmanagementwebsite.service.Transport.VehicleManagementService;

import lombok.RequiredArgsConstructor;

@RestController
@RequestMapping("/api/transport/vehicles")
@RequiredArgsConstructor
public class VehicleManagementController {

    private final VehicleManagementService vehicleService;


    // =========================
    // CREATE VEHICLE
    // =========================
    @PostMapping
    public ResponseEntity<VehicleManagementResponse> createVehicle(
            @RequestBody VehicleManagementRequest request) {

        VehicleManagementResponse response =
                vehicleService.createVehicle(request);

        return ResponseEntity
                .status(HttpStatus.CREATED)
                .body(response);
    }


    // =========================
    // GET ALL VEHICLES
    // =========================
    @GetMapping
    public ResponseEntity<List<VehicleManagementResponse>> getVehicles(
            @RequestParam Long schoolId) {

        return ResponseEntity.ok(
                vehicleService.getVehiclesBySchool(schoolId)
        );
    }


    // =========================
    // GET VEHICLE BY NUMBER
    // =========================
    @GetMapping("/number")
    public ResponseEntity<VehicleManagementResponse> getVehicleByNumber(
            @RequestParam Long schoolId,
            @RequestParam String vehicleNumber) {

        return ResponseEntity.ok(
                vehicleService.getVehicleByNumber(
                        schoolId,
                        vehicleNumber
                )
        );
    }


    // =========================
    // GET VEHICLE BY ID
    // =========================
    @GetMapping("/{id}")
    public ResponseEntity<VehicleManagementResponse> getVehicleById(
            @PathVariable Long id,
            @RequestParam Long schoolId) {

        return ResponseEntity.ok(
                vehicleService.getVehicleById(
                        schoolId,
                        id
                )
        );
    }


    // =========================
    // UPDATE VEHICLE
    // =========================
    @PutMapping("/{id}")
    public ResponseEntity<VehicleManagementResponse> updateVehicle(
            @PathVariable Long id,
            @RequestParam Long schoolId,
            @RequestBody VehicleManagementRequest request) {

        return ResponseEntity.ok(
                vehicleService.updateVehicle(
                        schoolId,
                        id,
                        request
                )
        );
    }


    // =========================
    // DELETE VEHICLE
    // =========================
    @DeleteMapping("/{id}")
    public ResponseEntity<Void> deleteVehicle(
            @PathVariable Long id,
            @RequestParam Long schoolId) {

        vehicleService.deleteVehicle(
                schoolId,
                id
        );

        return ResponseEntity.noContent().build();
    }
}