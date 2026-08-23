// package com.schoolmanagement.schoolmanagementwebsite.controller;

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
// private StudentRepository studentRepository;


//     @PostMapping("/login")
//     public ResponseEntity<LoginResponse> login(@RequestBody LoginRequest request) {
//         try {
//             authManager.authenticate(
//                     new UsernamePasswordAuthenticationToken(
//                             request.getEmail(),
//                             request.getPassword()
//                     )
//             );

//             UserDetails userDetails
//                     = userDetailsService.loadUserByUsername(request.getEmail());

//             String token = jwtService.generateToken(userDetails);

//             User dbUser = userRepository.findByEmail(request.getEmail());

//             // 🔹 Map School
//             School school = dbUser.getSchool();

//             SchoolDTO schoolDTO = new SchoolDTO();
//             schoolDTO.setId(school.getId());
//             schoolDTO.setSchoolName(school.getSchoolName());
//             schoolDTO.setSchoolCode(school.getSchoolCode());
//             schoolDTO.setEmail(school.getEmail());
//             schoolDTO.setMobileNo(school.getMobileNo());
//             schoolDTO.setAddress(school.getAddress());
//             schoolDTO.setStatus(school.getStatus());

//             // 🔹 Map User
//             UserDTO userDTO = new UserDTO();
//             userDTO.setId(dbUser.getId());
//             userDTO.setName(dbUser.getName());
//             userDTO.setEmail(dbUser.getEmail());
//             userDTO.setRole(dbUser.getRole());
//             userDTO.setPhone(dbUser.getPhone());
//             userDTO.setStatus(dbUser.getStatus());
//             userDTO.setSchoolId(school.getId());
//             userDTO.setSchool(schoolDTO);
//             userDTO.setUserGroupId(
//                     dbUser.getUserGroup().getId()
//             );

//             String admissionNumber = null;

// if ("STUDENT".equalsIgnoreCase(dbUser.getRole().toString())) {

//     Student student = studentRepository
//             .findByUserId(dbUser.getId())
//             .orElseThrow(() ->
//                     new RuntimeException("Student record not found")
//             );

//     admissionNumber = student.getAdmissionNumber();
// }

// return ResponseEntity.ok(
//         new LoginResponse(
//                 token,
//                 userDTO,
//                 admissionNumber
//         )
// );

//         } catch (Exception e) {
//             e.printStackTrace();
//             return ResponseEntity.status(HttpStatus.UNAUTHORIZED).build();
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


    @PostMapping("/login")
    public ResponseEntity<LoginResponse> login(
            @RequestBody LoginRequest request) {

        try {

            // =====================================================
            // AUTHENTICATE USER
            // =====================================================

            authManager.authenticate(
                    new UsernamePasswordAuthenticationToken(
                            request.getEmail(),
                            request.getPassword()
                    )
            );


            // =====================================================
            // LOAD USER DETAILS
            // =====================================================

            UserDetails userDetails =
                    userDetailsService.loadUserByUsername(
                            request.getEmail()
                    );

            String token =
                    jwtService.generateToken(userDetails);


            // =====================================================
            // GET USER FROM DATABASE
            // =====================================================

            User dbUser =
                    userRepository.findByEmail(request.getEmail());


            // =====================================================
            // SCHOOL
            // =====================================================

            School school = dbUser.getSchool();

            SchoolDTO schoolDTO = new SchoolDTO();

            schoolDTO.setId(school.getId());
            schoolDTO.setSchoolName(school.getSchoolName());
            schoolDTO.setSchoolCode(school.getSchoolCode());
            schoolDTO.setEmail(school.getEmail());
            schoolDTO.setMobileNo(school.getMobileNo());
            schoolDTO.setAddress(school.getAddress());
            schoolDTO.setStatus(school.getStatus());


            // =====================================================
            // USER DTO
            // =====================================================

            UserDTO userDTO = new UserDTO();

            userDTO.setId(dbUser.getId());
            userDTO.setName(dbUser.getName());
            userDTO.setEmail(dbUser.getEmail());
            userDTO.setRole(dbUser.getRole());
            userDTO.setPhone(dbUser.getPhone());
            userDTO.setStatus(dbUser.getStatus());
            userDTO.setSchoolId(school.getId());
            userDTO.setSchool(schoolDTO);

            if (dbUser.getUserGroup() != null) {

                userDTO.setUserGroupId(
                        dbUser.getUserGroup().getId()
                );
            }


            // =====================================================
            // STUDENT ADMISSION NUMBER
            // =====================================================
String admissionNumber = null;

if ("STUDENT".equalsIgnoreCase(dbUser.getRole())) {

    System.out.println("===== STUDENT LOGIN =====");
    System.out.println("User ID    : " + dbUser.getId());
    System.out.println("User Email : " + dbUser.getEmail());
    System.out.println("School ID  : " + school.getId());

    Optional<Student> student =
            studentRepository.findBySchool_IdAndEmail(
                    school.getId(),
                    dbUser.getEmail()
            );

    System.out.println("Student Found : " + student.isPresent());

    if (student.isPresent()) {

        admissionNumber =
                student.get().getAdmissionNumber();

        System.out.println(
                "Admission Number : " + admissionNumber
        );
    }
}

userDTO.setAdmissionNumber(admissionNumber);


            // =====================================================
            // LOGIN RESPONSE
            // =====================================================

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