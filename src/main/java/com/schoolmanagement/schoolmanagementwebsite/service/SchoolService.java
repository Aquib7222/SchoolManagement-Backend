// // package com.schoolmanagement.schoolmanagementwebsite.service;

// // import java.time.LocalDateTime;
// // import java.util.List;

// // import org.springframework.beans.factory.annotation.Autowired;
// // import org.springframework.stereotype.Service;

// // import com.schoolmanagement.schoolmanagementwebsite.entity.School;
// // import com.schoolmanagement.schoolmanagementwebsite.repository.SchoolRepository;

// // @Service
// // public class SchoolService {

// //     @Autowired
// //     private SchoolRepository repo;

// //     public School addSchool(School school) {
// //         school.setStatus("Active");
// //         school.setCreatedAt(LocalDateTime.now());
// //          // Set default logo if none provided
    
// //         return repo.save(school);
// //     }

// //     public List<School> getAll() {
// //         return repo.findAll();
// //     }

// //     public void deleteSchool(Long id) {

// //     School school = repo.findById(id)
// //             .orElseThrow(() -> new RuntimeException("School not found"));

// //     // Soft delete
// //     school.setStatus("Inactive");
// //     repo.save(school);
// // }
// // public School getSchoolById(Long id) {
// //     return repo.findById(id)
// //             .orElseThrow(() -> new RuntimeException("School not found with id " + id));
// // }



// //     public School toggleStatus(Long id) {
// //         School s = repo.findById(id).orElseThrow();
// //         s.setStatus(s.getStatus().equals("Active") ? "Inactive" : "Active");
// //         return repo.save(s);
// //     }
// // }

// // package com.schoolmanagement.schoolmanagementwebsite.service;

// // import java.time.LocalDate;
// // import java.time.LocalDateTime;
// // import java.util.List;

// // import org.springframework.beans.factory.annotation.Autowired;
// // import org.springframework.stereotype.Service;

// // import com.schoolmanagement.schoolmanagementwebsite.entity.School;
// // import com.schoolmanagement.schoolmanagementwebsite.repository.SchoolRepository;

// // @Service
// // public class SchoolService {

// // @Autowired
// // private SchoolRepository repo;

// // public School addSchool(School school) {

// //     school.setStatus("Active");
// //     school.setCreatedAt(LocalDateTime.now());

// //     applySubscriptionDetails(school);

// //     return repo.save(school);
// // }

// // public List<School> getAll() {
// //     return repo.findAll();
// // }

// // public School getSchoolById(Long id) {
// //     return repo.findById(id)
// //             .orElseThrow(() ->
// //                     new RuntimeException("School not found with id " + id));
// // }

// // public School updateSchool(Long id, School updatedSchool) {

// //     School school = repo.findById(id)
// //             .orElseThrow(() ->
// //                     new RuntimeException("School not found"));

// //     school.setSchoolName(updatedSchool.getSchoolName());
// //     school.setSchoolCode(updatedSchool.getSchoolCode());
// //     school.setSchoolType(updatedSchool.getSchoolType());
// //     school.setRegistrationNumber(updatedSchool.getRegistrationNumber());
// //     school.setAffiliationBoard(updatedSchool.getAffiliationBoard());
// //     school.setEstablishedYear(updatedSchool.getEstablishedYear());
// //     school.setAcademicYear(updatedSchool.getAcademicYear());

// //     school.setPrincipalName(updatedSchool.getPrincipalName());
// //     school.setContactPerson(updatedSchool.getContactPerson());
// //     school.setMobileNo(updatedSchool.getMobileNo());
// //     school.setAlternateNo(updatedSchool.getAlternateNo());
// //     school.setEmail(updatedSchool.getEmail());

// //     school.setAddress(updatedSchool.getAddress());
// //     school.setCity(updatedSchool.getCity());
// //     school.setState(updatedSchool.getState());
// //     school.setCountry(updatedSchool.getCountry());
// //     school.setPincode(updatedSchool.getPincode());

// //     school.setSubscriptionPlan(updatedSchool.getSubscriptionPlan());
// //     school.setSubscriptionType(updatedSchool.getSubscriptionType());
// //     school.setStartDate(updatedSchool.getStartDate());

// //     applySubscriptionDetails(school);

// //     return repo.save(school);
// // }

// // public void deleteSchool(Long id) {

// //     School school = repo.findById(id)
// //             .orElseThrow(() ->
// //                     new RuntimeException("School not found"));

