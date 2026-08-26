package com.schoolmanagement.schoolmanagementwebsite.service;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.schoolmanagement.schoolmanagementwebsite.entity.School;
import com.schoolmanagement.schoolmanagementwebsite.entity.User;
import com.schoolmanagement.schoolmanagementwebsite.repository.SchoolRepository;
import com.schoolmanagement.schoolmanagementwebsite.repository.UserRepository;


// @Service
// public class UserService {

//     @Autowired
//     private UserRepository userRepo;

//     @Autowired
//     private SchoolRepository schoolRepo;

//     public User createSuperAdmin(User user, Long schoolId) {
//         School school = schoolRepo.findById(schoolId)
//                 .orElseThrow(() -> new RuntimeException("School not found"));
        
//         user.setSchool(school);
//         user.setRole("Superadmin");
//         user.setStatus("Active");
//         return userRepo.save(user);
//     }

//     public List<User> getSuperAdmins() {
//         return userRepo.findAll();
//     }

//     public void deleteSuperAdmin(Long id) {
//         userRepo.deleteById(id);
//     }

//     public User toggleStatus(Long id) {
//         User u = userRepo.findById(id).orElseThrow();
//         u.setStatus(u.getStatus().equals("Active") ? "Inactive" : "Active");
//         return userRepo.save(u);
//     }
// }


// @Service
// public class UserService {

//     @Autowired
//     private UserRepository userRepo;

//     @Autowired
//     private SchoolRepository schoolRepo;

//     @Autowired
//     private OtpService otpService;

//     public User createSuperAdmin(User user, Long schoolId) {

//         // 1. School check
//         School school = schoolRepo.findById(schoolId)
//                 .orElseThrow(() ->
//                         new RuntimeException("School not found"));

//         // 2. Phone verification
//         if (!otpService.isPhoneVerified(user.getPhone())) {
//             throw new RuntimeException(
//                     "Phone number is not verified"
//             );
//         }

//         // 3. Email verification
//         if (!otpService.isEmailVerified(user.getEmail())) {
//             throw new RuntimeException(
//                     "Email is not verified"
//             );
//         }

//         // 4. Duplicate email
//         if (userRepo.existsByEmail(user.getEmail())) {
//             throw new RuntimeException(
//                     "Email already exists"
//             );
//         }

//         // 5. Duplicate phone
//         if (userRepo.existsByPhone(user.getPhone())) {
//             throw new RuntimeException(
//                     "Phone number already exists"
//             );
//         }

//         // 6. School set
//         user.setSchool(school);

//         // 7. Role
//         user.setRole("Superadmin");

//         // 8. Status
//         user.setStatus("Active");

//         // 9. Verification status
//         user.setPhoneVerified(true);
//         user.setEmailVerified(true);

//         // 10. Username generate
//         String username = generateUsername(
//                 user.getName(),
//                 school.getSchoolCode(),
//                 user.getPhone()
//         );

//         user.setUsername(username);

//         return userRepo.save(user);
//     }

//     private String generateUsername(
//             String name,
//             String schoolCode,
//             String phone) {

//         String firstName = name
//                 .trim()
//                 .split("\\s+")[0];

//         String code = schoolCode
//                 .trim()
//                 .toUpperCase();

//         String mobilePrefix = phone
//                 .trim()
//                 .substring(0, 5);

//         String username =
//                 firstName + "_" +
//                 code + "@" +
//                 mobilePrefix;

//         /*
//          * Same username aa gaya to
//          * unique username generate karenge.
//          */

//         if (!userRepo.existsByUsername(username)) {
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

//         } while (userRepo.existsByUsername(newUsername));

//         return newUsername;
//     }

//     // baaki existing methods...

//         public List<User> getSuperAdmins() {
//         return userRepo.findAll();
//     }

//     public void deleteSuperAdmin(Long id) {
//         userRepo.deleteById(id);
//     }

//     public User toggleStatus(Long id) {
//         User u = userRepo.findById(id).orElseThrow();
//         u.setStatus(u.getStatus().equals("Active") ? "Inactive" : "Active");
//         return userRepo.save(u);
//     }
// }

@Service
public class UserService {

    @Autowired
    private UserRepository userRepository;

    public List<User> getUsers() {
        return userRepository.findAll();
    }

    public void deleteUser(Long id) {
        userRepository.deleteById(id);
    }

    public User toggleStatus(Long id) {
        User user = userRepository.findById(id)
                .orElseThrow();

        user.setStatus(
            user.getStatus().equals("Active")
                ? "Inactive"
                : "Active"
        );

        return userRepository.save(user);
    }
}