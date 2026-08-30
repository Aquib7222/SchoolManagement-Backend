package com.schoolmanagement.schoolmanagementwebsite.service;

import com.schoolmanagement.schoolmanagementwebsite.dto.AttendanceItem;
import com.schoolmanagement.schoolmanagementwebsite.dto.AttendanceReportDTO;
import com.schoolmanagement.schoolmanagementwebsite.dto.AttendanceRequest;
import com.schoolmanagement.schoolmanagementwebsite.dto.DailyAttendanceReportDTO;
import com.schoolmanagement.schoolmanagementwebsite.entity.Attendance;
import com.schoolmanagement.schoolmanagementwebsite.entity.Student;
import com.schoolmanagement.schoolmanagementwebsite.entity.User;
import com.schoolmanagement.schoolmanagementwebsite.repository.AttendanceRepository;
import com.schoolmanagement.schoolmanagementwebsite.repository.StudentRepository;
import com.schoolmanagement.schoolmanagementwebsite.repository.UserRepository;

import lombok.RequiredArgsConstructor;

import org.springframework.security.core.Authentication;
import org.springframework.stereotype.Service;

import java.time.LocalDate;
import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.LinkedHashMap;
import java.util.List;
import java.util.Map;

import com.schoolmanagement.schoolmanagementwebsite.enums.AttendanceStatus;
import com.schoolmanagement.schoolmanagementwebsite.enums.Section;
import java.time.Month;

@Service
@RequiredArgsConstructor
public class AttendanceService {

    private final AttendanceRepository attendanceRepository;
    private final UserRepository userRepository;
    private final StudentRepository studentRepository;

    public void saveAttendance(AttendanceRequest request,
            Authentication authentication) {

        String email = authentication.getName();

        User user = userRepository.findByEmail(email);
        if (user == null) {
            throw new RuntimeException("User not found");
        }

        Long schoolId = user.getSchool().getId();

        String fullName = user.getName();

        for (AttendanceItem item : request.getAttendance()) {

            Attendance attendance = attendanceRepository
                    .findBySchoolIdAndStudentIdAndAttendanceDate(
                            schoolId,
                            item.getStudentId(),
                            request.getAttendanceDate()
                    )
                    .orElse(null);

            // ==========================
            // INSERT
            // ==========================
            if (attendance == null) {

                attendance = new Attendance();

                attendance.setSchoolId(schoolId);
                attendance.setStudentId(item.getStudentId());
                attendance.setAdmissionNumber(item.getAdmissionNumber());

                attendance.setAcademicYear(request.getAcademicYear());
                attendance.setStudentClass(request.getStudentClass());
                attendance.setSection(request.getSection());

                attendance.setAttendanceDate(request.getAttendanceDate());
                attendance.setStatus(item.getStatus());

                attendance.setCreatedByUserId(user.getId());
                attendance.setCreatedBy(fullName);
                attendance.setCreatedAt(LocalDateTime.now());

            } // ==========================
            // UPDATE
            // ==========================
            else {

                attendance.setStatus(item.getStatus());

                attendance.setUpdatedByUserId(user.getId());
                attendance.setUpdatedBy(fullName);
                attendance.setUpdatedAt(LocalDateTime.now());

            }

            attendanceRepository.save(attendance);

        }

    }

    public List<DailyAttendanceReportDTO> getClassAttendance(
            String academicYear,
            String studentClass,
            Section section,
            String attendanceDate,
            Authentication authentication) {

        String email = authentication.getName();

        User user = userRepository.findByEmail(email);

        if (user == null) {
            throw new RuntimeException("User not found");
        }

        Long schoolId = user.getSchool().getId();

        // Attendance List
        List<Attendance> attendanceList
                = attendanceRepository
                        .findBySchoolIdAndAcademicYearAndStudentClassAndSectionAndAttendanceDate(
                                schoolId,
                                academicYear,
                                studentClass,
                                section,
                                LocalDate.parse(attendanceDate)
                        );

        List<DailyAttendanceReportDTO> result = new ArrayList<>();

        for (Attendance attendance : attendanceList) {

            Student student = studentRepository
                    .findById(attendance.getStudentId())
                    .orElse(null);

            if (student == null) {
                continue;
            }

            DailyAttendanceReportDTO dto = new DailyAttendanceReportDTO();

            dto.setStudentId(student.getId());
            dto.setAdmissionNumber(student.getAdmissionNumber());
            dto.setStudentName(student.getFirstName() + " " + student.getLastName());
            dto.setStatus(attendance.getStatus());

            result.add(dto);
        }

        return result;
    }

//     monthly attendance 
    public List<AttendanceReportDTO> getMonthlyAttendance(
            String academicYear,
            String studentClass,
            Section section,
            Month month,
            Authentication authentication
    ) {

        String email = authentication.getName();

        User user = userRepository.findByEmail(email);

        if (user == null) {
            throw new RuntimeException("User not found");
        }

        Long schoolId = user.getSchool().getId();

        int year = Integer.parseInt(academicYear.split("-")[0]);

        LocalDate fromDate = LocalDate.of(year, month, 1);

        LocalDate toDate = fromDate.withDayOfMonth(fromDate.lengthOfMonth());

        // All Students
        List<Student> students
                = studentRepository.findBySchool_IdAndAcademicYearAndStudentClassAndSection(
                        schoolId,
                        academicYear,
                        studentClass,
                        section
                );

        // Monthly Attendance
        List<Attendance> attendanceList
                = attendanceRepository
                        .findBySchoolIdAndAcademicYearAndStudentClassAndSectionAndAttendanceDateBetween(
                                schoolId,
                                academicYear,
                                studentClass,
                                section,
                                fromDate,
                                toDate
                        );

        Map<Long, AttendanceReportDTO> report = new LinkedHashMap<>();

        // Create DTO for every student
        for (Student student : students) {

            AttendanceReportDTO dto = new AttendanceReportDTO();

            dto.setStudentId(student.getId());

            dto.setAdmissionNumber(student.getAdmissionNumber());

            dto.setStudentName(
                    student.getFirstName() + " " + student.getLastName()
            );

            report.put(student.getId(), dto);
        }

        // Fill Attendance
        for (Attendance attendance : attendanceList) {

            AttendanceReportDTO dto
                    = report.get(attendance.getStudentId());

            if (dto == null) {
                continue;
            }

            int day
                    = attendance.getAttendanceDate().getDayOfMonth();

            dto.getAttendance().put(day, attendance.getStatus());

            switch (attendance.getStatus()) {

                case PRESENT ->
                    dto.setPresent(dto.getPresent() + 1);

                case ABSENT ->
                    dto.setAbsent(dto.getAbsent() + 1);

                case LEAVE ->
                    dto.setLeave(dto.getLeave() + 1);

                case HALF_DAY ->
                    dto.setHalfDay(dto.getHalfDay() + 1);
            }
        }

        return new ArrayList<>(report.values());

    }

