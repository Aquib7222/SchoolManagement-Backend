package com.schoolmanagement.schoolmanagementwebsite.repository.fee;

import java.util.List;
import java.util.Optional;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import com.schoolmanagement.schoolmanagementwebsite.entity.fee.FeeMaster;

@Repository
public interface FeeMasterRepository extends JpaRepository<FeeMaster, Long> {

    List<FeeMaster> findBySchool_Id(Long schoolId);

    Optional<FeeMaster> findByIdAndSchool_Id(
            Long id,
            Long schoolId
    );

    Optional<FeeMaster> findBySchool_IdAndFeeName(
            Long schoolId,
            String feeName
    );

    Optional<FeeMaster> findBySchool_IdAndFeeCode(
            Long schoolId,
            String feeCode
    );

    boolean existsBySchool_IdAndFeeName(
            Long schoolId,
            String feeName
    );

    boolean existsBySchool_IdAndFeeCode(
            Long schoolId,
            String feeCode
    );

    void deleteByIdAndSchool_Id(
            Long id,
            Long schoolId
    );
}