package com.schoolmanagement.schoolmanagementwebsite.service.fee;

import java.time.LocalDate;
import java.time.LocalDateTime;
import java.time.LocalTime;
import java.util.ArrayList;
import java.util.List;
import java.util.UUID;

import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import com.schoolmanagement.schoolmanagementwebsite.dto.fee.FeeCollectionRequest;
import com.schoolmanagement.schoolmanagementwebsite.dto.fee.FeePaymentRequest;
import com.schoolmanagement.schoolmanagementwebsite.dto.fee.FeePaymentResponse;
import com.schoolmanagement.schoolmanagementwebsite.dto.fee.FeeReceiptResponse;
import com.schoolmanagement.schoolmanagementwebsite.dto.fee.FeeReceiptRow;
import com.schoolmanagement.schoolmanagementwebsite.entity.fee.StudentFeePayment;
import com.schoolmanagement.schoolmanagementwebsite.entity.fee.StudentFeeSchedule;
import com.schoolmanagement.schoolmanagementwebsite.repository.fee.StudentFeePaymentRepository;
import com.schoolmanagement.schoolmanagementwebsite.repository.fee.StudentFeeScheduleRepository;

import lombok.RequiredArgsConstructor;

@Service
@RequiredArgsConstructor
@Transactional
public class StudentFeePaymentService {

    private final StudentFeeScheduleRepository scheduleRepository;

    private final StudentFeePaymentRepository paymentRepository;

    public FeePaymentResponse collectFee(FeePaymentRequest request) {

        if (request.getScheduleIds() == null || request.getScheduleIds().isEmpty()) {
            throw new RuntimeException("Please select fee.");
        }

        if (request.getPaidAmount() == null || request.getPaidAmount() <= 0) {
            throw new RuntimeException("Invalid paid amount.");
        }

        double remainingPayment = request.getPaidAmount();

        String receiptNo = generateReceiptNumber();

        FeePaymentResponse response = new FeePaymentResponse();

        response.setReceiptNo(receiptNo);

        double totalBalance = 0;

        for (Long scheduleId : request.getScheduleIds()) {

            StudentFeeSchedule schedule
                    = scheduleRepository.findById(scheduleId)
                            .orElseThrow(()
                                    -> new RuntimeException("Schedule Not Found"));

            if (remainingPayment <= 0) {
                break;
            }

            remainingPayment
                    = processSingleSchedule(
                            schedule,
                            request,
                            receiptNo,
                            remainingPayment);

            totalBalance += schedule.getDueAmount();
        }

        response.setPaidAmount(request.getPaidAmount());

        response.setFineAmount(request.getFineAmount());

        response.setDiscountAmount(request.getDiscountAmount());
        

        response.setBalanceAmount(totalBalance);

        response.setPaymentMode(request.getPaymentMode());

        response.setCollectedBy(request.getCollectedBy());

        response.setPaymentDate(LocalDate.now().toString());

        response.setPaymentTime(LocalTime.now().toString());

        return response;
    }

    private double processSingleSchedule(
            StudentFeeSchedule schedule,
            FeePaymentRequest request,
            String receiptNo,
            double remainingPayment
    ) {

        double due
                = schedule.getDueAmount();

        due += request.getFineAmount();

        due -= request.getDiscountAmount();

        double paying
                = Math.min(remainingPayment, due);

        schedule.setPaidAmount(
                schedule.getPaidAmount() + paying);

        schedule.setDueAmount(
                due - paying);

        schedule.setFineAmount(
                request.getFineAmount());

        schedule.setDiscountAmount(
                request.getDiscountAmount());

        schedule.setPaymentDate(
                LocalDate.now());

        if (schedule.getDueAmount() <= 0) {

            schedule.setStatus("PAID");

        } else {

            schedule.setStatus("PARTIAL");

        }

        scheduleRepository.save(schedule);

        savePaymentHistory(
                schedule,
                request,
                receiptNo,
                paying
        );

        return remainingPayment - paying;
    }

