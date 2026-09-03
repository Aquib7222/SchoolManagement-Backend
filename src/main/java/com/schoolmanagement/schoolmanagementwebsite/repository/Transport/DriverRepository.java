package com.schoolmanagement.schoolmanagementwebsite.repository.Transport;

import java.util.List;
import java.util.Optional;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import com.schoolmanagement.schoolmanagementwebsite.entity.Transport.Driver;

@Repository
public interface DriverRepository
        extends JpaRepository<Driver, Long> {

    List<Driver> findBySchoolId(Long schoolId);

    Optional<Driver> findByIdAndSchoolId(
            Long id,
            Long schoolId
    );

    boolean existsBySchoolIdAndLicenseNumber(
            Long schoolId,
            String licenseNumber
    );

    boolean existsBySchoolIdAndLicenseNumberAndIdNot(
            Long schoolId,
            String licenseNumber,
            Long id
    );

    boolean existsBySchoolIdAndMobileNumber(
            Long schoolId,
            String mobileNumber
    );

    boolean existsBySchoolIdAndMobileNumberAndIdNot(
            Long schoolId,
            String mobileNumber,
            Long id
    );

    boolean existsBySchoolIdAndVehicleId(
            Long schoolId,
            Long vehicleId
    );

    boolean existsBySchoolIdAndVehicleIdAndIdNot(
            Long schoolId,
            Long vehicleId,
            Long id
    );

    Optional<Driver> findBySchoolIdAndVehicleId(
        Long schoolId,
        Long vehicleId
);
}