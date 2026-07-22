package com.schoolmanagement.schoolmanagementwebsite.repository;

import java.util.List;

import org.springframework.data.jpa.repository.JpaRepository;

import com.schoolmanagement.schoolmanagementwebsite.entity.UserGroupMenuMapping;
import com.schoolmanagement.schoolmanagementwebsite.entity.UserGroupMapping;

public interface UserGroupMenuMappingRepository
        extends JpaRepository<UserGroupMenuMapping, Long> {

    List<UserGroupMenuMapping> findByMapping(UserGroupMapping mapping);

    void deleteByMapping(UserGroupMapping mapping);

}