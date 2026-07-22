package com.schoolmanagement.schoolmanagementwebsite.repository;

import java.util.List;

import org.springframework.data.jpa.repository.JpaRepository;

import com.schoolmanagement.schoolmanagementwebsite.entity.StudentDocument;

public interface StudentDocumentRepository 
        extends JpaRepository<StudentDocument, Long> {

    List<StudentDocument> findByAdmission_Id(Long admissionId);
}
