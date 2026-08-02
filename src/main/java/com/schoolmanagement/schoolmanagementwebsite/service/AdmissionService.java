// package com.schoolmanagement.schoolmanagementwebsite.service;
// import lombok.RequiredArgsConstructor;
// import org.springframework.stereotype.Service;
// import com.schoolmanagement.schoolmanagementwebsite.dto.AdmissionRequest;
// import com.schoolmanagement.schoolmanagementwebsite.entity.Admission;
// import com.schoolmanagement.schoolmanagementwebsite.repository.AdmissionRepository;
// @Service
// @RequiredArgsConstructor
// public class AdmissionService {
//     private final AdmissionRepository admissionRepository;
//     public Admission createAdmission(AdmissionRequest request) {
//         if (admissionRepository.existsByAdmissionNumber(request.getAdmissionNumber())) {
//             throw new RuntimeException("Admission Number already exists");
//         }
//         Admission admission = Admission.builder()
//                 .admissionNumber(request.getAdmissionNumber())
//                 .academicYear(request.getAcademicYear())
//                 .academicType(request.getAcademicType())
//                 .invoice(request.getInvoice())
//                 .today(request.getToday())
//                 .firstName(request.getFirstName())
//                 .middleName(request.getMiddleName())
//                 .lastName(request.getLastName())
//                 .dob(request.getDob())
//                 .gender(request.getGender())
//                 .aadharNo(request.getAadharNo())
//                 .nationality(request.getNationality())
//                 .motherTongue(request.getMotherTongue())
//                 .religion(request.getReligion())
//                 .category(request.getCategory())
//                 .caste(request.getCaste())
//                 .bloodGroup(request.getBloodGroup())
//                 .transportRequired(request.getTransportRequired())
//                 .studentClass(request.getStudentClass())
//                 .age(request.getAge())
//                 .email(request.getEmail())
//                 .preferredNo(request.getPreferredNo())
//                 .alternateNo(request.getAlternateNo())
//                 .feeCategory(request.getFeeCategory())
//                 .feeBatch(request.getFeeBatch())
//                 .fatherName(request.getFatherName())
//                 .fatherMobile(request.getFatherMobile())
//                 .fatherEmail(request.getFatherEmail())
//                 .fatherOccupation(request.getFatherOccupation())
//                 .motherName(request.getMotherName())
//                 .motherMobile(request.getMotherMobile())
//                 .motherEmail(request.getMotherEmail())
//                 .motherOccupation(request.getMotherOccupation())
//                 .houseNo(request.getHouseNo())
//                 .street(request.getStreet())
//                 .area(request.getArea())
//                 .town(request.getTown())
//                 .city(request.getCity())
//                 .state(request.getState())
//                 .country(request.getCountry())
//                 .zip(request.getZip())
//                 .build();
//         return admissionRepository.save(admission);
//     }
// // }
// package com.schoolmanagement.schoolmanagementwebsite.service;
// import java.time.LocalDateTime;
// import java.util.List;
// import org.springframework.stereotype.Service;
// import org.springframework.transaction.annotation.Transactional;
// import com.schoolmanagement.schoolmanagementwebsite.dto.AdmissionRequest;
// import com.schoolmanagement.schoolmanagementwebsite.entity.Admission;
// import com.schoolmanagement.schoolmanagementwebsite.entity.AdmissionStatus;
// import com.schoolmanagement.schoolmanagementwebsite.entity.AdmissionStatusHistory;
// import com.schoolmanagement.schoolmanagementwebsite.entity.School;
// import com.schoolmanagement.schoolmanagementwebsite.entity.Student;
// import com.schoolmanagement.schoolmanagementwebsite.entity.User;
// import com.schoolmanagement.schoolmanagementwebsite.repository.AdmissionRepository;
// import com.schoolmanagement.schoolmanagementwebsite.repository.AdmissionStatusHistoryRepository;
// import com.schoolmanagement.schoolmanagementwebsite.repository.AdmissionStatusRepository;
// import com.schoolmanagement.schoolmanagementwebsite.repository.SchoolRepository;
// import com.schoolmanagement.schoolmanagementwebsite.repository.StudentRepository;
// import com.schoolmanagement.schoolmanagementwebsite.repository.UserRepository;
// import lombok.RequiredArgsConstructor;
// @Service
// @RequiredArgsConstructor
// public class AdmissionService {
//     private final AdmissionRepository admissionRepository;
//     private final StudentRepository studentRepository;
//     private final SchoolRepository schoolRepository; // Inject SchoolRepository
//     private final UserRepository userRepository;
//     private final AdmissionStatusRepository admissionStatusRepository;
//     private final AdmissionStatusHistoryRepository statusHistoryRepository;
//     public String generateAdmissionNumber() {
//         long count = admissionRepository.count() + 1;
//         return "ADM" + String.format("%05d", count);
//     }
//     @Transactional
//     public Admission createAdmission(AdmissionRequest request, String email) {
//         // 1️⃣ Fetch School first
//         User user = userRepository.findByEmail(email);
//         if (user == null) {
//             throw new RuntimeException("User not found: " + email);
//         }
//         School school = user.getSchool(); // ✅ LOGGED-IN user's school
//         if (school == null) {
//             throw new RuntimeException("User is not linked with any school");
//         }
//         // 2️⃣ Generate unique admission number
//         String admissionNo = generateAdmissionNumber();
//       AdmissionStatus appliedStatus =
//     admissionStatusRepository.findByNameIgnoreCase("APPLIED")
//     .orElseGet(() -> {
//         AdmissionStatus status = new AdmissionStatus();
//         status.setName("APPLIED");
//         return admissionStatusRepository.save(status);
//     });
//         // 3️⃣ Create Admission entity
//         Admission admission = Admission.builder()
//                 .admissionNumber(admissionNo)
//                 .academicYear(request.getAcademicYear())
//                 .academicType(request.getAcademicType())
//                 .invoice(request.getInvoice())
//                 .today(request.getToday())
//                 .firstName(request.getFirstName())
//                 .middleName(request.getMiddleName())
//                 .lastName(request.getLastName())
//                 .dob(request.getDob())
//                 .gender(request.getGender())
//                 .aadharNo(request.getAadharNo())
//                 .nationality(request.getNationality())
//                 .motherTongue(request.getMotherTongue())
//                 .religion(request.getReligion())
//                 .category(request.getCategory())
//                 .caste(request.getCaste())
//                 .bloodGroup(request.getBloodGroup())
//                 .transportRequired(request.getTransportRequired())
//                 .studentClass(request.getStudentClass())
//                 .age(request.getAge())
//                 .email(request.getEmail())
//                 .preferredNo(request.getPreferredNo())
//                 .alternateNo(request.getAlternateNo())
//                 .feeCategory(request.getFeeCategory())
//                 .feeBatch(request.getFeeBatch())
//                 .fatherName(request.getFatherName())
//                 .fatherMobile(request.getFatherMobile())
//                 .fatherEmail(request.getFatherEmail())
//                 .fatherOccupation(request.getFatherOccupation())
//                 .motherName(request.getMotherName())
//                 .motherMobile(request.getMotherMobile())
//                 .motherEmail(request.getMotherEmail())
//                 .motherOccupation(request.getMotherOccupation())
//                 .houseNo(request.getHouseNo())
//                 .street(request.getStreet())
//                 .area(request.getArea())
//                 .town(request.getTown())
//                 .city(request.getCity())
//                 .state(request.getState())
//                 .country(request.getCountry())
//                 .zip(request.getZip())
//                 .school(school) // ✅ link School here
//                 .status(appliedStatus)   // ✅ FIX HERE
//                 .build();
//         Admission savedAdmission = admissionRepository.save(admission);
//         // 4️⃣ Create Student automatically
//         Student student = Student.builder()
//                 .admissionNumber(savedAdmission.getAdmissionNumber())
//                 .firstName(savedAdmission.getFirstName())
//                 .middleName(savedAdmission.getMiddleName())
//                 .lastName(savedAdmission.getLastName())
//                 .dob(savedAdmission.getDob())
//                 .gender(savedAdmission.getGender())
//                 .age(savedAdmission.getAge())
//                 .studentClass(savedAdmission.getStudentClass())
//                 .email(savedAdmission.getEmail())
//                 .mobile(savedAdmission.getPreferredNo())
//                 .fatherName(savedAdmission.getFatherName())
//                 .motherName(savedAdmission.getMotherName())
//                 .address(savedAdmission.getHouseNo() + ", " + savedAdmission.getStreet() + ", " + savedAdmission.getCity())
//                 .school(school) // ✅ link the same school
//                 .admission(savedAdmission)
//                 .build();
//         studentRepository.save(student);
//         return savedAdmission;
//     }
//     public Admission updateAdmission(Long id, Admission updatedAdmission) {
//         Admission existing = admissionRepository.findById(id)
//                 .orElseThrow(() -> new RuntimeException("Admission not found"));
//         existing.setFirstName(updatedAdmission.getFirstName());
//         existing.setLastName(updatedAdmission.getLastName());
//         existing.setAcademicYear(updatedAdmission.getAcademicYear());
//         existing.setStudentClass(updatedAdmission.getStudentClass());
//         existing.setAcademicType(updatedAdmission.getAcademicType());
//         existing.setToday(updatedAdmission.getToday());
//         return admissionRepository.save(existing);
//     }
//     // ✅ DELETE
//     public void deleteAdmission(Long id) {
//         if (!admissionRepository.existsById(id)) {
//             throw new RuntimeException("Admission not found");
//         }
//         admissionRepository.deleteById(id);
//     }
//     public List<Admission> getAllAdmissions() {
//         return admissionRepository.findAll();
//     }
//     // update admission
//     @Transactional
//     public void updateAdmissionStatus(
//             Long admissionId,
//             String newStatusName,
//             String changedBy) {
//         Admission admission = admissionRepository.findById(admissionId)
//                 .orElseThrow(() -> new RuntimeException("Admission not found"));
//         AdmissionStatus newStatus = admissionStatusRepository
//                 .findByName(newStatusName.toUpperCase())
//                 .orElseThrow(() -> new RuntimeException("Invalid status"));
//         AdmissionStatus oldStatus = admission.getStatus();
//         // 🔒 Simple rule
//         if (oldStatus != null
//                 && (oldStatus.getName().equals("APPROVED")
//                 || oldStatus.getName().equals("REJECTED"))) {
//             throw new RuntimeException("Status cannot be changed");
//         }
//         // Update admission
//         admission.setStatus(newStatus);
//         admissionRepository.save(admission);
//         // Save history
//        AdmissionStatusHistory history = new AdmissionStatusHistory();
// history.setAdmission(savedAdmission);
// history.setOldStatus(null);
// history.setNewStatus(appliedStatus);
// history.setChangedBy(email);
// history.setChangedAt(LocalDateTime.now());
// statusHistoryRepository.save(history);
//     }
// }
// package com.schoolmanagement.schoolmanagementwebsite.service;
// import java.time.LocalDateTime;
// import java.util.List;
// import org.springframework.stereotype.Service;
// import org.springframework.transaction.annotation.Transactional;
// import com.schoolmanagement.schoolmanagementwebsite.dto.AdmissionRequest;
// import com.schoolmanagement.schoolmanagementwebsite.entity.Admission;
// import com.schoolmanagement.schoolmanagementwebsite.entity.AdmissionStatus;
// import com.schoolmanagement.schoolmanagementwebsite.entity.AdmissionStatusHistory;
// import com.schoolmanagement.schoolmanagementwebsite.entity.School;
// import com.schoolmanagement.schoolmanagementwebsite.entity.Student;
// import com.schoolmanagement.schoolmanagementwebsite.entity.User;
// import com.schoolmanagement.schoolmanagementwebsite.repository.AdmissionRepository;
// import com.schoolmanagement.schoolmanagementwebsite.repository.AdmissionStatusHistoryRepository;
// import com.schoolmanagement.schoolmanagementwebsite.repository.AdmissionStatusRepository;
// import com.schoolmanagement.schoolmanagementwebsite.repository.SchoolRepository;
// import com.schoolmanagement.schoolmanagementwebsite.repository.StudentRepository;
// import com.schoolmanagement.schoolmanagementwebsite.repository.UserRepository;
// import lombok.RequiredArgsConstructor;
// @Service
// @RequiredArgsConstructor
// public class AdmissionService {
//     private final AdmissionRepository admissionRepository;
//     private final StudentRepository studentRepository;
//     private final SchoolRepository schoolRepository;
//     private final UserRepository userRepository;
//     private final AdmissionStatusRepository admissionStatusRepository;
//     private final AdmissionStatusHistoryRepository statusHistoryRepository;
//     // 🔹 Generate Admission Number
//     public String generateAdmissionNumber(long schoolId) {
//         long count = admissionRepository.countBySchool_Id(schoolId) + 1;
//         return "ADM" + String.format("%05d", count);
//     }
//     // =====================================================
//     // ✅ CREATE ADMISSION (DEFAULT STATUS = APPLIED)
//     // =====================================================
//    @Transactional
// public Admission createAdmission(AdmissionRequest request, String email) {
//     User user = userRepository.findByEmail(email);
//     if (user == null) throw new RuntimeException("User not found");
//     School school = user.getSchool();
//     if (school == null) throw new RuntimeException("No school linked");
//     String admissionNo = generateAdmissionNumber(school.getId());
//     com.schoolmanagement.schoolmanagementwebsite.enums.AdmissionStatus appliedStatus =
//             admissionStatusRepository.findByNameIgnoreCase("APPLIED")
//                     .orElseGet(() -> {
//                         AdmissionStatus s = new AdmissionStatus();
//                         s.setName("APPLIED");
//                         return admissionStatusRepository.save(s);
//                     });
//             Admission admission = Admission.builder()
//                 .admissionNumber(admissionNo)
//                 .academicYear(request.getAcademicYear())
//                 .academicType(request.getAcademicType())
//                 .invoice(request.getInvoice())
//                 .today(request.getToday())
//                 .firstName(request.getFirstName())
//                 .middleName(request.getMiddleName())
//                 .lastName(request.getLastName())
//                 .dob(request.getDob())
//                 .gender(request.getGender())
//                 .aadharNo(request.getAadharNo())
//                 .nationality(request.getNationality())
//                 .motherTongue(request.getMotherTongue())
//                 .religion(request.getReligion())
//                 .category(request.getCategory())
//                 .caste(request.getCaste())
//                 .bloodGroup(request.getBloodGroup())
//                 .transportRequired(request.getTransportRequired())
//                 .studentClass(request.getStudentClass())
//                 .age(request.getAge())
//                 .email(request.getEmail())
//                 .preferredNo(request.getPreferredNo())
//                 .alternateNo(request.getAlternateNo())
//                 .feeCategory(request.getFeeCategory())
//                 .feeBatch(request.getFeeBatch())
//                 .fatherName(request.getFatherName())
//                 .fatherMobile(request.getFatherMobile())
//                 .fatherEmail(request.getFatherEmail())
//                 .fatherOccupation(request.getFatherOccupation())
//                 .motherName(request.getMotherName())
//                 .motherMobile(request.getMotherMobile())
//                 .motherEmail(request.getMotherEmail())
//                 .motherOccupation(request.getMotherOccupation())
//                 .houseNo(request.getHouseNo())
//                 .street(request.getStreet())
//                 .area(request.getArea())
//                 .town(request.getTown())
//                 .city(request.getCity())
//                 .state(request.getState())
//                 .country(request.getCountry())
//                 .zip(request.getZip())
//                 .school(school) // ✅ link School here
//                 .status(appliedStatus)   // ✅ FIX HERE
//                 .build();
//     Admission saved = admissionRepository.save(admission);
//     // ✅ STATUS HISTORY
//     AdmissionStatusHistory history = new AdmissionStatusHistory();
//     history.setAdmission(saved);
//     history.setOldStatus(null);
//     history.setNewStatus(appliedStatus);
//     history.setChangedBy(email);
//     history.setChangedAt(LocalDateTime.now());
//     statusHistoryRepository.save(history);
//     // ❌ NO STUDENT CREATION HERE
//     return saved;
// }
//     // =====================================================
//     // ✅ UPDATE BASIC ADMISSION DETAILS
//     // =====================================================
//     public Admission updateAdmission(Long id, Admission updatedAdmission) {
//         Admission existing = admissionRepository.findById(id)
//                 .orElseThrow(() -> new RuntimeException("Admission not found"));
//         existing.setFirstName(updatedAdmission.getFirstName());
//         existing.setLastName(updatedAdmission.getLastName());
//         existing.setAcademicYear(updatedAdmission.getAcademicYear());
//         existing.setStudentClass(updatedAdmission.getStudentClass());
//         existing.setAcademicType(updatedAdmission.getAcademicType());
//         existing.setToday(updatedAdmission.getToday());
//         return admissionRepository.save(existing);
//     }
//     // =====================================================
//     // ✅ UPDATE ADMISSION STATUS (APPROVED / REJECTED)
//     // =====================================================
//    @Transactional
// public void updateAdmissionStatus(
//         Long admissionId,
//         String newStatusName,
//         String changedBy) {
//     Admission admission = admissionRepository.findById(admissionId)
//             .orElseThrow(() -> new RuntimeException("Admission not found"));
//     AdmissionStatus newStatus = admissionStatusRepository
//             .findByNameIgnoreCase(newStatusName)
//             .orElseThrow(() -> new RuntimeException("Invalid status"));
//     AdmissionStatus oldStatus = admission.getStatus();
//     admission.setStatus(newStatus);
//     admissionRepository.save(admission);
//     // ✅ Save history
//     AdmissionStatusHistory history = new AdmissionStatusHistory();
//     history.setAdmission(admission);
//     history.setOldStatus(oldStatus);
//     history.setNewStatus(newStatus);
//     history.setChangedBy(changedBy);
//     history.setChangedAt(LocalDateTime.now());
//     statusHistoryRepository.save(history);
//     // 🔥 CREATE STUDENT ONLY IF APPROVED
//     if ("APPROVED".equalsIgnoreCase(newStatus.getName())) {
//         createStudentFromAdmission(admission);
//     }
// }
// private void createStudentFromAdmission(Admission admission) {
//     // prevent duplicate student
//     if (admission.getStudent() != null) return;
//     Student student = Student.builder()
//             .admissionNumber(admission.getAdmissionNumber())
//             .academicYear(admission.getAcademicYear())
//             .section(null)
//             .firstName(admission.getFirstName())
//             .lastName(admission.getLastName())
//             .studentClass(admission.getStudentClass())
//             .email(admission.getEmail())
//             .school(admission.getSchool())
//             .admission(admission)
//             .build();
//     studentRepository.save(student);
// }
//     // =====================================================
//     // ✅ DELETE ADMISSION
//     // =====================================================
//     public void deleteAdmission(Long id) {
//         if (!admissionRepository.existsById(id)) {
//             throw new RuntimeException("Admission not found");
//         }
//         admissionRepository.deleteById(id);
//     }
//     // =====================================================
//     // ✅ GET ALL ADMISSIONS
//     // =====================================================
//     public List<Admission> getAllAdmissions() {
//         return admissionRepository.findAll();
//     }
// }
package com.schoolmanagement.schoolmanagementwebsite.service;

