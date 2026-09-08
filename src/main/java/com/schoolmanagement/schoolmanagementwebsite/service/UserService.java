// package com.schoolmanagement.schoolmanagementwebsite.service;

// import java.util.List;

// import org.springframework.beans.factory.annotation.Autowired;
// import org.springframework.stereotype.Service;

// import com.schoolmanagement.schoolmanagementwebsite.dto.UserResponseDTO;
// import com.schoolmanagement.schoolmanagementwebsite.entity.School;
// import com.schoolmanagement.schoolmanagementwebsite.entity.User;
// import com.schoolmanagement.schoolmanagementwebsite.repository.SchoolRepository;
// import com.schoolmanagement.schoolmanagementwebsite.repository.UserRepository;

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

//     public void deleteUser(Long id) {
//         userRepo.deleteById(id);
//     }

//     public List<UserResponseDTO> getAllUsers() {

//     List<User> users = userRepo.findAll();

//     return users.stream()
//             .map(user -> {

//                 Long schoolId = null;
//                 String schoolName = null;
//                 String schoolCode = null;

//                 if (user.getSchool() != null) {
//                     schoolId = user.getSchool().getId();
//                     schoolName = user.getSchool().getSchoolName();
//                     schoolCode = user.getSchool().getSchoolCode();
//                 }

//                 Long userGroupId = null;
//                 String userGroupName = null;
//                 String userGroupCode = null;

//                 if (user.getUserGroup() != null) {
//                     userGroupId = user.getUserGroup().getId();
//                     userGroupName = user.getUserGroup().getGroupName();
//                     userGroupCode = user.getUserGroup().getGroupCode();
//                 }

//                 return new UserResponseDTO(
//                         user.getId(),
//                         user.getName(),
//                         user.getEmail(),
//                         user.getRole(),
//                         user.getStatus(),
//                         user.getPhone(),
//                         user.getUsername(),
//                         user.isPhoneVerified(),
//                         user.isEmailVerified(),
//                         schoolId,
//                         schoolName,
//                         schoolCode,
//                         userGroupId,
//                         userGroupName,
//                         userGroupCode
//                 );
//             })
//             .toList();
// }

//     public List<UserResponseDTO> getUsersBySchoolId(
//         Long schoolId
//     ) {

//         return userRepo.findBySchool_Id(
//             schoolId
//     );
//     }

//     public List<User> getActiveUsersBySchoolId(
//         Long schoolId
//     ) {

//     return userRepo
//             .findBySchool_IdAndStatusIgnoreCase(
//                     schoolId,
//                     "Active"
//             );
//     }
    
// // =====================================================
// // GET USER BY USERNAME
// // =====================================================

// public User getUserByUsername(String username) {

//     return userRepo
//             .findByUsername(username)
//             .orElse(null);
// }


// // =====================================================
// // GET USER BY EMAIL
// // =====================================================

// public User getUserByEmail(String email) {

//     return userRepo.findByEmail(email);
// }



// public User updateUserStatus(Long userId, String status) {

//     User user = userRepo.findById(userId)
//             .orElseThrow(() ->
//                     new RuntimeException("User not found with id: " + userId)
//             );

//     user.setStatus(status);

//     return userRepo.save(user);
// }

//     // public User toggleStatus(Long id) {
//     //     User user = userRepo.findById(id)
//     //             .orElseThrow();

//     //     user.setStatus(
//     //         user.getStatus().equals("Active")
//     //             ? "Inactive"
//     //             : "Active"
//     //     );

//     //     return userRepo.save(user);
//     // }
// }

package com.schoolmanagement.schoolmanagementwebsite.service;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.schoolmanagement.schoolmanagementwebsite.dto.UserResponseDTO;
import com.schoolmanagement.schoolmanagementwebsite.entity.School;
import com.schoolmanagement.schoolmanagementwebsite.entity.User;
import com.schoolmanagement.schoolmanagementwebsite.repository.SchoolRepository;
import com.schoolmanagement.schoolmanagementwebsite.repository.UserRepository;

@Service
public class UserService {

