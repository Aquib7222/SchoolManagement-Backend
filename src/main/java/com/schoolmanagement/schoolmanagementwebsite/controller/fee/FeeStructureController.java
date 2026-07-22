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

    public FeeStructureController(FeeStructureService service) {
        this.service = service;
    }

    @PostMapping
    public ResponseEntity<String> save(@RequestBody FeeStructureDto dto) {

        return ResponseEntity.ok(service.save(dto));
    }

    @GetMapping
    public ResponseEntity<List<FeeStructure>> getAll() {

        return ResponseEntity.ok(service.getAll());
    }

    @GetMapping("/{id}")
    public ResponseEntity<FeeStructure> getById(@PathVariable Long id) {

        return ResponseEntity.ok(service.getById(id));
    }

    @PutMapping("/{id}")
    public ResponseEntity<String> update(
            @PathVariable Long id,
            @RequestBody FeeStructureDto dto) {

        return ResponseEntity.ok(service.update(id, dto));
    }

    @DeleteMapping("/{id}")
    public ResponseEntity<String> delete(@PathVariable Long id) {

        return ResponseEntity.ok(service.delete(id));
    }
}