package com.schoolmanagement.schoolmanagementwebsite.dto.Assessment.SubjectManagement;


import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
@AllArgsConstructor
public class ClassSubjectMappingResponse {

    private Long id;

    private Long subjectId;

    private String subjectName;

    private String shortCode;

    private String subjectType;

    private String subjectCategory;

    private boolean status;
}