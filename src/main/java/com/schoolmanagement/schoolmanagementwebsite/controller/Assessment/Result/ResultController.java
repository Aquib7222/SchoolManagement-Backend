package com.schoolmanagement.schoolmanagementwebsite.controller.Assessment.Result;

import java.util.List;

import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import com.schoolmanagement.schoolmanagementwebsite.dto.Assessment.Result.ResultResponse;
import com.schoolmanagement.schoolmanagementwebsite.enums.Section;
import com.schoolmanagement.schoolmanagementwebsite.enums.Sessions;
import com.schoolmanagement.schoolmanagementwebsite.enums.Standard;
import com.schoolmanagement.schoolmanagementwebsite.service.Assessment.Result.ResultService;

import lombok.RequiredArgsConstructor;

@RestController
@RequestMapping("/api/assessment/result")
@RequiredArgsConstructor
public class ResultController {

    private final ResultService resultService;


    // =========================================================
    // PUBLISH SINGLE STUDENT RESULT
    // =========================================================

    @PutMapping("/publish")
    public ResponseEntity<ResultResponse> publishResult(

            @RequestParam Long schoolId,

            @RequestParam String session,

            @RequestParam Long examTermId,

            @RequestParam Standard studentClass,

            @RequestParam Section section,

            @RequestParam Long studentId) {


        Sessions sessions =
                Sessions.fromValue(session);


        ResultResponse response =
                resultService.publishStudentResult(
                        schoolId,
                        sessions,
                        examTermId,
                        studentClass,
                        section,
                        studentId
                );


        return ResponseEntity.ok(response);
    }


    // =========================================================
    // GET SINGLE PUBLISHED RESULT
    // =========================================================

    @GetMapping("/student")
    public ResponseEntity<ResultResponse> getStudentResult(

            @RequestParam Long schoolId,

            @RequestParam String session,

            @RequestParam Long examTermId,

            @RequestParam Long studentId) {


        Sessions sessions =
                Sessions.fromValue(session);


        ResultResponse response =
                resultService.getStudentResult(
                        schoolId,
                        sessions,
                        examTermId,
                        studentId
                );


        return ResponseEntity.ok(response);
    }


    // =========================================================
    // GET CLASS PUBLISHED RESULTS
    // =========================================================

    @GetMapping("/class")
    public ResponseEntity<List<ResultResponse>> getClassResults(

            @RequestParam Long schoolId,

            @RequestParam String session,

            @RequestParam Long examTermId,

            @RequestParam Standard studentClass,

            @RequestParam Section section) {


        Sessions sessions =
                Sessions.fromValue(session);


        List<ResultResponse> response =
                resultService.getClassResults(
                        schoolId,
                        sessions,
                        examTermId,
                        studentClass,
                        section
                );


        return ResponseEntity.ok(response);
    }

    // =========================================================
// GET PUBLISHED RESULT BY ADMISSION NUMBER
// =========================================================

// =========================================================
// GET STUDENT RESULT BY ADMISSION NUMBER
// =========================================================

@GetMapping("/student/admission-number")
public ResponseEntity<ResultResponse> getStudentResultByAdmissionNumber(

        @RequestParam Long schoolId,

        @RequestParam String session,

        @RequestParam Long examTermId,

        @RequestParam String admissionNumber) {

    ResultResponse response =
            resultService.getStudentResultByAdmissionNumber(
                    schoolId,
                    session,
                    examTermId,
                    admissionNumber
            );

    return ResponseEntity.ok(response);
}
}