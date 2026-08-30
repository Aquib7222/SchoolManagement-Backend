package com.schoolmanagement.schoolmanagementwebsite.entity;

import java.time.LocalDate;

import com.schoolmanagement.schoolmanagementwebsite.enums.Section;
import com.schoolmanagement.schoolmanagementwebsite.enums.StudentStatus;

import jakarta.persistence.*;
import lombok.*;

@Entity
@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
@Builder
@Table(
    name = "student_enrollment",
    uniqueConstraints = {
        @UniqueConstraint(
            name = "uk_student_academic_year",
            columnNames = {"student_id", "academic_year"}
        )
    }
)
public class StudentEnrollment {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    // ==========================================
    // STUDENT
    // ==========================================

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "student_id", nullable = false)
    private Student student;

    // ==========================================
    // SCHOOL
    // ==========================================

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "school_id", nullable = false)
    private School school;

    // ==========================================
    // ACADEMIC DETAILS
    // ==========================================

    @Column(name = "academic_year", nullable = false)
    private String academicYear;

    @Column(name = "student_class", nullable = false)
    private String studentClass;

    @Enumerated(EnumType.STRING)
    @Column(nullable = false)
    private Section section;

    @Column(name = "roll_number")
    private Integer rollNumber;

    // ==========================================
    // STATUS
    // ==========================================

    @Enumerated(EnumType.STRING)
    @Column(nullable = false)
    private StudentStatus status;

    // ==========================================
    // ENROLLMENT DATE
    // ==========================================

    @Column(name = "enrollment_date")
    private LocalDate enrollmentDate;

    // ==========================================
    // PROMOTION HISTORY
    // ==========================================

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "promoted_from_enrollment_id")
    private StudentEnrollment promotedFromEnrollment;
}