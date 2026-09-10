// // package com.schoolmanagement.schoolmanagementwebsite.controller;

// // import com.schoolmanagement.schoolmanagementwebsite.service.EmailOtpService;
// // import org.springframework.beans.factory.annotation.Autowired;
// // import org.springframework.http.ResponseEntity;
// // import org.springframework.web.bind.annotation.*;

// // import java.util.HashMap;
// // import java.util.Map;

// // @RestController
// // @RequestMapping("/api/email-otp")
// // @CrossOrigin(origins = "http://localhost:5173")
// // public class EmailOtpController {

// //     @Autowired
// //     private EmailOtpService emailOtpService;

// //     @PostMapping("/send")
// //     public ResponseEntity<?> sendOtp(
// //             @RequestParam String email
// //     ) {

// //         try {

// //             emailOtpService.sendOtp(email);

// //             Map<String, Object> response =
// //                     new HashMap<>();

// //             response.put(
// //                     "success",
// //                     true
// //             );

// //             response.put(
// //                     "message",
// //                     "OTP sent successfully"
// //             );

// //             return ResponseEntity.ok(response);

// //         } catch (Exception e) {

// //             Map<String, Object> response =
// //                     new HashMap<>();

// //             response.put(
// //                     "success",
// //                     false
// //             );

// //             response.put(
// //                     "message",
// //                     e.getMessage()
// //             );

// //             return ResponseEntity
// //                     .badRequest()
// //                     .body(response);
// //         }
// //     }
// // }




// package com.schoolmanagement.schoolmanagementwebsite.controller;

// import com.schoolmanagement.schoolmanagementwebsite.service.EmailOtpService;

// import org.springframework.beans.factory.annotation.Autowired;
// import org.springframework.http.ResponseEntity;
// import org.springframework.web.bind.annotation.*;

// import java.util.HashMap;
// import java.util.Map;

// @RestController
// @RequestMapping("/api/email-otp")
// @CrossOrigin
// public class EmailOtpController {

//     @Autowired
//     private EmailOtpService emailOtpService;

//     // =====================================================
//     // SEND OTP
//     // =====================================================

//     @PostMapping("/send")
//     public ResponseEntity<?> sendOtp(
//             @RequestParam String email
//     ) {

//         try {

//             emailOtpService.sendOtp(email);

//             Map<String, Object> response =
//                     new HashMap<>();

//             response.put(
//                     "success",
//                     true
//             );

//             response.put(
//                     "message",
//                     "OTP sent successfully"
//             );

//             return ResponseEntity.ok(response);

//         } catch (Exception e) {

//             Map<String, Object> response =
//                     new HashMap<>();

//             response.put(
//                     "success",
//                     false
//             );

//             response.put(
//                     "message",
//                     e.getMessage()
//             );

//             return ResponseEntity
//                     .badRequest()
//                     .body(response);
//         }
//     }

//     // =====================================================
//     // VERIFY OTP
//     // =====================================================

//     @PostMapping("/verify")
//     public ResponseEntity<?> verifyOtp(
//             @RequestParam String email,
//             @RequestParam String otp
//     ) {

//         try {

//             boolean verified =
//                     emailOtpService.verifyOtp(
//                             email,
//                             otp
//                     );

//             Map<String, Object> response =
//                     new HashMap<>();

//             response.put(
//                     "success",
//                     verified
//             );

//             response.put(
//                     "message",
//                     "Email verified successfully"
//             );

//             return ResponseEntity.ok(response);

//         } catch (Exception e) {

//             Map<String, Object> response =
//                     new HashMap<>();

//             response.put(
//                     "success",
//                     false
//             );

//             response.put(
//                     "message",
//                     e.getMessage()
//             );

//             return ResponseEntity
//                     .badRequest()
//                     .body(response);
//         }
//     }
// }


package com.schoolmanagement.schoolmanagementwebsite.controller;

import com.schoolmanagement.schoolmanagementwebsite.service.EmailOtpService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.HashMap;
import java.util.Map;

@RestController
@RequestMapping("/api/email-otp")
@CrossOrigin(origins = "http://localhost:5173")
public class EmailOtpController {

    @Autowired
    private EmailOtpService emailOtpService;

    // =====================================================
       @PostMapping("/send")
    public ResponseEntity<?> sendOtp(
            @RequestParam String email
    ) {

        try {

            emailOtpService.sendOtp(email);

            Map<String, Object> response =
                    new HashMap<>();

            response.put(
                    "success",
                    true
            );

            response.put(
                    "message",
                    "OTP sent successfully"
            );

            return ResponseEntity.ok(response);

       } catch (Exception e) {

    // IMPORTANT: backend terminal mein actual error dikhega
    e.printStackTrace();

    Map<String, Object> response =
            new HashMap<>();

    response.put(
            "success",
            false
    );

    response.put(
            "message",
            e.getMessage()
    );

    return ResponseEntity
            .badRequest()
            .body(response);
}
    }

    // =====================================================
    // VERIFY EMAIL OTP
    // =====================================================

    @PostMapping("/verify")
    public ResponseEntity<Map<String, Object>> verifyOtp(
            @RequestParam String email,
            @RequestParam String otp
    ) {

        Map<String, Object> response =
                new HashMap<>();

        try {

            boolean verified =
                    emailOtpService.verifyOtp(
                            email,
                            otp
                    );

            if (verified) {

                response.put(
                        "success",
                        true
                );

                response.put(
                        "message",
                        "Email verified successfully"
                );

                return ResponseEntity.ok(response);
            }

            response.put(
                    "success",
                    false
            );

            response.put(
                    "message",
                    "Invalid OTP"
            );

            return ResponseEntity
                    .status(HttpStatus.BAD_REQUEST)
                    .body(response);

        } catch (RuntimeException e) {

            response.put(
                    "success",
                    false
            );

            response.put(
                    "message",
                    e.getMessage()
            );

            return ResponseEntity
                    .status(HttpStatus.BAD_REQUEST)
                    .body(response);

        } catch (Exception e) {

            response.put(
                    "success",
                    false
            );

            response.put(
                    "message",
                    "Failed to verify OTP"
            );

            return ResponseEntity
                    .status(HttpStatus.INTERNAL_SERVER_ERROR)
                    .body(response);
        }
    }
}

