package com.schoolmanagement.schoolmanagementwebsite.service.Assessment;

import java.time.LocalDateTime;
import java.util.List;

import org.springframework.stereotype.Service;

import com.schoolmanagement.schoolmanagementwebsite.dto.Assessment.ExamTermDTO;
import com.schoolmanagement.schoolmanagementwebsite.dto.Assessment.ExamTermResponse;
import com.schoolmanagement.schoolmanagementwebsite.entity.Assessment.ExamTerm;
import com.schoolmanagement.schoolmanagementwebsite.enums.Sessions;
import com.schoolmanagement.schoolmanagementwebsite.repository.Assessment.ExamTermRepository;

import lombok.RequiredArgsConstructor;

@Service
@RequiredArgsConstructor
public class ExamTermService {

    private final ExamTermRepository repository;

    // =========================================================
    // CREATE
    // =========================================================

    public ExamTermResponse save(ExamTermDTO dto) {

        // Duplicate Exam Term check
        if (repository.existsBySchoolIdAndSessionAndExamTermIgnoreCase(
                dto.getSchoolId(),
                dto.getSession(),
                dto.getExamTerm())) {

            throw new RuntimeException(
                    "Exam Term already exists for this session."
            );
        }

        // Duplicate Short Code check
        if (repository.existsBySchoolIdAndSessionAndShortCodeIgnoreCase(
                dto.getSchoolId(),
                dto.getSession(),
                dto.getShortCode())) {

            throw new RuntimeException(
                    "Short Code already exists for this session."
            );
        }

        ExamTerm examTerm = ExamTerm.builder()
                .schoolId(dto.getSchoolId())
                .examTerm(dto.getExamTerm())
                .shortCode(dto.getShortCode())
                .session(dto.getSession())
                .examTermType(dto.getExamTermType())
                .startDate(dto.getStartDate())
                .endDate(dto.getEndDate())
                .description(dto.getDescription())
                .displayOrder(dto.getDisplayOrder())
                .status(dto.isStatus())
                .createdAt(LocalDateTime.now())
                .updatedAt(LocalDateTime.now())
                .build();

        ExamTerm savedExamTerm = repository.save(examTerm);

        return mapToResponse(savedExamTerm);
    }


    // =========================================================
    // GET ALL
    // =========================================================

    public List<ExamTermResponse> getAll(
            Long schoolId,
            Sessions session) {

        return repository
                .findBySchoolIdAndSessionOrderByDisplayOrderAsc(
                        schoolId,
                        session
                )
                .stream()
                .map(this::mapToResponse)
                .toList();
    }


    // =========================================================
    // GET BY ID
    // =========================================================

    public ExamTermResponse getById(
            Long id,
            Long schoolId,
            Sessions session) {

        ExamTerm examTerm = repository
                .findByIdAndSchoolIdAndSession(
                        id,
                        schoolId,
                        session
                )
                .orElseThrow(() ->
                        new RuntimeException(
                                "Exam Term not found."
                        )
                );

        return mapToResponse(examTerm);
    }


    // =========================================================
    // UPDATE
    // =========================================================

    public ExamTermResponse update(
            Long id,
            ExamTermDTO dto) {

        ExamTerm examTerm = repository
                .findByIdAndSchoolIdAndSession(
                        id,
                        dto.getSchoolId(),
                        dto.getSession()
                )
                .orElseThrow(() ->
                        new RuntimeException(
                                "Exam Term not found."
                        )
                );


        // -----------------------------------------------------
        // Duplicate Exam Term check
        // -----------------------------------------------------

        if (!examTerm.getExamTerm()
                .equalsIgnoreCase(dto.getExamTerm())) {

            if (repository
                    .existsBySchoolIdAndSessionAndExamTermIgnoreCase(
                            dto.getSchoolId(),
                            dto.getSession(),
                            dto.getExamTerm())) {

                throw new RuntimeException(
                        "Exam Term already exists for this session."
                );
            }
        }


        // -----------------------------------------------------
        // Duplicate Short Code check
        // -----------------------------------------------------

        if (!examTerm.getShortCode()
                .equalsIgnoreCase(dto.getShortCode())) {

            if (repository
                    .existsBySchoolIdAndSessionAndShortCodeIgnoreCase(
                            dto.getSchoolId(),
                            dto.getSession(),
                            dto.getShortCode())) {

                throw new RuntimeException(
                        "Short Code already exists for this session."
                );
            }
        }


        // -----------------------------------------------------
        // Update fields
        // -----------------------------------------------------

        examTerm.setExamTerm(dto.getExamTerm());

        examTerm.setShortCode(dto.getShortCode());

        examTerm.setSession(dto.getSession());

        examTerm.setExamTermType(dto.getExamTermType());

        examTerm.setStartDate(dto.getStartDate());

        examTerm.setEndDate(dto.getEndDate());

        examTerm.setDescription(dto.getDescription());

        examTerm.setDisplayOrder(dto.getDisplayOrder());

        examTerm.setStatus(dto.isStatus());

        examTerm.setUpdatedAt(LocalDateTime.now());


        // -----------------------------------------------------
        // Save
        // -----------------------------------------------------

        ExamTerm updatedExamTerm = repository.save(examTerm);

        return mapToResponse(updatedExamTerm);
    }


    // =========================================================
    // DELETE
    // =========================================================

    public void delete(
            Long id,
            Long schoolId,
            Sessions session) {

        ExamTerm examTerm = repository
                .findByIdAndSchoolIdAndSession(
                        id,
                        schoolId,
                        session
                )
                .orElseThrow(() ->
                        new RuntimeException(
                                "Exam Term not found."
                        )
                );

        repository.delete(examTerm);
    }


    // =========================================================
    // ENTITY -> RESPONSE
    // =========================================================

    private ExamTermResponse mapToResponse(
            ExamTerm examTerm) {

        return new ExamTermResponse(
                examTerm.getId(),
                examTerm.getExamTerm(),
                examTerm.getShortCode(),
                examTerm.getSession(),
                examTerm.getExamTermType(),
                examTerm.getStartDate(),
                examTerm.getEndDate(),
                examTerm.getDescription(),
                examTerm.isStatus(),
                examTerm.getDisplayOrder(),
                examTerm.getCreatedAt()
        );
    }
}