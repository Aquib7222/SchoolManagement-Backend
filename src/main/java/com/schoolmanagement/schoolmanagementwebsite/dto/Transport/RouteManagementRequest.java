package com.schoolmanagement.schoolmanagementwebsite.dto.Transport;

import com.schoolmanagement.schoolmanagementwebsite.enums.Transport.RouteStatus;

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
public class RouteManagementRequest {

    private Long schoolId;

    private String routeName;

    private String startLocation;

    private String endLocation;

    private String stops;

    private RouteStatus status;
}