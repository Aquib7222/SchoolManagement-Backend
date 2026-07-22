package com.schoolmanagement.schoolmanagementwebsite.service.fee;

import java.util.List;

import com.schoolmanagement.schoolmanagementwebsite.dto.fee.FeeStructureDto;
import com.schoolmanagement.schoolmanagementwebsite.entity.fee.FeeStructure;

public interface FeeStructureService {

    String save(FeeStructureDto dto);

    List<FeeStructure> getAll();

    FeeStructure getById(Long id);

    String update(Long id, FeeStructureDto dto);

    String delete(Long id);

}