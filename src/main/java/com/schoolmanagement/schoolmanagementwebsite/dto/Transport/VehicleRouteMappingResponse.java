package com.schoolmanagement.schoolmanagementwebsite.dto.Transport;

import java.time.LocalDateTime;

import com.schoolmanagement.schoolmanagementwebsite.enums.Transport.VehicleRouteStatus;

import lombok.*;

@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
@Builder
public class VehicleRouteMappingResponse {

    private Long id;

    private Long schoolId;

    private Long vehicleId;

    private String vehicleNumber;

    private Long routeId;

    private String routeName;

    private String startLocation;

    private String endLocation;

    private String stops;

    private VehicleRouteStatus status;

    private LocalDateTime assignedAt;

    private LocalDateTime updatedAt;
}