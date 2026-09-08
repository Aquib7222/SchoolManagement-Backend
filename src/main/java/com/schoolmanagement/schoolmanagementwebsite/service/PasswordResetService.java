
// package com.schoolmanagement.schoolmanagementwebsite.service;

// import com.schoolmanagement.schoolmanagementwebsite.entity.User;
// import com.schoolmanagement.schoolmanagementwebsite.repository.UserRepository;

// import org.springframework.beans.factory.annotation.Autowired;
// import org.springframework.security.crypto.password.PasswordEncoder;
// import org.springframework.stereotype.Service;

// import java.util.regex.Pattern;

// @Service
// public class PasswordResetService {

//     @Autowired
//     private UserRepository userRepository;

//     @Autowired
//     private EmailOtpService emailOtpService;

//     @Autowired
//     private PasswordEncoder passwordEncoder;

//     private static final Pattern EMAIL_PATTERN =
//             Pattern.compile(
//                     "^[A-Za-z0-9+_.-]+@[A-Za-z0-9.-]+$"
//             );

//     // =====================================================
//     // SEND PASSWORD RESET OTP
//     // =====================================================

//     public void sendResetOtp(String email) {

//         // -----------------------------------------
//         // Validate email
//         // -----------------------------------------

//         if (email == null || email.trim().isEmpty()) {
//             throw new RuntimeException(
//                     "Email is required"
//             );
//         }

//         email = email.trim().toLowerCase();

//         if (!EMAIL_PATTERN.matcher(email).matches()) {
//             throw new RuntimeException(
//                     "Please enter a valid email address"
//             );
//         }

//         // -----------------------------------------
//         // Check user exists
//         // -----------------------------------------

//         User user = userRepository
//                 .findOptionalByEmail(email)
//                 .orElseThrow(() ->
//                         new RuntimeException(
//                                 "No account found with this email address"
//                         )
//                 );

//         // -----------------------------------------
//         // Check account status
//         // -----------------------------------------

//         if (user.getStatus() != null &&
//                 user.getStatus().equalsIgnoreCase("Inactive")) {

//             throw new RuntimeException(
//                     "This account is inactive"
//             );
//         }

//         // -----------------------------------------
//         // Send OTP
//         // -----------------------------------------

//         emailOtpService.sendOtp(email);
//     }


//     // =====================================================
//     // VERIFY PASSWORD RESET OTP
//     // =====================================================

//     public boolean verifyResetOtp(
//             String email,
//             String otp
//     ) {

//         // -----------------------------------------
//         // Validate email
//         // -----------------------------------------

//         if (email == null || email.trim().isEmpty()) {
//             throw new RuntimeException(
//                     "Email is required"
//             );
//         }

//         // -----------------------------------------
//         // Validate OTP
//         // -----------------------------------------

//         if (otp == null || otp.trim().isEmpty()) {
//             throw new RuntimeException(
//                     "OTP is required"
//             );
//         }

//         email = email.trim().toLowerCase();
//         otp = otp.trim();

//         if (otp.length() != 6) {
//             throw new RuntimeException(
//                     "OTP must be 6 digits"
//             );
//         }

//         // -----------------------------------------
//         // Check user
//         // -----------------------------------------

//         if (!userRepository.existsByEmail(email)) {
//             throw new RuntimeException(
//                     "No account found with this email address"
//             );
//         }

//         // -----------------------------------------
//         // Verify existing Email OTP
//         // -----------------------------------------

//         return emailOtpService.verifyOtp(
//                 email,
//                 otp
//         );
//     }


//     // =====================================================
//     // RESET PASSWORD
//     // =====================================================

//     public void resetPassword(
//             String email,
//             String otp,
//             String newPassword,
//             String confirmPassword
//     ) {

//         // -----------------------------------------
//         // Validate email
//         // -----------------------------------------

//         if (email == null || email.trim().isEmpty()) {
//             throw new RuntimeException(
//                     "Email is required"
//             );
//         }

//         email = email.trim().toLowerCase();

//         // -----------------------------------------
//         // Validate OTP
//         // -----------------------------------------

