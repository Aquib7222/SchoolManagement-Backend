// package com.schoolmanagement.schoolmanagementwebsite.entity.Assessment;

// import java.time.LocalDateTime;

// import com.schoolmanagement.schoolmanagementwebsite.entity.Assessment.AssessmentCategory;
// import com.schoolmanagement.schoolmanagementwebsite.entity.Assessment.ExamTerm;
// import com.schoolmanagement.schoolmanagementwebsite.enums.Assessment.AssessmentNature;
// import com.schoolmanagement.schoolmanagementwebsite.enums.Sessions;

// import jakarta.persistence.Column;
// import jakarta.persistence.Entity;
// import jakarta.persistence.EnumType;
// import jakarta.persistence.Enumerated;
// import jakarta.persistence.FetchType;
// import jakarta.persistence.GeneratedValue;
// import jakarta.persistence.GenerationType;
// import jakarta.persistence.Id;
// import jakarta.persistence.JoinColumn;
// import jakarta.persistence.ManyToOne;
// import jakarta.persistence.Table;
// import jakarta.persistence.UniqueConstraint;
// import lombok.AllArgsConstructor;
// import lombok.Builder;
// import lombok.Getter;
// import lombok.NoArgsConstructor;
// import lombok.Setter;
// @Entity
// @Getter
// @Setter
// @NoArgsConstructor
// @AllArgsConstructor
// @Builder
// @Table(
//     name = "assessment_type",
//     uniqueConstraints = {
//         @UniqueConstraint(columnNames = {"school_id", "type_name"}),
//         @UniqueConstraint(columnNames = {"school_id", "short_code"})
//     }
// )
// public class AssessmentType {

//     @Id
//     @GeneratedValue(strategy = GenerationType.IDENTITY)
//     private Long id;

//     @Column(name = "school_id", nullable = false)
//     private Long schoolId;

//     @Column(name = "type_name", nullable = false)
//     private String typeName;

//     @Column(name = "short_code", nullable = false)
//     private String shortCode;

//     @ManyToOne(fetch = FetchType.LAZY)
//     @JoinColumn(name = "category_id", nullable = false)
//     private AssessmentCategory category;

//     @ManyToOne(fetch = FetchType.LAZY)
//     @JoinColumn(name = "exam_term_id", nullable = false)
//     private ExamTerm examTerm;

//     @Enumerated(EnumType.STRING)
//     @Column(nullable = false)
//     private AssessmentNature nature;

//     @Column(nullable = false)
//     private Integer maxMarks;

//     private Integer passingMarks;

//     private Integer displayOrder;

//     private Double weightage;

//     @Column(length = 255)
//     private String description;

//     @Column(nullable = false)
//     @Builder.Default
//     private boolean status = true;

//     private LocalDateTime createdAt;

//     private LocalDateTime updatedAt;
// }

package com.schoolmanagement.schoolmanagementwebsite.entity.Assessment;

import java.time.LocalDateTime;

import com.schoolmanagement.schoolmanagementwebsite.enums.Assessment.AssessmentNature;

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
import jakarta.persistence.Table;
import jakarta.persistence.UniqueConstraint;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@Entity
@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
@Builder
@Table(
    name = "assessment_type",
    uniqueConstraints = {
        @UniqueConstraint(
            columnNames = {"school_id", "type_name"}
        ),
        @UniqueConstraint(
            columnNames = {"school_id", "short_code"}
        )
    }
)
public class AssessmentType {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @Column(name = "school_id", nullable = false)
    private Long schoolId;

    @Column(name = "type_name", nullable = false)
    private String typeName;

    @Column(name = "short_code", nullable = false)
    private String shortCode;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "category_id", nullable = false)
    private AssessmentCategory category;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "exam_term_id", nullable = false)
    private ExamTerm examTerm;

    @Enumerated(EnumType.STRING)
    @Column(nullable = false)
    private AssessmentNature nature;

    @Column(nullable = false)
    private Integer maxMarks;

    private Integer passingMarks;

    private Integer displayOrder;

    private Double weightage;

    @Column(length = 255)
    private String description;

    @Column(nullable = false)
    @Builder.Default
    private boolean status = true;

    private LocalDateTime createdAt;

    private LocalDateTime updatedAt;
}