    @Autowired
    private UserRepository userRepo;

    @Autowired
    private SchoolRepository schoolRepo;


    // =====================================================
    // CREATE SUPER ADMIN
    // =====================================================

    public User createSuperAdmin(User user, Long schoolId) {

        School school = schoolRepo.findById(schoolId)
                .orElseThrow(() ->
                        new RuntimeException("School not found")
                );

        user.setSchool(school);
        user.setRole("Superadmin");
        user.setStatus("Active");

        return userRepo.save(user);
    }


    // =====================================================
    // GET SUPER ADMINS / ALL USERS
    // =====================================================

    public List<User> getSuperAdmins() {

        return userRepo.findAll();
    }


    // =====================================================
    // DELETE USER
    // =====================================================

    public void deleteUser(Long id) {

        userRepo.deleteById(id);
    }


    // =====================================================
    // GET ALL USERS
    // =====================================================

    public List<UserResponseDTO> getAllUsers() {

        List<User> users = userRepo.findAll();

        return users.stream()
                .map(this::convertToDTO)
                .toList();
    }


    // =====================================================
    // GET USERS BY SCHOOL ID
    // =====================================================

    public List<UserResponseDTO> getUsersBySchoolId(
            Long schoolId
    ) {

        List<User> users =
                userRepo.findBySchool_Id(schoolId);

        return users.stream()
                .map(this::convertToDTO)
                .toList();
    }


    // =====================================================
    // GET ACTIVE USERS BY SCHOOL ID
    // =====================================================

    public List<UserResponseDTO> getActiveUsersBySchoolId(
            Long schoolId
    ) {

        List<User> users =
                userRepo.findBySchool_IdAndStatusIgnoreCase(
                        schoolId,
                        "Active"
                );

        return users.stream()
                .map(this::convertToDTO)
                .toList();
    }


    // =====================================================
    // CONVERT USER ENTITY -> USER RESPONSE DTO
    // =====================================================

    private UserResponseDTO convertToDTO(User user) {

        Long schoolId = null;
        String schoolName = null;
        String schoolCode = null;

        // -------------------------------------------------
        // SCHOOL
        // -------------------------------------------------

        if (user.getSchool() != null) {

            schoolId =
                    user.getSchool().getId();

            schoolName =
                    user.getSchool().getSchoolName();

            schoolCode =
                    user.getSchool().getSchoolCode();
        }


        // -------------------------------------------------
        // USER GROUP
        // -------------------------------------------------

        Long userGroupId = null;
        String userGroupName = null;
        String userGroupCode = null;

        if (user.getUserGroup() != null) {

            userGroupId =
                    user.getUserGroup().getId();

            userGroupName =
                    user.getUserGroup().getGroupName();

            userGroupCode =
                    user.getUserGroup().getGroupCode();
        }


        // -------------------------------------------------
        // RETURN DTO
        // -------------------------------------------------

        return new UserResponseDTO(

                user.getId(),

                user.getName(),

                user.getEmail(),

                user.getRole(),

                user.getStatus(),

                user.getPhone(),

                user.getUsername(),

                user.isPhoneVerified(),

                user.isEmailVerified(),

                schoolId,

                schoolName,

                schoolCode,

                userGroupId,

                userGroupName,

                userGroupCode
        );
    }


    // =====================================================
    // GET USER BY USERNAME
    // =====================================================

    public User getUserByUsername(
            String username
    ) {

        return userRepo
                .findByUsername(username)
                .orElse(null);
    }


    // =====================================================
    // GET USER BY EMAIL
    // =====================================================

    public User getUserByEmail(
            String email
    ) {

        return userRepo.findByEmail(email);
    }


    // =====================================================
    // UPDATE USER STATUS
    // =====================================================

    public User updateUserStatus(
            Long userId,
            String status
    ) {

        User user =
                userRepo.findById(userId)
                        .orElseThrow(() ->
                                new RuntimeException(
                                        "User not found with id: "
                                                + userId
                                )
                        );

        user.setStatus(status);

        return userRepo.save(user);
    }
}