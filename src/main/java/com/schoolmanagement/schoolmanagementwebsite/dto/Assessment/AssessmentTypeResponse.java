// package com.schoolmanagement.schoolmanagementwebsite.dto.Assessment;

// import java.time.LocalDateTime;

// import lombok.AllArgsConstructor;
// import lombok.Getter;
// import lombok.NoArgsConstructor;
// import lombok.Setter;

// @Getter
// @Setter
// @NoArgsConstructor
// @AllArgsConstructor
// public class AssessmentTypeResponse {

//     private Long id;

//     private Long schoolId;

//     private String typeName;

//     private String shortCode;

//     private Long categoryId;
//     private String categoryName;

//     private String nature;
//     private String natureDisplayName;

//     private Long examTermId;
//     private String examTerm;

//     private Integer maxMarks;

//     private Integer passingMarks;

//     private Integer displayOrder;

//     private Double weightage;

//     private String description;

//     private boolean status;

//     private LocalDateTime createdAt;

//     private LocalDateTime updatedAt;
// }

package com.schoolmanagement.schoolmanagementwebsite.dto.Assessment;

import java.time.LocalDateTime;

import com.schoolmanagement.schoolmanagementwebsite.enums.Assessment.AssessmentNature;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
public class AssessmentTypeResponse {

    private Long id;

    private Long schoolId;

    private String typeName;

    private String shortCode;

    private Long categoryId;

    private String categoryName;

    private AssessmentNature nature;

    private String natureDisplayName;

    private Long examTermId;

    private String examTermName;

    private Integer maxMarks;

    private Integer passingMarks;

    private Integer displayOrder;

    private Double weightage;

    private String description;

    private boolean status;

    private LocalDateTime createdAt;

    private LocalDateTime updatedAt;
}

