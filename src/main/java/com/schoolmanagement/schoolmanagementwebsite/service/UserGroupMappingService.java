package com.schoolmanagement.schoolmanagementwebsite.service;

import java.util.List;

import com.schoolmanagement.schoolmanagementwebsite.dto.UserGroupMappingDto;
import com.schoolmanagement.schoolmanagementwebsite.entity.UserGroupMapping;

public interface UserGroupMappingService {

    String saveMapping(UserGroupMappingDto dto);

    List<UserGroupMapping> getAllMappings();

    UserGroupMapping getMappingById(Long id);

    void deleteMapping(Long id);
}