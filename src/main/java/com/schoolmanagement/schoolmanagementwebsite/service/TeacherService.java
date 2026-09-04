// // package com.schoolmanagement.schoolmanagementwebsite.service;

// // import java.util.List;
// // import java.util.Map;

// // import org.springframework.security.crypto.password.PasswordEncoder;
// // import org.springframework.stereotype.Service;

// // import com.schoolmanagement.schoolmanagementwebsite.entity.School;
// // import com.schoolmanagement.schoolmanagementwebsite.entity.Teacher;
// // import com.schoolmanagement.schoolmanagementwebsite.entity.User;
// // import com.schoolmanagement.schoolmanagementwebsite.repository.TeacherRepository;
// // import com.schoolmanagement.schoolmanagementwebsite.repository.UserRepository;

// // import lombok.RequiredArgsConstructor;

// // @Service
// // @RequiredArgsConstructor
// // public class TeacherService {

// //     private final TeacherRepository teacherRepo;
// //     private final UserRepository userRepo;
// //     private final PasswordEncoder passwordEncoder;

// //     // ================= ADD =================
// //     public Teacher addTeacher(Teacher teacher, School school) {

// //         // 🔢 Generate Employee ID
// //         long count = teacherRepo.count() + 1000;
// //         String employeeId = "EMP" + count;

// //         // 👤 Create User
// //         User user = new User();
// //         user.setName(teacher.getFirstName() + " " + teacher.getLastName());
// //         user.setEmail(teacher.getEmail());
// //         user.setPhone(teacher.getPhoneNumber());
// //         user.setRole("TEACHER");

// //         // 🔐 password = phone number
// //         // 🔑 VERY IMPORTANT
// //         user.setSchool(school);
// //         user.setPassword(passwordEncoder.encode(teacher.getPhoneNumber()));
// //         user.setStatus("Active");

// //         userRepo.save(user);

// //         // 👨‍🏫 Teacher
// //         teacher.setEmployeeId(employeeId);
// //         teacher.setSchool(school);
// //         teacher.setUser(user);
// //         teacher.setActive(true);

// //         return teacherRepo.save(teacher);
// //     }

// //     // ================= LIST =================
// //     public List<Teacher> getAllTeachers(Long schoolId) {
// //         return teacherRepo.findAll();
// //     }

// //     public List<Teacher> getTeachersByStatus(Long schoolId, String status) {
// //         return teacherRepo.findBySchoolIdAndStatus(schoolId, status);
// //     }

// //     // ================= UPDATE =================
// //         public Teacher updateTeacher(String employeeId,Long schoolId, Teacher updated) {
    
// //         Teacher teacher = teacherRepo
// //             .findByEmployeeIdAndSchoolId(employeeId, schoolId)
// //             .orElseThrow(() -> new RuntimeException("Teacher not found"));

// //         teacher.setFirstName(updated.getFirstName());
// //         teacher.setLastName(updated.getLastName());
// //         teacher.setDepartment(updated.getDepartment());
// //         teacher.setDesignation(updated.getDesignation());
// //         teacher.setStatus(updated.getStatus());
// //         teacher.setMobileNumber(updated.getMobileNumber());

// //         return teacherRepo.save(teacher);
// //     }

// //     // ================= DELETE =================
// //     public void deleteTeacher(Long id) {

// //         Teacher teacher = teacherRepo.findById(id)
// //                 .orElseThrow(() -> new RuntimeException("Teacher not found"));

// //         if (teacher.getUser() != null) {
// //             userRepo.delete(teacher.getUser());
// //         }

// //         teacherRepo.delete(teacher);
// //     }

// //     // ================= FETCH BY EMPLOYEE ID =================
// //     public Teacher getTeacherByEmployeeId(String employeeId, Long schoolId) {

// //         return teacherRepo
// //                 .findByEmployeeIdAndSchoolId(employeeId, schoolId)
// //                 .orElseThrow(() -> new RuntimeException("Teacher not found with EmployeeId: " + employeeId));
// //     }

// //     // ================= ACTIVE / INACTIVE =================
// //     public void toggleStatus(Long id, boolean active) {

// //         Teacher teacher = teacherRepo.findById(id)
// //                 .orElseThrow(() -> new RuntimeException("Teacher not found"));

