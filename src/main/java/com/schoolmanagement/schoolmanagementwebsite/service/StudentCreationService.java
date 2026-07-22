package com.schoolmanagement.schoolmanagementwebsite.service;

import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;

import com.schoolmanagement.schoolmanagementwebsite.dto.CreateStudentRequest;
import com.schoolmanagement.schoolmanagementwebsite.entity.Admission;
import com.schoolmanagement.schoolmanagementwebsite.entity.Student;
import com.schoolmanagement.schoolmanagementwebsite.entity.User;
import com.schoolmanagement.schoolmanagementwebsite.enums.AdmissionStatus;
import com.schoolmanagement.schoolmanagementwebsite.enums.StudentStatus;
import com.schoolmanagement.schoolmanagementwebsite.repository.AdmissionRepository;
import com.schoolmanagement.schoolmanagementwebsite.repository.StudentRepository;
import com.schoolmanagement.schoolmanagementwebsite.repository.UserRepository;

import jakarta.transaction.Transactional;
import lombok.RequiredArgsConstructor;

@Service
@RequiredArgsConstructor
public class StudentCreationService {

    private final AdmissionRepository admissionRepo;
    private final StudentRepository studentRepo;
    private final UserRepository userRepo;
    private final PasswordEncoder passwordEncoder;

    @Transactional
    public void createStudentAndAccount(CreateStudentRequest req) {

        Admission admission = admissionRepo.findById(req.getAdmissionId())
                .orElseThrow(() -> new RuntimeException("Admission not found"));

        // ✅ Only FEE_PAID admissions allowed
        if (admission.getStatus() != AdmissionStatus.FEE_PAID) {
            throw new RuntimeException("Admission fee not paid");
        }

        // ❌ Prevent duplicate student
        if (studentRepo.existsByAdmission(admission)) {
            throw new RuntimeException("Student already created");
        }

        //    ❌ Prevent duplicate STUDENT login
        if (userRepo.existsByEmailAndRole(req.getUsername(), "STUDENT")) {
            throw new RuntimeException("Student login already exists");
        }

        // ================= CREATE STUDENT =================
        Student student = Student.builder()
                .admissionNumber(admission.getAdmissionNumber())
                .academicYear(admission.getAcademicYear())
                .studentClass(admission.getStudentClass())
                .firstName(admission.getFirstName())

                .middleName(admission.getMiddleName())
                .lastName(admission.getLastName())
                .nationality(admission.getNationality())
                .motherTongue(admission.getMotherTongue())
                .religion(admission.getReligion())
                .category(admission.getCategory())
                .caste(admission.getCaste())
                .bloodGroup(admission.getBloodGroup())
                
                .fatherName(admission.getFatherName())
                .fatherMobile(admission.getFatherMobile())
                .fatherEmail(admission.getFatherEmail())
                .fatherOccupation(admission.getFatherOccupation())
                .motherName(admission.getMotherName())
                .motherMobile(admission.getMotherMobile())
                .motherEmail(admission.getMotherEmail())
                .motherOccupation(admission.getMotherOccupation())
                .houseNo(admission.getHouseNo())
                .street(admission.getStreet())
                .area(admission.getArea())
                .town(admission.getTown())
                .city(admission.getCity())
                .state(admission.getState())
                .country(admission.getCountry())
                .zip(admission.getZip())
                .email(admission.getEmail())
              
                .school(admission.getSchool())
                .admission(admission)
                .status(StudentStatus.ACTIVE)   // ✅ SAVE STATUS
                
                .build();

        studentRepo.save(student);

        // ================= AUTO PASSWORD =================
        String rawPassword
                = admission.getFirstName() + "@" + admission.getAdmissionNumber();

        // ================= CREATE USER LOGIN =================
        User user = new User();
        user.setName(admission.getFirstName() + " " + admission.getLastName());
        user.setEmail(req.getUsername());
        user.setPassword(passwordEncoder.encode(rawPassword)); // 🔐 BCrypt
        user.setRole("STUDENT");
        user.setPhone(admission.getPreferredNo());
        user.setSchool(admission.getSchool());
        user.setStatus("Active");

        userRepo.save(user);

        // ================= UPDATE ADMISSION STATUS =================
        admission.setStatus(AdmissionStatus.ENROLLED);
        admissionRepo.save(admission);
    }
}
