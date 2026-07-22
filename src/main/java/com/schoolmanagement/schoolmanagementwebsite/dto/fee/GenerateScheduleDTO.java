package com.schoolmanagement.schoolmanagementwebsite.dto.fee;

import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
public class GenerateScheduleDTO {

    private String month;

    private Long feeMasterId;

    private String feeCode;

    private String feeName;

    private Double amount;

}