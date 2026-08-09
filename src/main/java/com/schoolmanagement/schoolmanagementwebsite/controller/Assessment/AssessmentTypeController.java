// // package com.schoolmanagement.schoolmanagementwebsite.controller.Assessment;

// // import java.util.List;

// // import org.springframework.http.HttpStatus;
// // import org.springframework.http.ResponseEntity;
// // import org.springframework.web.bind.annotation.*;

// // import com.schoolmanagement.schoolmanagementwebsite.dto.Assessment.AssessmentTypeResponse;
// // import com.schoolmanagement.schoolmanagementwebsite.entity.Assessment.AssessmentType;
// // import com.schoolmanagement.schoolmanagementwebsite.service.Assessment.AssessmentTypeService;

// // import lombok.RequiredArgsConstructor;

// // @RestController
// // @RequestMapping("/api/assessment/type")
// // @RequiredArgsConstructor
// // @CrossOrigin(origins = "http://localhost:5173")
// // public class AssessmentTypeController {

// //     private final AssessmentTypeService service;


// //     // =====================================================
// //     // CREATE
// //     // =====================================================

// //     @PostMapping
// //     public ResponseEntity<?> save(
// //             @RequestBody AssessmentType assessmentType) {

// //         try {

// //             AssessmentTypeResponse response =
// //                     service.save(assessmentType);

// //             return ResponseEntity
// //                     .status(HttpStatus.CREATED)
// //                     .body(response);

// //         } catch (RuntimeException e) {

// //             return ResponseEntity
// //                     .status(HttpStatus.CONFLICT)
// //                     .body(e.getMessage());
// //         }
// //     }


// //     // =====================================================
// //     // GET ALL
// //     // =====================================================

// //     @GetMapping
// //     public ResponseEntity<?> getAll(
// //             @RequestParam Long schoolId) {

// //         try {

// //             List<AssessmentTypeResponse> response =
// //                     service.getAll(schoolId);

// //             return ResponseEntity.ok(response);

// //         } catch (RuntimeException e) {

// //             return ResponseEntity
// //                     .status(HttpStatus.NOT_FOUND)
// //                     .body(e.getMessage());
// //         }
// //     }


// //     // =====================================================
// //     // GET BY ID
// //     // =====================================================

// //     @GetMapping("/{id}")
// //     public ResponseEntity<?> getById(
// //             @PathVariable Long id,
// //             @RequestParam Long schoolId) {

// //         try {

// //             AssessmentTypeResponse response =
// //                     service.getById(id, schoolId);

// //             return ResponseEntity.ok(response);

// //         } catch (RuntimeException e) {

// //             return ResponseEntity
// //                     .status(HttpStatus.NOT_FOUND)
// //                     .body(e.getMessage());
// //         }
// //     }


// //     // =====================================================
// //     // GET BY CATEGORY
// //     // =====================================================

// //     @GetMapping("/category")
// //     public ResponseEntity<?> getByCategory(
// //             @RequestParam Long schoolId,
// //             @RequestParam Long categoryId) {

// //         try {

// //             List<AssessmentTypeResponse> response =
// //                     service.getByCategory(
// //                             schoolId,
// //                             categoryId
// //                     );

// //             return ResponseEntity.ok(response);

// //         } catch (RuntimeException e) {

// //             return ResponseEntity
// //                     .status(HttpStatus.NOT_FOUND)
// //                     .body(e.getMessage());
// //         }
// //     }


// //     // =====================================================
// //     // GET BY EXAM TERM
// //     // =====================================================

// //     @GetMapping("/exam-term")
// //     public ResponseEntity<?> getByExamTerm(
// //             @RequestParam Long schoolId,
// //             @RequestParam Long examTermId) {

// //         try {

// //             List<AssessmentTypeResponse> response =
// //                     service.getByExamTerm(
// //                             schoolId,
// //                             examTermId
// //                     );

// //             return ResponseEntity.ok(response);

// //         } catch (RuntimeException e) {

