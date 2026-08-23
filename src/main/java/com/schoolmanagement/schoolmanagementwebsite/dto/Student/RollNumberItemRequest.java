package com.schoolmanagement.schoolmanagementwebsite.dto.Student;

public class RollNumberItemRequest {

    private String admissionNumber;

    private Integer rollNumber;

    public RollNumberItemRequest() {
    }

    public String getAdmissionNumber() {
        return admissionNumber;
    }

    public void setAdmissionNumber(
            String admissionNumber) {
        this.admissionNumber = admissionNumber;
    }

    public Integer getRollNumber() {
        return rollNumber;
    }

    public void setRollNumber(
            Integer rollNumber) {
        this.rollNumber = rollNumber;
    }
}