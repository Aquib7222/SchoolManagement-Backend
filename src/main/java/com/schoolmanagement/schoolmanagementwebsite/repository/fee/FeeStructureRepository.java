package com.schoolmanagement.schoolmanagementwebsite.repository.fee;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import com.schoolmanagement.schoolmanagementwebsite.entity.fee.FeeStructure;

@Repository
public interface FeeStructureRepository extends JpaRepository<FeeStructure, Long> {

}