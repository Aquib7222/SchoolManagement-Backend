package com.schoolmanagement.schoolmanagementwebsite.entity.Assessment;

import java.math.BigDecimal;
import java.time.LocalDateTime;

import jakarta.persistence.*;
import lombok.*;

@Entity
@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
@Builder
@Table(
    name = "student_assessment_component_marks",
    uniqueConstraints = {
        @UniqueConstraint(
            name = "uk_student_component_marks",
            columnNames = {
                "student_assessment_marks_id",
                "assessment_structure_type_id"
            }
        )
    }
)
public class StudentAssessmentComponentMarks {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(
        name = "student_assessment_marks_id",
        nullable = false
    )
    private StudentAssessmentMarks studentAssessmentMarks;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(
        name = "assessment_structure_type_id",
        nullable = false
    )
    private AssessmentStructureType assessmentStructureType;

    @Column(
        name = "obtained_marks",
        precision = 10,
        scale = 2,
        nullable = false
    )
    private BigDecimal obtainedMarks;

    private LocalDateTime createdAt;

    private LocalDateTime updatedAt;

    @PrePersist
    protected void onCreate() {
        createdAt = LocalDateTime.now();
        updatedAt = LocalDateTime.now();
    }

    @PreUpdate
    protected void onUpdate() {
        updatedAt = LocalDateTime.now();
    }
}