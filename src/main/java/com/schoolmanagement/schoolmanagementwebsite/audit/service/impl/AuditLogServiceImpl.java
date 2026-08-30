package com.schoolmanagement.schoolmanagementwebsite.audit.service.impl;




import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;

import com.schoolmanagement.schoolmanagementwebsite.audit.dto.AuditLogResponse;
import com.schoolmanagement.schoolmanagementwebsite.audit.entity.AuditLog;
import com.schoolmanagement.schoolmanagementwebsite.audit.repository.AuditLogRepository;
import com.schoolmanagement.schoolmanagementwebsite.audit.service.AuditLogService;
import com.schoolmanagement.schoolmanagementwebsite.entity.School;
import com.schoolmanagement.schoolmanagementwebsite.repository.SchoolRepository;

import lombok.RequiredArgsConstructor;

@Service
@RequiredArgsConstructor
public class AuditLogServiceImpl
        implements AuditLogService {

    private final AuditLogRepository auditLogRepository;
    private final SchoolRepository schoolRepository;

    public AuditLogResponse getAuditById(Long auditId) {

        AuditLog auditLog =
                auditLogRepository.findById(auditId)
                        .orElseThrow(() ->
                                new RuntimeException(
                                        "Audit log not found"
                                )
                        );

        String targetName = null;

        // ==========================================
        // SCHOOL
        // ==========================================

        if ("SCHOOL".equalsIgnoreCase(
                auditLog.getTargetType())) {

            if (auditLog.getTargetId() != null
                    && !auditLog.getTargetId().isBlank()) {

                Long schoolId =
                        Long.valueOf(
                                auditLog.getTargetId()
                        );

                School school =
                        schoolRepository.findById(schoolId)
                                .orElse(null);

                if (school != null) {

                    targetName =
                            school.getSchoolName();
                }
            }
        }

        // ==========================================
        // RESPONSE
        // ==========================================

        return AuditLogResponse.builder()

                .id(auditLog.getId())

                .action(
                        auditLog.getAction()
                )

                .module(
                        auditLog.getModule()
                )

                .targetType(
                        auditLog.getTargetType()
                )

                .targetId(
                        auditLog.getTargetId()
                )

                .targetName(
                        targetName
                )

                .description(
                        auditLog.getDescription()
                )

                .username(
                        auditLog.getUsername()
                )

                .role(
                        auditLog.getRole()
                )

                .status(
                        auditLog.getStatus() != null
                                ? auditLog.getStatus()
                                : null
                )

                .createdAt(
                        auditLog.getCreatedAt()
                )

                .build();
    }


    @Override
public Page<AuditLogResponse> getAllLogs(Pageable pageable) {

    return auditLogRepository
            .findAll(pageable)
            .map(log -> {

                String targetName = null;

                // =========================================
                // SCHOOL
                // =========================================

                if ("SCHOOL".equalsIgnoreCase(log.getTargetType())
                        && log.getTargetId() != null
                        && !log.getTargetId().isBlank()) {

                    try {

                        Long schoolId =
                                Long.valueOf(log.getTargetId());

                        School school =
                                schoolRepository
                                        .findById(schoolId)
                                        .orElse(null);

                        if (school != null) {

                            targetName =
                                    school.getSchoolName();

                            System.out.println(
                                    "Audit School Found: "
                                    + school.getId()
                                    + " -> "
                                    + school.getSchoolName()
                            );

                        } else {

                            System.out.println(
                                    "School NOT FOUND for audit targetId: "
                                    + schoolId
                            );
                        }

                    } catch (NumberFormatException e) {

                        System.out.println(
                                "Invalid targetId: "
                                + log.getTargetId()
                        );
                    }
                }

                return AuditLogResponse.builder()

                        .id(log.getId())

                        .userId(log.getUserId())

                        .username(log.getUsername())

                        .role(log.getRole())

                        .action(log.getAction())

                        .module(log.getModule())

                        .targetType(log.getTargetType())

                        .targetId(log.getTargetId())

                        .targetName(targetName)

                        .description(log.getDescription())

                        .details(log.getDetails())

                        .requestMethod(log.getRequestMethod())

                        .requestUrl(log.getRequestUrl())

                        .ipAddress(log.getIpAddress())

                        .status(log.getStatus())

                        .createdAt(log.getCreatedAt())

                        .build();
            });
}
}