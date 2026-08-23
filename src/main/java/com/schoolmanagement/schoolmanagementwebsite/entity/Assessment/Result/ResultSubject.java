package com.schoolmanagement.schoolmanagementwebsite.entity.Assessment.Result;

import java.math.BigDecimal;
import java.util.ArrayList;

import java.util.List;

import jakarta.persistence.CascadeType;


import jakarta.persistence.Column;
import jakarta.persistence.Entity;
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
    name = "result_subjects",
    uniqueConstraints = {
        @UniqueConstraint(
            name = "uk_result_subject",
            columnNames = {
                "result_id",
                "subject_id"
            }
        )
    }
)
@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
@Builder
public class ResultSubject {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    

    @ManyToOne(
        fetch = FetchType.LAZY,
        optional = false
    )
    @JoinColumn(
        name = "result_id",
        nullable = false
    )
    private Result result;

    

    @Column(
        name = "subject_id",
        nullable = false
    )
    private Long subjectId;

    @Column(
        name = "subject_name",
        nullable = false
    )
    private String subjectName;

    

    @Column(
        name = "total_marks",
        precision = 10,
        scale = 2
    )
    private BigDecimal totalMarks;

    @Column(
        name = "max_marks",
        precision = 10,
        scale = 2
    )
    private BigDecimal maxMarks;

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

    @OneToMany(
    mappedBy = "resultSubject",
    cascade = CascadeType.ALL,
    orphanRemoval = true
)
private List<ResultComponent> components = new ArrayList<>();
}