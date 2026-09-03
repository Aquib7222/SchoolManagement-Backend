package com.schoolmanagement.schoolmanagementwebsite.service.Transport;

import java.util.List;

import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import com.schoolmanagement.schoolmanagementwebsite.dto.Transport.DriverManagementRequest;
import com.schoolmanagement.schoolmanagementwebsite.dto.Transport.DriverManagementResponse;
import com.schoolmanagement.schoolmanagementwebsite.entity.Transport.Driver;
import com.schoolmanagement.schoolmanagementwebsite.enums.Transport.DriverStatus;

import com.schoolmanagement.schoolmanagementwebsite.repository.Transport.DriverRepository;

import lombok.RequiredArgsConstructor;

@Service
@RequiredArgsConstructor
@Transactional
public class DriverManagementService {

    private final DriverRepository driverRepository;


    // =====================================================
    // CREATE
    // =====================================================

    public DriverManagementResponse createDriver(
            DriverManagementRequest request) {

        validateRequest(request);

        if (driverRepository.existsBySchoolIdAndLicenseNumber(
                request.getSchoolId(),
                request.getLicenseNumber())) {

            throw new RuntimeException(
                    "This license number is already registered."
            );
        }

        if (driverRepository.existsBySchoolIdAndMobileNumber(
                request.getSchoolId(),
                request.getMobileNumber())) {

            throw new RuntimeException(
                    "This mobile number is already registered."
            );
        }

        if (request.getVehicleId() != null &&
                driverRepository.existsBySchoolIdAndVehicleId(
                        request.getSchoolId(),
                        request.getVehicleId())) {

            throw new RuntimeException(
                    "This vehicle is already assigned to another driver."
            );
        }

        Driver driver = Driver.builder()
                .schoolId(request.getSchoolId())
                .driverName(request.getDriverName())
                .mobileNumber(request.getMobileNumber())
                .alternateMobile(request.getAlternateMobile())
                .licenseNumber(request.getLicenseNumber())
                .licenseType(request.getLicenseType())
                .licenseExpiryDate(request.getLicenseExpiryDate())
                .address(request.getAddress())
                .vehicleId(request.getVehicleId())
                .status(parseStatus(request.getStatus()))
                .build();

        Driver saved = driverRepository.save(driver);

        return mapToResponse(saved);
    }


    // =====================================================
    // GET ALL
    // =====================================================

    @Transactional(readOnly = true)
    public List<DriverManagementResponse> getDriversBySchool(
            Long schoolId) {

        return driverRepository
                .findBySchoolId(schoolId)
                .stream()
                .map(this::mapToResponse)
                .toList();
    }


    // =====================================================
    // GET BY ID
    // =====================================================

    @Transactional(readOnly = true)
    public DriverManagementResponse getDriverById(
            Long schoolId,
            Long id) {

        Driver driver = driverRepository
                .findByIdAndSchoolId(id, schoolId)
                .orElseThrow(() ->
                        new RuntimeException(
                                "Driver not found."
                        ));

        return mapToResponse(driver);
    }


    // =====================================================
    // UPDATE
    // =====================================================

    public DriverManagementResponse updateDriver(
            Long schoolId,
            Long id,
            DriverManagementRequest request) {

        Driver driver = driverRepository
                .findByIdAndSchoolId(id, schoolId)
                .orElseThrow(() ->
                        new RuntimeException(
                                "Driver not found."
                        ));

        validateRequest(request);

        // -----------------------------------------
        // LICENSE DUPLICATE
        // -----------------------------------------

        if (driverRepository
                .existsBySchoolIdAndLicenseNumberAndIdNot(
                        schoolId,
                        request.getLicenseNumber(),
                        id)) {

            throw new RuntimeException(
                    "This license number is already registered."
            );
        }


        // -----------------------------------------
        // MOBILE DUPLICATE
        // -----------------------------------------

        if (driverRepository
                .existsBySchoolIdAndMobileNumberAndIdNot(
                        schoolId,
                        request.getMobileNumber(),
                        id)) {

            throw new RuntimeException(
                    "This mobile number is already registered."
            );
        }


        // -----------------------------------------
        // VEHICLE DUPLICATE
        // -----------------------------------------

        if (request.getVehicleId() != null &&
                driverRepository
                        .existsBySchoolIdAndVehicleIdAndIdNot(
                                schoolId,
                                request.getVehicleId(),
                                id)) {

            throw new RuntimeException(
                    "This vehicle is already assigned to another driver."
            );
        }


        driver.setDriverName(
                request.getDriverName()
        );

        driver.setMobileNumber(
                request.getMobileNumber()
        );

        driver.setAlternateMobile(
                request.getAlternateMobile()
        );

        driver.setLicenseNumber(
                request.getLicenseNumber()
        );

        driver.setLicenseType(
                request.getLicenseType()
        );

        driver.setLicenseExpiryDate(
                request.getLicenseExpiryDate()
        );

        driver.setAddress(
                request.getAddress()
        );

        driver.setVehicleId(
                request.getVehicleId()
        );

        driver.setStatus(
                parseStatus(request.getStatus())
        );

        Driver updated =
                driverRepository.save(driver);

        return mapToResponse(updated);
    }


