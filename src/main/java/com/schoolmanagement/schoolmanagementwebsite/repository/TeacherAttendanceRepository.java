package com.schoolmanagement.schoolmanagementwebsite.repository;

import java.time.LocalDate;
import java.util.List;
import java.util.Optional;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;

import com.schoolmanagement.schoolmanagementwebsite.entity.TeacherAttendance;

public interface TeacherAttendanceRepository
        extends JpaRepository<TeacherAttendance, Long> {

    Optional<TeacherAttendance>
            findByTeacherIdAndAttendanceDate(Long teacherId, LocalDate date);

    List<TeacherAttendance>
            findBySchoolIdAndAttendanceDate(Long schoolId, LocalDate date);

            Optional<TeacherAttendance>
findByTeacherIdAndSchoolIdAndAttendanceDate(
        Long teacherId,
        Long schoolId,
        LocalDate date
);

    List<TeacherAttendance>
            findByTeacherId(Long teacherId);

             @Query("""
        SELECT ta 
        FROM TeacherAttendance ta
        WHERE ta.school.id = :schoolId
        AND ta.attendanceDate BETWEEN :startDate AND :endDate
    """)
    List<TeacherAttendance> findMonthlyAttendance(
            @Param("schoolId") Long schoolId,
            @Param("startDate") LocalDate startDate,
            @Param("endDate") LocalDate endDate
    );
}