    private String getCurrentAcademicYear(LocalDate date) {

    int year = date.getYear();

    if (date.getMonthValue() >= 4) {
        return year + "-" + (year + 1);
    }

    return (year - 1) + "-" + year;
}

 public AttendanceReportDTO getCurrentMonthAttendance(
        Long schoolId,
        String admissionNumber
) {

    LocalDate today = LocalDate.now();

    // Current month ka first day
    LocalDate startDate = today.withDayOfMonth(1);

    // Aaj tak
    LocalDate endDate = today;

    // Current academic year
    String academicYear = getCurrentAcademicYear(today);

    // Current month
    String month = today.getMonth().name();

    // ================= STUDENT =================

    Student student = studentRepository
            .findBySchool_IdAndAdmissionNumber(
                    schoolId,
                    admissionNumber
            )
            .orElseThrow(() ->
                    new RuntimeException("Student not found")
            );

    // ================= ATTENDANCE =================

    List<Attendance> attendanceList =
            attendanceRepository
                    .findBySchoolIdAndAdmissionNumberAndAcademicYearAndAttendanceDateBetween(
                            schoolId,
                            admissionNumber,
                            academicYear,
                            startDate,
                            endDate
                    );

    AttendanceReportDTO report = new AttendanceReportDTO();

    report.setStudentId(student.getId());
    report.setAdmissionNumber(student.getAdmissionNumber());
    report.setStudentName(student.getFirstName()+" "+student.getLastName());
    report.setMonth(month);

    int present = 0;
    int absent = 0;
    int leave = 0;
    int halfDay = 0;

    // ================= COUNT ATTENDANCE =================

    for (Attendance attendance : attendanceList) {

        if (attendance.getAttendanceDate() == null) {
            continue;
        }

        int day =
                attendance.getAttendanceDate()
                        .getDayOfMonth();

        AttendanceStatus status =
                attendance.getStatus();

        // Day wise attendance
        report.getAttendance().put(day, status);

        if (status == null) {
            continue;
        }

        switch (status) {

            case PRESENT:
                present++;
                break;

            case ABSENT:
                absent++;
                break;

            case LEAVE:
                leave++;
                break;

            case HALF_DAY:
                halfDay++;
                break;

            default:
                break;
        }
    }

    // ================= WORKING DAYS =================

    /*
     * Attendance record sirf working day par create hota hai.
     *
     * Isliye totalDays = Present + Absent + Leave + HalfDay
     *
     * Sunday / holiday ka record nahi hoga,
     * isliye denominator me nahi aayega.
     */

    int totalDays =
            present
            + absent
            + leave
            + halfDay;

    // ================= PERCENTAGE =================

    double attendancePercentage = 0.0;

    if (totalDays > 0) {

        // Half day ko 0.5 present maana
        double effectivePresent =
                present + (halfDay * 0.5);

        attendancePercentage =
                (effectivePresent / totalDays) * 100;

        // 2 decimal places
        attendancePercentage =
                Math.round(attendancePercentage * 100.0)
                        / 100.0;
    }

    // ================= SET RESPONSE =================

    report.setTotalDays(totalDays);

    report.setPresent(present);

    report.setAbsent(absent);

    report.setLeave(leave);

    report.setHalfDay(halfDay);

    report.setAttendancePercentage(
            attendancePercentage
    );

    return report;
}

 public List<Attendance> getAttendanceBySchoolId(Long schoolId) {

        if (schoolId == null) {
            throw new RuntimeException("School ID is required");
        }

        return attendanceRepository.findBySchoolId(schoolId);
    }
}
