package com.schoolmanagement.schoolmanagementwebsite.dto.Assessment.SubjectManagement;

import com.schoolmanagement.schoolmanagementwebsite.enums.Assessment.SubjectType;

import com.schoolmanagement.schoolmanagementwebsite.enums.Assessment.SubjectCategory;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
public class SubjectMasterDTO {

    private Long schoolId;

    private String subjectName;

    private String shortCode;

    private SubjectType subjectType;

    private SubjectCategory subjectCategory;

    private String displayOrder;

    private boolean status;
    
}
