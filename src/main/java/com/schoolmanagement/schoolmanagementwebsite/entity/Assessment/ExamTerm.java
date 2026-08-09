package com.schoolmanagement.schoolmanagementwebsite.entity.Assessment;

import java.time.LocalDateTime;

import com.schoolmanagement.schoolmanagementwebsite.enums.Assessment.ExamTermType;
import com.schoolmanagement.schoolmanagementwebsite.enums.Sessions;

import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.EnumType;
import jakarta.persistence.Enumerated;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
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
    name = "exam_term",
    uniqueConstraints = {
        @UniqueConstraint(columnNames = {
                "school_id",
                "session",
                "exam_term"
        }),
        @UniqueConstraint(columnNames = {
                "school_id",
                "session",
                "short_code"
        })
    }
)
public class ExamTerm {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @Column(name = "school_id", nullable = false)
    private Long schoolId;

    @Column(nullable = false)
    private String examTerm;

    @Column(nullable = false)
    private String shortCode;

    @Enumerated(EnumType.STRING)
    @Column(nullable = false)
    private Sessions session;

    @Enumerated(EnumType.STRING)
    @Column(nullable = false)
    private ExamTermType examTermType;
    
    private LocalDateTime startDate;

    private LocalDateTime endDate;

    private Integer displayOrder;

    @Column(nullable = false)
    @Builder.Default
    private boolean status = true;
    
    @Column(length = 255)
    private String description;

    private LocalDateTime createdAt;

    private LocalDateTime updatedAt;
}