// //         teacher.setActive(active);

// //         if (teacher.getUser() != null) {
// //             teacher.getUser().setStatus(active ? "Active" : "Inactive");
// //             userRepo.save(teacher.getUser());
// //         }

// //         teacherRepo.save(teacher);
// //     }

// //     public Teacher searchTeachersByEmployeeId(String employeeId, Long schoolId) {
// //     return teacherRepo.findTeacherByEmployeeId(employeeId, schoolId);

    
// // }
// //   public Teacher updateTeacherField(
// //         String employeeId,
// //         Long schoolId,
// //         Map<String, String> updates) {

// //     Teacher teacher = teacherRepo
// //             .findByEmployeeIdAndSchoolId(employeeId, schoolId)
// //             .orElseThrow(() -> new RuntimeException("Teacher not found"));

// //     if (updates.containsKey("department")) {
// //         teacher.setDepartment(updates.get("department"));
// //     }

// //     if (updates.containsKey("designation")) {
// //         teacher.setDesignation(updates.get("designation"));
// //     }

// //     if (updates.containsKey("category")) {
// //         teacher.setCategory(updates.get("category"));
// //     }

// //     return teacherRepo.save(teacher);
// // }

// // public List<Teacher> getAllTeachersList() {
// //         return teacherRepo.findAll();
// //     }

// //     public long getTeacherCount() {
// //         return teacherRepo.count();
// //     }
// // }


// package com.schoolmanagement.schoolmanagementwebsite.service;

// import java.util.List;
// import java.util.Map;

// import org.springframework.security.crypto.password.PasswordEncoder;
// import org.springframework.stereotype.Service;
// import org.springframework.transaction.annotation.Transactional;

// import com.schoolmanagement.schoolmanagementwebsite.entity.School;
// import com.schoolmanagement.schoolmanagementwebsite.entity.Teacher;
// import com.schoolmanagement.schoolmanagementwebsite.entity.User;
// import com.schoolmanagement.schoolmanagementwebsite.repository.TeacherRepository;
// import com.schoolmanagement.schoolmanagementwebsite.repository.UserRepository;

// import lombok.RequiredArgsConstructor;

// @Service
// @RequiredArgsConstructor
// @Transactional
// public class TeacherService {

//     private final TeacherRepository teacherRepo;
//     private final UserRepository userRepo;
//     private final PasswordEncoder passwordEncoder;

//     // =========================================================
//     // ADD TEACHER
//     // =========================================================

//     public Teacher addTeacher(
//             Teacher teacher,
//             School school) {

//         if (school == null || school.getId() == null) {
//             throw new RuntimeException(
//                     "School information is required");
//         }

//         // =====================================================
//         // GENERATE EMPLOYEE ID
//         // =====================================================

//         long count =
//                 teacherRepo.countBySchoolId(school.getId());

//         String employeeId =
//                 "EMP" + (1001 + count);

//         /*
//          * Make sure generated employee ID does not already exist.
//          */
//         while (
//                 teacherRepo
//                         .findByEmployeeIdAndSchoolId(
//                                 employeeId,
//                                 school.getId())
//                         .isPresent()
//         ) {
//             count++;

//             employeeId =
//                     "EMP" + (1001 + count);
//         }

//         // =====================================================
//         // CREATE USER
//         // =====================================================

//         User user = new User();

//         String firstName =
//                 teacher.getFirstName() != null
//                         ? teacher.getFirstName()
//                         : "";

//         String lastName =
//                 teacher.getLastName() != null
//                         ? teacher.getLastName()
//                         : "";

//         user.setName(
//                 (firstName + " " + lastName)
//                         .trim());

//         user.setEmail(
//                 teacher.getEmail());

//         user.setPhone(
//                 teacher.getPhoneNumber());

//         user.setRole("TEACHER");

//         user.setSchool(school);

//         // Password = phone number
//         if (teacher.getPhoneNumber() != null
//                 && !teacher.getPhoneNumber().isBlank()) {

//             user.setPassword(
//                     passwordEncoder.encode(
//                             teacher.getPhoneNumber()));
//         } else {

//             throw new RuntimeException(
//                     "Teacher phone number is required");
//         }

