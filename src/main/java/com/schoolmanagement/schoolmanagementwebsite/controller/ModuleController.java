package com.schoolmanagement.schoolmanagementwebsite.controller;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;

import com.schoolmanagement.schoolmanagementwebsite.dto.ModuleDto;
import com.schoolmanagement.schoolmanagementwebsite.service.ModuleService;
import com.schoolmanagement.schoolmanagementwebsite.entity.Module;

@RestController
@RequestMapping("/api/module")
@CrossOrigin(origins = "http://localhost:5173")
public class ModuleController {

    @Autowired
    private ModuleService moduleService;

    @PostMapping(
            value = "/create",
            consumes = MediaType.MULTIPART_FORM_DATA_VALUE)
    public ResponseEntity<?> createModule(
            @RequestPart("module") ModuleDto module,
            @RequestPart(value = "image", required = false) MultipartFile image) throws Exception {

        return ResponseEntity.ok(
          
                moduleService.createModule(module, image));
    }
    @GetMapping("/all")
public ResponseEntity<List<Module>> getAllModules() {

    return ResponseEntity.ok(moduleService.getAllModules());

}

}
