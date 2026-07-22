package com.schoolmanagement.schoolmanagementwebsite.repository;


import org.springframework.data.jpa.repository.JpaRepository;
import com.schoolmanagement.schoolmanagementwebsite.entity.Module;

public interface ModuleRepository extends JpaRepository<Module, Long> {

}