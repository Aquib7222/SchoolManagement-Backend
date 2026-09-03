package com.schoolmanagement.schoolmanagementwebsite.dto;


import lombok.*;
import java.time.LocalDate;

// @Data
// public class AdmissionRequest {

//     private String admissionNumber;
//     private String academicYear;
//     private String academicType;
//     private String invoice;
//     private String today;

//     private String firstName;
//     private String middleName;
//     private String lastName;
//     private String dob;
//     private String gender;
//     private String aadharNo;

//     private String nationality;
//     private String motherTongue;
//     private String religion;
//     private String category;
//     private String caste;
//     private String bloodGroup;

//     private String transportRequired;
//     private String studentClass;
//     private String age;

//     private String email;
//     private String preferredNo;
//     private String alternateNo;

//     private String feeCategory;
//     private String feeBatch;

//     private String fatherName;
//     private String fatherMobile;
//     private String fatherEmail;
//     private String fatherOccupation;

//     private String motherName;
//     private String motherMobile;
//     private String motherEmail;
//     private String motherOccupation;

//     private String houseNo;
//     private String street;
//     private String area;
//     private String town;
//     private String city;
//     private String state;
//     private String country;
//     private String zip;
// }

@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
public class AdmissionRequest {

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

    private String fatherName;
    private String fatherMobile;
    private String fatherEmail;
    private String fatherOccupation;

    private String motherName;
    private String motherMobile;
    private String motherEmail;
    private String motherOccupation;

    private String houseNo;
    private String street;
    private String area;
    private String town;
    private String city;
    private String state;
    private String country;
    private String zip;

    // 🔥 ADD THIS
    private Long schoolId;

    private LocalDate cancelDate;
}
