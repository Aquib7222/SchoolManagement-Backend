package com.schoolmanagement.schoolmanagementwebsite.dto;



import com.schoolmanagement.schoolmanagementwebsite.enums.AttendanceStatus;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
@Builder
public class AttendanceItem {

    private Long studentId;

    private String admissionNumber;

    private AttendanceStatus status;

}
