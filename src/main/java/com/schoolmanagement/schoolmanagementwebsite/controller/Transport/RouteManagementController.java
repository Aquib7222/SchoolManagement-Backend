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

import com.schoolmanagement.schoolmanagementwebsite.dto.Transport.RouteManagementRequest;
import com.schoolmanagement.schoolmanagementwebsite.dto.Transport.RouteManagementResponse;
import com.schoolmanagement.schoolmanagementwebsite.service.Transport.RouteManagementService;

import lombok.RequiredArgsConstructor;

@RestController
@RequestMapping("/api/transport/routes")
@RequiredArgsConstructor
public class RouteManagementController {

    private final RouteManagementService routeService;


    // =====================================================
    // CREATE
    // =====================================================

    @PostMapping
    public ResponseEntity<RouteManagementResponse> createRoute(
            @RequestBody RouteManagementRequest request) {

        RouteManagementResponse response =
                routeService.createRoute(request);

        return ResponseEntity
                .status(HttpStatus.CREATED)
                .body(response);
    }


    // =====================================================
    // GET ALL
    // =====================================================

    @GetMapping
    public ResponseEntity<List<RouteManagementResponse>> getRoutes(
            @RequestParam Long schoolId) {

        return ResponseEntity.ok(
                routeService.getRoutesBySchool(
                        schoolId
                )
        );
    }


    // =====================================================
    // GET BY NAME
    // =====================================================

    @GetMapping("/name")
    public ResponseEntity<RouteManagementResponse> getRouteByName(
            @RequestParam Long schoolId,
            @RequestParam String routeName) {

        return ResponseEntity.ok(
                routeService.getRouteByName(
                        schoolId,
                        routeName
                )
        );
    }


    // =====================================================
    // GET BY ID
    // =====================================================

    @GetMapping("/{id}")
    public ResponseEntity<RouteManagementResponse> getRouteById(
            @PathVariable Long id,
            @RequestParam Long schoolId) {

        return ResponseEntity.ok(
                routeService.getRouteById(
                        schoolId,
                        id
                )
        );
    }


    // =====================================================
    // UPDATE
    // =====================================================

    @PutMapping("/{id}")
    public ResponseEntity<RouteManagementResponse> updateRoute(
            @PathVariable Long id,
            @RequestParam Long schoolId,
            @RequestBody RouteManagementRequest request) {

        return ResponseEntity.ok(
                routeService.updateRoute(
                        schoolId,
                        id,
                        request
                )
        );
    }


    // =====================================================
    // DELETE
    // =====================================================

    @DeleteMapping("/{id}")
    public ResponseEntity<Void> deleteRoute(
            @PathVariable Long id,
            @RequestParam Long schoolId) {

        routeService.deleteRoute(
                schoolId,
                id
        );

        return ResponseEntity.noContent().build();
    }
}