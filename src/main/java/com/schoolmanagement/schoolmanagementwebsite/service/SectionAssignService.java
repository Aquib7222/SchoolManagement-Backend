package com.schoolmanagement.schoolmanagementwebsite.service;

import java.util.List;

import org.springframework.stereotype.Service;

import com.schoolmanagement.schoolmanagementwebsite.dto.AssignSectionRequest;
import com.schoolmanagement.schoolmanagementwebsite.entity.Student;
import com.schoolmanagement.schoolmanagementwebsite.repository.StudentRepository;

import lombok.RequiredArgsConstructor;
import jakarta.transaction.Transactional;

@Service
@RequiredArgsConstructor
public class SectionAssignService {

    private final StudentRepository studentRepo;

    @Transactional
    public void assignSection(Long schoolId, AssignSectionRequest request) {

        List<Student> students = studentRepo.findAllById(request.getStudentIds());

        for (Student s : students) {
            if (!s.getSchool().getId().equals(schoolId)) {
                throw new RuntimeException("Invalid student");
            }
            s.setSection(request.getSection());
        }

        studentRepo.saveAll(students);
    }
}

