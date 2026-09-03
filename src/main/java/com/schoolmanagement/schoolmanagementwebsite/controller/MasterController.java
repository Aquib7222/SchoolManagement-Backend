package com.schoolmanagement.schoolmanagementwebsite.controller;

import java.util.List;

import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.schoolmanagement.schoolmanagementwebsite.dto.Assessment.AssessmentNatureResponse;

import com.schoolmanagement.schoolmanagementwebsite.enums.AttendanceStatus;
import com.schoolmanagement.schoolmanagementwebsite.enums.FeeBatch;
import com.schoolmanagement.schoolmanagementwebsite.enums.FeeCategory;
import com.schoolmanagement.schoolmanagementwebsite.enums.Month;

import com.schoolmanagement.schoolmanagementwebsite.enums.Standard;
import com.schoolmanagement.schoolmanagementwebsite.enums.Section;
import com.schoolmanagement.schoolmanagementwebsite.enums.Sessions;
import com.schoolmanagement.schoolmanagementwebsite.enums.TeacherCategory;
import com.schoolmanagement.schoolmanagementwebsite.enums.TeacherDepartment;
import com.schoolmanagement.schoolmanagementwebsite.enums.TeacherDesignation;
import com.schoolmanagement.schoolmanagementwebsite.enums.Assessment.AssessmentNature;
import com.schoolmanagement.schoolmanagementwebsite.enums.Assessment.ExamTermType;
import com.schoolmanagement.schoolmanagementwebsite.enums.Assessment.SubjectType;
import com.schoolmanagement.schoolmanagementwebsite.enums.Enquiry.EnquiryStatus;
import com.schoolmanagement.schoolmanagementwebsite.enums.School.AffiliationBoard;
import com.schoolmanagement.schoolmanagementwebsite.enums.School.SchoolCategory;
import com.schoolmanagement.schoolmanagementwebsite.enums.School.SchoolType;
import com.schoolmanagement.schoolmanagementwebsite.enums.Assessment.SubjectCategory;
import com.schoolmanagement.schoolmanagementwebsite.service.Assessment.AssessmentNatureService;

@RestController
@RequestMapping("/api/master")
@CrossOrigin(origins = "http://localhost:5173")
public class MasterController {

    private final AssessmentNatureService assessmentService;

    public MasterController(AssessmentNatureService assessmentService) {
        this.assessmentService = assessmentService;
    }

    @GetMapping("/fee-category")
    public FeeCategory[] getFeeCategory() {
        return FeeCategory.values();
    }

    @GetMapping("/fee-batch")
    public FeeBatch[] getFeeBatch() {
        return FeeBatch.values();
    }

    @GetMapping("/standard")
    public Standard[] getStandard() {
        return Standard.values();
    }

    @GetMapping("/section")
    public Section[] getSection() {
        return Section.values();
    }

    @GetMapping("/sessions")
    public Sessions[] getSessions() {
        return Sessions.values();
    }

    @GetMapping("/teacherDesignation")
    public TeacherDesignation[] getTeacherDesignation() {
        return TeacherDesignation.values();
    }

    @GetMapping("/teacherDepartment")
    public TeacherDepartment[] getTeacherDepartment() {
        return TeacherDepartment.values();
    }

    @GetMapping("/teacherCategory")
    public TeacherCategory[] getTeacherCategory() {
        return TeacherCategory.values();
    }

    @GetMapping("/attendanceStatus")
    public AttendanceStatus[] getAttendanceStatus() {
        return AttendanceStatus.values();
    }

    @GetMapping("/month")
    public Month[] getMonth() {
        return Month.values();
    }

    @GetMapping("/assessment/nature")
    public List<AssessmentNatureResponse> getNature() {
        return assessmentService.getAllNature();
    }
    @GetMapping("/exam-type")
    public ExamTermType[] getExamTermType(){
        return ExamTermType.values();
    }

    @GetMapping("/subject-type")
    public SubjectType[] getSubjectType(){
        return SubjectType.values();
    }

    @GetMapping("/subject-category")
    public SubjectCategory[] getSubjectCategory(){
        return SubjectCategory.values();
    }

    @GetMapping("/school-type")
    public SchoolType[] getSchoolType(){
        return SchoolType.values();
    }

    @GetMapping("/school-category")
    public SchoolCategory[] getSchoolCategory(){
        return SchoolCategory.values();
    }

    @GetMapping("/affiliation-board")
    public AffiliationBoard[] getAffiliationBoard(){
        return AffiliationBoard.values();
    }


    @GetMapping("/enquiry-status")
    public EnquiryStatus[] getEnquiryStatus(){
        return EnquiryStatus.values();
    }

}
