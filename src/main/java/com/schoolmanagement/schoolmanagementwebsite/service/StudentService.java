package com.schoolmanagement.schoolmanagementwebsite.service;

import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.nio.file.StandardCopyOption;
import java.util.HashSet;
import java.util.List;
import java.util.Map;
import java.util.Set;
import java.util.UUID;
import java.util.stream.Collectors;
import java.util.Objects;

import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.web.multipart.MultipartFile;

import com.schoolmanagement.schoolmanagementwebsite.dto.SectionShufflingDTO;
import com.schoolmanagement.schoolmanagementwebsite.dto.Student.RollNumberItemRequest;
import com.schoolmanagement.schoolmanagementwebsite.dto.Student.RollNumberUpdateRequest;
import com.schoolmanagement.schoolmanagementwebsite.entity.Student;
import com.schoolmanagement.schoolmanagementwebsite.entity.User;
import com.schoolmanagement.schoolmanagementwebsite.enums.Section;
import com.schoolmanagement.schoolmanagementwebsite.enums.Section;
import com.schoolmanagement.schoolmanagementwebsite.enums.StudentStatus;
import com.schoolmanagement.schoolmanagementwebsite.repository.SchoolRepository;
import com.schoolmanagement.schoolmanagementwebsite.repository.StudentRepository;
import com.schoolmanagement.schoolmanagementwebsite.repository.UserRepository;

import lombok.RequiredArgsConstructor;

@Service
@RequiredArgsConstructor
public class StudentService {

    private final StudentRepository studentRepository;
    private final UserRepository userRepository;
    private final SchoolRepository schoolRepository;

    public List<Student> searchStudents(
            String email,
            String academicYear,
            String studentClass,
            Section section,
            String search
    ) {

        User user = userRepository.findByEmail(email);
        if (user == null || user.getSchool() == null) {
            throw new RuntimeException("User or School not found");
        }

        Long schoolId = user.getSchool().getId();

        return studentRepository.searchStudents(
                schoolId,
                academicYear,
                studentClass,
                section,
                search
        );

    }

    // search student with these parameters 
    public List<Student> searchStudentDetails(
            String email,
            String academicYear,
            String admissionNumber,
            String studentName,
            String fatherName,
            String motherName,
            String mobile,
            String studentClass,
            String section
    ) {

        User user = userRepository.findByEmail(email);
        if (user == null || user.getSchool() == null) {
            throw new RuntimeException("User or School not found");
        }
        Long schoolId = user.getSchool().getId();

        return studentRepository.searchStudentDetails(
                schoolId,
                academicYear,
                admissionNumber,
                studentName,
                fatherName,
                motherName,
                mobile,
                studentClass,
                section
        );
    }

    public long getTotalStudents(Long schoolId) {
        return studentRepository.countBySchool_Id(schoolId);
    }

    public long getActiveStudents(Long schoolId) {
        return studentRepository.countBySchool_IdAndStatus(
                schoolId, StudentStatus.ACTIVE
        );
    }

    // ? Total students in DB
    public long getTotalStudents() {
        return studentRepository.count();
    }

    public List<Student> getAllStudents(String email) {

        User user = userRepository.findByEmail(email);

        if (user == null || user.getSchool() == null) {
            throw new RuntimeException("User or School not found");
        }

        Long schoolId = user.getSchool().getId();

        return studentRepository.findBySchool_Id(schoolId);
    }

    public Student getStudentByAdmissionNumber(String email, String admissionNumber) {

        User user = userRepository.findByEmail(email);
        System.out.println("Logged in email = " + email);

        if (user == null || user.getSchool() == null) {
            throw new RuntimeException("User or School not found");
        }

        Long schoolId = user.getSchool().getId();

        return studentRepository
                .findBySchool_IdAndAdmissionNumber(schoolId, admissionNumber)
                .orElseThrow(() -> new RuntimeException("Student Not Found"));
    }

    public Student getStudentBySessionAndAdmissionNo(
            String email,
            String academicYear,
            String admissionNumber) {

        System.out.println("Logged in email = " + email);

        User user = userRepository.findByEmail(email);
        System.out.println("Logged in email = " + email);

        if (user == null) {
            throw new RuntimeException("User or School not found");
        }

        Long schoolId = user.getSchool().getId();

        return studentRepository
                .findBySchool_IdAndAcademicYearAndAdmissionNumber(
                        schoolId,
                        academicYear,
                        admissionNumber
                )
                .orElseThrow(() -> new RuntimeException("Student not found"));
    }

