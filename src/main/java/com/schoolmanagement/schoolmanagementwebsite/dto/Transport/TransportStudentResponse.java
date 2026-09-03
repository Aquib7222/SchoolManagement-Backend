package com.schoolmanagement.schoolmanagementwebsite.dto.Transport;

import com.schoolmanagement.schoolmanagementwebsite.enums.Section;

import lombok.*;

@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
@Builder
public class TransportStudentResponse {

    private Long studentId;

    private Long schoolId;

    private String admissionNumber;

    private String studentName;

    private Integer rollNumber;

    private String studentClass;

    private Section section;

    private Boolean transportRequired;

    private Boolean allocated;

    private Long allocationId;

    private Long routeId;

    private String routeName;

    private String stopName;

    private String vehicleNumber;

    private String allocationStatus;
}