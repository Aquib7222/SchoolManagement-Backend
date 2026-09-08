package com.schoolmanagement.schoolmanagementwebsite.dto;

import com.schoolmanagement.schoolmanagementwebsite.enums.NoticeAudience;
import com.schoolmanagement.schoolmanagementwebsite.enums.NoticeCategory;
import com.schoolmanagement.schoolmanagementwebsite.enums.NoticePriority;
import com.schoolmanagement.schoolmanagementwebsite.enums.NoticeStatus;

import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;
import jakarta.validation.constraints.Size;


import java.time.LocalDate;

public class NoticeRequest {

    @NotBlank(
        message = "Notice title is required"
    )
    @Size(
        max = 200,
        message = "Title cannot exceed 200 characters"
    )
    private String title;

    @NotNull(
        message = "Category is required"
    )
    private NoticeCategory category;

    @NotNull(
        message = "Audience is required"
    )
    private NoticeAudience audience;

    @NotNull(
        message = "Priority is required"
    )
    private NoticePriority priority;

    @NotBlank(
        message = "Description is required"
    )
    private String description;

    @NotNull(
        message = "Start date is required"
    )
    private LocalDate startDate;

    @NotNull(
        message = "End date is required"
    )
    private LocalDate endDate;

    @NotNull(
        message = "Status is required"
    )
    private NoticeStatus status;

    private boolean pinned;

    // =====================================================
    // GETTERS & SETTERS
    // =====================================================

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
}