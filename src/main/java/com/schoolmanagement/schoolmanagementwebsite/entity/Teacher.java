package com.schoolmanagement.schoolmanagementwebsite.entity;

import java.time.LocalDate;

import jakarta.persistence.*;
import lombok.*;

@Entity
@Table(name = "teachers")
@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
@Builder
public class Teacher {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    // 🔑 Auto generated
    @Column(unique = true)
    private String employeeId;

    // ===== Basic Details =====
    private String firstName;
    private String middleName;
    private String lastName;
    private String dob;
    private String fatherName;
    private LocalDate doj;

    private String status; // Working / Resign / LongLeave / MaternityLeave
    private String gender;
    private String category;

    private String nationality;
    private String bloodGroup;

    // ===== Job Details =====
    private String department;
    private String designation;
    private String teachingLevel;
    private String employeeType;

    // ===== Contact =====
    private String phoneNumber;
    private String alternatePhoneNumber;
    private String mobileNumber;

    private String emergencyContact;
    private String emergencyRelation;

    private String email;

    private String addressLine1;
    private String addressLine2;
    private String addressLine3;
    private String city;
    private String state;
    private String pincode;

    // ===== Documents =====
    private String panNumber;
    private String biometricCard;
    private String esiNumber;
    private String aadharNumber;
    private String pfNumber;

    // medical Insurance Claim Details 
    private String maritalStatus;
    private String spouseName;
    private String spouseGender;
    private String spouseDob;

    // Religious Details 
    private String religion;
    private String caste;

    // qualification Details 
    private String qualifiation;
    private String universityBoard;
    private String passingYear;
    private String percentage;
    

    //experience Details
    private String companyName;
    private String companyDesignation;
    private String startDate;
    private String endDate;
    private String totalExperience;


    @Lob
    @Column(columnDefinition = "LONGTEXT")
    private String photo;

    // ===== Login Control =====
    private boolean active; // ✅ controls login

    // ===== School Mapping =====
    @ManyToOne
    @JoinColumn(name = "school_id")
    private School school;

    // 🔗 Linked login
    @OneToOne
    @JoinColumn(name = "user_id")
    private User user;
}

