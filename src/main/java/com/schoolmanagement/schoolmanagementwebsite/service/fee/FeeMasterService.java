package com.schoolmanagement.schoolmanagementwebsite.service.fee;

import java.util.List;

import com.schoolmanagement.schoolmanagementwebsite.dto.fee.FeeMasterDto;
import com.schoolmanagement.schoolmanagementwebsite.entity.fee.FeeMaster;

public interface FeeMasterService {

    String save(Long schoolId, FeeMasterDto dto);

    List<FeeMaster> getAll(Long schoolId);

    FeeMaster getById(Long schoolId, Long id);

    String update(Long schoolId, Long id, FeeMasterDto dto);

    String delete(Long schoolId, Long id);
}