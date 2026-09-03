package com.schoolmanagement.schoolmanagementwebsite.entity.Transport;

import java.time.LocalDate;

import com.schoolmanagement.schoolmanagementwebsite.enums.Transport.RouteStatus;

import jakarta.persistence.Entity;
import jakarta.persistence.EnumType;
import jakarta.persistence.Enumerated;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.persistence.Table;
import jakarta.persistence.UniqueConstraint;
import jakarta.persistence.PrePersist;
import jakarta.persistence.PreUpdate;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@Entity
@Table(
    name = "transport_routes",
    uniqueConstraints = {
        @UniqueConstraint(
            name = "uk_route_school_name",
            columnNames = {"school_id", "route_name"}
        )
    }
)
@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
@Builder
public class RouteManagement {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    private Long schoolId;

    private String routeName;

    private String startLocation;

    private String endLocation;

    // Example:
    // Patna Junction, Gandhi Maidan, Kankarbagh, School
    private String stops;

    @Enumerated(EnumType.STRING)
    private RouteStatus status;

    private LocalDate createdAt;

    private LocalDate updatedAt;

    @PrePersist
    protected void onCreate() {
        createdAt = LocalDate.now();
        updatedAt = LocalDate.now();
    }

    @PreUpdate
    protected void onUpdate() {
        updatedAt = LocalDate.now();
    }
}