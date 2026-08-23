package com.schoolmanagement.schoolmanagementwebsite.repository.Assessment.SubjectManagement;


import java.util.List;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;

import com.schoolmanagement.schoolmanagementwebsite.dto.Assessment.SubjectManagement.ClassSubjectMappingResponse;
import com.schoolmanagement.schoolmanagementwebsite.entity.Assessment.SubjectManagement.ClassSubjectMapping;
public interface ClassSubjectMappingRepository
        extends JpaRepository<ClassSubjectMapping, Long> {

    boolean existsBySchoolIdAndAcademicYearAndStudentClassAndSubjectId(
            Long schoolId,
            String academicYear,
            String studentClass,
            Long subjectId
    );


    @Query("""
        SELECT new com.schoolmanagement.schoolmanagementwebsite.dto.Assessment.SubjectManagement.ClassSubjectMappingResponse(
            m.id,
            s.id,
            s.subjectName,
            s.shortCode,
            str(s.subjectType),
            str(s.subjectCategory),
            m.status
        )
        FROM ClassSubjectMapping m
        JOIN SubjectMaster s ON s.id = m.subjectId
        WHERE m.schoolId = :schoolId
        AND m.academicYear = :academicYear
        AND m.studentClass = :studentClass
        """)
    List<ClassSubjectMappingResponse> findMappedSubjects(
            @Param("schoolId") Long schoolId,
            @Param("academicYear") String academicYear,
            @Param("studentClass") String studentClass
    );
}