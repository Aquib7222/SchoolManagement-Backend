package com.schoolmanagement.schoolmanagementwebsite.repository.fee;

import java.util.List;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

// import com.schoolmanagement.schoolmanagementwebsite.entity.StudentFee;
import com.schoolmanagement.schoolmanagementwebsite.entity.fee.StudentFee;

@Repository
public interface StudentFeeRepository extends JpaRepository<StudentFee, Long> {

    List<StudentFee> findByAdmissionNumber(String admissionNumber);

    List<StudentFee> findByStudentId(Long studentId);

    List<StudentFee> findBySchoolId(Long schoolId);

    List<StudentFee> findBySchoolIdAndAdmissionNumber(
            Long schoolId,
            String admissionNumber
    );

    boolean existsByStudentIdAndFeeStructureIdAndFeeMasterId(
            Long studentId,
            Long feeStructureId,
            Long feeMasterId
    );

    boolean existsByStudentIdAndFeeMasterId(Long studentId, Long feeMasterId);

    boolean existsByStudentIdAndFeeStructureDetailId(
        Long studentId,
        Long feeStructureDetailId
);

  

}
