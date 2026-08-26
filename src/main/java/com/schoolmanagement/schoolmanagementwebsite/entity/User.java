package com.schoolmanagement.schoolmanagementwebsite.entity;

import com.fasterxml.jackson.annotation.JsonIgnore;

import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.persistence.JoinColumn;
import jakarta.persistence.ManyToOne;

@Entity
public class User {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    private String name;
    @Column(unique = true, nullable = false)
    private String email;

    @JsonIgnore
    private String password;
    private String role;
    private String phone;
    private String status = "Active";

    @ManyToOne
@JoinColumn(name = "user_group_id")
private UserGroup userGroup;

    // @Column(name = "school_id", insertable = false, updatable = false)
    // private Long schoolId;

   @ManyToOne(optional = false)
@JoinColumn(name = "school_id", nullable = false)
private School school;

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

@Column(unique = true, nullable = false)
private String username;

private boolean phoneVerified = false;

private boolean emailVerified = false;


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

    public String getPassword() {
        return password;
    }

    public void setPassword(String password) {
        this.password = password;
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

    public School getSchool() {
        return school;
    }

    public void setSchool(School school) {
        this.school = school;
    }

    // public Long getSchoolId() {
    //     return schoolId;
    // }

    // public void setSchoolId(Long schoolId) {
    //     this.schoolId = schoolId;
    // }

    public String getPhone() {
        return phone;
    }

    public void setPhone(String phone) {
        this.phone = phone;
    }
    public UserGroup getUserGroup() {
    return userGroup;
}

public void setUserGroup(UserGroup userGroup) {
    this.userGroup = userGroup;
}
}