// //     school.setStatus("Inactive");

// //     repo.save(school);
// // }

// // public School toggleStatus(Long id) {

// //     School school = repo.findById(id)
// //             .orElseThrow(() ->
// //                     new RuntimeException("School not found"));

// //     school.setStatus(
// //             school.getStatus().equalsIgnoreCase("Active")
// //                     ? "Inactive"
// //                     : "Active");

// //     return repo.save(school);
// // }

// // private void applySubscriptionDetails(School school) {

// //     String plan = school.getSubscriptionPlan();
// //     String type = school.getSubscriptionType();

// //     double amount = 0;

// //     if ("Basic".equalsIgnoreCase(plan)) {

// //         school.setMaxStudents(500);
// //         school.setMaxTeachers(20);
// //         school.setMaxAdmins(2);
// //         school.setStorageLimit(10);
// //         school.setSmsCredits(1000);
// //         school.setWhatsappCredits(500);

// //         if ("Monthly".equalsIgnoreCase(type))
// //             amount = 2000;
// //         else if ("Quarterly".equalsIgnoreCase(type))
// //             amount = 6000;
// //         else if ("Annually".equalsIgnoreCase(type))
// //             amount = 24000;

// //     } else if ("Standard".equalsIgnoreCase(plan)) {

// //         school.setMaxStudents(1500);
// //         school.setMaxTeachers(75);
// //         school.setMaxAdmins(5);
// //         school.setStorageLimit(50);
// //         school.setSmsCredits(5000);
// //         school.setWhatsappCredits(2500);

// //         if ("Monthly".equalsIgnoreCase(type))
// //             amount = 3500;
// //         else if ("Quarterly".equalsIgnoreCase(type))
// //             amount = 10500;
// //         else if ("Annually".equalsIgnoreCase(type))
// //             amount = 42000;

// //     } else if ("Premium".equalsIgnoreCase(plan)) {

// //         school.setMaxStudents(5000);
// //         school.setMaxTeachers(200);
// //         school.setMaxAdmins(10);
// //         school.setStorageLimit(200);
// //         school.setSmsCredits(20000);
// //         school.setWhatsappCredits(10000);

// //         if ("Monthly".equalsIgnoreCase(type))
// //             amount = 5000;
// //         else if ("Quarterly".equalsIgnoreCase(type))
// //             amount = 15000;
// //         else if ("Annually".equalsIgnoreCase(type))
// //             amount = 60000;
// //     }

// //     school.setAmount(amount);

// //     if (school.getStartDate() != null) {

// //         LocalDate endDate = school.getStartDate();

// //         if ("Monthly".equalsIgnoreCase(type)) {
// //             endDate = endDate.plusMonths(1);
// //         } else if ("Quarterly".equalsIgnoreCase(type)) {
// //             endDate = endDate.plusMonths(3);
// //         } else if ("Annually".equalsIgnoreCase(type)) {
// //             endDate = endDate.plusYears(1);
// //         }

// //         school.setEndDate(endDate);
// //         school.setRenewalDate(endDate);
// //     }

// //     if (school.getSubscriptionStatus() == null) {
// //         school.setSubscriptionStatus("Active");
// //     }

// //     if (school.getPaymentStatus() == null) {
// //         school.setPaymentStatus("Pending");
// //     }
// // }


// // }


// // package com.schoolmanagement.schoolmanagementwebsite.service;

// // import java.time.LocalDate;
// // import java.time.LocalDateTime;
// // import java.util.List;

// // import org.springframework.beans.factory.annotation.Autowired;
// // import org.springframework.stereotype.Service;

// // import com.schoolmanagement.schoolmanagementwebsite.audit.annotation.Audit;
// // import com.schoolmanagement.schoolmanagementwebsite.audit.enums.AuditAction;
// // import com.schoolmanagement.schoolmanagementwebsite.entity.School;
// // import com.schoolmanagement.schoolmanagementwebsite.repository.SchoolRepository;

// // @Service
// // public class SchoolService {

// //     @Autowired
// //     private SchoolRepository repo;


// //     // =========================
// //     // CREATE SCHOOL
// //     // =========================

// //     @Audit(
// //         module = "SCHOOL",
// //         action = AuditAction.CREATE,
// //         description = "Created new school",
// //         targetType = "SCHOOL"
// //     )
// //     public School addSchool(School school) {

// //         school.setStatus("Active");
// //         school.setCreatedAt(LocalDateTime.now());