//         user.setStatus("Active");

//         userRepo.save(user);

//         // =====================================================
//         // CREATE TEACHER
//         // =====================================================

//         teacher.setEmployeeId(
//                 employeeId);

//         teacher.setSchool(
//                 school);

//         teacher.setUser(
//                 user);

//         teacher.setActive(true);

//         return teacherRepo.save(teacher);
//     }

//     // =========================================================
//     // GET ALL TEACHERS BY SCHOOL
//     // =========================================================

//     public List<Teacher> getAllTeachers(
//             Long schoolId) {

//         if (schoolId == null) {
//             throw new RuntimeException(
//                     "School ID is required");
//         }

//         return teacherRepo
//                 .findBySchoolId(schoolId);
//     }

//     // =========================================================
//     // GET TEACHERS BY STATUS
//     // =========================================================

//     public List<Teacher> getTeachersByStatus(
//             Long schoolId,
//             String status) {

//         if (schoolId == null) {
//             throw new RuntimeException(
//                     "School ID is required");
//         }

//         if (status == null
//                 || status.isBlank()) {

//             return teacherRepo
//                     .findBySchoolId(schoolId);
//         }

//         return teacherRepo
//                 .findBySchoolIdAndStatus(
//                         schoolId,
//                         status);
//     }

//     // =========================================================
//     // UPDATE TEACHER
//     // =========================================================

//     public Teacher updateTeacher(
//             String employeeId,
//             Long schoolId,
//             Teacher updated) {

//         if (employeeId == null
//                 || employeeId.isBlank()) {

//             throw new RuntimeException(
//                     "Employee ID is required");
//         }

//         if (schoolId == null) {
//             throw new RuntimeException(
//                     "School ID is required");
//         }

//         Teacher teacher =
//                 teacherRepo
//                         .findByEmployeeIdAndSchoolId(
//                                 employeeId,
//                                 schoolId)
//                         .orElseThrow(
//                                 () -> new RuntimeException(
//                                         "Teacher not found"));

//         if (updated.getFirstName() != null) {
//             teacher.setFirstName(
//                     updated.getFirstName());
//         }

//         if (updated.getLastName() != null) {
//             teacher.setLastName(
//                     updated.getLastName());
//         }

//         if (updated.getDepartment() != null) {
//             teacher.setDepartment(
//                     updated.getDepartment());
//         }

//         if (updated.getDesignation() != null) {
//             teacher.setDesignation(
//                     updated.getDesignation());
//         }

//         if (updated.getStatus() != null) {
//             teacher.setStatus(
//                     updated.getStatus());
//         }

//         if (updated.getMobileNumber() != null) {
//             teacher.setMobileNumber(
//                     updated.getMobileNumber());
//         }

//         if (updated.getCategory() != null) {
//             teacher.setCategory(
//                     updated.getCategory());
//         }

//         return teacherRepo.save(teacher);
//     }

//     // =========================================================
//     // DELETE TEACHER
//     // =========================================================

//     public void deleteTeacher(
//             Long id,
//             Long schoolId) {

//         if (id == null) {
//             throw new RuntimeException(
//                     "Teacher ID is required");
//         }

//         if (schoolId == null) {
//             throw new RuntimeException(
//                     "School ID is required");
//         }

//         Teacher teacher =
//                 teacherRepo
//                         .findByIdAndSchoolId(
//                                 id,
//                                 schoolId)
//                         .orElseThrow(
//                                 () -> new RuntimeException(
//                                         "Teacher not found"));

//         User user =
//                 teacher.getUser();

//         /*
//          * Delete teacher first if User/Teacher relationship
//          * has foreign key dependency.
//          */
//         teacherRepo.delete(teacher);

//         if (user != null) {
//             userRepo.delete(user);
//         }
//     }

//     // =========================================================
//     // GET TEACHER BY EMPLOYEE ID
//     // =========================================================

//     public Teacher getTeacherByEmployeeId(
//             String employeeId,
//             Long schoolId) {

//         if (employeeId == null
//                 || employeeId.isBlank()) {

//             throw new RuntimeException(
//                     "Employee ID is required");
//         }

//         if (schoolId == null) {
//             throw new RuntimeException(
//                     "School ID is required");
//         }

