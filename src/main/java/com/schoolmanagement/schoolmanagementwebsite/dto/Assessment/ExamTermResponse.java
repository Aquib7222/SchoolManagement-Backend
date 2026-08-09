package com.schoolmanagement.schoolmanagementwebsite.dto.Assessment;

import java.time.LocalDateTime;

import com.schoolmanagement.schoolmanagementwebsite.enums.Assessment.ExamTermType;
import com.schoolmanagement.schoolmanagementwebsite.enums.Sessions;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
public class ExamTermResponse {
    
    private Long id;
    private String examTerm;
    private String shortCode;
    private Sessions session;
    private ExamTermType examTermType;
    private LocalDateTime startDate;
    private LocalDateTime endDate;
    private String description;
    private boolean status;
    private Integer displayOrder;
    private LocalDateTime createdAt;
}
