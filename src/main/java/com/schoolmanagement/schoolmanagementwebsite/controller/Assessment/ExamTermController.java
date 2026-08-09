package com.schoolmanagement.schoolmanagementwebsite.controller.Assessment;

import java.util.Arrays;
import java.util.List;

import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import com.schoolmanagement.schoolmanagementwebsite.dto.Assessment.ExamTermDTO;
import com.schoolmanagement.schoolmanagementwebsite.dto.Assessment.ExamTermResponse;
import com.schoolmanagement.schoolmanagementwebsite.enums.Sessions;
import com.schoolmanagement.schoolmanagementwebsite.service.Assessment.ExamTermService;

import lombok.RequiredArgsConstructor;

@RestController
@RequestMapping("/api/assessment/exam-term")
@RequiredArgsConstructor
@CrossOrigin(origins = "http://localhost:5173")
public class ExamTermController {

    private final ExamTermService service;

    // ================= CREATE =================

    @PostMapping
    public ResponseEntity<?> save(@RequestBody ExamTermDTO dto) {

        try {

            ExamTermResponse response = service.save(dto);

            return ResponseEntity.status(HttpStatus.CREATED)
                    .body(response);

        } catch (RuntimeException e) {

            return ResponseEntity.status(HttpStatus.CONFLICT)
                    .body(e.getMessage());
        }
    }

    // ================= GET ALL =================

    @GetMapping
    public ResponseEntity<List<ExamTermResponse>> getAll(
            @RequestParam Long schoolId,
            @RequestParam String session) {

            Sessions sessions = Arrays.stream(Sessions.values())
        .filter(s -> s.getValue().equals(session))
        .findFirst()
        .orElseThrow(() -> new IllegalArgumentException(
                "Invalid session: " + session
        ));

        return ResponseEntity.ok(
                service.getAll(schoolId, sessions)
        );
    }

    // ================= GET BY ID =================

    @GetMapping("/{id}")
    public ResponseEntity<?> getById(
            @PathVariable Long id,
            @RequestParam Long schoolId,
            @RequestParam Sessions session) {

        try {

            return ResponseEntity.ok(
                    service.getById(id, schoolId, session)
            );

        } catch (RuntimeException e) {

            return ResponseEntity.status(HttpStatus.NOT_FOUND)
                    .body(e.getMessage());
        }
    }

    // ================= UPDATE =================

    @PutMapping("/{id}")
    public ResponseEntity<?> update(
            @PathVariable Long id,
            @RequestBody ExamTermDTO dto) {

        try {

            return ResponseEntity.ok(
                    service.update(id, dto)
            );

        } catch (RuntimeException e) {

            if (e.getMessage().contains("already exists")) {

                return ResponseEntity.status(HttpStatus.CONFLICT)
                        .body(e.getMessage());
            }

            return ResponseEntity.status(HttpStatus.NOT_FOUND)
                    .body(e.getMessage());
        }
    }

    // ================= DELETE =================

    @DeleteMapping("/{id}")
    public ResponseEntity<?> delete(
            @PathVariable Long id,
            @RequestParam Long schoolId,
            @RequestParam Sessions session) {

        try {

            service.delete(id, schoolId, session);

            return ResponseEntity.ok("Exam Term deleted successfully.");

        } catch (RuntimeException e) {

            return ResponseEntity.status(HttpStatus.NOT_FOUND)
                    .body(e.getMessage());
        }
    }

}