// package com.schoolmanagement.schoolmanagementwebsite.service;

// import org.springframework.stereotype.Service;
// import java.time.LocalDateTime;
// import com.schoolmanagement.schoolmanagementwebsite.dto.AdmissionFeePaymentRequest;
// import com.schoolmanagement.schoolmanagementwebsite.entity.Admission;
// import com.schoolmanagement.schoolmanagementwebsite.entity.StudentFee;
// import com.schoolmanagement.schoolmanagementwebsite.entity.AdmissionFeePayment;
// import com.schoolmanagement.schoolmanagementwebsite.repository.AdmissionFeePaymentRepository;
// import com.schoolmanagement.schoolmanagementwebsite.repository.AdmissionRepository;
// import com.schoolmanagement.schoolmanagementwebsite.repository.StudentFeeRepository;

// import jakarta.transaction.Transactional;
// import lombok.RequiredArgsConstructor;
// import tools.jackson.databind.ObjectMapper;


// @Service
// @RequiredArgsConstructor
// public class AdmissionFeePaymentService {

//     private final AdmissionRepository admissionRepo;
//     private final AdmissionFeePaymentRepository admissionFeeRepo;
//     private final StudentFeeRepository studentFeeRepo;
//     private final ObjectMapper objectMapper;

//     @Transactional
//     public void payAdmissionFee(AdmissionFeePaymentRequest req) throws Exception {

//         Admission admission = admissionRepo.findById(req.getAdmissionId())
//                 .orElseThrow(() -> new RuntimeException("Admission not found"));

//         /* ================= SAVE ADMISSION FEE ================= */
//         AdmissionFeePayment payment = new AdmissionFeePayment();

//         payment.setAdmission(admission);
//         payment.setSchoolId(req.getSchoolId());
//         payment.setSession(req.getSession());
//         payment.setStandard(req.getStandard());

//         payment.setTuitionFee(objectMapper.writeValueAsString(req.getTuitionFee()));
//         payment.setPaidMonths(objectMapper.writeValueAsString(req.getPaidMonths()));

//         payment.setAnnualCharges(req.getFixedFees().get("annualCharges").getAmount());
//         payment.setExamCharges(req.getFixedFees().get("examCharges").getAmount());
//         payment.setSportsFee(req.getFixedFees().get("sportsFee").getAmount());
//         payment.setPhotoCardFee(req.getFixedFees().get("photoCardFee").getAmount());
//         payment.setLibraryLabFee(req.getFixedFees().get("libraryLabFee").getAmount());
//         payment.setTransportFee(req.getFixedFees().get("transportFee").getAmount());
//         payment.setMiscCharges(req.getFixedFees().get("miscCharges").getAmount());
//         payment.setRegistrationFee(req.getFixedFees().get("registrationFee").getAmount());
//         payment.setSecurityMoney(req.getFixedFees().get("securityMoney").getAmount());

//         payment.setTotalAmount(req.getTotalAmount());
//         payment.setPaymentMode(req.getPaymentMode());
//         payment.setPaymentDate(LocalDateTime.now());

//         admissionFeeRepo.save(payment);

//         /* ================= SAVE INTO STUDENT_FEE (LEDGER) ================= */

//         // Admission fixed fees
//         req.getFixedFees().forEach((key, val) -> {
//             if (val.getAmount() > 0) {
//                 StudentFee fee = new StudentFee();
//                 fee.setAdmission(admission);
//                 fee.setSchoolId(req.getSchoolId());
//                 fee.setFeeType("ADMISSION");
//                 fee.setAmount(val.getAmount());
//                 fee.setDiscount(val.getDiscount());
//                 fee.setPaidAmount(val.getAmount() - val.getDiscount());
//                 fee.setPaymentMode(req.getPaymentMode());
//                 fee.setPaymentDate(LocalDateTime.now());
//                 studentFeeRepo.save(fee);
//             }
//         });

