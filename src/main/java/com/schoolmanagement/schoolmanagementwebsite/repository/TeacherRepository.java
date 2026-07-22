package com.schoolmanagement.schoolmanagementwebsite.repository;

import java.util.List;
import java.util.Optional;

import org.springframework.data.jpa.repository.JpaRepository;

import com.schoolmanagement.schoolmanagementwebsite.entity.Teacher;

public interface TeacherRepository extends JpaRepository<Teacher, Long> {

    boolean existsByEmployeeId(String employeeId);
    // Fetch by employeeId and school
    Optional<Teacher> findByEmployeeIdAndSchoolId(String employeeId, Long schoolId);
    List<Teacher> findBySchoolIdAndStatus(Long schoolId, String status);

}
