package com.schoolmanagement.schoolmanagementwebsite.audit.repository;

import org.springframework.data.jpa.repository.JpaRepository;

import com.schoolmanagement.schoolmanagementwebsite.audit.entity.AuditLog;

public interface AuditLogRepository
        extends JpaRepository<AuditLog, Long> {
}