import java.util.List;

import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import com.schoolmanagement.schoolmanagementwebsite.dto.AdmissionRequest;
import com.schoolmanagement.schoolmanagementwebsite.entity.Admission;
import com.schoolmanagement.schoolmanagementwebsite.entity.School;
import com.schoolmanagement.schoolmanagementwebsite.entity.User;
import com.schoolmanagement.schoolmanagementwebsite.enums.AdmissionStatus;
import com.schoolmanagement.schoolmanagementwebsite.repository.AdmissionRepository;
import com.schoolmanagement.schoolmanagementwebsite.repository.UserRepository;

import lombok.RequiredArgsConstructor;

// @Service
// @RequiredArgsConstructor
// public class AdmissionService {
//     private final AdmissionRepository admissionRepository;
//     private final UserRepository userRepository;
//     // 🔹 Generate Admission Number
//     public String generateAdmissionNumber(long schoolId) {
//         long count = admissionRepository.countBySchool_Id(schoolId) + 1;
//         return "ADM" + String.format("%05d", count);
//     }
//     // =====================================================
//     // ✅ CREATE ADMISSION (STATUS = APPLIED)
//     // =====================================================
//     @Transactional
//     public Admission createAdmission(AdmissionRequest request, String email) {
//         User user = userRepository.findByEmail(email);
//         if (user == null) throw new RuntimeException("User not found");
//         School school = user.getSchool();
//         if (school == null) throw new RuntimeException("No school linked");
//         Admission admission = Admission.builder()
//                 .admissionNumber(generateAdmissionNumber(school.getId()))
//                 .academicYear(request.getAcademicYear())
//                 .academicType(request.getAcademicType())
//                 .firstName(request.getFirstName())
//                 .lastName(request.getLastName())
//                 .studentClass(request.getStudentClass())
//                 .email(request.getEmail())
//                 .school(school)
//                 .status(AdmissionStatus.APPLIED) // ✅ ENUM
//                 .build();
//         return admissionRepository.save(admission);
//     }
//     // =====================================================
//     // ✅ UPDATE STATUS (APPROVED / REJECTED)
//     // =====================================================
//     @Transactional
//     public void updateAdmissionStatus(Long admissionId, AdmissionStatus status) {
//         Admission admission = admissionRepository.findById(admissionId)
//                 .orElseThrow(() -> new RuntimeException("Admission not found"));
//         admission.setStatus(status);
//         admissionRepository.save(admission);
//     }
//     public List<Admission> getAllAdmissions() {
//         return admissionRepository.findAll();
//     }
// }
@Service
@RequiredArgsConstructor
public class AdmissionService {

