// // package com.schoolmanagement.schoolmanagementwebsite.entity;

// // import java.time.LocalDateTime;

// // import jakarta.persistence.Entity;
// // import jakarta.persistence.GeneratedValue;
// // import jakarta.persistence.GenerationType;
// // import jakarta.persistence.Id;
// // import jakarta.persistence.JoinColumn;
// // import jakarta.persistence.ManyToOne;
// // import jakarta.persistence.Table;

// // @Entity
// // @Table(name = "superadmins")
// // public class SuperAdmin {

// //     @Id
// //     @GeneratedValue(strategy = GenerationType.IDENTITY)
// //     private Long id;

// //     private String fullName;
// //     private String email;
// //     private String phone;

// //     @ManyToOne
// //     @JoinColumn(name = "school_id")
// //     private School school;

// //     private String status = "Active";

// //     private String role;

// //     private LocalDateTime createdAt = LocalDateTime.now();

// //     // getters & setters

// //     public Long getId() {
// //         return id;
// //     }

// //     public void setId(Long id) {
// //         this.id = id;
// //     }

// //     public String getFullName() {
// //         return fullName;
// //     }

// //     public void setFullName(String fullName) {
// //         this.fullName = fullName;
// //     }

// //     public String getEmail() {
// //         return email;
// //     }

// //     public void setEmail(String email) {
// //         this.email = email;
// //     }

// //     public String getPhone() {
// //         return phone;
// //     }

// //     public void setPhone(String phone) {
// //         this.phone = phone;
// //     }

// //     public School getSchool() {
// //         return school;
// //     }

// //     public void setSchool(School school) {
// //         this.school = school;
// //     }

// //     public String getStatus() {
// //         return status;
// //     }

// //     public void setStatus(String status) {
// //         this.status = status;
// //     }

// //     public LocalDateTime getCreatedAt() {
// //         return createdAt;
// //     }

// //     public void setCreatedAt(LocalDateTime createdAt) {
// //         this.createdAt = createdAt;
// //     }

// //     public String getRole() {
// //         return role;
// //     }

// //     public void setRole(String role) {
// //         this.role = role;
// //     }
// // }


// package com.schoolmanagement.schoolmanagementwebsite.entity;

// import java.time.LocalDate;
// import java.time.LocalDateTime;

// import jakarta.persistence.*;

// @Entity
// @Table(name = "superadmins")
// public class SuperAdmin {

//     @Id
//     @GeneratedValue(strategy = GenerationType.IDENTITY)
//     private Long id;


//     // =========================
//     // PERSONAL INFORMATION
//     // =========================

//     @Column(nullable = false)
//     private String fullName;

//     @Column(nullable = false)
//     private String email;

//     @Column(nullable = false)
//     private String phone;

//     private String alternatePhone;

//     private LocalDate dateOfBirth;

//     private String gender;


//     // =========================
//     // SECURITY INFORMATION
//     // =========================

//     private String securityQuestion;

//     private String securityAnswer;


//     // =========================
//     // ADDITIONAL INFORMATION
//     // =========================

//     @Column(columnDefinition = "TEXT")
//     private String address;

//     private String languagePreference;

//     private String timeZone;

//     @Column(columnDefinition = "TEXT")
//     private String note;


//     // =========================
//     // PROFILE
//     // =========================

//     private String profilePicture;


//     // =========================
//     // SCHOOL
//     // =========================

//     @ManyToOne(optional = false)
//     @JoinColumn(name = "school_id", nullable = false)
//     private School school;


//     // =========================
//     // ACCOUNT
//     // =========================

//     private String status = "Active";

//     private String role;


//     // =========================
//     // CREATED
//     // =========================

//     private LocalDateTime createdAt = LocalDateTime.now();


//     // =========================
//     // GETTERS & SETTERS
//     // =========================

//     public Long getId() {
//         return id;
//     }

//     public void setId(Long id) {
//         this.id = id;
//     }


//     public String getFullName() {
//         return fullName;
//     }

//     public void setFullName(String fullName) {
//         this.fullName = fullName;
//     }


//     public String getEmail() {
//         return email;
//     }

//     public void setEmail(String email) {
//         this.email = email;
//     }


//     public String getPhone() {
//         return phone;
//     }

//     public void setPhone(String phone) {
//         this.phone = phone;
//     }


//     public String getAlternatePhone() {
//         return alternatePhone;
//     }

//     public void setAlternatePhone(String alternatePhone) {
//         this.alternatePhone = alternatePhone;
//     }


//     public LocalDate getDateOfBirth() {
//         return dateOfBirth;
//     }

//     public void setDateOfBirth(LocalDate dateOfBirth) {
//         this.dateOfBirth = dateOfBirth;
//     }


//     public String getGender() {
//         return gender;
//     }

//     public void setGender(String gender) {
//         this.gender = gender;
//     }


//     public String getSecurityQuestion() {
//         return securityQuestion;
//     }

//     public void setSecurityQuestion(String securityQuestion) {
//         this.securityQuestion = securityQuestion;
//     }


//     public String getSecurityAnswer() {
//         return securityAnswer;
//     }

//     public void setSecurityAnswer(String securityAnswer) {
//         this.securityAnswer = securityAnswer;
//     }


//     public String getAddress() {
//         return address;
//     }

//     public void setAddress(String address) {
//         this.address = address;
//     }


