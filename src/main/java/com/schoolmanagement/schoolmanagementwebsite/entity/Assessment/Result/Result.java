package com.schoolmanagement.schoolmanagementwebsite.entity.Assessment.Result;

import java.math.BigDecimal;
import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.List;

import com.schoolmanagement.schoolmanagementwebsite.entity.Student;
import com.schoolmanagement.schoolmanagementwebsite.enums.Assessment.ResultStatus;
import com.schoolmanagement.schoolmanagementwebsite.enums.Section;
import com.schoolmanagement.schoolmanagementwebsite.enums.Sessions;
import com.schoolmanagement.schoolmanagementwebsite.enums.Standard;

import jakarta.persistence.CascadeType;
import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.EnumType;
import jakarta.persistence.Enumerated;
import jakarta.persistence.FetchType;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.persistence.JoinColumn;
import jakarta.persistence.ManyToOne;
import jakarta.persistence.OneToMany;
import jakarta.persistence.Table;
import jakarta.persistence.UniqueConstraint;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@Entity
@Table(
    name = "results",
    uniqueConstraints = {
        @UniqueConstraint(
            name = "uk_result_student_exam",
            columnNames = {
                "school_id",
                "student_id",
                "session",
                "exam_term_id"
            }
        )
    }
)
@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
@Builder
public class Result {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    // =========================================================
    // SCHOOL
    // =========================================================

    @Column(name = "school_id", nullable = false)
    private Long schoolId;

    // =========================================================
    // STUDENT
    // =========================================================

    @ManyToOne(fetch = FetchType.LAZY, optional = false)
    @JoinColumn(
        name = "student_id",
        nullable = false
    )
    private Student student;

    // Snapshot fields
    @Column(name = "admission_number")
    private String admissionNumber;

    @Column(name = "student_name")
    private String studentName;

    // =========================================================
    // ACADEMIC DETAILS
    // =========================================================

    @Enumerated(EnumType.STRING)
    @Column(name = "session", nullable = false)
    private Sessions session;

    @Column(name = "exam_term_id", nullable = false)
    private Long examTermId;

    @Enumerated(EnumType.STRING)
    @Column(name = "student_class", nullable = false)
    private Standard studentClass;

    @Enumerated(EnumType.STRING)
    @Column(name = "section", nullable = false)
    private Section section;

    // =========================================================
    // FINAL RESULT
    // =========================================================

    @Column(
        name = "total_marks",
        precision = 10,
        scale = 2
    )
    private BigDecimal totalMarks;

    @Column(
        name = "total_max_marks",
        precision = 10,
        scale = 2
    )
    private BigDecimal totalMaxMarks;

    @Column(
        name = "percentage",
        precision = 6,
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

    @Column(name = "remark")
    private String remark;

    @Column(name = "rank")
    private Integer rank;

    // =========================================================
    // STATUS
    // =========================================================

    @Enumerated(EnumType.STRING)
    @Column(
        name = "status",
        nullable = false
    )
    @Builder.Default
    private ResultStatus status = ResultStatus.PUBLISHED;

    @Column(name = "published_at")
    private LocalDateTime publishedAt;

    @Column(name = "created_at")
    private LocalDateTime createdAt;

    @Column(name = "updated_at")
    private LocalDateTime updatedAt;

    // =========================================================
    // SUBJECT RESULTS
    // =========================================================

    @OneToMany(
        mappedBy = "result",
        cascade = CascadeType.ALL,
        orphanRemoval = true
    )
    @Builder.Default
    private List<ResultSubject> subjects = new ArrayList<>();

    // =========================================================
    // HELPER
    // =========================================================

    public void addSubject(ResultSubject subject) {

        subjects.add(subject);

        subject.setResult(this);
    }

    public void removeSubject(ResultSubject subject) {

        subjects.remove(subject);

        subject.setResult(null);
    }

    // =========================================================
    // TIMESTAMP
    // =========================================================

    @jakarta.persistence.PrePersist
    protected void onCreate() {

        LocalDateTime now = LocalDateTime.now();

        createdAt = now;
        updatedAt = now;

        if (publishedAt == null
                && status == ResultStatus.PUBLISHED) {

            publishedAt = now;
        }
    }

    @jakarta.persistence.PreUpdate
    protected void onUpdate() {

        updatedAt = LocalDateTime.now();
    }
}