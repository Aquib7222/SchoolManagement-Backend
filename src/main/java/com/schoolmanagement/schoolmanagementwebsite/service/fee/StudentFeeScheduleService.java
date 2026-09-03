
package com.schoolmanagement.schoolmanagementwebsite.service.fee;

import java.time.LocalDate;
import java.time.Month;
import java.util.List;

import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import com.schoolmanagement.schoolmanagementwebsite.dto.fee.GenerateFeeRequest;
import com.schoolmanagement.schoolmanagementwebsite.entity.StudentAdmissionFee;
import com.schoolmanagement.schoolmanagementwebsite.entity.fee.StudentFee;
import com.schoolmanagement.schoolmanagementwebsite.entity.fee.StudentFeeSchedule;
import com.schoolmanagement.schoolmanagementwebsite.repository.StudentAdmissionFeeRepository;
import com.schoolmanagement.schoolmanagementwebsite.repository.fee.StudentFeeRepository;
import com.schoolmanagement.schoolmanagementwebsite.repository.fee.StudentFeeScheduleRepository;

import lombok.RequiredArgsConstructor;

@Service
@RequiredArgsConstructor
public class StudentFeeScheduleService {

    private final StudentFeeRepository studentFeeRepository;

    private final StudentFeeScheduleRepository studentFeeScheduleRepository;

    private final StudentAdmissionFeeRepository studentAdmissionFeeRepository;


    // =========================================================
    // GENERATE FEE
    // =========================================================

    @Transactional
    public void generateFee(GenerateFeeRequest request) {

        List<StudentFee> studentFees =
                studentFeeRepository.findByAdmissionNumber(
                        request.getAdmissionNumber()
                );

        if (studentFees.isEmpty()) {
            throw new RuntimeException("No Assigned Fee Found.");
        }


        List<StudentAdmissionFee> admissionPayments =
                studentAdmissionFeeRepository
                        .findByAdmission_IdAndFeeType(
                                request.getAdmissionId(),
                                "MONTHLY"
                        );


        for (var selectedSchedule : request.getSchedules()) {

            StudentFee studentFee =
                    studentFees.stream()
                            .filter(f ->
                                    f.getFeeMasterId()
                                            .equals(
                                                    selectedSchedule
                                                            .getFeeMasterId()
                                            )
                            )
                            .findFirst()
                            .orElse(null);

            if (studentFee == null) {
                continue;
            }


            // =====================================================
            // PREVENT DUPLICATE MONTH
            // =====================================================

            boolean exists =
                    studentFeeScheduleRepository
                            .existsByStudentFeeIdAndMonth(
                                    studentFee.getId(),
                                    selectedSchedule.getMonth()
                            );

            if (exists) {
                continue;
            }


            StudentFeeSchedule schedule =
                    new StudentFeeSchedule();


            // =====================================================
            // STUDENT DETAILS
            // =====================================================

            schedule.setSchoolId(
                    studentFee.getSchoolId()
            );

            schedule.setStudentId(
                    studentFee.getStudentId()
            );

            schedule.setAdmissionNumber(
                    studentFee.getAdmissionNumber()
            );

            schedule.setStudentName(
                    studentFee.getStudentName()
            );

            schedule.setStudentClass(
                    studentFee.getStudentClass()
            );

            schedule.setSection(
                    studentFee.getSection()
            );

            schedule.setMobileNumber(
                    studentFee.getMobileNumber()
            );

            schedule.setSession(
                    studentFee.getSession()
            );


            // =====================================================
            // FEE DETAILS
            // =====================================================

            schedule.setStudentFeeId(
                    studentFee.getId()
            );

            schedule.setFeeStructureId(
                    studentFee.getFeeStructureId()
            );

            schedule.setFeeMasterId(
                    studentFee.getFeeMasterId()
            );

            schedule.setFeeCode(
                    studentFee.getFeeCode()
            );

            schedule.setFeeName(
                    studentFee.getFeeName()
            );

            schedule.setFeeCategory(
                    studentFee.getFeeCategory()
            );

            schedule.setFeeBatch(
                    studentFee.getFeeBatch()
            );


            // =====================================================
            // MONTH
            // =====================================================

            schedule.setMonth(
                    selectedSchedule.getMonth()
            );


            // =====================================================
            // AMOUNT
            // =====================================================

            double feeAmount =
                    selectedSchedule.getAmount() == null
                            ? 0.0
                            : selectedSchedule.getAmount();


            /*
             * Find already paid amount for this month.
             */
            double paidAmount =
                    admissionPayments.stream()

                            .filter(payment ->
                                    payment.getMonth() != null
                                            && selectedSchedule
                                                    .getMonth()
                                                    .equalsIgnoreCase(
                                                            payment.getMonth()
                                                    )
                            )

                            .mapToDouble(payment ->
                                    payment.getPaidAmount() == null
                                            ? 0.0
                                            : payment.getPaidAmount()
                            )

                            .sum();


            // =====================================================
            // DUE
            // =====================================================

            double dueAmount =
                    Math.max(
                            feeAmount - paidAmount,
                            0.0
                    );


            schedule.setAmount(
                    feeAmount
            );

            schedule.setPaidAmount(
                    paidAmount
            );

            schedule.setDueAmount(
                    dueAmount
            );


            // =====================================================
            // FINE / DISCOUNT
            // =====================================================

            schedule.setFineAmount(
                    0.0
            );

            schedule.setDiscountAmount(
                    0.0
            );


            // =====================================================
            // STATUS
            // =====================================================

            if (dueAmount <= 0) {

                schedule.setStatus(
                        "PAID"
                );

            } else if (paidAmount > 0) {

                schedule.setStatus(
                        "PARTIAL"
                );

            } else {

                schedule.setStatus(
                        "UNPAID"
                );
            }


            // =====================================================
            // DATES
            // =====================================================

            schedule.setGenerateDate(
                    LocalDate.now()
            );

            schedule.setPaymentDate(
                    paidAmount > 0
                            ? LocalDate.now()
                            : null
            );

            schedule.setDueDate(
                    getDueDate(
                            selectedSchedule.getMonth()
                    )
            );


            studentFeeScheduleRepository.save(
                    schedule
            );
        }
    }


    // =========================================================
    // GET ALL
    // =========================================================

    public List<StudentFeeSchedule> getAll() {

        return studentFeeScheduleRepository.findAll();
    }


    // =========================================================
    // DUE DATE
    // =========================================================

    private LocalDate getDueDate(String month) {

        Month m =
                Month.valueOf(
                        month.toUpperCase()
                );

        int year =
                LocalDate.now().getYear();

        return LocalDate.of(
                year,
                m,
                10
        );
    }


    // =========================================================
    // STUDENT SCHEDULE
    // =========================================================

    public List<StudentFeeSchedule> getStudentSchedule(
            String admissionNumber
    ) {

        return studentFeeScheduleRepository
                .findByAdmissionNumberOrderByMonthAsc(
                        admissionNumber
                );
    }


    // =========================================================
    // UNDO
    // =========================================================

    @Transactional
    public void undoFee(List<Long> ids) {

        studentFeeScheduleRepository
                .deleteAllById(ids);
    }


    // =========================================================
    // NEW SCHEDULE
    // =========================================================

    public List<StudentFee> getNewSchedule(
            String admissionNumber
    ) {

        return studentFeeRepository
                .findByAdmissionNumber(
                        admissionNumber
                );
    }
}