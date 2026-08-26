// package com.schoolmanagement.schoolmanagementwebsite.service;

// import java.util.List;

// import org.springframework.beans.factory.annotation.Autowired;
// import org.springframework.security.crypto.password.PasswordEncoder;
// import org.springframework.stereotype.Service;

// import com.schoolmanagement.schoolmanagementwebsite.entity.School;
// import com.schoolmanagement.schoolmanagementwebsite.entity.SuperAdmin;
// import com.schoolmanagement.schoolmanagementwebsite.entity.User;
// import com.schoolmanagement.schoolmanagementwebsite.repository.SchoolRepository;
// import com.schoolmanagement.schoolmanagementwebsite.repository.SuperAdminRepository;
// import com.schoolmanagement.schoolmanagementwebsite.repository.UserRepository;

// // @Service
// // public class SuperAdminService {

// //     @Autowired
// //     private UserRepository userRepository;

// //     @Autowired
// //     private SuperAdminRepository superAdminRepository;

// //     @Autowired
// //     private SchoolRepository schoolRepository;

// //     @Autowired
// //     private PasswordEncoder passwordEncoder;

// //     public SuperAdmin createSuperAdmin(
// //             String name,
// //             String email,
// //             String password,
// //             String phone,
// //             String role,
// //             Long schoolId
// //     ) {

// //         // 2️⃣ Create SuperAdmin profile
// //         School school = schoolRepository.findById(schoolId)
// //                 .orElseThrow(() -> new RuntimeException("School not found"));

// //         // 1️⃣ Create login user (BCrypt)
// //         User user = new User();
// //         user.setEmail(email);
// //         user.setPassword(passwordEncoder.encode(password)); // 🔐 BCrypt
// //         user.setName(name);
// //         user.setPhone(phone);
// //         user.setRole(role);
// //         user.setStatus("Active");
// //         user.setSchool(school);

// //         userRepository.save(user);

// //         SuperAdmin admin = new SuperAdmin();
// //         admin.setFullName(name);
// //         admin.setEmail(email);
// //         admin.setPhone(phone);
// //         admin.setRole(role);
// //         admin.setSchool(school);

// //         return superAdminRepository.save(admin);
// //     }

// //     public List<SuperAdmin> getAll() {
// //         return superAdminRepository.findAll();
// //     }

// //     public void delete(Long id) {
// //         superAdminRepository.deleteById(id);
// //     }

// //     public SuperAdmin toggleStatus(Long id) {
// //         SuperAdmin admin = superAdminRepository.findById(id)
// //                 .orElseThrow();

// //         admin.setStatus(
// //                 admin.getStatus().equals("Active") ? "Inactive" : "Active"
// //         );

// //         return superAdminRepository.save(admin);
// //     }
// // }


// @Service
// public class SuperAdminService {

//     @Autowired
//     private UserRepository userRepository;

//     @Autowired
//     private SuperAdminRepository superAdminRepository;

//     @Autowired
//     private SchoolRepository schoolRepository;

//     @Autowired
//     private PasswordEncoder passwordEncoder;

//     @Autowired
//     private OtpService otpService;


//     public SuperAdmin createSuperAdmin(
//             String name,
//             String email,
//             String password,
//             String phone,
//             String role,
//             Long schoolId
//     ) {

//         // 1. School check
//         School school = schoolRepository.findById(schoolId)
//                 .orElseThrow(() ->
//                         new RuntimeException("School not found"));


//         // 2. Phone OTP verification
//         if (!otpService.isPhoneVerified(phone)) {

//             throw new RuntimeException(
//                     "Phone number is not verified"
//             );
//         }


//         // 3. Email OTP verification
//         if (!otpService.isEmailVerified(email)) {

//             throw new RuntimeException(
//                     "Email is not verified"
//             );
//         }


//         // 4. Duplicate email
//         if (userRepository.existsByEmail(email)) {

//             throw new RuntimeException(
//                     "Email already exists"
//             );
//         }


//         // 5. Duplicate phone
//         if (userRepository.existsByPhone(phone)) {

//             throw new RuntimeException(
//                     "Phone number already exists"
//             );
//         }


//         // 6. Create User
//         User user = new User();

//         user.setName(name);
//         user.setEmail(email);
//         user.setPhone(phone);

//         // BCrypt
//         user.setPassword(
//                 passwordEncoder.encode(password)
//         );

//         user.setRole("Superadmin");
//         user.setStatus("Active");
//         user.setSchool(school);

//         // Verification
//         user.setPhoneVerified(true);
//         user.setEmailVerified(true);


//         // 7. Generate username
//         String username = generateUsername(
//                 name,
//                 school.getSchoolCode(),
//                 phone
//         );

//         user.setUsername(username);


//         // 8. Save User
//         User savedUser = userRepository.save(user);


