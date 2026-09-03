// // package com.schoolmanagement.schoolmanagementwebsite.controller;

// // import org.springframework.beans.factory.annotation.Autowired;
// // import org.springframework.http.HttpStatus;
// // import org.springframework.http.ResponseEntity;
// // import org.springframework.security.authentication.AuthenticationManager;
// // import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
// // import org.springframework.security.core.userdetails.UserDetails;
// // import org.springframework.web.bind.annotation.CrossOrigin;
// // import org.springframework.web.bind.annotation.PostMapping;
// // import org.springframework.web.bind.annotation.RequestBody;
// // import org.springframework.web.bind.annotation.RequestMapping;
// // import org.springframework.web.bind.annotation.RestController;

// // import com.schoolmanagement.schoolmanagementwebsite.dto.LoginRequest;
// // import com.schoolmanagement.schoolmanagementwebsite.dto.LoginResponse;
// // import com.schoolmanagement.schoolmanagementwebsite.dto.SchoolDTO;
// // import com.schoolmanagement.schoolmanagementwebsite.dto.UserDTO;
// // import com.schoolmanagement.schoolmanagementwebsite.entity.School;
// // import com.schoolmanagement.schoolmanagementwebsite.entity.Student;
// // import com.schoolmanagement.schoolmanagementwebsite.entity.User;
// // import com.schoolmanagement.schoolmanagementwebsite.repository.StudentRepository;
// // import com.schoolmanagement.schoolmanagementwebsite.repository.UserRepository;
// // import com.schoolmanagement.schoolmanagementwebsite.service.JwtService;
// // import com.schoolmanagement.schoolmanagementwebsite.service.UserDetailsServiceImpl;

// // @RestController
// // @RequestMapping("/auth")
// // @CrossOrigin(origins = "http://localhost:5173")
// // public class AuthController {

// //     @Autowired
// //     private AuthenticationManager authManager;

// //     @Autowired
// //     private JwtService jwtService;

// //     @Autowired
// //     private UserDetailsServiceImpl userDetailsService;

// //     @Autowired
// //     private UserRepository userRepository;

// //     @Autowired
// // private StudentRepository studentRepository;


// //     @PostMapping("/login")
// //     public ResponseEntity<LoginResponse> login(@RequestBody LoginRequest request) {
// //         try {
// //             authManager.authenticate(
// //                     new UsernamePasswordAuthenticationToken(
// //                             request.getEmail(),
// //                             request.getPassword()
// //                     )
// //             );

// //             UserDetails userDetails
// //                     = userDetailsService.loadUserByUsername(request.getEmail());

// //             String token = jwtService.generateToken(userDetails);

// //             User dbUser = userRepository.findByEmail(request.getEmail());

// //             // 🔹 Map School
// //             School school = dbUser.getSchool();

// //             SchoolDTO schoolDTO = new SchoolDTO();
// //             schoolDTO.setId(school.getId());
// //             schoolDTO.setSchoolName(school.getSchoolName());
// //             schoolDTO.setSchoolCode(school.getSchoolCode());
// //             schoolDTO.setEmail(school.getEmail());
// //             schoolDTO.setMobileNo(school.getMobileNo());
// //             schoolDTO.setAddress(school.getAddress());
// //             schoolDTO.setStatus(school.getStatus());

// //             // 🔹 Map User
// //             UserDTO userDTO = new UserDTO();
// //             userDTO.setId(dbUser.getId());
// //             userDTO.setName(dbUser.getName());
// //             userDTO.setEmail(dbUser.getEmail());
// //             userDTO.setRole(dbUser.getRole());
// //             userDTO.setPhone(dbUser.getPhone());
// //             userDTO.setStatus(dbUser.getStatus());
// //             userDTO.setSchoolId(school.getId());
// //             userDTO.setSchool(schoolDTO);
// //             userDTO.setUserGroupId(
// //                     dbUser.getUserGroup().getId()
// //             );

// //             String admissionNumber = null;

// // if ("STUDENT".equalsIgnoreCase(dbUser.getRole().toString())) {

