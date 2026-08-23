package com.schoolmanagement.schoolmanagementwebsite.audit.dto;


import lombok.*;

import java.time.LocalDateTime;

import com.schoolmanagement.schoolmanagementwebsite.audit.enums.AuditStatus;

@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
@Builder
public class AuditLogResponse {

    private Long id;

    private Long userId;

    private String username;

    private String role;

    private String action;

    private String module;

    private String targetType;

    private String targetId;

    private String description;

    private String requestMethod;

    private String requestUrl;

    private String ipAddress;

    private AuditStatus status;

    private LocalDateTime createdAt;
}