// //         applySubscriptionDetails(school);

// //         return repo.save(school);
// //     }


// //     // =========================
// //     // GET ALL SCHOOLS
// //     // =========================

// //     public List<School> getAll() {

// //         return repo.findAll();
// //     }


// //     // =========================
// //     // GET SCHOOL BY ID
// //     // =========================

// //     public School getSchoolById(Long id) {

// //         return repo.findById(id)
// //                 .orElseThrow(() ->
// //                         new RuntimeException(
// //                                 "School not found with id " + id
// //                         ));
// //     }


// //     // =========================
// //     // UPDATE SCHOOL
// //     // =========================

// //     @Audit(
// //         module = "SCHOOL",
// //         action = AuditAction.UPDATE,
// //         description = "Updated school information",
// //         targetType = "SCHOOL"
// //     )
// //     public School updateSchool(
// //             Long id,
// //             School updatedSchool
// //     ) {

// //         School school = repo.findById(id)
// //                 .orElseThrow(() ->
// //                         new RuntimeException(
// //                                 "School not found"
// //                         ));


// //         school.setSchoolName(
// //                 updatedSchool.getSchoolName()
// //         );

// //         school.setSchoolCode(
// //                 updatedSchool.getSchoolCode()
// //         );

// //         school.setSchoolType(
// //                 updatedSchool.getSchoolType()
// //         );

// //         school.setRegistrationNumber(
// //                 updatedSchool.getRegistrationNumber()
// //         );

// //         school.setAffiliationBoard(
// //                 updatedSchool.getAffiliationBoard()
// //         );

// //         school.setEstablishedYear(
// //                 updatedSchool.getEstablishedYear()
// //         );

// //         school.setAcademicYear(
// //                 updatedSchool.getAcademicYear()
// //         );


// //         school.setPrincipalName(
// //                 updatedSchool.getPrincipalName()
// //         );

// //         school.setContactPerson(
// //                 updatedSchool.getContactPerson()
// //         );

// //         school.setMobileNo(
// //                 updatedSchool.getMobileNo()
// //         );

// //         school.setAlternateNo(
// //                 updatedSchool.getAlternateNo()
// //         );

// //         school.setEmail(
// //                 updatedSchool.getEmail()
// //         );


// //         school.setAddress(
// //                 updatedSchool.getAddress()
// //         );

// //         school.setCity(
// //                 updatedSchool.getCity()
// //         );

// //         school.setState(
// //                 updatedSchool.getState()
// //         );

// //         school.setCountry(
// //                 updatedSchool.getCountry()
// //         );

// //         school.setPincode(
// //                 updatedSchool.getPincode()
// //         );


// //         school.setSubscriptionPlan(
// //                 updatedSchool.getSubscriptionPlan()
// //         );

// //         school.setSubscriptionType(
// //                 updatedSchool.getSubscriptionType()
// //         );

// //         school.setStartDate(
// //                 updatedSchool.getStartDate()
// //         );


// //         applySubscriptionDetails(school);


// //         return repo.save(school);
// //     }


// //     // =========================
// //     // DELETE / DEACTIVATE SCHOOL
// //     // =========================

// //     @Audit(
// //         module = "SCHOOL",
// //         action = AuditAction.DELETE,
// //         description = "Deactivated school",
// //         targetType = "SCHOOL"
// //     )
// //     public void deleteSchool(Long id) {

// //         School school = repo.findById(id)
// //                 .orElseThrow(() ->
// //                         new RuntimeException(
// //                                 "School not found"
// //                         ));

// //         /*
// //          * This is actually a soft delete.
// //          * School is not physically deleted.
// //          */
// //         school.setStatus("Inactive");

// //         repo.save(school);
// //     }


// //     // =========================
// //     // TOGGLE SCHOOL STATUS
// //     // =========================

// //     @Audit(
// //         module = "SCHOOL",
// //         action = AuditAction.UPDATE,
// //         description = "Changed school status",
// //         targetType = "SCHOOL"
// //     )
// //     public School toggleStatus(Long id) {

// //         School school = repo.findById(id)
// //                 .orElseThrow(() ->
// //                         new RuntimeException(
// //                                 "School not found"
// //                         ));


// //         school.setStatus(
// //                 school.getStatus().equalsIgnoreCase("Active")
// //                         ? "Inactive"
// //                         : "Active"
// //         );


// //         return repo.save(school);
// //     }


// //     // =========================
// //     // SUBSCRIPTION DETAILS
// //     // =========================

