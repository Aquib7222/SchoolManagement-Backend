package com.schoolmanagement.schoolmanagementwebsite.entity;

import com.schoolmanagement.schoolmanagementwebsite.enums.HomeworkType;
import jakarta.persistence.*;
import lombok.*;

import java.time.LocalDate;

@Entity
@Table(
    name = "homework",
    uniqueConstraints = {
        @UniqueConstraint(
            columnNames = {
                "school_id",
                "academic_year",
                "student_class",
                "section",
                "subject",
                "homework_date"
            }
        )
    }
)
@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
@Builder
public class Homework {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @Column(name = "school_id", nullable = false)
    private Long schoolId;

    @Column(name = "teacher_id", nullable = false)
    private Long teacherId;

    @Column(name = "academic_year", nullable = false)
    private String academicYear;

    @Column(name = "student_class", nullable = false)
    private String studentClass;

    @Column(name = "section", nullable = false)
    private String section;

    @Column(name = "subject", nullable = false)
    private String subject;

    @Column(name = "homework_date", nullable = false)
    private LocalDate homeworkDate;

    @Column(name = "submission_date", nullable = false)
    private LocalDate submissionDate;

    @Enumerated(EnumType.STRING)
    @Column(name = "homework_type", nullable = false)
    private HomeworkType homeworkType;

    @Column(name = "homework_text", columnDefinition = "TEXT")
    private String homeworkText;

    @Column(name = "image_url", length = 500)
    private String imageUrl;

    @Column(nullable = false)
    private Boolean active = true;
}


