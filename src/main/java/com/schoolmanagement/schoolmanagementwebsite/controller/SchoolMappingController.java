package com.schoolmanagement.schoolmanagementwebsite.controller;

import java.util.List;

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

    @PostMapping("/save")
    public String saveMapping(
            @RequestBody SaveSchoolMappingRequest request) {

        schoolMappingService.saveMapping(request);

        return "School Mapping Saved Successfully";
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