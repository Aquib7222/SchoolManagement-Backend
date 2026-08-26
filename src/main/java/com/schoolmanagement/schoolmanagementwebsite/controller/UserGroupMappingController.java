package com.schoolmanagement.schoolmanagementwebsite.controller;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import com.schoolmanagement.schoolmanagementwebsite.dto.UserGroupMappingDto;
import com.schoolmanagement.schoolmanagementwebsite.service.UserGroupMappingService;

@RestController
@RequestMapping("/api/user-group-mapping")
@CrossOrigin(origins = "http://localhost:5173")
public class UserGroupMappingController {

    @Autowired
    private UserGroupMappingService service;

    // Save / Update Mapping
    @PostMapping("/save")
    public ResponseEntity<String> saveMapping(
            @RequestBody UserGroupMappingDto dto) {

        return ResponseEntity.ok(service.saveMapping(dto));
    }

    @PutMapping("/update/{id}")
public ResponseEntity<String> updateMapping(
        @PathVariable Long id,
        @RequestBody UserGroupMappingDto dto) {

    return ResponseEntity.ok(
            service.updateMapping(id, dto)
    );
}

    // Get All Mappings
    @GetMapping("/all")
    public ResponseEntity<?> getAllMappings() {

        return ResponseEntity.ok(service.getAllMappings());
    }

    // Get Mapping By Id
    @GetMapping("/{id}")
    public ResponseEntity<?> getMappingById(
            @PathVariable Long id) {

        return ResponseEntity.ok(service.getMappingById(id));
    }

    // Delete Mapping
    @DeleteMapping("/{id}")
    public ResponseEntity<String> deleteMapping(
            @PathVariable Long id) {

        service.deleteMapping(id);

        return ResponseEntity.ok("Mapping Deleted Successfully");
    }

}