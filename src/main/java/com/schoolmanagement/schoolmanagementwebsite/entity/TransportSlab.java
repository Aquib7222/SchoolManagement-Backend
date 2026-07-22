package com.schoolmanagement.schoolmanagementwebsite.entity;

import com.schoolmanagement.schoolmanagementwebsite.enums.TransportDistance;

import jakarta.persistence.*;
import lombok.*;


@Entity
@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
@Builder
@Table(
    uniqueConstraints = {
        @UniqueConstraint(columnNames = {"school_id", "distance"})
    }
)
public class TransportSlab {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @ManyToOne
    @JoinColumn(name = "school_id", nullable = false)
    private School school;

    @Enumerated(EnumType.STRING)
    private TransportDistance distance;

    private Double amount;
}