//         return teacherRepo
//                 .findByEmployeeIdAndSchoolId(
//                         employeeId,
//                         schoolId)
//                 .orElseThrow(
//                         () -> new RuntimeException(
//                                 "Teacher not found with EmployeeId: "
//                                         + employeeId));
//     }

//     // =========================================================
//     // TOGGLE ACTIVE / INACTIVE
//     // =========================================================

//     public void toggleStatus(
//             Long id,
//             Long schoolId,
//             boolean active) {

//         if (id == null) {
//             throw new RuntimeException(
//                     "Teacher ID is required");
//         }

//         if (schoolId == null) {
//             throw new RuntimeException(
//                     "School ID is required");
//         }

//         Teacher teacher =
//                 teacherRepo
//                         .findByIdAndSchoolId(
//                                 id,
//                                 schoolId)
//                         .orElseThrow(
//                                 () -> new RuntimeException(
//                                         "Teacher not found"));

//         teacher.setActive(active);

//         /*
//          * Keep Teacher status and User status synchronized.
//          */
//         teacher.setStatus(
//                 active
//                         ? "Working"
//                         : "Inactive");

//         if (teacher.getUser() != null) {

//             teacher.getUser()
//                     .setStatus(
//                             active
//                                     ? "Active"
//                                     : "Inactive");

//             userRepo.save(
//                     teacher.getUser());
//         }

//         teacherRepo.save(teacher);
//     }

//     // =========================================================
//     // SEARCH TEACHER BY EMPLOYEE ID
//     // =========================================================

//     public Teacher searchTeachersByEmployeeId(
//             String employeeId,
//             Long schoolId) {

//         if (employeeId == null
//                 || employeeId.isBlank()) {

//             throw new RuntimeException(
//                     "Employee ID is required");
//         }

//         if (schoolId == null) {
//             throw new RuntimeException(
//                     "School ID is required");
//         }

//         return teacherRepo
//                 .findTeacherByEmployeeId(
//                         employeeId,
//                         schoolId);
//     }

//     // =========================================================
//     // PATCH TEACHER FIELDS
//     // =========================================================

//     public Teacher updateTeacherField(
//             String employeeId,
//             Long schoolId,
//             Map<String, String> updates) {

//         if (employeeId == null
//                 || employeeId.isBlank()) {

//             throw new RuntimeException(
//                     "Employee ID is required");
//         }

//         if (schoolId == null) {
//             throw new RuntimeException(
//                     "School ID is required");
//         }

//         if (updates == null
//                 || updates.isEmpty()) {

//             throw new RuntimeException(
//                     "No update fields provided");
//         }

//         Teacher teacher =
//                 teacherRepo
//                         .findByEmployeeIdAndSchoolId(
//                                 employeeId,
//                                 schoolId)
//                         .orElseThrow(
//                                 () -> new RuntimeException(
//                                         "Teacher not found"));

//         if (updates.containsKey("department")) {
//             teacher.setDepartment(
//                     updates.get("department"));
//         }

//         if (updates.containsKey("designation")) {
//             teacher.setDesignation(
//                     updates.get("designation"));
//         }

//         if (updates.containsKey("category")) {
//             teacher.setCategory(
//                     updates.get("category"));
//         }

//         if (updates.containsKey("status")) {
//             teacher.setStatus(
//                     updates.get("status"));
//         }

//         if (updates.containsKey("firstName")) {
//             teacher.setFirstName(
//                     updates.get("firstName"));
//         }

//         if (updates.containsKey("lastName")) {
//             teacher.setLastName(
//                     updates.get("lastName"));
//         }

//         if (updates.containsKey("mobileNumber")) {
//             teacher.setMobileNumber(
//                     updates.get("mobileNumber"));
//         }

//         return teacherRepo.save(teacher);
//     }

//     // =========================================================
//     // ALL TEACHERS
//     // =========================================================

//     public List<Teacher> getAllTeachersList(
//             Long schoolId) {

//         if (schoolId == null) {
//             throw new RuntimeException(
//                     "School ID is required");
//         }

//         return teacherRepo
//                 .findBySchoolId(schoolId);
//     }

//     // =========================================================
//     // TEACHER COUNT BY SCHOOL
//     // =========================================================

