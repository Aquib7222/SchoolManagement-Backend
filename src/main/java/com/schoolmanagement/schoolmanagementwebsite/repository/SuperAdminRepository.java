package com.schoolmanagement.schoolmanagementwebsite.repository;

import org.springframework.data.jpa.repository.JpaRepository;

import com.schoolmanagement.schoolmanagementwebsite.entity.SuperAdmin;

public interface SuperAdminRepository extends JpaRepository<SuperAdmin, Long> {
}

