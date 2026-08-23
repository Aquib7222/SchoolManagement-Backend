package com.schoolmanagement.schoolmanagementwebsite.dto.Assessment;

import java.util.List;

import lombok.*;

@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
public class StudentMarksRequest {

    private Long studentId;

    private List<ComponentMarksRequest> components;
}