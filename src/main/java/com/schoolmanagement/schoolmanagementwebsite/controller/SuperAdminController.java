// // // package com.schoolmanagement.schoolmanagementwebsite.controller;

// // // import com.schoolmanagement.schoolmanagementwebsite.entity.User;
// // // import com.schoolmanagement.schoolmanagementwebsite.service.UserService;

// // // import org.springframework.beans.factory.annotation.Autowired;
// // // import org.springframework.web.bind.annotation.*;

// // // import java.util.*;

// // // import com.schoolmanagement.schoolmanagementwebsite.entity.SuperAdmin;
// // // import com.schoolmanagement.schoolmanagementwebsite.service.SuperAdminService;

// // // // @RestController
// // // // @RequestMapping("/api/superadmin")
// // // // @CrossOrigin
// // // // public class SuperAdminController {

// // // //     @Autowired
// // // //     private UserService service;

// // // //     @PostMapping("/create/{schoolId}")
// // // //     public User create(@RequestBody User user, @PathVariable Long schoolId) {
// // // //         return service.createSuperAdmin(user, schoolId);
// // // //     }

// // // //     @GetMapping("/all")
// // // //     public List<User> all() {
// // // //         return service.getSuperAdmins();
// // // //     }

// // // //     @DeleteMapping("/delete/{id}")
// // // //     public void delete(@PathVariable Long id) {
// // // //         service.deleteSuperAdmin(id);
// // // //     }

// // // //     @PutMapping("/toggle/{id}")
// // // //     public User toggle(@PathVariable Long id) {
// // // //         return service.toggleStatus(id);
// // // //     }
// // // // }


// // // @RestController
// // // @RequestMapping("/api/superadmin")
// // // @CrossOrigin(origins = "http://localhost:5173")
// // // public class SuperAdminController {

// // //     @Autowired
// // //     private SuperAdminService service;

// // //     @PostMapping("/create/{schoolId}")
// // //     public SuperAdmin create(
// // //             @RequestBody Map<String, String> body,
// // //             @PathVariable Long schoolId
// // //     ) {
// // //         return service.createSuperAdmin(
// // //                 body.get("name"),
// // //                 body.get("email"),
// // //                 body.get("password"),
// // //                 body.get("phone"),
// // //                 body.get("role"),
// // //                 schoolId
// // //         );
// // //     }

// // //     @GetMapping("/all")
// // //     public List<SuperAdmin> all() {
// // //         return service.getAll();
// // //     }

// // //     @DeleteMapping("/delete/{id}")
// // //     public void delete(@PathVariable Long id) {
// // //         service.delete(id);
// // //     }

// // //     @PutMapping("/toggle/{id}")
// // //     public SuperAdmin toggle(@PathVariable Long id) {
// // //         return service.toggleStatus(id);
// // //     }
// // // }


// // package com.schoolmanagement.schoolmanagementwebsite.controller;

// // import java.util.List;

// // import org.springframework.beans.factory.annotation.Autowired;
// // import org.springframework.http.ResponseEntity;
// // import org.springframework.web.bind.annotation.*;

// // import com.schoolmanagement.schoolmanagementwebsite.dto.SuperAdminRequest;
// // import com.schoolmanagement.schoolmanagementwebsite.entity.SuperAdmin;
// // import com.schoolmanagement.schoolmanagementwebsite.service.SuperAdminService;

// // @RestController
// // @RequestMapping("/api/superadmin")
// // @CrossOrigin(origins = "http://localhost:5173")
// // public class SuperAdminController {

// //     @Autowired
// //     private SuperAdminService service;


// //     // =========================
// //     // CREATE SUPER ADMIN
// //     // =========================

// //     @PostMapping("/create/{schoolId}")
// //     public ResponseEntity<SuperAdmin> create(
// //             @RequestBody SuperAdminRequest request,
// //             @PathVariable Long schoolId
// //     ) {

// //         SuperAdmin admin =
// //                 service.createSuperAdmin(
// //                         request,
// //                         schoolId
// //                 );

// //         return ResponseEntity.ok(admin);
// //     }


// //     // =========================
// //     // GET ALL
// //     // =========================

// //     @GetMapping("/all")
// //     public ResponseEntity<List<SuperAdmin>> all() {

