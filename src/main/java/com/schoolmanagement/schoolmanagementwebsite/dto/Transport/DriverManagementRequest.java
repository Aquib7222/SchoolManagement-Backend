package com.schoolmanagement.schoolmanagementwebsite.dto.Transport;

import java.time.LocalDate;

import lombok.*;

@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
@Builder
public class DriverManagementRequest {

    private Long schoolId;

    private String driverName;

    private String mobileNumber;

    private String alternateMobile;

    private String licenseNumber;

    private String licenseType;

    private LocalDate licenseExpiryDate;

    private String address;

    private Long vehicleId;

    private String status;
}