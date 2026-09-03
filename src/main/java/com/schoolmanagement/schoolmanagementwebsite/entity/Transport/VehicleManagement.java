package com.schoolmanagement.schoolmanagementwebsite.entity.Transport;

import java.time.LocalDate;

import com.schoolmanagement.schoolmanagementwebsite.enums.Transport.VehicleStatus;

import jakarta.persistence.Entity;
import jakarta.persistence.EnumType;
import jakarta.persistence.Enumerated;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.persistence.PrePersist;
import jakarta.persistence.PreUpdate;
import jakarta.persistence.Table;
import jakarta.persistence.UniqueConstraint;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@Entity
@Table(
    name = "vehicles",
    uniqueConstraints = {
        @UniqueConstraint(
            name = "uk_vehicle_school_number",
            columnNames = {"school_id", "vehicle_number"}
        )
    }
)
@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
@Builder
public class VehicleManagement {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    // School-wise vehicle
    private Long schoolId;

    // Vehicle registration number
    private String vehicleNumber;

    // BUS / VAN / MINI_BUS etc.
    private String vehicleType;

    private String vehicleModel;

    // Number of students the vehicle can carry
    private Integer vehicleCapacity;

    @Enumerated(EnumType.STRING)
    private VehicleStatus status;

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