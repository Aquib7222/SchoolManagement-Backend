package com.schoolmanagement.schoolmanagementwebsite.service;

import com.schoolmanagement.schoolmanagementwebsite.dto.PeriodBulkRequest;
import com.schoolmanagement.schoolmanagementwebsite.dto.PeriodItemRequest;
import com.schoolmanagement.schoolmanagementwebsite.dto.PeriodRequest;
import com.schoolmanagement.schoolmanagementwebsite.entity.Period;
import com.schoolmanagement.schoolmanagementwebsite.repository.PeriodRepository;

import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.time.DayOfWeek;
import java.time.LocalTime;
import java.util.Comparator;
import java.util.HashSet;
import java.util.List;
import java.util.Set;

@Service
public class PeriodService {

    private final PeriodRepository periodRepository;

    public PeriodService(PeriodRepository periodRepository) {
        this.periodRepository = periodRepository;
    }

    // =========================================================
    // BULK SAVE DAY PERIODS
    // =========================================================

    @Transactional
    public List<Period> saveDayPeriods(PeriodBulkRequest request) {

        validateBulkRequest(request);

        Long schoolId = request.getSchoolId();

        String academicYear =
                request.getAcademicYear().trim();

        DayOfWeek dayOfWeek =
                request.getDayOfWeek();

        List<PeriodItemRequest> items =
                request.getPeriods();

        // -----------------------------------------------------
        // VALIDATE PERIOD ROWS
        // -----------------------------------------------------

        validatePeriodItems(items);

        // -----------------------------------------------------
        // GET EXISTING PERIODS FOR THIS DAY
        // -----------------------------------------------------

        List<Period> existingPeriods =
                periodRepository
                        .findBySchoolIdAndAcademicYearAndDayOfWeekOrderByStartTimeAsc(
                                schoolId,
                                academicYear,
                                dayOfWeek
                        );

        // -----------------------------------------------------
        // VALIDATE IDs
        // -----------------------------------------------------

        Set<Long> existingIds =
                existingPeriods
                        .stream()
                        .map(Period::getId)
                        .collect(java.util.stream.Collectors.toSet());

        Set<Long> submittedIds =
                new HashSet<>();

        for (PeriodItemRequest item : items) {

            if (item.getId() != null) {

                if (!existingIds.contains(item.getId())) {

                    throw new RuntimeException(
                            "Invalid period id: " + item.getId()
                    );
                }

                if (!submittedIds.add(item.getId())) {

                    throw new RuntimeException(
                            "Duplicate period id: " + item.getId()
                    );
                }
            }
        }

        // -----------------------------------------------------
        // DELETE REMOVED PERIODS
        // -----------------------------------------------------

        for (Period existing : existingPeriods) {

            if (!submittedIds.contains(existing.getId())) {

                periodRepository.delete(existing);
            }
        }

        // -----------------------------------------------------
        // SAVE / UPDATE
        // -----------------------------------------------------

        for (PeriodItemRequest item : items) {

            Period period;

            if (item.getId() != null) {

                period =
                        existingPeriods
                                .stream()
                                .filter(
                                        p -> p.getId()
                                                .equals(item.getId())
                                )
                                .findFirst()
                                .orElseThrow(
                                        () -> new RuntimeException(
                                                "Period not found with id: "
                                                        + item.getId()
                                        )
                                );

            } else {

                period = new Period();

                period.setSchoolId(schoolId);

                period.setAcademicYear(
                        academicYear
                );

                period.setDayOfWeek(
                        dayOfWeek
                );
            }

            period.setPeriodName(
                    item.getPeriodName().trim()
            );

            period.setStartTime(
                    item.getStartTime()
            );

            period.setEndTime(
                    item.getEndTime()
            );

            period.setDescription(
                    item.getDescription() != null
                            && !item.getDescription().isBlank()
                            ? item.getDescription().trim()
                            : null
            );

            period.setActive(
                    item.getActive() != null
                            ? item.getActive()
                            : true
            );

            periodRepository.save(period);
        }

        // -----------------------------------------------------
        // RETURN UPDATED LIST
        // -----------------------------------------------------

        return periodRepository
                .findBySchoolIdAndAcademicYearAndDayOfWeekOrderByStartTimeAsc(
                        schoolId,
                        academicYear,
                        dayOfWeek
                );
    }

    // =========================================================
    // VALIDATE BULK REQUEST
    // =========================================================

    private void validateBulkRequest(
            PeriodBulkRequest request
    ) {

        if (request == null) {

            throw new RuntimeException(
                    "Period request cannot be null"
            );
        }

        if (request.getSchoolId() == null) {

            throw new RuntimeException(
                    "School is required"
            );
        }

        if (request.getAcademicYear() == null
                || request.getAcademicYear().isBlank()) {

            throw new RuntimeException(
                    "Academic session is required"
            );
        }

        if (request.getDayOfWeek() == null) {

            throw new RuntimeException(
                    "Day is required"
            );
        }

        if (request.getPeriods() == null
                || request.getPeriods().isEmpty()) {

            throw new RuntimeException(
                    "At least one period is required"
            );
        }
    }

