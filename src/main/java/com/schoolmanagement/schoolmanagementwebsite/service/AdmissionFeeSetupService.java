package com.schoolmanagement.schoolmanagementwebsite.service;

import org.springframework.stereotype.Service;

import com.schoolmanagement.schoolmanagementwebsite.entity.AdmissionFeeSetup;
import com.schoolmanagement.schoolmanagementwebsite.entity.School;
import com.schoolmanagement.schoolmanagementwebsite.repository.AdmissionFeeSetupRepository;
import com.schoolmanagement.schoolmanagementwebsite.repository.SchoolRepository;

import lombok.RequiredArgsConstructor;

@Service
@RequiredArgsConstructor
public class AdmissionFeeSetupService {

    private final AdmissionFeeSetupRepository feeRepo;
    private final SchoolRepository schoolRepo;

    public AdmissionFeeSetup saveOrUpdate(Long schoolId, AdmissionFeeSetup data) {

        School school = schoolRepo.findById(schoolId)
                .orElseThrow(() -> new RuntimeException("School not found"));

        return feeRepo
                .findBySchool_IdAndSessionAndStandard(
                        schoolId, data.getSession(), data.getStandard()
                )
                .map(existing -> {
                    // UPDATE
                    existing.setAnnualCharges(data.getAnnualCharges());
                    existing.setExamCharges(data.getExamCharges());
                    existing.setTuitionFee(data.getTuitionFee());
                    existing.setSportsFee(data.getSportsFee());
                    existing.setPhotoCardFee(data.getPhotoCardFee());
                    existing.setLibraryLabFee(data.getLibraryLabFee());
                    existing.setTransportFee(data.getTransportFee());
                    existing.setMiscCharges(data.getMiscCharges());
                    existing.setRegistrationFee(data.getRegistrationFee());
                    existing.setSecurityMoney(data.getSecurityMoney());
                    return feeRepo.save(existing);
                })
                .orElseGet(() -> {
                    // CREATE
                    data.setSchool(school);
                    return feeRepo.save(data);
                });
    }

    public AdmissionFeeSetup getFee(Long schoolId, String session, String standard) {
        return feeRepo.findBySchool_IdAndSessionAndStandard(
                schoolId, session, standard
        ).orElse(null);
    }
}
