package com.schoolmanagement.schoolmanagementwebsite.ServiceImpl.fee;

import java.util.List;

import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import com.schoolmanagement.schoolmanagementwebsite.dto.fee.FeeMasterDto;
import com.schoolmanagement.schoolmanagementwebsite.entity.School;
import com.schoolmanagement.schoolmanagementwebsite.entity.fee.FeeMaster;
import com.schoolmanagement.schoolmanagementwebsite.repository.SchoolRepository;
import com.schoolmanagement.schoolmanagementwebsite.repository.fee.FeeMasterRepository;
import com.schoolmanagement.schoolmanagementwebsite.service.fee.FeeMasterService;

@Service
@Transactional
public class FeeMasterServiceImpl implements FeeMasterService {

    private final FeeMasterRepository repository;
    private final SchoolRepository schoolRepository;

    public FeeMasterServiceImpl(
            FeeMasterRepository repository,
            SchoolRepository schoolRepository) {

        this.repository = repository;
        this.schoolRepository = schoolRepository;
    }

    @Override
    public String save(Long schoolId, FeeMasterDto dto) {

        if (schoolId == null) {
            throw new RuntimeException("School ID is required");
        }

        if (dto == null) {
            throw new RuntimeException("Fee Master data is required");
        }

        School school = schoolRepository.findById(schoolId)
                .orElseThrow(() ->
                        new RuntimeException("School Not Found"));

        if (repository.findBySchool_IdAndFeeName(
                schoolId,
                dto.getFeeName()
        ).isPresent()) {

            return "Fee Type Already Exists";
        }

        if (repository.findBySchool_IdAndFeeCode(
                schoolId,
                dto.getFeeCode()
        ).isPresent()) {

            return "Fee Code Already Exists";
        }

        FeeMaster fee = new FeeMaster();

        fee.setSchool(school);
        fee.setFeeName(dto.getFeeName());
        fee.setStatus(dto.getStatus());
        fee.setFeeCode(dto.getFeeCode());
        fee.setFeeCategory(dto.getFeeCategory());

        repository.save(fee);

        return "Fee Type Saved Successfully";
    }

    @Override
    @Transactional(readOnly = true)
    public List<FeeMaster> getAll(Long schoolId) {

        if (schoolId == null) {
            throw new RuntimeException("School ID is required");
        }

        return repository.findBySchool_Id(schoolId);
    }

    @Override
    @Transactional(readOnly = true)
    public FeeMaster getById(Long schoolId, Long id) {

        if (schoolId == null) {
            throw new RuntimeException("School ID is required");
        }

        return repository.findByIdAndSchool_Id(id, schoolId)
                .orElseThrow(() ->
                        new RuntimeException("Fee Master Not Found"));
    }

    @Override
    public String update(
            Long schoolId,
            Long id,
            FeeMasterDto dto) {

        if (schoolId == null) {
            throw new RuntimeException("School ID is required");
        }

        FeeMaster fee = repository
                .findByIdAndSchool_Id(id, schoolId)
                .orElseThrow(() ->
                        new RuntimeException("Fee Master Not Found"));

        if (repository
                .findBySchool_IdAndFeeName(
                        schoolId,
                        dto.getFeeName())
                .filter(existing -> !existing.getId().equals(id))
                .isPresent()) {

            return "Fee Type Already Exists";
        }

        if (repository
                .findBySchool_IdAndFeeCode(
                        schoolId,
                        dto.getFeeCode())
                .filter(existing -> !existing.getId().equals(id))
                .isPresent()) {

            return "Fee Code Already Exists";
        }

        fee.setFeeName(dto.getFeeName());
        fee.setStatus(dto.getStatus());
        fee.setFeeCode(dto.getFeeCode());
        fee.setFeeCategory(dto.getFeeCategory());

        repository.save(fee);

        return "Updated Successfully";
    }

    @Override
    public String delete(Long schoolId, Long id) {

        FeeMaster fee = repository
                .findByIdAndSchool_Id(id, schoolId)
                .orElseThrow(() ->
                        new RuntimeException("Fee Master Not Found"));

        repository.delete(fee);

        return "Deleted Successfully";
    }
}