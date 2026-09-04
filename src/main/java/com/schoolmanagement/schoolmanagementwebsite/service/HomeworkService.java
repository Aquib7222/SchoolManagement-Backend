package com.schoolmanagement.schoolmanagementwebsite.service;

import com.schoolmanagement.schoolmanagementwebsite.entity.Homework;
import com.schoolmanagement.schoolmanagementwebsite.enums.HomeworkType;
import com.schoolmanagement.schoolmanagementwebsite.repository.HomeworkRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.web.multipart.MultipartFile;

import java.io.IOException;
import java.nio.file.*;
import java.time.LocalDate;
import java.util.List;
import java.util.UUID;

@Service
@RequiredArgsConstructor
public class HomeworkService {

    private final HomeworkRepository homeworkRepository;

    private final Path uploadDirectory =
            Paths.get("uploads/homework");

    // =========================================================
    // ADD HOMEWORK
    // =========================================================

    public Homework addHomework(
            Long schoolId,
            Long teacherId,
            String academicYear,
            String studentClass,
            String section,
            String subject,
            LocalDate homeworkDate,
            LocalDate submissionDate,
            HomeworkType homeworkType,
            String homeworkText,
            MultipartFile image
    ) throws IOException {

        // -----------------------------------------------------
        // BASIC VALIDATION
        // -----------------------------------------------------

        if (schoolId == null) {
            throw new RuntimeException("School ID is required.");
        }

        if (teacherId == null) {
            throw new RuntimeException("Teacher ID is required.");
        }

        if (academicYear == null || academicYear.isBlank()) {
            throw new RuntimeException("Academic year is required.");
        }

        if (studentClass == null || studentClass.isBlank()) {
            throw new RuntimeException("Class is required.");
        }

        if (section == null || section.isBlank()) {
            throw new RuntimeException("Section is required.");
        }

        if (subject == null || subject.isBlank()) {
            throw new RuntimeException("Subject is required.");
        }

        if (homeworkDate == null) {
            throw new RuntimeException("Homework date is required.");
        }

        if (submissionDate == null) {
            throw new RuntimeException("Submission date is required.");
        }

        if (submissionDate.isBefore(homeworkDate)) {
            throw new RuntimeException(
                    "Submission date cannot be before homework date."
            );
        }

        if (homeworkType == null) {
            throw new RuntimeException("Homework type is required.");
        }

        // -----------------------------------------------------
        // TEXT VALIDATION
        // -----------------------------------------------------

        if (homeworkType == HomeworkType.TEXT) {

            if (homeworkText == null ||
                    homeworkText.isBlank()) {

                throw new RuntimeException(
                        "Homework text is required."
                );
            }
        }

        // -----------------------------------------------------
        // IMAGE VALIDATION
        // -----------------------------------------------------

        if (homeworkType == HomeworkType.IMAGE) {

            if (image == null || image.isEmpty()) {

                throw new RuntimeException(
                        "Homework image is required."
                );
            }

            if (!image.getContentType()
                    .startsWith("image/")) {

                throw new RuntimeException(
                        "Only image files are allowed."
                );
            }

            if (image.getSize() > 5 * 1024 * 1024) {

                throw new RuntimeException(
                        "Image size should be less than 5 MB."
                );
            }
        }

        // -----------------------------------------------------
        // DUPLICATE CHECK
        // -----------------------------------------------------

        boolean exists =
                homeworkRepository
                        .existsBySchoolIdAndAcademicYearAndStudentClassAndSectionAndSubjectAndHomeworkDate(
                                schoolId,
                                academicYear,
                                studentClass,
                                section,
                                subject,
                                homeworkDate
                        );

        if (exists) {

            throw new RuntimeException(
                    "Homework already exists for this class, section, subject and date."
            );
        }

        // -----------------------------------------------------
        // IMAGE SAVE
        // -----------------------------------------------------

        String imageUrl = null;

        if (homeworkType == HomeworkType.IMAGE) {

            Files.createDirectories(uploadDirectory);

            String originalFileName =
                    image.getOriginalFilename();

            String extension = "";

            if (originalFileName != null &&
                    originalFileName.contains(".")) {

                extension =
                        originalFileName.substring(
                                originalFileName.lastIndexOf(".")
                        );
            }

            String fileName =
                    UUID.randomUUID() + extension;

            Path filePath =
                    uploadDirectory.resolve(fileName);

            Files.copy(
                    image.getInputStream(),
                    filePath,
                    StandardCopyOption.REPLACE_EXISTING
            );

            imageUrl =
                    "/uploads/homework/" + fileName;
        }

        // -----------------------------------------------------
        // CREATE ENTITY
        // -----------------------------------------------------

        Homework homework =
                Homework.builder()
                        .schoolId(schoolId)
                        .teacherId(teacherId)
                        .academicYear(academicYear)
                        .studentClass(studentClass)
                        .section(section)
                        .subject(subject)
                        .homeworkDate(homeworkDate)
                        .submissionDate(submissionDate)
                        .homeworkType(homeworkType)
                        .homeworkText(
                                homeworkType == HomeworkType.TEXT
                                        ? homeworkText
                                        : null
                        )
                        .imageUrl(imageUrl)
                        .active(true)
                        .build();

        return homeworkRepository.save(homework);
    }

    // =========================================================
    // TEACHER HOMEWORK
    // =========================================================

    public List<Homework> getTeacherHomework(
            Long schoolId,
            Long teacherId,
            String academicYear
    ) {

        return homeworkRepository
                .findBySchoolIdAndTeacherIdAndAcademicYearAndActiveTrueOrderByHomeworkDateDesc(
                        schoolId,
                        teacherId,
                        academicYear
                );
    }

    // =========================================================
    // STUDENT CLASS HOMEWORK
    // =========================================================

    public List<Homework> getClassHomework(
            Long schoolId,
            String academicYear,
            String studentClass,
            String section
    ) {

        return homeworkRepository
                .findBySchoolIdAndAcademicYearAndStudentClassAndSectionAndActiveTrueOrderByHomeworkDateDesc(
                        schoolId,
                        academicYear,
                        studentClass,
                        section
                );
    }
}