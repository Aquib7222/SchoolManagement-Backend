// // package com.schoolmanagement.schoolmanagementwebsite.dto;

// // public class UserDTO {

// //     private Long id;
// //     private String name;
// //     private String email;
// //     private String role;
// //     private String phone;
// //     private String username;
// //     private boolean phoneVerified;
// //     private boolean emailVerified;

// //     public String getAdmissionNumber() {
// //         return admissionNumber;
// //     }

// //     public String getUsername() {
// //         return username;
// //     }

// //     public void setUsername(String username) {
// //         this.username = username;
// //     }

// //     public boolean isPhoneVerified() {
// //         return phoneVerified;
// //     }

// //     public void setPhoneVerified(boolean phoneVerified) {
// //         this.phoneVerified = phoneVerified;
// //     }

// //     public boolean isEmailVerified() {
// //         return emailVerified;
// //     }

// //     public void setEmailVerified(boolean emailVerified) {
// //         this.emailVerified = emailVerified;
// //     }

// //     public void setAdmissionNumber(String admissionNumber) {
// //         this.admissionNumber = admissionNumber;
// //     }

// //     private String status;
// //     private Long schoolId;
// //     private SchoolDTO school;
// //     private String admissionNumber;


// //     // getters & setters
// //     public Long getId() {
// //         return id;
// //     }

// //     private Long userGroupId;

// //     public Long getUserGroupId() {
// //         return userGroupId;
// //     }

// //     public void setUserGroupId(Long userGroupId) {
// //         this.userGroupId = userGroupId;
// //     }

// //     public void setId(Long id) {
// //         this.id = id;
// //     }

// //     public String getName() {
// //         return name;
// //     }

// //     public void setName(String name) {
// //         this.name = name;
// //     }

// //     public String getEmail() {
// //         return email;
// //     }

// //     public void setEmail(String email) {
// //         this.email = email;
// //     }

// //     public String getRole() {
// //         return role;
// //     }

// //     public void setRole(String role) {
// //         this.role = role;
// //     }

// //     public String getPhone() {
// //         return phone;
// //     }

// //     public void setPhone(String phone) {
// //         this.phone = phone;
// //     }

// //     public String getStatus() {
// //         return status;
// //     }

// //     public void setStatus(String status) {
// //         this.status = status;
// //     }

// //     public Long getSchoolId() {
// //         return schoolId;
// //     }

// //     public void setSchoolId(Long schoolId) {
// //         this.schoolId = schoolId;
// //     }

// //     public SchoolDTO getSchool() {
// //         return school;
// //     }

// //     public void setSchool(SchoolDTO school) {
// //         this.school = school;
// //     }
// // }


// package com.schoolmanagement.schoolmanagementwebsite.dto;

// import com.fasterxml.jackson.annotation.JsonInclude;

// @JsonInclude(JsonInclude.Include.NON_NULL)
// public class UserDTO {

//     private Long id;
//     private String name;
//     private String email;
//     private String role;
//     private String phone;
//     private String username;

//     private boolean phoneVerified;
//     private boolean emailVerified;

//     private String status;

//     private Long schoolId;
//     private SchoolDTO school;

//     // Student ke liye
//     private String admissionNumber;

//     // Teacher ke liye
//     private Long teacherId;

//     private Long userGroupId;


//     // =========================
//     // GETTERS & SETTERS
//     // =========================

//     public Long getId() {
//         return id;
//     }

//     public void setId(Long id) {
//         this.id = id;
//     }


//     public String getName() {
//         return name;
//     }

//     public void setName(String name) {
//         this.name = name;
//     }


//     public String getEmail() {
//         return email;
//     }

//     public void setEmail(String email) {
//         this.email = email;
//     }


//     public String getRole() {
//         return role;
//     }

//     public void setRole(String role) {
//         this.role = role;
//     }


//     public String getPhone() {
//         return phone;
//     }

//     public void setPhone(String phone) {
//         this.phone = phone;
//     }