// //     private void applySubscriptionDetails(
// //             School school
// //     ) {

// //         String plan =
// //                 school.getSubscriptionPlan();

// //         String type =
// //                 school.getSubscriptionType();


// //         double amount = 0;


// //         // =========================
// //         // BASIC PLAN
// //         // =========================

// //         if ("Basic".equalsIgnoreCase(plan)) {

// //             school.setMaxStudents(500);
// //             school.setMaxTeachers(20);
// //             school.setMaxAdmins(2);
// //             school.setStorageLimit(10);
// //             school.setSmsCredits(1000);
// //             school.setWhatsappCredits(500);


// //             if ("Monthly".equalsIgnoreCase(type)) {

// //                 amount = 2000;

// //             } else if ("Quarterly".equalsIgnoreCase(type)) {

// //                 amount = 6000;

// //             } else if ("Annually".equalsIgnoreCase(type)) {

// //                 amount = 24000;
// //             }


// //         }

// //         // =========================
// //         // STANDARD PLAN
// //         // =========================

// //         else if ("Standard".equalsIgnoreCase(plan)) {

// //             school.setMaxStudents(1500);
// //             school.setMaxTeachers(75);
// //             school.setMaxAdmins(5);
// //             school.setStorageLimit(50);
// //             school.setSmsCredits(5000);
// //             school.setWhatsappCredits(2500);


// //             if ("Monthly".equalsIgnoreCase(type)) {

// //                 amount = 3500;

// //             } else if ("Quarterly".equalsIgnoreCase(type)) {

// //                 amount = 10500;

// //             } else if ("Annually".equalsIgnoreCase(type)) {

// //                 amount = 42000;
// //             }


// //         }

// //         // =========================
// //         // PREMIUM PLAN
// //         // =========================

// //         else if ("Premium".equalsIgnoreCase(plan)) {

// //             school.setMaxStudents(5000);
// //             school.setMaxTeachers(200);
// //             school.setMaxAdmins(10);
// //             school.setStorageLimit(200);
// //             school.setSmsCredits(20000);
// //             school.setWhatsappCredits(10000);


// //             if ("Monthly".equalsIgnoreCase(type)) {

// //                 amount = 5000;

// //             } else if ("Quarterly".equalsIgnoreCase(type)) {

// //                 amount = 15000;

// //             } else if ("Annually".equalsIgnoreCase(type)) {

// //                 amount = 60000;
// //             }
// //         }


// //         school.setAmount(amount);


// //         // =========================
// //         // SUBSCRIPTION DATES
// //         // =========================

// //         if (school.getStartDate() != null) {

// //             LocalDate endDate =
// //                     school.getStartDate();


// //             if ("Monthly".equalsIgnoreCase(type)) {

// //                 endDate =
// //                         endDate.plusMonths(1);

// //             } else if ("Quarterly".equalsIgnoreCase(type)) {

// //                 endDate =
// //                         endDate.plusMonths(3);

// //             } else if ("Annually".equalsIgnoreCase(type)) {

// //                 endDate =
// //                         endDate.plusYears(1);
// //             }


// //             school.setEndDate(endDate);

// //             school.setRenewalDate(endDate);
// //         }


// //         // =========================
// //         // DEFAULT SUBSCRIPTION STATUS
// //         // =========================

// //         if (school.getSubscriptionStatus() == null) {

// //             school.setSubscriptionStatus("Active");
// //         }


// //         // =========================
// //         // DEFAULT PAYMENT STATUS
// //         // =========================

// //         if (school.getPaymentStatus() == null) {

// //             school.setPaymentStatus("Pending");
// //         }
// //     }
// // }

// package com.schoolmanagement.schoolmanagementwebsite.service;

// import java.nio.file.Files;
// import java.nio.file.StandardCopyOption;
// import java.util.List;

// import org.springframework.beans.factory.annotation.Autowired;
// import org.springframework.stereotype.Service;
// import org.springframework.transaction.annotation.Transactional;
// import org.springframework.web.multipart.MultipartFile;

// import com.schoolmanagement.schoolmanagementwebsite.audit.annotation.Audit;
// import com.schoolmanagement.schoolmanagementwebsite.audit.enums.AuditAction;
// import com.schoolmanagement.schoolmanagementwebsite.entity.School;
// import com.schoolmanagement.schoolmanagementwebsite.repository.SchoolRepository;

// @Service
// @Transactional
// public class SchoolService {

