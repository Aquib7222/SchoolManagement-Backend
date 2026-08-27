package com.schoolmanagement.schoolmanagementwebsite.repository;

import java.util.List;
import java.util.Optional;

import org.springframework.data.jpa.repository.JpaRepository;

import com.schoolmanagement.schoolmanagementwebsite.entity.UserGroup;

public interface UserGroupRepository extends JpaRepository<UserGroup, Long> {

    List<UserGroup> findByStatus(String status);

    boolean existsByGroupCodeIgnoreCase(String groupCode);

    boolean existsByGroupNameIgnoreCase(String groupName);

    Optional<UserGroup> findByGroupCodeIgnoreCase(String groupCode);

    boolean existsByGroupCodeIgnoreCaseAndIdNot(
            String groupCode,
            Long id
    );

    boolean existsByGroupNameIgnoreCaseAndIdNot(
            String groupName,
            Long id
    );

    Optional<UserGroup> findById(Long id);
    
}
