package com.schoolmanagement.schoolmanagementwebsite.dto.Assessment.Result;

import java.math.BigDecimal;
import java.time.LocalDateTime;
import java.util.List;

import com.schoolmanagement.schoolmanagementwebsite.enums.Assessment.ResultStatus;
import com.schoolmanagement.schoolmanagementwebsite.enums.Section;
import com.schoolmanagement.schoolmanagementwebsite.enums.Sessions;
import com.schoolmanagement.schoolmanagementwebsite.enums.Standard;

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
public class ResultResponse {

    private Long id;

    private Long schoolId;

    private Long studentId;

    private String admissionNumber;

    private String studentName;

    private Sessions session;

    private Long examTermId;

    private Standard studentClass;

    private Section section;

    private BigDecimal totalMarks;

    private BigDecimal totalMaxMarks;

    private BigDecimal percentage;

    private String grade;

    private BigDecimal gradePoint;

    private String remark;

    private Integer rank;

    private ResultStatus status;

    private LocalDateTime publishedAt;

    private LocalDateTime createdAt;

    private LocalDateTime updatedAt;

    private List<ResultSubjectResponse> subjects;

    private List<ResultComponentResponse> components;
}