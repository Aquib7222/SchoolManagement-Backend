package com.schoolmanagement.schoolmanagementwebsite.ServiceImpl.fee;

import java.util.ArrayList;
import java.util.List;

import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import com.schoolmanagement.schoolmanagementwebsite.dto.fee.FeeStructureDetailsDto;
import com.schoolmanagement.schoolmanagementwebsite.dto.fee.FeeStructureDto;
import com.schoolmanagement.schoolmanagementwebsite.entity.School;
import com.schoolmanagement.schoolmanagementwebsite.entity.fee.FeeMaster;
import com.schoolmanagement.schoolmanagementwebsite.entity.fee.FeeStructure;
import com.schoolmanagement.schoolmanagementwebsite.entity.fee.FeeStructureDetails;
import com.schoolmanagement.schoolmanagementwebsite.repository.SchoolRepository;
import com.schoolmanagement.schoolmanagementwebsite.repository.fee.FeeMasterRepository;
import com.schoolmanagement.schoolmanagementwebsite.repository.fee.FeeStructureRepository;
import com.schoolmanagement.schoolmanagementwebsite.service.fee.FeeStructureService;

@Service
@Transactional
public class FeeStructureServiceImpl
        implements FeeStructureService {

    private final FeeStructureRepository repository;
    private final FeeMasterRepository feeMasterRepository;
    private final SchoolRepository schoolRepository;

    public FeeStructureServiceImpl(
            FeeStructureRepository repository,
            FeeMasterRepository feeMasterRepository,
            SchoolRepository schoolRepository) {

        this.repository = repository;
        this.feeMasterRepository = feeMasterRepository;
        this.schoolRepository = schoolRepository;
    }

    @Override
    public String save(
            Long schoolId,
            FeeStructureDto dto) {

        if (schoolId == null) {
            throw new RuntimeException("School ID is required");
        }

        if (dto == null) {
            throw new RuntimeException("Fee Structure data is required");
        }

        School school = schoolRepository.findById(schoolId)
                .orElseThrow(() ->
                        new RuntimeException("School Not Found"));

        FeeStructure feeStructure = new FeeStructure();

        feeStructure.setSchool(school);
        feeStructure.setSession(dto.getSession());
        feeStructure.setStandard(dto.getStandard());
        feeStructure.setFeeCategory(dto.getFeeCategory());
        feeStructure.setBatch(dto.getBatch());

        List<FeeStructureDetails> detailsList =
                new ArrayList<>();

        if (dto.getFees() == null || dto.getFees().isEmpty()) {
            throw new RuntimeException(
                    "At least one fee is required");
        }

        for (FeeStructureDetailsDto item : dto.getFees()) {

            FeeMaster feeMaster =
                    feeMasterRepository
                            .findByIdAndSchool_Id(
                                    item.getFeeMasterId(),
                                    schoolId
                            )
                            .orElseThrow(() ->
                                    new RuntimeException(
                                            "Fee Master Not Found for this School"
                                    ));

            FeeStructureDetails detail =
                    new FeeStructureDetails();

            detail.setFeeStructure(feeStructure);
            detail.setFeeMaster(feeMaster);
            detail.setAmount(item.getAmount());

            detailsList.add(detail);
        }

        feeStructure.setFeeDetails(detailsList);

        repository.save(feeStructure);

        return "Fee Structure Saved Successfully";
    }

    @Override
    @Transactional(readOnly = true)
    public List<FeeStructure> getAll(Long schoolId) {

        if (schoolId == null) {
            throw new RuntimeException("School ID is required");
        }

        return repository.findBySchool_Id(schoolId);
    }

    @Override
    @Transactional(readOnly = true)
    public FeeStructure getById(
            Long schoolId,
            Long id) {

        return repository
                .findByIdAndSchool_Id(id, schoolId)
                .orElseThrow(() ->
                        new RuntimeException(
                                "Fee Structure Not Found"));
    }

    @Override
    public String update(
            Long schoolId,
            Long id,
            FeeStructureDto dto) {

        FeeStructure feeStructure =
                repository
                        .findByIdAndSchool_Id(
                                id,
                                schoolId
                        )
                        .orElseThrow(() ->
                                new RuntimeException(
                                        "Fee Structure Not Found"));

        feeStructure.setSession(dto.getSession());
        feeStructure.setStandard(dto.getStandard());
        feeStructure.setFeeCategory(dto.getFeeCategory());
        feeStructure.setBatch(dto.getBatch());

        List<FeeStructureDetails> existingDetails =
                feeStructure.getFeeDetails();

        List<FeeStructureDetailsDto> newDetails =
                dto.getFees();

        if (newDetails == null || newDetails.isEmpty()) {
            throw new RuntimeException(
                    "At least one fee is required");
        }

        for (int i = 0; i < newDetails.size(); i++) {

            FeeStructureDetailsDto dtoDetail =
                    newDetails.get(i);

            FeeMaster feeMaster =
                    feeMasterRepository
                            .findByIdAndSchool_Id(
                                    dtoDetail.getFeeMasterId(),
                                    schoolId
                            )
                            .orElseThrow(() ->
                                    new RuntimeException(
                                            "Fee Master Not Found for this School"
                                    ));

            if (i < existingDetails.size()) {

                FeeStructureDetails detail =
                        existingDetails.get(i);

                detail.setFeeMaster(feeMaster);
                detail.setAmount(dtoDetail.getAmount());

            } else {

                FeeStructureDetails detail =
                        new FeeStructureDetails();

                detail.setFeeStructure(feeStructure);
                detail.setFeeMaster(feeMaster);
                detail.setAmount(dtoDetail.getAmount());

                existingDetails.add(detail);
            }
        }

        while (existingDetails.size()
                > newDetails.size()) {

            existingDetails.remove(
                    existingDetails.size() - 1
            );
        }

        repository.save(feeStructure);

        return "Fee Structure Updated Successfully";
    }

    @Override
    public String delete(
            Long schoolId,
            Long id) {

        FeeStructure feeStructure =
                repository
                        .findByIdAndSchool_Id(
                                id,
                                schoolId
                        )
                        .orElseThrow(() ->
                                new RuntimeException(
                                        "Fee Structure Not Found"));

        repository.delete(feeStructure);

        return "Fee Structure Deleted Successfully";
    }
}