package com.schoolmanagement.schoolmanagementwebsite.dto.Assessment;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
public class AssessmentCategoryResponse {

    private Long id;

    private String categoryName;

    private String shortCode;

    private String natureName;
    private String natureDisplayName;
    private String natureShortCode;

    private String weightage;

    private String description;

    private boolean status;

    private Integer displayOrder;

 
  
}