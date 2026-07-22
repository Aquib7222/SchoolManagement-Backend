package com.schoolmanagement.schoolmanagementwebsite.ServiceImpl.fee;

import java.util.ArrayList;
import java.util.List;

import org.springframework.stereotype.Service;

import com.schoolmanagement.schoolmanagementwebsite.dto.fee.FeeStructureDetailsDto;
import com.schoolmanagement.schoolmanagementwebsite.dto.fee.FeeStructureDto;
import com.schoolmanagement.schoolmanagementwebsite.entity.fee.FeeMaster;
import com.schoolmanagement.schoolmanagementwebsite.entity.fee.FeeStructure;
import com.schoolmanagement.schoolmanagementwebsite.entity.fee.FeeStructureDetails;
import com.schoolmanagement.schoolmanagementwebsite.repository.fee.FeeMasterRepository;
import com.schoolmanagement.schoolmanagementwebsite.repository.fee.FeeStructureRepository;
import com.schoolmanagement.schoolmanagementwebsite.service.fee.FeeStructureService;

@Service
public class FeeStructureServiceImpl implements FeeStructureService {

    private final FeeStructureRepository repository;
    private final FeeMasterRepository feeMasterRepository;

    public FeeStructureServiceImpl(
            FeeStructureRepository repository,
            FeeMasterRepository feeMasterRepository) {

        this.repository = repository;
        this.feeMasterRepository = feeMasterRepository;
    }

    @Override
    public String save(FeeStructureDto dto) {

        FeeStructure feeStructure = new FeeStructure();

        feeStructure.setSession(dto.getSession());
        feeStructure.setStandard(dto.getStandard());
        feeStructure.setFeeCategory(dto.getFeeCategory());
        feeStructure.setBatch(dto.getBatch());

        List<FeeStructureDetails> detailsList = new ArrayList<>();

        for (FeeStructureDetailsDto item : dto.getFees()) {

            FeeMaster feeMaster = feeMasterRepository
                    .findById(item.getFeeMasterId())
                    .orElseThrow(() -> new RuntimeException("Fee Master Not Found"));

            FeeStructureDetails detail = new FeeStructureDetails();

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
    public List<FeeStructure> getAll() {
        return repository.findAll();
    }

    @Override
    public FeeStructure getById(Long id) {

        return repository.findById(id)
                .orElseThrow(() -> new RuntimeException("Fee Structure Not Found"));
    }

    @Override
public String update(Long id, FeeStructureDto dto) {

    FeeStructure feeStructure = repository.findById(id)
            .orElseThrow(() -> new RuntimeException("Fee Structure Not Found"));

    feeStructure.setSession(dto.getSession());
    feeStructure.setStandard(dto.getStandard());
    feeStructure.setFeeCategory(dto.getFeeCategory());
    feeStructure.setBatch(dto.getBatch());

    List<FeeStructureDetails> existingDetails = feeStructure.getFeeDetails();
    List<FeeStructureDetailsDto> newDetails = dto.getFees();

    // Existing rows update
    for (int i = 0; i < newDetails.size(); i++) {

        FeeStructureDetailsDto dtoDetail = newDetails.get(i);

        FeeMaster feeMaster = feeMasterRepository.findById(dtoDetail.getFeeMasterId())
                .orElseThrow(() -> new RuntimeException("Fee Master Not Found"));

        if (i < existingDetails.size()) {

            // Update existing detail
            FeeStructureDetails detail = existingDetails.get(i);
            detail.setFeeMaster(feeMaster);
            detail.setAmount(dtoDetail.getAmount());

        } else {

            // Add new detail only if required
            FeeStructureDetails detail = new FeeStructureDetails();
            detail.setFeeStructure(feeStructure);
            detail.setFeeMaster(feeMaster);
            detail.setAmount(dtoDetail.getAmount());

            existingDetails.add(detail);
        }
    }

    // Remove extra details if user deleted any
    while (existingDetails.size() > newDetails.size()) {
        existingDetails.remove(existingDetails.size() - 1);
    }

    repository.save(feeStructure);

    return "Fee Structure Updated Successfully";
}

   

    @Override
    public String delete(Long id) {

        repository.deleteById(id);

        return "Fee Structure Deleted Successfully";
    }
}