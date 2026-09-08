// package com.schoolmanagement.schoolmanagementwebsite.service;

// import java.util.List;

// import org.springframework.stereotype.Service;

// import com.schoolmanagement.schoolmanagementwebsite.dto.AssignSectionRequest;
// import com.schoolmanagement.schoolmanagementwebsite.entity.Student;
// import com.schoolmanagement.schoolmanagementwebsite.repository.StudentRepository;

// import lombok.RequiredArgsConstructor;
// import jakarta.transaction.Transactional;

// @Service
// @RequiredArgsConstructor
// public class SectionAssignService {

//     private final StudentRepository studentRepo;

//     @Transactional
//     public void assignSection(Long schoolId, AssignSectionRequest request) {

//         List<Student> students = studentRepo.findAllById(request.getStudentIds());

//         for (Student s : students) {
//             if (!s.getSchool().getId().equals(schoolId)) {
//                 throw new RuntimeException("Invalid student");
//             }
//             s.setSection(request.getSection());
//         }

//         studentRepo.saveAll(students);
//     }
// }


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

    // ===============================
    // GET STUDENTS FOR SECTION ASSIGN
    // ===============================
    public List<Student> getStudentsForAssignment(
            Long schoolId,
            String academicYear,
            String studentClass
    ) {

        return studentRepo
                .findBySchool_IdAndAcademicYearAndStudentClass(
                        schoolId,
                        academicYear,
                        studentClass
                );
    }

    // ===============================
    // ASSIGN SECTION
    // ===============================
    @Transactional
    public void assignSection(
            Long schoolId,
            AssignSectionRequest request
    ) {

        if (request.getStudentIds() == null ||
                request.getStudentIds().isEmpty()) {

            throw new RuntimeException(
                    "Please select at least one student"
            );
        }

        if (request.getSection() == null) {
            throw new RuntimeException(
                    "Please select a section"
            );
        }

        List<Student> students =
                studentRepo.findAllById(request.getStudentIds());

        if (students.size() != request.getStudentIds().size()) {
            throw new RuntimeException(
                    "One or more students not found"
            );
        }

        for (Student s : students) {

            if (s.getSchool() == null ||
                    !s.getSchool().getId().equals(schoolId)) {

                throw new RuntimeException(
                        "Invalid student for this school"
                );
            }

            s.setSection(request.getSection());
        }

        studentRepo.saveAll(students);
    }
}