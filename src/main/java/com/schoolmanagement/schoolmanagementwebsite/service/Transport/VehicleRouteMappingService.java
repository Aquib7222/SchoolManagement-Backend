package com.schoolmanagement.schoolmanagementwebsite.service.Transport;

import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import com.schoolmanagement.schoolmanagementwebsite.dto.Transport.StopSearchResponse;
import com.schoolmanagement.schoolmanagementwebsite.dto.Transport.VehicleRouteMappingRequest;
import com.schoolmanagement.schoolmanagementwebsite.dto.Transport.VehicleRouteMappingResponse;
import com.schoolmanagement.schoolmanagementwebsite.entity.Transport.Driver;
import com.schoolmanagement.schoolmanagementwebsite.entity.Transport.RouteManagement;
import com.schoolmanagement.schoolmanagementwebsite.entity.Transport.VehicleManagement;
import com.schoolmanagement.schoolmanagementwebsite.entity.Transport.VehicleRouteMapping;
import com.schoolmanagement.schoolmanagementwebsite.repository.Transport.DriverRepository;
import com.schoolmanagement.schoolmanagementwebsite.repository.Transport.RouteManagementRepository;
import com.schoolmanagement.schoolmanagementwebsite.repository.Transport.VehicleManagementRepository;
import com.schoolmanagement.schoolmanagementwebsite.repository.Transport.VehicleRouteMappingRepository;

import lombok.RequiredArgsConstructor;

@Service
@RequiredArgsConstructor
public class VehicleRouteMappingService {

    private final VehicleRouteMappingRepository mappingRepository;

    private final VehicleManagementRepository vehicleRepository;

    private final RouteManagementRepository routeRepository;

    private final DriverRepository driverRepository;
    // =====================================================
    // CREATE ASSIGNMENT
    // =====================================================

    @Transactional
    public VehicleRouteMappingResponse assignRoute(
            VehicleRouteMappingRequest request) {

        Long schoolId = request.getSchoolId();

        // -----------------------------
        // VEHICLE CHECK
        // -----------------------------

        VehicleManagement vehicle =
                vehicleRepository
                        .findById(request.getVehicleId())
                        .orElseThrow(() ->
                                new RuntimeException(
                                        "Vehicle not found"
                                )
                        );

        if (!vehicle.getSchoolId().equals(schoolId)) {
            throw new RuntimeException(
                    "Vehicle does not belong to this school"
            );
        }

        // -----------------------------
        // ROUTE CHECK
        // -----------------------------

        RouteManagement route =
                routeRepository
                        .findById(request.getRouteId())
                        .orElseThrow(() ->
                                new RuntimeException(
                                        "Route not found"
                                )
                        );

        if (!route.getSchoolId().equals(schoolId)) {
            throw new RuntimeException(
                    "Route does not belong to this school"
            );
        }

        // -----------------------------
        // VEHICLE ALREADY ASSIGNED?
        // -----------------------------

        if (mappingRepository
                .existsBySchoolIdAndVehicleId(
                        schoolId,
                        request.getVehicleId())) {

            throw new RuntimeException(
                    "This vehicle is already assigned to a route"
            );
        }

        // -----------------------------
        // CREATE
        // -----------------------------

        VehicleRouteMapping mapping =
                VehicleRouteMapping.builder()
                        .schoolId(schoolId)
                        .vehicleId(request.getVehicleId())
                        .routeId(request.getRouteId())
                        .status(
                                request.getStatus() != null
                                        ? request.getStatus()
                                        : com.schoolmanagement.schoolmanagementwebsite.enums.Transport.VehicleRouteStatus.ACTIVE
                        )
                        .assignedAt(LocalDateTime.now())
                        .updatedAt(LocalDateTime.now())
                        .build();

        VehicleRouteMapping saved =
                mappingRepository.save(mapping);

        return mapToResponse(
                saved,
                vehicle,
                route
        );
    }


    // =====================================================
    // GET ALL
    // =====================================================

    public List<VehicleRouteMappingResponse> getAllMappings(
            Long schoolId) {

        return mappingRepository
                .findBySchoolId(schoolId)
                .stream()
                .map(mapping -> {

                    VehicleManagement vehicle =
                            vehicleRepository
                                    .findById(mapping.getVehicleId())
                                    .orElse(null);

                    RouteManagement route =
                            routeRepository
                                    .findById(mapping.getRouteId())
                                    .orElse(null);

                    return mapToResponse(
                            mapping,
                            vehicle,
                            route
                    );

                })
                .toList();
    }


    // =====================================================
    // GET BY ID
    // =====================================================

