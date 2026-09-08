package com.schoolmanagement.schoolmanagementwebsite.controller.fee;

import java.util.List;

import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import com.schoolmanagement.schoolmanagementwebsite.dto.fee.FeeStructureDto;
import com.schoolmanagement.schoolmanagementwebsite.entity.fee.FeeStructure;
import com.schoolmanagement.schoolmanagementwebsite.service.fee.FeeStructureService;

@RestController
@RequestMapping("/api/fee-structure")
@CrossOrigin(origins = "http://localhost:5173")
public class FeeStructureController {

    private final FeeStructureService service;

    public FeeStructureController(
            FeeStructureService service) {

        this.service = service;
    }

    @PostMapping
    public ResponseEntity<String> save(
            @RequestParam Long schoolId,
            @RequestBody FeeStructureDto dto) {

        return ResponseEntity.ok(
                service.save(schoolId, dto)
        );
    }

    @GetMapping
    public ResponseEntity<List<FeeStructure>> getAll(
            @RequestParam Long schoolId) {

        return ResponseEntity.ok(
                service.getAll(schoolId)
        );
    }

    @GetMapping("/{id}")
    public ResponseEntity<FeeStructure> getById(
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
            @RequestBody FeeStructureDto dto) {

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
                service.delete(
                        schoolId,
                        id
                )
        );
    }
}