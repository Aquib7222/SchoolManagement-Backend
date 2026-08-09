package com.schoolmanagement.schoolmanagementwebsite.service.Assessment;

import java.util.Arrays;

import org.springframework.stereotype.Service;

import com.schoolmanagement.schoolmanagementwebsite.dto.Assessment.AssessmentNatureResponse;
import com.schoolmanagement.schoolmanagementwebsite.enums.Assessment.AssessmentNature;

import java.util.List;


@Service
public class AssessmentNatureService {
    
    public List<AssessmentNatureResponse> getAllNature() {

    return Arrays.stream(AssessmentNature.values())
            .map(nature -> new AssessmentNatureResponse(
                    nature.name(),
                    
                    nature.getShortCode(),
                    nature.getDescription(),
                    nature.getStatus()
            ))
            .toList();
}
}
