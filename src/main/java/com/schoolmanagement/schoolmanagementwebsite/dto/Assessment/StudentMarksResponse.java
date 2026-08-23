package com.schoolmanagement.schoolmanagementwebsite.dto.Assessment;

import java.math.BigDecimal;
import java.util.List;

import lombok.*;

@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
@Builder
public class StudentMarksResponse {

    private Long studentId;

    private String admissionNumber;

    private String studentName;

    private List<ComponentMarksResponse> components;

    private BigDecimal totalMarks;

    private BigDecimal percentage;

    private String grade;

    private BigDecimal gradePoint;

    private String remark;
}