//         // Tuition fees
//         for (String month : req.getPaidMonths()) {
//             StudentFee fee = new StudentFee();
//             fee.setAdmission(admission);
//             fee.setSchoolId(req.getSchoolId());
//             fee.setFeeType("MONTHLY");
//             fee.setMonth(month);
//             fee.setAmount(req.getTuitionFee().get(month));
//             fee.setPaidAmount(req.getTuitionFee().get(month));
//             fee.setPaymentMode(req.getPaymentMode());
//             fee.setPaymentDate(LocalDateTime.now());
//             studentFeeRepo.save(fee);
//         }
//     }
// }

// package com.schoolmanagement.schoolmanagementwebsite.service;

// import org.springframework.stereotype.Service;
// import java.time.LocalDateTime;
// import java.util.*;

// import com.schoolmanagement.schoolmanagementwebsite.dto.AdmissionFeePaymentRequest;
// import com.schoolmanagement.schoolmanagementwebsite.dto.AdmissionFeePaymentRequest.FeeAmount;
// import com.schoolmanagement.schoolmanagementwebsite.dto.FeeAmountDTO;
// import com.schoolmanagement.schoolmanagementwebsite.entity.Admission;
// import com.schoolmanagement.schoolmanagementwebsite.entity.StudentFee;
// import com.schoolmanagement.schoolmanagementwebsite.entity.AdmissionFeePayment;
// import com.schoolmanagement.schoolmanagementwebsite.repository.AdmissionFeePaymentRepository;
// import com.schoolmanagement.schoolmanagementwebsite.repository.AdmissionRepository;
// import com.schoolmanagement.schoolmanagementwebsite.repository.StudentFeeRepository;

// import jakarta.transaction.Transactional;
// import lombok.RequiredArgsConstructor;
// import tools.jackson.databind.ObjectMapper;

// @Service
// @RequiredArgsConstructor
// public class AdmissionFeePaymentService {

//     private final AdmissionRepository admissionRepo;
//     private final AdmissionFeePaymentRepository admissionFeeRepo;
//     private final StudentFeeRepository studentFeeRepo;
//     private final ObjectMapper objectMapper;

//     @Transactional
// public void payAdmissionFee(AdmissionFeePaymentRequest req) throws Exception {

//     // Find admission by admissionNumber + schoolId
//     Admission admission = admissionRepo.findByAdmissionNumberAndSchoolId(
//         req.getAdmissionNumber(), req.getSchoolId()
//     ).orElseThrow(() -> new RuntimeException("Admission not found"));

//     AdmissionFeePayment payment = new AdmissionFeePayment();
//     payment.setAdmission(admission);
//     payment.setSchoolId(req.getSchoolId());
//     payment.setSession(req.getSession());
//     payment.setStandard(req.getStandard());

//     payment.setTuitionFee(objectMapper.writeValueAsString(req.getTuitionFee()));
//     payment.setPaidMonths(objectMapper.writeValueAsString(req.getPaidMonths()));

//     double totalDiscount = 0;
//     Map<String, FeeAmountDTO> fixed = req.getFixedFees();

//     payment.setAnnualCharges(getAmount(fixed, "annualCharges"));
//     payment.setExamCharges(getAmount(fixed, "examCharges"));
//     payment.setSportsFee(getAmount(fixed, "sportsFee"));
//     payment.setPhotoCardFee(getAmount(fixed, "photoCardFee"));
//     payment.setLibraryLabFee(getAmount(fixed, "libraryLabFee"));
//     payment.setTransportFee(getAmount(fixed, "transportFee"));
//     payment.setMiscCharges(getAmount(fixed, "miscCharges"));
//     payment.setRegistrationFee(getAmount(fixed, "registrationFee"));
//     payment.setSecurityMoney(getAmount(fixed, "securityMoney"));

//     totalDiscount += getDiscount(fixed);

//     payment.setTotalAmount(req.getTotalAmount());
//     payment.setTotalDiscount(totalDiscount);
//     payment.setPaymentMode(req.getPaymentMode());
//     payment.setPaymentDate(LocalDateTime.now());

//     admissionFeeRepo.save(payment);

