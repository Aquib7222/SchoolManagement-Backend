package com.schoolmanagement.schoolmanagementwebsite.audit.service.impl;




import lombok.RequiredArgsConstructor;

import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;

import org.springframework.stereotype.Service;

import com.schoolmanagement.schoolmanagementwebsite.audit.dto.AuditLogResponse;
import com.schoolmanagement.schoolmanagementwebsite.audit.repository.AuditLogRepository;
import com.schoolmanagement.schoolmanagementwebsite.audit.service.AuditLogService;

@Service
@RequiredArgsConstructor
public class AuditLogServiceImpl
        implements AuditLogService {

    private final AuditLogRepository auditLogRepository;


    @Override
    public Page<AuditLogResponse> getAllLogs(
            Pageable pageable
    ) {

        return auditLogRepository
                .findAll(pageable)
                .map(log ->
                        AuditLogResponse.builder()

                                .id(log.getId())

                                .userId(
                                        log.getUserId()
                                )

                                .username(
                                        log.getUsername()
                                )

                                .role(
                                        log.getRole()
                                )

                                .action(
                                        log.getAction()
                                )

                                .module(
                                        log.getModule()
                                )

                                .targetType(
                                        log.getTargetType()
                                )

                                .targetId(
                                        log.getTargetId()
                                )

                                .description(
                                        log.getDescription()
                                )

                                .requestMethod(
                                        log.getRequestMethod()
                                )

                                .requestUrl(
                                        log.getRequestUrl()
                                )

                                .ipAddress(
                                        log.getIpAddress()
                                )

                                .status(
                                        log.getStatus()
                                )

                                .createdAt(
                                        log.getCreatedAt()
                                )

                                .build()
                );
    }
}