// // package com.schoolmanagement.schoolmanagementwebsite.controller;

// // import java.util.List;
// // import java.util.Map;

// // import org.springframework.http.ResponseEntity;
// // import org.springframework.web.bind.annotation.CrossOrigin;
// // import org.springframework.web.bind.annotation.DeleteMapping;
// // import org.springframework.web.bind.annotation.GetMapping;
// // import org.springframework.web.bind.annotation.PatchMapping;
// // import org.springframework.web.bind.annotation.PathVariable;
// // import org.springframework.web.bind.annotation.PostMapping;
// // import org.springframework.web.bind.annotation.PutMapping;
// // import org.springframework.web.bind.annotation.RequestBody;
// // import org.springframework.web.bind.annotation.RequestMapping;
// // import org.springframework.web.bind.annotation.RequestParam;
// // import org.springframework.web.bind.annotation.RestController;

// // import com.schoolmanagement.schoolmanagementwebsite.entity.School;
// // import com.schoolmanagement.schoolmanagementwebsite.entity.Teacher;
// // import com.schoolmanagement.schoolmanagementwebsite.service.TeacherService;

// // import lombok.RequiredArgsConstructor;

// // @RestController
// // @RequestMapping("/api/teachers")
// // @RequiredArgsConstructor
// // @CrossOrigin(origins = "http://localhost:5173")
// // public class TeacherController {

// //     private final TeacherService teacherService;

// //     // ✅ ADD
// //     @PostMapping
// //     public ResponseEntity<Teacher> addTeacher(
// //             @RequestBody Teacher teacher,
// //             @RequestParam Long schoolId) {

// //         School school = new School();
// //         school.setId(schoolId);

// //         return ResponseEntity.ok(
// //                 teacherService.addTeacher(teacher, school)
// //         );
// //     }

// //     @GetMapping("/all")
// //     public ResponseEntity<List<Teacher>> getAllTeachersList() {

// //         List<Teacher> teachers = teacherService.getAllTeachersList();

// //         return ResponseEntity.ok(teachers);
// //     }
// //      @GetMapping("/count")
// //     public ResponseEntity<Long> getTeacherCount() {

// //         return ResponseEntity.ok(
// //             teacherService.getTeacherCount()
// //         );
// //     }
// //     @GetMapping
// //     public ResponseEntity<List<Teacher>> listTeachers(
// //             @RequestParam Long schoolId,
// //             @RequestParam(required = false) String status) {

// //         if (status != null) {
// //             return ResponseEntity.ok(
// //                     teacherService.getTeachersByStatus(schoolId, status)
// //             );
// //         }

// //         return ResponseEntity.ok(
// //                 teacherService.getAllTeachers(schoolId)
// //         );
// //     }

// //     // ✏️ EDIT
// //     @PutMapping("/{employeeId}")
// //     public ResponseEntity<Teacher> updateTeacher(
// //             @PathVariable String employeeId,
// //         @RequestParam Long schoolId,
// //             @RequestBody Teacher teacher) {

// //         return ResponseEntity.ok(
// //                 teacherService.updateTeacher(employeeId,schoolId, teacher)
// //         );
// //     }

// //     // ❌ DELETE
// //     @DeleteMapping("/{id}")
// //     public ResponseEntity<Void> deleteTeacher(@PathVariable Long id) {

// //         teacherService.deleteTeacher(id);
// //         return ResponseEntity.noContent().build();
// //     }

// //     // 🔁 ACTIVE / INACTIVE
// //     @PatchMapping("/{id}/status")
// //     public ResponseEntity<Void> updateStatus(
// //             @PathVariable Long id,
// //             @RequestParam boolean active) {

// //         teacherService.toggleStatus(id, active);
// //         return ResponseEntity.ok().build();
// //     }
   
// //     @GetMapping("/search")
// //     public ResponseEntity<Teacher> searchTeachersByEmployeeId(
// //             @RequestParam String employeeId,
// //         @RequestParam Long schoolId
// //             ) {

// //         return ResponseEntity.ok(
// //                 teacherService.searchTeachersByEmployeeId(employeeId,schoolId)
// //         );
// //     }

// //    @PatchMapping("/field/{employeeId}")
// // public ResponseEntity<Teacher> updateTeacherField(
// //         @PathVariable String employeeId,
// //         @RequestParam Long schoolId,
// //         @RequestBody Map<String, String> updates) {