//     // Save StudentFee ledger (fixed fees)
//     fixed.forEach((key, val) -> {
//         if (val != null && val.getAmount() > 0) {
//             StudentFee fee = new StudentFee();
//             fee.setAdmission(admission);
//             fee.setSchoolId(req.getSchoolId());
//             fee.setFeeType("ADMISSION");
//             fee.setFeeName(key);
//             fee.setAmount(val.getAmount());
//             fee.setDiscount(val.getDiscount());
//             fee.setPaidAmount(val.getAmount() - val.getDiscount());
//             fee.setPaymentMode(req.getPaymentMode());
//             fee.setPaymentDate(LocalDateTime.now());
//             studentFeeRepo.save(fee);
//         }
//     });

//     // Tuition fees
//     for (String month : req.getPaidMonths()) {
//         StudentFee fee = new StudentFee();
//         fee.setAdmission(admission);
//         fee.setSchoolId(req.getSchoolId());
//         fee.setFeeType("MONTHLY");
//         fee.setMonth(month);
//         fee.setAmount(req.getTuitionFee().get(month));
//         fee.setPaidAmount(req.getTuitionFee().get(month));
//         fee.setPaymentMode(req.getPaymentMode());
//         fee.setPaymentDate(LocalDateTime.now());
//         studentFeeRepo.save(fee);
//     }
// }

// }

// package com.schoolmanagement.schoolmanagementwebsite.service;

// import org.springframework.stereotype.Service;
// import java.time.LocalDateTime;
// import java.util.*;

// import com.schoolmanagement.schoolmanagementwebsite.dto.AdmissionFeePaymentRequest;
// import com.schoolmanagement.schoolmanagementwebsite.dto.AdmissionFeePaymentRequest.FeeAmount;
// import com.schoolmanagement.schoolmanagementwebsite.entity.Admission;
// import com.schoolmanagement.schoolmanagementwebsite.entity.StudentFee;
// import com.schoolmanagement.schoolmanagementwebsite.entity.AdmissionFeePayment;
// import com.schoolmanagement.schoolmanagementwebsite.repository.AdmissionFeePaymentRepository;
// import com.schoolmanagement.schoolmanagementwebsite.repository.AdmissionRepository;
// import com.schoolmanagement.schoolmanagementwebsite.repository.StudentFeeRepository;

// import jakarta.transaction.Transactional;
// import lombok.RequiredArgsConstructor;
// import tools.jackson.databind.ObjectMapper;

// @Service
// @RequiredArgsConstructor
// public class AdmissionFeePaymentService {

//     private final AdmissionRepository admissionRepo;
//     private final AdmissionFeePaymentRepository admissionFeeRepo;
//     private final StudentFeeRepository studentFeeRepo;
//     private final ObjectMapper objectMapper;

//     @Transactional
//     public void payAdmissionFee(AdmissionFeePaymentRequest req) throws Exception {

//         // ------------------ Fetch Admission by admissionNumber + schoolId ------------------
//         Admission admission = admissionRepo.findByAdmissionNumberAndSchoolId(
//             req.getAdmission(), req.getSchoolId()
//         ).orElseThrow(() -> new RuntimeException("Admission not found"));

//         // ------------------ Check if fee already paid ------------------
//     boolean alreadyPaid = admissionFeeRepo.existsByAdmissionAndSessionAndStandard(
//         admission, req.getSession(), req.getStandard()
//     );

//     if (alreadyPaid) {
//         throw new RuntimeException("Admission fee already paid for this session and class");
//     }

//         // ------------------ Save AdmissionFeePayment ------------------
//         AdmissionFeePayment payment = new AdmissionFeePayment();
//         payment.setAdmission(admission);
//         payment.setSchoolId(req.getSchoolId());
//         payment.setSession(req.getSession());
//         payment.setStandard(req.getStandard());

//         // Serialize tuitionFee & paidMonths to JSON
//         payment.setTuitionFee(objectMapper.writeValueAsString(req.getTuitionFee()));
//         payment.setPaidMonths(objectMapper.writeValueAsString(req.getPaidMonths()));

//         Map<String, FeeAmount> fixed = req.getFixedFees();

