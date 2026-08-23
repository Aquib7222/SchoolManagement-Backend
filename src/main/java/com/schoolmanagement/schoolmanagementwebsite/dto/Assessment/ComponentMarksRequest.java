package com.schoolmanagement.schoolmanagementwebsite.dto.Assessment;

import java.math.BigDecimal;

import lombok.*;

@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
public class ComponentMarksRequest {

    private Long componentId;

    private BigDecimal obtainedMarks;
}