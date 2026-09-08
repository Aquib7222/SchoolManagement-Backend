package com.schoolmanagement.schoolmanagementwebsite.service;

import com.schoolmanagement.schoolmanagementwebsite.dto.NoticeRequest;
import com.schoolmanagement.schoolmanagementwebsite.dto.NoticeResponse;
import com.schoolmanagement.schoolmanagementwebsite.entity.Notice;
import com.schoolmanagement.schoolmanagementwebsite.enums.NoticeAudience;
import com.schoolmanagement.schoolmanagementwebsite.enums.NoticeStatus;
import com.schoolmanagement.schoolmanagementwebsite.repository.NoticeRepository;

import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.time.LocalDate;
import java.util.ArrayList;
import java.util.List;

@Service
@Transactional
public class NoticeService {

    private final NoticeRepository noticeRepository;

    public NoticeService(
        NoticeRepository noticeRepository
    ) {
        this.noticeRepository =
            noticeRepository;
    }

    // =====================================================
    // CREATE
    // =====================================================

    public NoticeResponse createNotice(
        Long schoolId,
        NoticeRequest request
    ) {

        validateDates(request);

        Notice notice =
            new Notice();

        notice.setSchoolId(
            schoolId
        );

        mapRequestToEntity(
            notice,
            request
        );

        Notice saved =
            noticeRepository.save(
                notice
            );

        return mapToResponse(saved);
    }

    // =====================================================
    // GET ALL
    // =====================================================

    @Transactional(readOnly = true)
    public List<NoticeResponse> getAllNotices(
        Long schoolId
    ) {

        return noticeRepository
            .findBySchoolIdOrderByPinnedDescCreatedAtDesc(
                schoolId
            )
            .stream()
            .map(this::mapToResponse)
            .toList();
    }

    // =====================================================
    // GET BY ID
    // =====================================================

    @Transactional(readOnly = true)
    public NoticeResponse getNoticeById(
        Long schoolId,
        Long id
    ) {

        Notice notice =
            getSchoolNotice(
                schoolId,
                id
            );

        return mapToResponse(notice);
    }

    // =====================================================
    // UPDATE
    // =====================================================

    public NoticeResponse updateNotice(
        Long schoolId,
        Long id,
        NoticeRequest request
    ) {

        validateDates(request);

        Notice notice =
            getSchoolNotice(
                schoolId,
                id
            );

        mapRequestToEntity(
            notice,
            request
        );

        Notice updated =
            noticeRepository.save(
                notice
            );

        return mapToResponse(
            updated
        );
    }

    // =====================================================
    // DELETE
    // =====================================================

    public void deleteNotice(
        Long schoolId,
        Long id
    ) {

        Notice notice =
            getSchoolNotice(
                schoolId,
                id
            );

        noticeRepository.delete(
            notice
        );
    }

    // =====================================================
    // TOGGLE PIN
    // =====================================================

    public NoticeResponse togglePin(
        Long schoolId,
        Long id
    ) {

        Notice notice =
            getSchoolNotice(
                schoolId,
                id
            );

        notice.setPinned(
            !notice.isPinned()
        );

        Notice saved =
            noticeRepository.save(
                notice
            );

        return mapToResponse(saved);
    }

    // =====================================================
    // DASHBOARD NOTICES
    // =====================================================

   @Transactional(readOnly = true)
public List<NoticeResponse> getDashboardNotices(
        Long schoolId,
        NoticeAudience audience
) {

    List<NoticeAudience> audiences = new ArrayList<>();

    // EVERYONE notices always visible
    audiences.add(NoticeAudience.EVERYONE);

    // Student ke liye STUDENT notices
    if (audience != null) {
        audiences.add(audience);
    }

    return noticeRepository
            .findBySchoolIdAndAudienceInAndStatusOrderByPinnedDescCreatedAtDesc(
                    schoolId,
                    audiences,
                    NoticeStatus.PUBLISHED
            )
            .stream()
            .map(this::mapToResponse)
            .toList();
}

    // =====================================================
    // PUBLISHED
    // =====================================================

    @Transactional(readOnly = true)
    public List<NoticeResponse> getPublishedNotices(
        Long schoolId
    ) {

        return noticeRepository
            .findBySchoolIdAndStatusOrderByPinnedDescCreatedAtDesc(
                schoolId,
                NoticeStatus.PUBLISHED
            )
            .stream()
            .map(this::mapToResponse)
            .toList();
    }

    // =====================================================
    // PRIVATE
    // =====================================================

    private Notice getSchoolNotice(
        Long schoolId,
        Long id
    ) {

        return noticeRepository
            .findById(id)
            .filter(
                notice ->
                    notice.getSchoolId()
                        .equals(schoolId)
            )
            .orElseThrow(
                () ->
                    new RuntimeException(
                        "Notice not found"
                    )
            );
    }

    private void validateDates(
        NoticeRequest request
    ) {

        if (
            request.getStartDate()
                .isAfter(
                    request.getEndDate()
                )
        ) {

            throw new IllegalArgumentException(
                "End date cannot be before start date"
            );
        }
    }

    private void mapRequestToEntity(
        Notice notice,
        NoticeRequest request
    ) {

        notice.setTitle(
            request.getTitle().trim()
        );

        notice.setCategory(
            request.getCategory()
        );

        notice.setAudience(
            request.getAudience()
        );

        notice.setPriority(
            request.getPriority()
        );

        notice.setDescription(
            request.getDescription().trim()
        );

        notice.setStartDate(
            request.getStartDate()
        );

        notice.setEndDate(
            request.getEndDate()
        );

        notice.setStatus(
            request.getStatus()
        );

        notice.setPinned(
            request.isPinned()
        );
    }

    private NoticeResponse mapToResponse(
        Notice notice
    ) {

        NoticeResponse response =
            new NoticeResponse();

        response.setId(
            notice.getId()
        );

        response.setSchoolId(
            notice.getSchoolId()
        );

        response.setTitle(
            notice.getTitle()
        );

        response.setCategory(
            notice.getCategory()
        );

        response.setAudience(
            notice.getAudience()
        );

        response.setPriority(
            notice.getPriority()
        );

        response.setDescription(
            notice.getDescription()
        );

        response.setStartDate(
            notice.getStartDate()
        );

        response.setEndDate(
            notice.getEndDate()
        );

        response.setStatus(
            notice.getStatus()
        );

        response.setPinned(
            notice.isPinned()
        );

        response.setCreatedAt(
            notice.getCreatedAt()
        );

        response.setUpdatedAt(
            notice.getUpdatedAt()
        );

        return response;
    }
}



