package com.schoolmanagement.schoolmanagementwebsite.entity;

import com.schoolmanagement.schoolmanagementwebsite.enums.NoticeAudience;
import com.schoolmanagement.schoolmanagementwebsite.enums.NoticeCategory;
import com.schoolmanagement.schoolmanagementwebsite.enums.NoticePriority;
import com.schoolmanagement.schoolmanagementwebsite.enums.NoticeStatus;

import jakarta.persistence.*;

import java.time.LocalDate;
import java.time.LocalDateTime;

@Entity
@Table(
    name = "notices",
    indexes = {
        @Index(
            name = "idx_notice_school",
            columnList = "school_id"
        ),
        @Index(
            name = "idx_notice_school_status",
            columnList = "school_id,status"
        ),
        @Index(
            name = "idx_notice_school_dates",
            columnList = "school_id,start_date,end_date"
        )
    }
)
public class Notice {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @Column(
        name = "school_id",
        nullable = false
    )
    private Long schoolId;

    @Column(
        nullable = false,
        length = 200
    )
    private String title;

    @Enumerated(EnumType.STRING)
    @Column(
        nullable = false,
        length = 50
    )
    private NoticeCategory category;

    @Enumerated(EnumType.STRING)
    @Column(
        nullable = false,
        length = 30
    )
    private NoticeAudience audience;

    @Enumerated(EnumType.STRING)
    @Column(
        nullable = false,
        length = 20
    )
    private NoticePriority priority;

    @Column(
        nullable = false,
        columnDefinition = "TEXT"
    )
    private String description;

    @Column(
        name = "start_date",
        nullable = false
    )
    private LocalDate startDate;

    @Column(
        name = "end_date",
        nullable = false
    )
    private LocalDate endDate;

    @Enumerated(EnumType.STRING)
    @Column(
        nullable = false,
        length = 20
    )
    private NoticeStatus status;

    @Column(
        nullable = false
    )
    private boolean pinned = false;

    @Column(
        name = "created_at",
        nullable = false
    )
    private LocalDateTime createdAt;

    @Column(
        name = "updated_at"
    )
    private LocalDateTime updatedAt;

    @PrePersist
    protected void onCreate() {

        LocalDateTime now =
            LocalDateTime.now();

        createdAt = now;
        updatedAt = now;
    }

    @PreUpdate
    protected void onUpdate() {

        updatedAt =
            LocalDateTime.now();
    }

    // =====================================================
    // GETTERS & SETTERS
    // =====================================================

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