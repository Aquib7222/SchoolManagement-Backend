package com.schoolmanagement.schoolmanagementwebsite.dto.Assessment.Result;

import java.math.BigDecimal;

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
public class ResultComponentResponse {

    private Long id;

    private Long componentId;

    private String componentName;

    private BigDecimal maxMarks;

    private BigDecimal obtainedMarks;

    private BigDecimal percentage;

    private String grade;

    private BigDecimal gradePoint;

    private String status;
}