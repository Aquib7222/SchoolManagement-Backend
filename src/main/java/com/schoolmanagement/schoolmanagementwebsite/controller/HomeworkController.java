package com.schoolmanagement.schoolmanagementwebsite.controller;

import com.schoolmanagement.schoolmanagementwebsite.entity.Homework;
import com.schoolmanagement.schoolmanagementwebsite.enums.HomeworkType;
import com.schoolmanagement.schoolmanagementwebsite.service.HomeworkService;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;

import java.time.LocalDate;
import java.util.List;
import java.util.Map;

@RestController
@RequestMapping("/api/homework")
@RequiredArgsConstructor
public class HomeworkController {

    private final HomeworkService homeworkService;

    // =========================================================
    // ADD HOMEWORK
    // =========================================================

    @PostMapping(
            value = "/add",
            consumes = "multipart/form-data"
    )
    public ResponseEntity<?> addHomework(

            @RequestParam Long schoolId,

            @RequestParam Long teacherId,

            @RequestParam String academicYear,

            @RequestParam String studentClass,

            @RequestParam String section,

            @RequestParam String subject,

            @RequestParam LocalDate homeworkDate,

            @RequestParam LocalDate submissionDate,

            @RequestParam HomeworkType homeworkType,

            @RequestParam(required = false)
            String homeworkText,

            @RequestParam(required = false)
            MultipartFile image

    ) {

        try {

            Homework homework =
                    homeworkService.addHomework(
                            schoolId,
                            teacherId,
                            academicYear,
                            studentClass,
                            section,
                            subject,
                            homeworkDate,
                            submissionDate,
                            homeworkType,
                            homeworkText,
                            image
                    );

            return ResponseEntity
                    .status(HttpStatus.CREATED)
                    .body(homework);

        } catch (Exception e) {

            return ResponseEntity
                    .status(HttpStatus.BAD_REQUEST)
                    .body(
                            Map.of(
                                    "message",
                                    e.getMessage()
                            )
                    );
        }
    }

    // =========================================================
    // GET TEACHER HOMEWORK
    // =========================================================

    @GetMapping("/teacher")
    public ResponseEntity<?> getTeacherHomework(

            @RequestParam Long schoolId,

            @RequestParam Long teacherId,

            @RequestParam String academicYear

    ) {

        try {

            List<Homework> homework =
                    homeworkService.getTeacherHomework(
                            schoolId,
                            teacherId,
                            academicYear
                    );

            return ResponseEntity.ok(homework);

        } catch (Exception e) {

            return ResponseEntity
                    .status(HttpStatus.BAD_REQUEST)
                    .body(
                            Map.of(
                                    "message",
                                    e.getMessage()
                            )
                    );
        }
    }

    // =========================================================
    // GET CLASS HOMEWORK
    // =========================================================

    @GetMapping("/class")
    public ResponseEntity<?> getClassHomework(

            @RequestParam Long schoolId,

            @RequestParam String academicYear,

            @RequestParam String studentClass,

            @RequestParam String section

    ) {

        try {

            List<Homework> homework =
                    homeworkService.getClassHomework(
                            schoolId,
                            academicYear,
                            studentClass,
                            section
                    );

            return ResponseEntity.ok(homework);

        } catch (Exception e) {

            return ResponseEntity
                    .status(HttpStatus.BAD_REQUEST)
                    .body(
                            Map.of(
                                    "message",
                                    e.getMessage()
                            )
                    );
        }
    }
}