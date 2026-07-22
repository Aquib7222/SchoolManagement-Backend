package com.schoolmanagement.schoolmanagementwebsite.entity;

import com.fasterxml.jackson.annotation.JsonManagedReference;
import com.schoolmanagement.schoolmanagementwebsite.enums.AdmissionStatus;

import jakarta.persistence.CascadeType;
import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.EnumType;
import jakarta.persistence.Enumerated;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.persistence.JoinColumn;
import jakarta.persistence.ManyToOne;
import jakarta.persistence.OneToOne;
import jakarta.persistence.Table;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@Entity
@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
@Builder
@Table(name = "admissions")
public class Admission {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    private String admissionNumber;
    private String academicYear;
    private String academicType;
    private String invoice;
    private String today;

    private String firstName;
    private String middleName;
    private String lastName;
    private String dob;
    private String gender;
    private String aadharNo;

    private String nationality;
    private String motherTongue;
    private String religion;
    private String category;
    private String caste;
    private String bloodGroup;

    private String transportRequired;
    private String studentClass;
    private String age;

    private String email;
    private String preferredNo;
    private String alternateNo;

    private String feeCategory;
    private String feeBatch;

    // Father
    private String fatherName;
    private String fatherMobile;
    private String fatherEmail;
    private String fatherOccupation;

    // Mother
    private String motherName;
    private String motherMobile;
    private String motherEmail;
    private String motherOccupation;

    // Address
    private String houseNo;
    private String street;
    private String area;
    private String town;
    private String city;
    private String state;
    private String country;
    private String zip;

    @JsonManagedReference
    @OneToOne(mappedBy = "admission", cascade = CascadeType.ALL)
    private Student student;

    @ManyToOne
    @JoinColumn(name = "school_id")
    private School school;

    @Enumerated(EnumType.STRING)
    @Column(nullable = false)
    private AdmissionStatus status; // ✅ HERE

    

}
