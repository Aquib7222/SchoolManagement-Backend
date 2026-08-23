package com.schoolmanagement.schoolmanagementwebsite.repository.Assessment.Result;

import java.util.List;

import org.springframework.data.jpa.repository.JpaRepository;

import com.schoolmanagement.schoolmanagementwebsite.entity.Assessment.Result.ResultComponent;

public interface ResultComponentRepository
        extends JpaRepository<ResultComponent, Long> {

    List<ResultComponent> findByResultSubjectId(Long resultSubjectId);

    void deleteByResultSubjectId(Long resultSubjectId);
}