    private final AdmissionRepository admissionRepository;
    private final UserRepository userRepository;

    // 🔹 Generate Admission Number
    public String generateAdmissionNumber(long schoolId) {
        long count = admissionRepository.countBySchool_Id(schoolId) + 1;
        return "ADM" + String.format("%05d", count);
    }

    // =====================================================
    // ✅ CREATE ADMISSION (STATUS = APPLIED)
    // =====================================================
    @Transactional
    public Admission createAdmission(AdmissionRequest request, String email) {

        User user = userRepository.findByEmail(email);
        if (user == null) {
            throw new RuntimeException("User not found");
        }

        School school = user.getSchool();
        if (school == null) {
            throw new RuntimeException("No school linked");
        }

        Admission admission = Admission.builder()
                .admissionNumber(generateAdmissionNumber(school.getId()))
                .academicYear(request.getAcademicYear())
                .academicType(request.getAcademicType())
                .invoice(request.getInvoice())
                .today(request.getToday())
                .firstName(request.getFirstName())
                .middleName(request.getMiddleName())
                .lastName(request.getLastName())
                .dob(request.getDob())
                .gender(request.getGender())
                .aadharNo(request.getAadharNo())
                .nationality(request.getNationality())
                .motherTongue(request.getMotherTongue())
                .religion(request.getReligion())
                .category(request.getCategory())
                .caste(request.getCaste())
                .bloodGroup(request.getBloodGroup())
                .transportRequired(request.getTransportRequired())
                .studentClass(request.getStudentClass())
                .age(request.getAge())
                .email(request.getEmail())
                .preferredNo(request.getPreferredNo())
                .alternateNo(request.getAlternateNo())
                .feeCategory(request.getFeeCategory())
                .feeBatch(request.getFeeBatch())
                .fatherName(request.getFatherName())
                .fatherMobile(request.getFatherMobile())
                .fatherEmail(request.getFatherEmail())
                .fatherOccupation(request.getFatherOccupation())
                .motherName(request.getMotherName())
                .motherMobile(request.getMotherMobile())
                .motherEmail(request.getMotherEmail())
                .motherOccupation(request.getMotherOccupation())
                .houseNo(request.getHouseNo())
                .street(request.getStreet())
                .area(request.getArea())
                .town(request.getTown())
                .city(request.getCity())
                .state(request.getState())
                .country(request.getCountry())
                .zip(request.getZip())
                .school(school) // ✅ link School here
                .status(AdmissionStatus.APPLIED) // ✅ ENUM
                .build();

        return admissionRepository.save(admission);
    }