//     public String getUsername() {
//         return username;
//     }

//     public void setUsername(String username) {
//         this.username = username;
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


//     public String getStatus() {
//         return status;
//     }

//     public void setStatus(String status) {
//         this.status = status;
//     }


//     public Long getSchoolId() {
//         return schoolId;
//     }

//     public void setSchoolId(Long schoolId) {
//         this.schoolId = schoolId;
//     }


//     public SchoolDTO getSchool() {
//         return school;
//     }

//     public void setSchool(SchoolDTO school) {
//         this.school = school;
//     }


//     // =========================
//     // STUDENT
//     // =========================

//     public String getAdmissionNumber() {
//         return admissionNumber;
//     }

//     public void setAdmissionNumber(String admissionNumber) {
//         this.admissionNumber = admissionNumber;
//     }


//     // =========================
//     // TEACHER
//     // =========================

//     public Long getTeacherId() {
//         return teacherId;
//     }

//     public void setTeacherId(Long teacherId) {
//         this.teacherId = teacherId;
//     }


//     // =========================
//     // USER GROUP
//     // =========================

//     public Long getUserGroupId() {
//         return userGroupId;
//     }

//     public void setUserGroupId(Long userGroupId) {
//         this.userGroupId = userGroupId;
//     }
// }




package com.schoolmanagement.schoolmanagementwebsite.dto;

import com.fasterxml.jackson.annotation.JsonInclude;

@JsonInclude(JsonInclude.Include.NON_NULL)
public class UserDTO {

    // =====================================================
    // BASIC INFORMATION
    // =====================================================

    private Long id;

    private String name;

    private String email;

    private String role;

    private String phone;

    private String username;

    private String status;


    // =====================================================
    // VERIFICATION
    // =====================================================

    private boolean phoneVerified;

    private boolean emailVerified;


    // =====================================================
    // SCHOOL
    // =====================================================

    private Long schoolId;

    private SchoolDTO school;


    // =====================================================
    // STUDENT
    // =====================================================

    /*
     * Only STUDENT role gets this.
     */

    private String admissionNumber;


    // =====================================================
    // TEACHER
    // =====================================================

    /*
     * Only TEACHER role gets this.
     */

    private Long teacherId;


    // =====================================================
    // USER GROUP
    // =====================================================

    private Long userGroupId;


    // =====================================================
    // GETTERS & SETTERS
    // =====================================================

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }


    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }


    public String getEmail() {
        return email;
    }

    public void setEmail(String email) {
        this.email = email;
    }


    public String getRole() {
        return role;
    }

    public void setRole(String role) {
        this.role = role;
    }


    public String getPhone() {
        return phone;
    }

    public void setPhone(String phone) {
        this.phone = phone;
    }


    public String getUsername() {
        return username;
    }

    public void setUsername(String username) {
        this.username = username;
    }


    public String getStatus() {
        return status;
    }

    public void setStatus(String status) {
        this.status = status;
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


    public Long getSchoolId() {
        return schoolId;
    }

    public void setSchoolId(Long schoolId) {
        this.schoolId = schoolId;
    }


    public SchoolDTO getSchool() {
        return school;
    }

    public void setSchool(SchoolDTO school) {
        this.school = school;
    }


    // =====================================================
    // STUDENT
    // =====================================================

    public String getAdmissionNumber() {
        return admissionNumber;
    }

    public void setAdmissionNumber(
            String admissionNumber) {

        this.admissionNumber = admissionNumber;
    }


    // =====================================================
    // TEACHER
    // =====================================================

    public Long getTeacherId() {
        return teacherId;
    }

    public void setTeacherId(Long teacherId) {
        this.teacherId = teacherId;
    }


    // =====================================================
    // USER GROUP
    // =====================================================

    public Long getUserGroupId() {
        return userGroupId;
    }

    public void setUserGroupId(Long userGroupId) {
        this.userGroupId = userGroupId;
    }
}


