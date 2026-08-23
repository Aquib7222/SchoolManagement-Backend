package com.schoolmanagement.schoolmanagementwebsite.controller.Assessment.SubjectManagement;

import java.util.List;

import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import com.schoolmanagement.schoolmanagementwebsite.entity.Assessment.SubjectManagement.SubjectMaster;
import com.schoolmanagement.schoolmanagementwebsite.service.Assessment.SubjectManagement.SubjectMasterService;

import lombok.RequiredArgsConstructor;

@RestController
@RequestMapping("/api/assessment/subject")
@RequiredArgsConstructor
@CrossOrigin(origins = "http://localhost:5173")
public class SubjectMasterController {

    private final SubjectMasterService subjectMasterService;


    // =====================================================
    // CREATE
    // =====================================================

    @PostMapping
    public ResponseEntity<?> save(
            @RequestBody SubjectMaster subject
    ) {

        try {

            SubjectMaster saved =
                    subjectMasterService.save(subject);

            return ResponseEntity
                    .status(HttpStatus.CREATED)
                    .body(saved);

        } catch (RuntimeException e) {

            return ResponseEntity
                    .status(HttpStatus.CONFLICT)
                    .body(e.getMessage());
        }
    }


    // =====================================================
    // GET ALL
    // =====================================================

    @GetMapping
    public ResponseEntity<?> getAll(
            @RequestParam Long schoolId
    ) {

        try {

            List<SubjectMaster> subjects =
                    subjectMasterService.getAll(schoolId);

            return ResponseEntity.ok(subjects);

        } catch (RuntimeException e) {

            return ResponseEntity
                    .status(HttpStatus.BAD_REQUEST)
                    .body(e.getMessage());
        }
    }


    // =====================================================
    // GET BY ID
    // =====================================================

   @GetMapping("/{id}")
public ResponseEntity<?> getById(
        @PathVariable Long id,
        @RequestParam Long schoolId
) {

    try {

        SubjectMaster subject =
                subjectMasterService.getById(
                        id,
                        schoolId
                );

        return ResponseEntity.ok(subject);

    } catch (RuntimeException e) {

        return ResponseEntity
                .status(HttpStatus.NOT_FOUND)
                .body(e.getMessage());
    }
}


    // =====================================================
    // UPDATE
    // =====================================================

    @PutMapping("/{id}")
    public ResponseEntity<?> update(
            @PathVariable Long id,
            @RequestBody SubjectMaster subject
    ) {

        try {

            SubjectMaster updated =
                    subjectMasterService.update(
                            id,
                            subject
                    );

            return ResponseEntity.ok(updated);

        } catch (RuntimeException e) {

            String message = e.getMessage();

            if (message != null &&
                    message.toLowerCase()
                           .contains("already exists")) {

                return ResponseEntity
                        .status(HttpStatus.CONFLICT)
                        .body(message);
            }

            return ResponseEntity
                    .status(HttpStatus.NOT_FOUND)
                    .body(message);
        }
    }


    // =====================================================
    // DELETE
    // =====================================================

    @DeleteMapping("/{id}")
public ResponseEntity<?> delete(
        @PathVariable Long id,
        @RequestParam Long schoolId
) {

    try {

        subjectMasterService.delete(
                id,
                schoolId
        );

        return ResponseEntity.ok(
                "Subject deleted successfully."
        );

    } catch (RuntimeException e) {

        return ResponseEntity
                .status(HttpStatus.NOT_FOUND)
                .body(e.getMessage());
    }
}
}