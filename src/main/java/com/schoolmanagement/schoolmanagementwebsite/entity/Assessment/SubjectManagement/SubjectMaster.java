package com.schoolmanagement.schoolmanagementwebsite.entity.Assessment.SubjectManagement;
import com.schoolmanagement.schoolmanagementwebsite.enums.Assessment.SubjectType;

import com.schoolmanagement.schoolmanagementwebsite.enums.Assessment.SubjectCategory;

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
    name = "subject_master",
    uniqueConstraints = {
        @UniqueConstraint(
            columnNames = {"school_id", "subject_name"}
        ),
        @UniqueConstraint(
            columnNames = {"school_id", "short_code"}
        )
    }
)
public class SubjectMaster {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @Column(name = "school_id", nullable = false)
    private Long schoolId;

    @Column(name = "subject_name", nullable = false)
    private String subjectName;

    @Column(name = "short_code", nullable = false)
    private String shortCode;

    @Enumerated(EnumType.STRING)
    @Column(nullable = false)
    private SubjectType subjectType;

    @Enumerated(EnumType.STRING)
    @Column(nullable = false)
    private SubjectCategory subjectCategory;

    private Integer displayOrder;

    @Column(nullable = false)
    @Builder.Default
    private boolean status=true;

    
}
