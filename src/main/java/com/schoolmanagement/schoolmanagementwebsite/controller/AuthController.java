package com.schoolmanagement.schoolmanagementwebsite.controller;

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
import com.schoolmanagement.schoolmanagementwebsite.entity.User;
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

//     @PostMapping("/login")
// public ResponseEntity<LoginResponse> login(@RequestBody LoginRequest request) {
//     try {
//         authManager.authenticate(
//             new UsernamePasswordAuthenticationToken(request.getEmail(), request.getPassword())
//         );
//         UserDetails userDetails = userDetailsService.loadUserByUsername(request.getEmail());
//         String token = jwtService.generateToken(userDetails);
//         User dbUser = userRepository.findByEmail(request.getEmail());
//         LoginResponse response = new LoginResponse(
//             token,
//             dbUser.getRole(),
//             dbUser.getEmail()
//             // dbUser.getSchoolId()
//         );
//         return ResponseEntity.ok(response);
//     } catch (Exception e) {
//     e.printStackTrace();   // <<< ADD THIS
//     return ResponseEntity.status(HttpStatus.UNAUTHORIZED).build();
// }
// }
    @PostMapping("/login")
    public ResponseEntity<LoginResponse> login(@RequestBody LoginRequest request) {
        try {
            authManager.authenticate(
                    new UsernamePasswordAuthenticationToken(
                            request.getEmail(),
                            request.getPassword()
                    )
            );

            UserDetails userDetails
                    = userDetailsService.loadUserByUsername(request.getEmail());

            String token = jwtService.generateToken(userDetails);

            User dbUser = userRepository.findByEmail(request.getEmail());

            // 🔹 Map School
            School school = dbUser.getSchool();

            SchoolDTO schoolDTO = new SchoolDTO();
            schoolDTO.setId(school.getId());
            schoolDTO.setSchoolName(school.getSchoolName());
            schoolDTO.setSchoolCode(school.getSchoolCode());
            schoolDTO.setEmail(school.getEmail());
            schoolDTO.setMobileNo(school.getMobileNo());
            schoolDTO.setAddress(school.getAddress());
            schoolDTO.setStatus(school.getStatus());

            // 🔹 Map User
            UserDTO userDTO = new UserDTO();
            userDTO.setId(dbUser.getId());
            userDTO.setName(dbUser.getName());
            userDTO.setEmail(dbUser.getEmail());
            userDTO.setRole(dbUser.getRole());
            userDTO.setPhone(dbUser.getPhone());
            userDTO.setStatus(dbUser.getStatus());
            userDTO.setSchoolId(school.getId());
            userDTO.setSchool(schoolDTO);
            userDTO.setUserGroupId(
                    dbUser.getUserGroup().getId()
            );

            return ResponseEntity.ok(new LoginResponse(token, userDTO));

        } catch (Exception e) {
            e.printStackTrace();
            return ResponseEntity.status(HttpStatus.UNAUTHORIZED).build();
        }
    }

}
