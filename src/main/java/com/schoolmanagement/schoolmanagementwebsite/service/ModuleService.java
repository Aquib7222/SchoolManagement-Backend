package com.schoolmanagement.schoolmanagementwebsite.service;

import java.util.List;

import org.springframework.web.multipart.MultipartFile;

import com.schoolmanagement.schoolmanagementwebsite.dto.ModuleDto;
import com.schoolmanagement.schoolmanagementwebsite.entity.Module;

public interface ModuleService {

    String createModule(ModuleDto dto, MultipartFile image) throws Exception;

     List<Module> getAllModules();

     Module getModuleById(Long id);

    String updateModule(
            Long id,
            ModuleDto dto,
            MultipartFile image
    ) throws Exception;

    String deleteModule(Long id) throws Exception;
}