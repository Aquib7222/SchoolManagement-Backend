// package com.schoolmanagement.schoolmanagementwebsite.ServiceImpl;

// import java.nio.file.*;
// import java.util.List;
// import java.util.UUID;

// import org.springframework.beans.factory.annotation.Autowired;
// import org.springframework.stereotype.Service;
// import org.springframework.web.multipart.MultipartFile;

// import com.schoolmanagement.schoolmanagementwebsite.dto.ModuleDto;
// import com.schoolmanagement.schoolmanagementwebsite.repository.ModuleRepository;
// import com.schoolmanagement.schoolmanagementwebsite.service.ModuleService;
// import com.schoolmanagement.schoolmanagementwebsite.entity.Module;

// @Service
// public class ModuleServiceImpl implements ModuleService {

//     @Autowired
//     private ModuleRepository repository;

//     private final String uploadDir = "uploads";

//     @Override
//     public String createModule(ModuleDto dto, MultipartFile image) throws Exception {

//         Module module = new Module();

//         module.setModuleName(dto.getModuleName());
//         module.setDescription(dto.getDescription());
//         module.setPath(dto.getPath());
//         module.setHasMenu(dto.getHasMenu());
//         module.setStatus(dto.getStatus());
//         module.setModuleCode(dto.getModuleCode());
//         module.setSequenceNumber(dto.getSequenceNumber());

//         if (image != null && !image.isEmpty()) {

//             Path path = Paths.get(uploadDir);

//             if (!Files.exists(path)) {
//                 Files.createDirectories(path);
//             }

//             String fileName = image.getOriginalFilename().replaceAll("\\s+", "_");

//             Files.copy(
//                     image.getInputStream(),
//                     path.resolve(fileName),
//                     StandardCopyOption.REPLACE_EXISTING);

//             module.setImage(fileName);
//         }

//         repository.save(module);

//         return "Module Created Successfully";
//     }

//     @Override
//     public List<Module> getAllModules() {

//          List<Module> modules = repository.findAll();

//     System.out.println("TOTAL MODULES FROM DB = " + modules.size());

//     return modules;

//     }
// }


package com.schoolmanagement.schoolmanagementwebsite.ServiceImpl;

import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.nio.file.StandardCopyOption;
import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.web.multipart.MultipartFile;

import com.schoolmanagement.schoolmanagementwebsite.dto.ModuleDto;
import com.schoolmanagement.schoolmanagementwebsite.entity.Module;
import com.schoolmanagement.schoolmanagementwebsite.repository.ModuleRepository;
import com.schoolmanagement.schoolmanagementwebsite.service.ModuleService;

@Service
public class ModuleServiceImpl implements ModuleService {

    @Autowired
    private ModuleRepository repository;

    private final String uploadDir = "uploads";

    // =====================================================
    // CREATE
    // =====================================================

    @Override
    public String createModule(
            ModuleDto dto,
            MultipartFile image
    ) throws Exception {

        Module module = new Module();

        module.setModuleName(dto.getModuleName());
        module.setDescription(dto.getDescription());
        module.setPath(dto.getPath());
        module.setHasMenu(dto.getHasMenu());
        module.setStatus(dto.getStatus());
        module.setModuleCode(dto.getModuleCode());
        module.setSequenceNumber(dto.getSequenceNumber());

        if (image != null && !image.isEmpty()) {

            Path path = Paths.get(uploadDir);

            if (!Files.exists(path)) {
                Files.createDirectories(path);
            }

            String fileName =
                    image.getOriginalFilename()
                            .replaceAll("\\s+", "_");

            Files.copy(
                    image.getInputStream(),
                    path.resolve(fileName),
                    StandardCopyOption.REPLACE_EXISTING
            );

            module.setImage(fileName);
        }

        repository.save(module);

        return "Module Created Successfully";
    }

    // =====================================================
    // GET ALL
    // =====================================================

    @Override
    public List<Module> getAllModules() {

        List<Module> modules =
                repository.findAll();

        System.out.println(
                "TOTAL MODULES FROM DB = "
                        + modules.size()
        );

        return modules;
    }

    // =====================================================
    // GET BY ID
    // =====================================================

    @Override
    public Module getModuleById(Long id) {

        return repository.findById(id)
                .orElseThrow(() ->
                        new RuntimeException(
                                "Module not found with id: " + id
                        )
                );
    }

    // =====================================================
    // UPDATE
    // =====================================================

    @Override
    public String updateModule(
            Long id,
            ModuleDto dto,
            MultipartFile image
    ) throws Exception {

        Module module = repository.findById(id)
                .orElseThrow(() ->
                        new RuntimeException(
                                "Module not found with id: " + id
                        )
                );

        // Update basic fields

        module.setModuleName(dto.getModuleName());

        module.setDescription(
                dto.getDescription()
        );

        module.setPath(
                dto.getPath()
        );

        module.setHasMenu(
                dto.getHasMenu()
        );

        module.setStatus(
                dto.getStatus()
        );

        module.setModuleCode(
                dto.getModuleCode()
        );

        module.setSequenceNumber(
                dto.getSequenceNumber()
        );

        // =================================================
        // IMAGE UPDATE
        // =================================================

        if (image != null && !image.isEmpty()) {

            Path uploadPath =
                    Paths.get(uploadDir);

            if (!Files.exists(uploadPath)) {
                Files.createDirectories(uploadPath);
            }

            String newFileName =
                    image.getOriginalFilename()
                            .replaceAll("\\s+", "_");

            // Delete old image
            if (module.getImage() != null
                    && !module.getImage().isBlank()) {

                Path oldImage =
                        uploadPath.resolve(
                                module.getImage()
                        );

                try {

                    Files.deleteIfExists(
                            oldImage
                    );

                } catch (Exception e) {

                    System.out.println(
                            "Unable to delete old image: "
                                    + e.getMessage()
                    );
                }
            }

            // Save new image

            Files.copy(
                    image.getInputStream(),
                    uploadPath.resolve(newFileName),
                    StandardCopyOption.REPLACE_EXISTING
            );

            module.setImage(newFileName);
        }

        repository.save(module);

        return "Module Updated Successfully";
    }

    // =====================================================
    // DELETE
    // =====================================================

    @Override
    public String deleteModule(Long id)
            throws Exception {

        Module module =
                repository.findById(id)
                        .orElseThrow(() ->
                                new RuntimeException(
                                        "Module not found with id: "
                                                + id
                                )
                        );

        // =================================================
        // DELETE IMAGE
        // =================================================

        if (module.getImage() != null
                && !module.getImage().isBlank()) {

            Path imagePath =
                    Paths.get(uploadDir)
                            .resolve(module.getImage());

            try {

                Files.deleteIfExists(
                        imagePath
                );

            } catch (Exception e) {

                System.out.println(
                        "Unable to delete module image: "
                                + e.getMessage()
                );
            }
        }

        // =================================================
        // DELETE MODULE
        // =================================================

        repository.delete(module);

        return "Module Deleted Successfully";
    }
}