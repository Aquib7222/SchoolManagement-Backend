package com.schoolmanagement.schoolmanagementwebsite.service.Assessment.SubjectManagement;


import java.util.ArrayList;
import java.util.List;

import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import com.schoolmanagement.schoolmanagementwebsite.dto.Assessment.SubjectManagement.ClassSubjectMappingRequest;
import com.schoolmanagement.schoolmanagementwebsite.dto.Assessment.SubjectManagement.ClassSubjectMappingResponse;
import com.schoolmanagement.schoolmanagementwebsite.entity.Assessment.SubjectManagement.ClassSubjectMapping;
import com.schoolmanagement.schoolmanagementwebsite.repository.Assessment.SubjectManagement.ClassSubjectMappingRepository;

import lombok.RequiredArgsConstructor;

@Service
@RequiredArgsConstructor
public class ClassSubjectMappingService {

    private final ClassSubjectMappingRepository mappingRepository;


    // ============================
    // MAP SUBJECTS
    // ============================

    @Transactional
    public List<ClassSubjectMapping> mapSubjects(
            ClassSubjectMappingRequest request) {

        if (request.getSchoolId() == null) {
            throw new RuntimeException("School ID is required");
        }

        if (request.getAcademicYear() == null ||
                request.getAcademicYear().isBlank()) {

            throw new RuntimeException("Academic year is required");
        }

        if (request.getStudentClass() == null ||
                request.getStudentClass().isBlank()) {

            throw new RuntimeException("Class is required");
        }

        if (request.getSubjectIds() == null ||
                request.getSubjectIds().isEmpty()) {

            throw new RuntimeException(
                    "Please select at least one subject"
            );
        }

        List<ClassSubjectMapping> mappings = new ArrayList<>();

        for (Long subjectId : request.getSubjectIds()) {

            boolean exists =
                    mappingRepository
                            .existsBySchoolIdAndAcademicYearAndStudentClassAndSubjectId(
                                    request.getSchoolId(),
                                    request.getAcademicYear(),
                                    request.getStudentClass(),
                                    subjectId
                            );

            if (!exists) {

                ClassSubjectMapping mapping =
                        new ClassSubjectMapping();

                mapping.setSchoolId(
                        request.getSchoolId()
                );

                mapping.setAcademicYear(
                        request.getAcademicYear()
                );

                mapping.setStudentClass(
                        request.getStudentClass()
                );

                mapping.setSubjectId(subjectId);

                mapping.setStatus(true);

                mappings.add(mapping);
            }
        }

        return mappingRepository.saveAll(mappings);
    }


    // ============================
    // GET MAPPED SUBJECTS
    // ============================

    public List<ClassSubjectMappingResponse> getMappedSubjects(
            Long schoolId,
            String academicYear,
            String studentClass) {

        return mappingRepository.findMappedSubjects(
                schoolId,
                academicYear,
                studentClass
        );
    }
}