package com.schoolmanagement.schoolmanagementwebsite.controller;

import com.schoolmanagement.schoolmanagementwebsite.dto.TeacherClassAssignmentBulkRequest;
import com.schoolmanagement.schoolmanagementwebsite.entity.TeacherClassAssignment;
import com.schoolmanagement.schoolmanagementwebsite.service.TeacherClassAssignmentService;

import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import java.util.Map;

@RestController
@RequestMapping("/api/teacher-class-assignment")
public class TeacherClassAssignmentController {

    private final TeacherClassAssignmentService service;

    public TeacherClassAssignmentController(
            TeacherClassAssignmentService service
    ) {
        this.service = service;
    }

    // =========================================================
    // SAVE WHOLE DAY
    // =========================================================

    @PostMapping("/day-bulk")
    public ResponseEntity<?> saveDayAssignments(
            @RequestBody TeacherClassAssignmentBulkRequest request
    ) {

        try {

            List<TeacherClassAssignment> assignments =
                    service.saveDayAssignments(request);

            return ResponseEntity
                    .status(HttpStatus.OK)
                    .body(
                            Map.of(
                                    "message",
                                    "Teacher class assignments saved successfully.",
                                    "assignments",
                                    assignments
                            )
                    );

        } catch (RuntimeException e) {

            return ResponseEntity
                    .status(HttpStatus.CONFLICT)
                    .body(
                            Map.of(
                                    "message",
                                    e.getMessage()
                            )
                    );
        }
    }

    // =========================================================
    // GET SCHOOL + SESSION + DAY
    // =========================================================

    @GetMapping("/day")
    public ResponseEntity<?> getBySchoolSessionDay(
            @RequestParam Long schoolId,
            @RequestParam String academicYear,
            @RequestParam String dayOfWeek
    ) {

        try {

            List<TeacherClassAssignment> assignments =
                    service.getBySchoolSessionDay(
                            schoolId,
                            academicYear,
                            dayOfWeek
                    );

            return ResponseEntity.ok(assignments);

        } catch (RuntimeException e) {

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
    // GET SPECIFIC TEACHER DAY
    // =========================================================

    @GetMapping("/teacher/day")
    public ResponseEntity<?> getTeacherDayAssignments(
            @RequestParam Long schoolId,
            @RequestParam String academicYear,
            @RequestParam Long teacherId,
            @RequestParam(required=false) String dayOfWeek
    ) {

        try {

            List<TeacherClassAssignment> assignments =
                    service.getTeacherDayAssignments(
                            schoolId,
                            academicYear,
                            teacherId,
                            dayOfWeek
                    );

            return ResponseEntity.ok(assignments);

        } catch (RuntimeException e) {

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
    // DELETE
    // =========================================================

    @DeleteMapping("/{id}")
    public ResponseEntity<?> deleteAssignment(
            @PathVariable Long id
    ) {

        try {

            service.deleteAssignment(id);

            return ResponseEntity.ok(
                    Map.of(
                            "message",
                            "Teacher class assignment deleted successfully."
                    )
            );

        } catch (RuntimeException e) {

            return ResponseEntity
                    .status(HttpStatus.NOT_FOUND)
                    .body(
                            Map.of(
                                    "message",
                                    e.getMessage()
                            )
                    );
        }
    }
}