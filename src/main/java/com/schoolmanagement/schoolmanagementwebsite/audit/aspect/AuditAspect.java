package com.schoolmanagement.schoolmanagementwebsite.audit.aspect;
import jakarta.servlet.http.HttpServletRequest;

import lombok.RequiredArgsConstructor;

import org.aspectj.lang.ProceedingJoinPoint;
import org.aspectj.lang.annotation.Around;
import org.aspectj.lang.annotation.Aspect;

import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;

import org.springframework.stereotype.Component;

import com.schoolmanagement.schoolmanagementwebsite.audit.annotation.Audit;
import com.schoolmanagement.schoolmanagementwebsite.audit.entity.AuditLog;
import com.schoolmanagement.schoolmanagementwebsite.audit.enums.AuditStatus;
import com.schoolmanagement.schoolmanagementwebsite.audit.repository.AuditLogRepository;

import java.time.LocalDateTime;

@Aspect
@Component
@RequiredArgsConstructor
public class AuditAspect {

    private final AuditLogRepository auditLogRepository;

    private final HttpServletRequest request;


    @Around("@annotation(audit)")
    public Object auditMethod(
            ProceedingJoinPoint joinPoint,
            Audit audit
    ) throws Throwable {

        try {

            // First execute the actual method
            Object result = joinPoint.proceed();

            // If method succeeds
            saveAuditLog(
                    audit,
                    AuditStatus.SUCCESS,
                    null
            );

            return result;

        } catch (Exception exception) {

            // If method fails
            saveAuditLog(
                    audit,
                    AuditStatus.FAILED,
                    exception.getMessage()
            );

            // Important:
            // original exception must continue
            throw exception;
        }
    }


    private void saveAuditLog(
            Audit audit,
            AuditStatus status,
            String errorMessage
    ) {

        Authentication authentication =
                SecurityContextHolder
                        .getContext()
                        .getAuthentication();


        String username = null;

        String role = null;

        Long userId = null;


        /*
         * Get logged-in user
         */
        if (authentication != null
                && authentication.isAuthenticated()) {

            username = authentication.getName();

            role = authentication
                    .getAuthorities()
                    .stream()
                    .findFirst()
                    .map(authority ->
                            authority.getAuthority()
                    )
                    .orElse(null);
        }


        /*
         * Description
         */
        String description =
                audit.description();


        if (errorMessage != null
                && !errorMessage.isBlank()) {

            description =
                    description
                            + " | Error: "
                            + errorMessage;
        }


        /*
         * Create Audit Log
         */
        AuditLog log = AuditLog.builder()

                .userId(userId)

                .username(username)

                .role(role)

                .action(
                        audit.action().name()
                )

                .module(
                        audit.module()
                )

                .targetType(
                        audit.targetType()
                )

                .targetId(
                        audit.targetId()
                )

                .description(
                        description
                )

                .requestMethod(
                        request.getMethod()
                )

                .requestUrl(
                        request.getRequestURI()
                )

                .ipAddress(
                        getClientIp()
                )

                .status(status)

                .createdAt(
                        LocalDateTime.now()
                )

                .build();


        auditLogRepository.save(log);
    }


    /*
     * Get real client IP
     */
    private String getClientIp() {

        String ip =
                request.getHeader(
                        "X-Forwarded-For"
                );

        if (ip != null
                && !ip.isBlank()) {

            return ip.split(",")[0].trim();
        }


        ip =
                request.getHeader(
                        "X-Real-IP"
                );

        if (ip != null
                && !ip.isBlank()) {

            return ip;
        }


        return request.getRemoteAddr();
    }
}