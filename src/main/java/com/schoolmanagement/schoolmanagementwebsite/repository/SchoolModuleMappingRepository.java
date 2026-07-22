package com.schoolmanagement.schoolmanagementwebsite.repository;

import org.springframework.data.jpa.repository.JpaRepository;

import com.schoolmanagement.schoolmanagementwebsite.entity.SchoolModuleMapping;

public interface SchoolModuleMappingRepository
        extends JpaRepository<SchoolModuleMapping, Long> {

}