    private void savePaymentHistory(
            StudentFeeSchedule schedule,
            FeePaymentRequest request,
            String receiptNo,
            Double paidAmount
    ) {

        StudentFeePayment payment
                = new StudentFeePayment();

        payment.setScheduleId(
                schedule.getId());

        payment.setSchoolId(
                schedule.getSchoolId());

        payment.setStudentId(
                schedule.getStudentId());

        payment.setAdmissionNumber(
                schedule.getAdmissionNumber());

        payment.setStudentName(
                schedule.getStudentName());

        payment.setStudentClass(
                schedule.getStudentClass());

        payment.setSection(
                schedule.getSection());

        payment.setSession(
                schedule.getSession());

        payment.setFeeStructureId(
                schedule.getFeeStructureId());

        payment.setFeeMasterId(
                schedule.getFeeMasterId());

        payment.setFeeCode(
                schedule.getFeeCode());

        payment.setFeeName(
                schedule.getFeeName());

        payment.setFeeCategory(
                schedule.getFeeCategory());

        payment.setFeeBatch(
                schedule.getFeeBatch());

        payment.setMonth(
                schedule.getMonth());

        payment.setReceiptNo(
                receiptNo);

        payment.setAmount(
                schedule.getAmount());

        payment.setPaidAmount(
                paidAmount);

                payment.setDueAmount(schedule.getDueAmount());

        payment.setFineAmount(
                request.getFineAmount());

        payment.setDiscountAmount(
                request.getDiscountAmount());

        payment.setPaymentMode(
                request.getPaymentMode());

        payment.setTransactionId(
                request.getTransactionId());

        payment.setBankName(
                request.getBankName());

        payment.setChequeNo(
                request.getChequeNo());

        payment.setRemarks(
                request.getRemarks());

        payment.setCollectedBy(
                request.getCollectedBy());

        payment.setPaymentDate(
                LocalDate.now());

        payment.setPaymentTime(LocalDateTime.now());

        payment.setStatus("SUCCESS");

        paymentRepository.save(payment);
    }

    private String generateReceiptNumber() {

        StudentFeePayment last
                = paymentRepository
                        .findTopByOrderByIdDesc()
                        .orElse(null);

        long nextId
                = last == null ? 1 : last.getId() + 1;

        return String.format("RCPT%06d", nextId);
    }

    public List<StudentFeePayment> getPaymentHistory(String admissionNumber) {

        return paymentRepository
                .findByAdmissionNumberOrderByPaymentDateDescPaymentTimeDesc(
                        admissionNumber);

    }

//     public StudentFeePayment getReceipt(String receiptNo) {

//         return paymentRepository
//                 .findByReceiptNo(receiptNo)
//                 .orElseThrow(()
//                         -> new RuntimeException("Receipt Not Found"));

//     }

    public List<StudentFeePayment> getScheduleHistory(Long scheduleId) {

        return paymentRepository
                .findByScheduleIdOrderByPaymentDateAscPaymentTimeAsc(
                        scheduleId);

    }

    public Long totalReceiptCount() {


        return paymentRepository.count();

    }

    public FeeReceiptResponse getReceipt(String receiptNo) {

    List<StudentFeePayment> payments =
            paymentRepository.findByReceiptNoOrderByIdAsc(receiptNo);

    if (payments.isEmpty()) {
        throw new RuntimeException("Receipt Not Found");
    }

    StudentFeePayment first = payments.get(0);

    FeeReceiptResponse response = new FeeReceiptResponse();

    response.setReceiptNo(first.getReceiptNo());

    response.setAdmissionNumber(first.getAdmissionNumber());

    response.setStudentName(first.getStudentName());

    response.setStudentClass(first.getStudentClass());

    response.setSection(first.getSection());

    response.setSession(first.getSession());

    response.setPaymentDate(first.getPaymentDate().toString());

    response.setPaymentTime(first.getPaymentTime().toString());

    response.setPaymentMode(first.getPaymentMode());

    response.setCollectedBy(first.getCollectedBy());

    double total = 0;
    double paid = 0;
    double due = 0;
    double fine = 0;
    double discount = 0;

    List<FeeReceiptRow> rows = new ArrayList<>();

    for (StudentFeePayment payment : payments) {

        FeeReceiptRow row = new FeeReceiptRow();

        row.setMonth(payment.getMonth());

        row.setFeeCode(payment.getFeeCode());

        row.setFeeName(payment.getFeeName());

        row.setAmount(payment.getAmount());

        row.setPaidAmount(payment.getPaidAmount());

        row.setDueAmount(payment.getDueAmount());

        rows.add(row);

        total += payment.getAmount();

        paid += payment.getPaidAmount();

        due += payment.getDueAmount();

        fine += payment.getFineAmount();

        discount += payment.getDiscountAmount();
    }

    response.setTotalAmount(total);

    response.setPaidAmount(paid);

    response.setDueAmount(due);

    response.setFineAmount(fine);

    response.setDiscountAmount(discount);

    response.setFeeDetails(rows);

    return response;
}
}            
                
                            
            

