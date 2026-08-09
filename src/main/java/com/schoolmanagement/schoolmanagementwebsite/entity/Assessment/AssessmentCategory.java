package com.schoolmanagement.schoolmanagementwebsite.entity.Assessment;

import java.time.LocalDateTime;

import com.schoolmanagement.schoolmanagementwebsite.enums.Assessment.AssessmentNature;

import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.EnumType;
import jakarta.persistence.Enumerated;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.persistence.Table;
import jakarta.persistence.UniqueConstraint;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@Entity
@Setter
@Getter
@NoArgsConstructor
@AllArgsConstructor
@Builder
@Table(
        name = "assessment_category",
        uniqueConstraints = {
            @UniqueConstraint(columnNames = {"school_id", "category_name"}),
            @UniqueConstraint(columnNames = {"school_id", "short_code"})
        }
)
public class AssessmentCategory {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @Column(name = "school_id", nullable = false)
    private Long schoolId;

    @Column(nullable = false)
    private String categoryName;

    @Column(nullable = false)
    private String shortCode;

    @Enumerated(EnumType.STRING)
    @Column(nullable = false)
    private AssessmentNature nature;

    private String weightage;

    @Column(length = 255)
    private String description;

    @Column(nullable = false)
    @Builder.Default
    private boolean status = true;

    private Integer displayOrder;

    private LocalDateTime createdAt;

    private LocalDateTime updatedAt;

}