    public Student updateStudent(
            String email,
            String admissionNumber,
            Student request,
            MultipartFile photo
    ) throws IOException {

        System.out.println("========== UPDATE REQUEST ==========");
        System.out.println("Request First Name : " + request.getFirstName());
        System.out.println("Request Last Name  : " + request.getLastName());
        System.out.println("Request Mobile     : " + request.getMobile());
        System.out.println("Request Email      : " + request.getEmail());
        System.out.println("Request Class      : " + request.getStudentClass());
        System.out.println("====================================");

        User user = userRepository.findByEmail(email);
        // System.out.println("Logged in email = " + email);

        if (user == null || user.getSchool() == null) {
            throw new RuntimeException("User or School not found");
        }

        Long schoolId = user.getSchool().getId();

        Student student = studentRepository
                .findBySchool_IdAndAdmissionNumber(schoolId, admissionNumber)
                .orElseThrow(() -> new RuntimeException("Student not found"));

        // Basic Details
        if (request.getFirstName() != null) {
            student.setFirstName(request.getFirstName());
        }

        if (request.getLastName() != null) {
            student.setLastName(request.getLastName());
        }

        if (request.getGender() != null) {
            student.setGender(request.getGender());
        }

        if (request.getDob() != null) {
            student.setDob(request.getDob());
        }

        if (request.getMobile() != null) {
            student.setMobile(request.getMobile());
        }

        // Academic
        if (request.getAcademicYear() != null) {
            student.setAcademicYear(request.getAcademicYear());
        }

        if (request.getStudentClass() != null) {
            student.setStudentClass(request.getStudentClass());
        }

        if (request.getSection() != null) {
            student.setSection(request.getSection());
        }

        // if (request.getRollNumber() != null)
        //     student.setRollNumber(request.getRollNumber());
        // Parents
        if (request.getFatherName() != null) {
            student.setFatherName(request.getFatherName());
        }

        if (request.getMotherName() != null) {
            student.setMotherName(request.getMotherName());
        }

        // if (request.getGuardianName() != null)
        //     student.setGuardianName(request.getGuardianName());
        // Address
        if (request.getHouseNo() != null) {
            student.setHouseNo(request.getHouseNo());
        }

        if (request.getStreet() != null) {
            student.setStreet(request.getStreet());
        }

        if (request.getTown() != null) {
            student.setTown(request.getTown());
        }

        if (request.getState() != null) {
            student.setState(request.getState());
        }

        if (request.getZip() != null) {
            student.setZip(request.getZip());
        }

        // Photo
        // if (request.getPhoto() != null)
        //     student.setPhoto(request.getPhoto());
        if (photo != null && !photo.isEmpty()) {

            String fileName = UUID.randomUUID() + "_" + photo.getOriginalFilename();

            Path path = Paths.get("uploads/student");

            Files.createDirectories(path);

            Files.copy(
                    photo.getInputStream(),
                    path.resolve(fileName),
                    StandardCopyOption.REPLACE_EXISTING
            );

            student.setPhoto(fileName);
        }

        System.out.println("========== BEFORE SAVE ==========");
        System.out.println("Student First Name : " + student.getFirstName());
        System.out.println("Student Last Name  : " + student.getLastName());
        System.out.println("Student Mobile     : " + student.getMobile());
        System.out.println("Student Email      : " + student.getEmail());
        System.out.println("=================================");

        // return studentRepository.save(student);
        Student savedStudent = studentRepository.save(student);

        System.out.println("========== AFTER SAVE ==========");
        System.out.println("ID : " + savedStudent.getId());
        System.out.println("First Name : " + savedStudent.getFirstName());
        System.out.println("Email : " + savedStudent.getEmail());

        return savedStudent;
    }

    public void sectionShuffling(SectionShufflingDTO request) {
        List<Student> students = studentRepository.findBySchoolIdAndAdmissionNumberIn(request.getSchoolId(), request.getAdmissionNumber());

        System.out.println("School Id : " + request.getSchoolId());
        System.out.println("Admission Numbers : " + request.getAdmissionNumber());
        System.out.println("Section : " + request.getSection());
        if (students.isEmpty()) {
            throw new RuntimeException("No Students Found");

        }
        if (students.size() != request.getAdmissionNumber().size()) {
            throw new RuntimeException("Some admission numbers are invalid.");
        }
        for (Student student : students) {
            student.setSection(request.getSection());

        }
        studentRepository.saveAll(students);
    }

