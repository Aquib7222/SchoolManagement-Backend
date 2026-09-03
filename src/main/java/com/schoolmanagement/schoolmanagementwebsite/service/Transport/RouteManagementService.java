package com.schoolmanagement.schoolmanagementwebsite.service.Transport;

import java.util.List;

import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import com.schoolmanagement.schoolmanagementwebsite.dto.Transport.RouteManagementRequest;
import com.schoolmanagement.schoolmanagementwebsite.dto.Transport.RouteManagementResponse;
import com.schoolmanagement.schoolmanagementwebsite.entity.Transport.RouteManagement;
import com.schoolmanagement.schoolmanagementwebsite.repository.Transport.RouteManagementRepository;

import lombok.RequiredArgsConstructor;

@Service
@RequiredArgsConstructor
public class RouteManagementService {

    private final RouteManagementRepository routeRepository;


    // =====================================================
    // CREATE ROUTE
    // =====================================================

    @Transactional
    public RouteManagementResponse createRoute(
            RouteManagementRequest request) {

        if (routeRepository.existsBySchoolIdAndRouteName(
                request.getSchoolId(),
                request.getRouteName())) {

            throw new RuntimeException(
                    "Route name already exists for this school"
            );
        }

        RouteManagement route = RouteManagement.builder()
                .schoolId(request.getSchoolId())
                .routeName(request.getRouteName())
                .startLocation(request.getStartLocation())
                .endLocation(request.getEndLocation())
                .stops(request.getStops())
                .status(request.getStatus())
                .build();

        RouteManagement savedRoute =
                routeRepository.save(route);

        return mapToResponse(savedRoute);
    }


    // =====================================================
    // GET ROUTE BY ID
    // =====================================================

    public RouteManagementResponse getRouteById(
            Long schoolId,
            Long id) {

        RouteManagement route = routeRepository
                .findById(id)
                .orElseThrow(() ->
                        new RuntimeException("Route not found"));

        if (!route.getSchoolId().equals(schoolId)) {
            throw new RuntimeException("Route not found");
        }

        return mapToResponse(route);
    }


    // =====================================================
    // GET ROUTE BY NAME
    // =====================================================

    public RouteManagementResponse getRouteByName(
            Long schoolId,
            String routeName) {

        RouteManagement route =
                routeRepository
                        .findBySchoolIdAndRouteName(
                                schoolId,
                                routeName
                        )
                        .orElseThrow(() ->
                                new RuntimeException(
                                        "Route not found"
                                ));

        return mapToResponse(route);
    }


    // =====================================================
    // GET ALL ROUTES BY SCHOOL
    // =====================================================

    public List<RouteManagementResponse> getRoutesBySchool(
            Long schoolId) {

        return routeRepository
                .findBySchoolId(schoolId)
                .stream()
                .map(this::mapToResponse)
                .toList();
    }


    // =====================================================
    // UPDATE ROUTE
    // =====================================================

    @Transactional
    public RouteManagementResponse updateRoute(
            Long schoolId,
            Long id,
            RouteManagementRequest request) {

        RouteManagement route = routeRepository
                .findById(id)
                .orElseThrow(() ->
                        new RuntimeException(
                                "Route not found"
                        ));

        if (!route.getSchoolId().equals(schoolId)) {
            throw new RuntimeException("Route not found");
        }


        // Check duplicate route name
        if (!route.getRouteName()
                .equals(request.getRouteName())) {

            if (routeRepository
                    .existsBySchoolIdAndRouteName(
                            schoolId,
                            request.getRouteName())) {

                throw new RuntimeException(
                        "Route name already exists for this school"
                );
            }
        }


        route.setRouteName(
                request.getRouteName()
        );

        route.setStartLocation(
                request.getStartLocation()
        );

        route.setEndLocation(
                request.getEndLocation()
        );

        route.setStops(
                request.getStops()
        );

        route.setStatus(
                request.getStatus()
        );


        RouteManagement updatedRoute =
                routeRepository.save(route);

        return mapToResponse(updatedRoute);
    }


    // =====================================================
    // DELETE ROUTE
    // =====================================================

    @Transactional
    public void deleteRoute(
            Long schoolId,
            Long id) {

        RouteManagement route = routeRepository
                .findById(id)
                .orElseThrow(() ->
                        new RuntimeException(
                                "Route not found"
                        ));

        if (!route.getSchoolId().equals(schoolId)) {
            throw new RuntimeException("Route not found");
        }

        routeRepository.delete(route);
    }


    // =====================================================
    // ENTITY → RESPONSE
    // =====================================================

    private RouteManagementResponse mapToResponse(
            RouteManagement route) {

        return RouteManagementResponse.builder()
                .id(route.getId())
                .schoolId(route.getSchoolId())
                .routeName(route.getRouteName())
                .startLocation(route.getStartLocation())
                .endLocation(route.getEndLocation())
                .stops(route.getStops())
                .status(route.getStatus())
                .createdAt(route.getCreatedAt())
                .updatedAt(route.getUpdatedAt())
                .build();
    }
}



