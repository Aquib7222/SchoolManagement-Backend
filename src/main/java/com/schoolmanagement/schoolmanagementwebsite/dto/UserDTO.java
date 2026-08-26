package com.schoolmanagement.schoolmanagementwebsite.dto;

public class UserDTO {

    private Long id;
    private String name;
    private String email;
    private String role;
    private String phone;
    public String getAdmissionNumber() {
        return admissionNumber;
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

    public void setAdmissionNumber(String admissionNumber) {
        this.admissionNumber = admissionNumber;
    }

    private String status;
    private Long schoolId;
    private SchoolDTO school;
    private String admissionNumber;
    private String username;
private boolean phoneVerified;
private boolean emailVerified;

    // getters & setters

    public Long getId() {
        return id;
    }

    private Long userGroupId;

public Long getUserGroupId() {
    return userGroupId;
}

public void setUserGroupId(Long userGroupId) {
    this.userGroupId = userGroupId;
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

    public String getStatus() {
        return status;
    }

    public void setStatus(String status) {
        this.status = status;
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
}
