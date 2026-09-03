package com.schoolmanagement.schoolmanagementwebsite.dto;

import java.util.List;

import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
public class StudentBulkImportRequest {

    private Long schoolId;

    private String academicYear;

    private String studentClass;

    private String section;

    private List<StudentImportItem> students;
}