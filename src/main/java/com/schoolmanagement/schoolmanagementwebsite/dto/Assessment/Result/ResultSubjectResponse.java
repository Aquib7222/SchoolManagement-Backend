package com.schoolmanagement.schoolmanagementwebsite.dto.Assessment.Result;

import java.math.BigDecimal;
import java.util.List;

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
public class ResultSubjectResponse {

    private Long id;

    private Long subjectId;

    private String subjectName;

    private BigDecimal totalMarks;

    private BigDecimal maxMarks;

    private BigDecimal percentage;

    private String grade;

    private BigDecimal gradePoint;

    private String remark;
    private String status;

    private List<ResultComponentResponse> components;
}