// @Service
// @RequiredArgsConstructor
// public class StudentFeePaymentService {
//     private final StudentFeeScheduleRepository scheduleRepository;
//     private final StudentFeePaymentRepository paymentRepository;
//     @Transactional
//     public FeeReceiptResponse collectFee(FeeCollectionRequest request) {
//         List<StudentFeeSchedule> schedules =
//                 scheduleRepository.findAllById(request.getScheduleIds());
//         if (schedules.isEmpty()) {
//             throw new RuntimeException("No Schedule Found");
//         }
//         String receiptNo = generateReceiptNo();
//         double totalAmount = 0;
//         double totalPaid = 0;
//         double totalDue = 0;
//         double fine =
//                 request.getFineAmount() == null ? 0 : request.getFineAmount();
//         double discount =
//                 request.getDiscountAmount() == null
//                         ? 0
//                         : request.getDiscountAmount();
//         for (StudentFeeSchedule schedule : schedules) {
//             StudentFeePayment payment = new StudentFeePayment();
//             // ==========================
//             // Student Details
//             // ==========================
//             payment.setSchoolId(schedule.getSchoolId());
//             payment.setStudentId(schedule.getStudentId());
//             payment.setAdmissionNumber(schedule.getAdmissionNumber());
//             payment.setStudentName(schedule.getStudentName());
//             payment.setStudentClass(schedule.getStudentClass());
//             payment.setSection(schedule.getSection());
//             payment.setSession(schedule.getSession());
//             payment.setMobileNumber(schedule.getMobileNumber());
//             // ==========================
//             // Fee Details
//             // ==========================
//             payment.setStudentFeeScheduleId(schedule.getId());
//             payment.setFeeMasterId(schedule.getFeeMasterId());
//             payment.setFeeCode(schedule.getFeeCode());
//             payment.setFeeName(schedule.getFeeName());
//             payment.setMonth(schedule.getMonth());
//             // ==========================
//             // Amount
//             // ==========================
//             double amount = schedule.getAmount();
//             payment.setAmount(amount);
//             payment.setPaidAmount(amount);
//             payment.setDueAmount(0.0);
//             payment.setFineAmount(fine);
//             payment.setDiscountAmount(discount);
//             // ==========================
//             // Payment
//             // ==========================
//             payment.setPaymentMode(request.getPaymentMode());
//             payment.setTransactionId(request.getTransactionId());
//             payment.setChequeNo(request.getChequeNo());
//             payment.setBankName(request.getBankName());
//             payment.setRemarks(request.getRemarks());
//             payment.setCollectedBy(request.getCollectedBy());
//             payment.setReceiptNo(receiptNo);
//             payment.setPaymentDate(LocalDate.now());
//             payment.setPaymentTime(LocalDateTime.now());
//             payment.setStatus("PAID");
//             paymentRepository.save(payment);
//             // ==========================
//             // Update Schedule
//             // ==========================
//             schedule.setPaidAmount(amount);
//             schedule.setDueAmount(0.0);
//             schedule.setFineAmount(fine);
//             schedule.setDiscountAmount(discount);
//             schedule.setPaymentDate(LocalDate.now());
//             schedule.setStatus("PAID");
//             scheduleRepository.save(schedule);
//             totalAmount += amount;
//             totalPaid += amount;
//         }
//         FeeReceiptResponse response = new FeeReceiptResponse();
//         response.setReceiptNo(receiptNo);
//         response.setTotalAmount(totalAmount);
//         response.setPaidAmount(totalPaid);
//         response.setDueAmount(totalDue);
//         response.setFineAmount(fine);
//         response.setDiscountAmount(discount);
//         return response;
//     }
//     private String generateReceiptNo() {
//         return "RCPT-" +
//                 LocalDate.now().getYear() +
//                 "-" +
//                 UUID.randomUUID()
//                         .toString()
//                         .substring(0, 6)
//                         .toUpperCase();
//     }
//     // ==========================================
// // Payment History
// // ==========================================
// public List<StudentFeePayment> getPaymentHistory(
//         String admissionNumber) {
//     return paymentRepository
//             .findByAdmissionNumberOrderByPaymentDateDesc(
//                     admissionNumber);
// }
// // ==========================================
// // Receipt
// // ==========================================
// public List<StudentFeePayment> getReceipt(
//         String receiptNo) {
//     return paymentRepository.findByReceiptNo(receiptNo);
// }
// }
                
                             
            
                
                            
                
