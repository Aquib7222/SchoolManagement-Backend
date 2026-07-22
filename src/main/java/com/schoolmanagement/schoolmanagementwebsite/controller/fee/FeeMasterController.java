package com.schoolmanagement.schoolmanagementwebsite.controller.fee;

import java.util.List;

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
    public String save(@RequestBody FeeMasterDto dto) {
        return service.save(dto);
    }

    @GetMapping
    public List<FeeMaster> getAll() {
        return service.getAll();
    }

    @GetMapping("/{id}")
    public FeeMaster getById(@PathVariable Long id) {
        return service.getById(id);
    }

    @PutMapping("/{id}")
    public String update(
            @PathVariable Long id,
            @RequestBody FeeMasterDto dto) {

        return service.update(id, dto);
    }

    @DeleteMapping("/{id}")
    public String delete(@PathVariable Long id) {
        return service.delete(id);
    }
}