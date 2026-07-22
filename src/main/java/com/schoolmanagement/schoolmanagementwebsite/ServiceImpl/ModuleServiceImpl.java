package com.schoolmanagement.schoolmanagementwebsite.ServiceImpl;

import java.nio.file.*;
import java.util.List;
import java.util.UUID;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.web.multipart.MultipartFile;

import com.schoolmanagement.schoolmanagementwebsite.dto.ModuleDto;
import com.schoolmanagement.schoolmanagementwebsite.repository.ModuleRepository;
import com.schoolmanagement.schoolmanagementwebsite.service.ModuleService;
import com.schoolmanagement.schoolmanagementwebsite.entity.Module;

@Service
public class ModuleServiceImpl implements ModuleService {

    @Autowired
    private ModuleRepository repository;

    private final String uploadDir = "uploads";

    @Override
    public String createModule(ModuleDto dto, MultipartFile image) throws Exception {

        Module module = new Module();

        module.setModuleName(dto.getModuleName());
        module.setIcon(dto.getIcon());
        module.setPath(dto.getPath());
        module.setHasMenu(dto.getHasMenu());
        module.setStatus(dto.getStatus());
        module.setSequenceNumber(dto.getSequenceNumber());

        if (image != null && !image.isEmpty()) {

            Path path = Paths.get(uploadDir);

            if (!Files.exists(path)) {
                Files.createDirectories(path);
            }

            String fileName = image.getOriginalFilename().replaceAll("\\s+", "_");

            Files.copy(
                    image.getInputStream(),
                    path.resolve(fileName),
                    StandardCopyOption.REPLACE_EXISTING);

            module.setImage(fileName);
        }

        repository.save(module);

        return "Module Created Successfully";
    }

    @Override
    public List<Module> getAllModules() {

        return repository.findAll();

    }
}
