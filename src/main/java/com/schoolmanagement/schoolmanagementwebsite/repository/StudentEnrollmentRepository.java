// package com.schoolmanagement.schoolmanagementwebsite.repository;

// import java.util.List;
// import java.util.Optional;

// import org.springframework.data.jpa.repository.JpaRepository;
// import org.springframework.data.jpa.repository.Query;
// import org.springframework.data.repository.query.Param;

// import com.schoolmanagement.schoolmanagementwebsite.entity.StudentEnrollment;
// import com.schoolmanagement.schoolmanagementwebsite.enums.Section;
// import com.schoolmanagement.schoolmanagementwebsite.enums.StudentStatus;

// public interface StudentEnrollmentRepository
//         extends JpaRepository<StudentEnrollment, Long> {

//     // =====================================================
//     // STUDENT + ACADEMIC YEAR
//     // =====================================================

//     Optional<StudentEnrollment>
//     findByStudent_IdAndAcademicYear(
//             Long studentId,
//             String academicYear
//     );

//     // =====================================================
//     // SCHOOL + ACADEMIC YEAR
//     // =====================================================

//     List<StudentEnrollment>
//     findBySchool_IdAndAcademicYear(
//             Long schoolId,
//             String academicYear
//     );

//     // =====================================================
//     // FILTER STUDENTS
//     // =====================================================

//     List<StudentEnrollment>
//     findBySchool_IdAndAcademicYearAndStudentClassAndSection(
//             Long schoolId,
//             String academicYear,
//             String studentClass,
//             Section section
//     );

//     // =====================================================
//     // CHECK EXISTING ENROLLMENT
//     // =====================================================

//     boolean existsByStudent_IdAndAcademicYear(
//             Long studentId,
//             String academicYear
//     );

//     // =====================================================
//     // ADMISSION NUMBER + YEAR
//     // =====================================================

//     @Query("""
//         SELECT e
//         FROM StudentEnrollment e
//         JOIN FETCH e.student s
//         WHERE e.school.id = :schoolId
//         AND e.academicYear = :academicYear
//         AND s.admissionNumber = :admissionNumber
//     """)
//     Optional<StudentEnrollment> findByAdmissionNumberAndAcademicYear(
//             @Param("schoolId") Long schoolId,
//             @Param("academicYear") String academicYear,
//             @Param("admissionNumber") String admissionNumber
//     );

//     // =====================================================
//     // SEARCH STUDENTS
//     // =====================================================

//     @Query("""
//         SELECT e
//         FROM StudentEnrollment e
//         JOIN FETCH e.student s
//         WHERE e.school.id = :schoolId
//         AND e.academicYear = :academicYear
//         AND e.studentClass = :studentClass
//         AND e.section = :section
//         AND e.status = :status
//         AND (
//             :search IS NULL
//             OR :search = ''
//             OR LOWER(s.firstName) LIKE LOWER(CONCAT('%', :search, '%'))
//             OR LOWER(s.lastName) LIKE LOWER(CONCAT('%', :search, '%'))
//             OR LOWER(s.admissionNumber) LIKE LOWER(CONCAT('%', :search, '%'))
//             OR LOWER(s.mobile) LIKE LOWER(CONCAT('%', :search, '%'))
//         )
//     """)
//     List<StudentEnrollment> searchStudents(
//             @Param("schoolId") Long schoolId,
//             @Param("academicYear") String academicYear,
//             @Param("studentClass") String studentClass,
//             @Param("section") Section section,
//             @Param("status") StudentStatus status,
//             @Param("search") String search
//     );
// }

