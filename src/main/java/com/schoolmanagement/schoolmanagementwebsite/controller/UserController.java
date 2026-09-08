
// package com.schoolmanagement.schoolmanagementwebsite.controller;

// import com.schoolmanagement.schoolmanagementwebsite.dto.UserResponseDTO;
// import com.schoolmanagement.schoolmanagementwebsite.entity.User;
// import com.schoolmanagement.schoolmanagementwebsite.service.UserService;

// import org.springframework.beans.factory.annotation.Autowired;
// import org.springframework.http.HttpStatus;
// import org.springframework.http.ResponseEntity;
// import org.springframework.web.bind.annotation.*;

// import java.util.List;
// import java.util.Map;

// @RestController
// @RequestMapping("/api/user")
// @CrossOrigin(origins = "http://localhost:5173")
// public class UserController {

//     @Autowired
//     private UserService userService;


//     // =====================================================
//     // GET ALL USERS
//     // =====================================================

//    @GetMapping("/all")
// public ResponseEntity<List<UserResponseDTO>> getAllUsers() {

//     return ResponseEntity.ok(
//             userService.getAllUsers()
//     );
// }


//     // =====================================================
//     // GET USERS BY SCHOOL ID
//     // =====================================================

//     @GetMapping("/school/{schoolId}")
//     public ResponseEntity<UserResponseDTO> getUsersBySchoolId(
//             @PathVariable Long schoolId
//     ) {

//         try {

//             List<User> users =
//                     userService.getUsersBySchoolId(
//                             schoolId
//                     );

//             return ResponseEntity.ok(users);

//         } catch (Exception e) {

//             e.printStackTrace();

//             return ResponseEntity
//                     .status(HttpStatus.INTERNAL_SERVER_ERROR)
//                     .body(
//                             "Failed to fetch users for school"
//                     );
//         }
//     }


//     // =====================================================
//     // GET ACTIVE USERS BY SCHOOL ID
//     // =====================================================

//     @GetMapping("/school/{schoolId}/active")
//     public ResponseEntity<?> getActiveUsersBySchoolId(
//             @PathVariable Long schoolId
//     ) {

//         try {

//             List<User> users =
//                     userService.getActiveUsersBySchoolId(
//                             schoolId
//                     );

//             return ResponseEntity.ok(users);

//         } catch (Exception e) {

//             e.printStackTrace();

//             return ResponseEntity
//                     .status(HttpStatus.INTERNAL_SERVER_ERROR)
//                     .body(
//                             "Failed to fetch active users for school"
//                     );
//         }
//     }

//     @GetMapping("/username/{username}")
//     public ResponseEntity<?> getUserByUsername(
//             @PathVariable String username
//     ) {

//         try {

//             User user =
//                     userService.getUserByUsername(
//                             username
//                     );

//             if (user == null) {

//                 return ResponseEntity
//                         .status(HttpStatus.NOT_FOUND)
//                         .body(
//                                 "User not found"
//                         );
//             }

//             return ResponseEntity.ok(user);

//         } catch (Exception e) {

//             e.printStackTrace();

//             return ResponseEntity
//                     .status(HttpStatus.INTERNAL_SERVER_ERROR)
//                     .body(
//                             "Failed to fetch user"
//                     );
//         }
//     }

//     @GetMapping("/email")
//     public ResponseEntity<?> getUserByEmail(
//             @RequestParam String email
//     ) {

//         try {

//             User user =
//                     userService.getUserByEmail(
//                             email
//                     );

//             if (user == null) {

//                 return ResponseEntity
//                         .status(HttpStatus.NOT_FOUND)
//                         .body(
//                                 "User not found"
//                         );
//             }

//             return ResponseEntity.ok(user);

//         } catch (Exception e) {

//             e.printStackTrace();

//             return ResponseEntity
//                     .status(HttpStatus.INTERNAL_SERVER_ERROR)
//                     .body(
//                             "Failed to fetch user"
//                     );
//         }
//     }

//     // =====================================================
// // UPDATE USER STATUS
// // =====================================================

// @PutMapping("/{userId}/status")
// public ResponseEntity<?> updateUserStatus(
//         @PathVariable Long userId,
//         @RequestBody Map<String, String> request
// ) {

//     try {

//         String status = request.get("status");

//         if (status == null || status.isBlank()) {

//             return ResponseEntity
//                     .badRequest()
//                     .body("Status is required");
//         }

//         status = status.trim().toUpperCase();

//         if (!status.equals("ACTIVE") &&
//             !status.equals("INACTIVE")) {

//             return ResponseEntity
//                     .badRequest()
//                     .body("Status must be ACTIVE or INACTIVE");
//         }

//         User updatedUser =
//                 userService.updateUserStatus(
//                         userId,
//                         status
//                 );

//         return ResponseEntity.ok(updatedUser);

//     } catch (RuntimeException e) {

