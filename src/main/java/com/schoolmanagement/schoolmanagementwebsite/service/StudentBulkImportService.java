package com.schoolmanagement.schoolmanagementwebsite.service;

import java.util.*;
import java.util.stream.Collectors;

import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;

import com.schoolmanagement.schoolmanagementwebsite.dto.StudentBulkImportRequest;
import com.schoolmanagement.schoolmanagementwebsite.dto.StudentImportItem;
import com.schoolmanagement.schoolmanagementwebsite.entity.School;
import com.schoolmanagement.schoolmanagementwebsite.entity.Student;
import com.schoolmanagement.schoolmanagementwebsite.entity.User;
import com.schoolmanagement.schoolmanagementwebsite.enums.Section;
import com.schoolmanagement.schoolmanagementwebsite.enums.StudentStatus;
import com.schoolmanagement.schoolmanagementwebsite.repository.SchoolRepository;
import com.schoolmanagement.schoolmanagementwebsite.repository.StudentRepository;
import com.schoolmanagement.schoolmanagementwebsite.repository.UserRepository;

import jakarta.transaction.Transactional;
import lombok.RequiredArgsConstructor;

@Service
@RequiredArgsConstructor
public class StudentBulkImportService {

    private final StudentRepository studentRepo;
    private final UserRepository userRepo;
    private final SchoolRepository schoolRepo;
    private final PasswordEncoder passwordEncoder;


