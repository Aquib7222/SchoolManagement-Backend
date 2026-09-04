package com.schoolmanagement.schoolmanagementwebsite.service;

import com.schoolmanagement.schoolmanagementwebsite.dto.TeacherClassAssignmentBulkRequest;
import com.schoolmanagement.schoolmanagementwebsite.dto.TeacherClassAssignmentItemRequest;
import com.schoolmanagement.schoolmanagementwebsite.entity.TeacherClassAssignment;

import com.schoolmanagement.schoolmanagementwebsite.repository.TeacherClassAssignmentRepository;

import jakarta.transaction.Transactional;

import org.springframework.stereotype.Service;

import java.time.DayOfWeek;
import java.util.*;
import java.util.stream.Collectors;

@Service
public class TeacherClassAssignmentService {

    private final TeacherClassAssignmentRepository repository;

    public TeacherClassAssignmentService(
            TeacherClassAssignmentRepository repository
    ) {
        this.repository = repository;
    }

    // =========================================================
    // SAVE DAY BULK
    // =========================================================
    @Transactional
    public List<TeacherClassAssignment> saveDayAssignments(
            TeacherClassAssignmentBulkRequest request
    ) {

        // -----------------------------------------------------
        // BASIC VALIDATION
        // -----------------------------------------------------
        if (request == null) {
            throw new RuntimeException(
                    "Request cannot be null."
            );
        }

        if (request.getSchoolId() == null) {
            throw new RuntimeException(
                    "School ID is required."
            );
        }

        if (request.getAcademicYear() == null
                || request.getAcademicYear().isBlank()) {

            throw new RuntimeException(
                    "Academic session is required."
            );
        }

        if (request.getDayOfWeek() == null
                || request.getDayOfWeek().isBlank()) {

            throw new RuntimeException(
                    "Day of week is required."
            );
        }

        if (request.getAssignments() == null) {
            throw new RuntimeException(
                    "Assignments are required."
            );
        }

        // -----------------------------------------------------
        // DAY
        // -----------------------------------------------------
        DayOfWeek dayOfWeek;

        try {

            dayOfWeek = DayOfWeek.valueOf(
                    request.getDayOfWeek()
                            .trim()
                            .toUpperCase()
            );

        } catch (IllegalArgumentException e) {

            throw new RuntimeException(
                    "Invalid day of week: "
                    + request.getDayOfWeek()
            );
        }

        // -----------------------------------------------------
        // REMOVE EMPTY ITEMS
        // -----------------------------------------------------
        List<TeacherClassAssignmentItemRequest> items
                = request.getAssignments()
                        .stream()
                        .filter(Objects::nonNull)
                        .filter(item
                                -> item.getPeriodId() != null
                        || item.getSubject() != null
                        || item.getStudentClass() != null
                        || item.getSection() != null
                        )
                        .toList();

        // -----------------------------------------------------
        // VALIDATE ITEMS
        // -----------------------------------------------------
        for (TeacherClassAssignmentItemRequest item : items) {

            validateItem(item);
        }

        // -----------------------------------------------------
        // DUPLICATE VALIDATION INSIDE REQUEST
        // -----------------------------------------------------
        validateDuplicateAssignments(
                items
        );

        // -----------------------------------------------------
        // EXISTING ASSIGNMENTS FOR SCHOOL + SESSION + DAY
        // -----------------------------------------------------
        List<TeacherClassAssignment> existing
                = repository
                        .findBySchoolIdAndAcademicYearAndDayOfWeek(
                                request.getSchoolId(),
                                request.getAcademicYear(),
                                dayOfWeek
                        );

        // -----------------------------------------------------
        // SAVE / UPDATE
        // -----------------------------------------------------
        List<TeacherClassAssignment> saved
                = new ArrayList<>();

        for (TeacherClassAssignmentItemRequest item : items) {

            TeacherClassAssignment assignment;

            // -------------------------------------------------
            // UPDATE
            // -------------------------------------------------
            if (item.getId() != null) {

                assignment = repository
                        .findById(item.getId())
                        .orElseThrow(()
                                -> new RuntimeException(
                                "Assignment not found with id: "
                                + item.getId()
                        )
                        );

                // Security / ownership check
                if (!Objects.equals(
                        assignment.getSchoolId(),
                        request.getSchoolId()
                )) {

                    throw new RuntimeException(
                            "Assignment does not belong to selected school."
                    );
                }

                if (!Objects.equals(
                        assignment.getAcademicYear(),
                        request.getAcademicYear()
                )) {

                    throw new RuntimeException(
                            "Assignment does not belong to selected academic session."
                    );
                }

                if (!Objects.equals(
                        assignment.getDayOfWeek(),
                        dayOfWeek
                )) {

                    throw new RuntimeException(
                            "Assignment does not belong to selected day."
                    );
                }

            } else {

                // -------------------------------------------------
                // CREATE
                // -------------------------------------------------
                assignment
                        = new TeacherClassAssignment();

                assignment.setSchoolId(
                        request.getSchoolId()
                );

                assignment.setAcademicYear(
                        request.getAcademicYear()
                );

                assignment.setDayOfWeek(
                        dayOfWeek
                );
            }

            // -------------------------------------------------
            // CONFLICT CHECK
            // -------------------------------------------------
            validateConflicts(
                    request,
                    dayOfWeek,
                    item,
                    existing
            );

            // -------------------------------------------------
            // SET VALUES
            // -------------------------------------------------
            assignment.setTeacherId(
                    item.getTeacherId()
            );

            assignment.setPeriodId(
                    item.getPeriodId()
            );
            assignment.setPeriodName(
                    item.getPeriodName
            );

            assignment.setStartTime(
                    item.getStartTime()
            );

            assignment.setEndTime(
                    item.getEndTime()
            );

            assignment.setSubject(
                    item.getSubject()
            );

            assignment.setStudentClass(
                    item.getStudentClass().trim()
            );

            assignment.setSection(
                    item.getSection().trim()
            );

            assignment.setRoom(
                    item.getRoom() == null
                    || item.getRoom().isBlank()
                    ? null
                    : item.getRoom().trim()
            );

            assignment.setActive(
                    item.getActive() == null
                    ? true
                    : item.getActive()
            );

            TeacherClassAssignment savedAssignment
                    = repository.save(assignment);

            saved.add(savedAssignment);

            // -------------------------------------------------
            // UPDATE LOCAL EXISTING LIST
            // -------------------------------------------------
            existing.removeIf(
                    e -> Objects.equals(
                            e.getId(),
                            savedAssignment.getId()
                    )
            );

            existing.add(savedAssignment);
        }

        return saved;
    }