// //     Student student = studentRepository
// //             .findByUserId(dbUser.getId())
// //             .orElseThrow(() ->
// //                     new RuntimeException("Student record not found")
// //             );

// //     admissionNumber = student.getAdmissionNumber();
// // }

// // return ResponseEntity.ok(
// //         new LoginResponse(
// //                 token,
// //                 userDTO,
// //                 admissionNumber
// //         )
// // );

// //         } catch (Exception e) {
// //             e.printStackTrace();
// //             return ResponseEntity.status(HttpStatus.UNAUTHORIZED).build();
// //         }
// //     }

// // }


// package com.schoolmanagement.schoolmanagementwebsite.controller;

// import java.util.Optional;

// import org.springframework.beans.factory.annotation.Autowired;
// import org.springframework.http.HttpStatus;
// import org.springframework.http.ResponseEntity;
// import org.springframework.security.authentication.AuthenticationManager;
// import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
// import org.springframework.security.core.userdetails.UserDetails;
// import org.springframework.web.bind.annotation.CrossOrigin;
// import org.springframework.web.bind.annotation.PostMapping;
// import org.springframework.web.bind.annotation.RequestBody;
// import org.springframework.web.bind.annotation.RequestMapping;
// import org.springframework.web.bind.annotation.RestController;

// import com.schoolmanagement.schoolmanagementwebsite.dto.LoginRequest;
// import com.schoolmanagement.schoolmanagementwebsite.dto.LoginResponse;
// import com.schoolmanagement.schoolmanagementwebsite.dto.SchoolDTO;
// import com.schoolmanagement.schoolmanagementwebsite.dto.UserDTO;
// import com.schoolmanagement.schoolmanagementwebsite.entity.School;
// import com.schoolmanagement.schoolmanagementwebsite.entity.Student;
// import com.schoolmanagement.schoolmanagementwebsite.entity.User;
// import com.schoolmanagement.schoolmanagementwebsite.repository.StudentRepository;
// import com.schoolmanagement.schoolmanagementwebsite.repository.UserRepository;
// import com.schoolmanagement.schoolmanagementwebsite.service.JwtService;
// import com.schoolmanagement.schoolmanagementwebsite.service.UserDetailsServiceImpl;

// @RestController
// @RequestMapping("/auth")
// @CrossOrigin(origins = "http://localhost:5173")
// public class AuthController {

//     @Autowired
//     private AuthenticationManager authManager;

//     @Autowired
//     private JwtService jwtService;

//     @Autowired
//     private UserDetailsServiceImpl userDetailsService;

//     @Autowired
//     private UserRepository userRepository;

//     @Autowired
//     private StudentRepository studentRepository;


//     @PostMapping("/login")
//     public ResponseEntity<LoginResponse> login(
//             @RequestBody LoginRequest request) {

//         try {

//             // =====================================================
//             // AUTHENTICATE USER
//             // =====================================================

//             authManager.authenticate(
//                     new UsernamePasswordAuthenticationToken(
//                             request.getEmail(),
//                             request.getPassword()
//                     )
//             );


//             // =====================================================
//             // LOAD USER DETAILS
//             // =====================================================

//             UserDetails userDetails =
//                     userDetailsService.loadUserByUsername(
//                             request.getEmail()
//                     );

//             String token =
//                     jwtService.generateToken(userDetails);


//             // =====================================================
//             // GET USER FROM DATABASE
//             // =====================================================

//             User dbUser =
//                     userRepository.findByEmail(request.getEmail());


//             // =====================================================
//             // SCHOOL
//             // =====================================================

//             School school = dbUser.getSchool();

//            SchoolDTO schoolDTO = new SchoolDTO();

// schoolDTO.setId(school.getId());
// schoolDTO.setSchoolName(school.getSchoolName());
// schoolDTO.setSchoolCode(school.getSchoolCode());
// schoolDTO.setEmail(school.getEmail());

// schoolDTO.setMobileNo(
//     school.getPhoneNumber()
// );

// String fullAddress =
//         school.getAddressLine1();

// if (school.getAddressLine2() != null
//         && !school.getAddressLine2().isBlank()) {

//     fullAddress +=
//             ", " + school.getAddressLine2();
// }

