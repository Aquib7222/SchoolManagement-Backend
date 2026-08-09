package com.schoolmanagement.schoolmanagementwebsite.dto;
import java.util.List;
import com.schoolmanagement.schoolmanagementwebsite.enums.Section;

public class SectionShufflingDTO {
    
    private Long schoolId;
    private List<String> admissionNumber;
    private Section section;
    public Long getSchoolId() {
        return schoolId;
    }
    public void setSchoolId(Long schoolId) {
        this.schoolId = schoolId;
    }
    public List<String> getAdmissionNumber() {
        return admissionNumber;
    }
    public void setAdmissionNumber(List<String> admissionNumber) {
        this.admissionNumber = admissionNumber;
    }
    public Section getSection() {
        return section;
    }
    public void setSection(Section section) {
        this.section = section;
    }


}
