package com.schoolmanagement.schoolmanagementwebsite.repository;

import org.springframework.data.jpa.repository.JpaRepository;

import com.schoolmanagement.schoolmanagementwebsite.entity.SchoolSubMenuMapping;

public interface SchoolSubMenuMappingRepository
        extends JpaRepository<SchoolSubMenuMapping, Long> {

}