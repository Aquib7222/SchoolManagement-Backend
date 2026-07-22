package com.schoolmanagement.schoolmanagementwebsite.repository;

import java.util.List;

import org.springframework.data.jpa.repository.JpaRepository;

import com.schoolmanagement.schoolmanagementwebsite.entity.UserGroup;

public interface UserGroupRepository extends JpaRepository<UserGroup, Long> {

    List<UserGroup> findByStatus(String status);
    
}
