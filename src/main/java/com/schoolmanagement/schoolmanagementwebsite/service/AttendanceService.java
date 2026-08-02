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
    List<Attendance> attendanceList =
            attendanceRepository
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
}
