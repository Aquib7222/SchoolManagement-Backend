package com.schoolmanagement.schoolmanagementwebsite.controller;

import com.schoolmanagement.schoolmanagementwebsite.entity.User;
import com.schoolmanagement.schoolmanagementwebsite.service.UserService;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import java.util.*;

import com.schoolmanagement.schoolmanagementwebsite.entity.SuperAdmin;
import com.schoolmanagement.schoolmanagementwebsite.service.SuperAdminService;

// @RestController
// @RequestMapping("/api/superadmin")
// @CrossOrigin
// public class SuperAdminController {

//     @Autowired
//     private UserService service;

//     @PostMapping("/create/{schoolId}")
//     public User create(@RequestBody User user, @PathVariable Long schoolId) {
//         return service.createSuperAdmin(user, schoolId);
//     }

//     @GetMapping("/all")
//     public List<User> all() {
//         return service.getSuperAdmins();
//     }

//     @DeleteMapping("/delete/{id}")
//     public void delete(@PathVariable Long id) {
//         service.deleteSuperAdmin(id);
//     }

//     @PutMapping("/toggle/{id}")
//     public User toggle(@PathVariable Long id) {
//         return service.toggleStatus(id);
//     }
// }


@RestController
@RequestMapping("/api/superadmin")
@CrossOrigin(origins = "http://localhost:5173")
public class SuperAdminController {

    @Autowired
    private SuperAdminService service;

    @PostMapping("/create/{schoolId}")
    public SuperAdmin create(
            @RequestBody Map<String, String> body,
            @PathVariable Long schoolId
    ) {
        return service.createSuperAdmin(
                body.get("name"),
                body.get("email"),
                body.get("password"),
                body.get("phone"),
                body.get("role"),
                schoolId
        );
    }

    @GetMapping("/all")
    public List<SuperAdmin> all() {
        return service.getAll();
    }

    @DeleteMapping("/delete/{id}")
    public void delete(@PathVariable Long id) {
        service.delete(id);
    }

    @PutMapping("/toggle/{id}")
    public SuperAdmin toggle(@PathVariable Long id) {
        return service.toggleStatus(id);
    }
}
