package com.schoolmanagement.schoolmanagementwebsite.repository;



import com.schoolmanagement.schoolmanagementwebsite.entity.Attendance;
import com.schoolmanagement.schoolmanagementwebsite.enums.Section;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.time.LocalDate;
import java.util.List;
import java.util.Optional;

@Repository
public interface AttendanceRepository extends JpaRepository<Attendance, Long> {

    // Check Attendance Already Exists
    Optional<Attendance> findBySchoolIdAndStudentIdAndAttendanceDate(
            Long schoolId,
            Long studentId,
            LocalDate attendanceDate
    );

    // Student Attendance
    List<Attendance> findByStudentId(Long studentId);

    // Student Attendance Between Dates
    List<Attendance> findByStudentIdAndAttendanceDateBetween(
            Long studentId,
            LocalDate fromDate,
            LocalDate toDate
    );

    // Class Attendance By Date
    List<Attendance> findBySchoolIdAndAcademicYearAndStudentClassAndSectionAndAttendanceDate(
            Long schoolId,
            String academicYear,
            String studentClass,
            Section section,
            LocalDate attendanceDate
    );

    // Monthly Attendance
    List<Attendance> findBySchoolIdAndAttendanceDateBetween(
            Long schoolId,
            LocalDate fromDate,
            LocalDate toDate
    );

    List<Attendance> findBySchoolIdAndAcademicYearAndStudentClassAndSectionAndAttendanceDateBetween(
        Long schoolId,
        String academicYear,
        String studentClass,
        Section section,
        LocalDate fromDate,
        LocalDate toDate
);

}