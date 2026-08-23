package com.schoolmanagement.schoolmanagementwebsite.service.Assessment.Result;

import java.util.List;

import org.springframework.stereotype.Service;

import com.schoolmanagement.schoolmanagementwebsite.dto.Assessment.Result.ResultComponentResponse;
import com.schoolmanagement.schoolmanagementwebsite.entity.Assessment.Result.ResultComponent;
import com.schoolmanagement.schoolmanagementwebsite.repository.Assessment.Result.ResultComponentRepository;

import lombok.RequiredArgsConstructor;

@Service
@RequiredArgsConstructor
public class ResultComponentService {

    private final ResultComponentRepository repository;

    public List<ResultComponentResponse> getByResultSubject(
            Long resultSubjectId) {

        return repository
                .findByResultSubjectId(resultSubjectId)
                .stream()
                .map(this::mapToResponse)
                .toList();
    }

    private ResultComponentResponse mapToResponse(
            ResultComponent component) {

        return new ResultComponentResponse(
                component.getId(),
                component.getComponentId(),
                component.getComponentName(),
                component.getMaxMarks(),
                component.getObtainedMarks(),
                component.getPercentage(),
                component.getGrade(),
                component.getGradePoint(),
                component.getStatus()
        );
    }
}