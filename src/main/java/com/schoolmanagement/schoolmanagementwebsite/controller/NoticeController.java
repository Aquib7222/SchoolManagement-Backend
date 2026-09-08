package com.schoolmanagement.schoolmanagementwebsite.controller;

import com.schoolmanagement.schoolmanagementwebsite.dto.NoticeRequest;
import com.schoolmanagement.schoolmanagementwebsite.dto.NoticeResponse;
import com.schoolmanagement.schoolmanagementwebsite.enums.NoticeAudience;
import com.schoolmanagement.schoolmanagementwebsite.service.NoticeService;

import jakarta.validation.Valid;

import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;

import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/api/notices")
@CrossOrigin
public class NoticeController {

    private final NoticeService noticeService;

    public NoticeController(
        NoticeService noticeService
    ) {
        this.noticeService =
            noticeService;
    }

    // =====================================================
    // CREATE
    // =====================================================

    @PostMapping
    public ResponseEntity<NoticeResponse> createNotice(
        @RequestParam Long schoolId,
        @Valid @RequestBody NoticeRequest request
    ) {

        NoticeResponse response =
            noticeService.createNotice(
                schoolId,
                request
            );

        return ResponseEntity
            .status(HttpStatus.CREATED)
            .body(response);
    }

    // =====================================================
    // GET ALL
    // =====================================================

    @GetMapping("/school")
    public ResponseEntity<List<NoticeResponse>> getAllNotices(
        @RequestParam Long schoolId
    ) {

        return ResponseEntity.ok(
            noticeService.getAllNotices(
                schoolId
            )
        );
    }

    // =====================================================
    // GET ONE
    // =====================================================

    @GetMapping("/{id}")
    public ResponseEntity<NoticeResponse> getNotice(
        @RequestParam Long schoolId,
        @PathVariable Long id
    ) {

        return ResponseEntity.ok(
            noticeService.getNoticeById(
                schoolId,
                id
            )
        );
    }

    // =====================================================
    // UPDATE
    // =====================================================

    @PutMapping("/{id}")
    public ResponseEntity<NoticeResponse> updateNotice(
        @RequestParam Long schoolId,
        @PathVariable Long id,
        @Valid @RequestBody NoticeRequest request
    ) {

        return ResponseEntity.ok(
            noticeService.updateNotice(
                schoolId,
                id,
                request
            )
        );
    }

    // =====================================================
    // DELETE
    // =====================================================

    @DeleteMapping("/{id}")
    public ResponseEntity<Void> deleteNotice(
        @RequestParam Long schoolId,
        @PathVariable Long id
    ) {

        noticeService.deleteNotice(
            schoolId,
            id
        );

        return ResponseEntity
            .noContent()
            .build();
    }

    // =====================================================
    // TOGGLE PIN
    // =====================================================

    @PatchMapping("/{id}/toggle-pin")
    public ResponseEntity<NoticeResponse> togglePin(
        @RequestParam Long schoolId,
        @PathVariable Long id
    ) {

        return ResponseEntity.ok(
            noticeService.togglePin(
                schoolId,
                id
            )
        );
    }

    // =====================================================
    // PUBLISHED
    // =====================================================

    @GetMapping("/published")
    public ResponseEntity<List<NoticeResponse>> getPublished(
        @RequestParam Long schoolId
    ) {

        return ResponseEntity.ok(
            noticeService.getPublishedNotices(
                schoolId
            )
        );
    }

    // =====================================================
    // DASHBOARD
    // =====================================================

    @GetMapping("/dashboard")
    public ResponseEntity<List<NoticeResponse>> getDashboardNotices(
        @RequestParam Long schoolId,
        @RequestParam NoticeAudience audience
    ) {

        return ResponseEntity.ok(
            noticeService.getDashboardNotices(
                schoolId,
                audience
            )
        );
    }
}