    // =========================================================
    // VALIDATE ALL PERIOD ITEMS
    // =========================================================

    private void validatePeriodItems(
            List<PeriodItemRequest> items
    ) {

        Set<String> periodNames =
                new HashSet<>();

        for (PeriodItemRequest item : items) {

            if (item == null) {

                throw new RuntimeException(
                        "Invalid period row"
                );
            }

            // ---------------------------------------------
            // PERIOD NAME
            // ---------------------------------------------

            if (item.getPeriodName() == null
                    || item.getPeriodName().isBlank()) {

                throw new RuntimeException(
                        "Period name is required"
                );
            }

            String normalizedName =
                    item.getPeriodName()
                            .trim()
                            .toLowerCase();

            if (!periodNames.add(normalizedName)) {

                throw new RuntimeException(
                        "Duplicate period name: "
                                + item.getPeriodName()
                );
            }

            // ---------------------------------------------
            // START TIME
            // ---------------------------------------------

            if (item.getStartTime() == null) {

                throw new RuntimeException(
                        "Start time is required for "
                                + item.getPeriodName()
                );
            }

            // ---------------------------------------------
            // END TIME
            // ---------------------------------------------

            if (item.getEndTime() == null) {

                throw new RuntimeException(
                        "End time is required for "
                                + item.getPeriodName()
                );
            }

            // ---------------------------------------------
            // TIME VALIDATION
            // ---------------------------------------------

            if (!item.getEndTime()
                    .isAfter(item.getStartTime())) {

                throw new RuntimeException(
                        "End time must be greater than start time for "
                                + item.getPeriodName()
                );
            }
        }

        // =================================================
        // OVERLAP VALIDATION
        // =================================================

        List<PeriodItemRequest> sortedPeriods =
                items.stream()
                        .sorted(
                                Comparator.comparing(
                                        PeriodItemRequest::getStartTime
                                )
                        )
                        .toList();

        for (int i = 0;
             i < sortedPeriods.size() - 1;
             i++) {

            PeriodItemRequest current =
                    sortedPeriods.get(i);

            PeriodItemRequest next =
                    sortedPeriods.get(i + 1);

            if (current.getEndTime()
                    .isAfter(next.getStartTime())) {

                throw new RuntimeException(
                        "Period time overlaps between "
                                + current.getPeriodName()
                                + " and "
                                + next.getPeriodName()
                );
            }
        }
    }

    // =========================================================
    // OLD SINGLE CREATE
    // =========================================================

    public Period createPeriod(
            PeriodRequest request
    ) {

        validateRequest(request);

        validateTime(request);

        boolean exists =
                periodRepository
                        .existsBySchoolIdAndAcademicYearAndDayOfWeekAndPeriodName(
                                request.getSchoolId(),
                                request.getAcademicYear().trim(),
                                request.getDayOfWeek(),
                                request.getPeriodName().trim()
                        );

        if (exists) {

            throw new RuntimeException(
                    "Period already exists for this school, session, day and period name"
            );
        }

        Period period = new Period();

        period.setSchoolId(
                request.getSchoolId()
        );

        period.setAcademicYear(
                request.getAcademicYear().trim()
        );

        period.setDayOfWeek(
                request.getDayOfWeek()
        );

        period.setPeriodName(
                request.getPeriodName().trim()
        );

        period.setStartTime(
                request.getStartTime()
        );

        period.setEndTime(
                request.getEndTime()
        );

        period.setDescription(
                request.getDescription() != null
                        && !request.getDescription().isBlank()
                        ? request.getDescription().trim()
                        : null
        );

        period.setActive(
                request.getActive() != null
                        ? request.getActive()
                        : true
        );

        return periodRepository.save(period);
    }

    // =========================================================
    // GET ALL
    // =========================================================

    public List<Period> getAllPeriods() {

        return periodRepository
                .findAll()
                .stream()
                .sorted(
                        Comparator
                                .comparing(
                                        Period::getAcademicYear
                                )
                                .thenComparing(
                                        p -> getDayOrder(
                                                p.getDayOfWeek()
                                        )
                                )
                                .thenComparing(
                                        Period::getStartTime
                                )
                )
                .toList();
    }

    // =========================================================
    // GET BY SCHOOL
    // =========================================================

    public List<Period> getBySchool(
            Long schoolId
    ) {

        return periodRepository
                .findBySchoolIdOrderByAcademicYearAscDayOfWeekAscStartTimeAsc(
                        schoolId
                );
    }

    // =========================================================
    // GET BY SCHOOL + SESSION
    // =========================================================

    public List<Period> getBySchoolAndSession(
            Long schoolId,
            String academicYear
    ) {

        return periodRepository
                .findBySchoolIdAndAcademicYearOrderByDayOfWeekAscStartTimeAsc(
                        schoolId,
                        academicYear
                );
    }