    // =====================================================
    // ✅ UPDATE ADMISSION
    // =====================================================
    // @Transactional
    // public Admission updateAdmission(Long id, Admission updatedAdmission) {
    //     Admission admission = admissionRepository.findById(id)
    //             .orElseThrow(() -> new RuntimeException("Admission not found"));
    //     // Update only allowed fields
    //     admission.setFirstName(updatedAdmission.getFirstName());
    //     admission.setLastName(updatedAdmission.getLastName());
    //     admission.setStudentClass(updatedAdmission.getStudentClass());
    //     admission.setAcademicYear(updatedAdmission.getAcademicYear());
    //     admission.setAcademicType(updatedAdmission.getAcademicType());
    //     admission.setEmail(updatedAdmission.getEmail());
    //     return admissionRepository.save(admission);
    // }
    public Admission updateAdmission(Long id, Admission request) {

        Admission existing = admissionRepository.findById(id)
                .orElseThrow(() -> new RuntimeException("Admission not found"));

        // 🔹 Academic
        existing.setAcademicYear(request.getAcademicYear());
        existing.setAcademicType(request.getAcademicType());
        existing.setInvoice(request.getInvoice());
        existing.setToday(request.getToday());

        // 🔹 Student
        existing.setFirstName(request.getFirstName());
        existing.setMiddleName(request.getMiddleName());
        existing.setLastName(request.getLastName());
        existing.setDob(request.getDob());
        existing.setGender(request.getGender());
        existing.setAadharNo(request.getAadharNo());
        existing.setNationality(request.getNationality());
        existing.setMotherTongue(request.getMotherTongue());
        existing.setReligion(request.getReligion());
        existing.setCategory(request.getCategory());
        existing.setCaste(request.getCaste());
        existing.setBloodGroup(request.getBloodGroup());
        // existing.setTransportRequired(request.isTransportRequired());
        existing.setStudentClass(request.getStudentClass());
        // existing.setSection(request.getSection());
        existing.setAge(request.getAge());

        // 🔹 Contact
        existing.setEmail(request.getEmail());
        existing.setAlternateNo(request.getAlternateNo());
        existing.setPreferredNo(request.getPreferredNo());
        existing.setFeeCategory(request.getFeeCategory());

        // 🔹 Parent details
        existing.setFatherName(request.getFatherName());
        existing.setFatherMobile(request.getFatherMobile());
        existing.setFatherEmail(request.getFatherEmail());
        existing.setFatherOccupation(request.getFatherOccupation());

        existing.setMotherName(request.getMotherName());
        existing.setMotherMobile(request.getMotherMobile());
        existing.setMotherEmail(request.getMotherEmail());
        existing.setMotherOccupation(request.getMotherOccupation());

        // 🔹 Images (Base64 safe)
        // if (request.getStudentImage() != null)
        //     existing.setStudentImage(request.getStudentImage());
        // if (request.getFatherImage() != null)
        //     existing.setFatherImage(request.getFatherImage());
        // if (request.getMotherImage() != null)
        //     existing.setMotherImage(request.getMotherImage());
        // if (request.getGuardianImage() != null)
        //     existing.setGuardianImage(request.getGuardianImage());
        // ❌ DO NOT update STATUS here
        // ❌ DO NOT update ID
        return admissionRepository.save(existing);
    }

    // =====================================================
    // ✅ UPDATE STATUS (APPLIED / APPROVED / REJECTED)
    // =====================================================
    @Transactional
    public void updateAdmissionStatus(Long admissionId, AdmissionStatus status) {

        Admission admission = admissionRepository.findById(admissionId)
                .orElseThrow(() -> new RuntimeException("Admission not found"));

        admission.setStatus(status);
        admissionRepository.save(admission);
    }

    public List<Admission> getAllAdmissions() {
        return admissionRepository.findAll();
    }

    public Admission getAdmissionById(Long id) {
        return admissionRepository.findById(id)
                .orElseThrow(() -> new RuntimeException("Admission not found with id: " + id));
    }

    public List<Admission> searchAdmissions(
            Long schoolId,
            String academicYear,
            String admissionNumber,
            String studentClass
            ) {

        return admissionRepository.searchAdmissions(
                schoolId,
                academicYear == null || academicYear.isBlank() ? null : academicYear,
                admissionNumber == null || admissionNumber.isBlank() ? null : admissionNumber,
                studentClass == null || studentClass.isBlank() ? null : studentClass
                
        );
    }

    
            
            
}       
            
                    
                
                
                
                
                
            