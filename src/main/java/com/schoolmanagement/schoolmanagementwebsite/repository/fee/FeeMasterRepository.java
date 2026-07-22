package com.schoolmanagement.schoolmanagementwebsite.repository.fee;

import java.util.Optional;

import org.springframework.data.jpa.repository.JpaRepository;

import com.schoolmanagement.schoolmanagementwebsite.entity.fee.FeeMaster;

public interface FeeMasterRepository
        extends JpaRepository<FeeMaster, Long> {

    Optional<FeeMaster> findByFeeName(String feeName);

}