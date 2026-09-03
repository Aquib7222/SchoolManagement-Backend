package com.schoolmanagement.schoolmanagementwebsite.dto.Transport;

import java.time.LocalDate;

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
public class RouteManagementResponse {

    private Long id;

    private Long schoolId;

    private String routeName;

    private String startLocation;

    private String endLocation;

    private String stops;

    private RouteStatus status;

    private LocalDate createdAt;

    private LocalDate updatedAt;
}