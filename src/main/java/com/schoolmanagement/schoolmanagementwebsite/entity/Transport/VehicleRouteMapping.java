package com.schoolmanagement.schoolmanagementwebsite.entity.Transport;

import java.time.LocalDateTime;

import com.schoolmanagement.schoolmanagementwebsite.enums.Transport.VehicleRouteStatus;

import jakarta.persistence.*;
import lombok.*;

@Entity
@Table(
    name = "vehicle_route_mapping",
    uniqueConstraints = {
        @UniqueConstraint(
            name = "uk_vehicle_route_school",
            columnNames = {"school_id", "vehicle_id", "route_id"}
        ),
        @UniqueConstraint(
            name = "uk_vehicle_school",
            columnNames = {"school_id", "vehicle_id"}
        )
    }
)
@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
@Builder
public class VehicleRouteMapping {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @Column(name = "school_id", nullable = false)
    private Long schoolId;

    @Column(name = "vehicle_id", nullable = false)
    private Long vehicleId;

    @Column(name = "route_id", nullable = false)
    private Long routeId;

    @Enumerated(EnumType.STRING)
    @Column(nullable = false)
    private VehicleRouteStatus status;

    @Column(nullable = false)
    private LocalDateTime assignedAt;

    private LocalDateTime updatedAt;
}