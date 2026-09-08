
// package com.schoolmanagement.schoolmanagementwebsite.controller;

// import com.schoolmanagement.schoolmanagementwebsite.service.PasswordResetService;

// import org.springframework.beans.factory.annotation.Autowired;
// import org.springframework.http.HttpStatus;
// import org.springframework.http.ResponseEntity;
// import org.springframework.web.bind.annotation.*;

// import java.util.HashMap;
// import java.util.Map;

// @RestController
// @RequestMapping("/api/password-reset")
// @CrossOrigin(origins = "http://localhost:5173")
// public class PasswordResetController {

//     @Autowired
//     private PasswordResetService passwordResetService;


//     // =====================================================
//     // SEND OTP
//     // =====================================================

//     @PostMapping("/send-otp")
//     public ResponseEntity<Map<String, Object>> sendOtp(
//             @RequestParam String email
//     ) {

//         Map<String, Object> response =
//                 new HashMap<>();

//         try {

//             passwordResetService.sendResetOtp(
//                     email
//             );

//             response.put(
//                     "success",
//                     true
//             );

//             response.put(
//                     "message",
//                     "Password reset OTP sent successfully"
//             );

//             return ResponseEntity.ok(
//                     response
//             );

//         } catch (RuntimeException e) {

//             response.put(
//                     "success",
//                     false
//             );

//             response.put(
//                     "message",
//                     e.getMessage()
//             );

//             return ResponseEntity
//                     .status(HttpStatus.BAD_REQUEST)
//                     .body(response);

//         } catch (Exception e) {

//             response.put(
//                     "success",
//                     false
//             );

//             response.put(
//                     "message",
//                     "Failed to send password reset OTP"
//             );

//             return ResponseEntity
//                     .status(HttpStatus.INTERNAL_SERVER_ERROR)
//                     .body(response);
//         }
//     }


//     // =====================================================
//     // VERIFY OTP
//     // =====================================================

//     @PostMapping("/verify-otp")
//     public ResponseEntity<Map<String, Object>> verifyOtp(
//             @RequestParam String email,
//             @RequestParam String otp
//     ) {

//         Map<String, Object> response =
//                 new HashMap<>();

//         try {

//             boolean verified =
//                     passwordResetService.verifyResetOtp(
//                             email,
//                             otp
//                     );

//             if (verified) {

//                 response.put(
//                         "success",
//                         true
//                 );

//                 response.put(
//                         "message",
//                         "OTP verified successfully"
//                 );

//                 return ResponseEntity.ok(
//                         response
//                 );
//             }

//             response.put(
//                     "success",
//                     false
//             );

//             response.put(
//                     "message",
//                     "Invalid OTP"
//             );

//             return ResponseEntity
//                     .status(HttpStatus.BAD_REQUEST)
//                     .body(response);

//         } catch (RuntimeException e) {

//             response.put(
//                     "success",
//                     false
//             );

//             response.put(
//                     "message",
//                     e.getMessage()
//             );

//             return ResponseEntity
//                     .status(HttpStatus.BAD_REQUEST)
//                     .body(response);

//         } catch (Exception e) {

//             response.put(
//                     "success",
//                     false
//             );

//             response.put(
//                     "message",
//                     "Failed to verify OTP"
//             );

//             return ResponseEntity
//                     .status(HttpStatus.INTERNAL_SERVER_ERROR)
//                     .body(response);
//         }
//     }


//     // =====================================================
//     // RESET PASSWORD
//     // =====================================================

//     @PostMapping("/reset")
//     public ResponseEntity<Map<String, Object>> resetPassword(
//             @RequestParam String email,
//             @RequestParam String otp,
//             @RequestParam String newPassword,
//             @RequestParam String confirmPassword
//     ) {

//         Map<String, Object> response =
//                 new HashMap<>();

//         try {

//             passwordResetService.resetPassword(
//                     email,
//                     otp,
//                     newPassword,
//                     confirmPassword
//             );

//             response.put(
//                     "success",
//                     true
//             );

//             response.put(
//                     "message",
//                     "Password reset successfully"
//             );

//             return ResponseEntity.ok(
//                     response
//             );

//         } catch (RuntimeException e) {

//             response.put(
//                     "success",
//                     false
//             );

//             response.put(
//                     "message",
//                     e.getMessage()
//             );

//             return ResponseEntity
//                     .status(HttpStatus.BAD_REQUEST)
//                     .body(response);

//         } catch (Exception e) {

//             response.put(
//                     "success",
//                     false
//             );

//             response.put(
//                     "message",
//                     "Failed to reset password"
//             );

//             return ResponseEntity
//                     .status(HttpStatus.INTERNAL_SERVER_ERROR)
//                     .body(response);
//         }
//     }
// }



package com.schoolmanagement.schoolmanagementwebsite.controller;

import com.schoolmanagement.schoolmanagementwebsite.dto.PasswordResetRequest;
import com.schoolmanagement.schoolmanagementwebsite.service.PasswordResetService;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.Map;

@RestController
@RequestMapping("/api/password-reset")
public class PasswordResetController {

    private final PasswordResetService passwordResetService;

    public PasswordResetController(
            PasswordResetService passwordResetService) {

        this.passwordResetService = passwordResetService;
    }

    // =========================================================
    // SEND OTP
    // =========================================================

    @PostMapping("/send-otp")
    public ResponseEntity<?> sendOtp(
            @RequestParam String email) {

        try {

            passwordResetService.sendOtp(email);

            return ResponseEntity.ok(
                    Map.of(
                            "success", true,
                            "message",
                            "OTP sent successfully to registered email."
                    )
            );

        } catch (Exception e) {

            return ResponseEntity.badRequest().body(
                    Map.of(
                            "success", false,
                            "message", e.getMessage()
                    )
            );
        }
    }

    // =========================================================
    // VERIFY OTP
    // =========================================================

    @PostMapping("/verify-otp")
    public ResponseEntity<?> verifyOtp(
            @RequestParam String email,
            @RequestParam String otp) {

        try {

            boolean verified =
                    passwordResetService.verifyOtp(
                            email,
                            otp
                    );

            if (!verified) {

                return ResponseEntity.badRequest().body(
                        Map.of(
                                "success", false,
                                "message",
                                "Invalid or expired OTP."
                        )
                );
            }

            return ResponseEntity.ok(
                    Map.of(
                            "success", true,
                            "message",
                            "OTP verified successfully."
                    )
            );

        } catch (Exception e) {

            return ResponseEntity.badRequest().body(
                    Map.of(
                            "success", false,
                            "message", e.getMessage()
                    )
            );
        }
    }

    // =========================================================
    // CHANGE PASSWORD
    // =========================================================

    @PostMapping("/change-password")
    public ResponseEntity<?> changePassword(
            @RequestBody PasswordResetRequest request) {

        try {

            passwordResetService.changePassword(request);

            return ResponseEntity.ok(
                    Map.of(
                            "success", true,
                            "message",
                            "Password changed successfully."
                    )
            );

        } catch (Exception e) {

            return ResponseEntity.badRequest().body(
                    Map.of(
                            "success", false,
                            "message", e.getMessage()
                    )
            );
        }
    }
}