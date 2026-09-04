package com.schoolmanagement.schoolmanagementwebsite.repository;

import com.schoolmanagement.schoolmanagementwebsite.entity.Period;

import java.time.DayOfWeek;
import java.util.List;
import java.util.Optional;

import org.springframework.data.jpa.repository.JpaRepository;

public interface PeriodRepository extends JpaRepository<Period, Long> {

    List<Period> findBySchoolIdOrderByAcademicYearAscDayOfWeekAscStartTimeAsc(
            Long schoolId
    );

    List<Period> findBySchoolIdAndAcademicYearOrderByDayOfWeekAscStartTimeAsc(
            Long schoolId,
            String academicYear
    );

    List<Period> findBySchoolIdAndAcademicYearAndDayOfWeekOrderByStartTimeAsc(
            Long schoolId,
            String academicYear,
            DayOfWeek dayOfWeek
    );

    Optional<Period> findBySchoolIdAndAcademicYearAndDayOfWeekAndPeriodName(
            Long schoolId,
            String academicYear,
            DayOfWeek dayOfWeek,
            String periodName
    );

    boolean existsBySchoolIdAndAcademicYearAndDayOfWeekAndPeriodName(
            Long schoolId,
            String academicYear,
            DayOfWeek dayOfWeek,
            String periodName
    );
}