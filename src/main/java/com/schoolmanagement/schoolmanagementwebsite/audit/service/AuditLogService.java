package com.schoolmanagement.schoolmanagementwebsite.audit.service;




import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;

import com.schoolmanagement.schoolmanagementwebsite.audit.dto.AuditLogResponse;

public interface AuditLogService {

    Page<AuditLogResponse> getAllLogs(
            Pageable pageable
    );
}