//     public long getTeacherCount(
//             Long schoolId) {

//         if (schoolId == null) {
//             return 0;
//         }

//         return teacherRepo
//                 .countBySchoolId(schoolId);
//     }
// }


package com.schoolmanagement.schoolmanagementwebsite.service;

import java.util.Base64;
import java.util.List;
import java.util.Map;

import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import com.schoolmanagement.schoolmanagementwebsite.entity.School;
import com.schoolmanagement.schoolmanagementwebsite.entity.Teacher;
import com.schoolmanagement.schoolmanagementwebsite.entity.User;
import com.schoolmanagement.schoolmanagementwebsite.repository.TeacherRepository;
import com.schoolmanagement.schoolmanagementwebsite.repository.UserRepository;

import lombok.RequiredArgsConstructor;

@Service
@RequiredArgsConstructor
@Transactional
public class TeacherService {

    private final TeacherRepository teacherRepo;
    private final UserRepository userRepo;
    private final PasswordEncoder passwordEncoder;


    // =========================================================
    // ADD TEACHER
    // =========================================================

    public Teacher addTeacher(
            Teacher teacher,
            School school) {

        if (school == null || school.getId() == null) {
            throw new IllegalArgumentException(
                    "School ID is required"
            );
        }

        Long schoolId = school.getId();

        // -----------------------------------------------------
        // Generate school-wise employee ID
        // -----------------------------------------------------

        long count = teacherRepo.countBySchoolId(schoolId);

        String employeeId;

        do {
            count++;

            employeeId = "EMP" + (1000 + count);

        } while (teacherRepo.existsByEmployeeId(employeeId));


        // -----------------------------------------------------
        // Create login user
        // -----------------------------------------------------

        User user = new User();

        user.setName(
                safe(teacher.getFirstName())
                        + " "
                        + safe(teacher.getLastName())
        );
        

        user.setEmail(
                teacher.getEmail()
        );
        user.setUsername(teacher.getEmail());
        user.setPhone(
                teacher.getPhoneNumber()
        );

        user.setRole("TEACHER");

        user.setSchool(school);


        if (teacher.getPhoneNumber() != null
                && !teacher.getPhoneNumber().isBlank()) {

            user.setPassword(
                    passwordEncoder.encode(
                            teacher.getPhoneNumber()
                    )
            );
        }

        user.setStatus("Active");

        User savedUser = userRepo.save(user);


        // -----------------------------------------------------
        // Teacher mapping
        // -----------------------------------------------------

        teacher.setEmployeeId(employeeId);

        teacher.setSchool(school);

        teacher.setUser(savedUser);

        teacher.setActive(true);


        return teacherRepo.save(teacher);
    }


    // =========================================================
    // GET ALL TEACHERS
    // =========================================================

    @Transactional(readOnly = true)
    public List<Teacher> getAllTeachers(
            Long schoolId) {

        validateSchoolId(schoolId);

        return teacherRepo.findBySchoolId(
                schoolId
        );
    }


    // =========================================================
    // GET TEACHERS BY STATUS
    // =========================================================

    @Transactional(readOnly = true)
    public List<Teacher> getTeachersByStatus(
            Long schoolId,
            String status) {

        validateSchoolId(schoolId);

        if (status == null || status.isBlank()) {

            return teacherRepo.findBySchoolId(
                    schoolId
            );
        }

        return teacherRepo.findBySchoolIdAndStatus(
                schoolId,
                status
        );
    }


    // =========================================================
    // GET TEACHER BY ID
    // =========================================================

    @Transactional(readOnly = true)
    public Teacher getTeacherById(
            Long id,
            Long schoolId) {

        validateSchoolId(schoolId);

        return teacherRepo
                .findByIdAndSchoolId(
                        id,
                        schoolId
                )
                .orElseThrow(
                        () -> new RuntimeException(
                                "Teacher not found"
                        )
                );
    }


    // =========================================================
    // GET BY EMPLOYEE ID
    // =========================================================

    @Transactional(readOnly = true)
    public Teacher getTeacherByEmployeeId(
            String employeeId,
            Long schoolId) {

        validateSchoolId(schoolId);

        return teacherRepo
                .findByEmployeeIdAndSchoolId(
                        employeeId,
                        schoolId
                )
                .orElseThrow(
                        () -> new RuntimeException(
                                "Teacher not found with Employee ID: "
                                        + employeeId
                        )
                );
    }


