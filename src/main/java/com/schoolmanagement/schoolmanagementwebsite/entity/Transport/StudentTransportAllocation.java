package com.schoolmanagement.schoolmanagementwebsite.entity.Transport;

import java.time.LocalDateTime;

import com.schoolmanagement.schoolmanagementwebsite.enums.Section;
import com.schoolmanagement.schoolmanagementwebsite.enums.Transport.TransportAllocationStatus;

import jakarta.persistence.*;
import lombok.*;

@Entity
@Table(
    name = "student_transport_allocation",
    uniqueConstraints = {
        @UniqueConstraint(
            name = "uk_student_transport_allocation",
            columnNames = {
                "school_id",
                "student_id",
                "academic_year"
            }
        )
    }
)
@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
@Builder
public class StudentTransportAllocation {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @Column(name = "school_id", nullable = false)
    private Long schoolId;

    @Column(name = "student_id", nullable = false)
    private Long studentId;

    @Column(name = "admission_number", nullable = false)
    private String admissionNumber;

    @Column(name = "academic_year", nullable = false)
    private String academicYear;

    @Column(name = "student_class", nullable = false)
    private String studentClass;

    @Enumerated(EnumType.STRING)
    @Column(nullable = false)
    private Section section;

    @Column(name = "route_id", nullable = false)
    private Long routeId;

    @Column(name = "stop_name", nullable = false)
    private String stopName;

    @Enumerated(EnumType.STRING)
    @Column(nullable = false)
    private TransportAllocationStatus status;

    @Column(name = "assigned_at")
    private LocalDateTime assignedAt;

    @Column(name = "updated_at")
    private LocalDateTime updatedAt;
}