    // =========================================================
    // VALIDATE ITEM
    // =========================================================
    private void validateItem(
            TeacherClassAssignmentItemRequest item
    ) {

        if (item.getPeriodId() == null) {

            throw new RuntimeException(
                    "Period ID is required."
            );
        }

        if (item.getTeacherId() == null) {

            throw new RuntimeException(
                    "Teacher ID is required."
            );
        }

        if (item.getSubject() == null) {

            throw new RuntimeException(
                    "Subject is required."
            );
        }

        if (item.getStudentClass() == null
                || item.getStudentClass().isBlank()) {

            throw new RuntimeException(
                    "Class / Standard is required."
            );
        }

        if (item.getSection() == null
                || item.getSection().isBlank()) {

            throw new RuntimeException(
                    "Section is required."
            );
        }
        if (item.getStartTime() == null) {
            throw new RuntimeException("Start time is required.");
        }

        if (item.getEndTime() == null) {
            throw new RuntimeException("End time is required.");
        }

        if (!item.getEndTime().isAfter(item.getStartTime())) {
            throw new RuntimeException("End time must be after start time.");
        }
    }

    // =========================================================
    // DUPLICATE REQUEST VALIDATION
    // =========================================================
    private void validateDuplicateAssignments(
            List<TeacherClassAssignmentItemRequest> items
    ) {

        Set<String> teacherPeriodKeys
                = new HashSet<>();

        Set<String> classSectionPeriodKeys
                = new HashSet<>();

        Set<String> roomPeriodKeys
                = new HashSet<>();

        for (TeacherClassAssignmentItemRequest item : items) {

            if (item.getActive() != null
                    && !item.getActive()) {
                continue;
            }

            // -------------------------------------------------
            // TEACHER + PERIOD
            // -------------------------------------------------
            String teacherPeriodKey
                    = item.getTeacherId()
                    + "_"
                    + item.getPeriodId();

            if (!teacherPeriodKeys.add(
                    teacherPeriodKey
            )) {

                throw new RuntimeException(
                        "Same teacher cannot be assigned more than once in the same period."
                );
            }

            // -------------------------------------------------
            // CLASS + SECTION + PERIOD
            // -------------------------------------------------
            String classSectionPeriodKey
                    = item.getStudentClass().trim()
                            .toUpperCase()
                    + "_"
                    + item.getSection().trim()
                            .toUpperCase()
                    + "_"
                    + item.getPeriodId();

            if (!classSectionPeriodKeys.add(
                    classSectionPeriodKey
            )) {

                throw new RuntimeException(
                        "Same class and section cannot have multiple assignments in the same period."
                );
            }

            // -------------------------------------------------
            // ROOM + PERIOD
            // -------------------------------------------------
            if (item.getRoom() != null
                    && !item.getRoom().isBlank()) {

                String roomPeriodKey
                        = item.getRoom().trim()
                                .toUpperCase()
                        + "_"
                        + item.getPeriodId();

                if (!roomPeriodKeys.add(
                        roomPeriodKey
                )) {

                    throw new RuntimeException(
                            "Same room cannot be assigned to multiple classes in the same period."
                    );
                }
            }
        }
    }

