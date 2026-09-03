package com.schoolmanagement.schoolmanagementwebsite.dto;

import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
public class StudentImportItem {

    private String admissionNumber;

    private String firstName;
    private String middleName;
    private String lastName;

    private String dateOfBirth;
    private String gender;
    private String age;

    private String bloodGroup;
    private String nationality;
    private String motherTongue;
    private String religion;
    private String category;
    private String caste;

    private String fatherName;
    private String fatherMobile;
    private String fatherEmail;
    private String fatherOccupation;

    private String motherName;
    private String motherMobile;
    private String motherEmail;
    private String motherOccupation;

    private String mobile;
    private String email;

    private String transportRequired;

    private String feeCategory;
    private String feeBatch;

    private String houseNo;
    private String street;
    private String area;
    private String town;
    private String city;
    private String state;
    private String country;
    private String zip;

    private Integer rollNumber;
}