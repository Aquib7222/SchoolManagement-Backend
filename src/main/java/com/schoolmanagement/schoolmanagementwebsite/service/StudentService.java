package com.schoolmanagement.schoolmanagementwebsite.service;

import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.nio.file.StandardCopyOption;
import java.util.List;
import java.util.UUID;

import org.springframework.stereotype.Service;
import org.springframework.web.multipart.MultipartFile;

import com.schoolmanagement.schoolmanagementwebsite.entity.School;
import com.schoolmanagement.schoolmanagementwebsite.entity.Student;
import com.schoolmanagement.schoolmanagementwebsite.entity.User;
import com.schoolmanagement.schoolmanagementwebsite.enums.StudentStatus;
import com.schoolmanagement.schoolmanagementwebsite.repository.StudentRepository;
import com.schoolmanagement.schoolmanagementwebsite.repository.UserRepository;
import com.schoolmanagement.schoolmanagementwebsite.repository.SchoolRepository;

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
            String section,
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

    // ✅ Total students in DB
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
}
