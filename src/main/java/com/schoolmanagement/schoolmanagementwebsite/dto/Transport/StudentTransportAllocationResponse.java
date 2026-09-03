package com.schoolmanagement.schoolmanagementwebsite.dto.Transport;

import java.time.LocalDateTime;

import com.schoolmanagement.schoolmanagementwebsite.enums.Section;

import lombok.*;

@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
@Builder
public class StudentTransportAllocationResponse {

    private Long id;

    private Long schoolId;

    private Long studentId;

    private String admissionNumber;

    private String studentName;

    private String studentClass;

    private Section section;

    private String academicYear;

    private Long routeId;

    private String routeName;

    private String stopName;

    private Long vehicleId;

    private String vehicleNumber;

    private String vehicleType;

    private String status;

    private LocalDateTime assignedAt;

    private LocalDateTime updatedAt;
}