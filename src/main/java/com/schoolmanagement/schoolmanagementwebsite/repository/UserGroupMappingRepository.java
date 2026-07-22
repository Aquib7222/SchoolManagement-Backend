package com.schoolmanagement.schoolmanagementwebsite.repository;

import java.util.List;
import java.util.Optional;

import org.springframework.data.jpa.repository.EntityGraph;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import com.schoolmanagement.schoolmanagementwebsite.entity.UserGroupMapping;

@Repository
public interface UserGroupMappingRepository
        extends JpaRepository<UserGroupMapping, Long> {

    
    Optional<UserGroupMapping> findByUserGroupIdAndModuleId(
            Long userGroupId,
            Long moduleId);
}