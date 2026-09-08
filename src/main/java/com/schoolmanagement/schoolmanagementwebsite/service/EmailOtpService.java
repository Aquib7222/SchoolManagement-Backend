// package com.schoolmanagement.schoolmanagementwebsite.service;

// import com.schoolmanagement.schoolmanagementwebsite.entity.EmailOtp;
// import com.schoolmanagement.schoolmanagementwebsite.repository.EmailOtpRepository;
// import org.springframework.beans.factory.annotation.Autowired;
// import org.springframework.mail.SimpleMailMessage;
// import org.springframework.mail.javamail.JavaMailSender;
// import org.springframework.stereotype.Service;

// import java.time.LocalDateTime;
// import java.util.Random;
// import java.util.regex.Pattern;

// @Service
// public class EmailOtpService {

//     @Autowired
//     private EmailOtpRepository emailOtpRepository;

//     @Autowired
//     private JavaMailSender mailSender;

//     private static final Pattern EMAIL_PATTERN =
//             Pattern.compile(
//                     "^[A-Za-z0-9+_.-]+@[A-Za-z0-9.-]+$"
//             );

//     public void sendOtp(String email) {

//         // -----------------------------------------
//         // 1. Basic email validation
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
//         // 2. Generate 6 digit OTP
//         // -----------------------------------------

//         String otp = String.format(
//                 "%06d",
//                 new Random().nextInt(1000000)
//         );

//         // -----------------------------------------
//         // 3. Create OTP entity
//         // -----------------------------------------

//         LocalDateTime now =
//                 LocalDateTime.now();

//         LocalDateTime expiry =
//                 now.plusMinutes(5);

//         EmailOtp emailOtp =
//                 new EmailOtp();

//         emailOtp.setEmail(email);
//         emailOtp.setOtp(otp);
//         emailOtp.setCreatedAt(now);
//         emailOtp.setExpiresAt(expiry);

//         // -----------------------------------------
//         // 4. Save OTP in database
//         // -----------------------------------------

//         emailOtpRepository.save(emailOtp);

//         // -----------------------------------------
//         // 5. Send OTP through Gmail SMTP
//         // -----------------------------------------

//         SimpleMailMessage message =
//                 new SimpleMailMessage();

//         message.setTo(email);

//         message.setSubject(
//                 "Email Verification OTP"
//         );

//         message.setText(
//                 "Dear User,\n\n" +
//                 "Your email verification OTP is: "
//                 + otp
//                 + "\n\n"
//                 + "This OTP is valid for 5 minutes.\n\n"
//                 + "Please do not share this OTP with anyone.\n\n"
//                 + "Regards,\n"
//                 + "ZYNTaks Education"
//         );

//         mailSender.send(message);
//     }

//     public boolean verifyOtp( String email, String otp ) { if (email == null || email.trim().isEmpty()) { throw new RuntimeException( "Email is required" ); } if (otp == null || otp.trim().isEmpty()) { throw new RuntimeException( "OTP is required" ); } email = email.trim().toLowerCase(); otp = otp.trim(); EmailOtp emailOtp = emailOtpRepository .findTopByEmailOrderByCreatedAtDesc(email) .orElseThrow(() -> new RuntimeException( "OTP not found" ) );  if ( LocalDateTime.now() .isAfter(emailOtp.getExpiresAt()) ) { throw new RuntimeException( "OTP has expired" ); }  if (!emailOtp.getOtp().equals(otp)) { throw new RuntimeException( "Invalid OTP" ); } return true; }
// }


package com.schoolmanagement.schoolmanagementwebsite.service;

import com.schoolmanagement.schoolmanagementwebsite.entity.EmailOtp;
import com.schoolmanagement.schoolmanagementwebsite.repository.EmailOtpRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.mail.SimpleMailMessage;
import org.springframework.mail.javamail.JavaMailSender;
import org.springframework.stereotype.Service;

import java.time.LocalDateTime;
import java.util.Random;
import java.util.regex.Pattern;

