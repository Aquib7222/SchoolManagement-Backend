// package com.schoolmanagement.schoolmanagementwebsite.dto;

// public class StudentRequest {
    
    
// }


package com.schoolmanagement.schoolmanagementwebsite.dto;

import java.time.LocalDate;

import com.schoolmanagement.schoolmanagementwebsite.enums.Section;
import com.schoolmanagement.schoolmanagementwebsite.enums.StudentStatus;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
@Builder
public class StudentRequest {

    // =========================
    // BASIC DETAILS
    // =========================
    private String admissionNumber;

    private String firstName;
    private String middleName;
    private String lastName;

    private String dob;
    private String gender;
    private String age;

    // =========================
    // ACADEMIC DETAILS
    // =========================
    private String studentClass;
    private Section section;
    private String academicYear;

    private Integer rollNumber;

    // =========================
    // PERSONAL DETAILS
    // =========================
    private String nationality;
    private String motherTongue;
    private String religion;
    private String category;
    private String caste;
    private String bloodGroup;

    // =========================
    // TRANSPORT
    // =========================
    private String transportRequired;

    // =========================
    // STUDENT CONTACT
    // =========================
    private String email;
    private String mobile;

    // =========================
    // FEE DETAILS
    // =========================
    private String feeCategory;
    private String feeBatch;

    // =========================
    // FATHER DETAILS
    // =========================
    private String fatherName;
    private String fatherMobile;
    private String fatherEmail;
    private String fatherOccupation;

    // =========================
    // MOTHER DETAILS
    // =========================
    private String motherName;
    private String motherMobile;
    private String motherEmail;
    private String motherOccupation;

    // =========================
    // ADDRESS
    // =========================
    private String houseNo;
    private String street;
    private String area;
    private String town;
    private String city;
    private String state;
    private String country;
    private String zip;

    // =========================
    // STATUS
    // =========================
    private StudentStatus status;

    private LocalDate discontinueDate;

    // =========================
    // RELATION IDs
    // =========================
    private Long schoolId;
    private Long admissionId;
}