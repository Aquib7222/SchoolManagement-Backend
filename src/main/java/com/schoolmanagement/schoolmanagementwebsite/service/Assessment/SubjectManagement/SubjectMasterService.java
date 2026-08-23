package com.schoolmanagement.schoolmanagementwebsite.service.Assessment.SubjectManagement;

import java.util.List;

import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import com.schoolmanagement.schoolmanagementwebsite.entity.Assessment.SubjectManagement.SubjectMaster;
import com.schoolmanagement.schoolmanagementwebsite.repository.Assessment.SubjectManagement.SubjectMasterRepository;

import lombok.RequiredArgsConstructor;

@Service
@RequiredArgsConstructor
public class SubjectMasterService {

    private final SubjectMasterRepository subjectMasterRepository;


    // =====================================================
    // CREATE
    // =====================================================

    @Transactional
    public SubjectMaster save(SubjectMaster subject) {

        if (subject.getSchoolId() == null) {
    throw new RuntimeException("School ID is required.");
}

        if (subject.getSubjectName() == null ||
                subject.getSubjectName().isBlank()) {

            throw new RuntimeException("Subject name is required.");
        }

        if (subject.getShortCode() == null ||
                subject.getShortCode().isBlank()) {

            throw new RuntimeException("Subject short code is required.");
        }


        // Duplicate subject name
        if (subjectMasterRepository
                .existsBySchoolIdAndSubjectNameIgnoreCase(
                        subject.getSchoolId(),
                        subject.getSubjectName())) {

            throw new RuntimeException(
                    "Subject name already exists."
            );
        }


        // Duplicate short code
        if (subjectMasterRepository
                .existsBySchoolIdAndShortCodeIgnoreCase(
                        subject.getSchoolId(),
                        subject.getShortCode())) {

            throw new RuntimeException(
                    "Subject short code already exists."
            );
        }


        // Short code uppercase
        subject.setShortCode(
                subject.getShortCode().toUpperCase()
        );

        return subjectMasterRepository.save(subject);
    }


    // =====================================================
    // GET ALL
    // =====================================================

    public List<SubjectMaster> getAll(Long schoolId) {

        if (schoolId == null) {
            throw new RuntimeException("School ID is required.");
        }

        return subjectMasterRepository
                .findBySchoolIdOrderByDisplayOrderAsc(schoolId);
    }


    // =====================================================
    // GET BY ID
    // =====================================================

    public SubjectMaster getById(
            Long id,
            Long schoolId
    ) {

        return subjectMasterRepository
                .findByIdAndSchoolId(id, schoolId)
                .orElseThrow(() ->
                        new RuntimeException(
                                "Subject not found."
                        )
                );
    }


    // =====================================================
    // UPDATE
    // =====================================================

    @Transactional
    public SubjectMaster update(
            Long id,
            SubjectMaster request
    ) {

        SubjectMaster existing =
                subjectMasterRepository
                        .findByIdAndSchoolId(
                                id,
                                request.getSchoolId()
                        )
                        .orElseThrow(() ->
                                new RuntimeException(
                                        "Subject not found."
                                )
                        );


        // Check subject name if changed
        if (!existing.getSubjectName()
                .equalsIgnoreCase(request.getSubjectName())) {

            if (subjectMasterRepository
                    .existsBySchoolIdAndSubjectNameIgnoreCase(
                            request.getSchoolId(),
                            request.getSubjectName())) {

                throw new RuntimeException(
                        "Subject name already exists."
                );
            }
        }


        // Check short code if changed
        if (!existing.getShortCode()
                .equalsIgnoreCase(request.getShortCode())) {

            if (subjectMasterRepository
                    .existsBySchoolIdAndShortCodeIgnoreCase(
                            request.getSchoolId(),
                            request.getShortCode())) {

                throw new RuntimeException(
                        "Subject short code already exists."
                );
            }
        }


        existing.setSubjectName(
                request.getSubjectName()
        );

        existing.setShortCode(
                request.getShortCode().toUpperCase()
        );

        existing.setSubjectType(
                request.getSubjectType()
        );

        existing.setSubjectCategory(
                request.getSubjectCategory()
        );

        existing.setDisplayOrder(
                request.getDisplayOrder()
        );

        existing.setStatus(
                request.isStatus()
        );


        return subjectMasterRepository.save(existing);
    }


    // =====================================================
    // DELETE
    // =====================================================

    public void delete(
        Long id,
        Long schoolId
) {

    SubjectMaster subject =
            subjectMasterRepository
                    .findByIdAndSchoolId(
                            id,
                            schoolId
                    )
                    .orElseThrow(() ->
                            new RuntimeException(
                                    "Subject not found."
                            )
                    );

    subjectMasterRepository.delete(subject);
}
}