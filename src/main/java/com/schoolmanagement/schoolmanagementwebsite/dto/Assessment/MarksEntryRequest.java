package com.schoolmanagement.schoolmanagementwebsite.dto.Assessment;

import java.util.List;

import com.schoolmanagement.schoolmanagementwebsite.enums.Section;
import com.schoolmanagement.schoolmanagementwebsite.enums.Sessions;
import com.schoolmanagement.schoolmanagementwebsite.enums.Standard;

import lombok.*;

@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
public class MarksEntryRequest {

    private Long schoolId;

    private Sessions session;

    private Long examTermId;

    private Standard studentClass;

    private Section section;

    private Long subjectId;

    private List<StudentMarksRequest> students;
}