// //     return ResponseEntity.ok(
// //             teacherService.updateTeacherField(employeeId, schoolId, updates)
// //     );
// // }



// // }


// package com.schoolmanagement.schoolmanagementwebsite.controller;

// import java.util.List;
// import java.util.Map;

// import org.springframework.http.ResponseEntity;
// import org.springframework.web.bind.annotation.CrossOrigin;
// import org.springframework.web.bind.annotation.DeleteMapping;
// import org.springframework.web.bind.annotation.GetMapping;
// import org.springframework.web.bind.annotation.PatchMapping;
// import org.springframework.web.bind.annotation.PathVariable;
// import org.springframework.web.bind.annotation.PostMapping;
// import org.springframework.web.bind.annotation.PutMapping;
// import org.springframework.web.bind.annotation.RequestBody;
// import org.springframework.web.bind.annotation.RequestMapping;
// import org.springframework.web.bind.annotation.RequestParam;
// import org.springframework.web.bind.annotation.RestController;

// import com.schoolmanagement.schoolmanagementwebsite.entity.School;
// import com.schoolmanagement.schoolmanagementwebsite.entity.Teacher;
// import com.schoolmanagement.schoolmanagementwebsite.service.TeacherService;

// import lombok.RequiredArgsConstructor;

// @RestController
// @RequestMapping("/api/teachers")
// @RequiredArgsConstructor
// @CrossOrigin(origins = "http://localhost:5173")
// public class TeacherController {

//     private final TeacherService teacherService;

//     // =========================================================
//     // ADD TEACHER
//     // POST /api/teachers?schoolId=1
//     // =========================================================

//     @PostMapping
//     public ResponseEntity<Teacher> addTeacher(
//             @RequestBody Teacher teacher,
//             @RequestParam Long schoolId) {

//         if (schoolId == null) {
//             return ResponseEntity.badRequest().build();
//         }

//         School school = new School();
//         school.setId(schoolId);

//         Teacher savedTeacher =
//                 teacherService.addTeacher(
//                         teacher,
//                         school
//                 );

//         return ResponseEntity.ok(savedTeacher);
//     }

//     // =========================================================
//     // GET ALL TEACHERS OF SCHOOL
//     // GET /api/teachers/all?schoolId=1
//     // =========================================================

//     @GetMapping("/all")
//     public ResponseEntity<List<Teacher>> getAllTeachersList(
//             @RequestParam Long schoolId) {

//         List<Teacher> teachers =
//                 teacherService.getAllTeachersList(
//                         schoolId
//                 );

//         return ResponseEntity.ok(teachers);
//     }

//     // =========================================================
//     // TEACHER COUNT
//     // GET /api/teachers/count?schoolId=1
//     // =========================================================

//     @GetMapping("/count")
//     public ResponseEntity<Long> getTeacherCount(
//             @RequestParam Long schoolId) {

//         long count =
//                 teacherService.getTeacherCount(
//                         schoolId
//                 );

//         return ResponseEntity.ok(count);
//     }

//     // =========================================================
//     // GET TEACHERS
//     //
//     // GET /api/teachers?schoolId=1
//     //
//     // GET /api/teachers?schoolId=1&status=Working
//     // =========================================================

//     @GetMapping
//     public ResponseEntity<List<Teacher>> listTeachers(
//             @RequestParam Long schoolId,
//             @RequestParam(required = false) String status) {

//         List<Teacher> teachers;

//         if (status != null
//                 && !status.isBlank()) {

//             teachers =
//                     teacherService
//                             .getTeachersByStatus(
//                                     schoolId,
//                                     status
//                             );

//         } else {

//             teachers =
//                     teacherService
//                             .getAllTeachers(
//                                     schoolId
//                             );
//         }

//         return ResponseEntity.ok(teachers);
//     }

//     // =========================================================
//     // GET TEACHER BY EMPLOYEE ID
//     //
//     // GET /api/teachers/search
//     // ?employeeId=EMP1001
//     // &schoolId=1
//     // =========================================================

//     @GetMapping("/search")
//     public ResponseEntity<Teacher> searchTeachersByEmployeeId(
//             @RequestParam String employeeId,
//             @RequestParam Long schoolId) {

