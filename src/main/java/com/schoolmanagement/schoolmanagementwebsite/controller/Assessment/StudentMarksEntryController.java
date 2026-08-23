package com.schoolmanagement.schoolmanagementwebsite.controller.Assessment;

import java.util.List;

import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import com.schoolmanagement.schoolmanagementwebsite.dto.Assessment.MarksEntryRequest;
import com.schoolmanagement.schoolmanagementwebsite.dto.Assessment.MarksEntryResponse;
import com.schoolmanagement.schoolmanagementwebsite.enums.Section;
import com.schoolmanagement.schoolmanagementwebsite.enums.Sessions;
import com.schoolmanagement.schoolmanagementwebsite.enums.Standard;
import com.schoolmanagement.schoolmanagementwebsite.service.Assessment.StudentMarksEntryService;

import lombok.RequiredArgsConstructor;

@RestController
@RequestMapping("/api/assessment/marks-entry")
@RequiredArgsConstructor
public class StudentMarksEntryController {

    private final StudentMarksEntryService marksEntryService;



    @PostMapping("/draft")
    public ResponseEntity<MarksEntryResponse> saveDraft(
            @RequestBody MarksEntryRequest request) {

        MarksEntryResponse response =
                marksEntryService.saveDraft(request);

        return ResponseEntity
                .status(HttpStatus.CREATED)
                .body(response);
    }



 @GetMapping
public ResponseEntity<MarksEntryResponse> getMarks(

        @RequestParam Long schoolId,

        @RequestParam String session,

        @RequestParam Long examTermId,

        @RequestParam Standard studentClass,

        @RequestParam Section section,

        @RequestParam Long subjectId) {

    Sessions sessions = Sessions.fromValue(session);

    MarksEntryResponse response =
            marksEntryService.getMarks(
                    schoolId,
                    sessions,
                    examTermId,
                    studentClass,
                    section,
                    subjectId
            );

    return ResponseEntity.ok(response);
}

// =========================================================
// GET ALL SUBJECT MARKS
// =========================================================

@GetMapping("/class")
public ResponseEntity<List<MarksEntryResponse>> getClassMarks(

        @RequestParam Long schoolId,

        @RequestParam String session,

        @RequestParam Long examTermId,

        @RequestParam Standard studentClass,

        @RequestParam Section section) {

    Sessions sessions = Sessions.fromValue(session);

    List<MarksEntryResponse> response =
            marksEntryService.getClassMarks(
                    schoolId,
                    sessions,
                    examTermId,
                    studentClass,
                    section
            );

    return ResponseEntity.ok(response);
}


   
   @PutMapping("/publish")
public ResponseEntity<MarksEntryResponse> publishMarks(

        @RequestParam Long schoolId,

        @RequestParam String session,

        @RequestParam Long examTermId,

        @RequestParam Standard studentClass,

        @RequestParam Section section,

        @RequestParam Long subjectId) {

    Sessions sessions = Sessions.fromValue(session);

    MarksEntryResponse response =
            marksEntryService.publishMarks(
                    schoolId,
                    sessions,
                    examTermId,
                    studentClass,
                    section,
                    subjectId
            );

    return ResponseEntity.ok(response);
}

// =========================================================
// VERIFY ALL SUBJECT MARKS
// =========================================================

@PutMapping("/verify")
public ResponseEntity<List<MarksEntryResponse>> verifyClassMarks(

        @RequestParam Long schoolId,

        @RequestParam String session,

        @RequestParam Long examTermId,

        @RequestParam Standard studentClass,

        @RequestParam Section section) {

    Sessions sessions = Sessions.fromValue(session);

    List<MarksEntryResponse> response =
            marksEntryService.verifyClassMarks(
                    schoolId,
                    sessions,
                    examTermId,
                    studentClass,
                    section
            );

    return ResponseEntity.ok(response);
}


@PutMapping("/publish-result")
public ResponseEntity<MarksEntryResponse> publishResult(

        @RequestParam Long schoolId,

        @RequestParam String session,

        @RequestParam Long examTermId,

        @RequestParam Standard studentClass,

        @RequestParam Section section,

        @RequestParam Long subjectId) {

    Sessions sessions =
            Sessions.fromValue(session);

    MarksEntryResponse response =
            marksEntryService.publishResult(
                    schoolId,
                    sessions,
                    examTermId,
                    studentClass,
                    section,
                    subjectId
            );

    return ResponseEntity.ok(response);
}


@PutMapping("/publish-all-results")
public ResponseEntity<List<MarksEntryResponse>> publishAllResults(

        @RequestParam Long schoolId,

        @RequestParam String session,

        @RequestParam Long examTermId,

        @RequestParam Standard studentClass,

        @RequestParam Section section) {

    Sessions sessions =
            Sessions.fromValue(session);

    List<MarksEntryResponse> response =
            marksEntryService.publishAllResults(
                    schoolId,
                    sessions,
                    examTermId,
                    studentClass,
                    section
            );

    return ResponseEntity.ok(response);
}

}