//     @Autowired
//     private SchoolRepository repo;


//     // =========================================================
//     // CREATE SCHOOL
//     // =========================================================

//     @Audit(
//         module = "SCHOOL",
//         action = AuditAction.CREATE,
//         description = "Created new school",
//         targetType = "SCHOOL"
//     )
//    public School addSchool(
//         School school,
//         MultipartFile attachment) {

//     // =========================
//     // LOGO UPLOAD
//     // =========================

//     if (attachment != null && !attachment.isEmpty()) {

//         try {

//             String uploadDir = "uploads/schools/";

//             Path directory = Paths.get(uploadDir);

//             if (!Files.exists(directory)) {
//                 Files.createDirectories(directory);
//             }

//             String originalFileName =
//                     attachment.getOriginalFilename();

//             String fileName =
//                     System.currentTimeMillis()
//                     + "_"
//                     + originalFileName;

//             Path filePath =
//                     directory.resolve(fileName);

//             Files.copy(
//                     attachment.getInputStream(),
//                     filePath,
//                     StandardCopyOption.REPLACE_EXISTING
//             );

//             school.setLogoUrl(
//                     "/uploads/schools/" + fileName
//             );

//         } catch (IOException e) {

//             throw new RuntimeException(
//                     "Failed to upload school logo",
//                     e
//             );
//         }
//     }

//     return repo.save(school);
// }

//     // =========================================================
//     // GET ALL SCHOOLS
//     // =========================================================

//     public List<School> getAll() {

//         return repo.findAll();
//     }


//     // =========================================================
//     // GET SCHOOL BY ID
//     // =========================================================

//     public School getSchoolById(Long id) {

//         return repo.findById(id)
//                 .orElseThrow(() ->
//                     new RuntimeException(
//                         "School not found with id: " + id
//                     )
//                 );
//     }


//     // =========================================================
//     // UPDATE SCHOOL
//     // =========================================================

//     @Audit(
//         module = "SCHOOL",
//         action = AuditAction.UPDATE,
//         description = "Updated school information",
//         targetType = "SCHOOL"
//     )
//     public School updateSchool(
//             Long id,
//             School updatedSchool
//     ) {

//         School school = repo.findById(id)
//                 .orElseThrow(() ->
//                     new RuntimeException(
//                         "School not found with id: " + id
//                     )
//                 );


//         // =====================================================
//         // BASIC INFORMATION
//         // =====================================================

//         school.setSchoolName(
//             updatedSchool.getSchoolName()
//         );

//         /*
//          * School Code update karte waqt duplicate check.
//          */
//         if (
//             updatedSchool.getSchoolCode() != null
//             && !updatedSchool.getSchoolCode()
//                 .equals(school.getSchoolCode())
//         ) {

//             if (
//                 repo.existsBySchoolCode(
//                     updatedSchool.getSchoolCode()
//                 )
//             ) {

//                 throw new RuntimeException(
//                     "School code already exists: "
//                     + updatedSchool.getSchoolCode()
//                 );
//             }

//             school.setSchoolCode(
//                 updatedSchool.getSchoolCode()
//             );
//         }


//         school.setOrganizationName(
//             updatedSchool.getOrganizationName()
//         );


//         // =====================================================
//         // ADDRESS INFORMATION
//         // =====================================================

//         school.setAddressLine1(
//             updatedSchool.getAddressLine1()
//         );

//         school.setAddressLine2(
//             updatedSchool.getAddressLine2()
//         );

//         school.setCity(
//             updatedSchool.getCity()
//         );

//         school.setState(
//             updatedSchool.getState()
//         );

//         school.setCountry(
//             updatedSchool.getCountry()
//         );

//         school.setPincode(
//             updatedSchool.getPincode()
//         );


//         // =====================================================
//         // SCHOOL LOGO
//         // =====================================================

//         school.setLogoUrl(
//             updatedSchool.getLogoUrl()
//         );


//         // =====================================================
//         // CONTACT INFORMATION
//         // =====================================================

//         school.setContactPerson(
//             updatedSchool.getContactPerson()
//         );

//         school.setDesignation(
//             updatedSchool.getDesignation()
//         );

//         school.setEmail(
//             updatedSchool.getEmail()
//         );

//         school.setPhoneNumber(
//             updatedSchool.getPhoneNumber()
//         );

//         school.setAlternatePhone(
//             updatedSchool.getAlternatePhone()
//         );


//         // =====================================================
//         // ACADEMIC INFORMATION
//         // =====================================================

