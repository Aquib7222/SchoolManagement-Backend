package com.schoolmanagement.schoolmanagementwebsite.dto;

import com.schoolmanagement.schoolmanagementwebsite.enums.NoticeAudience;
import com.schoolmanagement.schoolmanagementwebsite.enums.NoticeCategory;
import com.schoolmanagement.schoolmanagementwebsite.enums.NoticePriority;
import com.schoolmanagement.schoolmanagementwebsite.enums.NoticeStatus;

import java.time.LocalDate;
import java.time.LocalDateTime;

public class NoticeResponse {

    private Long id;

    private Long schoolId;

    private String title;

    private NoticeCategory category;

    private NoticeAudience audience;

    private NoticePriority priority;

    private String description;

    private LocalDate startDate;

    private LocalDate endDate;

    private NoticeStatus status;

    private boolean pinned;

    private LocalDateTime createdAt;

    private LocalDateTime updatedAt;

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public Long getSchoolId() {
        return schoolId;
    }

    public void setSchoolId(Long schoolId) {
        this.schoolId = schoolId;
    }

    public String getTitle() {
        return title;
    }

    public void setTitle(String title) {
        this.title = title;
    }

    public NoticeCategory getCategory() {
        return category;
    }

    public void setCategory(
        NoticeCategory category
    ) {
        this.category = category;
    }

    public NoticeAudience getAudience() {
        return audience;
    }

    public void setAudience(
        NoticeAudience audience
    ) {
        this.audience = audience;
    }

    public NoticePriority getPriority() {
        return priority;
    }

    public void setPriority(
        NoticePriority priority
    ) {
        this.priority = priority;
    }

    public String getDescription() {
        return description;
    }

    public void setDescription(
        String description
    ) {
        this.description = description;
    }

    public LocalDate getStartDate() {
        return startDate;
    }

    public void setStartDate(
        LocalDate startDate
    ) {
        this.startDate = startDate;
    }

    public LocalDate getEndDate() {
        return endDate;
    }

    public void setEndDate(
        LocalDate endDate
    ) {
        this.endDate = endDate;
    }

    public NoticeStatus getStatus() {
        return status;
    }

    public void setStatus(
        NoticeStatus status
    ) {
        this.status = status;
    }

    public boolean isPinned() {
        return pinned;
    }

    public void setPinned(
        boolean pinned
    ) {
        this.pinned = pinned;
    }

    public LocalDateTime getCreatedAt() {
        return createdAt;
    }

    public void setCreatedAt(
        LocalDateTime createdAt
    ) {
        this.createdAt = createdAt;
    }

    public LocalDateTime getUpdatedAt() {
        return updatedAt;
    }

    public void setUpdatedAt(
        LocalDateTime updatedAt
    ) {
        this.updatedAt = updatedAt;
    }
}