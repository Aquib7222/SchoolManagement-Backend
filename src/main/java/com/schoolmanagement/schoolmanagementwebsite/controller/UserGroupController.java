package com.schoolmanagement.schoolmanagementwebsite.controller;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
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

    @GetMapping("/all")
    public ResponseEntity<List<UserGroup>> getAllGroups(){

        return ResponseEntity.ok(service.getAllGroups());

    }

}