// package com.schoolmanagement.schoolmanagementwebsite.controller.Transport;

// import java.util.List;

// import org.springframework.http.HttpStatus;
// import org.springframework.http.ResponseEntity;
// import org.springframework.web.bind.annotation.*;

// import com.schoolmanagement.schoolmanagementwebsite.dto.Transport.StopSearchResponse;
// import com.schoolmanagement.schoolmanagementwebsite.dto.Transport.VehicleRouteMappingRequest;
// import com.schoolmanagement.schoolmanagementwebsite.dto.Transport.VehicleRouteMappingResponse;
// import com.schoolmanagement.schoolmanagementwebsite.service.Transport.VehicleRouteMappingService;

// import lombok.RequiredArgsConstructor;

// @RestController
// @RequestMapping("/api/transport/vehicle-routes")
// @RequiredArgsConstructor
// public class VehicleRouteMappingController {

//     private final VehicleRouteMappingService mappingService;


//     // =====================================================
//     // ASSIGN ROUTE
//     // =====================================================

//     @PostMapping
//     public ResponseEntity<VehicleRouteMappingResponse> assignRoute(
//             @RequestParam Long schoolId,
//             @RequestBody VehicleRouteMappingRequest request) {

//         request.setSchoolId(schoolId);

//         return ResponseEntity
//                 .status(HttpStatus.CREATED)
//                 .body(
//                         mappingService.assignRoute(request)
//                 );
//     }


//     // =====================================================
//     // GET ALL
//     // =====================================================

//     @GetMapping
//     public ResponseEntity<List<VehicleRouteMappingResponse>> getAll(
//             @RequestParam Long schoolId) {

//         return ResponseEntity.ok(
//                 mappingService.getAllMappings(schoolId)
//         );
//     }


//     // =====================================================
//     // GET BY ID
//     // =====================================================

//     @GetMapping("/{id}")
//     public ResponseEntity<VehicleRouteMappingResponse> getById(
//             @PathVariable Long id,
//             @RequestParam Long schoolId) {

//         return ResponseEntity.ok(
//                 mappingService.getById(
//                         schoolId,
//                         id
//                 )
//         );
//     }


//     // =====================================================
//     // UPDATE
//     // =====================================================

//     @PutMapping("/{id}")
//     public ResponseEntity<VehicleRouteMappingResponse> update(
//             @PathVariable Long id,
//             @RequestParam Long schoolId,
//             @RequestBody VehicleRouteMappingRequest request) {

//         request.setSchoolId(schoolId);

//         return ResponseEntity.ok(
//                 mappingService.updateMapping(
//                         schoolId,
//                         id,
//                         request
//                 )
//         );
//     }


//     // =====================================================
//     // TOGGLE STATUS
//     // =====================================================

//     @PatchMapping("/{id}/status")
//     public ResponseEntity<VehicleRouteMappingResponse> toggleStatus(
//             @PathVariable Long id,
//             @RequestParam Long schoolId) {

//         return ResponseEntity.ok(
//                 mappingService.toggleStatus(
//                         schoolId,
//                         id
//                 )
//         );
//     }


//     // =====================================================
//     // DELETE
//     // =====================================================

//     @DeleteMapping("/{id}")
//     public ResponseEntity<Void> delete(
//             @PathVariable Long id,
//             @RequestParam Long schoolId) {

//         mappingService.deleteMapping(
//                 schoolId,
//                 id
//         );

//         return ResponseEntity
//                 .noContent()
//                 .build();
//     }

//     @GetMapping("/search")
// public ResponseEntity<List<StopSearchResponse>> searchByStop(
//         @RequestParam Long schoolId,
//         @RequestParam String stop) {

//     return ResponseEntity.ok(
//             mappingService.searchByStop(
//                     schoolId,
//                     stop
//             )
//     );
// }
// }



package com.schoolmanagement.schoolmanagementwebsite.controller.Transport;

import java.util.List;

import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import com.schoolmanagement.schoolmanagementwebsite.dto.Transport.StopSearchResponse;
import com.schoolmanagement.schoolmanagementwebsite.dto.Transport.VehicleRouteMappingRequest;
import com.schoolmanagement.schoolmanagementwebsite.dto.Transport.VehicleRouteMappingResponse;
import com.schoolmanagement.schoolmanagementwebsite.service.Transport.VehicleRouteMappingService;

import lombok.RequiredArgsConstructor;

@RestController
@RequestMapping("/api/transport/vehicle-routes")
@RequiredArgsConstructor
public class VehicleRouteMappingController {

    private final VehicleRouteMappingService mappingService;

    // =====================================================
    // ASSIGN ROUTE
    // =====================================================

    @PostMapping
    public ResponseEntity<VehicleRouteMappingResponse> assignRoute(
            @RequestParam Long schoolId,
            @RequestBody VehicleRouteMappingRequest request) {

        request.setSchoolId(schoolId);

        return ResponseEntity
                .status(HttpStatus.CREATED)
                .body(mappingService.assignRoute(request));
    }

    // =====================================================
    // GET ALL
    // =====================================================

    @GetMapping
    public ResponseEntity<List<VehicleRouteMappingResponse>> getAll(
            @RequestParam Long schoolId) {

        return ResponseEntity.ok(
                mappingService.getAllMappings(schoolId)
        );
    }

    // =====================================================
    // SEARCH STOP
    // Keep this BEFORE /{id}
    // =====================================================

    @GetMapping("/search")
    public ResponseEntity<List<StopSearchResponse>> searchByStop(
            @RequestParam Long schoolId,
            @RequestParam String stop) {

        return ResponseEntity.ok(
                mappingService.searchByStop(
                        schoolId,
                        stop
                )
        );
    }

    // =====================================================
    // GET BY ID
    // =====================================================

    @GetMapping("/{id}")
    public ResponseEntity<VehicleRouteMappingResponse> getById(
            @PathVariable Long id,
            @RequestParam Long schoolId) {

        return ResponseEntity.ok(
                mappingService.getById(
                        schoolId,
                        id
                )
        );
    }

    // =====================================================
    // UPDATE
    // =====================================================

    @PutMapping("/{id}")
    public ResponseEntity<VehicleRouteMappingResponse> update(
            @PathVariable Long id,
            @RequestParam Long schoolId,
            @RequestBody VehicleRouteMappingRequest request) {

        request.setSchoolId(schoolId);

        return ResponseEntity.ok(
                mappingService.updateMapping(
                        schoolId,
                        id,
                        request
                )
        );
    }

    // =====================================================
    // TOGGLE STATUS
    // =====================================================

    @PatchMapping("/{id}/status")
    public ResponseEntity<VehicleRouteMappingResponse> toggleStatus(
            @PathVariable Long id,
            @RequestParam Long schoolId) {

        return ResponseEntity.ok(
                mappingService.toggleStatus(
                        schoolId,
                        id
                )
        );
    }

    // =====================================================
    // DELETE
    // =====================================================

    @DeleteMapping("/{id}")
    public ResponseEntity<Void> delete(
            @PathVariable Long id,
            @RequestParam Long schoolId) {

        mappingService.deleteMapping(
                schoolId,
                id
        );

        return ResponseEntity
                .noContent()
                .build();
    }
}