//     public String getLanguagePreference() {
//         return languagePreference;
//     }

//     public void setLanguagePreference(String languagePreference) {
//         this.languagePreference = languagePreference;
//     }


//     public String getTimeZone() {
//         return timeZone;
//     }

//     public void setTimeZone(String timeZone) {
//         this.timeZone = timeZone;
//     }


//     public String getNote() {
//         return note;
//     }

//     public void setNote(String note) {
//         this.note = note;
//     }


//     public String getProfilePicture() {
//         return profilePicture;
//     }

//     public void setProfilePicture(String profilePicture) {
//         this.profilePicture = profilePicture;
//     }


//     public School getSchool() {
//         return school;
//     }

//     public void setSchool(School school) {
//         this.school = school;
//     }


//     public String getStatus() {
//         return status;
//     }

//     public void setStatus(String status) {
//         this.status = status;
//     }


//     public String getRole() {
//         return role;
//     }

//     public void setRole(String role) {
//         this.role = role;
//     }


//     public LocalDateTime getCreatedAt() {
//         return createdAt;
//     }

//     public void setCreatedAt(LocalDateTime createdAt) {
//         this.createdAt = createdAt;
//     }
// }


package com.schoolmanagement.schoolmanagementwebsite.entity;

import java.time.LocalDate;
import java.time.LocalDateTime;

import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.persistence.JoinColumn;
import jakarta.persistence.ManyToOne;
import jakarta.persistence.Table;

@Entity
@Table(name = "superadmins")
public class SuperAdmin {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    private String fullName;

    private String email;

    private String phone;

    // private String userName;

    private String alternatePhone;

    private LocalDate dateOfBirth;

    private String gender;

    private String securityQuestion;

    private String securityAnswer;

    @Column(columnDefinition = "TEXT")
    private String address;

    private String languagePreference;

    private String timeZone;

    @Column(columnDefinition = "TEXT")
    private String note;

    private String status = "Active";

    private String role;

    private boolean twoFactorAuthentication = false;

    private boolean loginNotification = true;

    private String profilePicture;

    @ManyToOne
    @JoinColumn(name = "user_group_id")
    private UserGroup userGroup;

    @ManyToOne(optional = false)
    @JoinColumn(name = "school_id", nullable = false)
    private School school;

    private LocalDateTime createdAt = LocalDateTime.now();


    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public String getFullName() {
        return fullName;
    }

    public void setFullName(String fullName) {
        this.fullName = fullName;
    }

    public String getEmail() {
        return email;
    }

    public void setEmail(String email) {
        this.email = email;
    }

    public String getPhone() {
        return phone;
    }

    public void setPhone(String phone) {
        this.phone = phone;
    }

    public String getAlternatePhone() {
        return alternatePhone;
    }

    public void setAlternatePhone(String alternatePhone) {
        this.alternatePhone = alternatePhone;
    }

    public LocalDate getDateOfBirth() {
        return dateOfBirth;
    }

    public void setDateOfBirth(LocalDate dateOfBirth) {
        this.dateOfBirth = dateOfBirth;
    }

    public String getGender() {
        return gender;
    }

    public void setGender(String gender) {
        this.gender = gender;
    }

    public String getSecurityQuestion() {
        return securityQuestion;
    }

    public void setSecurityQuestion(String securityQuestion) {
        this.securityQuestion = securityQuestion;
    }

    public String getSecurityAnswer() {
        return securityAnswer;
    }

    public void setSecurityAnswer(String securityAnswer) {
        this.securityAnswer = securityAnswer;
    }

    public String getAddress() {
        return address;
    }

    public void setAddress(String address) {
        this.address = address;
    }

    public String getLanguagePreference() {
        return languagePreference;
    }

    public void setLanguagePreference(String languagePreference) {
        this.languagePreference = languagePreference;
    }

    public String getTimeZone() {
        return timeZone;
    }

    public void setTimeZone(String timeZone) {
        this.timeZone = timeZone;
    }

    public String getNote() {
        return note;
    }

    public void setNote(String note) {
        this.note = note;
    }

    public String getStatus() {
        return status;
    }

    public void setStatus(String status) {
        this.status = status;
    }

    public String getRole() {
        return role;
    }

    public void setRole(String role) {
        this.role = role;
    }

    public boolean isTwoFactorAuthentication() {
        return twoFactorAuthentication;
    }

    public void setTwoFactorAuthentication(boolean twoFactorAuthentication) {
        this.twoFactorAuthentication = twoFactorAuthentication;
    }

    public boolean isLoginNotification() {
        return loginNotification;
    }

    public void setLoginNotification(boolean loginNotification) {
        this.loginNotification = loginNotification;
    }

    public String getProfilePicture() {
        return profilePicture;
    }

    public void setProfilePicture(String profilePicture) {
        this.profilePicture = profilePicture;
    }

    public UserGroup getUserGroup() {
        return userGroup;
    }

    public void setUserGroup(UserGroup userGroup) {
        this.userGroup = userGroup;
    }

    public School getSchool() {
        return school;
    }

    public void setSchool(School school) {
        this.school = school;
    }

    public LocalDateTime getCreatedAt() {
        return createdAt;
    }

    public void setCreatedAt(LocalDateTime createdAt) {
        this.createdAt = createdAt;
    }
}