//         // 9. Create SuperAdmin profile
//         SuperAdmin admin = new SuperAdmin();

//         admin.setFullName(name);
//         admin.setEmail(email);
//         admin.setPhone(phone);
//         admin.setRole("Superadmin");
//         admin.setSchool(school);
//         admin.setStatus("Active");


//         // 10. Save SuperAdmin
//         SuperAdmin savedAdmin =
//                 superAdminRepository.save(admin);


//         return savedAdmin;
//     }


//     private String generateUsername(
//             String name,
//             String schoolCode,
//             String phone
//     ) {

//         String firstName = name
//                 .trim()
//                 .split("\\s+")[0];

//         String code = schoolCode
//                 .trim()
//                 .toUpperCase();

//         String cleanPhone =
//                 phone.replaceAll("\\D", "");

//         if (cleanPhone.length() < 5) {
//             throw new RuntimeException(
//                     "Invalid phone number"
//             );
//         }

//         // Tumhare requirement ke according
//         // first 5 digits
//         String mobilePrefix =
//                 cleanPhone.substring(0, 5);


//         String username =
//                 firstName + "_" +
//                 code + "@" +
//                 mobilePrefix;


//         if (!userRepository.existsByUsername(username)) {
//             return username;
//         }


//         int counter = 1;

//         String newUsername;

//         do {

//             newUsername =
//                     firstName + "_" +
//                     code + "@" +
//                     mobilePrefix +
//                     counter;

//             counter++;

//         } while (
//                 userRepository.existsByUsername(newUsername)
//         );


//         return newUsername;
//     }


//     public List<SuperAdmin> getAll() {
//         return superAdminRepository.findAll();
//     }


//     public void delete(Long id) {
//         superAdminRepository.deleteById(id);
//     }


//     public SuperAdmin toggleStatus(Long id) {

//         SuperAdmin admin =
//                 superAdminRepository.findById(id)
//                         .orElseThrow();

//         admin.setStatus(
//                 admin.getStatus().equals("Active")
//                         ? "Inactive"
//                         : "Active"
//         );

//         return superAdminRepository.save(admin);
//     }
// }


package com.schoolmanagement.schoolmanagementwebsite.service;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import com.schoolmanagement.schoolmanagementwebsite.audit.annotation.Audit;
import com.schoolmanagement.schoolmanagementwebsite.audit.enums.AuditAction;
import com.schoolmanagement.schoolmanagementwebsite.dto.SuperAdminCreateDTO;
import com.schoolmanagement.schoolmanagementwebsite.entity.School;
import com.schoolmanagement.schoolmanagementwebsite.entity.SuperAdmin;
import com.schoolmanagement.schoolmanagementwebsite.entity.User;
import com.schoolmanagement.schoolmanagementwebsite.entity.UserGroup;
import com.schoolmanagement.schoolmanagementwebsite.repository.SchoolRepository;
import com.schoolmanagement.schoolmanagementwebsite.repository.SuperAdminRepository;
import com.schoolmanagement.schoolmanagementwebsite.repository.UserGroupRepository;
import com.schoolmanagement.schoolmanagementwebsite.repository.UserRepository;

@Service
public class SuperAdminService {

    @Autowired
    private UserRepository userRepository;

    @Autowired
    private SuperAdminRepository superAdminRepository;

    @Autowired
    private SchoolRepository schoolRepository;

    @Autowired
    private UserGroupRepository userGroupRepository;

    @Autowired
    private PasswordEncoder passwordEncoder;

