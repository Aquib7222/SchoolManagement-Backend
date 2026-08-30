// package com.schoolmanagement.schoolmanagementwebsite.controller;

// import java.util.List;

// import org.springframework.http.ResponseEntity;
// import org.springframework.web.bind.annotation.*;

// import com.schoolmanagement.schoolmanagementwebsite.dto.PromotionRequest;
// import com.schoolmanagement.schoolmanagementwebsite.dto.StudentEnrollmentResponse;
// import com.schoolmanagement.schoolmanagementwebsite.service.StudentEnrollmentService;

// import lombok.RequiredArgsConstructor;

// @RestController
// @RequestMapping("/api/student-enrollment")
// @RequiredArgsConstructor
// @CrossOrigin(origins = "http://localhost:5173")
// public class StudentEnrollmentController {

//     private final StudentEnrollmentService enrollmentService;


//     // =========================================================
//     // PROMOTE STUDENTS
//     // =========================================================

//     @PostMapping("/promote")
//     public ResponseEntity<?> promoteStudents(
//             @RequestBody PromotionRequest request
//     ) {

//         try {

//             String response =
//                     enrollmentService.promoteStudents(request);

//             return ResponseEntity.ok(response);

//         } catch (RuntimeException e) {

//             return ResponseEntity
//                     .badRequest()
//                     .body(e.getMessage());

//         } catch (Exception e) {

//             e.printStackTrace();

//             return ResponseEntity
//                     .internalServerError()
//                     .body(
//                             "Failed to promote students"
//                     );
//         }
//     }


//     // =========================================================
//     // GET STUDENTS
//     // =========================================================

//     @GetMapping("/students")
// public ResponseEntity<?> getStudents(

//         @RequestParam Long schoolId,

//         @RequestParam(required = false) String academicYear,

//         @RequestParam(required = false) String studentClass,

//         @RequestParam(required = false)
//         String section,

//         @RequestParam(required = false)
//         String search

// ) {

//     try {

//         List<StudentEnrollmentResponse> students =
//                 enrollmentService.getStudents(
//                         schoolId,
//                         academicYear,
//                         studentClass,
//                         section,
//                         search
//                 );

//         return ResponseEntity.ok(students);

//     } catch (RuntimeException e) {

//         return ResponseEntity
//                 .badRequest()
//                 .body(e.getMessage());

//     } catch (Exception e) {

//         e.printStackTrace();

//         return ResponseEntity
//                 .internalServerError()
//                 .body("Failed to load students");
//     }
// }


//     // =========================================================
//     // STUDENT ACADEMIC HISTORY
//     // =========================================================

//     @GetMapping("/history")
//     public ResponseEntity<?> getStudentHistory(

//             @RequestParam Long schoolId,

//             @RequestParam String admissionNumber

//     ) {

//         try {

//             return ResponseEntity.ok(
//                     enrollmentService.getStudentHistory(
//                             schoolId,
//                             admissionNumber
//                     )
//             );

//         } catch (RuntimeException e) {

//             return ResponseEntity
//                     .badRequest()
//                     .body(e.getMessage());

//         } catch (Exception e) {

//             e.printStackTrace();

//             return ResponseEntity
//                     .internalServerError()
//                     .body(
//                             "Failed to load student history"
//                     );
//         }
//     }
// }