// package com.schoolmanagement.schoolmanagementwebsite.controller;
// import java.util.List;
// import org.springframework.beans.factory.annotation.Autowired;
// import org.springframework.http.ResponseEntity;
// import org.springframework.web.bind.annotation.CrossOrigin;
// import org.springframework.web.bind.annotation.DeleteMapping;
// import org.springframework.web.bind.annotation.GetMapping;
// import org.springframework.web.bind.annotation.PathVariable;
// import org.springframework.web.bind.annotation.PostMapping;
// import org.springframework.web.bind.annotation.PutMapping;
// import org.springframework.web.bind.annotation.RequestBody;
// import org.springframework.web.bind.annotation.RequestMapping;
// import org.springframework.web.bind.annotation.RestController;
// import com.schoolmanagement.schoolmanagementwebsite.entity.School;
// import com.schoolmanagement.schoolmanagementwebsite.service.SchoolService;
// @RestController
// @RequestMapping("/api/school")
// @CrossOrigin(origins = "http://localhost:5173")
// public class SchoolController {
//     @Autowired
//     private SchoolService service;
//     @PostMapping("/add")
//     public School add(@RequestBody School school) {
//         return service.addSchool(school);
//     }
//     @GetMapping("/all")
//     public List<School> all() {
//         return service.getAll();
//     }
//     // ✅ GET SCHOOL BY ID
//     @GetMapping("/{id}")
//     public ResponseEntity<School> getById(@PathVariable Long id) {
//         School school = service.getSchoolById(id);
//         return ResponseEntity.ok(school);
//     }
//     @DeleteMapping("/delete/{id}")
// public ResponseEntity<String> delete(@PathVariable Long id) {
//     service.deleteSchool(id);
//     return ResponseEntity.ok("School deactivated successfully");
// }
//     @PutMapping("/toggle/{id}")
//     public School toggle(@PathVariable Long id) {
//         return service.toggleStatus(id);
//     }
// }
package com.schoolmanagement.schoolmanagementwebsite.controller;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import com.schoolmanagement.schoolmanagementwebsite.entity.School;
import com.schoolmanagement.schoolmanagementwebsite.service.SchoolService;

@RestController
@RequestMapping("/api/school")
@CrossOrigin(origins = "http://localhost:5173")
public class SchoolController {

    @Autowired
    private SchoolService service;

// Create School
    @PostMapping("/add")
    public ResponseEntity<School> addSchool(@RequestBody School school) {
        return ResponseEntity.ok(service.addSchool(school));
    }

// Get All Schools
    @GetMapping("/all")
    public ResponseEntity<List<School>> getAllSchools() {
        return ResponseEntity.ok(service.getAll());
    }

// Get School By Id
    @GetMapping("/{id}")
    public ResponseEntity<School> getSchoolById(@PathVariable Long id) {
        return ResponseEntity.ok(service.getSchoolById(id));
    }

// Update School
    @PutMapping("/update/{id}")
    public ResponseEntity<School> updateSchool(
            @PathVariable Long id,
            @RequestBody School school) {

        return ResponseEntity.ok(service.updateSchool(id, school));
    }

// Activate / Deactivate School
    @PutMapping("/toggle/{id}")
    public ResponseEntity<School> toggleSchoolStatus(@PathVariable Long id) {
        return ResponseEntity.ok(service.toggleStatus(id));
    }

// Delete School
    @DeleteMapping("/delete/{id}")
    public ResponseEntity<String> deleteSchool(@PathVariable Long id) {
        service.deleteSchool(id);
        return ResponseEntity.ok("School deleted successfully");
    }

}