    // =========================================================
    // GET BY SCHOOL + SESSION + DAY
    // =========================================================

    public List<Period> getBySchoolSessionDay(
            Long schoolId,
            String academicYear,
            String dayOfWeek
    ) {

        DayOfWeek day;

        try {

            day =
                    DayOfWeek.valueOf(
                            dayOfWeek
                                    .trim()
                                    .toUpperCase()
                    );

        } catch (IllegalArgumentException e) {

            throw new RuntimeException(
                    "Invalid day of week: "
                            + dayOfWeek
            );
        }

        return periodRepository
                .findBySchoolIdAndAcademicYearAndDayOfWeekOrderByStartTimeAsc(
                        schoolId,
                        academicYear,
                        day
                );
    }

    // =========================================================
    // UPDATE SINGLE
    // =========================================================

    public Period updatePeriod(
            Long id,
            PeriodRequest request
    ) {

        validateRequest(request);

        validateTime(request);

        Period period =
                periodRepository
                        .findById(id)
                        .orElseThrow(
                                () -> new RuntimeException(
                                        "Period not found with id: "
                                                + id
                                )
                        );

        boolean duplicate =
                periodRepository
                        .existsBySchoolIdAndAcademicYearAndDayOfWeekAndPeriodName(
                                request.getSchoolId(),
                                request.getAcademicYear().trim(),
                                request.getDayOfWeek(),
                                request.getPeriodName().trim()
                        );

        if (duplicate
                && !isSamePeriod(period, request)) {

            throw new RuntimeException(
                    "Period already exists for this school, session, day and period name"
            );
        }

        period.setSchoolId(
                request.getSchoolId()
        );

        period.setAcademicYear(
                request.getAcademicYear().trim()
        );

        period.setDayOfWeek(
                request.getDayOfWeek()
        );

        period.setPeriodName(
                request.getPeriodName().trim()
        );

        period.setStartTime(
                request.getStartTime()
        );

        period.setEndTime(
                request.getEndTime()
        );

        period.setDescription(
                request.getDescription() != null
                        && !request.getDescription().isBlank()
                        ? request.getDescription().trim()
                        : null
        );

        period.setActive(
                request.getActive() != null
                        ? request.getActive()
                        : true
        );

        return periodRepository.save(period);
    }

    // =========================================================
    // DELETE
    // =========================================================

    public void deletePeriod(Long id) {

        if (!periodRepository.existsById(id)) {

            throw new RuntimeException(
                    "Period not found with id: "
                            + id
            );
        }

        periodRepository.deleteById(id);
    }

    // =========================================================
    // OLD VALIDATION
    // =========================================================

    private void validateRequest(
            PeriodRequest request
    ) {

        if (request == null) {

            throw new RuntimeException(
                    "Period request cannot be null"
            );
        }

        if (request.getSchoolId() == null) {

            throw new RuntimeException(
                    "School is required"
            );
        }

        if (request.getAcademicYear() == null
                || request.getAcademicYear().isBlank()) {

            throw new RuntimeException(
                    "Academic session is required"
            );
        }

        if (request.getDayOfWeek() == null) {

            throw new RuntimeException(
                    "Day is required"
            );
        }

        if (request.getPeriodName() == null
                || request.getPeriodName().isBlank()) {

            throw new RuntimeException(
                    "Period name is required"
            );
        }

        if (request.getStartTime() == null) {

            throw new RuntimeException(
                    "Start time is required"
            );
        }

        if (request.getEndTime() == null) {

            throw new RuntimeException(
                    "End time is required"
            );
        }
    }

    // =========================================================
    // OLD TIME VALIDATION
    // =========================================================

    private void validateTime(
            PeriodRequest request
    ) {

        if (!request.getEndTime()
                .isAfter(request.getStartTime())) {

            throw new RuntimeException(
                    "End time must be greater than start time"
            );
        }
    }

    // =========================================================
    // SAME PERIOD
    // =========================================================

    private boolean isSamePeriod(
            Period period,
            PeriodRequest request
    ) {

        return period.getSchoolId()
                .equals(request.getSchoolId())

                && period.getAcademicYear()
                .equals(request.getAcademicYear().trim())

                && period.getDayOfWeek()
                .equals(request.getDayOfWeek())

                && period.getPeriodName()
                .equalsIgnoreCase(
                        request.getPeriodName().trim()
                );
    }

    // =========================================================
    // DAY ORDER
    // =========================================================

    private int getDayOrder(
            DayOfWeek day
    ) {

        return switch (day) {

            case SUNDAY -> 0;
            case MONDAY -> 1;
            case TUESDAY -> 2;
            case WEDNESDAY -> 3;
            case THURSDAY -> 4;
            case FRIDAY -> 5;
            case SATURDAY -> 6;
        };
    }
}