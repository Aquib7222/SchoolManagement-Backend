// package com.schoolmanagement.schoolmanagementwebsite.repository.fee;
// import java.util.List;
// import org.springframework.data.jpa.repository.JpaRepository;
// import com.schoolmanagement.schoolmanagementwebsite.entity.fee.StudentFeePayment;
// import com.schoolmanagement.schoolmanagementwebsite.entity.fee.StudentFeeSchedule;
// public interface StudentFeePaymentRepository
//         extends JpaRepository<StudentFeePayment, Long> {
//     List<StudentFeePayment> findByAdmissionNumberOrderByPaymentDateDesc(
//             String admissionNumber);
//     boolean existsByReceiptNo(String receiptNo);
//     List<StudentFeePayment> findByReceiptNo(String receiptNo);
//     List<StudentFeeSchedule> findAllById(Iterable<Long> ids);
// }
package com.schoolmanagement.schoolmanagementwebsite.repository.fee;

import java.time.LocalDate;
import java.util.List;
import java.util.Optional;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;

import com.schoolmanagement.schoolmanagementwebsite.entity.fee.StudentFeePayment;

public interface StudentFeePaymentRepository
        extends JpaRepository<StudentFeePayment, Long> {

    List<StudentFeePayment> findByAdmissionNumberOrderByPaymentDateDescPaymentTimeDesc(
            String admissionNumber);

    List<StudentFeePayment> findByScheduleIdOrderByPaymentDateAscPaymentTimeAsc(
            Long scheduleId);

    Optional<StudentFeePayment> findTopByOrderByIdDesc();

    Optional<StudentFeePayment> findByReceiptNo(String receiptNo);

    List<StudentFeePayment> findByReceiptNoOrderByIdAsc(String receiptNo);

    List<StudentFeePayment>
            findByPaymentDateAndStatusAndDeletedFalseOrderByPaymentTimeAsc(
                    LocalDate paymentDate,
                    String status
            );

    @Query("""
SELECT p
FROM StudentFeePayment p
WHERE YEAR(p.paymentDate)=:year
AND MONTH(p.paymentDate)=:month
AND p.status='SUCCESS'
AND p.deleted=false
ORDER BY p.paymentDate,p.paymentTime
""")
    List<StudentFeePayment> monthlyCollectionReport(
            int year,
            int month
    );

//     List<StudentFeePayment> findByReceiptNoOrderByIdAsc(String receiptNo);
}
