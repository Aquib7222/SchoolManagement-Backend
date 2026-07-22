package com.schoolmanagement.schoolmanagementwebsite.repository.fee;

import java.util.List;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import com.schoolmanagement.schoolmanagementwebsite.entity.fee.StudentFeeSchedule;

@Repository
public interface StudentFeeScheduleRepository
        extends JpaRepository<StudentFeeSchedule, Long> {

    List<StudentFeeSchedule> findByAdmissionNumberOrderByMonthAsc(
            String admissionNumber
    );

    List<StudentFeeSchedule> findByStudentId(Long studentId);

    List<StudentFeeSchedule> findBySchoolId(Long schoolId);

    List<StudentFeeSchedule> findByStudentIdAndMonth(
            Long studentId,
            String month
    );

    boolean existsByStudentFeeIdAndMonth(
            Long studentFeeId,
            String month
    );
}