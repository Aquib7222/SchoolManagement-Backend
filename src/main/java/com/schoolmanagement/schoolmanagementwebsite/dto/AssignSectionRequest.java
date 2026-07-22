package com.schoolmanagement.schoolmanagementwebsite.dto;

import java.util.List;

import com.schoolmanagement.schoolmanagementwebsite.enums.Section;

import lombok.Data;


    @Data
public class AssignSectionRequest {
    private List<Long> studentIds;
    private Section section;
}


