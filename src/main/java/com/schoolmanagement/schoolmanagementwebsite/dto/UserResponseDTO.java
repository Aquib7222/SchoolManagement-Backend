// package com.schoolmanagement.schoolmanagementwebsite.dto;

// import com.schoolmanagement.schoolmanagementwebsite.entity.School;

// public class UserResponseDTO {

//     private Long id;
//     private String name;
//     private String email;
//     private String role;
//     private String status;
//     private Long schoolId;
//     private School school;

//     public UserResponseDTO(Long id, String name, String email, String role,
//                            String status, Long schoolId, School school) {
//         this.id = id;
//         this.name = name;
//         this.email = email;
//         this.role = role;
//         this.status = status;
//         this.schoolId = schoolId;
//         this.school = school;
//     }

//     // getters only (no password!)

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

//     public School getSchool() {
//         return school;
//     }

//     public void setSchool(School school) {
//         this.school = school;
//     }
// }


package com.schoolmanagement.schoolmanagementwebsite.dto;

public class UserResponseDTO {

    private Long id;
    private String name;
    private String email;
    private String role;
    private String status;

    private String phone;
    private String username;

    private boolean phoneVerified;
    private boolean emailVerified;

    private Long schoolId;
    private String schoolName;
    private String schoolCode;

    private Long userGroupId;
    private String userGroupName;
    private String userGroupCode;

    public UserResponseDTO() {
    }

    public UserResponseDTO(
            Long id,
            String name,
            String email,
            String role,
            String status,
            String phone,
            String username,
            boolean phoneVerified,
            boolean emailVerified,
            Long schoolId,
            String schoolName,
            String schoolCode,
            Long userGroupId,
            String userGroupName,
            String userGroupCode
    ) {
        this.id = id;
        this.name = name;
        this.email = email;
        this.role = role;
        this.status = status;
        this.phone = phone;
        this.username = username;
        this.phoneVerified = phoneVerified;
        this.emailVerified = emailVerified;
        this.schoolId = schoolId;
        this.schoolName = schoolName;
        this.schoolCode = schoolCode;
        this.userGroupId = userGroupId;
        this.userGroupName = userGroupName;
        this.userGroupCode = userGroupCode;
    }

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

    public String getStatus() {
        return status;
    }

    public void setStatus(String status) {
        this.status = status;
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

    public String getSchoolName() {
        return schoolName;
    }

    public void setSchoolName(String schoolName) {
        this.schoolName = schoolName;
    }

    public String getSchoolCode() {
        return schoolCode;
    }

    public void setSchoolCode(String schoolCode) {
        this.schoolCode = schoolCode;
    }

    public Long getUserGroupId() {
        return userGroupId;
    }

    public void setUserGroupId(Long userGroupId) {
        this.userGroupId = userGroupId;
    }

    public String getUserGroupName() {
        return userGroupName;
    }

    public void setUserGroupName(String userGroupName) {
        this.userGroupName = userGroupName;
    }

    public String getUserGroupCode() {
        return userGroupCode;
    }

    public void setUserGroupCode(String userGroupCode) {
        this.userGroupCode = userGroupCode;
    }
}