    // =========================================================
    // EXISTING CONFLICT VALIDATION
    // =========================================================
    private void validateConflicts(
            TeacherClassAssignmentBulkRequest request,
            DayOfWeek dayOfWeek,
            TeacherClassAssignmentItemRequest item,
            List<TeacherClassAssignment> existing
    ) {

        if (item.getActive() != null
                && !item.getActive()) {

            return;
        }

        for (TeacherClassAssignment old : existing) {

            // Current record ko ignore karo during UPDATE
            if (item.getId() != null
                    && Objects.equals(
                            old.getId(),
                            item.getId()
                    )) {

                continue;
            }

            if (old.getActive() == null
                    || !old.getActive()) {

                continue;
            }

            // -------------------------------------------------
            // SAME TEACHER + SAME PERIOD
            // -------------------------------------------------
            if (Objects.equals(
                    old.getTeacherId(),
                    item.getTeacherId()
            )
                    && Objects.equals(
                            old.getPeriodId(),
                            item.getPeriodId()
                    )) {

                throw new RuntimeException(
                        "Teacher is already assigned to this period."
                );
            }

            // -------------------------------------------------
            // SAME CLASS + SECTION + SAME PERIOD
            // -------------------------------------------------
            if (old.getStudentClass()
                    .equalsIgnoreCase(
                            item.getStudentClass().trim()
                    )
                    && old.getSection()
                            .equalsIgnoreCase(
                                    item.getSection().trim()
                            )
                    && Objects.equals(
                            old.getPeriodId(),
                            item.getPeriodId()
                    )) {

                throw new RuntimeException(
                        "This class and section is already assigned to another teacher for this period."
                );
            }

            // -------------------------------------------------
            // SAME ROOM + SAME PERIOD
            // -------------------------------------------------
            if (item.getRoom() != null
                    && !item.getRoom().isBlank()
                    && old.getRoom() != null
                    && !old.getRoom().isBlank()
                    && old.getRoom()
                            .equalsIgnoreCase(
                                    item.getRoom().trim()
                            )
                    && Objects.equals(
                            old.getPeriodId(),
                            item.getPeriodId()
                    )) {

                throw new RuntimeException(
                        "Room "
                        + item.getRoom()
                        + " is already occupied in this period."
                );
            }
        }
    }

    // =========================================================
    // GET ALL ASSIGNMENTS FOR SCHOOL + SESSION + DAY
    // =========================================================
    public List<TeacherClassAssignment>
            getBySchoolSessionDay(
                    Long schoolId,
                    String academicYear,
                    String dayOfWeek
            ) {

        DayOfWeek day;

        try {

            day = DayOfWeek.valueOf(
                    dayOfWeek.trim().toUpperCase()
            );

        } catch (IllegalArgumentException e) {

            throw new RuntimeException(
                    "Invalid day of week: " + dayOfWeek
            );
        }

        return repository
                .findBySchoolIdAndAcademicYearAndDayOfWeek(
                        schoolId,
                        academicYear,
                        day
                );
    }

    // =========================================================
    // GET TEACHER DAY ASSIGNMENTS
    // =========================================================
    // public List<TeacherClassAssignment>
    //         getTeacherDayAssignments(
    //                 Long schoolId,
    //                 String academicYear,
    //                 Long teacherId,
    //                 String dayOfWeek
    //         ) {

    //     DayOfWeek day;

    //     try {

    //         day = DayOfWeek.valueOf(
    //                 dayOfWeek.trim().toUpperCase()
    //         );

    //     } catch (IllegalArgumentException e) {

    //         throw new RuntimeException(
    //                 "Invalid day of week: " + dayOfWeek
    //         );
    //     }

    //     return repository
    //             .findBySchoolIdAndAcademicYearAndTeacherIdAndDayOfWeek(
    //                     schoolId,
    //                     academicYear,
    //                     teacherId,
    //                     day
    //             );
    // }
// 
public List<TeacherClassAssignment> getTeacherDayAssignments(
        Long schoolId,
        String academicYear,
        Long teacherId,
        String dayOfWeek
) {

    // dayOfWeek nahi diya gaya
    // to teacher ke saare assignments return karo
    if (dayOfWeek == null || dayOfWeek.isBlank()) {

        return repository
                .findBySchoolIdAndAcademicYearAndTeacherId(
                        schoolId,
                        academicYear,
                        teacherId
                );
    }

    // dayOfWeek diya gaya hai
    DayOfWeek day = DayOfWeek.valueOf(
            dayOfWeek.trim().toUpperCase()
    );

    return repository
            .findBySchoolIdAndAcademicYearAndTeacherIdAndDayOfWeek(
                    schoolId,
                    academicYear,
                    teacherId,
                    day
            );
}
   
    @Transactional
    public void deleteAssignment(Long id) {

        TeacherClassAssignment assignment
                = repository.findById(id)
                        .orElseThrow(()
                                -> new RuntimeException(
                                "Assignment not found with id: "
                                + id
                        )
                        );

        repository.delete(assignment);
    }
}