//         school.setAcademicSessionStartMonth(
//             updatedSchool.getAcademicSessionStartMonth()
//         );

//         school.setAcademicSessionFormat(
//             updatedSchool.getAcademicSessionFormat()
//         );

//         school.setDefaultLanguage(
//             updatedSchool.getDefaultLanguage()
//         );

//         school.setCurrency(
//             updatedSchool.getCurrency()
//         );


//         // =====================================================
//         // OTHER INFORMATION
//         // =====================================================

//         school.setSchoolType(
//             updatedSchool.getSchoolType()
//         );

//         school.setSchoolCategory(
//             updatedSchool.getSchoolCategory()
//         );

//         school.setAffiliationBoard(
//             updatedSchool.getAffiliationBoard()
//         );

//         school.setEstablishedYear(
//             updatedSchool.getEstablishedYear()
//         );

//         school.setTotalClasses(
//             updatedSchool.getTotalClasses()
//         );

//         school.setTotalStudents(
//             updatedSchool.getTotalStudents()
//         );

//         school.setDescription(
//             updatedSchool.getDescription()
//         );


//         // =====================================================
//         // STATUS & SETTINGS
//         // =====================================================

//         if (updatedSchool.getActive() != null) {

//             school.setActive(
//                 updatedSchool.getActive()
//             );
//         }

//         if (updatedSchool.getAllowParentLogin() != null) {

//             school.setAllowParentLogin(
//                 updatedSchool.getAllowParentLogin()
//             );
//         }

//         if (updatedSchool.getAllowStudentLogin() != null) {

//             school.setAllowStudentLogin(
//                 updatedSchool.getAllowStudentLogin()
//             );
//         }

//         school.setTimeZone(
//             updatedSchool.getTimeZone()
//         );

//         school.setDateFormat(
//             updatedSchool.getDateFormat()
//         );


//         /*
//          * updatedAt @PreUpdate se automatically set hoga.
//          */

//         return repo.save(school);
//     }


//     // =========================================================
//     // DELETE / DEACTIVATE SCHOOL
//     // =========================================================

//     @Audit(
//         module = "SCHOOL",
//         action = AuditAction.DELETE,
//         description = "Deactivated school",
//         targetType = "SCHOOL"
//     )
//     public void deleteSchool(Long id) {

//         School school = repo.findById(id)
//                 .orElseThrow(() ->
//                     new RuntimeException(
//                         "School not found with id: " + id
//                     )
//                 );


//         /*
//          * Soft Delete
//          *
//          * Database se record delete nahi hoga.
//          * Sirf active = false hoga.
//          */

//         school.setActive(false);

//         repo.save(school);
//     }


//     // =========================================================
//     // ACTIVATE SCHOOL
//     // =========================================================

//     @Audit(
//         module = "SCHOOL",
//         action = AuditAction.UPDATE,
//         description = "Activated school",
//         targetType = "SCHOOL"
//     )
//     public School activateSchool(Long id) {

//         School school = repo.findById(id)
//                 .orElseThrow(() ->
//                     new RuntimeException(
//                         "School not found with id: " + id
//                     )
//                 );

//         school.setActive(true);

//         return repo.save(school);
//     }


//     // =========================================================
//     // TOGGLE SCHOOL STATUS
//     // =========================================================

//     @Audit(
//         module = "SCHOOL",
//         action = AuditAction.UPDATE,
//         description = "Changed school active status",
//         targetType = "SCHOOL"
//     )
//     public School toggleStatus(Long id) {

//         School school = repo.findById(id)
//                 .orElseThrow(() ->
//                     new RuntimeException(
//                         "School not found with id: " + id
//                     )
//                 );


//         school.setActive(
//             !Boolean.TRUE.equals(
//                 school.getActive()
//             )
//         );


//         return repo.save(school);
//     }
// }

package com.schoolmanagement.schoolmanagementwebsite.service;

import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.nio.file.StandardCopyOption;
import java.util.List;
import java.util.UUID;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.web.multipart.MultipartFile;

import com.schoolmanagement.schoolmanagementwebsite.audit.annotation.Audit;
import com.schoolmanagement.schoolmanagementwebsite.audit.enums.AuditAction;
import com.schoolmanagement.schoolmanagementwebsite.entity.School;
import com.schoolmanagement.schoolmanagementwebsite.repository.SchoolRepository;

@Service
@Transactional
public class SchoolService {

