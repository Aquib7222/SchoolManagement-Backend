package com.schoolmanagement.schoolmanagementwebsite.entity.Assessment.Result;

import java.math.BigDecimal;

import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import lombok.Builder;

@Entity
@Table(
    name = "result_component",
    uniqueConstraints = {
        @UniqueConstraint(
            name = "uk_result_subject_component",
            columnNames = {
                "result_subject_id",
                "component_id"
            }
        )
    }
)
@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
@Builder
public class ResultComponent {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @ManyToOne(fetch = FetchType.LAZY, optional = false)
    @JoinColumn(
        name = "result_subject_id",
        nullable = false
    )
    private ResultSubject resultSubject;

   
    @Column(name = "component_id", nullable = false)
    private Long componentId;

    @Column(name = "component_name", nullable = false)
    private String componentName;

    @Column(
        name = "max_marks",
        precision = 10,
        scale = 2,
        nullable = false
    )
    private BigDecimal maxMarks;

   
    @Column(
        name = "obtained_marks",
        precision = 10,
        scale = 2,
        nullable = false
    )
    private BigDecimal obtainedMarks;

    @Column(
        name = "percentage",
        precision = 5,
        scale = 2
    )
    private BigDecimal percentage;

   
    @Column(name = "grade")
    private String grade;

    @Column(
        name = "grade_point",
        precision = 5,
        scale = 2
    )
    private BigDecimal gradePoint;

    @Column(name = "status")
    private String status;
}