//         Teacher teacher =
//                 teacherService
//                         .searchTeachersByEmployeeId(
//                                 employeeId,
//                                 schoolId
//                         );

//         return ResponseEntity.ok(teacher);
//     }

//     // =========================================================
//     // GET TEACHER BY EMPLOYEE ID
//     //
//     // GET /api/teachers/{employeeId}
//     // ?schoolId=1
//     //
//     // NOTE:
//     // This endpoint is optional but useful.
//     // =========================================================

//     @GetMapping("/{employeeId}")
//     public ResponseEntity<Teacher> getTeacherByEmployeeId(
//             @PathVariable String employeeId,
//             @RequestParam Long schoolId) {

//         Teacher teacher =
//                 teacherService
//                         .getTeacherByEmployeeId(
//                                 employeeId,
//                                 schoolId
//                         );

//         return ResponseEntity.ok(teacher);
//     }

//     // =========================================================
//     // UPDATE TEACHER
//     //
//     // PUT /api/teachers/EMP1001?schoolId=1
//     // =========================================================

//     @PutMapping("/{employeeId}")
//     public ResponseEntity<Teacher> updateTeacher(
//             @PathVariable String employeeId,
//             @RequestParam Long schoolId,
//             @RequestBody Teacher teacher) {

//         Teacher updatedTeacher =
//                 teacherService.updateTeacher(
//                         employeeId,
//                         schoolId,
//                         teacher
//                 );

//         return ResponseEntity.ok(
//                 updatedTeacher
//         );
//     }

//     // =========================================================
//     // DELETE TEACHER
//     //
//     // DELETE /api/teachers/15?schoolId=1
//     // =========================================================

//     @DeleteMapping("/{id}")
//     public ResponseEntity<Void> deleteTeacher(
//             @PathVariable Long id,
//             @RequestParam Long schoolId) {

//         teacherService.deleteTeacher(
//                 id,
//                 schoolId
//         );

//         return ResponseEntity
//                 .noContent()
//                 .build();
//     }

//     // =========================================================
//     // ACTIVE / INACTIVE
//     //
//     // PATCH /api/teachers/15/status
//     // ?schoolId=1
//     // &active=true
//     // =========================================================

//     @PatchMapping("/{id}/status")
//     public ResponseEntity<Void> updateStatus(
//             @PathVariable Long id,
//             @RequestParam Long schoolId,
//             @RequestParam boolean active) {

//         teacherService.toggleStatus(
//                 id,
//                 schoolId,
//                 active
//         );

//         return ResponseEntity
//                 .ok()
//                 .build();
//     }

//     // =========================================================
//     // PATCH SPECIFIC FIELDS
//     //
//     // PATCH /api/teachers/field/EMP1001?schoolId=1
//     // =========================================================

//     @PatchMapping("/field/{employeeId}")
//     public ResponseEntity<Teacher> updateTeacherField(
//             @PathVariable String employeeId,
//             @RequestParam Long schoolId,
//             @RequestBody Map<String, String> updates) {

//         Teacher teacher =
//                 teacherService.updateTeacherField(
//                         employeeId,
//                         schoolId,
//                         updates
//                 );

//         return ResponseEntity.ok(teacher);
//     }
// }

package com.schoolmanagement.schoolmanagementwebsite.controller;

import java.util.List;
import java.util.Map;

import org.springframework.http.CacheControl;
import org.springframework.http.HttpHeaders;
import org.springframework.http.MediaType;
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