    // =========================================================
    // SEARCH BY EMPLOYEE ID
    // =========================================================

    @Transactional(readOnly = true)
    public Teacher searchTeachersByEmployeeId(
            String employeeId,
            Long schoolId) {

        validateSchoolId(schoolId);

        Teacher teacher =
                teacherRepo.findTeacherByEmployeeId(
                        employeeId,
                        schoolId
                );

        if (teacher == null) {

            throw new RuntimeException(
                    "Teacher not found with Employee ID: "
                            + employeeId
            );
        }

        return teacher;
    }


    // =========================================================
    // SEARCH TEACHERS
    // =========================================================

    @Transactional(readOnly = true)
    public List<Teacher> searchTeachers(
            String keyword,
            Long schoolId) {

        validateSchoolId(schoolId);

        if (keyword == null) {
            keyword = "";
        }

        return teacherRepo.searchTeachers(
                keyword.trim(),
                schoolId
        );
    }


    // =========================================================
    // UPDATE TEACHER
    // =========================================================

    public Teacher updateTeacher(
            String employeeId,
            Long schoolId,
            Teacher updated) {

        validateSchoolId(schoolId);

        Teacher teacher =
                teacherRepo
                        .findByEmployeeIdAndSchoolId(
                                employeeId,
                                schoolId
                        )
                        .orElseThrow(
                                () -> new RuntimeException(
                                        "Teacher not found with Employee ID: "
                                                + employeeId
                                )
                        );


        // -----------------------------------------------------
        // BASIC DETAILS
        // -----------------------------------------------------

        teacher.setFirstName(
                updated.getFirstName()
        );

        teacher.setMiddleName(
                updated.getMiddleName()
        );

        teacher.setLastName(
                updated.getLastName()
        );

        teacher.setDob(
                updated.getDob()
        );

        teacher.setFatherName(
                updated.getFatherName()
        );

        teacher.setDoj(
                updated.getDoj()
        );

        teacher.setGender(
                updated.getGender()
        );

        teacher.setCategory(
                updated.getCategory()
        );

        teacher.setNationality(
                updated.getNationality()
        );

        teacher.setBloodGroup(
                updated.getBloodGroup()
        );


        // -----------------------------------------------------
        // JOB DETAILS
        // -----------------------------------------------------

        teacher.setDepartment(
                updated.getDepartment()
        );

        teacher.setDesignation(
                updated.getDesignation()
        );

        teacher.setTeachingLevel(
                updated.getTeachingLevel()
        );

        teacher.setEmployeeType(
                updated.getEmployeeType()
        );


        // -----------------------------------------------------
        // CONTACT
        // -----------------------------------------------------

        teacher.setPhoneNumber(
                updated.getPhoneNumber()
        );

        teacher.setAlternatePhoneNumber(
                updated.getAlternatePhoneNumber()
        );

        teacher.setMobileNumber(
                updated.getMobileNumber()
        );

        teacher.setEmergencyContact(
                updated.getEmergencyContact()
        );

        teacher.setEmergencyRelation(
                updated.getEmergencyRelation()
        );

        teacher.setEmail(
                updated.getEmail()
        );


        // -----------------------------------------------------
        // ADDRESS
        // -----------------------------------------------------

        teacher.setAddressLine1(
                updated.getAddressLine1()
        );

        teacher.setAddressLine2(
                updated.getAddressLine2()
        );

        teacher.setAddressLine3(
                updated.getAddressLine3()
        );

        teacher.setCity(
                updated.getCity()
        );

        teacher.setState(
                updated.getState()
        );

        teacher.setPincode(
                updated.getPincode()
        );


        // -----------------------------------------------------
        // DOCUMENTS
        // -----------------------------------------------------

        teacher.setPanNumber(
                updated.getPanNumber()
        );

        teacher.setBiometricCard(
                updated.getBiometricCard()
        );

        teacher.setEsiNumber(
                updated.getEsiNumber()
        );

        teacher.setAadharNumber(
                updated.getAadharNumber()
        );

        teacher.setPfNumber(
                updated.getPfNumber()
        );


        // -----------------------------------------------------
        // MARITAL DETAILS
        // -----------------------------------------------------

        teacher.setMaritalStatus(
                updated.getMaritalStatus()
        );

        teacher.setSpouseName(
                updated.getSpouseName()
        );

        teacher.setSpouseGender(
                updated.getSpouseGender()
        );

        teacher.setSpouseDob(
                updated.getSpouseDob()
        );


        // -----------------------------------------------------
        // RELIGIOUS DETAILS
        // -----------------------------------------------------

        teacher.setReligion(
                updated.getReligion()
        );

        teacher.setCaste(
                updated.getCaste()
        );


        // -----------------------------------------------------
        // QUALIFICATION
        // -----------------------------------------------------

        teacher.setQualifiation(
                updated.getQualifiation()
        );

        teacher.setUniversityBoard(
                updated.getUniversityBoard()
        );

        teacher.setPassingYear(
                updated.getPassingYear()
        );

        teacher.setPercentage(
                updated.getPercentage()
        );


        // -----------------------------------------------------
        // EXPERIENCE
        // -----------------------------------------------------

        teacher.setCompanyName(
                updated.getCompanyName()
        );

        teacher.setCompanyDesignation(
                updated.getCompanyDesignation()
        );

        teacher.setStartDate(
                updated.getStartDate()
        );

        teacher.setEndDate(
                updated.getEndDate()
        );

        teacher.setTotalExperience(
                updated.getTotalExperience()
        );


        // -----------------------------------------------------
        // STATUS
        // -----------------------------------------------------

        if (updated.getStatus() != null) {

            teacher.setStatus(
                    updated.getStatus()
            );

            boolean active =
                    "Working".equalsIgnoreCase(
                            updated.getStatus()
                    );

            teacher.setActive(active);

            if (teacher.getUser() != null) {

                teacher.getUser().setStatus(
                        active
                                ? "Active"
                                : "Inactive"
                );

                userRepo.save(
                        teacher.getUser()
                );
            }
        }


        /*
         * IMPORTANT:
         *
         * We intentionally do NOT update:
         *
         * teacher.employeeId
         * teacher.school
         * teacher.user
         *
         * from request body.
         *
         * These are controlled by backend.
         */


        return teacherRepo.save(teacher);
    }


