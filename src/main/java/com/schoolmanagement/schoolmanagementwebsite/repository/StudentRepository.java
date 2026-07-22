package com.schoolmanagement.schoolmanagementwebsite.repository;

import com.schoolmanagement.schoolmanagementwebsite.entity.Admission;
import com.schoolmanagement.schoolmanagementwebsite.entity.Student;
import com.schoolmanagement.schoolmanagementwebsite.enums.StudentStatus;

import java.util.List;
import java.util.Optional;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;
@Repository
public interface StudentRepository extends JpaRepository<Student, Long> {

    @Query("""
        SELECT s FROM Student s
        WHERE s.school.id = :schoolId
        AND (:academicYear IS NULL OR s.academicYear = :academicYear)
        AND (:studentClass IS NULL OR s.studentClass = :studentClass)
        AND (:section IS NULL OR s.section = :section)
        AND (
            :search IS NULL OR
            LOWER(s.firstName) LIKE LOWER(CONCAT('%', :search, '%')) OR
            LOWER(s.lastName) LIKE LOWER(CONCAT('%', :search, '%')) OR
            s.admissionNumber LIKE CONCAT('%', :search, '%') OR
            s.mobile LIKE CONCAT('%', :search, '%')
        )
    """)
    List<Student> searchStudents(
            @Param("schoolId") Long schoolId,
            @Param("academicYear") String academicYear,
            @Param("studentClass") String studentClass,
            @Param("section") String section,
            @Param("search") String search
    );
    
    // ✅ Check if student already created for this admission
    boolean existsByAdmission(Admission admission);

    long countBySchool_Id(Long schoolId);

    long countBySchool_IdAndStatus(Long schoolId, StudentStatus status);

    List<Student> findBySchool_IdAndStudentClass(Long schoolId, String studentClass);

    List<Student> findBySchool_Id(Long schoolId);

    

Optional<Student> findBySchool_IdAndAdmissionNumber(Long schoolId, String admissionNumber);


}
