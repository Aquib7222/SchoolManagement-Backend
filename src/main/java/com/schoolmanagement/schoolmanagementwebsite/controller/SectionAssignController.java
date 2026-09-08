// package com.schoolmanagement.schoolmanagementwebsite.controller;

// import java.util.List;

// import org.springframework.http.ResponseEntity;
// import org.springframework.web.bind.annotation.GetMapping;
// import org.springframework.web.bind.annotation.PostMapping;
// import org.springframework.web.bind.annotation.RequestBody;
// import org.springframework.web.bind.annotation.RequestMapping;
// import org.springframework.web.bind.annotation.RequestParam;
// import org.springframework.web.bind.annotation.RestController;

// import com.schoolmanagement.schoolmanagementwebsite.dto.AssignSectionRequest;
// import com.schoolmanagement.schoolmanagementwebsite.entity.Student;
// import com.schoolmanagement.schoolmanagementwebsite.repository.StudentRepository;
// import com.schoolmanagement.schoolmanagementwebsite.service.SectionAssignService;

// import lombok.RequiredArgsConstructor;

// @RestController
// @RequestMapping("/api/sections")
// @RequiredArgsConstructor
// public class SectionAssignController {

//     private final SectionAssignService sectionService;
//     private final StudentRepository studentRepo;

//     @GetMapping("/students")
// public ResponseEntity<List<Student>> getStudentsForAssignment(
//         @RequestParam Long schoolId,
//         @RequestParam String academicYear,
//         @RequestParam String studentClass
// ) {

//     return ResponseEntity.ok(
//             sectionService.getStudentsForAssignment(
//                     schoolId,
//                     academicYear,
//                     studentClass
//             )
//     );
// }

//     @PostMapping("/assign")
//     public ResponseEntity<?> assignSection(
//             @RequestParam Long schoolId,
//             @RequestBody AssignSectionRequest request
//     ) {
//         sectionService.assignSection(schoolId, request);
//         return ResponseEntity.ok("Section assigned successfully");
//     }
// }


package com.schoolmanagement.schoolmanagementwebsite.controller;

import java.util.List;

import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import com.schoolmanagement.schoolmanagementwebsite.dto.AssignSectionRequest;
import com.schoolmanagement.schoolmanagementwebsite.entity.Student;
import com.schoolmanagement.schoolmanagementwebsite.service.SectionAssignService;

import lombok.RequiredArgsConstructor;

@RestController
@RequestMapping("/api/sections")
@RequiredArgsConstructor
public class SectionAssignController {

    private final SectionAssignService sectionService;

    // ==========================================
    // GET STUDENTS FOR SECTION ASSIGNMENT
    // ==========================================
    @GetMapping("/students")
    public ResponseEntity<List<Student>> getStudentsForAssignment(
            @RequestParam Long schoolId,
            @RequestParam String academicYear,
            @RequestParam String studentClass
    ) {

        return ResponseEntity.ok(
                sectionService.getStudentsForAssignment(
                        schoolId,
                        academicYear,
                        studentClass
                )
        );
    }

    // ==========================================
    // ASSIGN SECTION TO SELECTED STUDENTS
    // ==========================================
    @PostMapping("/assign")
    public ResponseEntity<?> assignSection(
            @RequestParam Long schoolId,
            @RequestBody AssignSectionRequest request
    ) {

        sectionService.assignSection(
                schoolId,
                request
        );

        return ResponseEntity.ok(
                "Section assigned successfully"
        );
    }
}