    @Transactional
    public Map<String, Object> importStudents(
            StudentBulkImportRequest request) {

        // =========================================================
        // 1. BASIC VALIDATION
        // =========================================================

        if (request == null) {
            throw new RuntimeException("Import request is required");
        }

        if (request.getSchoolId() == null) {
            throw new RuntimeException("School ID is required");
        }

        if (isBlank(request.getAcademicYear())) {
            throw new RuntimeException("Academic year is required");
        }

        if (isBlank(request.getStudentClass())) {
            throw new RuntimeException("Student class is required");
        }

        if (isBlank(request.getSection())) {
            throw new RuntimeException("Section is required");
        }

        if (request.getStudents() == null ||
                request.getStudents().isEmpty()) {

            throw new RuntimeException(
                    "No students found in Excel file"
            );
        }


        // =========================================================
        // 2. MAX IMPORT LIMIT
        // =========================================================

        if (request.getStudents().size() > 500) {

            throw new RuntimeException(
                    "Maximum 500 students can be imported at once"
            );
        }


        // =========================================================
        // 3. FIND SCHOOL
        // =========================================================

        School school = schoolRepo.findById(request.getSchoolId())
                .orElseThrow(() ->
                        new RuntimeException(
                                "School not found with ID: "
                                        + request.getSchoolId()
                        )
                );


        // =========================================================
        // 4. CONVERT SECTION ENUM
        // =========================================================

        Section section;

        try {

            section = Section.valueOf(
                    request.getSection()
                            .trim()
                            .toUpperCase()
            );

        } catch (IllegalArgumentException e) {

            throw new RuntimeException(
                    "Invalid section: "
                            + request.getSection()
            );
        }


        // =========================================================
        // 5. EXCEL DUPLICATE CHECK
        // =========================================================

        Set<String> admissionNumbers = new HashSet<>();

        Set<String> usernames = new HashSet<>();


        for (StudentImportItem item : request.getStudents()) {

            if (isBlank(item.getAdmissionNumber())) {

                throw new RuntimeException(
                        "Admission Number is required"
                );
            }

            String admissionNumber =
                    item.getAdmissionNumber().trim();

            item.setAdmissionNumber(admissionNumber);


            // Duplicate admission number inside Excel

            if (!admissionNumbers.add(admissionNumber)) {

                throw new RuntimeException(
                        "Duplicate Admission Number in Excel: "
                                + admissionNumber
                );
            }


            // Student mobile will be username

            if (isBlank(item.getMobile())) {

                throw new RuntimeException(
                        "Student Mobile is required for Admission Number: "
                                + admissionNumber
                );
            }

            String username =
                    item.getMobile().trim();

            item.setMobile(username);


            // Duplicate username inside Excel

            if (!usernames.add(username)) {

                throw new RuntimeException(
                        "Duplicate Student Mobile/Username in Excel: "
                                + username
                );
            }
        }


        // =========================================================
        // 6. CHECK EXISTING STUDENTS IN DATABASE
        // =========================================================

        List<Student> existingStudents =
                studentRepo.findBySchoolIdAndAdmissionNumberIn(
                        request.getSchoolId(),
                        new ArrayList<>(admissionNumbers)
                );


        if (!existingStudents.isEmpty()) {

            String duplicates =
                    existingStudents.stream()
                            .map(Student::getAdmissionNumber)
                            .collect(Collectors.joining(", "));

            throw new RuntimeException(
                    "These Admission Numbers already exist: "
                            + duplicates
            );
        }


        // =========================================================
        // 7. CHECK EXISTING USERS
        // =========================================================

        List<User> existingUsers =
                userRepo.findByUsernameIn(
                        new ArrayList<>(usernames)
                );


        if (!existingUsers.isEmpty()) {

            String duplicates =
                    existingUsers.stream()
                            .map(User::getUsername)
                            .collect(Collectors.joining(", "));

            throw new RuntimeException(
                    "These Student usernames/mobile numbers already exist: "
                            + duplicates
            );
        }


        // =========================================================
        // 8. CREATE STUDENTS
        // =========================================================

        List<Student> students = new ArrayList<>();


        for (StudentImportItem item : request.getStudents()) {

            validateStudentRow(item);


            Student student = Student.builder()

                    .admissionNumber(
                            item.getAdmissionNumber()
                    )

                    .firstName(
                            trim(item.getFirstName())
                    )

                    .middleName(
                            trim(item.getMiddleName())
                    )

                    .lastName(
                            trim(item.getLastName())
                    )

                    .dob(
                            trim(item.getDateOfBirth())
                    )

                    .gender(
                            trim(item.getGender())
                    )

                    .age(
                            trim(item.getAge())
                    )

                    .studentClass(
                            request.getStudentClass().trim()
                    )

                    .nationality(
                            trim(item.getNationality())
                    )

                    .motherTongue(
                            trim(item.getMotherTongue())
                    )

                    .religion(
                            trim(item.getReligion())
                    )

                    .category(
                            trim(item.getCategory())
                    )

                    .caste(
                            trim(item.getCaste())
                    )

                    .bloodGroup(
                            trim(item.getBloodGroup())
                    )

                    .transportRequired(
                            trim(item.getTransportRequired())
                    )

                    .email(
                            trim(item.getEmail())
                    )

                    .mobile(
                            trim(item.getMobile())
                    )

                    .feeCategory(
                            trim(item.getFeeCategory())
                    )

                    .feeBatch(
                            trim(item.getFeeBatch())
                    )

                    // Father

                    .fatherName(
                            trim(item.getFatherName())
                    )

                    .fatherMobile(
                            trim(item.getFatherMobile())
                    )

                    .fatherEmail(
                            trim(item.getFatherEmail())
                    )

                    .fatherOccupation(
                            trim(item.getFatherOccupation())
                    )

                    // Mother

                    .motherName(
                            trim(item.getMotherName())
                    )

                    .motherMobile(
                            trim(item.getMotherMobile())
                    )

                    .motherEmail(
                            trim(item.getMotherEmail())
                    )

                    .motherOccupation(
                            trim(item.getMotherOccupation())
                    )

                    // Address

                    .houseNo(
                            trim(item.getHouseNo())
                    )

                    .street(
                            trim(item.getStreet())
                    )

                    .area(
                            trim(item.getArea())
                    )

                    .town(
                            trim(item.getTown())
                    )

                    .city(
                            trim(item.getCity())
                    )

                    .state(
                            trim(item.getState())
                    )

                    .country(
                            trim(item.getCountry())
                    )

                    .zip(
                            trim(item.getZip())
                    )

                    // Academic

                    .academicYear(
                            request.getAcademicYear().trim()
                    )

                    .section(section)

                    .rollNumber(
                            item.getRollNumber()
                    )

                    // New direct bulk student

                    .school(school)

                    // No admission because this
                    // student is directly imported

                    .admission(null)

                    .status(
                            StudentStatus.ACTIVE
                    )

                    .build();


            students.add(student);
        }


        // =========================================================
        // 9. SAVE ALL STUDENTS
        // =========================================================

        List<Student> savedStudents =
                studentRepo.saveAll(students);


        // =========================================================
        // 10. CREATE USER ACCOUNTS
        // =========================================================

        List<User> users = new ArrayList<>();


        for (int i = 0; i < savedStudents.size(); i++) {

            Student student = savedStudents.get(i);

            StudentImportItem item =
                    request.getStudents().get(i);


            User user =
                    createStudentUser(
                            item,
                            student,
                            school
                    );


            users.add(user);
        }


        // =========================================================
        // 11. SAVE ALL USERS
        // =========================================================

        userRepo.saveAll(users);


        // =========================================================
        // 12. RESPONSE
        // =========================================================

        Map<String, Object> response =
                new LinkedHashMap<>();

        response.put(
                "success",
                true
        );

        response.put(
                "message",
                "Students imported successfully"
        );

        response.put(
                "schoolId",
                request.getSchoolId()
        );

        response.put(
                "academicYear",
                request.getAcademicYear()
        );

        response.put(
                "studentClass",
                request.getStudentClass()
        );

        response.put(
                "section",
                request.getSection()
        );

        response.put(
                "studentsImported",
                savedStudents.size()
        );

        response.put(
                "accountsCreated",
                users.size()
        );

        return response;
    }