// //             return ResponseEntity
// //                     .status(HttpStatus.NOT_FOUND)
// //                     .body(e.getMessage());
// //         }
// //     }


// //     // =====================================================
// //     // UPDATE
// //     // =====================================================

// //     @PutMapping("/{id}")
// //     public ResponseEntity<?> update(
// //             @PathVariable Long id,
// //             @RequestBody AssessmentType assessmentType) {

// //         try {

// //             AssessmentTypeResponse response =
// //                     service.update(
// //                             id,
// //                             assessmentType
// //                     );

// //             return ResponseEntity.ok(response);

// //         } catch (RuntimeException e) {

// //             String message = e.getMessage();

// //             if (message != null &&
// //                     message.toLowerCase()
// //                            .contains("already exists")) {

// //                 return ResponseEntity
// //                         .status(HttpStatus.CONFLICT)
// //                         .body(message);
// //             }

// //             return ResponseEntity
// //                     .status(HttpStatus.NOT_FOUND)
// //                     .body(message);
// //         }
// //     }


// //     // =====================================================
// //     // DELETE
// //     // =====================================================

// //     @DeleteMapping("/{id}")
// //     public ResponseEntity<?> delete(
// //             @PathVariable Long id,
// //             @RequestParam Long schoolId) {

// //         try {

// //             service.delete(
// //                     id,
// //                     schoolId
// //             );

// //             return ResponseEntity.ok(
// //                     "Assessment type deleted successfully."
// //             );

// //         } catch (RuntimeException e) {

// //             return ResponseEntity
// //                     .status(HttpStatus.NOT_FOUND)
// //                     .body(e.getMessage());
// //         }
// //     }
// // }

// package com.schoolmanagement.schoolmanagementwebsite.controller.Assessment;

// import java.util.List;

// import org.springframework.http.HttpStatus;
// import org.springframework.http.ResponseEntity;
// import org.springframework.web.bind.annotation.CrossOrigin;
// import org.springframework.web.bind.annotation.DeleteMapping;
// import org.springframework.web.bind.annotation.GetMapping;
// import org.springframework.web.bind.annotation.PathVariable;
// import org.springframework.web.bind.annotation.PostMapping;
// import org.springframework.web.bind.annotation.PutMapping;
// import org.springframework.web.bind.annotation.RequestBody;
// import org.springframework.web.bind.annotation.RequestMapping;
// import org.springframework.web.bind.annotation.RequestParam;
// import org.springframework.web.bind.annotation.RestController;

// import com.schoolmanagement.schoolmanagementwebsite.dto.Assessment.AssessmentTypeResponse;
// import com.schoolmanagement.schoolmanagementwebsite.entity.Assessment.AssessmentType;
// import com.schoolmanagement.schoolmanagementwebsite.service.Assessment.AssessmentTypeService;

// import lombok.RequiredArgsConstructor;

// @RestController
// @RequestMapping("/api/assessment/type")
// @RequiredArgsConstructor
// @CrossOrigin(origins = "http://localhost:5173")
// public class AssessmentTypeController {

//     private final AssessmentTypeService service;

//     // =====================================================
//     // CREATE
//     // =====================================================

//     @PostMapping
//     public ResponseEntity<?> save(
//             @RequestBody AssessmentType assessmentType) {

//         try {

//             AssessmentTypeResponse response =
//                     service.save(assessmentType);

//             return ResponseEntity
//                     .status(HttpStatus.CREATED)
//                     .body(response);

//         } catch (RuntimeException e) {

//             return ResponseEntity
//                     .status(HttpStatus.CONFLICT)
//                     .body(e.getMessage());
//         }
//     }

//     // =====================================================
//     // GET ALL BY SCHOOL
//     // =====================================================

//     @GetMapping
//     public ResponseEntity<?> getAll(
//             @RequestParam Long schoolId) {

//         try {

//             List<AssessmentTypeResponse> response =
//                     service.getAll(schoolId);

//             return ResponseEntity.ok(response);

