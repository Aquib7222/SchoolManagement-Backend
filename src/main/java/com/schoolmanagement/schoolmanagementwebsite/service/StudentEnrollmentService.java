// package com.schoolmanagement.schoolmanagementwebsite.service;

// import java.time.LocalDate;
// import java.util.ArrayList;
// import java.util.List;

// import org.springframework.stereotype.Service;
// import org.springframework.transaction.annotation.Transactional;

// import com.schoolmanagement.schoolmanagementwebsite.dto.PromotionRequest;
// import com.schoolmanagement.schoolmanagementwebsite.dto.StudentEnrollmentResponse;
// import com.schoolmanagement.schoolmanagementwebsite.entity.School;
// import com.schoolmanagement.schoolmanagementwebsite.entity.Student;
// import com.schoolmanagement.schoolmanagementwebsite.entity.StudentEnrollment;
// import com.schoolmanagement.schoolmanagementwebsite.enums.Section;
// import com.schoolmanagement.schoolmanagementwebsite.enums.StudentStatus;
// import com.schoolmanagement.schoolmanagementwebsite.repository.SchoolRepository;
// import com.schoolmanagement.schoolmanagementwebsite.repository.StudentEnrollmentRepository;
// import com.schoolmanagement.schoolmanagementwebsite.repository.StudentRepository;

// import lombok.RequiredArgsConstructor;

// @Service
// @RequiredArgsConstructor
// public class StudentEnrollmentService {

//     private final StudentEnrollmentRepository enrollmentRepository;
//     private final StudentRepository studentRepository;
//     private final SchoolRepository schoolRepository;


//     // =========================================================
//     // PROMOTE STUDENTS
//     // =========================================================

//     @Transactional
//     public String promoteStudents(PromotionRequest request) {

//         // =====================================================
//         // VALIDATION
//         // =====================================================

//         if (request.getSchoolId() == null) {
//             throw new RuntimeException("School ID is required");
//         }

//         if (isBlank(request.getFromAcademicYear())) {
//             throw new RuntimeException(
//                     "Current academic year is required"
//             );
//         }

//         if (isBlank(request.getFromStudentClass())) {
//             throw new RuntimeException(
//                     "Current class is required"
//             );
//         }

//         if (isBlank(request.getFromSection())) {
//             throw new RuntimeException(
//                     "Current section is required"
//             );
//         }

//         if (isBlank(request.getToAcademicYear())) {
//             throw new RuntimeException(
//                     "Promotion academic year is required"
//             );
//         }

//         if (isBlank(request.getToStudentClass())) {
//             throw new RuntimeException(
//                     "Promotion class is required"
//             );
//         }

//         if (isBlank(request.getToSection())) {
//             throw new RuntimeException(
//                     "Promotion section is required"
//             );
//         }

//         if (request.getAdmissionNumbers() == null
//                 || request.getAdmissionNumbers().isEmpty()) {

//             throw new RuntimeException(
//                     "Please select at least one student"
//             );
//         }


//         // =====================================================
//         // SECTION CONVERSION
//         // =====================================================

//         Section fromSection;

//         Section toSection;

//         try {

//             fromSection = Section.valueOf(
//                     request.getFromSection()
//                             .trim()
//                             .toUpperCase()
//             );

//             toSection = Section.valueOf(
//                     request.getToSection()
//                             .trim()
//                             .toUpperCase()
//             );

//         } catch (IllegalArgumentException e) {

//             throw new RuntimeException(
//                     "Invalid section"
//             );
//         }


//         // =====================================================
//         // SCHOOL
//         // =====================================================

//         School school = schoolRepository
//                 .findById(request.getSchoolId())
//                 .orElseThrow(() ->
//                         new RuntimeException(
//                                 "School not found"
//                         )
//                 );


//         // =====================================================
//         // PROMOTION TO SAME YEAR CHECK
//         // =====================================================

//         if (request.getFromAcademicYear()
//                 .equalsIgnoreCase(request.getToAcademicYear())) {

//             throw new RuntimeException(
//                     "Promotion year must be different from current year"
//             );
//         }


//         // =====================================================
//         // LOAD STUDENTS
//         // =====================================================

//         List<Student> students = new ArrayList<>();

//         for (String admissionNumber :
//                 request.getAdmissionNumbers()) {

//             if (admissionNumber == null
//                     || admissionNumber.trim().isEmpty()) {

//                 continue;
//             }

//             Student student = studentRepository
//                     .findBySchool_IdAndAdmissionNumber(
//                             request.getSchoolId(),
//                             admissionNumber.trim()
//                     )
//                     .orElseThrow(() ->
//                             new RuntimeException(
//                                     "Student not found: "
//                                             + admissionNumber
//                             )
//                     );

//             students.add(student);
//         }


//         if (students.isEmpty()) {

//             throw new RuntimeException(
//                     "No valid students found"
//             );
//         }


//         // =====================================================
//         // PROMOTION
//         // =====================================================

//         int promotedCount = 0;

