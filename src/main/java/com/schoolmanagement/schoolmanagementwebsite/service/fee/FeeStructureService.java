package com.schoolmanagement.schoolmanagementwebsite.service.fee;

import java.util.List;

import com.schoolmanagement.schoolmanagementwebsite.dto.fee.FeeStructureDto;
import com.schoolmanagement.schoolmanagementwebsite.entity.fee.FeeStructure;

public interface FeeStructureService {

    String save(Long schoolId, FeeStructureDto dto);

    List<FeeStructure> getAll(Long schoolId);

    FeeStructure getById(Long schoolId, Long id);

    String update(Long schoolId, Long id, FeeStructureDto dto);

    String delete(Long schoolId, Long id);
}