package com.schoolmanagement.schoolmanagementwebsite.controller;

import java.security.Principal;
import java.util.List;

import org.springframework.http.ResponseEntity;
import org.springframework.security.core.Authentication;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import com.schoolmanagement.schoolmanagementwebsite.dto.AdmissionRequest;
import com.schoolmanagement.schoolmanagementwebsite.dto.StatusUpdateRequest;
import com.schoolmanagement.schoolmanagementwebsite.entity.Admission;
import com.schoolmanagement.schoolmanagementwebsite.entity.AdmissionStatusHistory;
import com.schoolmanagement.schoolmanagementwebsite.repository.AdmissionRepository;
import com.schoolmanagement.schoolmanagementwebsite.repository.AdmissionStatusHistoryRepository;
import com.schoolmanagement.schoolmanagementwebsite.service.AdmissionService;

import lombok.RequiredArgsConstructor;

// @RestController
// @RequestMapping("/api/admissions")
// @RequiredArgsConstructor
// @CrossOrigin(origins = "http://localhost:5173")
// public class AdmissionController {

//     private final AdmissionService admissionService;

//     private final AdmissionStatusHistoryRepository statusHistoryRepository;

//     private final AdmissionRepository admissionRepository;

//     // @PostMapping
//     // public ResponseEntity<Admission> createAdmission(
//     //         @RequestBody AdmissionRequest request) {

//     //     Admission admission = admissionService.createAdmission(request);
//     //     return ResponseEntity.ok(admission);
//     // }
//    @PostMapping
// public ResponseEntity<Admission> createAdmission(
//         @RequestBody AdmissionRequest request,
//         Principal principal) {

//     if (principal == null) {
//         throw new RuntimeException("User not authenticated");
//     }

//     String email = principal.getName(); // ✅ logged-in user email
//     return ResponseEntity.ok(admissionService.createAdmission(request, email));
// }


//     @GetMapping
//     public ResponseEntity<?> getAllAdmissions() {
//         return ResponseEntity.ok("API Ready");
//     }

//     // @GetMapping("/applied")
//     // public ResponseEntity<List<Admission>> getAppliedStudents() {
//     //     return ResponseEntity.ok(admissionService.getAllAdmissions());
//     // }
//    // Get admissions for a specific school
//     @GetMapping("/school")
//     public List<Admission> getAdmissionsBySchool(@RequestParam Long schoolId) {
//         return admissionRepository.findBySchool_Id(schoolId);
//     }

//     // ✅ UPDATE
//     @PutMapping("/{id}")
//     public ResponseEntity<Admission> updateAdmission(
//             @PathVariable Long id,
//             @RequestBody Admission admission) {

//         return ResponseEntity.ok(admissionService.updateAdmission(id, admission));
//     }

//     // ✅ DELETE
//     @DeleteMapping("/{id}")
//     public ResponseEntity<String> deleteAdmission(@PathVariable Long id) {
//         admissionService.deleteAdmission(id);
//         return ResponseEntity.ok("Admission deleted successfully");
//     }

//     @PutMapping("/{id}/status")
// public ResponseEntity<?> updateStatus(
//         @PathVariable Long id,
//         @RequestParam String status,
//         Authentication authentication) {

//     admissionService.updateAdmissionStatus(
//             id,
//             status,
//             authentication.getName()
//     );

//     return ResponseEntity.ok("Status updated");
// }
// @GetMapping("/{id}/status-history")
// public List<AdmissionStatusHistory>
// getStatusHistory(@PathVariable Long id) {

//     return statusHistoryRepository
//             .findByAdmission_IdOrderByChangedAtDesc(id);
// }


// }

@RestController
@RequestMapping("/api/admissions")
@RequiredArgsConstructor
@CrossOrigin(origins = "http://localhost:5173")
public class AdmissionController {

    private final AdmissionService admissionService;
    private final AdmissionStatusHistoryRepository statusHistoryRepository;
    private final AdmissionRepository admissionRepository;

    // ---------------- CREATE ADMISSION ----------------
    @PostMapping
    public ResponseEntity<Admission> createAdmission(
            @RequestBody AdmissionRequest request,
            Principal principal) {

        if (principal == null) {
            throw new RuntimeException("User not authenticated");
        }

        String email = principal.getName(); // logged-in user email
        Admission admission = admissionService.createAdmission(request, email);
        return ResponseEntity.ok(admission);
    }

    // ---------------- GET ALL ADMISSIONS ----------------
    @GetMapping
    public ResponseEntity<List<Admission>> getAllAdmissions() {
        List<Admission> admissions = admissionService.getAllAdmissions();
        return ResponseEntity.ok(admissions);
    }

    // ---------------- GET ADMISSIONS BY SCHOOL ----------------
    @GetMapping("/school")
    public ResponseEntity<List<Admission>> getAdmissionsBySchool(@RequestParam Long schoolId) {
        List<Admission> admissions = admissionRepository.findBySchool_Id(schoolId);
        return ResponseEntity.ok(admissions);
    }

    // ---------------- UPDATE ADMISSION ----------------
    @PutMapping("/{id}")
    public ResponseEntity<Admission> updateAdmission(
            @PathVariable Long id,
            @RequestBody Admission admission) {

        Admission updated = admissionService.updateAdmission(id, admission);
        return ResponseEntity.ok(updated);
    }
    @GetMapping("/{id}")
public ResponseEntity<Admission> getAdmissionById(@PathVariable Long id) {

    Admission admission = admissionService.getAdmissionById(id);
    return ResponseEntity.ok(admission);
}


    // ---------------- DELETE ADMISSION ----------------
    // @DeleteMapping("/{id}")
    // public ResponseEntity<String> deleteAdmission(@PathVariable Long id) {
    //     admissionService.deleteAdmission(id);
    //     return ResponseEntity.ok("Admission deleted successfully");
    // }

    // ---------------- UPDATE STATUS ----------------
    // @PutMapping("/{id}/status")
    // public ResponseEntity<String> updateStatus(
    //         @PathVariable Long id,
    //         @RequestParam String status, // received as String
    //         Authentication authentication) {

    //     // Convert String to Enum safely
    //     AdmissionStatus admissionStatus;
    //     try {
    //         admissionStatus = AdmissionStatus.valueOf(status.toUpperCase());
    //     } catch (IllegalArgumentException e) {
    //         throw new RuntimeException("Invalid status value. Allowed: APPLIED, APPROVED, REJECTED");
    //     }

    //     admissionService.updateAdmissionStatus(id, admissionStatus);
    //     return ResponseEntity.ok("Status updated successfully");
    // }

    @PutMapping("/{id}/status")
public ResponseEntity<String> updateStatus(
        @PathVariable Long id,
        @RequestBody StatusUpdateRequest request, // receive enum directly
        Authentication authentication) {

    admissionService.updateAdmissionStatus(id, request.status());
    return ResponseEntity.ok("Status updated successfully");
}


    // ---------------- GET STATUS HISTORY ----------------
    @GetMapping("/{id}/status-history")
    public ResponseEntity<List<AdmissionStatusHistory>> getStatusHistory(@PathVariable Long id) {
        List<AdmissionStatusHistory> history =
                statusHistoryRepository.findByAdmission_IdOrderByChangedAtDesc(id);
        return ResponseEntity.ok(history);
    }
}