// schoolDTO.setAddress(fullAddress);

// schoolDTO.setStatus(
//     Boolean.TRUE.equals(school.getActive())
//         ? "ACTIVE"
//         : "INACTIVE"
// );

// // =====================================================
// // USER DTO
// // =====================================================

// UserDTO userDTO = new UserDTO();

// userDTO.setId(dbUser.getId());
// userDTO.setName(dbUser.getName());
// userDTO.setEmail(dbUser.getEmail());
// userDTO.setRole(dbUser.getRole());
// userDTO.setPhone(dbUser.getPhone());
// userDTO.setUsername(dbUser.getUsername());
// userDTO.setStatus(dbUser.getStatus());
// userDTO.setSchoolId(school.getId());
// userDTO.setSchool(schoolDTO);

// if (dbUser.getUserGroup() != null) {

//     userDTO.setUserGroupId(
//             dbUser.getUserGroup().getId()
//     );
// }


// // =====================================================
// // ROLE BASED DATA
// // =====================================================

// String admissionNumber = null;

// if ("TEACHER".equalsIgnoreCase(dbUser.getRole())) {

//     // =============================================
//     // TEACHER
//     // =============================================

//     if (dbUser.getTeacher() != null) {

//         Long teacherId =
//                 dbUser.getTeacher().getId();

//         userDTO.setTeacherId(teacherId);

//         System.out.println(
//                 "===== TEACHER LOGIN ====="
//         );

//         System.out.println(
//                 "User ID    : " + dbUser.getId()
//         );

//         System.out.println(
//                 "Teacher ID : " + teacherId
//         );

//         System.out.println(
//                 "School ID  : " + school.getId()
//         );

//     } else {

//         System.out.println(
//                 "WARNING: Teacher user has no teacher mapping"
//         );
//     }

//     // Teacher ke liye admissionNumber nahi
//     userDTO.setAdmissionNumber(null);

// } else if ("STUDENT".equalsIgnoreCase(dbUser.getRole())) {

//     // =============================================
//     // STUDENT
//     // =============================================

//     System.out.println(
//             "===== STUDENT LOGIN ====="
//     );

//     System.out.println(
//             "User ID    : " + dbUser.getId()
//     );

//     System.out.println(
//             "User Email : " + dbUser.getEmail()
//     );

//     System.out.println(
//             "School ID  : " + school.getId()
//     );

//     Optional<Student> student =
//             studentRepository.findBySchool_IdAndEmail(
//                     school.getId(),
//                     dbUser.getEmail()
//             );

//     System.out.println(
//             "Student Found : " + student.isPresent()
//     );

//     if (student.isPresent()) {

//         admissionNumber =
//                 student.get().getAdmissionNumber();

//         userDTO.setAdmissionNumber(
//                 admissionNumber
//         );

//         System.out.println(
//                 "Admission Number : " +
//                 admissionNumber
//         );
//     }

//     // Student ke liye teacherId nahi
//     userDTO.setTeacherId(null);

// } else {

//     // =============================================
//     // ADMIN / OTHER ROLES
//     // =============================================

//     userDTO.setTeacherId(null);
//     userDTO.setAdmissionNumber(null);
// }


//             // =====================================================
//             // LOGIN RESPONSE
//             // =====================================================

//             return ResponseEntity.ok(
//                     new LoginResponse(
//                             token,
//                             userDTO,
//                             admissionNumber
//                     )
//             );

//         } catch (Exception e) {

//             e.printStackTrace();

//             return ResponseEntity
//                     .status(HttpStatus.UNAUTHORIZED)
//                     .build();
//         }
//     }
// }



package com.schoolmanagement.schoolmanagementwebsite.controller;

import java.util.Optional;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.schoolmanagement.schoolmanagementwebsite.dto.LoginRequest;
import com.schoolmanagement.schoolmanagementwebsite.dto.LoginResponse;
import com.schoolmanagement.schoolmanagementwebsite.dto.SchoolDTO;
import com.schoolmanagement.schoolmanagementwebsite.dto.UserDTO;
import com.schoolmanagement.schoolmanagementwebsite.entity.School;
import com.schoolmanagement.schoolmanagementwebsite.entity.Student;
import com.schoolmanagement.schoolmanagementwebsite.entity.User;
import com.schoolmanagement.schoolmanagementwebsite.repository.StudentRepository;
import com.schoolmanagement.schoolmanagementwebsite.repository.UserRepository;
import com.schoolmanagement.schoolmanagementwebsite.service.JwtService;
import com.schoolmanagement.schoolmanagementwebsite.service.UserDetailsServiceImpl;

