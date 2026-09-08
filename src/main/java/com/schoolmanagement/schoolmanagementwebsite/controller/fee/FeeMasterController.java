package com.schoolmanagement.schoolmanagementwebsite.controller.fee;

import java.util.List;

import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import com.schoolmanagement.schoolmanagementwebsite.dto.fee.FeeMasterDto;
import com.schoolmanagement.schoolmanagementwebsite.entity.fee.FeeMaster;
import com.schoolmanagement.schoolmanagementwebsite.service.fee.FeeMasterService;

@RestController
@RequestMapping("/api/fee-master")
@CrossOrigin(origins = "http://localhost:5173")
public class FeeMasterController {

    private final FeeMasterService service;

    public FeeMasterController(FeeMasterService service) {
        this.service = service;
    }

    @PostMapping
    public ResponseEntity<String> save(
            @RequestParam Long schoolId,
            @RequestBody FeeMasterDto dto) {

        return ResponseEntity.ok(
                service.save(schoolId, dto)
        );
    }

    @GetMapping
    public ResponseEntity<List<FeeMaster>> getAll(
            @RequestParam Long schoolId) {

        return ResponseEntity.ok(
                service.getAll(schoolId)
        );
    }

    @GetMapping("/{id}")
    public ResponseEntity<FeeMaster> getById(
            @RequestParam Long schoolId,
            @PathVariable Long id) {

        return ResponseEntity.ok(
                service.getById(schoolId, id)
        );
    }

    @PutMapping("/{id}")
    public ResponseEntity<String> update(
            @RequestParam Long schoolId,
            @PathVariable Long id,
            @RequestBody FeeMasterDto dto) {

        return ResponseEntity.ok(
                service.update(
                        schoolId,
                        id,
                        dto
                )
        );
    }

    @DeleteMapping("/{id}")
    public ResponseEntity<String> delete(
            @RequestParam Long schoolId,
            @PathVariable Long id) {

        return ResponseEntity.ok(
                service.delete(schoolId, id)
        );
    }
}