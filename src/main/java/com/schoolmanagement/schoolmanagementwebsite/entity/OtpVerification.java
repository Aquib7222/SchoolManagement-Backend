// package com.schoolmanagement.schoolmanagementwebsite.entity;

// import java.time.LocalDateTime;

// import jakarta.persistence.Entity;
// import jakarta.persistence.GeneratedValue;
// import jakarta.persistence.GenerationType;
// import jakarta.persistence.Id;
// import jakarta.persistence.PrePersist;
// import jakarta.persistence.Table;

// @Entity
// @Table(name = "otp_verification")
// public class OtpVerification {

//     @Id
//     @GeneratedValue(strategy = GenerationType.IDENTITY)
//     private Long id;

//     private String phone;

//     private String email;

//     private String phoneOtp;

//     private String emailOtp;

//     private LocalDateTime phoneOtpExpiry;

//     private LocalDateTime emailOtpExpiry;

//     private boolean phoneVerified = false;

//     private boolean emailVerified = false;

//     private int phoneAttempts = 0;

//     private int emailAttempts = 0;

//     private LocalDateTime createdAt;

//     @PrePersist
//     public void onCreate() {
//         createdAt = LocalDateTime.now();
//     }

//     public Long getId() {
//         return id;
//     }

//     public String getPhone() {
//         return phone;
//     }

//     public void setPhone(String phone) {
//         this.phone = phone;
//     }

//     public String getEmail() {
//         return email;
//     }

//     public void setEmail(String email) {
//         this.email = email;
//     }

//     public String getPhoneOtp() {
//         return phoneOtp;
//     }

//     public void setPhoneOtp(String phoneOtp) {
//         this.phoneOtp = phoneOtp;
//     }

//     public String getEmailOtp() {
//         return emailOtp;
//     }

//     public void setEmailOtp(String emailOtp) {
//         this.emailOtp = emailOtp;
//     }

//     public LocalDateTime getPhoneOtpExpiry() {
//         return phoneOtpExpiry;
//     }

//     public void setPhoneOtpExpiry(LocalDateTime phoneOtpExpiry) {
//         this.phoneOtpExpiry = phoneOtpExpiry;
//     }

//     public LocalDateTime getEmailOtpExpiry() {
//         return emailOtpExpiry;
//     }

//     public void setEmailOtpExpiry(LocalDateTime emailOtpExpiry) {
//         this.emailOtpExpiry = emailOtpExpiry;
//     }

//     public boolean isPhoneVerified() {
//         return phoneVerified;
//     }

//     public void setPhoneVerified(boolean phoneVerified) {
//         this.phoneVerified = phoneVerified;
//     }

//     public boolean isEmailVerified() {
//         return emailVerified;
//     }

//     public void setEmailVerified(boolean emailVerified) {
//         this.emailVerified = emailVerified;
//     }

//     public int getPhoneAttempts() {
//         return phoneAttempts;
//     }

//     public void setPhoneAttempts(int phoneAttempts) {
//         this.phoneAttempts = phoneAttempts;
//     }

//     public int getEmailAttempts() {
//         return emailAttempts;
//     }

//     public void setEmailAttempts(int emailAttempts) {
//         this.emailAttempts = emailAttempts;
//     }

//     public LocalDateTime getCreatedAt() {
//         return createdAt;
//     }
// }

package com.schoolmanagement.schoolmanagementwebsite.entity;

import java.time.LocalDateTime;

import jakarta.persistence.*;

@Entity
@Table(name = "otp_verification")
public class OtpVerification {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    private String phone;
    private String email;

    private String phoneOtp;
    private String emailOtp;

    private LocalDateTime phoneOtpExpiry;
    private LocalDateTime emailOtpExpiry;

    private boolean phoneVerified;
    private boolean emailVerified;

    private int phoneAttempts;
    private int emailAttempts;

    private LocalDateTime createdAt;

    @PrePersist
    public void onCreate() {
        createdAt = LocalDateTime.now();
    }

    public Long getId() {
        return id;
    }

    public String getPhone() {
        return phone;
    }

    public void setPhone(String phone) {
        this.phone = phone;
    }

    public String getEmail() {
        return email;
    }

    public void setEmail(String email) {
        this.email = email;
    }

    public String getPhoneOtp() {
        return phoneOtp;
    }

    public void setPhoneOtp(String phoneOtp) {
        this.phoneOtp = phoneOtp;
    }

    public String getEmailOtp() {
        return emailOtp;
    }

    public void setEmailOtp(String emailOtp) {
        this.emailOtp = emailOtp;
    }

    public LocalDateTime getPhoneOtpExpiry() {
        return phoneOtpExpiry;
    }

    public void setPhoneOtpExpiry(LocalDateTime phoneOtpExpiry) {
        this.phoneOtpExpiry = phoneOtpExpiry;
    }

    public LocalDateTime getEmailOtpExpiry() {
        return emailOtpExpiry;
    }

    public void setEmailOtpExpiry(LocalDateTime emailOtpExpiry) {
        this.emailOtpExpiry = emailOtpExpiry;
    }

    public boolean isPhoneVerified() {
        return phoneVerified;
    }

    public void setPhoneVerified(boolean phoneVerified) {
        this.phoneVerified = phoneVerified;
    }

    public boolean isEmailVerified() {
        return emailVerified;
    }

    public void setEmailVerified(boolean emailVerified) {
        this.emailVerified = emailVerified;
    }

    public int getPhoneAttempts() {
        return phoneAttempts;
    }

    public void setPhoneAttempts(int phoneAttempts) {
        this.phoneAttempts = phoneAttempts;
    }

    public int getEmailAttempts() {
        return emailAttempts;
    }

    public void setEmailAttempts(int emailAttempts) {
        this.emailAttempts = emailAttempts;
    }

    public LocalDateTime getCreatedAt() {
        return createdAt;
    }
}