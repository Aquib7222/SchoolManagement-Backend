package com.schoolmanagement.schoolmanagementwebsite.controller;

import java.util.List;

import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import com.schoolmanagement.schoolmanagementwebsite.entity.School;
import com.schoolmanagement.schoolmanagementwebsite.entity.Teacher;
import com.schoolmanagement.schoolmanagementwebsite.service.TeacherService;

import lombok.RequiredArgsConstructor;

@RestController
@RequestMapping("/api/teachers")
@RequiredArgsConstructor
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
    @PutMapping("/{id}")
    public ResponseEntity<Teacher> updateTeacher(
            @PathVariable Long id,
            @RequestBody Teacher teacher) {

        return ResponseEntity.ok(
                teacherService.updateTeacher(id, teacher)
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
    // 🔍 SEARCH BY EMPLOYEE ID
@GetMapping("/search")
public ResponseEntity<Teacher> getTeacherByEmployeeId(
        @RequestParam String employeeId,
        @RequestParam Long schoolId) {

    return ResponseEntity.ok(
            teacherService.getTeacherByEmployeeId(employeeId, schoolId)
    );
}

}
