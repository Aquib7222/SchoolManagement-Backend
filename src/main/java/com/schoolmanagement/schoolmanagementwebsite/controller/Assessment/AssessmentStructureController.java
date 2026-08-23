package com.schoolmanagement.schoolmanagementwebsite.controller.Assessment;


import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import com.schoolmanagement.schoolmanagementwebsite.dto.Assessment.AssessmentStructureRequest;
import com.schoolmanagement.schoolmanagementwebsite.dto.Assessment.AssessmentStructureResponse;
import com.schoolmanagement.schoolmanagementwebsite.enums.Sessions;
import com.schoolmanagement.schoolmanagementwebsite.enums.Standard;
import com.schoolmanagement.schoolmanagementwebsite.enums.Assessment.ExamTermType;
import com.schoolmanagement.schoolmanagementwebsite.service.Assessment.AssessmentStructureService;

@RestController
@RequestMapping("/api/assessment-structure")
public class AssessmentStructureController {

    private final AssessmentStructureService assessmentStructureService;

    public AssessmentStructureController(
            AssessmentStructureService assessmentStructureService) {

        this.assessmentStructureService =
                assessmentStructureService;
    }

    // =========================================================
    // SAVE / UPDATE ASSESSMENT STRUCTURE
    // =========================================================

    @PostMapping("/save")
    public ResponseEntity<?> saveStructure(
            @RequestBody AssessmentStructureRequest request) {

        try {

            assessmentStructureService.saveStructure(request);

            return ResponseEntity.ok(
                    "Assessment Structure Saved Successfully"
            );

        } catch (RuntimeException e) {

            return ResponseEntity
                    .badRequest()
                    .body(e.getMessage());

        } catch (Exception e) {

            e.printStackTrace();

            return ResponseEntity
                    .internalServerError()
                    .body("Unable to save Assessment Structure");
        }
    }

        @GetMapping("/get")
public ResponseEntity<?> getStructure(
        @RequestParam Long schoolId,
        @RequestParam String session,
        @RequestParam String examTerm,
        @RequestParam Standard studentClass,
        @RequestParam Long subjectId) {

    try {

        Sessions sessionEnum = Sessions.fromValue(session);

        AssessmentStructureResponse response =
                assessmentStructureService.getStructure(
                        schoolId,
                        sessionEnum,
                        examTerm,
                        studentClass,
                        subjectId
                );

        if (response == null) {
            return ResponseEntity.ok(null);
        }

        return ResponseEntity.ok(response);

    } catch (Exception e) {

        e.printStackTrace();

        return ResponseEntity
                .badRequest()
                .body(e.getMessage());
    }
}
}