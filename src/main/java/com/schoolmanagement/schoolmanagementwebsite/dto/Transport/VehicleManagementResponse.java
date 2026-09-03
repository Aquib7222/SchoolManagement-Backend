package com.schoolmanagement.schoolmanagementwebsite.dto.Transport;

import java.time.LocalDate;

import com.schoolmanagement.schoolmanagementwebsite.enums.Transport.VehicleStatus;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
@Builder
public class VehicleManagementResponse {
    
     private Long id;

    private Long schoolId;

    private String vehicleNumber;

    private String vehicleType;

    private String vehicleModel;

    private Integer vehicleCapacity;

    private VehicleStatus status;

    private LocalDate createdAt;

    private LocalDate updatedAt;
}
