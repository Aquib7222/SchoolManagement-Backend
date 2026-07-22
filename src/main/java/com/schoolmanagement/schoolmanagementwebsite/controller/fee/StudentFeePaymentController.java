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

import java.util.List;

import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

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

//     @GetMapping("/receipt/{receiptNo}")
//     public ResponseEntity<StudentFeePayment> receipt(
//             @PathVariable String receiptNo) {

//         return ResponseEntity.ok(
//                 paymentService.getReceipt(receiptNo));

//     }
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

}