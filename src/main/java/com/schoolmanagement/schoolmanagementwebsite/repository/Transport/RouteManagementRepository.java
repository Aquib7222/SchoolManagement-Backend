package com.schoolmanagement.schoolmanagementwebsite.repository.Transport;

import java.util.List;
import java.util.Optional;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import com.schoolmanagement.schoolmanagementwebsite.entity.Transport.RouteManagement;

@Repository
public interface RouteManagementRepository
        extends JpaRepository<RouteManagement, Long> {

    Optional<RouteManagement> findBySchoolIdAndRouteName(
            Long schoolId,
            String routeName
    );

    List<RouteManagement> findBySchoolId(Long schoolId);

    boolean existsBySchoolIdAndRouteName(
            Long schoolId,
            String routeName
    );
}