    // =========================================================
    // UPDATE SINGLE FIELD
    // =========================================================

    public Teacher updateTeacherField(
            String employeeId,
            Long schoolId,
            Map<String, String> updates) {

        validateSchoolId(schoolId);

        Teacher teacher =
                teacherRepo
                        .findByEmployeeIdAndSchoolId(
                                employeeId,
                                schoolId
                        )
                        .orElseThrow(
                                () -> new RuntimeException(
                                        "Teacher not found"
                                )
                        );


        if (updates == null || updates.isEmpty()) {
            return teacher;
        }


        if (updates.containsKey("department")) {

            teacher.setDepartment(
                    updates.get("department")
            );
        }


        if (updates.containsKey("designation")) {

            teacher.setDesignation(
                    updates.get("designation")
            );
        }


        if (updates.containsKey("category")) {

            teacher.setCategory(
                    updates.get("category")
            );
        }


        if (updates.containsKey("status")) {

            String status =
                    updates.get("status");

            teacher.setStatus(status);

            boolean active =
                    "Working".equalsIgnoreCase(
                            status
                    );

            teacher.setActive(active);

            if (teacher.getUser() != null) {

                teacher.getUser().setStatus(
                        active
                                ? "Active"
                                : "Inactive"
                );

                userRepo.save(
                        teacher.getUser()
                );
            }
        }


        return teacherRepo.save(teacher);
    }


    // =========================================================
    // DELETE TEACHER
    // =========================================================

    public void deleteTeacher(
            Long id,
            Long schoolId) {

        validateSchoolId(schoolId);

        Teacher teacher =
                teacherRepo
                        .findByIdAndSchoolId(
                                id,
                                schoolId
                        )
                        .orElseThrow(
                                () -> new RuntimeException(
                                        "Teacher not found"
                                )
                        );


        if (teacher.getUser() != null) {

            userRepo.delete(
                    teacher.getUser()
            );
        }


        teacherRepo.delete(teacher);
    }