    public VehicleRouteMappingResponse getById(
            Long schoolId,
            Long id) {

        VehicleRouteMapping mapping =
                mappingRepository
                        .findBySchoolIdAndId(
                                schoolId,
                                id
                        )
                        .orElseThrow(() ->
                                new RuntimeException(
                                        "Route assignment not found"
                                )
                        );

        VehicleManagement vehicle =
                vehicleRepository
                        .findById(mapping.getVehicleId())
                        .orElseThrow(() ->
                                new RuntimeException(
                                        "Vehicle not found"
                                )
                        );

        RouteManagement route =
                routeRepository
                        .findById(mapping.getRouteId())
                        .orElseThrow(() ->
                                new RuntimeException(
                                        "Route not found"
                                )
                        );

        return mapToResponse(
                mapping,
                vehicle,
                route
        );
    }


    // =====================================================
    // UPDATE ASSIGNMENT
    // =====================================================

    @Transactional
    public VehicleRouteMappingResponse updateMapping(
            Long schoolId,
            Long id,
            VehicleRouteMappingRequest request) {

        VehicleRouteMapping mapping =
                mappingRepository
                        .findBySchoolIdAndId(
                                schoolId,
                                id
                        )
                        .orElseThrow(() ->
                                new RuntimeException(
                                        "Route assignment not found"
                                )
                        );

        // -----------------------------
        // VEHICLE
        // -----------------------------

        VehicleManagement vehicle =
                vehicleRepository
                        .findById(request.getVehicleId())
                        .orElseThrow(() ->
                                new RuntimeException(
                                        "Vehicle not found"
                                )
                        );

        if (!vehicle.getSchoolId().equals(schoolId)) {
            throw new RuntimeException(
                    "Vehicle does not belong to this school"
            );
        }

        // -----------------------------
        // ROUTE
        // -----------------------------

        RouteManagement route =
                routeRepository
                        .findById(request.getRouteId())
                        .orElseThrow(() ->
                                new RuntimeException(
                                        "Route not found"
                                )
                        );

        if (!route.getSchoolId().equals(schoolId)) {
            throw new RuntimeException(
                    "Route does not belong to this school"
            );
        }

        // -----------------------------
        // CHECK VEHICLE
        // -----------------------------

        VehicleRouteMapping existing =
                mappingRepository
                        .findBySchoolIdAndVehicleId(
                                schoolId,
                                request.getVehicleId()
                        )
                        .orElse(null);

        if (existing != null &&
                !existing.getId().equals(id)) {

            throw new RuntimeException(
                    "This vehicle is already assigned to another route"
            );
        }

        mapping.setVehicleId(
                request.getVehicleId()
        );

        mapping.setRouteId(
                request.getRouteId()
        );

        if (request.getStatus() != null) {
            mapping.setStatus(
                    request.getStatus()
            );
        }

        mapping.setUpdatedAt(
                LocalDateTime.now()
        );

        VehicleRouteMapping updated =
                mappingRepository.save(mapping);

        return mapToResponse(
                updated,
                vehicle,
                route
        );
    }


    // =====================================================
    // TOGGLE STATUS
    // =====================================================

    @Transactional
    public VehicleRouteMappingResponse toggleStatus(
            Long schoolId,
            Long id) {

        VehicleRouteMapping mapping =
                mappingRepository
                        .findBySchoolIdAndId(
                                schoolId,
                                id
                        )
                        .orElseThrow(() ->
                                new RuntimeException(
                                        "Route assignment not found"
                                )
                        );

        mapping.setStatus(
                mapping.getStatus()
                        == com.schoolmanagement.schoolmanagementwebsite.enums.Transport.VehicleRouteStatus.ACTIVE
                        ? com.schoolmanagement.schoolmanagementwebsite.enums.Transport.VehicleRouteStatus.INACTIVE
                        : com.schoolmanagement.schoolmanagementwebsite.enums.Transport.VehicleRouteStatus.ACTIVE
        );

        mapping.setUpdatedAt(
                LocalDateTime.now()
        );

        VehicleRouteMapping updated =
                mappingRepository.save(mapping);

        VehicleManagement vehicle =
                vehicleRepository
                        .findById(mapping.getVehicleId())
                        .orElseThrow();

        RouteManagement route =
                routeRepository
                        .findById(mapping.getRouteId())
                        .orElseThrow();

        return mapToResponse(
                updated,
                vehicle,
                route
        );
    }


    // =====================================================
    // DELETE
    // =====================================================

    @Transactional
    public void deleteMapping(
            Long schoolId,
            Long id) {

        VehicleRouteMapping mapping =
                mappingRepository
                        .findBySchoolIdAndId(
                                schoolId,
                                id
                        )
                        .orElseThrow(() ->
                                new RuntimeException(
                                        "Route assignment not found"
                                )
                        );

        mappingRepository.delete(mapping);
    }


