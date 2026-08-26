// package com.schoolmanagement.schoolmanagementwebsite.controller;

// import java.util.List;

// import org.springframework.beans.factory.annotation.Autowired;
// import org.springframework.http.MediaType;
// import org.springframework.http.ResponseEntity;
// import org.springframework.web.bind.annotation.*;
// import org.springframework.web.multipart.MultipartFile;

// import com.schoolmanagement.schoolmanagementwebsite.dto.ModuleDto;
// import com.schoolmanagement.schoolmanagementwebsite.service.ModuleService;
// import com.schoolmanagement.schoolmanagementwebsite.entity.Module;

// @RestController
// @RequestMapping("/api/module")
// @CrossOrigin(origins = "http://localhost:5173")
// public class ModuleController {

//     @Autowired
//     private ModuleService moduleService;

//     @PostMapping(
//             value = "/create",
//             consumes = MediaType.MULTIPART_FORM_DATA_VALUE)
//     public ResponseEntity<?> createModule(
//             @RequestPart("module") ModuleDto module,
//             @RequestPart(value = "image", required = false) MultipartFile image) throws Exception {

//         return ResponseEntity.ok(
          
//                 moduleService.createModule(module, image));
//     }
//     @GetMapping("/all")
// public ResponseEntity<List<Module>> getAllModules() {

//     return ResponseEntity.ok(moduleService.getAllModules());

// }

// }


package com.schoolmanagement.schoolmanagementwebsite.controller;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;

import com.schoolmanagement.schoolmanagementwebsite.dto.ModuleDto;
import com.schoolmanagement.schoolmanagementwebsite.entity.Module;
import com.schoolmanagement.schoolmanagementwebsite.service.ModuleService;

@RestController
@RequestMapping("/api/module")
@CrossOrigin
public class ModuleController {

    @Autowired
    private ModuleService moduleService;

    // =====================================================
    // CREATE
    // =====================================================

    @PostMapping(
            value = "/create",
            consumes = "multipart/form-data"
    )
    public ResponseEntity<?> createModule(

            @RequestPart("module")
            ModuleDto dto,

            @RequestPart(
                    value = "image",
                    required = false
            )
            MultipartFile image

    ) {

        try {

            String result =
                    moduleService.createModule(
                            dto,
                            image
                    );

            return ResponseEntity.ok(result);

        } catch (Exception e) {

            e.printStackTrace();

            return ResponseEntity
                    .internalServerError()
                    .body(e.getMessage());
        }
    }

    // =====================================================
    // GET ALL
    // =====================================================

    @GetMapping("/all")
    public ResponseEntity<List<Module>>
    getAllModules() {

        return ResponseEntity.ok(
                moduleService.getAllModules()
        );
    }

    // =====================================================
    // GET BY ID
    // =====================================================

    @GetMapping("/{id}")
    public ResponseEntity<?> getModuleById(
            @PathVariable Long id
    ) {

        try {

            Module module =
                    moduleService.getModuleById(id);

            return ResponseEntity.ok(module);

        } catch (Exception e) {

            return ResponseEntity
                    .status(404)
                    .body(e.getMessage());
        }
    }

    // =====================================================
    // UPDATE
    // =====================================================

    @PutMapping(
            value = "/update/{id}",
            consumes = "multipart/form-data"
    )
    public ResponseEntity<?> updateModule(

            @PathVariable Long id,

            @RequestPart("module")
            ModuleDto dto,

            @RequestPart(
                    value = "image",
                    required = false
            )
            MultipartFile image

    ) {

        try {

            String result =
                    moduleService.updateModule(
                            id,
                            dto,
                            image
                    );

            return ResponseEntity.ok(result);

        } catch (Exception e) {

            e.printStackTrace();

            return ResponseEntity
                    .internalServerError()
                    .body(e.getMessage());
        }
    }

    // =====================================================
    // DELETE
    // =====================================================

    @DeleteMapping("/delete/{id}")
    public ResponseEntity<?> deleteModule(
            @PathVariable Long id
    ) {

        try {

            String result =
                    moduleService.deleteModule(id);

            return ResponseEntity.ok(result);

        } catch (Exception e) {

            e.printStackTrace();

            return ResponseEntity
                    .internalServerError()
                    .body(e.getMessage());
        }
    }
}