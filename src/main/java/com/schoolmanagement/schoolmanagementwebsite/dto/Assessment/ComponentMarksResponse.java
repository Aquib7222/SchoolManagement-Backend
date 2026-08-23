package com.schoolmanagement.schoolmanagementwebsite.dto.Assessment;

import java.math.BigDecimal;

import lombok.*;

@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
@Builder
public class ComponentMarksResponse {

    private Long componentId;

    private String componentName;

    private Double maxMarks;

    private Double passingMarks;

    private BigDecimal obtainedMarks;
}