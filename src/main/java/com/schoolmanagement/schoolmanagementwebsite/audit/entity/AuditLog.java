package com.schoolmanagement.schoolmanagementwebsite.audit.entity;




import java.time.LocalDateTime;

import com.schoolmanagement.schoolmanagementwebsite.audit.enums.AuditStatus;

import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.EnumType;
import jakarta.persistence.Enumerated;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.persistence.Table;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

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

    private String targetName;

    private String description;

    // Request details
    private String requestMethod;

    private String requestUrl;

    private String ipAddress;

    @Column(columnDefinition = "TEXT")
private String details;

    // SUCCESS / FAILED
    @Enumerated(EnumType.STRING)
    private AuditStatus status;

    private LocalDateTime createdAt;
}
