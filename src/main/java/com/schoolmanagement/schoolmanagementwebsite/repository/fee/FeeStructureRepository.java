package com.schoolmanagement.schoolmanagementwebsite.repository.fee;

import java.util.List;
import java.util.Optional;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import com.schoolmanagement.schoolmanagementwebsite.entity.fee.FeeStructure;

@Repository
public interface FeeStructureRepository
        extends JpaRepository<FeeStructure, Long> {

    List<FeeStructure> findBySchool_Id(Long schoolId);

    Optional<FeeStructure> findByIdAndSchool_Id(
            Long id,
            Long schoolId
    );

    void deleteByIdAndSchool_Id(
            Long id,
            Long schoolId
    );
}