package com.schoolmanagement.schoolmanagementwebsite.ServiceImpl.fee;

import java.util.List;

import org.springframework.stereotype.Service;

import com.schoolmanagement.schoolmanagementwebsite.dto.fee.FeeMasterDto;
import com.schoolmanagement.schoolmanagementwebsite.entity.fee.FeeMaster;
import com.schoolmanagement.schoolmanagementwebsite.repository.fee.FeeMasterRepository;
import com.schoolmanagement.schoolmanagementwebsite.service.fee.FeeMasterService;

@Service
public class FeeMasterServiceImpl implements FeeMasterService {

    private final FeeMasterRepository repository;

    public FeeMasterServiceImpl(FeeMasterRepository repository) {
        this.repository = repository;
    }

    @Override
    public String save(FeeMasterDto dto) {

        if (repository.findByFeeName(dto.getFeeName()).isPresent()) {
            return "Fee Type Already Exists";
        }

        FeeMaster fee = new FeeMaster();
        fee.setFeeName(dto.getFeeName());
        fee.setStatus(dto.getStatus());
        fee.setFeeCode(dto.getFeeCode());
        fee.setFeeCategory(dto.getFeeCategory());

        repository.save(fee);

        return "Fee Type Saved Successfully";
    }

    @Override
    public List<FeeMaster> getAll() {
        return repository.findAll();
    }

    @Override
    public FeeMaster getById(Long id) {
        return repository.findById(id).orElseThrow();
    }

    @Override
    public String update(Long id, FeeMasterDto dto) {

        FeeMaster fee = repository.findById(id).orElseThrow();

        fee.setFeeName(dto.getFeeName());
        fee.setStatus(dto.getStatus());
        fee.setFeeCode(dto.getFeeCode());
        fee.setFeeCategory(dto.getFeeCategory());

        repository.save(fee);

        return "Updated Successfully";
    }

    @Override
    public String delete(Long id) {

        repository.deleteById(id);

        return "Deleted Successfully";
    }

}