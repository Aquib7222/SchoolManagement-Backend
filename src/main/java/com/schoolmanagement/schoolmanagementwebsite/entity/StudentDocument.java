package com.schoolmanagement.schoolmanagementwebsite.entity;

import jakarta.persistence.*;
import lombok.*;

@Entity
@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
@Builder
@Table(name = "student_documents")
public class StudentDocument {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    // STUDENT / ADMISSION
    @ManyToOne
    @JoinColumn(name = "admission_id", nullable = false)
    private Admission admission;

    // Document Type
    @Column(nullable = false)
    private String documentType;
    // STUDENT_PHOTO, FATHER_PHOTO, MOTHER_PHOTO, AADHAR, TC

    // File info
    private String fileName;
    private String filePath; // stored path or URL
}
