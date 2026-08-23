package com.schoolmanagement.schoolmanagementwebsite.service.Assessment;

import java.util.List;

import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import com.schoolmanagement.schoolmanagementwebsite.dto.Assessment.GradeRequest;
import com.schoolmanagement.schoolmanagementwebsite.dto.Assessment.GradeResponse;
import com.schoolmanagement.schoolmanagementwebsite.entity.Assessment.GradeMaster;
import com.schoolmanagement.schoolmanagementwebsite.entity.School;
import com.schoolmanagement.schoolmanagementwebsite.enums.Sessions;
import com.schoolmanagement.schoolmanagementwebsite.repository.Assessment.GradeMasterRepository;
import com.schoolmanagement.schoolmanagementwebsite.repository.SchoolRepository;

@Service
public class GradeMasterService {

    private final GradeMasterRepository gradeMasterRepository;

    private final SchoolRepository schoolRepository;

    public GradeMasterService(
            GradeMasterRepository gradeMasterRepository,
            SchoolRepository schoolRepository) {

        this.gradeMasterRepository = gradeMasterRepository;
        this.schoolRepository = schoolRepository;
    }

    // =====================================================
    // GET ALL
    // =====================================================

    public List<GradeResponse> getAll(Long schoolId) {

        return gradeMasterRepository
                .findBySchoolId(schoolId)
                .stream()
                .map(this::toResponse)
                .toList();
    }

    // =====================================================
    // GET BY SESSION
    // =====================================================

    public List<GradeResponse> getBySession(
            Long schoolId,
            Sessions session) {

        return gradeMasterRepository
                .findBySchoolIdAndSession(
                        schoolId,
                        session)
                .stream()
                .map(this::toResponse)
                .toList();
    }

    // =====================================================
    // GET ACTIVE GRADES FOR MARK CALCULATION
    // =====================================================

    public List<GradeResponse> getActiveGrades(
            Long schoolId,
            Sessions session) {

        return gradeMasterRepository
                .findBySchoolIdAndSessionAndStatusTrueOrderByMinPercentageDesc(
                        schoolId,
                        session)
                .stream()
                .map(this::toResponse)
                .toList();
    }

    // =====================================================
    // SAVE
    // =====================================================

    @Transactional
    public GradeResponse save(GradeRequest request) {

        validate(request);

        School school = schoolRepository
                .findById(request.getSchoolId())
                .orElseThrow(() ->
                        new RuntimeException(
                                "School not found"));

        boolean exists =
                gradeMasterRepository
                        .findBySchoolIdAndSessionAndGrade(
                                request.getSchoolId(),
                                request.getSession(),
                                request.getGrade())
                        .isPresent();

        if (exists) {
            throw new RuntimeException(
                    "Grade already exists for this session");
        }

        GradeMaster grade = new GradeMaster();

        grade.setSchool(school);
        grade.setSession(request.getSession());
        grade.setGrade(
                request.getGrade()
                        .trim()
                        .toUpperCase());

        grade.setMinPercentage(
                request.getMinPercentage());

        grade.setMaxPercentage(
                request.getMaxPercentage());

        grade.setGradePoint(
                request.getGradePoint());
        
        grade.setRemarks(request.getRemarks());

        grade.setDescription(
                request.getDescription());

        grade.setStatus(
                request.getStatus() != null
                        ? request.getStatus()
                        : true);

        return toResponse(
                gradeMasterRepository.save(grade));
    }

    // =====================================================
    // UPDATE
    // =====================================================

    @Transactional
    public GradeResponse update(
            Long id,
            GradeRequest request) {

        validate(request);

        GradeMaster grade =
                gradeMasterRepository
                        .findByIdAndSchoolId(
                                id,
                                request.getSchoolId())
                        .orElseThrow(() ->
                                new RuntimeException(
                                        "Grade not found"));

        gradeMasterRepository
                .findBySchoolIdAndSessionAndGrade(
                        request.getSchoolId(),
                        request.getSession(),
                        request.getGrade())
                .ifPresent(existing -> {

                    if (!existing.getId().equals(id)) {

                        throw new RuntimeException(
                                "Grade already exists for this session");
                    }
                });

        grade.setSession(request.getSession());

        grade.setGrade(
                request.getGrade()
                        .trim()
                        .toUpperCase());

        grade.setMinPercentage(
                request.getMinPercentage());

        grade.setMaxPercentage(
                request.getMaxPercentage());

        grade.setGradePoint(
                request.getGradePoint());
        
        grade.setRemarks(request.getRemarks());

        grade.setDescription(
                request.getDescription());

        grade.setStatus(
                request.getStatus() != null
                        ? request.getStatus()
                        : true);

        return toResponse(
                gradeMasterRepository.save(grade));
    }

    // =====================================================
    // DELETE
    // =====================================================

    @Transactional
    public void delete(
            Long id,
            Long schoolId) {

        GradeMaster grade =
                gradeMasterRepository
                        .findByIdAndSchoolId(
                                id,
                                schoolId)
                        .orElseThrow(() ->
                                new RuntimeException(
                                        "Grade not found"));

        gradeMasterRepository.delete(grade);
    }

    // =====================================================
    // VALIDATION
    // =====================================================

    private void validate(
            GradeRequest request) {

        if (request.getSchoolId() == null) {
            throw new RuntimeException(
                    "School ID is required");
        }

        if (request.getSession() == null) {
            throw new RuntimeException(
                    "Session is required");
        }

        if (request.getGrade() == null
                || request.getGrade().trim().isEmpty()) {

            throw new RuntimeException(
                    "Grade is required");
        }

        if (request.getMinPercentage() == null
                || request.getMaxPercentage() == null) {

            throw new RuntimeException(
                    "Minimum and maximum percentage are required");
        }

        if (request.getMinPercentage() < 0
                || request.getMinPercentage() > 100) {

            throw new RuntimeException(
                    "Minimum percentage must be between 0 and 100");
        }

        if (request.getMaxPercentage() < 0
                || request.getMaxPercentage() > 100) {

            throw new RuntimeException(
                    "Maximum percentage must be between 0 and 100");
        }

        if (request.getMinPercentage()
                > request.getMaxPercentage()) {

            throw new RuntimeException(
                    "Minimum percentage cannot be greater than maximum percentage");
        }

        if (request.getGradePoint() != null
                && request.getGradePoint() < 0) {

            throw new RuntimeException(
                    "Grade point cannot be negative");
        }
    }

    // =====================================================
    // RESPONSE MAPPER
    // =====================================================

    private GradeResponse toResponse(
            GradeMaster grade) {

        return new GradeResponse(
                grade.getId(),
                grade.getSchool().getId(),
                grade.getSession().getValue(),
                grade.getGrade(),
                grade.getMinPercentage(),
                grade.getMaxPercentage(),
                grade.getGradePoint(),
                grade.getRemarks(),
                grade.getDescription(),
                grade.getStatus());
    }
}