import com.schoolmanagement.schoolmanagementwebsite.dto.TeacherResponse;
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


    // =========================================================
    // ADD TEACHER
    // =========================================================

    @PostMapping
    public ResponseEntity<TeacherResponse> addTeacher(
            @RequestBody Teacher teacher,
            @RequestParam Long schoolId) {

        if (schoolId == null) {

            return ResponseEntity
                    .badRequest()
                    .build();
        }


        School school = new School();

        school.setId(schoolId);


        Teacher savedTeacher =
                teacherService.addTeacher(
                        teacher,
                        school
                );


        return ResponseEntity.ok(
                toResponse(
                        savedTeacher,
                        schoolId
                )
        );
    }


    // =========================================================
    // GET TEACHERS
    // =========================================================

    @GetMapping
    public ResponseEntity<List<TeacherResponse>> listTeachers(
            @RequestParam Long schoolId,
            @RequestParam(required = false) String status) {


        List<Teacher> teachers;


        if (status != null
                && !status.isBlank()) {

            teachers =
                    teacherService.getTeachersByStatus(
                            schoolId,
                            status
                    );

        } else {

            teachers =
                    teacherService.getAllTeachers(
                            schoolId
                    );
        }


        List<TeacherResponse> response =
                teachers.stream()
                        .map(teacher ->
                                toResponse(
                                        teacher,
                                        schoolId
                                )
                        )
                        .toList();


        return ResponseEntity.ok(response);
    }


    // =========================================================
    // GET ALL
    // =========================================================

    @GetMapping("/all")
    public ResponseEntity<List<TeacherResponse>>
            getAllTeachersList(
                    @RequestParam Long schoolId) {


        List<Teacher> teachers =
                teacherService.getAllTeachersList(
                        schoolId
                );


        List<TeacherResponse> response =
                teachers.stream()
                        .map(teacher ->
                                toResponse(
                                        teacher,
                                        schoolId
                                )
                        )
                        .toList();


        return ResponseEntity.ok(response);
    }


    // =========================================================
    // COUNT
    // =========================================================

    @GetMapping("/count")
    public ResponseEntity<Long> getTeacherCount(
            @RequestParam Long schoolId) {

        return ResponseEntity.ok(
                teacherService.getTeacherCount(
                        schoolId
                )
        );
    }


    // =========================================================
    // SEARCH
    // =========================================================

    @GetMapping("/search")
    public ResponseEntity<List<TeacherResponse>>
            searchTeachers(
                    @RequestParam String keyword,
                    @RequestParam Long schoolId) {


        List<Teacher> teachers =
                teacherService.searchTeachers(
                        keyword,
                        schoolId
                );


        List<TeacherResponse> response =
                teachers.stream()
                        .map(teacher ->
                                toResponse(
                                        teacher,
                                        schoolId
                                )
                        )
                        .toList();


        return ResponseEntity.ok(response);
    }


    // =========================================================
    // SEARCH BY EMPLOYEE ID
    // =========================================================

    @GetMapping("/search/employee")
    public ResponseEntity<TeacherResponse>
            searchTeacherByEmployeeId(
                    @RequestParam String employeeId,
                    @RequestParam Long schoolId) {


        Teacher teacher =
                teacherService
                        .searchTeachersByEmployeeId(
                                employeeId,
                                schoolId
                        );


        return ResponseEntity.ok(
                toResponse(
                        teacher,
                        schoolId
                )
        );
    }


    // =========================================================
    // GET BY ID
    // =========================================================

    @GetMapping("/id/{id}")
    public ResponseEntity<TeacherResponse>
            getTeacherById(
                    @PathVariable Long id,
                    @RequestParam Long schoolId) {


        Teacher teacher =
                teacherService.getTeacherById(
                        id,
                        schoolId
                );


        return ResponseEntity.ok(
                toResponse(
                        teacher,
                        schoolId
                )
        );
    }


    // =========================================================
    // GET BY EMPLOYEE ID
    // =========================================================

    @GetMapping("/employee/{employeeId}")
    public ResponseEntity<TeacherResponse>
            getTeacherByEmployeeId(
                    @PathVariable String employeeId,
                    @RequestParam Long schoolId) {


        Teacher teacher =
                teacherService.getTeacherByEmployeeId(
                        employeeId,
                        schoolId
                );


        return ResponseEntity.ok(
                toResponse(
                        teacher,
                        schoolId
                )
        );
    }


    // =========================================================
    // UPDATE
    // =========================================================

    @PutMapping("/{employeeId}")
    public ResponseEntity<TeacherResponse>
            updateTeacher(
                    @PathVariable String employeeId,
                    @RequestParam Long schoolId,
                    @RequestBody Teacher teacher) {


        Teacher updatedTeacher =
                teacherService.updateTeacher(
                        employeeId,
                        schoolId,
                        teacher
                );


        return ResponseEntity.ok(
                toResponse(
                        updatedTeacher,
                        schoolId
                )
        );
    }


    // =========================================================
    // DELETE
    // =========================================================

    @DeleteMapping("/{id}")
    public ResponseEntity<Void> deleteTeacher(
            @PathVariable Long id,
            @RequestParam Long schoolId) {


        teacherService.deleteTeacher(
                id,
                schoolId
        );


        return ResponseEntity
                .noContent()
                .build();
    }


    // =========================================================
    // ACTIVE / INACTIVE
    // =========================================================

    @PatchMapping("/{id}/status")
    public ResponseEntity<Void> updateStatus(
            @PathVariable Long id,
            @RequestParam Long schoolId,
            @RequestParam boolean active) {


        teacherService.toggleStatus(
                id,
                schoolId,
                active
        );


        return ResponseEntity.ok().build();
    }


    // =========================================================
    // UPDATE FIELD
    // =========================================================

    @PatchMapping("/field/{employeeId}")
    public ResponseEntity<TeacherResponse>
            updateTeacherField(
                    @PathVariable String employeeId,
                    @RequestParam Long schoolId,
                    @RequestBody Map<String, String> updates) {


        Teacher teacher =
                teacherService.updateTeacherField(
                        employeeId,
                        schoolId,
                        updates
                );


        return ResponseEntity.ok(
                toResponse(
                        teacher,
                        schoolId
                )
        );
    }


    // =========================================================
    // TEACHER PHOTO API
    // =========================================================
    //
    // GET:
    //
    // /api/teachers/{id}/photo?schoolId=1
    //
    // Returns actual image bytes.
    //
    // =========================================================

    @GetMapping("/{id}/photo")
    public ResponseEntity<byte[]> getTeacherPhoto(
            @PathVariable Long id,
            @RequestParam Long schoolId) {


        byte[] photo =
                teacherService.getTeacherPhoto(
                        id,
                        schoolId
                );


        String contentType =
                teacherService.getTeacherPhotoContentType(
                        id,
                        schoolId
                );


        return ResponseEntity.ok()
                .contentType(
                        MediaType.parseMediaType(
                                contentType
                        )
                )
                .cacheControl(
                        CacheControl
                                .maxAge(
                                        1,
                                        java.util.concurrent.TimeUnit.HOURS
                                )
                                .cachePublic()
                )
                .header(
                        HttpHeaders.CONTENT_DISPOSITION,
                        "inline"
                )
                .body(photo);
    }


    // =========================================================
    // ENTITY -> RESPONSE DTO
    // =========================================================

    private TeacherResponse toResponse(
            Teacher t,
            Long schoolId) {


        String photoUrl = null;


        if (t.getPhoto() != null
                && !t.getPhoto().isBlank()) {

            photoUrl =
                    "/api/teachers/"
                            + t.getId()
                            + "/photo?schoolId="
                            + schoolId;
        }


        return new TeacherResponse(

                t.getId(),

                t.getEmployeeId(),

                t.getFirstName(),
                t.getMiddleName(),
                t.getLastName(),

                t.getDob(),
                t.getFatherName(),

                t.getDoj() != null
                        ? t.getDoj().toString()
                        : null,

                t.getStatus(),
                t.getGender(),
                t.getCategory(),

                t.getNationality(),
                t.getBloodGroup(),

                t.getDepartment(),
                t.getDesignation(),
                t.getTeachingLevel(),
                t.getEmployeeType(),

                t.getPhoneNumber(),
                t.getAlternatePhoneNumber(),
                t.getMobileNumber(),

                t.getEmergencyContact(),
                t.getEmergencyRelation(),

                t.getEmail(),

                t.getAddressLine1(),
                t.getAddressLine2(),
                t.getAddressLine3(),
                t.getCity(),
                t.getState(),
                t.getPincode(),

                t.getPanNumber(),
                t.getBiometricCard(),
                t.getEsiNumber(),
                t.getAadharNumber(),
                t.getPfNumber(),

                t.getMaritalStatus(),
                t.getSpouseName(),
                t.getSpouseGender(),
                t.getSpouseDob(),

                t.getReligion(),
                t.getCaste(),

                t.getQualifiation(),
                t.getUniversityBoard(),
                t.getPassingYear(),
                t.getPercentage(),

                t.getCompanyName(),
                t.getCompanyDesignation(),
                t.getStartDate(),
                t.getEndDate(),
                t.getTotalExperience(),

                t.isActive(),

                photoUrl
        );
    }
}