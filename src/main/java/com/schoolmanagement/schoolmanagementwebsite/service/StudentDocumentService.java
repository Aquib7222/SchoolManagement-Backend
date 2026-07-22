package com.schoolmanagement.schoolmanagementwebsite.service;

import java.io.File;
import java.io.IOException;
import java.util.List;

import org.springframework.stereotype.Service;
import org.springframework.web.multipart.MultipartFile;

import com.schoolmanagement.schoolmanagementwebsite.entity.Admission;
import com.schoolmanagement.schoolmanagementwebsite.entity.StudentDocument;
import com.schoolmanagement.schoolmanagementwebsite.repository.AdmissionRepository;
import com.schoolmanagement.schoolmanagementwebsite.repository.StudentDocumentRepository;

import lombok.RequiredArgsConstructor;

// @Service
// @RequiredArgsConstructor
// public class StudentDocumentService {

//     private final StudentDocumentRepository documentRepository;
//     private final AdmissionRepository admissionRepository;

//     private static final String BASE_PATH = "uploads/admissions/";

//    public StudentDocument uploadDocument(
//         Long admissionId,
//         String documentType,
//         MultipartFile file) throws IOException {

//     if (file == null || file.isEmpty()) {
//         throw new RuntimeException("File is required");
//     }

//     Admission admission = admissionRepository.findById(admissionId)
//             .orElseThrow(() -> new RuntimeException("Admission not found"));

//     String folderPath = BASE_PATH + admission.getAdmissionNumber();
//     File folder = new File(folderPath);
//     if (!folder.exists()) folder.mkdirs();

//     String filePath = folderPath + "/" + System.currentTimeMillis()
//             + "_" + file.getOriginalFilename();

//     file.transferTo(new File(filePath));

//     StudentDocument document = StudentDocument.builder()
//             .admission(admission)
//             .documentType(documentType.toUpperCase())
//             .fileName(file.getOriginalFilename())
//             .filePath(filePath)
//             .build();

//     return documentRepository.save(document);
// }


//     public List<StudentDocument> getDocuments(Long admissionId) {
//         return documentRepository.findByAdmission_Id(admissionId);
//     }
// }

import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;

@Service
@RequiredArgsConstructor
public class StudentDocumentService {

    private final StudentDocumentRepository documentRepository;
    private final AdmissionRepository admissionRepository;

    // OUTSIDE tomcat, stable location
    private static final String BASE_PATH =
            System.getProperty("user.home") + "/school_uploads/admissions/";

    public StudentDocument uploadDocument(
            Long admissionId,
            String documentType,
            MultipartFile file) throws IOException {

        if (file == null || file.isEmpty()) {
            throw new RuntimeException("File is empty");
        }

        Admission admission = admissionRepository.findById(admissionId)
                .orElseThrow(() -> new RuntimeException("Admission not found"));

        // ✅ Create folder safely
        Path admissionFolder = Paths.get(
                BASE_PATH,
                admission.getAdmissionNumber()
        );

        Files.createDirectories(admissionFolder); // 💥 IMPORTANT

        // Safe filename
        String safeFileName =
                System.currentTimeMillis() + "_" +
                file.getOriginalFilename().replaceAll("\\s+", "_");

        Path filePath = admissionFolder.resolve(safeFileName);

        // ✅ Save file
        file.transferTo(filePath.toFile());

        StudentDocument document = StudentDocument.builder()
                .admission(admission)
                .documentType(documentType)
                .fileName(safeFileName)
                .filePath(filePath.toString())
                .build();

        return documentRepository.save(document);
    }

    public List<StudentDocument> getDocuments(Long admissionId) {
        return documentRepository.findByAdmission_Id(admissionId);
    }
}
