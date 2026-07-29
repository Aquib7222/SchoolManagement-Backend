package com.schoolmanagement.schoolmanagementwebsite.repository;

import java.util.List;
import java.util.Optional;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;

import com.schoolmanagement.schoolmanagementwebsite.entity.Teacher;

public interface TeacherRepository extends JpaRepository<Teacher, Long> {

    @Query("""
SELECT t FROM Teacher t
WHERE t.school.id = :schoolId
AND (
LOWER(t.employeeId) LIKE LOWER(CONCAT('%', :keyword, '%'))
OR LOWER(t.firstName) LIKE LOWER(CONCAT('%', :keyword, '%'))
OR LOWER(t.phoneNumber) LIKE LOWER(CONCAT('%', :keyword, '%'))
OR LOWER(t.email) LIKE LOWER(CONCAT('%', :keyword, '%'))
OR LOWER(t.department) LIKE LOWER(CONCAT('%', :keyword, '%'))
OR LOWER(t.designation) LIKE LOWER(CONCAT('%', :keyword, '%'))
)
""")
List<Teacher> searchTeachers(
        @Param("keyword") String keyword,
        @Param("schoolId") Long schoolId);

    boolean existsByEmployeeId(String employeeId);
    // Fetch by employeeId and school
    Optional<Teacher> findByEmployeeIdAndSchoolId(String employeeId, Long schoolId);
    List<Teacher> findBySchoolIdAndStatus(Long schoolId, String status);

    @Query("""
SELECT t FROM Teacher t
WHERE t.school.id = :schoolId
AND LOWER(t.employeeId) = LOWER(:employeeId)
""")
Teacher findTeacherByEmployeeId(
        @Param("employeeId") String employeeId,
        @Param("schoolId") Long schoolId
);
    

}
