package com.schoolmanagement.schoolmanagementwebsite.dto;

public record TeacherResponse(

        Long id,

        String employeeId,

        String firstName,
        String middleName,
        String lastName,

        String dob,
        String fatherName,
        String doj,

        String status,
        String gender,
        String category,

        String nationality,
        String bloodGroup,

        String department,
        String designation,
        String teachingLevel,
        String employeeType,

        String phoneNumber,
        String alternatePhoneNumber,
        String mobileNumber,

        String emergencyContact,
        String emergencyRelation,

        String email,

        String addressLine1,
        String addressLine2,
        String addressLine3,
        String city,
        String state,
        String pincode,

        String panNumber,
        String biometricCard,
        String esiNumber,
        String aadharNumber,
        String pfNumber,

        String maritalStatus,
        String spouseName,
        String spouseGender,
        String spouseDob,

        String religion,
        String caste,

        String qualifiation,
        String universityBoard,
        String passingYear,
        String percentage,

        String companyName,
        String companyDesignation,
        String startDate,
        String endDate,
        String totalExperience,

        Boolean active,

        /*
         * Actual photo is NOT included.
         *
         * Frontend can use this URL to load the photo.
         */
        String photoUrl

) {}