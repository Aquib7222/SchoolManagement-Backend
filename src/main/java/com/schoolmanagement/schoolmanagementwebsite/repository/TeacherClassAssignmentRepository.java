package com.schoolmanagement.schoolmanagementwebsite.repository;

import com.schoolmanagement.schoolmanagementwebsite.entity.TeacherClassAssignment;

import org.springframework.data.jpa.repository.JpaRepository;

import java.time.DayOfWeek;
import java.util.List;

public interface TeacherClassAssignmentRepository
        extends JpaRepository<TeacherClassAssignment, Long> {

    List<TeacherClassAssignment>
    findBySchoolIdAndAcademicYearAndDayOfWeek(
            Long schoolId,
            String academicYear,
            DayOfWeek dayOfWeek
    );

    List<TeacherClassAssignment>
    findBySchoolIdAndAcademicYearAndTeacherIdAndDayOfWeek(
            Long schoolId,
            String academicYear,
            Long teacherId,
            DayOfWeek dayOfWeek
    );

    List<TeacherClassAssignment>
findBySchoolIdAndAcademicYearAndTeacherId(
        Long schoolId,
        String academicYear,
        Long teacherId
);

    boolean existsBySchoolIdAndAcademicYearAndTeacherIdAndDayOfWeekAndPeriodIdAndActiveTrue(
            Long schoolId,
            String academicYear,
            Long teacherId,
            DayOfWeek dayOfWeek,
            Long periodId
    );

    boolean existsBySchoolIdAndAcademicYearAndStudentClassAndSectionAndDayOfWeekAndPeriodIdAndActiveTrue(
            Long schoolId,
            String academicYear,
            String studentClass,
            String section,
            DayOfWeek dayOfWeek,
            Long periodId
    );

    boolean existsBySchoolIdAndAcademicYearAndRoomAndDayOfWeekAndPeriodIdAndActiveTrue(
            Long schoolId,
            String academicYear,
            String room,
            DayOfWeek dayOfWeek,
            Long periodId
    );
}