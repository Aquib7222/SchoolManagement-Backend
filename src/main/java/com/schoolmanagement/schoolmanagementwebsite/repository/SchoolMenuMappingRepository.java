package com.schoolmanagement.schoolmanagementwebsite.repository;

import org.springframework.data.jpa.repository.JpaRepository;

import com.schoolmanagement.schoolmanagementwebsite.entity.SchoolMenuMapping;

public interface SchoolMenuMappingRepository
        extends JpaRepository<SchoolMenuMapping, Long> {

}