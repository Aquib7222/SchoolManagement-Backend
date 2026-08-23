package com.schoolmanagement.schoolmanagementwebsite.controller;

import java.util.List;

import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import com.schoolmanagement.schoolmanagementwebsite.dto.SaveSchoolMappingRequest;
import com.schoolmanagement.schoolmanagementwebsite.dto.SchoolMappingResponse;
import com.schoolmanagement.schoolmanagementwebsite.dto.SidebarModuleResponse;
import com.schoolmanagement.schoolmanagementwebsite.service.SchoolMappingService;

@RestController
@RequestMapping("/api/school-mapping")
@CrossOrigin(origins = "http://localhost:5173")
public class SchoolMappingController {

    private final SchoolMappingService schoolMappingService;

    public SchoolMappingController(SchoolMappingService schoolMappingService) {
        this.schoolMappingService = schoolMappingService;
    }

    // ================= SAVE =================
    // @PostMapping("/save")
    // public String saveMapping(
    //         @RequestBody SaveSchoolMappingRequest request) {
    //     schoolMappingService.saveMapping(request);
    //     return "School Mapping Saved Successfully";
    // }
@PostMapping("/save")
public ResponseEntity<?> saveMapping(
            @RequestBody SaveSchoolMappingRequest request) {

        try {

            System.out.println("===== SCHOOL MAPPING REQUEST =====");
            System.out.println("School ID: " + request.getSchoolId());
            System.out.println("User Group ID: " + request.getUserGroupId());
            System.out.println("Module IDs: " + request.getModuleIds());
            System.out.println("Menu IDs: " + request.getMenuIds());
            System.out.println("Sub Menu IDs: " + request.getSubMenuIds());

            schoolMappingService.saveMapping(request);

            return ResponseEntity.ok("School Mapping Saved Successfully");

        } catch (Exception e) {

            e.printStackTrace();

            return ResponseEntity
                    .status(HttpStatus.BAD_REQUEST)
                    .body(e.getMessage());
        }
    }

    // ================= LOAD =================
    @GetMapping("/load")
    public SchoolMappingResponse loadMapping(
            @RequestParam Long schoolId,
            @RequestParam Long groupId) {

        return schoolMappingService.loadMapping(
                schoolId,
                groupId);
    }

    @GetMapping("/sidebar")
    public List<SidebarModuleResponse> getSidebar(
            @RequestParam Long schoolId,
            @RequestParam Long groupId) {

        return schoolMappingService.getSidebar(
                schoolId,
                groupId);
    }

}