    // =============================================================
    // CREATE STUDENT USER
    // =============================================================

    private User createStudentUser(
            StudentImportItem item,
            Student student,
            School school) {


        String username =
                trim(item.getMobile());


        if (username.isEmpty()) {

            throw new RuntimeException(
                    "Student Mobile is required for Admission Number: "
                            + student.getAdmissionNumber()
            );
        }


        // Same password pattern as your existing
        // StudentCreationService

        String rawPassword =
                student.getFirstName()
                        + "@"
                        + student.getAdmissionNumber();


        User user = new User();


        // Login username

        user.setUsername(username);


        // Student full name

        String fullName =
                (
                        trim(student.getFirstName())
                                + " "
                                + trim(student.getLastName())
                ).trim();


        user.setName(fullName);


        // Email

        String email =
                trim(student.getEmail());


        /*
         * Your User entity has:
         *
         * @Column(unique = true, nullable = false)
         * private String email;
         *
         * Therefore email cannot be null.
         */

        if (email.isEmpty()) {

            // If student email is not provided,
            // username/mobile will be used.

            email = username;
        }


        user.setEmail(email);


        // Password

        user.setPassword(
                passwordEncoder.encode(rawPassword)
        );


        // Role

        user.setRole("STUDENT");


        // Phone

        user.setPhone(username);


        // School

        user.setSchool(school);


        // Status

        user.setStatus("Active");


        /*
         * IMPORTANT:
         *
         * User entity does NOT have:
         *
         * private Student student;
         *
         * Therefore:
         *
         * user.setStudent(student);
         *
         * WILL NOT BE USED.
         */


        return user;
    }


    // =============================================================
    // ROW VALIDATION
    // =============================================================

    private void validateStudentRow(
            StudentImportItem item) {


        String admissionNumber =
                trim(item.getAdmissionNumber());


        if (admissionNumber.isEmpty()) {

            throw new RuntimeException(
                    "Admission Number is required"
            );
        }


        if (trim(item.getFirstName()).isEmpty()) {

            throw new RuntimeException(
                    "First Name is required for Admission Number: "
                            + admissionNumber
            );
        }


        if (trim(item.getDateOfBirth()).isEmpty()) {

            throw new RuntimeException(
                    "Date of Birth is required for Admission Number: "
                            + admissionNumber
            );
        }


        if (trim(item.getGender()).isEmpty()) {

            throw new RuntimeException(
                    "Gender is required for Admission Number: "
                            + admissionNumber
            );
        }


        // Father mobile validation

        validateMobile(
                item.getFatherMobile(),
                "Father Mobile",
                admissionNumber
        );


        // Mother mobile validation

        validateMobile(
                item.getMotherMobile(),
                "Mother Mobile",
                admissionNumber
        );


        // Student mobile validation

        validateMobile(
                item.getMobile(),
                "Mobile",
                admissionNumber
        );


        // Transport

        String transport =
                trim(item.getTransportRequired());


        if (!transport.isEmpty()
                && !transport.equalsIgnoreCase("Yes")
                && !transport.equalsIgnoreCase("No")) {

            throw new RuntimeException(
                    "Transport Required must be Yes or No for Admission Number: "
                            + admissionNumber
            );
        }


        // Roll number

        if (item.getRollNumber() != null
                && item.getRollNumber() < 0) {

            throw new RuntimeException(
                    "Roll Number cannot be negative for Admission Number: "
                            + admissionNumber
            );
        }
    }


    // =============================================================
    // MOBILE VALIDATION
    // =============================================================

    private void validateMobile(
            String mobile,
            String fieldName,
            String admissionNumber) {


        if (isBlank(mobile)) {
            return;
        }


        String value = mobile.trim();


        if (!value.matches("\\d{10}")) {

            throw new RuntimeException(
                    fieldName
                            + " must contain exactly 10 digits for Admission Number: "
                            + admissionNumber
            );
        }
    }


    // =============================================================
    // STRING HELPERS
    // =============================================================

    private String trim(String value) {

        return value == null
                ? ""
                : value.trim();
    }


    private boolean isBlank(String value) {

        return value == null
                || value.trim().isEmpty();
    }
}