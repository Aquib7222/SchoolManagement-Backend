package com.schoolmanagement.schoolmanagementwebsite.dto.Transport;

import lombok.*;

@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
@Builder
public class StopSearchResponse {

    private Long routeId;
    private String routeName;

    private String stopName;
    private String location;

    private Long vehicleId;
    private String vehicleNumber;
    private String vehicleType;
    private String vehicleModel;

    private Long driverId;
    private String driverName;
    private String driverPhone;

    private String routeStatus;
    private String vehicleStatus;
    private String driverStatus;
    private String mappingStatus;
}