package com.schoolmanagement.schoolmanagementwebsite.dto.AdmissionEnquiry;

import java.time.LocalDate;



import com.schoolmanagement.schoolmanagementwebsite.enums.Enquiry.EnquirySource;


import lombok.*;

@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
@Builder
public class AdmissionEnquiryRequest {

 
    private Long schoolId;



    private LocalDate enquiryDate;

    private LocalDate followUpDate;

    private EnquirySource enquirySource;

    private String studentName;

    private LocalDate dob;

    private String gender;

    
    private String studentClass;

   
    private String academicYear;

    private String fatherName;

    private String motherName;

    private String guardianName;

   
    private String phone;

   
    private String alternatePhone;

  
    private String email;


    private String address;

    private String city;

    private String state;

    private String country;

    private String pincode;

    private String previousSchool;

    private String previousClass;

    private String remarks;
}