//         // Set fixed fees
//         payment.setAnnualCharges(getAmount(fixed, "annualCharges"));
//         payment.setExamCharges(getAmount(fixed, "examCharges"));
//         payment.setSportsFee(getAmount(fixed, "sportsFee"));
//         payment.setPhotoCardFee(getAmount(fixed, "photoCardFee"));
//         payment.setLibraryLabFee(getAmount(fixed, "libraryLabFee"));
//         payment.setTransportFee(getAmount(fixed, "transportFee"));
//         payment.setMiscCharges(getAmount(fixed, "miscCharges"));
//         payment.setRegistrationFee(getAmount(fixed, "registrationFee"));
//         payment.setSecurityMoney(getAmount(fixed, "securityMoney"));

//         // Calculate total discount
//         double totalDiscount = fixed.values().stream()
//                 .filter(Objects::nonNull)
//                 .mapToDouble(FeeAmount::getDiscount)
//                 .sum();

//         payment.setTotalDiscount(totalDiscount);
//         payment.setTotalAmount(req.getTotalAmount());
//         payment.setPaymentMode(req.getPaymentMode());
//         payment.setPaymentDate(LocalDateTime.now());

//         admissionFeeRepo.save(payment);

//         // ------------------ Save Fixed Fees into StudentFee Ledger ------------------
//         fixed.forEach((key, val) -> {
//             if (val != null && val.getAmount() > 0) {
//                 StudentFee fee = new StudentFee();
//                 fee.setAdmission(admission);
//                 fee.setSchoolId(req.getSchoolId());
//                 fee.setFeeType("ADMISSION");
//                 // fee.setFeeName(key);
//                 fee.setAmount(val.getAmount());
//                 fee.setDiscount(val.getDiscount());
//                 fee.setPaidAmount(val.getAmount() - val.getDiscount());
//                 fee.setPaymentMode(req.getPaymentMode());
//                 fee.setPaymentDate(LocalDateTime.now());
//                 studentFeeRepo.save(fee);
//             }
//         });

//         // ------------------ Save Tuition Fees into StudentFee Ledger ------------------
//         for (String month : req.getPaidMonths()) {
//             Double amount = req.getTuitionFee().get(month);
//             if (amount != null && amount > 0) {
//                 StudentFee fee = new StudentFee();
//                 fee.setAdmission(admission);
//                 fee.setSchoolId(req.getSchoolId());
//                 fee.setFeeType("MONTHLY");
//                 fee.setMonth(month);
//                 fee.setAmount(amount);
//                 fee.setPaidAmount(amount);
//                 fee.setPaymentMode(req.getPaymentMode());
//                 fee.setPaymentDate(LocalDateTime.now());
//                 studentFeeRepo.save(fee);
//             }
//         }

        
//     }

//     // ------------------ Helper Methods ------------------
//     private Double getAmount(Map<String, FeeAmount> fixed, String key) {
//         FeeAmount f = fixed.get(key);
//         return f != null ? f.getAmount() : 0.0;
//     }
// }

package com.schoolmanagement.schoolmanagementwebsite.service;

import org.springframework.stereotype.Service;

import java.time.LocalDateTime;
import java.util.*;


import com.schoolmanagement.schoolmanagementwebsite.dto.AdmissionFeePaymentRequest;
import com.schoolmanagement.schoolmanagementwebsite.dto.AdmissionFeePaymentRequest.FeeAmount;
import com.schoolmanagement.schoolmanagementwebsite.entity.Admission;

import com.schoolmanagement.schoolmanagementwebsite.entity.AdmissionFeePayment;
import com.schoolmanagement.schoolmanagementwebsite.entity.StudentAdmissionFee;
import com.schoolmanagement.schoolmanagementwebsite.enums.AdmissionStatus;
import com.schoolmanagement.schoolmanagementwebsite.repository.AdmissionFeePaymentRepository;
import com.schoolmanagement.schoolmanagementwebsite.repository.AdmissionRepository;
import com.schoolmanagement.schoolmanagementwebsite.repository.StudentAdmissionFeeRepository;


import jakarta.transaction.Transactional;
import lombok.RequiredArgsConstructor;
import tools.jackson.databind.ObjectMapper;


@Service
@RequiredArgsConstructor
public class AdmissionFeePaymentService {

    private final AdmissionRepository admissionRepo;
    private final AdmissionFeePaymentRepository admissionFeeRepo;
    private final StudentAdmissionFeeRepository studentFeeRepo;
    private final ObjectMapper objectMapper;