//         for (Student student : students) {

//             // =================================================
//             // FIND CURRENT ENROLLMENT
//             // =================================================

//             StudentEnrollment currentEnrollment =
//                     enrollmentRepository
//                             .findByStudent_IdAndAcademicYear(
//                                     student.getId(),
//                                     request.getFromAcademicYear()
//                             )
//                             .orElseThrow(() ->
//                                     new RuntimeException(
//                                             "Current enrollment not found for "
//                                                     + student.getAdmissionNumber()
//                                     )
//                             );


//             // =================================================
//             // VERIFY CLASS
//             // =================================================

//             if (!currentEnrollment
//                     .getStudentClass()
//                     .equalsIgnoreCase(
//                             request.getFromStudentClass()
//                     )) {

//                 throw new RuntimeException(
//                         "Student "
//                                 + student.getAdmissionNumber()
//                                 + " is not in class "
//                                 + request.getFromStudentClass()
//                 );
//             }


//             // =================================================
//             // VERIFY SECTION
//             // =================================================

//             if (currentEnrollment.getSection()
//                     != fromSection) {

//                 throw new RuntimeException(
//                         "Student "
//                                 + student.getAdmissionNumber()
//                                 + " is not in section "
//                                 + fromSection
//                 );
//             }


//             // =================================================
//             // ONLY ACTIVE STUDENT
//             // =================================================

//             if (currentEnrollment.getStatus()
//                     != StudentStatus.ACTIVE) {

//                 throw new RuntimeException(
//                         "Student "
//                                 + student.getAdmissionNumber()
//                                 + " is not active"
//                 );
//             }


//             // =================================================
//             // CHECK ALREADY PROMOTED
//             // =================================================

//             boolean alreadyExists =
//                     enrollmentRepository
//                             .existsByStudent_IdAndAcademicYear(
//                                     student.getId(),
//                                     request.getToAcademicYear()
//                             );

//             if (alreadyExists) {

//                 throw new RuntimeException(
//                         "Student "
//                                 + student.getAdmissionNumber()
//                                 + " is already enrolled in "
//                                 + request.getToAcademicYear()
//                 );
//             }


//             // =================================================
//             // CREATE NEW ENROLLMENT
//             // =================================================

//             StudentEnrollment newEnrollment =
//                     StudentEnrollment.builder()
//                             .student(student)
//                             .school(school)
//                             .academicYear(
//                                     request.getToAcademicYear()
//                             )
//                             .studentClass(
//                                     request.getToStudentClass()
//                             )
//                             .section(toSection)
//                             .rollNumber(null)
//                             .status(StudentStatus.ACTIVE)
//                             .enrollmentDate(LocalDate.now())
//                             .promotedFromEnrollment(
//                                     currentEnrollment
//                             )
//                             .build();


//             enrollmentRepository.save(newEnrollment);

//             promotedCount++;
//         }


//         return promotedCount
//                 + " student(s) promoted successfully";
//     }


//     // =========================================================
//     // GET STUDENTS BY YEAR / CLASS / SECTION
//     // =========================================================

//     public List<StudentEnrollmentResponse> getStudents(
//             Long schoolId,
//             String academicYear,
//             String studentClass,
//             String section,
//             String search
//     ) {

//         if (schoolId == null) {
//             throw new RuntimeException(
//                     "School ID is required"
//             );
//         }

//         if (isBlank(academicYear)) {
//             throw new RuntimeException(
//                     "Academic year is required"
//             );
//         }

//         if (isBlank(studentClass)) {
//             throw new RuntimeException(
//                     "Class is required"
//             );
//         }

//         if (isBlank(section)) {
//             throw new RuntimeException(
//                     "Section is required"
//             );
//         }

//         Section sectionEnum;

//         try {

//             sectionEnum = Section.valueOf(
//                     section.trim().toUpperCase()
//             );

//         } catch (IllegalArgumentException e) {

//             throw new RuntimeException(
//                     "Invalid section: " + section
//             );
//         }


//         List<StudentEnrollment> enrollments =
//                 enrollmentRepository.searchStudents(
//                         schoolId,
//                         academicYear,
//                         studentClass,
//                         sectionEnum,
//                         StudentStatus.ACTIVE,
//                         search
//                 );


//         return enrollments.stream()
//                 .map(this::mapToResponse)
//                 .toList();
//     }


//     // =========================================================
//     // GET STUDENT ACADEMIC HISTORY
//     // =========================================================

//     public List<StudentEnrollmentResponse> getStudentHistory(
//             Long schoolId,
//             String admissionNumber
//     ) {

//         Student student =
//                 studentRepository
//                         .findBySchool_IdAndAdmissionNumber(
//                                 schoolId,
//                                 admissionNumber
//                         )
//                         .orElseThrow(() ->
//                                 new RuntimeException(
//                                         "Student not found"
//                                 )
//                         );


//         List<StudentEnrollment> enrollments =
//                 enrollmentRepository
//                         .findAll()
//                         .stream()
//                         .filter(e ->
//                                 e.getStudent()
//                                         .getId()
//                                         .equals(student.getId())
//                         )
//                         .toList();