//         } catch (RuntimeException e) {

//             return ResponseEntity
//                     .status(HttpStatus.NOT_FOUND)
//                     .body(e.getMessage());
//         }
//     }

//     // =====================================================
//     // GET BY ID
//     // =====================================================

//     @GetMapping("/{id}")
//     public ResponseEntity<?> getById(
//             @PathVariable Long id,
//             @RequestParam Long schoolId) {

//         try {

//             AssessmentTypeResponse response =
//                     service.getById(id, schoolId);

//             return ResponseEntity.ok(response);

//         } catch (RuntimeException e) {

//             return ResponseEntity
//                     .status(HttpStatus.NOT_FOUND)
//                     .body(e.getMessage());
//         }
//     }

//     // =====================================================
//     // GET BY CATEGORY
//     // =====================================================

//     @GetMapping("/category")
//     public ResponseEntity<?> getByCategory(
//             @RequestParam Long schoolId,
//             @RequestParam Long categoryId) {

//         try {

//             List<AssessmentTypeResponse> response =
//                     service.getByCategory(
//                             schoolId,
//                             categoryId
//                     );

//             return ResponseEntity.ok(response);

//         } catch (RuntimeException e) {

//             return ResponseEntity
//                     .status(HttpStatus.NOT_FOUND)
//                     .body(e.getMessage());
//         }
//     }

//     // =====================================================
//     // GET BY EXAM TERM
//     // =====================================================

//     @GetMapping("/exam-term")
//     public ResponseEntity<?> getByExamTerm(
//             @RequestParam Long schoolId,
//             @RequestParam Long examTermId) {

//         try {

//             List<AssessmentTypeResponse> response =
//                     service.getByExamTerm(
//                             schoolId,
//                             examTermId
//                     );

//             return ResponseEntity.ok(response);

//         } catch (RuntimeException e) {

//             return ResponseEntity
//                     .status(HttpStatus.NOT_FOUND)
//                     .body(e.getMessage());
//         }
//     }

//     // =====================================================
//     // UPDATE
//     // =====================================================

//     @PutMapping("/{id}")
//     public ResponseEntity<?> update(
//             @PathVariable Long id,
//             @RequestBody AssessmentType assessmentType) {

//         try {

//             AssessmentTypeResponse response =
//                     service.update(
//                             id,
//                             assessmentType
//                     );

//             return ResponseEntity.ok(response);

//         } catch (RuntimeException e) {

//             String message = e.getMessage();

//             if (message != null &&
//                     message.toLowerCase()
//                            .contains("already exists")) {

//                 return ResponseEntity
//                         .status(HttpStatus.CONFLICT)
//                         .body(message);
//             }

//             return ResponseEntity
//                     .status(HttpStatus.NOT_FOUND)
//                     .body(message);
//         }
//     }

//     // =====================================================
//     // DELETE
//     // =====================================================

//     @DeleteMapping("/{id}")
//     public ResponseEntity<?> delete(
//             @PathVariable Long id,
//             @RequestParam Long schoolId) {

//         try {

//             service.delete(
//                     id,
//                     schoolId
//             );

//             return ResponseEntity.ok(
//                     "Assessment type deleted successfully."
//             );

//         } catch (RuntimeException e) {

//             return ResponseEntity
//                     .status(HttpStatus.NOT_FOUND)
//                     .body(e.getMessage());
//         }
//     }
// }

package com.schoolmanagement.schoolmanagementwebsite.controller.Assessment;

import java.util.List;

import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import com.schoolmanagement.schoolmanagementwebsite.dto.Assessment.AssessmentTypeDTO;
import com.schoolmanagement.schoolmanagementwebsite.dto.Assessment.AssessmentTypeResponse;
import com.schoolmanagement.schoolmanagementwebsite.service.Assessment.AssessmentTypeService;

import lombok.RequiredArgsConstructor;

@RestController
@RequestMapping("/api/assessment/type")
@RequiredArgsConstructor
@CrossOrigin(origins = "http://localhost:5173")
public class AssessmentTypeController {