    @Audit(
        module = "SUPERADMIN",
        action = AuditAction.CREATE,
        description = "CREATE SUPERADMIN",
        targetType = "SUPERADMIN"
    )
    @Transactional
    public SuperAdmin createSuperAdmin(
            SuperAdminCreateDTO dto,
            Long schoolId
    ) {

        // =========================
        // 1. VALIDATION
        // =========================

        if (dto.getPassword() == null ||
            dto.getPassword().length() < 8) {

            throw new RuntimeException(
                    "Password must be at least 8 characters"
            );
        }

        if (!dto.getPassword().equals(dto.getConfirmPassword())) {

            throw new RuntimeException(
                    "Password and confirm password do not match"
            );
        }


        // =========================
        // 2. SCHOOL
        // =========================

        School school = schoolRepository.findById(schoolId)
                .orElseThrow(() ->
                        new RuntimeException("School not found")
                );


        // =========================
        // 3. DUPLICATE EMAIL
        // =========================

        if (userRepository.existsByEmail(dto.getEmail())) {

            throw new RuntimeException(
                    "Email already exists"
            );
        }


        // =========================
        // 4. DUPLICATE PHONE
        // =========================

        if (userRepository.existsByPhone(dto.getPhone())) {

            throw new RuntimeException(
                    "Phone number already exists"
            );
        }


        // =========================
        // 5. USER GROUP
        // =========================

        UserGroup userGroup = null;

        if (dto.getUserGroupId() != null) {

            userGroup = userGroupRepository
                    .findById(dto.getUserGroupId())
                    .orElseThrow(() ->
                            new RuntimeException(
                                    "User group not found"
                            )
                    );
        }


        // =========================
        // 6. CREATE USER
        // =========================

        User user = new User();

        user.setName(dto.getFullName());

        user.setEmail(dto.getEmail());

        user.setPhone(dto.getPhone());

        user.setPassword(
                passwordEncoder.encode(
                        dto.getPassword()
                )
        );

        user.setRole("Superadmin");

        user.setStatus(
                dto.isAccountStatus()
                        ? "Active"
                        : "Inactive"
        );

        user.setSchool(school);

        user.setUserGroup(userGroup);


        // =========================
        // 7. USERNAME GENERATE
        // =========================

        String username = generateUsername(
                dto.getFullName(),
                school.getSchoolCode(),
                dto.getPhone()
        );

        user.setUsername(username);


        userRepository.save(user);


        // =========================
        // 8. CREATE SUPER ADMIN
        // =========================

        SuperAdmin admin = new SuperAdmin();

        admin.setFullName(dto.getFullName());

        admin.setEmail(dto.getEmail());

        admin.setPhone(dto.getPhone());

        admin.setAlternatePhone(
                dto.getAlternatePhone()
        );

        admin.setDateOfBirth(
                dto.getDateOfBirth()
        );

        admin.setGender(
                dto.getGender()
        );

        admin.setSecurityQuestion(
                dto.getSecurityQuestion()
        );

        admin.setSecurityAnswer(
                dto.getSecurityAnswer()
        );

        admin.setAddress(
                dto.getAddress()
        );

        admin.setLanguagePreference(
                dto.getLanguagePreference()
        );

        admin.setTimeZone(
                dto.getTimeZone()
        );

        admin.setNote(
                dto.getNote()
        );

        admin.setRole(
                "Superadmin"
        );

        admin.setStatus(
                dto.isAccountStatus()
                        ? "Active"
                        : "Inactive"
        );

        admin.setTwoFactorAuthentication(
                dto.isTwoFactorAuthentication()
        );

        admin.setLoginNotification(
                dto.isLoginNotification()
        );

        admin.setProfilePicture(
                dto.getProfilePicture()
        );

        admin.setSchool(school);

        admin.setUserGroup(userGroup);


        // =========================
        // 9. SAVE PROFILE
        // =========================

        return superAdminRepository.save(admin);
    }


    // =====================================================
    // USERNAME GENERATOR
    // =====================================================

    private String generateUsername(
            String name,
            String schoolCode,
            String phone
    ) {

        String firstName = name
                .trim()
                .split("\\s+")[0];

        String code = schoolCode
                .trim()
                .toUpperCase();

        String mobilePrefix = phone
                .trim()
                .substring(0, 5);

        String username =
                firstName + "_" +
                code + "@" +
                mobilePrefix;


        if (!userRepository.existsByUsername(username)) {
            return username;
        }


        int counter = 1;

        String newUsername;

        do {

            newUsername =
                    firstName + "_" +
                    code + "@" +
                    mobilePrefix +
                    counter;

            counter++;

        } while (
                userRepository.existsByUsername(
                        newUsername
                )
        );

        return newUsername;
    }


    // =====================================================
    // GET ALL
    // =====================================================

    public List<SuperAdmin> getAll() {

        return superAdminRepository.findAll();
    }


    // =====================================================
    // DELETE
    // =====================================================

    @Transactional
    public void delete(Long id) {

        SuperAdmin admin =
                superAdminRepository.findById(id)
                        .orElseThrow(() ->
                                new RuntimeException(
                                        "Super Admin not found"
                                )
                        );

        String email = admin.getEmail();

        superAdminRepository.delete(admin);

        // Login user bhi delete karna ho to
        userRepository.deleteByEmail(email);
    }


    // =====================================================
    // TOGGLE
    // =====================================================

    @Transactional
    public SuperAdmin toggleStatus(Long id) {

        SuperAdmin admin =
                superAdminRepository.findById(id)
                        .orElseThrow(() ->
                                new RuntimeException(
                                        "Super Admin not found"
                                )
                        );

        String newStatus =
                admin.getStatus().equals("Active")
                        ? "Inactive"
                        : "Active";

        admin.setStatus(newStatus);


        // User login status bhi change karo
        userRepository.findByEmail(admin.getEmail())
                .ifPresent(user -> {
                    user.setStatus(newStatus);
                    userRepository.save(user);
                });


        return superAdminRepository.save(admin);
    }
}