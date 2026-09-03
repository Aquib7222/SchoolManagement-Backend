package com.schoolmanagement.schoolmanagementwebsite.repository.Transport;

import java.util.List;
import java.util.Optional;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import com.schoolmanagement.schoolmanagementwebsite.entity.Transport.VehicleManagement;

@Repository
public interface VehicleManagementRepository
        extends JpaRepository<VehicleManagement, Long> {

    Optional<VehicleManagement> findBySchoolIdAndVehicleNumber(
            Long schoolId,
            String vehicleNumber
    );

    List<VehicleManagement> findBySchoolId(Long schoolId);

    boolean existsBySchoolIdAndVehicleNumber(
            Long schoolId,
            String vehicleNumber
    );
}