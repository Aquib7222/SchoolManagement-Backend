package com.schoolmanagement.schoolmanagementwebsite.service.fee;

import java.time.LocalDate;
import java.util.ArrayList;
import java.util.List;

import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import com.schoolmanagement.schoolmanagementwebsite.dto.fee.AssignFeeRequest;
import com.schoolmanagement.schoolmanagementwebsite.entity.Student;
import com.schoolmanagement.schoolmanagementwebsite.entity.User;
import com.schoolmanagement.schoolmanagementwebsite.entity.fee.FeeStructureDetails;
import com.schoolmanagement.schoolmanagementwebsite.entity.fee.StudentFee;
import com.schoolmanagement.schoolmanagementwebsite.repository.StudentRepository;
import com.schoolmanagement.schoolmanagementwebsite.repository.UserRepository;
import com.schoolmanagement.schoolmanagementwebsite.repository.fee.FeeStructureDetailsRepository;
import com.schoolmanagement.schoolmanagementwebsite.repository.fee.StudentFeeRepository;

import lombok.RequiredArgsConstructor;

@Service
@RequiredArgsConstructor
public class StudentFeeService {

    private final StudentRepository studentRepository;

    private final FeeStructureDetailsRepository feeStructureDetailsRepository;

    private final StudentFeeRepository studentFeeRepository;

    private final UserRepository userRepository;

    @Transactional
    public void assignFee(AssignFeeRequest request) {

        List<Student> students
                = studentRepository.findAllById(request.getStudentIds());

        List<FeeStructureDetails> feeDetails
                = feeStructureDetailsRepository.findAllById(request.getFeeStructureIds());

        List<StudentFee> saveList = new ArrayList<>();

        for (Student student : students) {

            for (FeeStructureDetails detail : feeDetails) {

                // Duplicate Check
                boolean exists
                        = studentFeeRepository.existsByStudentIdAndFeeStructureIdAndFeeMasterId(
                                student.getId(),
                                detail.getFeeStructure().getId(),
                                detail.getFeeMaster().getId());

                if (exists) {
                    continue;
                }

                StudentFee fee = new StudentFee();

                // ===================================
                // Student Details
                // ===================================
                fee.setSchoolId(student.getSchool().getId());

                fee.setStudentId(student.getId());

                fee.setAdmissionNumber(student.getAdmissionNumber());

                fee.setStudentName(
                        student.getFirstName() + " " + student.getLastName());

                fee.setStudentClass(student.getStudentClass());

                // fee.setSection(student.getSection());
                fee.setMobileNumber(student.getMobile());

                // ===================================
                // Fee Structure Details
                // ===================================
                fee.setFeeStructureId(detail.getFeeStructure().getId());

                fee.setFeeMasterId(detail.getFeeMaster().getId());

                fee.setFeeCode(detail.getFeeMaster().getFeeCode());

                fee.setFeeName(detail.getFeeMaster().getFeeName());

                fee.setFeeCategory(detail.getFeeStructure().getFeeCategory());

                fee.setFeeBatch(detail.getFeeStructure().getBatch());

                fee.setSession(detail.getFeeStructure().getSession());

                fee.setFeeStructureDetailId(detail.getId());

                // ===================================
                // Amount
                // ===================================
                fee.setAmount(detail.getAmount());

                fee.setPaidAmount(0.0);

                fee.setDueAmount(detail.getAmount());

                // ===================================
                // Status
                // ===================================
                fee.setStatus("UNPAID");

                fee.setAssignDate(LocalDate.now());

                saveList.add(fee);
            }
        }

        System.out.println("Student IDs : " + request.getStudentIds());

        System.out.println("Students : " + students.size());

        // System.out.println("Fee Structures : " + feeStructures.size());
        System.out.println("Save List : " + saveList.size());

        System.out.println("Saved Successfully");

    }

    public List<StudentFee> getStudentFee(String admissionNumber) {

        return studentFeeRepository.findByAdmissionNumber(admissionNumber);

    }

    public List<StudentFee> getStudentFee(
            String email,
            String admissionNumber
    ) {

        User user = userRepository.findByEmail(email);

        if (user == null || user.getSchool() == null) {
            throw new RuntimeException("School not found");
        }

        return studentFeeRepository.findBySchoolIdAndAdmissionNumber(
                user.getSchool().getId(),
                admissionNumber
        );

    }

    public List<StudentFee> getAllStudentFees(String email) {

        User user = userRepository.findByEmail(email);

        return studentFeeRepository.findBySchoolId(
                user.getSchool().getId()
        );
    }

}