    // =====================================================
    // DELETE
    // =====================================================

    public void deleteDriver(
            Long schoolId,
            Long id) {

        Driver driver = driverRepository
                .findByIdAndSchoolId(id, schoolId)
                .orElseThrow(() ->
                        new RuntimeException(
                                "Driver not found."
                        ));

        driverRepository.delete(driver);
    }


    // =====================================================
    // TOGGLE STATUS
    // =====================================================

    public DriverManagementResponse toggleStatus(
            Long schoolId,
            Long id) {

        Driver driver = driverRepository
                .findByIdAndSchoolId(id, schoolId)
                .orElseThrow(() ->
                        new RuntimeException(
                                "Driver not found."
                        ));

        if (driver.getStatus() == DriverStatus.ACTIVE) {
            driver.setStatus(DriverStatus.INACTIVE);
        } else {
            driver.setStatus(DriverStatus.ACTIVE);
        }

        Driver updated =
                driverRepository.save(driver);

        return mapToResponse(updated);
    }


    // =====================================================
    // VALIDATION
    // =====================================================

    private void validateRequest(
            DriverManagementRequest request) {

        if (request.getSchoolId() == null) {
            throw new RuntimeException(
                    "School ID is required."
            );
        }

        if (request.getDriverName() == null ||
                request.getDriverName().isBlank()) {

            throw new RuntimeException(
                    "Driver name is required."
            );
        }

        if (request.getMobileNumber() == null ||
                request.getMobileNumber().isBlank()) {

            throw new RuntimeException(
                    "Mobile number is required."
            );
        }

        if (request.getLicenseNumber() == null ||
                request.getLicenseNumber().isBlank()) {

            throw new RuntimeException(
                    "License number is required."
            );
        }

        if (request.getLicenseType() == null ||
                request.getLicenseType().isBlank()) {

            throw new RuntimeException(
                    "License type is required."
            );
        }

        if (request.getLicenseExpiryDate() == null) {
            throw new RuntimeException(
                    "License expiry date is required."
            );
        }
    }


    // =====================================================
    // STATUS
    // =====================================================

    private DriverStatus parseStatus(
            String status) {

        if (status == null ||
                status.isBlank()) {

            return DriverStatus.ACTIVE;
        }

        try {

            return DriverStatus.valueOf(
                    status.toUpperCase()
            );

        } catch (IllegalArgumentException e) {

            throw new RuntimeException(
                    "Invalid driver status."
            );
        }
    }


    // =====================================================
    // MAPPER
    // =====================================================

    private DriverManagementResponse mapToResponse(
            Driver driver) {

        return DriverManagementResponse.builder()
                .id(driver.getId())
                .schoolId(driver.getSchoolId())
                .driverName(driver.getDriverName())
                .mobileNumber(driver.getMobileNumber())
                .alternateMobile(driver.getAlternateMobile())
                .licenseNumber(driver.getLicenseNumber())
                .licenseType(driver.getLicenseType())
                .licenseExpiryDate(
                        driver.getLicenseExpiryDate()
                )
                .address(driver.getAddress())
                .vehicleId(driver.getVehicleId())
                .vehicleNumber(null)
                .vehicleType(null)
                .status(
                        driver.getStatus().name()
                )
                .build();
    }
}