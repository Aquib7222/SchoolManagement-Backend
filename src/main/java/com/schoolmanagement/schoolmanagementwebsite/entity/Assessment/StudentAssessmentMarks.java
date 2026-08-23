package com.schoolmanagement.schoolmanagementwebsite.entity.Assessment;

import java.math.BigDecimal;
import java.time.LocalDateTime;

import com.schoolmanagement.schoolmanagementwebsite.entity.Student;
import com.schoolmanagement.schoolmanagementwebsite.enums.Assessment.MarksStatus;

import jakarta.persistence.*;
import lombok.*;

@Entity
@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
@Builder
@Table(
    name = "student_assessment_marks",
    uniqueConstraints = {
        @UniqueConstraint(
            name = "uk_marks_assessment_student",
            columnNames = {
                "marks_assessment_id",
                "student_id"
            }
        )
    }
)
public class StudentAssessmentMarks {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(
        name = "marks_assessment_id",
        nullable = false
    )
    private MarksAssessment marksAssessment;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(
        name = "student_id",
        nullable = false
    )
    private Student student;

    @Column(
        name = "total_marks",
        precision = 10,
        scale = 2
    )
    private BigDecimal totalMarks;

    @Column(
        name = "percentage",
        precision = 10,
        scale = 2
    )
    private BigDecimal percentage;

    @Column(name = "grade")
    private String grade;

    @Column(
        name = "grade_point",
        precision = 10,
        scale = 2
    )
    private BigDecimal gradePoint;

    @Column(length = 500)
    private String remark;

    @Enumerated(EnumType.STRING)
@Column(name = "status", nullable = false)
@Builder.Default
private MarksStatus status = MarksStatus.GENERATED;

private LocalDateTime publishedAt;

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