package com.schoolmanagement.schoolmanagementwebsite.dto.Assessment.SubjectManagement;

import java.util.List;

import lombok.Getter;
import lombok.Setter;


@Getter
@Setter
public class ClassSubjectMappingRequest {

    private Long schoolId;

    private String academicYear;

    private String studentClass;

    private List<Long> subjectIds;
}