    @Autowired
    private SchoolRepository repo;

    // =========================================================
    // UPLOAD DIRECTORY
    // =========================================================

    private static final String UPLOAD_DIR =
            "uploads/schools/";

    // =========================================================
    // CREATE SCHOOL
    // =========================================================

    @Audit(
        module = "SCHOOL",
        action = AuditAction.CREATE,
        description = "Created new school",
        targetType = "SCHOOL"
    )
    public School addSchool(
            School school,
            MultipartFile attachment) {

        // =====================================================
        // DUPLICATE SCHOOL CODE CHECK
        // =====================================================

        if (school.getSchoolCode() != null
                && repo.existsBySchoolCode(
                    school.getSchoolCode()
                )) {

            throw new RuntimeException(
                "School code already exists: "
                + school.getSchoolCode()
            );
        }

        // =====================================================
        // LOGO UPLOAD
        // =====================================================

        if (attachment != null
                && !attachment.isEmpty()) {

            String logoUrl =
                    uploadLogo(attachment);

            school.setLogoUrl(logoUrl);
        }

        // =====================================================
        // DEFAULT VALUES
        // =====================================================

        if (school.getActive() == null) {
            school.setActive(true);
        }

        if (school.getAllowParentLogin() == null) {
            school.setAllowParentLogin(true);
        }

        if (school.getAllowStudentLogin() == null) {
            school.setAllowStudentLogin(true);
        }

        // =====================================================
        // SAVE
        // =====================================================

        return repo.save(school);
    }

    // =========================================================
    // GET ALL SCHOOLS
    // =========================================================

    public List<School> getAll() {

        return repo.findAll();
    }

    // =========================================================
    // GET SCHOOL BY ID
    // =========================================================

    public School getSchoolById(Long id) {

        return repo.findById(id)
                .orElseThrow(() ->
                    new RuntimeException(
                        "School not found with id: " + id
                    )
                );
    }

    // =========================================================
    // UPDATE SCHOOL
    // =========================================================

    @Audit(
        module = "SCHOOL",
        action = AuditAction.UPDATE,
        description = "Updated school information",
        targetType = "SCHOOL"
    )
    public School updateSchool(
            Long id,
            School updatedSchool,
            MultipartFile attachment) {

        School school =
                repo.findById(id)
                    .orElseThrow(() ->
                        new RuntimeException(
                            "School not found with id: "
                            + id
                        )
                    );

        // =====================================================
        // SCHOOL CODE
        // =====================================================

        if (updatedSchool.getSchoolCode() != null
                && !updatedSchool.getSchoolCode()
                    .equals(school.getSchoolCode())) {

            if (repo.existsBySchoolCode(
                    updatedSchool.getSchoolCode())) {

                throw new RuntimeException(
                    "School code already exists: "
                    + updatedSchool.getSchoolCode()
                );
            }

            school.setSchoolCode(
                updatedSchool.getSchoolCode()
            );
        }

        // =====================================================
        // BASIC INFORMATION
        // =====================================================

        school.setSchoolName(
            updatedSchool.getSchoolName()
        );

        school.setOrganizationName(
            updatedSchool.getOrganizationName()
        );

        // =====================================================
        // ADDRESS
        // =====================================================

        school.setAddressLine1(
            updatedSchool.getAddressLine1()
        );

        school.setAddressLine2(
            updatedSchool.getAddressLine2()
        );

        school.setCity(
            updatedSchool.getCity()
        );

        school.setState(
            updatedSchool.getState()
        );

        school.setCountry(
            updatedSchool.getCountry()
        );

        school.setPincode(
            updatedSchool.getPincode()
        );

        // =====================================================
        // LOGO
        // =====================================================

        if (attachment != null
                && !attachment.isEmpty()) {

            String logoUrl =
                    uploadLogo(attachment);

            school.setLogoUrl(logoUrl);

        } else if (updatedSchool.getLogoUrl() != null) {

            school.setLogoUrl(
                updatedSchool.getLogoUrl()
            );
        }

        // =====================================================
        // CONTACT INFORMATION
        // =====================================================

        school.setContactPerson(
            updatedSchool.getContactPerson()
        );

        school.setDesignation(
            updatedSchool.getDesignation()
        );

        school.setEmail(
            updatedSchool.getEmail()
        );

        school.setPhoneNumber(
            updatedSchool.getPhoneNumber()
        );

        school.setAlternatePhone(
            updatedSchool.getAlternatePhone()
        );

        // =====================================================
        // ACADEMIC INFORMATION
        // =====================================================

        school.setAcademicSessionStartMonth(
            updatedSchool
                .getAcademicSessionStartMonth()
        );

        school.setAcademicSessionFormat(
            updatedSchool
                .getAcademicSessionFormat()
        );

        school.setDefaultLanguage(
            updatedSchool.getDefaultLanguage()
        );

        school.setCurrency(
            updatedSchool.getCurrency()
        );

        // =====================================================
        // OTHER INFORMATION
        // =====================================================

        school.setSchoolType(
            updatedSchool.getSchoolType()
        );

        school.setSchoolCategory(
            updatedSchool.getSchoolCategory()
        );

        school.setAffiliationBoard(
            updatedSchool.getAffiliationBoard()
        );

        school.setEstablishedYear(
            updatedSchool.getEstablishedYear()
        );

        school.setTotalClasses(
            updatedSchool.getTotalClasses()
        );

        school.setTotalStudents(
            updatedSchool.getTotalStudents()
        );

        school.setDescription(
            updatedSchool.getDescription()
        );

        // =====================================================
        // STATUS & SETTINGS
        // =====================================================

        if (updatedSchool.getActive() != null) {

            school.setActive(
                updatedSchool.getActive()
            );
        }

        if (updatedSchool.getAllowParentLogin() != null) {

            school.setAllowParentLogin(
                updatedSchool.getAllowParentLogin()
            );
        }

        if (updatedSchool.getAllowStudentLogin() != null) {

            school.setAllowStudentLogin(
                updatedSchool.getAllowStudentLogin()
            );
        }

        school.setTimeZone(
            updatedSchool.getTimeZone()
        );

        school.setDateFormat(
            updatedSchool.getDateFormat()
        );

        // =====================================================
        // SAVE
        // =====================================================

        return repo.save(school);
    }

