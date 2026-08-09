package com.schoolmanagement.schoolmanagementwebsite.dto.Assessment;

import com.schoolmanagement.schoolmanagementwebsite.enums.Assessment.AssessmentNature;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
public class AssessmentCategoryDTO {

    private Long schoolId;

    private String categoryName;

    private String shortCode;

    private AssessmentNature nature;

    private String weightage;

    private String description;

    private boolean status;

    private Integer displayOrder;

   
}