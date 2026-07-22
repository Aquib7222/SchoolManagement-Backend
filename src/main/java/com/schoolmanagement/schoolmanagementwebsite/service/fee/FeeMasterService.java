package com.schoolmanagement.schoolmanagementwebsite.service.fee;

import java.util.List;

import com.schoolmanagement.schoolmanagementwebsite.dto.fee.FeeMasterDto;
import com.schoolmanagement.schoolmanagementwebsite.entity.fee.FeeMaster;

public interface FeeMasterService {

    String save(FeeMasterDto dto);

    List<FeeMaster> getAll();

    FeeMaster getById(Long id);

    String update(Long id, FeeMasterDto dto);

    String delete(Long id);

}