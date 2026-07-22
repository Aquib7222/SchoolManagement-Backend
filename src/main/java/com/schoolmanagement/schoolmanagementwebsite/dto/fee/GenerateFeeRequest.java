package com.schoolmanagement.schoolmanagementwebsite.dto.fee;

import java.util.List;

import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
public class GenerateFeeRequest {

    private String admissionNumber;

    // private List<String> months;

    private List<GenerateScheduleDTO> schedules;

}