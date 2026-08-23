// package com.schoolmanagement.schoolmanagementwebsite.dto;

// public class LoginResponse {

//     private String token;
//     private String role;
//     private String email;
//     private Long schoolId;

//     // constructor, getters, setters

//     public String getToken() {
//         return token;
//     }

//     public LoginResponse(String token, String role, String email) {
//         this.token = token;
//         this.role = role;
//         this.email = email;
//         // this.schoolId = schoolId;
//     }

//     public void setToken(String token) {
//         this.token = token;
//     }

//     public String getRole() {
//         return role;
//     }

//     public void setRole(String role) {
//         this.role = role;
//     }

//     public String getEmail() {
//         return email;
//     }

//     public void setEmail(String email) {
//         this.email = email;
//     }

//     public Long getSchoolId() {
//         return schoolId;
//     }

//     public void setSchoolId(Long schoolId) {
//         this.schoolId = schoolId;
//     }
// }




package com.schoolmanagement.schoolmanagementwebsite.dto;

public class LoginResponse {

    private String token;
    private UserDTO user;
    private String admissionNumber;

    public LoginResponse(
            String token,
            UserDTO user,
            String admissionNumber
    ) {
        this.token = token;
        this.user = user;
        this.admissionNumber = admissionNumber;
    }

    public String getToken() {
        return token;
    }

    public UserDTO getUser() {
        return user;
    }

    public String getAdmissionNumber() {
        return admissionNumber;
    }
}