@RestController
@RequestMapping("/auth")
@CrossOrigin(origins = "http://localhost:5173")
public class AuthController {

    // =====================================================
    // DEPENDENCIES
    // =====================================================

    @Autowired
    private AuthenticationManager authManager;

    @Autowired
    private JwtService jwtService;

    @Autowired
    private UserDetailsServiceImpl userDetailsService;

    @Autowired
    private UserRepository userRepository;

    @Autowired
    private StudentRepository studentRepository;


    // =====================================================
    // LOGIN
    // =====================================================

    @PostMapping("/login")
    public ResponseEntity<LoginResponse> login(
            @RequestBody LoginRequest request) {

        try {

            // =================================================
            // 1. AUTHENTICATE USER
            // =================================================

            authManager.authenticate(
                    new UsernamePasswordAuthenticationToken(
                            request.getEmail(),
                            request.getPassword()
                    )
            );


            // =================================================
            // 2. LOAD USER DETAILS
            // =================================================

            UserDetails userDetails =
                    userDetailsService.loadUserByUsername(
                            request.getEmail()
                    );


            // =================================================
            // 3. GENERATE JWT
            // =================================================

            String token =
                    jwtService.generateToken(
                            userDetails
                    );


            // =================================================
            // 4. GET USER FROM DATABASE
            // =================================================

            User dbUser =
                    userRepository.findByEmail(
                            request.getEmail()
                    );

            if (dbUser == null) {

                return ResponseEntity
                        .status(HttpStatus.UNAUTHORIZED)
                        .build();
            }


            // =================================================
            // 5. SCHOOL
            // =================================================

            School school = dbUser.getSchool();

            if (school == null) {

                throw new RuntimeException(
                        "User is not assigned to any school"
                );
            }


            // =================================================
            // 6. SCHOOL DTO
            // =================================================

            SchoolDTO schoolDTO =
                    new SchoolDTO();

            schoolDTO.setId(
                    school.getId()
            );

            schoolDTO.setSchoolName(
                    school.getSchoolName()
            );

            schoolDTO.setSchoolCode(
                    school.getSchoolCode()
            );

            schoolDTO.setEmail(
                    school.getEmail()
            );

            schoolDTO.setMobileNo(
                    school.getPhoneNumber()
            );


            // =================================================
            // SCHOOL ADDRESS
            // =================================================

            String fullAddress =
                    school.getAddressLine1();

            if (school.getAddressLine2() != null
                    && !school.getAddressLine2().isBlank()) {

                fullAddress +=
                        ", " +
                        school.getAddressLine2();
            }

            schoolDTO.setAddress(
                    fullAddress
            );


            // =================================================
            // SCHOOL STATUS
            // =================================================

            schoolDTO.setStatus(
                    Boolean.TRUE.equals(
                            school.getActive()
                    )
                            ? "ACTIVE"
                            : "INACTIVE"
            );


            // =================================================
            // 7. USER DTO
            // =================================================

            UserDTO userDTO =
                    new UserDTO();

            userDTO.setId(
                    dbUser.getId()
            );

            userDTO.setName(
                    dbUser.getName()
            );

            userDTO.setEmail(
                    dbUser.getEmail()
            );

            userDTO.setRole(
                    dbUser.getRole()
            );

            userDTO.setPhone(
                    dbUser.getPhone()
            );

            userDTO.setUsername(
                    dbUser.getUsername()
            );

            userDTO.setStatus(
                    dbUser.getStatus()
            );

            userDTO.setPhoneVerified(
                    dbUser.isPhoneVerified()
            );

            userDTO.setEmailVerified(
                    dbUser.isEmailVerified()
            );

            userDTO.setSchoolId(
                    school.getId()
            );

            userDTO.setSchool(
                    schoolDTO
            );


            // =================================================
            // 8. USER GROUP
            // =================================================

            if (dbUser.getUserGroup() != null) {

                userDTO.setUserGroupId(
                        dbUser
                                .getUserGroup()
                                .getId()
                );
            }


            // =================================================
            // 9. ROLE BASED DATA
            // =================================================

            String admissionNumber = null;


            // =================================================
            // TEACHER
            // =================================================

            if ("TEACHER".equalsIgnoreCase(
                    dbUser.getRole())) {

                System.out.println(
                        "================================"
                );

                System.out.println(
                        "       TEACHER LOGIN"
                );

                System.out.println(
                        "================================"
                );

                System.out.println(
                        "User ID    : " +
                        dbUser.getId()
                );

                System.out.println(
                        "User Email : " +
                        dbUser.getEmail()
                );

                System.out.println(
                        "School ID  : " +
                        school.getId()
                );


                // ---------------------------------------------
                // TEACHER MAPPING
                // ---------------------------------------------

                if (dbUser.getTeacher() != null) {

                    Long teacherId =
                            dbUser
                                    .getTeacher()
                                    .getId();

                    userDTO.setTeacherId(
                            teacherId
                    );

                    System.out.println(
                            "Teacher ID : " +
                            teacherId
                    );

                } else {

                    System.out.println(
                            "WARNING: Teacher user has no teacher mapping"
                    );

                    userDTO.setTeacherId(
                            null
                    );
                }


                // ---------------------------------------------
                // Teacher ko admission number nahi
                // ---------------------------------------------

                userDTO.setAdmissionNumber(
                        null
                );
            }


            // =================================================
            // STUDENT
            // =================================================

            else if ("STUDENT".equalsIgnoreCase(
                    dbUser.getRole())) {

                System.out.println(
                        "================================"
                );

                System.out.println(
                        "       STUDENT LOGIN"
                );

                System.out.println(
                        "================================"
                );

                System.out.println(
                        "User ID    : " +
                        dbUser.getId()
                );

                System.out.println(
                        "User Email : " +
                        dbUser.getEmail()
                );

                System.out.println(
                        "School ID  : " +
                        school.getId()
                );


                // ---------------------------------------------
                // FIND STUDENT
                // ---------------------------------------------

                Optional<Student> student =
                        studentRepository
                                .findBySchool_IdAndEmail(
                                        school.getId(),
                                        dbUser.getEmail()
                                );


                System.out.println(
                        "Student Found : " +
                        student.isPresent()
                );


                if (student.isPresent()) {

                    admissionNumber =
                            student
                                    .get()
                                    .getAdmissionNumber();

                    userDTO.setAdmissionNumber(
                            admissionNumber
                    );

                    System.out.println(
                            "Admission Number : " +
                            admissionNumber
                    );

                } else {

                    System.out.println(
                            "WARNING: Student record not found"
                    );

                    userDTO.setAdmissionNumber(
                            null
                    );
                }


                // ---------------------------------------------
                // Student ko teacherId nahi
                // ---------------------------------------------

                userDTO.setTeacherId(
                        null
                );
            }


            // =================================================
            // ADMIN / SUPER ADMIN / OTHER
            // =================================================

            else {

                System.out.println(
                        "================================"
                );

                System.out.println(
                        "       ADMIN / OTHER LOGIN"
                );

                System.out.println(
                        "================================"
                );

                System.out.println(
                        "Role : " +
                        dbUser.getRole()
                );


                // ---------------------------------------------
                // Admin ko teacher/student information nahi
                // ---------------------------------------------

                userDTO.setTeacherId(
                        null
                );

                userDTO.setAdmissionNumber(
                        null
                );
            }


            // =================================================
            // 10. LOGIN RESPONSE
            // =================================================

            return ResponseEntity.ok(
                    new LoginResponse(
                            token,
                            userDTO,
                            admissionNumber
                    )
            );


        } catch (Exception e) {

            e.printStackTrace();

            return ResponseEntity
                    .status(HttpStatus.UNAUTHORIZED)
                    .build();
        }
    }
}