    // =====================================================
    // RESPONSE MAPPER
    // =====================================================

    private VehicleRouteMappingResponse mapToResponse(
            VehicleRouteMapping mapping,
            VehicleManagement vehicle,
            RouteManagement route) {

        return VehicleRouteMappingResponse.builder()
                .id(mapping.getId())
                .schoolId(mapping.getSchoolId())

                .vehicleId(mapping.getVehicleId())
                .vehicleNumber(
                        vehicle != null
                                ? vehicle.getVehicleNumber()
                                : null
                )

                .routeId(mapping.getRouteId())
                .routeName(
                        route != null
                                ? route.getRouteName()
                                : null
                )
                .startLocation(
                        route != null
                                ? route.getStartLocation()
                                : null
                )
                .endLocation(
                        route != null
                                ? route.getEndLocation()
                                : null
                )
                .stops(
                        route != null
                                ? route.getStops()
                                : null
                )

                .status(mapping.getStatus())
                .assignedAt(mapping.getAssignedAt())
                .updatedAt(mapping.getUpdatedAt())

                .build();
    }

public List<StopSearchResponse> searchByStop(
        Long schoolId,
        String stop) {

    if (stop == null || stop.trim().isEmpty()) {
        return List.of();
    }

    String searchText = stop.trim().toLowerCase();

    // ==========================================
    // GET ALL VEHICLE-ROUTE MAPPINGS
    // ==========================================

    List<VehicleRouteMapping> mappings =
            mappingRepository.findBySchoolId(schoolId);

    List<StopSearchResponse> result =
            new ArrayList<>();

    // ==========================================
    // LOOP THROUGH MAPPINGS
    // ==========================================

    for (VehicleRouteMapping mapping : mappings) {

        // ======================================
        // GET ROUTE
        // ======================================

        RouteManagement route =
                routeRepository.findById(mapping.getRouteId())
                        .orElse(null);

        if (route == null) {
            continue;
        }

        // School security check
        if (!schoolId.equals(route.getSchoolId())) {
            continue;
        }

        // ======================================
        // FIND MATCHING STOP
        // ======================================

        String matchingStop =
                findMatchingStop(
                        route.getStops(),
                        searchText
                );

        if (matchingStop == null) {
            continue;
        }

        // ======================================
        // GET VEHICLE
        // ======================================

        VehicleManagement vehicle =
                vehicleRepository
                        .findById(mapping.getVehicleId())
                        .orElse(null);

        if (vehicle == null) {
            continue;
        }

        // School security check
        if (!schoolId.equals(vehicle.getSchoolId())) {
            continue;
        }

        // ======================================
        // GET DRIVER
        // ======================================

        Driver driver =
                driverRepository
                        .findBySchoolIdAndVehicleId(
                                schoolId,
                                vehicle.getId()
                        )
                        .orElse(null);

        // ======================================
        // BUILD RESPONSE
        // ======================================

        StopSearchResponse response =
                StopSearchResponse.builder()

                        // Route
                        .routeId(route.getId())
                        .routeName(route.getRouteName())

                        // Stop
                        .stopName(matchingStop)
                        .location(matchingStop)

                        // Vehicle
                        .vehicleId(vehicle.getId())
                        .vehicleNumber(
                                vehicle.getVehicleNumber()
                        )
                        .vehicleType(
                                vehicle.getVehicleType()
                        )
                        .vehicleModel(
                                vehicle.getVehicleModel()
                        )

                        // Driver
                        .driverId(
                                driver != null
                                        ? driver.getId()
                                        : null
                        )
                        .driverName(
                                driver != null
                                        ? driver.getDriverName()
                                        : null
                        )
                        .driverPhone(
                                driver != null
                                        ? driver.getMobileNumber()
                                        : null
                        )
                        .driverStatus(
                                driver != null &&
                                driver.getStatus() != null
                                        ? driver.getStatus().name()
                                        : null
                        )

                        // Status
                        .routeStatus(
                                route.getStatus() != null
                                        ? route.getStatus().name()
                                        : null
                        )
                        .vehicleStatus(
                                vehicle.getStatus() != null
                                        ? vehicle.getStatus().name()
                                        : null
                        )
                        .mappingStatus(
                                mapping.getStatus() != null
                                        ? mapping.getStatus().name()
                                        : null
                        )

                        .build();

        result.add(response);
    }

    return result;
}

private String findMatchingStop(
        String stops,
        String searchText) {

    if (stops == null || stops.trim().isEmpty()) {
        return null;
    }

    return Arrays.stream(stops.split(","))
            .map(String::trim)
            .filter(s ->
                    s.toLowerCase()
                     .contains(searchText)
            )
            .findFirst()
            .orElse(null);
}

}