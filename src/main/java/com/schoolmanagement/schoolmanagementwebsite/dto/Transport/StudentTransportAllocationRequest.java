package com.schoolmanagement.schoolmanagementwebsite.dto.Transport;

import java.util.List;

import lombok.*;

@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
@Builder
public class StudentTransportAllocationRequest {

    private Long schoolId;

    private String academicYear;

    private Long routeId;

    private String stopName;

    private List<String> admissionNumbers;
}