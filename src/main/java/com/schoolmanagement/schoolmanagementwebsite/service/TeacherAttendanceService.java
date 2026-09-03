package com.schoolmanagement.schoolmanagementwebsite.service;

import java.time.LocalDate;
import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;

import org.springframework.stereotype.Service;
import java.util.Map;

import com.schoolmanagement.schoolmanagementwebsite.dto.TeacherAttendanceRequestDTO;
import com.schoolmanagement.schoolmanagementwebsite.dto.TeacherAttendanceResponseDTO;
import com.schoolmanagement.schoolmanagementwebsite.dto.TeacherMonthlyAttendanceDTO;
import com.schoolmanagement.schoolmanagementwebsite.entity.School;
import com.schoolmanagement.schoolmanagementwebsite.entity.Teacher;
import com.schoolmanagement.schoolmanagementwebsite.entity.TeacherAttendance;
import com.schoolmanagement.schoolmanagementwebsite.enums.TeacherAttendanceStatus;
import com.schoolmanagement.schoolmanagementwebsite.repository.SchoolRepository;
import com.schoolmanagement.schoolmanagementwebsite.repository.TeacherAttendanceRepository;
import com.schoolmanagement.schoolmanagementwebsite.repository.TeacherRepository;

import lombok.RequiredArgsConstructor;

@Service
@RequiredArgsConstructor
public class TeacherAttendanceService {

    private final TeacherAttendanceRepository attendanceRepo;
    private final TeacherRepository teacherRepo;
    private final SchoolRepository schoolRepo;

    
    // ✅ SINGLE / SELECTED / ALL (same logic)
    public void saveAttendance(
            Long schoolId,
            LocalDate date,
            List<TeacherAttendanceRequestDTO> requests) {

        School school = schoolRepo.findById(schoolId)
                .orElseThrow(() -> new RuntimeException("School not found"));

        for (TeacherAttendanceRequestDTO dto : requests) {

            Teacher teacher = teacherRepo.findById(dto.getTeacherId())
                    .orElseThrow(() -> new RuntimeException("Teacher not found"));

            TeacherAttendance attendance =
                    attendanceRepo
                            .findByTeacherIdAndAttendanceDate(
                                    teacher.getId(), date)
                            .orElse(new TeacherAttendance());

            attendance.setTeacher(teacher);
            attendance.setSchool(school);
            attendance.setAttendanceDate(date);
            attendance.setStatus(
                    TeacherAttendanceStatus.valueOf(dto.getStatus()));

            attendanceRepo.save(attendance);
        }
    }

    // ✅ FETCH DAY ATTENDANCE
    public List<TeacherAttendance> getAttendanceByDate(
            Long schoolId, LocalDate date) {

        return attendanceRepo.findBySchoolIdAndAttendanceDate(schoolId, date);
    }

    // ✅ MONTHLY ATTENDANCE (Calendar View)
    public List<TeacherAttendance> getTeacherAttendance(Long teacherId) {
        return attendanceRepo.findByTeacherId(teacherId);
    }
    
