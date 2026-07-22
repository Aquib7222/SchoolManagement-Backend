package com.schoolmanagement.schoolmanagementwebsite.controller;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import com.schoolmanagement.schoolmanagementwebsite.dto.MenuDto;
import com.schoolmanagement.schoolmanagementwebsite.entity.Menu;
import com.schoolmanagement.schoolmanagementwebsite.service.MenuService;

@RestController
@RequestMapping("/api/menu")
@CrossOrigin(origins = "http://localhost:5173")
public class MenuController {

    @Autowired
    private MenuService menuService;

    // Create Menu
    @PostMapping("/create")
    public ResponseEntity<?> createMenu(@RequestBody MenuDto dto) {

        return ResponseEntity.ok(menuService.save(dto));

    }

    // Get All Menus
    @GetMapping("/all")
    public ResponseEntity<List<Menu>> getAllMenus() {

        return ResponseEntity.ok(menuService.getAllMenus());

    }

    // Get Menu By Id
    @GetMapping("/{id}")
    public ResponseEntity<Menu> getMenuById(@PathVariable Long id) {

        return ResponseEntity.ok(menuService.getMenuById(id));

    }

    // Get Menus By Module
    @GetMapping("/module/{moduleId}")
    public ResponseEntity<List<Menu>> getMenusByModule(
            @PathVariable Long moduleId) {

        return ResponseEntity.ok(menuService.getMenusByModule(moduleId));

    }

    // Update Menu
    @PutMapping("/update/{id}")
    public ResponseEntity<?> updateMenu(
            @PathVariable Long id,
            @RequestBody MenuDto dto) {

        return ResponseEntity.ok(menuService.updateMenu(id, dto));

    }

    // Delete Menu
    @DeleteMapping("/delete/{id}")
    public ResponseEntity<?> deleteMenu(@PathVariable Long id) {

        return ResponseEntity.ok(menuService.deleteMenu(id));

    }

}