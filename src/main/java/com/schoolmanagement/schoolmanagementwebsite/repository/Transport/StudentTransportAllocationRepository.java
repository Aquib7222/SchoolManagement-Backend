package com.schoolmanagement.schoolmanagementwebsite.repository.Transport;

import java.util.List;
import java.util.Optional;

import org.springframework.data.jpa.repository.JpaRepository;

import com.schoolmanagement.schoolmanagementwebsite.entity.Transport.StudentTransportAllocation;

public interface StudentTransportAllocationRepository
        extends JpaRepository<StudentTransportAllocation, Long> {

    List<StudentTransportAllocation> findBySchoolIdAndAcademicYear(
            Long schoolId,
            String academicYear
    );

    Optional<StudentTransportAllocation>
    findBySchoolIdAndStudentIdAndAcademicYear(
            Long schoolId,
            Long studentId,
            String academicYear
    );

    Optional<StudentTransportAllocation>
    findBySchoolIdAndId(
            Long schoolId,
            Long id
    );

    List<StudentTransportAllocation>
    findBySchoolIdAndRouteIdAndAcademicYear(
            Long schoolId,
            Long routeId,
            String academicYear
    );
}