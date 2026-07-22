package com.schoolmanagement.schoolmanagementwebsite.repository;

import java.util.List;

import org.springframework.data.jpa.repository.JpaRepository;

import com.schoolmanagement.schoolmanagementwebsite.entity.UserGroupMapping;
import com.schoolmanagement.schoolmanagementwebsite.entity.UserGroupSubMenuMapping;

public interface UserGroupSubMenuMappingRepository
        extends JpaRepository<UserGroupSubMenuMapping, Long> {

    List<UserGroupSubMenuMapping> findByMapping(UserGroupMapping mapping);

    void deleteByMapping(UserGroupMapping mapping);

}