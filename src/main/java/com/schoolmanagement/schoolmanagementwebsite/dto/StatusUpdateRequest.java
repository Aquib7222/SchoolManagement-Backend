package com.schoolmanagement.schoolmanagementwebsite.dto;

import com.schoolmanagement.schoolmanagementwebsite.enums.AdmissionStatus;

// DTO for status update
public record StatusUpdateRequest(AdmissionStatus status) {}

