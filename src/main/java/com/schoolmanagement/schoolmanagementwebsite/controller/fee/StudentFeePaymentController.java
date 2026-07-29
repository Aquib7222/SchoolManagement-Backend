// package com.schoolmanagement.schoolmanagementwebsite.controller.fee;
// import java.util.List;
// import org.springframework.http.ResponseEntity;
// import org.springframework.web.bind.annotation.*;
// import com.schoolmanagement.schoolmanagementwebsite.dto.fee.FeeCollectionRequest;
// import com.schoolmanagement.schoolmanagementwebsite.dto.fee.FeeReceiptResponse;
// import com.schoolmanagement.schoolmanagementwebsite.entity.fee.StudentFeePayment;
// import com.schoolmanagement.schoolmanagementwebsite.service.fee.StudentFeePaymentService;
// import lombok.RequiredArgsConstructor;
// @RestController
// @RequestMapping("/api/student-fee")
// @RequiredArgsConstructor
// @CrossOrigin(origins = "http://localhost:5173")
// public class StudentFeePaymentController {
//     private final StudentFeePaymentService paymentService;
//     // ==========================================
//     // Collect Fee
//     // ==========================================
//     @PostMapping("/payment")
//     public ResponseEntity<FeeReceiptResponse> collectFee(
//             @RequestBody FeeCollectionRequest request) {
//         return ResponseEntity.ok(
//                 paymentService.collectFee(request));
//     }
//     // ==========================================
//     // Payment History
//     // ==========================================
//     @GetMapping("/payment/{admissionNumber}")
//     public ResponseEntity<List<StudentFeePayment>> getPaymentHistory(
//             @PathVariable String admissionNumber) {
//         return ResponseEntity.ok(
//                 paymentService.getPaymentHistory(admissionNumber));
//     }
//     // ==========================================
//     // Receipt
//     // ==========================================
//     @GetMapping("/receipt/{receiptNo}")
//     public ResponseEntity<List<StudentFeePayment>> getReceipt(
//             @PathVariable String receiptNo) {
//         return ResponseEntity.ok(
//                 paymentService.getReceipt(receiptNo));
//     }
// }
package com.schoolmanagement.schoolmanagementwebsite.controller.fee;

import java.time.LocalDate;
import java.util.List;

import org.springframework.http.ResponseEntity;
import org.springframework.security.core.Authentication;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import com.schoolmanagement.schoolmanagementwebsite.dto.fee.FeePaymentRequest;
import com.schoolmanagement.schoolmanagementwebsite.dto.fee.FeePaymentResponse;
import com.schoolmanagement.schoolmanagementwebsite.dto.fee.FeeReceiptResponse;
import com.schoolmanagement.schoolmanagementwebsite.entity.fee.StudentFeePayment;
import com.schoolmanagement.schoolmanagementwebsite.service.fee.StudentFeePaymentService;

import lombok.RequiredArgsConstructor;

@RestController
@RequiredArgsConstructor
@RequestMapping("/api/student-fee/payment")
@CrossOrigin(origins = "http://localhost:5173")
public class StudentFeePaymentController {

    private final StudentFeePaymentService paymentService;

    @PostMapping
    public ResponseEntity<FeePaymentResponse> collectFee(
            @RequestBody FeePaymentRequest request) {

        return ResponseEntity.ok(
                paymentService.collectFee(request));

    }

    @GetMapping("/history/{admissionNumber}")
    public ResponseEntity<List<StudentFeePayment>> history(
            @PathVariable String admissionNumber) {

        return ResponseEntity.ok(
                paymentService.getPaymentHistory(admissionNumber));

    }

    @GetMapping("/receipt/{receiptNo}")
    public ResponseEntity<FeeReceiptResponse> getReceipt(
            @PathVariable String receiptNo) {

        return ResponseEntity.ok(
                paymentService.getReceipt(receiptNo));
    }

    @GetMapping("/schedule/{scheduleId}")
    public ResponseEntity<List<StudentFeePayment>> scheduleHistory(
            @PathVariable Long scheduleId) {

        return ResponseEntity.ok(
                paymentService.getScheduleHistory(scheduleId));

    }

    @DeleteMapping("/receipt/{receiptNo}")
    public ResponseEntity<?> deleteReceipt(
            @PathVariable String receiptNo,
            Authentication authentication
    ) {

        String username = authentication.getName();

        String message
                = paymentService.deleteReceipt(
                        receiptNo,
                        username);

        return ResponseEntity.ok(message);

    }

    @GetMapping("/report/daily")
    public ResponseEntity<List<StudentFeePayment>> dailyReport(
            @RequestParam LocalDate date
    ) {
        return ResponseEntity.ok(
                paymentService.dailyCollectionReport(date)
        );
    }

    @GetMapping("/report/monthly")
    public ResponseEntity<List<StudentFeePayment>> monthlyReport(
            @RequestParam int year,
            @RequestParam int month
    ) {

        return ResponseEntity.ok(
                paymentService.monthlyCollectionReport(year, month)
        );

    }

}
