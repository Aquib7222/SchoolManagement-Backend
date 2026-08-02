package com.schoolmanagement.schoolmanagementwebsite.repository;

import java.util.List;
import java.util.Optional;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;

import com.schoolmanagement.schoolmanagementwebsite.entity.Admission;

public interface AdmissionRepository extends JpaRepository<Admission, Long> {

    boolean existsByAdmissionNumber(String admissionNumber);

    List<Admission> findBySchool_Id(Long schoolId);

    long countBySchool_Id(Long schoolId);

    Optional<Admission> findByAdmissionNumberAndSchoolId(String admissionNumber, Long schoolId);

    @Query("""
SELECT a FROM Admission a
WHERE a.school.id = :schoolId
AND (:academicYear IS NULL OR a.academicYear = :academicYear)
AND (:admissionNumber IS NULL OR a.admissionNumber = :admissionNumber)
AND (:studentClass IS NULL OR a.studentClass = :studentClass)

""")
    List<Admission> searchAdmissions(
            @Param("schoolId") Long schoolId,
            @Param("academicYear") String academicYear,
            @Param("admissionNumber") String admissionNumber,
            @Param("studentClass") String studentClass
            
    );

}