    @Transactional
    public void updateRollNumbers(RollNumberUpdateRequest request) {

        if (request.getSchoolId() == null) {
            throw new RuntimeException("School ID is required");
        }

        if (request.getAcademicYear() == null
                || request.getAcademicYear().trim().isEmpty()) {
            throw new RuntimeException("Academic year is required");
        }

        if (request.getStudentClass() == null
                || request.getStudentClass().trim().isEmpty()) {
            throw new RuntimeException("Student class is required");
        }

        if (request.getSection() == null
                || request.getSection().trim().isEmpty()) {
            throw new RuntimeException("Section is required");
        }

        if (request.getStudents() == null
                || request.getStudents().isEmpty()) {
            throw new RuntimeException("No students provided");
        }

        // =====================================================
        // SECTION ENUM CONVERSION
        // =====================================================
        Section section;

        try {

            section = Section.valueOf(
                    request.getSection().trim().toUpperCase()
            );

        } catch (IllegalArgumentException e) {

            throw new RuntimeException(
                    "Invalid section: " + request.getSection()
            );
        }

        // =====================================================
        // ADMISSION NUMBERS
        // =====================================================
        List<String> admissionNumbers
                = request.getStudents()
                        .stream()
                        .map(RollNumberItemRequest::getAdmissionNumber)
                        .filter(Objects::nonNull)
                        .map(String::trim)
                        .toList();

        if (admissionNumbers.isEmpty()) {
            throw new RuntimeException(
                    "Admission numbers are required"
            );
        }

        // =====================================================
        // CHECK DUPLICATE ROLL NUMBERS
        // =====================================================
        List<Integer> rollNumbers
                = request.getStudents()
                        .stream()
                        .map(RollNumberItemRequest::getRollNumber)
                        .filter(Objects::nonNull)
                        .toList();

        Set<Integer> uniqueRollNumbers
                = new HashSet<>(rollNumbers);

        if (uniqueRollNumbers.size() != rollNumbers.size()) {

            throw new RuntimeException(
                    "Duplicate roll numbers are not allowed"
            );
        }

        // =====================================================
        // LOAD STUDENTS
        // =====================================================
        List<Student> students
                = studentRepository
                        .findBySchool_IdAndAcademicYearAndStudentClassAndSectionAndAdmissionNumberIn(
                                request.getSchoolId(),
                                request.getAcademicYear(),
                                request.getStudentClass(),
                                section,
                                admissionNumbers
                        );

        if (students.isEmpty()) {

            throw new RuntimeException(
                    "No students found for selected class and section"
            );
        }

        // =====================================================
        // CHECK ALL STUDENTS FOUND
        // =====================================================
        if (students.size() != admissionNumbers.size()) {

            Set<String> foundAdmissionNumbers
                    = students.stream()
                            .map(Student::getAdmissionNumber)
                            .collect(Collectors.toSet());

            List<String> missingStudents
                    = admissionNumbers.stream()
                            .filter(
                                    admissionNumber
                                    -> !foundAdmissionNumbers.contains(
                                            admissionNumber
                                    )
                            )
                            .toList();

            throw new RuntimeException(
                    "Some students were not found: "
                    + missingStudents
            );
        }

        // =====================================================
        // CREATE ADMISSION NUMBER -> ROLL NUMBER MAP
        // =====================================================
        Map<String, Integer> rollNumberMap
                = request.getStudents()
                        .stream()
                        .collect(
                                Collectors.toMap(
                                        item
                                        -> item.getAdmissionNumber().trim(),
                                        RollNumberItemRequest::getRollNumber
                                )
                        );

        // =====================================================
        // UPDATE
        // =====================================================
        for (Student student : students) {

            Integer rollNumber
                    = rollNumberMap.get(
                            student.getAdmissionNumber()
                    );

            if (rollNumber == null) {

                throw new RuntimeException(
                        "Roll number missing for admission number: "
                        + student.getAdmissionNumber()
                );
            }

            if (rollNumber <= 0) {

                throw new RuntimeException(
                        "Roll number must be greater than 0"
                );
            }

            student.setRollNumber(rollNumber);
        }

        // =====================================================
        // SAVE
        // =====================================================
        studentRepository.saveAll(students);
    }
}
