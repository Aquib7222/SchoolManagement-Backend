package com.schoolmanagement.schoolmanagementwebsite.repository;

import com.schoolmanagement.schoolmanagementwebsite.entity.Notice;
import com.schoolmanagement.schoolmanagementwebsite.enums.NoticeAudience;
import com.schoolmanagement.schoolmanagementwebsite.enums.NoticeStatus;

import org.springframework.data.jpa.repository.JpaRepository;

import java.time.LocalDate;
import java.util.List;

public interface NoticeRepository
        extends JpaRepository<Notice, Long> {

    List<Notice> findBySchoolIdOrderByPinnedDescCreatedAtDesc(
        Long schoolId
    );

    List<Notice> findBySchoolIdAndStatusOrderByPinnedDescCreatedAtDesc(
        Long schoolId,
        NoticeStatus status
    );

    List<Notice>
    findBySchoolIdAndAudienceInAndStatusAndStartDateLessThanEqualAndEndDateGreaterThanEqualOrderByPinnedDescCreatedAtDesc(
        Long schoolId,
        List<NoticeAudience> audiences,
        NoticeStatus status,
        LocalDate startDate,
        LocalDate endDate
    );

    List<Notice> findBySchoolIdAndAudienceInAndStatusOrderByPinnedDescCreatedAtDesc(
        Long schoolId,
        List<NoticeAudience> audiences,
        NoticeStatus status
);

    List<Notice> findBySchoolIdAndPinnedTrueOrderByCreatedAtDesc(
        Long schoolId
    );
}