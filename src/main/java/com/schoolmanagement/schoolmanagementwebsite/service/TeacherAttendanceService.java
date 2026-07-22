package com.schoolmanagement.schoolmanagementwebsite.service;

import java.time.LocalDate;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;

import org.springframework.stereotype.Service;
import java.util.Map;

import com.schoolmanagement.schoolmanagementwebsite.dto.TeacherAttendanceRequestDTO;
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
    
}
