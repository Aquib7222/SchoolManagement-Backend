// package com.schoolmanagement.schoolmanagementwebsite.dto.Assessment;

// import com.schoolmanagement.schoolmanagementwebsite.enums.Assessment.AssessmentNature;

// import lombok.Getter;
// import lombok.NoArgsConstructor;
// import lombok.Setter;
// import lombok.AllArgsConstructor;

// @Getter
// @Setter
// @NoArgsConstructor
// @AllArgsConstructor
// public class AssessmentTypeDTO {

//     private Long schoolId;

//     private String typeName;

//     private String shortCode;

//     private AssessmentNature nature;

//     private Long categoryId;

//     private Long examTermId;

//     private Integer maxMarks;

//     private Integer passingMarks;

//     private Double weightage;

//     private String description;

//     private Integer displayOrder;

//     private boolean status;
// }

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
public class AssessmentTypeDTO {

    private Long schoolId;

    private String typeName;

    private String shortCode;

    private AssessmentNature nature;

    private Long categoryId;

    private Long examTermId;

    private Integer maxMarks;

    private Integer passingMarks;

    private Double weightage;

    private String description;

    private Integer displayOrder;

    private boolean status;
}