// //         return ResponseEntity.ok(
// //                 service.getAll()
// //         );
// //     }


// //     // =========================
// //     // DELETE
// //     // =========================

// //     @DeleteMapping("/delete/{id}")
// //     public ResponseEntity<Void> delete(
// //             @PathVariable Long id
// //     ) {

// //         service.delete(id);

// //         return ResponseEntity.noContent().build();
// //     }


// //     // =========================
// //     // TOGGLE STATUS
// //     // =========================

// //     @PutMapping("/toggle/{id}")
// //     public ResponseEntity<SuperAdmin> toggle(
// //             @PathVariable Long id
// //     ) {

// //         return ResponseEntity.ok(
// //                 service.toggleStatus(id)
// //         );
// //     }
// // }

// package com.schoolmanagement.schoolmanagementwebsite.controller;

// import java.util.List;

// import org.springframework.beans.factory.annotation.Autowired;
// import org.springframework.http.ResponseEntity;
// import org.springframework.web.bind.annotation.*;

// import com.schoolmanagement.schoolmanagementwebsite.dto.SuperAdminCreateDTO;
// import com.schoolmanagement.schoolmanagementwebsite.entity.SuperAdmin;
// import com.schoolmanagement.schoolmanagementwebsite.service.SuperAdminService;

// @RestController
// @RequestMapping("/api/superadmin")
// @CrossOrigin(origins = "http://localhost:5173")
// public class SuperAdminController {

//     @Autowired
//     private SuperAdminService service;


//     @PostMapping("/create/{schoolId}")
//     public ResponseEntity<SuperAdmin> create(
//             @RequestBody SuperAdminCreateDTO dto,
//             @PathVariable Long schoolId
//     ) {

//         SuperAdmin admin =
//                 service.createSuperAdmin(dto, schoolId);

//         return ResponseEntity.ok(admin);
//     }


//     @GetMapping("/all")
//     public ResponseEntity<List<SuperAdmin>> all() {

//         return ResponseEntity.ok(
//                 service.getAll()
//         );
//     }


//     @DeleteMapping("/delete/{id}")
//     public ResponseEntity<Void> delete(
//             @PathVariable Long id
//     ) {

//         service.delete(id);

//         return ResponseEntity.noContent().build();
//     }


//     @PutMapping("/toggle/{id}")
//     public ResponseEntity<SuperAdmin> toggle(
//             @PathVariable Long id
//     ) {

//         return ResponseEntity.ok(
//                 service.toggleStatus(id)
//         );
//     }
// }



package com.schoolmanagement.schoolmanagementwebsite.controller;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import com.schoolmanagement.schoolmanagementwebsite.dto.SuperAdminCreateDTO;
import com.schoolmanagement.schoolmanagementwebsite.entity.SuperAdmin;
import com.schoolmanagement.schoolmanagementwebsite.service.SuperAdminService;

@RestController
@RequestMapping("/api/superadmin")
@CrossOrigin(origins = "http://localhost:5173")
public class SuperAdminController {

    @Autowired
    private SuperAdminService service;

    // =========================================================
    // CREATE SUPER ADMIN
    // POST:
    // /api/superadmin/create?schoolId=1
    // =========================================================

    @PostMapping("/create")
    public ResponseEntity<SuperAdmin> create(
            @RequestBody SuperAdminCreateDTO dto,
            @RequestParam Long schoolId
    ) {

        SuperAdmin admin =
                service.createSuperAdmin(dto, schoolId);

        return ResponseEntity.ok(admin);
    }

    // =========================================================
    // GET ALL SUPER ADMINS
    // =========================================================

    @GetMapping("/all")
    public ResponseEntity<List<SuperAdmin>> all() {

        return ResponseEntity.ok(
                service.getAll()
        );
    }

    // =========================================================
    // DELETE
    // =========================================================

    @DeleteMapping("/delete/{id}")
    public ResponseEntity<Void> delete(
            @PathVariable Long id
    ) {

        service.delete(id);

        return ResponseEntity.noContent().build();
    }

    // =========================================================
    // TOGGLE STATUS
    // =========================================================

    @PutMapping("/toggle/{id}")
    public ResponseEntity<SuperAdmin> toggle(
            @PathVariable Long id
    ) {

        return ResponseEntity.ok(
                service.toggleStatus(id)
        );
    }
}