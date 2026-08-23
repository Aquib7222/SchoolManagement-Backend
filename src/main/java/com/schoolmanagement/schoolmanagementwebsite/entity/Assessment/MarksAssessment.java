package com.schoolmanagement.schoolmanagementwebsite.entity.Assessment;

import java.time.LocalDateTime;

import com.schoolmanagement.schoolmanagementwebsite.enums.Assessment.MarksStatus;
import com.schoolmanagement.schoolmanagementwebsite.converter.SessionsConverter;
import com.schoolmanagement.schoolmanagementwebsite.enums.Section;
import com.schoolmanagement.schoolmanagementwebsite.enums.Sessions;
import com.schoolmanagement.schoolmanagementwebsite.enums.Standard;

import jakarta.persistence.*;
import lombok.*;

@Entity
@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
@Builder
@Table(
    name = "marks_assessment",
    uniqueConstraints = {
        @UniqueConstraint(
            name = "uk_marks_assessment",
            columnNames = {
                "school_id",
                "session",
                "exam_term_id",
                "student_class",
                "section",
                "subject_id"
            }
        )
    }
)
public class MarksAssessment {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @Column(name = "school_id", nullable = false)
    private Long schoolId;

    @Convert(converter = SessionsConverter.class)
@Column(nullable = false)
private Sessions session;

    @Column(name = "exam_term_id", nullable = false)
    private Long examTermId;

    @Enumerated(EnumType.STRING)
    @Column(name = "student_class", nullable = false)
    private Standard studentClass;

    @Enumerated(EnumType.STRING)
    @Column(nullable = false)
    private Section section;

    @Column(name = "subject_id", nullable = false)
    private Long subjectId;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(
        name = "assessment_structure_id",
        nullable = false
    )
    private AssessmentStructure assessmentStructure;

    @Enumerated(EnumType.STRING)
    @Column(nullable = false)
    @Builder.Default
    private MarksStatus status = MarksStatus.DRAFT;

    private LocalDateTime createdAt;

    private LocalDateTime updatedAt;

    private LocalDateTime publishedAt;

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