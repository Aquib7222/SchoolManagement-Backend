package com.schoolmanagement.schoolmanagementwebsite.controller;

import java.util.List;
import java.util.Map;

import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PatchMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import com.schoolmanagement.schoolmanagementwebsite.entity.School;
import com.schoolmanagement.schoolmanagementwebsite.entity.Teacher;
import com.schoolmanagement.schoolmanagementwebsite.service.TeacherService;

import lombok.RequiredArgsConstructor;

@RestController
@RequestMapping("/api/teachers")
@RequiredArgsConstructor
@CrossOrigin(origins = "http://localhost:5173")
public class TeacherController {

    private final TeacherService teacherService;

    // ✅ ADD
    @PostMapping
    public ResponseEntity<Teacher> addTeacher(
            @RequestBody Teacher teacher,
            @RequestParam Long schoolId) {

        School school = new School();
        school.setId(schoolId);

        return ResponseEntity.ok(
                teacherService.addTeacher(teacher, school)
        );
    }

    // 📄 LIST
    // @GetMapping
    // public ResponseEntity<List<Teacher>> listTeachers(
    //         @RequestParam Long schoolId) {
    //     return ResponseEntity.ok(
    //             teacherService.getAllTeachers(schoolId)
    //     );
    // }
    @GetMapping
    public ResponseEntity<List<Teacher>> listTeachers(
            @RequestParam Long schoolId,
            @RequestParam(required = false) String status) {

        if (status != null) {
            return ResponseEntity.ok(
                    teacherService.getTeachersByStatus(schoolId, status)
            );
        }

        return ResponseEntity.ok(
                teacherService.getAllTeachers(schoolId)
        );
    }

    // ✏️ EDIT
    @PutMapping("/{employeeId}")
    public ResponseEntity<Teacher> updateTeacher(
            @PathVariable String employeeId,
        @RequestParam Long schoolId,
            @RequestBody Teacher teacher) {

        return ResponseEntity.ok(
                teacherService.updateTeacher(employeeId,schoolId, teacher)
        );
    }

    // ❌ DELETE
    @DeleteMapping("/{id}")
    public ResponseEntity<Void> deleteTeacher(@PathVariable Long id) {

        teacherService.deleteTeacher(id);
        return ResponseEntity.noContent().build();
    }

    // 🔁 ACTIVE / INACTIVE
    @PatchMapping("/{id}/status")
    public ResponseEntity<Void> updateStatus(
            @PathVariable Long id,
            @RequestParam boolean active) {

        teacherService.toggleStatus(id, active);
        return ResponseEntity.ok().build();
    }
   
    @GetMapping("/search")
    public ResponseEntity<Teacher> searchTeachersByEmployeeId(
            @RequestParam String employeeId,
        @RequestParam Long schoolId
            ) {

        return ResponseEntity.ok(
                teacherService.searchTeachersByEmployeeId(employeeId,schoolId)
        );
    }

   @PatchMapping("/field/{employeeId}")
public ResponseEntity<Teacher> updateTeacherField(
        @PathVariable String employeeId,
        @RequestParam Long schoolId,
        @RequestBody Map<String, String> updates) {

    return ResponseEntity.ok(
            teacherService.updateTeacherField(employeeId, schoolId, updates)
    );
}



}
