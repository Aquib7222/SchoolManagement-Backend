package com.schoolmanagement.schoolmanagementwebsite.audit.entity;




import jakarta.persistence.*;
import lombok.*;

import java.time.LocalDateTime;

import com.schoolmanagement.schoolmanagementwebsite.audit.enums.AuditStatus;

@Entity
@Table(name = "audit_logs")
@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
@Builder
public class AuditLog {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    // User who performed the action
    private Long userId;

    private String username;

    private String role;

    // Action details
    private String action;

    private String module;

    // Target details
    private String targetType;

    private String targetId;

    private String description;

    // Request details
    private String requestMethod;

    private String requestUrl;

    private String ipAddress;

    // SUCCESS / FAILED
    @Enumerated(EnumType.STRING)
    private AuditStatus status;

    private LocalDateTime createdAt;
}
