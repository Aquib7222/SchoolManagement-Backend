package com.schoolmanagement.schoolmanagementwebsite.entity.Transport;

import java.time.LocalDate;

import com.schoolmanagement.schoolmanagementwebsite.enums.Transport.DriverStatus;

import jakarta.persistence.*;
import lombok.*;

@Entity
@Table(
    name = "transport_driver",
    uniqueConstraints = {
        @UniqueConstraint(
            name = "uk_driver_school_license",
            columnNames = {"school_id", "license_number"}
        ),
        @UniqueConstraint(
            name = "uk_driver_school_mobile",
            columnNames = {"school_id", "mobile_number"}
        ),
        @UniqueConstraint(
            name = "uk_driver_school_vehicle",
            columnNames = {"school_id", "vehicle_id"}
        )
    }
)
@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
@Builder
public class Driver {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @Column(name = "school_id", nullable = false)
    private Long schoolId;

    @Column(name = "driver_name", nullable = false)
    private String driverName;

    @Column(name = "mobile_number", nullable = false)
    private String mobileNumber;

    @Column(name = "alternate_mobile")
    private String alternateMobile;

    @Column(name = "license_number", nullable = false)
    private String licenseNumber;

    @Column(name = "license_type", nullable = false)
    private String licenseType;

    @Column(name = "license_expiry_date", nullable = false)
    private LocalDate licenseExpiryDate;

    @Column(length = 500)
    private String address;

    @Column(name = "vehicle_id")
    private Long vehicleId;

    @Enumerated(EnumType.STRING)
    @Column(nullable = false)
    private DriverStatus status;
}