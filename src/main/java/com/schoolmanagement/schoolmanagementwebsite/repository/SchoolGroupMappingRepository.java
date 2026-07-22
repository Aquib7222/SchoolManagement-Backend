package com.schoolmanagement.schoolmanagementwebsite.repository;

import java.util.Optional;

import org.springframework.data.jpa.repository.JpaRepository;

import com.schoolmanagement.schoolmanagementwebsite.entity.SchoolGroupMapping;

public interface SchoolGroupMappingRepository
        extends JpaRepository<SchoolGroupMapping, Long> {

    Optional<SchoolGroupMapping> findBySchoolIdAndUserGroupId(
            Long schoolId,
            Long userGroupId
    );

    void deleteBySchoolIdAndUserGroupId(
            Long schoolId,
            Long userGroupId
    );
}