    // =========================================================
    // LOGO UPLOAD METHOD
    // =========================================================

    private String uploadLogo(
            MultipartFile attachment) {

        try {

            Path directory =
                    Paths.get(UPLOAD_DIR);

            if (!Files.exists(directory)) {

                Files.createDirectories(
                    directory
                );
            }

            String originalFileName =
                    attachment.getOriginalFilename();

            String extension = "";

            if (originalFileName != null
                    && originalFileName.contains(".")) {

                extension =
                    originalFileName.substring(
                        originalFileName.lastIndexOf(".")
                    );
            }

            String fileName =
                    UUID.randomUUID()
                    + extension;

            Path filePath =
                    directory.resolve(fileName);

            Files.copy(
                attachment.getInputStream(),
                filePath,
                StandardCopyOption.REPLACE_EXISTING
            );

            return "/uploads/schools/" + fileName;

        } catch (IOException e) {

            throw new RuntimeException(
                "Failed to upload school logo",
                e
            );
        }
    }

    // =========================================================
    // DELETE / DEACTIVATE SCHOOL
    // =========================================================

    @Audit(
        module = "SCHOOL",
        action = AuditAction.DELETE,
        description = "Deactivated school",
        targetType = "SCHOOL"
    )
    public void deleteSchool(Long id) {

        School school =
                repo.findById(id)
                    .orElseThrow(() ->
                        new RuntimeException(
                            "School not found with id: "
                            + id
                        )
                    );

        school.setActive(false);

        repo.save(school);
    }

    // =========================================================
    // ACTIVATE SCHOOL
    // =========================================================

    @Audit(
        module = "SCHOOL",
        action = AuditAction.UPDATE,
        description = "Activated school",
        targetType = "SCHOOL"
    )
    public School activateSchool(Long id) {

        School school =
                repo.findById(id)
                    .orElseThrow(() ->
                        new RuntimeException(
                            "School not found with id: "
                            + id
                        )
                    );

        school.setActive(true);

        return repo.save(school);
    }

    // =========================================================
    // TOGGLE SCHOOL STATUS
    // =========================================================

    @Audit(
        module = "SCHOOL",
        action = AuditAction.UPDATE,
        description = "Changed school active status",
        targetType = "SCHOOL"
    )
    public School toggleStatus(Long id) {

        School school =
                repo.findById(id)
                    .orElseThrow(() ->
                        new RuntimeException(
                            "School not found with id: "
                            + id
                        )
                    );

        school.setActive(
            !Boolean.TRUE.equals(
                school.getActive()
            )
        );

        return repo.save(school);
    }
}