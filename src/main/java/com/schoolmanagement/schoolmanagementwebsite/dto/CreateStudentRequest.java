package com.schoolmanagement.schoolmanagementwebsite.dto;

import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
public class CreateStudentRequest {

    private Long admissionId;
    private String username; // student login email
}