    @Transactional
    public void payAdmissionFee(AdmissionFeePaymentRequest req) throws Exception {

        // 1️⃣ Fetch Admission
        Admission admission = admissionRepo
                .findByAdmissionNumberAndSchoolId(req.getAdmission(), req.getSchoolId())
                .orElseThrow(() -> new RuntimeException("Admission not found"));

        // 2️⃣ Prevent duplicate payment
        boolean alreadyPaid = admissionFeeRepo
                .existsByAdmissionAndSessionAndStandard(
                        admission, req.getSession(), req.getStandard());

        if (alreadyPaid) {
            throw new RuntimeException("Admission fee already paid");
        }

        // 3️⃣ Save AdmissionFeePayment
        AdmissionFeePayment payment = new AdmissionFeePayment();
        payment.setAdmission(admission);
        payment.setSchoolId(req.getSchoolId());
        payment.setSession(req.getSession());
        payment.setStandard(req.getStandard());

        payment.setTuitionFee(objectMapper.writeValueAsString(req.getTuitionFee()));
        payment.setPaidMonths(objectMapper.writeValueAsString(req.getPaidMonths()));

        Map<String, FeeAmount> fixed = req.getFixedFees();

        payment.setAnnualCharges(getAmount(fixed, "annualCharges"));
        payment.setExamCharges(getAmount(fixed, "examCharges"));
        payment.setSportsFee(getAmount(fixed, "sportsFee"));
        payment.setPhotoCardFee(getAmount(fixed, "photoCardFee"));
        payment.setLibraryLabFee(getAmount(fixed, "libraryLabFee"));
        payment.setTransportFee(getAmount(fixed, "transportFee"));
        payment.setMiscCharges(getAmount(fixed, "miscCharges"));
        payment.setRegistrationFee(getAmount(fixed, "registrationFee"));
        payment.setSecurityMoney(getAmount(fixed, "securityMoney"));

        double totalDiscount = fixed.values().stream()
                .filter(Objects::nonNull)
                .mapToDouble(FeeAmount::getDiscount)
                .sum();

        payment.setTotalDiscount(totalDiscount);
        payment.setTotalAmount(req.getTotalAmount());
        payment.setPaymentMode(req.getPaymentMode());
        payment.setPaymentDate(LocalDateTime.now());

        admissionFeeRepo.save(payment);

        // 4️⃣ Save FIXED fees to StudentFee ledger
        fixed.forEach((key, val) -> {
            if (val != null && val.getAmount() > 0) {
                StudentAdmissionFee fee = new StudentAdmissionFee();
                fee.setAdmission(admission);
                fee.setSchoolId(req.getSchoolId());
                fee.setFeeType("ADMISSION");
                fee.setAmount(val.getAmount());
                fee.setDiscount(val.getDiscount());
                fee.setPaidAmount(val.getAmount() - val.getDiscount());
                fee.setPaymentMode(req.getPaymentMode());
                fee.setPaymentDate(LocalDateTime.now());
                studentFeeRepo.save(fee);
            }
        });

        // 5️⃣ Save MONTHLY tuition fees
        for (String month : req.getPaidMonths()) {
            Double amount = req.getTuitionFee().get(month);
            if (amount != null && amount > 0) {
                StudentAdmissionFee fee = new StudentAdmissionFee();
                fee.setAdmission(admission);
                fee.setSchoolId(req.getSchoolId());
                fee.setFeeType("MONTHLY");
                fee.setMonth(month);
                fee.setAmount(amount);
                fee.setPaidAmount(amount);
                fee.setPaymentMode(req.getPaymentMode());
                fee.setPaymentDate(LocalDateTime.now());
                studentFeeRepo.save(fee);
            }
        }

        // ✅ 6️⃣ UPDATE STATUS ONLY (NO STUDENT CREATION)
        admission.setStatus(AdmissionStatus.FEE_PAID);
        admissionRepo.save(admission);
    }

    // Helper
    private Double getAmount(Map<String, FeeAmount> fixed, String key) {
        FeeAmount f = fixed.get(key);
        return f != null ? f.getAmount() : 0.0;
    }
}
