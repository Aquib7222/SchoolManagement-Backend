package com.schoolmanagement.schoolmanagementwebsite.repository.Transport;

import java.util.List;
import java.util.Optional;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;

import com.schoolmanagement.schoolmanagementwebsite.entity.Transport.VehicleRouteMapping;

public interface VehicleRouteMappingRepository
        extends JpaRepository<VehicleRouteMapping, Long> {

    boolean existsBySchoolIdAndVehicleId(
            Long schoolId,
            Long vehicleId
    );

    boolean existsBySchoolIdAndVehicleIdAndRouteId(
            Long schoolId,
            Long vehicleId,
            Long routeId
    );

    Optional<VehicleRouteMapping> findBySchoolIdAndId(
            Long schoolId,
            Long id
    );

    Optional<VehicleRouteMapping> findBySchoolIdAndVehicleId(
            Long schoolId,
            Long vehicleId
    );

    Optional<VehicleRouteMapping> findBySchoolIdAndRouteId(
            Long schoolId,
            Long routeId
    );

    List<VehicleRouteMapping> findBySchoolId(
            Long schoolId
    );

    void deleteBySchoolIdAndId(
            Long schoolId,
            Long id
    );

}