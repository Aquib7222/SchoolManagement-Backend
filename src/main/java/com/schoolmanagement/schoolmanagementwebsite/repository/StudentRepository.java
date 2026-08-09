package com.schoolmanagement.schoolmanagementwebsite.repository;

import com.schoolmanagement.schoolmanagementwebsite.entity.Admission;
import com.schoolmanagement.schoolmanagementwebsite.entity.School;
import com.schoolmanagement.schoolmanagementwebsite.entity.Student;
import com.schoolmanagement.schoolmanagementwebsite.enums.StudentStatus;

import java.util.List;
import java.util.Optional;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

import com.schoolmanagement.schoolmanagementwebsite.enums.Section;

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
            @Param("section") Section section,
            @Param("search") String search
    );

    @Query("""
SELECT s FROM Student s
WHERE s.school.id = :schoolId
AND s.academicYear = :academicYear

AND (:admissionNumber IS NULL OR :admissionNumber='' OR
     s.admissionNumber LIKE CONCAT('%',:admissionNumber,'%'))

AND (:studentName IS NULL OR :studentName='' OR
     LOWER(s.firstName) LIKE LOWER(CONCAT('%',:studentName,'%'))
     OR LOWER(s.lastName) LIKE LOWER(CONCAT('%',:studentName,'%')))

AND (:fatherName IS NULL OR :fatherName='' OR
     LOWER(s.fatherName) LIKE LOWER(CONCAT('%',:fatherName,'%')))

AND (:motherName IS NULL OR :motherName='' OR
     LOWER(s.motherName) LIKE LOWER(CONCAT('%',:motherName,'%')))

AND (:mobile IS NULL OR :mobile='' OR
     s.mobile LIKE CONCAT('%',:mobile,'%'))

AND (:studentClass IS NULL OR :studentClass='' OR
     s.studentClass = :studentClass)

AND (:section IS NULL OR :section='' OR
     s.section = :section)

""")
    List<Student> searchStudentDetails(
            @Param("schoolId") Long schoolId,
            @Param("academicYear") String academicYear,
            @Param("admissionNumber") String admissionNumber,
            @Param("studentName") String studentName,
            @Param("fatherName") String fatherName,
            @Param("motherName") String motherName,
            @Param("mobile") String mobile,
            @Param("studentClass") String studentClass,
            @Param("section") String section
    );

    // ✅ Check if student already created for this admission
    boolean existsByAdmission(Admission admission);

    long countBySchool_Id(Long schoolId);

    long countBySchool_IdAndStatus(Long schoolId, StudentStatus status);

    List<Student> findBySchool_IdAndStudentClass(Long schoolId, String studentClass);

    List<Student> findBySchool_Id(Long schoolId);

    Optional<Student> findBySchool_IdAndAdmissionNumber(Long schoolId, String admissionNumber);

    Optional<Student> findBySchool_IdAndAcademicYearAndAdmissionNumber(
            Long schoolId,
            String academicYear,
            String admissionNumber
    );

    List<Student> findBySchool_IdAndAcademicYearAndStudentClassAndSection(
            Long schoolId,
            String academicYear,
            String studentClass,
            Section section
    );

    List<Student> findBySchoolIdAndAdmissionNumberIn(
        Long schoolId,
        List<String> admissionNumbers
);
    

      
}