    public List<TeacherMonthlyAttendanceDTO> getMonthlyReport(
            Long schoolId, int year, int month) {

        LocalDate start = LocalDate.of(year, month, 1);
        LocalDate end = start.withDayOfMonth(start.lengthOfMonth());

        List<TeacherAttendance> records =
                attendanceRepo.findMonthlyAttendance(
                        schoolId, start, end
                );

        Map<Long, TeacherMonthlyAttendanceDTO> map = new HashMap<>();

        for (TeacherAttendance a : records) {
            Long teacherId = a.getTeacher().getId();

            map.putIfAbsent(
                    teacherId,
                    new TeacherMonthlyAttendanceDTO(
                            teacherId,
                            a.getTeacher().getFirstName() + " " +
                            a.getTeacher().getLastName(),
                            0, 0, 0, 0
                    )
            );

            TeacherMonthlyAttendanceDTO dto = map.get(teacherId);

            switch (a.getStatus()) {
                case PRESENT -> dto.setPresent(dto.getPresent() + 1);
                case ABSENT -> dto.setAbsent(dto.getAbsent() + 1);
                case LEAVE -> dto.setLeave(dto.getLeave() + 1);
                case HALF_DAY -> dto.setHalfDay(dto.getHalfDay() + 1);
            }
        }

        return new ArrayList<>(map.values());
    }

public TeacherAttendanceResponseDTO checkIn(
        Long teacherId,
        Long schoolId) {

    LocalDate today = LocalDate.now();

    Teacher teacher = teacherRepo.findById(teacherId)
            .orElseThrow(() ->
                    new RuntimeException("Teacher not found"));

    School school = schoolRepo.findById(schoolId)
            .orElseThrow(() ->
                    new RuntimeException("School not found"));

    TeacherAttendance attendance =
            attendanceRepo
                    .findByTeacherIdAndSchoolIdAndAttendanceDate(
                            teacherId,
                            schoolId,
                            today
                    )
                    .orElseGet(() -> {

                        TeacherAttendance a =
                                new TeacherAttendance();

                        a.setTeacher(teacher);
                        a.setSchool(school);
                        a.setAttendanceDate(today);

                        return a;
                    });

    if (attendance.getCheckInTime() != null) {
        throw new RuntimeException(
                "Teacher already checked in today"
        );
    }

    attendance.setCheckInTime(
            LocalDateTime.now()
    );

    attendance.setStatus(
            TeacherAttendanceStatus.PRESENT
    );

    TeacherAttendance saved =
            attendanceRepo.save(attendance);

    return toDTO(saved);
}
public TeacherAttendanceResponseDTO checkOut(
        Long teacherId,
        Long schoolId) {

    LocalDate today = LocalDate.now();

    TeacherAttendance attendance =
            attendanceRepo
                    .findByTeacherIdAndSchoolIdAndAttendanceDate(
                            teacherId,
                            schoolId,
                            today
                    )
                    .orElseThrow(() ->
                            new RuntimeException(
                                    "Today's attendance not found"
                            ));

    if (attendance.getCheckInTime() == null) {
        throw new RuntimeException(
                "Please check in first"
        );
    }

    if (attendance.getCheckOutTime() != null) {
        throw new RuntimeException(
                "Teacher already checked out today"
        );
    }

    attendance.setCheckOutTime(
            LocalDateTime.now()
    );

    TeacherAttendance saved =
            attendanceRepo.save(attendance);

    return toDTO(saved);
}
private TeacherAttendanceResponseDTO toDTO(
        TeacherAttendance attendance) {

    Teacher teacher = attendance.getTeacher();

    String teacherName =
            ((teacher.getFirstName() != null
                    ? teacher.getFirstName()
                    : "")
            + " "
            + (teacher.getLastName() != null
                    ? teacher.getLastName()
                    : ""))
            .trim();

    return new TeacherAttendanceResponseDTO(
            attendance.getId(),
            teacher.getId(),
            teacherName,
            attendance.getSchool().getId(),
            attendance.getAttendanceDate(),
            attendance.getStatus() != null
                    ? attendance.getStatus().name()
                    : null,
            attendance.getCheckInTime(),
            attendance.getCheckOutTime()
    );

}


public TeacherAttendanceResponseDTO getTodayAttendance(
        Long teacherId,
        Long schoolId) {

    TeacherAttendance attendance =
            attendanceRepo
                    .findByTeacherIdAndSchoolIdAndAttendanceDate(
                            teacherId,
                            schoolId,
                            LocalDate.now()
                    )
                    .orElse(null);

    if (attendance == null) {
        return null;
    }

    Teacher teacher = attendance.getTeacher();

    String teacherName =
            ((teacher.getFirstName() != null
                    ? teacher.getFirstName()
                    : "")
            + " "
            + (teacher.getLastName() != null
                    ? teacher.getLastName()
                    : ""))
            .trim();

    return new TeacherAttendanceResponseDTO(
            attendance.getId(),
            teacher.getId(),
            teacherName,
            attendance.getSchool().getId(),
            attendance.getAttendanceDate(),
            attendance.getStatus() != null
                    ? attendance.getStatus().name()
                    : null,
            attendance.getCheckInTime(),
            attendance.getCheckOutTime()
    );
}
}
