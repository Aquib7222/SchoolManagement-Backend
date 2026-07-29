package com.schoolmanagement.schoolmanagementwebsite.service.fee;

import java.time.LocalDate;
import java.time.Month;
import java.util.List;

import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import com.schoolmanagement.schoolmanagementwebsite.dto.fee.GenerateFeeRequest;
import com.schoolmanagement.schoolmanagementwebsite.entity.fee.StudentFee;
import com.schoolmanagement.schoolmanagementwebsite.entity.fee.StudentFeeSchedule;
import com.schoolmanagement.schoolmanagementwebsite.repository.fee.StudentFeeRepository;
import com.schoolmanagement.schoolmanagementwebsite.repository.fee.StudentFeeScheduleRepository;

import lombok.RequiredArgsConstructor;

@Service
@RequiredArgsConstructor
public class StudentFeeScheduleService {

    private final StudentFeeRepository studentFeeRepository;

    private final StudentFeeScheduleRepository studentFeeScheduleRepository;

    @Transactional
    public void generateFee(GenerateFeeRequest request) {

        List<StudentFee> studentFees
                = studentFeeRepository.findByAdmissionNumber(
                        request.getAdmissionNumber());

        if (studentFees.isEmpty()) {
            throw new RuntimeException("No Assigned Fee Found.");
        }

        for (var selectedSchedule : request.getSchedules()) {

            StudentFee studentFee = studentFees.stream()
                    .filter(f
                            -> f.getFeeMasterId().equals(selectedSchedule.getFeeMasterId()))
                    .findFirst()
                    .orElse(null);

            if (studentFee == null) {
                continue;
            }

            boolean exists
                    = studentFeeScheduleRepository.existsByStudentFeeIdAndMonth(
                            studentFee.getId(),
                            selectedSchedule.getMonth());

            if (exists) {
                continue;
            }

            StudentFeeSchedule schedule = new StudentFeeSchedule();

            // ==========================
            // Student Details
            // ==========================
            schedule.setSchoolId(studentFee.getSchoolId());

            schedule.setStudentId(studentFee.getStudentId());

            schedule.setAdmissionNumber(studentFee.getAdmissionNumber());

            schedule.setStudentName(studentFee.getStudentName());

            schedule.setStudentClass(studentFee.getStudentClass());

            schedule.setSection(studentFee.getSection());

            schedule.setMobileNumber(studentFee.getMobileNumber());

            schedule.setSession(studentFee.getSession());

            // ==========================
            // Fee Details
            // ==========================
            schedule.setStudentFeeId(studentFee.getId());

            schedule.setFeeStructureId(studentFee.getFeeStructureId());

            schedule.setFeeMasterId(studentFee.getFeeMasterId());

            schedule.setFeeCode(studentFee.getFeeCode());

            schedule.setFeeName(studentFee.getFeeName());

            schedule.setFeeCategory(studentFee.getFeeCategory());

            schedule.setFeeBatch(studentFee.getFeeBatch());

            // ==========================
            // Month
            // ==========================
            schedule.setMonth(selectedSchedule.getMonth());

            // ==========================
            // Amount
            // ==========================
            schedule.setAmount(selectedSchedule.getAmount());

            schedule.setPaidAmount(0.0);

            schedule.setDueAmount(selectedSchedule.getAmount());
            schedule.setFineAmount(selectedSchedule.getAmount());
            schedule.setDiscountAmount(selectedSchedule.getAmount());

            schedule.setStatus("UNPAID");

            schedule.setGenerateDate(LocalDate.now());
            schedule.setPaymentDate(LocalDate.now());

            schedule.setDueDate(getDueDate(selectedSchedule.getMonth()));

            studentFeeScheduleRepository.save(schedule);
        }
    }

     public List<StudentFeeSchedule> getAll(){
        return studentFeeScheduleRepository.findAll();
    }

    // ===========================
    // Due Date
    // ===========================
    private LocalDate getDueDate(String month) {

        Month m = Month.valueOf(month.toUpperCase());

        int year = LocalDate.now().getYear();

        return LocalDate.of(year, m, 10);
    }

    public List<StudentFeeSchedule> getStudentSchedule(String admissionNumber) {

        return studentFeeScheduleRepository
                .findByAdmissionNumberOrderByMonthAsc(admissionNumber);

    }

    @Transactional
    public void undoFee(List<Long> ids) {

        studentFeeScheduleRepository.deleteAllById(ids);

    }

    public List<StudentFee> getNewSchedule(String admissionNumber) {

        return studentFeeRepository.findByAdmissionNumber(admissionNumber);

        
    }
   

}
