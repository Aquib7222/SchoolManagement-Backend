package com.schoolmanagement.schoolmanagementwebsite.repository.Assessment.Result;

import java.util.List;

import org.springframework.data.jpa.repository.JpaRepository;

import com.schoolmanagement.schoolmanagementwebsite.entity.Assessment.Result.ResultSubject;

public interface ResultSubjectRepository
        extends JpaRepository<ResultSubject, Long> {

    List<ResultSubject>
    findByResultId(Long resultId);

    void deleteByResultId(Long resultId);
}