//         return enrollments.stream()
//                 .map(this::mapToResponse)
//                 .toList();
//     }


//     // =========================================================
//     // MAPPER
//     // =========================================================

//  private StudentEnrollmentResponse mapToResponse(
//         StudentEnrollment enrollment
// ) {

//     Student student = enrollment.getStudent();

//     return StudentEnrollmentResponse.builder()

//             // =============================================
//             // ENROLLMENT
//             // =============================================

//             .enrollmentId(
//                     enrollment.getId()
//             )

//             .studentId(
//                     student.getId()
//             )

//             .schoolId(
//                     enrollment.getSchool() != null
//                             ? enrollment.getSchool().getId()
//                             : null
//             )

//             .enrollmentAcademicYear(
//                     enrollment.getAcademicYear()
//             )

//             .enrollmentClass(
//                     enrollment.getStudentClass()
//             )

//             .enrollmentSection(
//                     enrollment.getSection() != null
//                             ? enrollment.getSection().name()
//                             : null
//             )

//             .enrollmentRollNumber(
//                     enrollment.getRollNumber()
//             )

//             .enrollmentStatus(
//                     enrollment.getStatus() != null
//                             ? enrollment.getStatus().name()
//                             : null
//             )

//             .enrollmentDate(
//                     enrollment.getEnrollmentDate()
//             )

//             .promotedFromEnrollmentId(
//                     enrollment.getPromotedFromEnrollment() != null
//                             ? enrollment
//                                 .getPromotedFromEnrollment()
//                                 .getId()
//                             : null
//             )


//             // =============================================
//             // STUDENT
//             // =============================================

//             .admissionNumber(
//                     student.getAdmissionNumber()
//             )

//             .firstName(
//                     student.getFirstName()
//             )

//             .middleName(
//                     student.getMiddleName()
//             )

//             .lastName(
//                     student.getLastName()
//             )

//             .dob(
//                     student.getDob()
//             )

//             .gender(
//                     student.getGender()
//             )

//             .age(
//                     student.getAge()
//             )

//             .studentAcademicYear(
//                     student.getAcademicYear()
//             )

//             .studentClass(
//                     student.getStudentClass()
//             )

//             .studentSection(
//                     student.getSection() != null
//                             ? student.getSection().name()
//                             : null
//             )

//             .studentRollNumber(
//                     student.getRollNumber()
//             )

//             .studentStatus(
//                     student.getStatus() != null
//                             ? student.getStatus().name()
//                             : null
//             )

//             .nationality(
//                     student.getNationality()
//             )

//             .motherTongue(
//                     student.getMotherTongue()
//             )

//             .religion(
//                     student.getReligion()
//             )

//             .category(
//                     student.getCategory()
//             )

//             .caste(
//                     student.getCaste()
//             )

//             .bloodGroup(
//                     student.getBloodGroup()
//             )

//             .transportRequired(
//                     student.getTransportRequired()
//             )

//             .email(
//                     student.getEmail()
//             )

//             .mobile(
//                     student.getMobile()
//             )

//             .feeCategory(
//                     student.getFeeCategory()
//             )

//             .feeBatch(
//                     student.getFeeBatch()
//             )


//             // =============================================
//             // FATHER
//             // =============================================

//             .fatherName(
//                     student.getFatherName()
//             )

//             .fatherMobile(
//                     student.getFatherMobile()
//             )

//             .fatherEmail(
//                     student.getFatherEmail()
//             )

//             .fatherOccupation(
//                     student.getFatherOccupation()
//             )


//             // =============================================
//             // MOTHER
//             // =============================================

//             .motherName(
//                     student.getMotherName()
//             )

//             .motherMobile(
//                     student.getMotherMobile()
//             )

//             .motherEmail(
//                     student.getMotherEmail()
//             )

//             .motherOccupation(
//                     student.getMotherOccupation()
//             )


//             // =============================================
//             // ADDRESS
//             // =============================================

//             .houseNo(
//                     student.getHouseNo()
//             )

//             .street(
//                     student.getStreet()
//             )

//             .area(
//                     student.getArea()
//             )

//             .town(
//                     student.getTown()
//             )

//             .city(
//                     student.getCity()
//             )

//             .state(
//                     student.getState()
//             )

//             .country(
//                     student.getCountry()
//             )

//             .zip(
//                     student.getZip()
//             )


//             // =============================================
//             // OTHER
//             // =============================================

//             .discontinueDate(
//                     student.getDiscontinueDate()
//             )

//             .photo(
//                     student.getPhoto()
//             )

//             .admissionId(
//                     student.getAdmission() != null
//                             ? student.getAdmission().getId()
//                             : null
//             )

//             .build();
// }


//     // =========================================================
//     // UTIL
//     // =========================================================

//     private boolean isBlank(String value) {

//         return value == null
//                 || value.trim().isEmpty();
//     }
// }