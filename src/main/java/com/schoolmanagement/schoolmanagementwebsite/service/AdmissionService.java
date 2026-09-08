
package com.schoolmanagement.schoolmanagementwebsite.service;

import java.time.LocalDate;
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


@Service
@RequiredArgsConstructor
public class AdmissionService {

    private final AdmissionRepository admissionRepository;
    private final UserRepository userRepository;

    // =====================================================
// 🔹 Generate Admission Number
// Format: SCHOOLCODE + 01
// Example: ZYN01, ZYN02, ZYN03
// =====================================================
public String generateAdmissionNumber(School school) {

    String schoolCode = school.getSchoolCode();

    if (schoolCode == null || schoolCode.isBlank()) {
        throw new RuntimeException(
                "School code is not configured"
        );
    }

    long count =
            admissionRepository.countBySchool_Id(
                    school.getId()
            ) + 1;

    return schoolCode.toUpperCase()
            + String.format("%02d", count);
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
                .admissionNumber(
    generateAdmissionNumber(school)
)
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
                .cancelDate(request.getCancelDate())
                .school(school) // ✅ link School here
                .status(AdmissionStatus.APPLIED) // ✅ ENUM
                .build();

        return admissionRepository.save(admission);
    }

 
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

@Transactional
public void cancelAdmission(Long schoolId, String admissionNumber) {

    Admission admission = admissionRepository
            .findByAdmissionNumberAndSchoolId(admissionNumber,schoolId)
            .orElseThrow(() -> new RuntimeException(
                    "Admission not found for admission number: " + admissionNumber
            ));

    admission.setStatus(AdmissionStatus.CANCELLED);
    admission.setCancelDate(LocalDate.now());

    admissionRepository.save(admission);
}
    @Transactional
public void reApproveAdmission(
        Long schoolId,
        String admissionNumber
) {

    Admission admission = admissionRepository
            .findByAdmissionNumberAndSchoolId(
 
                admissionNumber,
                     schoolId
            )
            .orElseThrow(() ->
                    new RuntimeException("Admission not found")
            );

    if (admission.getStatus() != AdmissionStatus.CANCELLED) {
        throw new RuntimeException(
                "Only cancelled admission can be re-approved"
        );
    }

    admission.setStatus(AdmissionStatus.APPROVED);

    // Cancel date clear kar do
    admission.setCancelDate(null);

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

     public Admission getAdmissionBySchoolIdAndAdmissionNumber(
            Long schoolId,
            String admissionNumber) {

        return admissionRepository
                .findBySchoolIdAndAdmissionNumber(
                        schoolId,
                        admissionNumber
                )
                .orElseThrow(() ->
                        new RuntimeException(
                                "Admission not found with admission number: "
                                        + admissionNumber
                        )
                );
    }
            
            
}       
            
                    
                
                
                
                
                
            