//         if (otp == null || otp.trim().isEmpty()) {
//             throw new RuntimeException(
//                     "OTP is required"
//             );
//         }

//         otp = otp.trim();

//         // -----------------------------------------
//         // Validate password
//         // -----------------------------------------

//         if (newPassword == null ||
//                 newPassword.trim().isEmpty()) {

//             throw new RuntimeException(
//                     "New password is required"
//             );
//         }

//         if (confirmPassword == null ||
//                 confirmPassword.trim().isEmpty()) {

//             throw new RuntimeException(
//                     "Confirm password is required"
//             );
//         }

//         // -----------------------------------------
//         // Password length
//         // -----------------------------------------

//         if (newPassword.length() < 6) {
//             throw new RuntimeException(
//                     "Password must be at least 6 characters"
//             );
//         }

//         // -----------------------------------------
//         // Password match
//         // -----------------------------------------

//         if (!newPassword.equals(confirmPassword)) {
//             throw new RuntimeException(
//                     "New password and confirm password do not match"
//             );
//         }

//         // -----------------------------------------
//         // Find user
//         // -----------------------------------------

//         User user = userRepository
//                 .findOptionalByEmail(email)
//                 .orElseThrow(() ->
//                         new RuntimeException(
//                                 "No account found with this email address"
//                         )
//                 );

//         // -----------------------------------------
//         // Verify OTP again
//         //
//         // Important:
//         // Password reset tabhi hoga jab OTP valid ho.
//         // -----------------------------------------

//         emailOtpService.verifyOtp(
//                 email,
//                 otp
//         );

//         // -----------------------------------------
//         // Encrypt password using BCrypt
//         // -----------------------------------------

//         String encodedPassword =
//                 passwordEncoder.encode(newPassword);

//         // -----------------------------------------
//         // Update password
//         // -----------------------------------------

//         user.setPassword(encodedPassword);

//         // -----------------------------------------
//         // Save user
//         // -----------------------------------------

//         userRepository.save(user);
//     }
// }

package com.schoolmanagement.schoolmanagementwebsite.service;

import com.schoolmanagement.schoolmanagementwebsite.dto.PasswordResetRequest;
import com.schoolmanagement.schoolmanagementwebsite.entity.User;
import com.schoolmanagement.schoolmanagementwebsite.repository.UserRepository;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;

import java.time.LocalDateTime;
import java.util.Random;

@Service
public class PasswordResetService {

    private final UserRepository userRepository;
    private final EmailOtpService emailOtpService;
    private final PasswordEncoder passwordEncoder;

    public PasswordResetService(
            UserRepository userRepository,
            EmailOtpService emailOtpService,
            PasswordEncoder passwordEncoder) {

        this.userRepository = userRepository;
        this.emailOtpService = emailOtpService;
        this.passwordEncoder = passwordEncoder;
    }

    // =========================================================
    // SEND OTP
    // =========================================================

    public void sendOtp(String email) {

        User user = userRepository.findByEmail(email);

        if (user == null) {
            throw new RuntimeException(
                    "No user found with this email address."
            );
        }

        emailOtpService.sendOtp(email);
    }

    // =========================================================
    // VERIFY OTP
    // =========================================================

    public boolean verifyOtp(String email, String otp) {

        return emailOtpService.verifyOtp(email, otp);
    }

    // =========================================================
    // CHANGE PASSWORD
    // =========================================================

    public void changePassword(PasswordResetRequest request) {

        if (request == null ||
                request.getEmail() == null ||
                request.getEmail().trim().isEmpty()) {

            throw new RuntimeException("Email is required.");
        }

        if (request.getNewPassword() == null ||
                request.getNewPassword().trim().isEmpty()) {

            throw new RuntimeException("New password is required.");
        }

        if (request.getNewPassword().length() < 6) {

            throw new RuntimeException(
                    "Password must be at least 6 characters."
            );
        }

        User user = userRepository.findByEmail(
                request.getEmail()
        );

        if (user == null) {

            throw new RuntimeException(
                    "No user found with this email address."
            );
        }

        user.setPassword(
                passwordEncoder.encode(
                        request.getNewPassword()
                )
        );

        userRepository.save(user);
    }
}