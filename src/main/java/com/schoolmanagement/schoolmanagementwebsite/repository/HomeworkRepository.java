package com.schoolmanagement.schoolmanagementwebsite.repository;

import com.schoolmanagement.schoolmanagementwebsite.entity.Homework;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;

public interface HomeworkRepository
        extends JpaRepository<Homework, Long> {

    List<Homework> findBySchoolIdAndTeacherIdAndAcademicYearAndActiveTrueOrderByHomeworkDateDesc(
            Long schoolId,
            Long teacherId,
            String academicYear
    );

    List<Homework> findBySchoolIdAndAcademicYearAndStudentClassAndSectionAndActiveTrueOrderByHomeworkDateDesc(
            Long schoolId,
            String academicYear,
            String studentClass,
            String section
    );

    boolean existsBySchoolIdAndAcademicYearAndStudentClassAndSectionAndSubjectAndHomeworkDate(
            Long schoolId,
            String academicYear,
            String studentClass,
            String section,
            String subject,
            java.time.LocalDate homeworkDate
    );
}