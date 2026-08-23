package com.schoolmanagement.schoolmanagementwebsite.entity.Assessment.SubjectManagement;

import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.persistence.Table;
import jakarta.persistence.UniqueConstraint;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@Entity
@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
@Table(
    name = "class_subject_mapping",
    uniqueConstraints = {
        @UniqueConstraint(
            columnNames = {
                "school_id",
                "academic_year",
                "student_class",
                "subject_id"
            }
        )
    }
)
public class ClassSubjectMapping {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @Column(name = "school_id", nullable = false)
    private Long schoolId;

    @Column(name = "academic_year", nullable = false)
    private String academicYear;

    @Column(name = "student_class", nullable = false)
    private String studentClass;

    @Column(name = "subject_id", nullable = false)
    private Long subjectId;

    @Column(nullable = false)
    private boolean status = true;
}