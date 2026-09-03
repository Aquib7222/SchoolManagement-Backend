package com.schoolmanagement.schoolmanagementwebsite.dto.Transport;

import com.schoolmanagement.schoolmanagementwebsite.enums.Transport.VehicleRouteStatus;

import lombok.*;

@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
@Builder
public class VehicleRouteMappingRequest {

    private Long schoolId;

    private Long vehicleId;

    private Long routeId;

    private VehicleRouteStatus status;
}