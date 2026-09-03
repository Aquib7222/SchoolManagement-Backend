package com.schoolmanagement.schoolmanagementwebsite.dto.Transport;

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
public class VehicleManagementRequest {
    

     private Long schoolId;

    private String vehicleNumber;

    private String vehicleType;

    private String vehicleModel;

    private Integer vehicleCapacity;

    private VehicleStatus status;
}
