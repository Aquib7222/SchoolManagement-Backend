package com.schoolmanagement.schoolmanagementwebsite.dto.Assessment;

import java.util.List;

import lombok.*;

@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
@Builder
public class MarksEntryResponse {

    private Long assessmentId;

    private Long subjectId;

private String subjectName;

    private String status;

    private Double totalMaxMarks;

    private List<StudentMarksResponse> students;
}