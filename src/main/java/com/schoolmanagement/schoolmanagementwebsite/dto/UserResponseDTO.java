package com.schoolmanagement.schoolmanagementwebsite.dto;

import com.schoolmanagement.schoolmanagementwebsite.entity.School;

public class UserResponseDTO {

    private Long id;
    private String name;
    private String email;
    private String role;
    private String status;
    private Long schoolId;
    private School school;

    public UserResponseDTO(Long id, String name, String email, String role,
                           String status, Long schoolId, School school) {
        this.id = id;
        this.name = name;
        this.email = email;
        this.role = role;
        this.status = status;
        this.schoolId = schoolId;
        this.school = school;
    }

    // getters only (no password!)

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

    public Long getSchoolId() {
        return schoolId;
    }

    public void setSchoolId(Long schoolId) {
        this.schoolId = schoolId;
    }

    public School getSchool() {
        return school;
    }

    public void setSchool(School school) {
        this.school = school;
    }
}
