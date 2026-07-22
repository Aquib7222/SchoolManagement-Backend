package com.schoolmanagement.schoolmanagementwebsite.controller;

import java.io.IOException;
import java.util.List;

import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;

import com.schoolmanagement.schoolmanagementwebsite.entity.StudentDocument;
import com.schoolmanagement.schoolmanagementwebsite.service.StudentDocumentService;

import lombok.RequiredArgsConstructor;

@RestController
@RequestMapping("/api/documents")
@RequiredArgsConstructor
@CrossOrigin(origins = "http://localhost:5173")
public class StudentDocumentController {

    private final StudentDocumentService documentService;

    @PostMapping(
    value = "/upload",
    consumes = "multipart/form-data"
)
public ResponseEntity<StudentDocument> uploadDocument(
        @RequestParam Long admissionId,
        @RequestParam String type,
        @RequestParam MultipartFile file
) throws IOException {

    return ResponseEntity.ok(
            documentService.uploadDocument(admissionId, type, file)
    );
}


    // ✅ Get documents by admission
    @GetMapping("/admission/{admissionId}")
    public List<StudentDocument> getDocuments(
            @PathVariable Long admissionId) {

        return documentService.getDocuments(admissionId);
    }
}
