package com.schoolmanagement.schoolmanagementwebsite.audit.controller;


import lombok.RequiredArgsConstructor;

import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;

import org.springframework.http.ResponseEntity;

import org.springframework.web.bind.annotation.*;

import com.schoolmanagement.schoolmanagementwebsite.audit.dto.AuditLogResponse;
import com.schoolmanagement.schoolmanagementwebsite.audit.service.AuditLogService;

@RestController
@RequestMapping("/api/audit-logs")
@RequiredArgsConstructor
public class AuditLogController {

    private final AuditLogService auditLogService;


    @GetMapping
    public ResponseEntity<Page<AuditLogResponse>> getAllLogs(
            Pageable pageable
    ) {

        return ResponseEntity.ok(
                auditLogService.getAllLogs(
                        pageable
                )
        );
    }

    @GetMapping("/{id}")
    public ResponseEntity<AuditLogResponse> getAuditById(
            @PathVariable Long id) {

        AuditLogResponse response =
                auditLogService.getAuditById(id);

        return ResponseEntity.ok(response);
    }
}