    private final AssessmentTypeService service;


    // =====================================================
    // CREATE
    // =====================================================

    @PostMapping
    public ResponseEntity<?> save(
            @RequestBody AssessmentTypeDTO dto) {

        try {

            AssessmentTypeResponse response =
                    service.save(dto);

            return ResponseEntity
                    .status(HttpStatus.CREATED)
                    .body(response);

        } catch (RuntimeException e) {

            return ResponseEntity
                    .status(HttpStatus.CONFLICT)
                    .body(e.getMessage());
        }
    }


    // =====================================================
    // GET ALL
    // =====================================================

    @GetMapping
    public ResponseEntity<?> getAll(
            @RequestParam Long schoolId) {

        try {

            List<AssessmentTypeResponse> response =
                    service.getAll(schoolId);

            return ResponseEntity.ok(response);

        } catch (RuntimeException e) {

            return ResponseEntity
                    .status(HttpStatus.NOT_FOUND)
                    .body(e.getMessage());
        }
    }


    // =====================================================
    // GET BY ID
    // =====================================================

    @GetMapping("/{id}")
    public ResponseEntity<?> getById(
            @PathVariable Long id,
            @RequestParam Long schoolId) {

        try {

            AssessmentTypeResponse response =
                    service.getById(id, schoolId);

            return ResponseEntity.ok(response);

        } catch (RuntimeException e) {

            return ResponseEntity
                    .status(HttpStatus.NOT_FOUND)
                    .body(e.getMessage());
        }
    }


    // =====================================================
    // GET BY CATEGORY
    // =====================================================

    @GetMapping("/category")
    public ResponseEntity<?> getByCategory(
            @RequestParam Long schoolId,
            @RequestParam Long categoryId) {

        try {

            List<AssessmentTypeResponse> response =
                    service.getByCategory(
                            schoolId,
                            categoryId
                    );

            return ResponseEntity.ok(response);

        } catch (RuntimeException e) {

            return ResponseEntity
                    .status(HttpStatus.NOT_FOUND)
                    .body(e.getMessage());
        }
    }


    // =====================================================
    // GET BY EXAM TERM
    // =====================================================

    @GetMapping("/exam-term")
    public ResponseEntity<?> getByExamTerm(
            @RequestParam Long schoolId,
            @RequestParam Long examTermId) {

        try {

            List<AssessmentTypeResponse> response =
                    service.getByExamTerm(
                            schoolId,
                            examTermId
                    );

            return ResponseEntity.ok(response);

        } catch (RuntimeException e) {

            return ResponseEntity
                    .status(HttpStatus.NOT_FOUND)
                    .body(e.getMessage());
        }
    }


    // =====================================================
    // UPDATE
    // =====================================================

    @PutMapping("/{id}")
    public ResponseEntity<?> update(
            @PathVariable Long id,
            @RequestBody AssessmentTypeDTO dto) {

        try {

            AssessmentTypeResponse response =
                    service.update(id, dto);

            return ResponseEntity.ok(response);

        } catch (RuntimeException e) {

            String message = e.getMessage();

            if (message != null &&
                    message.toLowerCase()
                            .contains("already exists")) {

                return ResponseEntity
                        .status(HttpStatus.CONFLICT)
                        .body(message);
            }

            return ResponseEntity
                    .status(HttpStatus.NOT_FOUND)
                    .body(message);
        }
    }


    // =====================================================
    // DELETE
    // =====================================================

    @DeleteMapping("/{id}")
    public ResponseEntity<?> delete(
            @PathVariable Long id,
            @RequestParam Long schoolId) {

        try {

            service.delete(id, schoolId);

            return ResponseEntity.ok(
                    "Assessment type deleted successfully."
            );

        } catch (RuntimeException e) {

            return ResponseEntity
                    .status(HttpStatus.NOT_FOUND)
                    .body(e.getMessage());
        }
    }
}