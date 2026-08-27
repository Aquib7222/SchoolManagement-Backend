package com.schoolmanagement.schoolmanagementwebsite.controller;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import com.schoolmanagement.schoolmanagementwebsite.entity.UserGroup;
import com.schoolmanagement.schoolmanagementwebsite.service.UserGroupService;

@RestController
@RequestMapping("/api/user-group")
@CrossOrigin(origins = "http://localhost:5173")
public class UserGroupController {

    @Autowired
    private UserGroupService service;

    // ==========================================
    // GET ALL ACTIVE GROUPS
    // ==========================================

    @GetMapping("/all")
    public ResponseEntity<List<UserGroup>> getAllGroups() {

        return ResponseEntity.ok(
                service.getAllGroups()
        );
    }

    // ==========================================
    // CREATE
    // ==========================================

    @PostMapping("/create")
    public ResponseEntity<?> createGroup(
            @RequestBody UserGroup userGroup
    ) {

        try {

            UserGroup savedGroup =
                    service.createGroup(userGroup);

            return ResponseEntity
                    .status(HttpStatus.CREATED)
                    .body(savedGroup);

        } catch (RuntimeException e) {

            return ResponseEntity
                    .badRequest()
                    .body(e.getMessage());
        }
    }

    // GET BY ID
    @GetMapping("/{id}")
    public ResponseEntity<UserGroup> getGroupById(
            @PathVariable Long id) {

        return ResponseEntity.ok(
                service.getGroupById(id)
        );
    }

    // ==========================================
    // UPDATE
    // ==========================================

    @PutMapping("/update/{id}")
    public ResponseEntity<?> updateGroup(
            @PathVariable Long id,
            @RequestBody UserGroup userGroup
    ) {

        try {

            UserGroup updatedGroup =
                    service.updateGroup(
                            id,
                            userGroup
                    );

            return ResponseEntity.ok(
                    updatedGroup
            );

        } catch (RuntimeException e) {

            return ResponseEntity
                    .badRequest()
                    .body(e.getMessage());
        }
    }

    // ==========================================
    // DELETE
    // ==========================================

    @DeleteMapping("/delete/{id}")
    public ResponseEntity<?> deleteGroup(
            @PathVariable Long id
    ) {

        try {

            service.deleteGroup(id);

            return ResponseEntity.ok(
                    "User group deleted successfully"
            );

        } catch (RuntimeException e) {

            return ResponseEntity
                    .badRequest()
                    .body(e.getMessage());
        }
    }
}