//         return ResponseEntity
//                 .status(HttpStatus.NOT_FOUND)
//                 .body(e.getMessage());

//     } catch (Exception e) {

//         e.printStackTrace();

//         return ResponseEntity
//                 .status(HttpStatus.INTERNAL_SERVER_ERROR)
//                 .body("Failed to update user status");
//     }
// }
// }



package com.schoolmanagement.schoolmanagementwebsite.controller;

import com.schoolmanagement.schoolmanagementwebsite.dto.UserResponseDTO;
import com.schoolmanagement.schoolmanagementwebsite.entity.User;
import com.schoolmanagement.schoolmanagementwebsite.service.UserService;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import java.util.Map;

@RestController
@RequestMapping("/api/user")
@CrossOrigin(origins = "http://localhost:5173")
public class UserController {

    @Autowired
    private UserService userService;


    // =====================================================
    // GET ALL USERS
    // =====================================================

    @GetMapping("/all")
    public ResponseEntity<List<UserResponseDTO>> getAllUsers() {

        return ResponseEntity.ok(
                userService.getAllUsers()
        );
    }


    // =====================================================
    // GET USERS BY SCHOOL ID
    // =====================================================

    @GetMapping("/school/{schoolId}")
    public ResponseEntity<List<UserResponseDTO>> getUsersBySchoolId(
            @PathVariable Long schoolId
    ) {

        try {

            List<UserResponseDTO> users =
                    userService.getUsersBySchoolId(
                            schoolId
                    );

            return ResponseEntity.ok(users);

        } catch (Exception e) {

            e.printStackTrace();

            return ResponseEntity
                    .status(HttpStatus.INTERNAL_SERVER_ERROR)
                    .build();
        }
    }


    // =====================================================
    // GET ACTIVE USERS BY SCHOOL ID
    // =====================================================

    @GetMapping("/school/{schoolId}/active")
    public ResponseEntity<List<UserResponseDTO>> getActiveUsersBySchoolId(
            @PathVariable Long schoolId
    ) {

        try {

            List<UserResponseDTO> users =
                    userService.getActiveUsersBySchoolId(
                            schoolId
                    );

            return ResponseEntity.ok(users);

        } catch (Exception e) {

            e.printStackTrace();

            return ResponseEntity
                    .status(HttpStatus.INTERNAL_SERVER_ERROR)
                    .build();
        }
    }


    // =====================================================
    // GET USER BY USERNAME
    // =====================================================

    @GetMapping("/username/{username}")
    public ResponseEntity<?> getUserByUsername(
            @PathVariable String username
    ) {

        try {

            User user =
                    userService.getUserByUsername(
                            username
                    );

            if (user == null) {

                return ResponseEntity
                        .status(HttpStatus.NOT_FOUND)
                        .body("User not found");
            }

            return ResponseEntity.ok(user);

        } catch (Exception e) {

            e.printStackTrace();

            return ResponseEntity
                    .status(HttpStatus.INTERNAL_SERVER_ERROR)
                    .body("Failed to fetch user");
        }
    }


    // =====================================================
    // GET USER BY EMAIL
    // =====================================================

    @GetMapping("/email")
    public ResponseEntity<?> getUserByEmail(
            @RequestParam String email
    ) {

        try {

            User user =
                    userService.getUserByEmail(
                            email
                    );

            if (user == null) {

                return ResponseEntity
                        .status(HttpStatus.NOT_FOUND)
                        .body("User not found");
            }

            return ResponseEntity.ok(user);

        } catch (Exception e) {

            e.printStackTrace();

            return ResponseEntity
                    .status(HttpStatus.INTERNAL_SERVER_ERROR)
                    .body("Failed to fetch user");
        }
    }


    // =====================================================
    // UPDATE USER STATUS
    // =====================================================

    @PutMapping("/{userId}/status")
    public ResponseEntity<?> updateUserStatus(
            @PathVariable Long userId,
            @RequestBody Map<String, String> request
    ) {

        try {

            String status = request.get("status");

            if (status == null || status.isBlank()) {

                return ResponseEntity
                        .badRequest()
                        .body("Status is required");
            }

            status =
                    status.trim().toUpperCase();

            if (!status.equals("ACTIVE") &&
                !status.equals("INACTIVE")) {

                return ResponseEntity
                        .badRequest()
                        .body(
                                "Status must be ACTIVE or INACTIVE"
                        );
            }

            User updatedUser =
                    userService.updateUserStatus(
                            userId,
                            status
                    );

            return ResponseEntity.ok(
                    updatedUser
            );

        } catch (RuntimeException e) {

            return ResponseEntity
                    .status(HttpStatus.NOT_FOUND)
                    .body(e.getMessage());

        } catch (Exception e) {

            e.printStackTrace();

            return ResponseEntity
                    .status(
                            HttpStatus.INTERNAL_SERVER_ERROR
                    )
                    .body(
                            "Failed to update user status"
                    );
        }
    }
}