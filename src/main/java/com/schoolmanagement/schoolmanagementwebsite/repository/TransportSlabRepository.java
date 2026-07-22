package com.schoolmanagement.schoolmanagementwebsite.repository;

import java.util.Optional;

import org.springframework.data.jpa.repository.JpaRepository;

import com.schoolmanagement.schoolmanagementwebsite.entity.TransportSlab;
import com.schoolmanagement.schoolmanagementwebsite.enums.TransportDistance;

public interface TransportSlabRepository
        extends JpaRepository<TransportSlab, Long> {

    Optional<TransportSlab> findBySchool_IdAndDistance(
            Long schoolId,
            TransportDistance distance
    );
}

