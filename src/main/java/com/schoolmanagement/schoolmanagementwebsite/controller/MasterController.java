package com.schoolmanagement.schoolmanagementwebsite.controller;

import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.schoolmanagement.schoolmanagementwebsite.enums.FeeBatch;
import com.schoolmanagement.schoolmanagementwebsite.enums.FeeCategory;
import com.schoolmanagement.schoolmanagementwebsite.enums.Standard;
import com.schoolmanagement.schoolmanagementwebsite.enums.Sessions;

@RestController
@RequestMapping("/api/master")
@CrossOrigin(origins = "http://localhost:5173")
public class MasterController {

    @GetMapping("/fee-category")
    public FeeCategory[] getFeeCategory() {
        return FeeCategory.values();
    }

    @GetMapping("/fee-batch")
    public FeeBatch[] getFeeBatch() {
        return FeeBatch.values();
    }

    @GetMapping("/standard")
    public Standard[] getStandard() {
        return Standard.values();
    }

    @GetMapping("/sessions")
    public Sessions[] getSessions() {
        return Sessions.values();
    }


}