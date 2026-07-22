// package com.schoolmanagement.schoolmanagementwebsite.service;

// import java.time.LocalDateTime;
// import java.util.List;

// import org.springframework.beans.factory.annotation.Autowired;
// import org.springframework.stereotype.Service;

// import com.schoolmanagement.schoolmanagementwebsite.entity.School;
// import com.schoolmanagement.schoolmanagementwebsite.repository.SchoolRepository;

// @Service
// public class SchoolService {

//     @Autowired
//     private SchoolRepository repo;

//     public School addSchool(School school) {
//         school.setStatus("Active");
//         school.setCreatedAt(LocalDateTime.now());
//          // Set default logo if none provided
    
//         return repo.save(school);
//     }

//     public List<School> getAll() {
//         return repo.findAll();
//     }

//     public void deleteSchool(Long id) {

//     School school = repo.findById(id)
//             .orElseThrow(() -> new RuntimeException("School not found"));

//     // Soft delete
//     school.setStatus("Inactive");
//     repo.save(school);
// }
// public School getSchoolById(Long id) {
//     return repo.findById(id)
//             .orElseThrow(() -> new RuntimeException("School not found with id " + id));
// }



//     public School toggleStatus(Long id) {
//         School s = repo.findById(id).orElseThrow();
//         s.setStatus(s.getStatus().equals("Active") ? "Inactive" : "Active");
//         return repo.save(s);
//     }
// }

package com.schoolmanagement.schoolmanagementwebsite.service;

import java.time.LocalDate;
import java.time.LocalDateTime;
import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.schoolmanagement.schoolmanagementwebsite.entity.School;
import com.schoolmanagement.schoolmanagementwebsite.repository.SchoolRepository;

@Service
public class SchoolService {

@Autowired
private SchoolRepository repo;

public School addSchool(School school) {

    school.setStatus("Active");
    school.setCreatedAt(LocalDateTime.now());

    applySubscriptionDetails(school);

    return repo.save(school);
}

public List<School> getAll() {
    return repo.findAll();
}

public School getSchoolById(Long id) {
    return repo.findById(id)
            .orElseThrow(() ->
                    new RuntimeException("School not found with id " + id));
}

public School updateSchool(Long id, School updatedSchool) {

    School school = repo.findById(id)
            .orElseThrow(() ->
                    new RuntimeException("School not found"));

    school.setSchoolName(updatedSchool.getSchoolName());
    school.setSchoolCode(updatedSchool.getSchoolCode());
    school.setSchoolType(updatedSchool.getSchoolType());
    school.setRegistrationNumber(updatedSchool.getRegistrationNumber());
    school.setAffiliationBoard(updatedSchool.getAffiliationBoard());
    school.setEstablishedYear(updatedSchool.getEstablishedYear());
    school.setAcademicYear(updatedSchool.getAcademicYear());

    school.setPrincipalName(updatedSchool.getPrincipalName());
    school.setContactPerson(updatedSchool.getContactPerson());
    school.setMobileNo(updatedSchool.getMobileNo());
    school.setAlternateNo(updatedSchool.getAlternateNo());
    school.setEmail(updatedSchool.getEmail());

    school.setAddress(updatedSchool.getAddress());
    school.setCity(updatedSchool.getCity());
    school.setState(updatedSchool.getState());
    school.setCountry(updatedSchool.getCountry());
    school.setPincode(updatedSchool.getPincode());

    school.setSubscriptionPlan(updatedSchool.getSubscriptionPlan());
    school.setSubscriptionType(updatedSchool.getSubscriptionType());
    school.setStartDate(updatedSchool.getStartDate());

    applySubscriptionDetails(school);

    return repo.save(school);
}

public void deleteSchool(Long id) {

    School school = repo.findById(id)
            .orElseThrow(() ->
                    new RuntimeException("School not found"));

    school.setStatus("Inactive");

    repo.save(school);
}

public School toggleStatus(Long id) {

    School school = repo.findById(id)
            .orElseThrow(() ->
                    new RuntimeException("School not found"));

    school.setStatus(
            school.getStatus().equalsIgnoreCase("Active")
                    ? "Inactive"
                    : "Active");

    return repo.save(school);
}

private void applySubscriptionDetails(School school) {

    String plan = school.getSubscriptionPlan();
    String type = school.getSubscriptionType();

    double amount = 0;

    if ("Basic".equalsIgnoreCase(plan)) {

        school.setMaxStudents(500);
        school.setMaxTeachers(20);
        school.setMaxAdmins(2);
        school.setStorageLimit(10);
        school.setSmsCredits(1000);
        school.setWhatsappCredits(500);

        if ("Monthly".equalsIgnoreCase(type))
            amount = 2000;
        else if ("Quarterly".equalsIgnoreCase(type))
            amount = 6000;
        else if ("Annually".equalsIgnoreCase(type))
            amount = 24000;

    } else if ("Standard".equalsIgnoreCase(plan)) {

        school.setMaxStudents(1500);
        school.setMaxTeachers(75);
        school.setMaxAdmins(5);
        school.setStorageLimit(50);
        school.setSmsCredits(5000);
        school.setWhatsappCredits(2500);

        if ("Monthly".equalsIgnoreCase(type))
            amount = 3500;
        else if ("Quarterly".equalsIgnoreCase(type))
            amount = 10500;
        else if ("Annually".equalsIgnoreCase(type))
            amount = 42000;

    } else if ("Premium".equalsIgnoreCase(plan)) {

        school.setMaxStudents(5000);
        school.setMaxTeachers(200);
        school.setMaxAdmins(10);
        school.setStorageLimit(200);
        school.setSmsCredits(20000);
        school.setWhatsappCredits(10000);

        if ("Monthly".equalsIgnoreCase(type))
            amount = 5000;
        else if ("Quarterly".equalsIgnoreCase(type))
            amount = 15000;
        else if ("Annually".equalsIgnoreCase(type))
            amount = 60000;
    }

    school.setAmount(amount);

    if (school.getStartDate() != null) {

        LocalDate endDate = school.getStartDate();

        if ("Monthly".equalsIgnoreCase(type)) {
            endDate = endDate.plusMonths(1);
        } else if ("Quarterly".equalsIgnoreCase(type)) {
            endDate = endDate.plusMonths(3);
        } else if ("Annually".equalsIgnoreCase(type)) {
            endDate = endDate.plusYears(1);
        }

        school.setEndDate(endDate);
        school.setRenewalDate(endDate);
    }

    if (school.getSubscriptionStatus() == null) {
        school.setSubscriptionStatus("Active");
    }

    if (school.getPaymentStatus() == null) {
        school.setPaymentStatus("Pending");
    }
}


}