@Service
public class EmailOtpService {

    @Autowired
    private EmailOtpRepository emailOtpRepository;

    @Autowired
    private JavaMailSender mailSender;

    private static final Pattern EMAIL_PATTERN =
            Pattern.compile(
                    "^[A-Za-z0-9+_.-]+@[A-Za-z0-9.-]+$"
            );

    // =====================================================
    // SEND EMAIL OTP
    // =====================================================

    public void sendOtp(String email) {

        // -----------------------------------------
        // 1. Validate Email
        // -----------------------------------------

        if (email == null || email.trim().isEmpty()) {
            throw new RuntimeException("Email is required");
        }

        email = email.trim().toLowerCase();

        if (!EMAIL_PATTERN.matcher(email).matches()) {
            throw new RuntimeException(
                    "Please enter a valid email address"
            );
        }

        // -----------------------------------------
        // 2. Generate 6 Digit OTP
        // -----------------------------------------

        String otp = String.format(
                "%06d",
                new Random().nextInt(1000000)
        );

        // -----------------------------------------
        // 3. Create OTP Entity
        // -----------------------------------------

        LocalDateTime now = LocalDateTime.now();

        LocalDateTime expiry =
                now.plusMinutes(5);

        EmailOtp emailOtp =
                new EmailOtp();

        emailOtp.setEmail(email);
        emailOtp.setOtp(otp);
        emailOtp.setCreatedAt(now);
        emailOtp.setExpiresAt(expiry);

        // -----------------------------------------
        // 4. Save OTP in Database
        // -----------------------------------------

        emailOtpRepository.save(emailOtp);

        // -----------------------------------------
        // 5. Create Email
        // -----------------------------------------

        SimpleMailMessage message =
                new SimpleMailMessage();

        message.setTo(email);

        message.setSubject(
                "ZYNTaks Education - Email Verification OTP"
        );

        // -----------------------------------------
        // COMPLETE EMAIL MESSAGE
        // -----------------------------------------

        message.setText(
                "Dear User,\n\n" +

                "Thank you for using ZYNTaks Education.\n\n" +

                "Your email verification OTP is:\n\n" +

                "================================\n" +
                "              " + otp + "\n" +
                "================================\n\n" +

                "This OTP is valid for 5 minutes.\n\n" +

                "Please enter this OTP on the verification page " +
                "to verify your email address.\n\n" +

                "For security reasons, please do not share this OTP " +
                "with anyone.\n\n" +

                "If you did not request this verification, " +
                "please ignore this email.\n\n" +

                "Regards,\n" +
                "ZYNTaks Education\n" +
                "School Management System"
        );

        // -----------------------------------------
        // 6. Send Email
        // -----------------------------------------

        mailSender.send(message);
    }

    // =====================================================
    // VERIFY EMAIL OTP
    // =====================================================

    public boolean verifyOtp(String email, String otp) {

        if (email == null || email.trim().isEmpty()) {
            throw new RuntimeException(
                    "Email is required"
            );
        }

        if (otp == null || otp.trim().isEmpty()) {
            throw new RuntimeException(
                    "OTP is required"
            );
        }

        email = email.trim().toLowerCase();
        otp = otp.trim();

        EmailOtp emailOtp =
                emailOtpRepository
                        .findTopByEmailOrderByCreatedAtDesc(email)
                        .orElseThrow(() ->
                                new RuntimeException(
                                        "OTP not found"
                                )
                        );

        // -----------------------------------------
        // Check Expiry
        // -----------------------------------------

        if (LocalDateTime.now()
                .isAfter(emailOtp.getExpiresAt())) {

            throw new RuntimeException(
                    "OTP has expired"
            );
        }

        // -----------------------------------------
        // Check OTP
        // -----------------------------------------

        if (!emailOtp.getOtp().equals(otp)) {

            throw new RuntimeException(
                    "Invalid OTP"
            );
        }

        return true;
    }
}