    // =========================================================
    // TOGGLE ACTIVE STATUS
    // =========================================================

    public void toggleStatus(
            Long id,
            Long schoolId,
            boolean active) {

        validateSchoolId(schoolId);

        Teacher teacher =
                teacherRepo
                        .findByIdAndSchoolId(
                                id,
                                schoolId
                        )
                        .orElseThrow(
                                () -> new RuntimeException(
                                        "Teacher not found"
                                )
                        );


        teacher.setActive(active);

        teacher.setStatus(
                active
                        ? "Working"
                        : "Resign"
        );


        if (teacher.getUser() != null) {

            teacher.getUser().setStatus(
                    active
                            ? "Active"
                            : "Inactive"
            );

            userRepo.save(
                    teacher.getUser()
            );
        }


        teacherRepo.save(teacher);
    }


    // =========================================================
    // GET COUNT
    // =========================================================

    @Transactional(readOnly = true)
    public long getTeacherCount(
            Long schoolId) {

        validateSchoolId(schoolId);

        return teacherRepo.countBySchoolId(
                schoolId
        );
    }


    // =========================================================
    // GET ALL LIST
    // =========================================================

    @Transactional(readOnly = true)
    public List<Teacher> getAllTeachersList(
            Long schoolId) {

        validateSchoolId(schoolId);

        return teacherRepo.findBySchoolId(
                schoolId
        );
    }


    // =========================================================
    // PHOTO
    // =========================================================

    @Transactional(readOnly = true)
    public byte[] getTeacherPhoto(
            Long id,
            Long schoolId) {

        validateSchoolId(schoolId);

        Teacher teacher =
                teacherRepo
                        .findByIdAndSchoolId(
                                id,
                                schoolId
                        )
                        .orElseThrow(
                                () -> new RuntimeException(
                                        "Teacher not found"
                                )
                        );


        String photo = teacher.getPhoto();


        if (photo == null || photo.isBlank()) {

            throw new RuntimeException(
                    "Teacher photo not found"
            );
        }


        try {

            /*
             * Expected format:
             *
             * data:image/jpeg;base64,/9j/4AAQ...
             *
             * OR:
             *
             * data:image/png;base64,...
             *
             * OR raw Base64.
             */

            String base64Data = photo;

            if (photo.contains(",")) {

                base64Data =
                        photo.substring(
                                photo.indexOf(",") + 1
                        );
            }


            return Base64.getDecoder().decode(
                    base64Data
            );

        } catch (IllegalArgumentException e) {

            throw new RuntimeException(
                    "Invalid teacher photo data",
                    e
            );
        }
    }


    // =========================================================
    // PHOTO CONTENT TYPE
    // =========================================================

    @Transactional(readOnly = true)
    public String getTeacherPhotoContentType(
            Long id,
            Long schoolId) {

        validateSchoolId(schoolId);

        Teacher teacher =
                teacherRepo
                        .findByIdAndSchoolId(
                                id,
                                schoolId
                        )
                        .orElseThrow(
                                () -> new RuntimeException(
                                        "Teacher not found"
                                )
                        );


        String photo = teacher.getPhoto();


        if (photo == null || photo.isBlank()) {

            throw new RuntimeException(
                    "Teacher photo not found"
            );
        }


        /*
         * Detect MIME type from data URI.
         */

        if (photo.startsWith("data:image/png")) {

            return "image/png";
        }

        if (photo.startsWith("data:image/webp")) {

            return "image/webp";
        }

        if (photo.startsWith("data:image/gif")) {

            return "image/gif";
        }

        if (photo.startsWith("data:image/jpg")) {

            return "image/jpeg";
        }

        if (photo.startsWith("data:image/jpeg")) {

            return "image/jpeg";
        }


        /*
         * Default
         */

        return "image/jpeg";
    }


    // =========================================================
    // VALIDATE SCHOOL
    // =========================================================

    private void validateSchoolId(
            Long schoolId) {

        if (schoolId == null) {

            throw new IllegalArgumentException(
                    "School ID is required"
            );
        }
    }


    private String safe(String value) {

        return value == null
                ? ""
                : value;
    }
}


