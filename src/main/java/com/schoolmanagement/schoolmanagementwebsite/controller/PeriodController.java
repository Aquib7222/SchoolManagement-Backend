package com.schoolmanagement.schoolmanagementwebsite.controller;

import com.schoolmanagement.schoolmanagementwebsite.dto.PeriodBulkRequest;
import com.schoolmanagement.schoolmanagementwebsite.dto.PeriodRequest;
import com.schoolmanagement.schoolmanagementwebsite.entity.Period;
import com.schoolmanagement.schoolmanagementwebsite.service.PeriodService;

import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import java.util.Map;

@RestController
@RequestMapping("/api/periods")
public class PeriodController {

    private final PeriodService periodService;

    public PeriodController(
            PeriodService periodService
    ) {
        this.periodService = periodService;
    }

    // =========================================================
    // BULK SAVE DAY PERIODS
    // =========================================================

    @PostMapping("/day-bulk")
    public ResponseEntity<?> saveDayPeriods(
            @RequestBody PeriodBulkRequest request
    ) {

        try {

            List<Period> periods =
                    periodService.saveDayPeriods(
                            request
                    );

            return ResponseEntity
                    .status(HttpStatus.OK)
                    .body(
                            Map.of(
                                    "message",
                                    "Day periods saved successfully",
                                    "periods",
                                    periods
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
    // CREATE SINGLE PERIOD
    // =========================================================

    @PostMapping
    public ResponseEntity<?> createPeriod(
            @RequestBody PeriodRequest request
    ) {

        try {

            Period period =
                    periodService.createPeriod(
                            request
                    );

            return ResponseEntity
                    .status(HttpStatus.CREATED)
                    .body(period);

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
    // GET ALL
    // =========================================================

    @GetMapping("/all")
    public ResponseEntity<List<Period>>
    getAllPeriods() {

        return ResponseEntity.ok(
                periodService.getAllPeriods()
        );
    }

    // =========================================================
    // GET BY SCHOOL
    // =========================================================

    @GetMapping("/school/{schoolId}")
    public ResponseEntity<List<Period>>
    getBySchool(
            @PathVariable Long schoolId
    ) {

        return ResponseEntity.ok(
                periodService.getBySchool(
                        schoolId
                )
        );
    }

    // =========================================================
    // GET BY SCHOOL + SESSION
    // =========================================================

    @GetMapping("/school/{schoolId}/session")
    public ResponseEntity<List<Period>>
    getBySchoolAndSession(
            @PathVariable Long schoolId,
            @RequestParam String academicYear
    ) {

        return ResponseEntity.ok(
                periodService.getBySchoolAndSession(
                        schoolId,
                        academicYear
                )
        );
    }

    // =========================================================
    // GET BY SCHOOL + SESSION + DAY
    // =========================================================

    @GetMapping("/school/{schoolId}/session/day")
    public ResponseEntity<List<Period>>
    getBySchoolSessionDay(
            @PathVariable Long schoolId,
            @RequestParam String academicYear,
            @RequestParam String dayOfWeek
    ) {

        return ResponseEntity.ok(
                periodService.getBySchoolSessionDay(
                        schoolId,
                        academicYear,
                        dayOfWeek
                )
        );
    }

    // =========================================================
    // UPDATE SINGLE
    // =========================================================

    @PutMapping("/{id}")
    public ResponseEntity<?> updatePeriod(
            @PathVariable Long id,
            @RequestBody PeriodRequest request
    ) {

        try {

            Period period =
                    periodService.updatePeriod(
                            id,
                            request
                    );

            return ResponseEntity.ok(
                    period
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
    // DELETE
    // =========================================================

    @DeleteMapping("/{id}")
    public ResponseEntity<?> deletePeriod(
            @PathVariable Long id
    ) {

        try {

            periodService.deletePeriod(
                    id
            );

            return ResponseEntity.ok(
                    Map.of(
                            "message",
                            "Period deleted successfully"
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