// package com.schoolmanagement.schoolmanagementwebsite.entity;
// import java.time.LocalDateTime;
// import jakarta.persistence.Entity;
// import jakarta.persistence.GeneratedValue;
// import jakarta.persistence.GenerationType;
// import jakarta.persistence.Id;
// @Entity
// public class School {
//      @Id
//     @GeneratedValue(strategy = GenerationType.IDENTITY)
//     private Long id;
//     private String schoolName;
//     private String schoolCode;
//     private String schoolType;
//     private String registrationNumber;
//     private String establishedYear;
//     private String affiliationBoard;
//     private String academicYear;
//     private String gstNumber;
//     private String principalName;
//     private String contactPerson;
//     private String mobileNo;
//     private String aternateNo;
//     private String email;
//     private String website;
//     private String address;
//     private String city;
//     private String state;
//     private String country;
//     private String pincode;
//     private LocalDateTime createdAt;
//     // Single logo URL or path
//     // private String logoUrl;
//     private String status = "Active";  // Default Active
//     public Long getId() {
//         return id;
//     }
//     public void setId(Long id) {
//         this.id = id;
//     }
//     public String getSchoolName() {
//         return schoolName;
//     }
//     public void setSchoolName(String schoolName) {
//         this.schoolName = schoolName;
//     }
//     public String getSchoolCode() {
//         return schoolCode;
//     }
//     public void setSchoolCode(String schoolCode) {
//         this.schoolCode = schoolCode;
//     }
//     public String getAddress() {
//         return address;
//     }
//     public void setAddress(String address) {
//         this.address = address;
//     }
//     public String getEmail() {
//         return email;
//     }
//     public void setEmail(String email) {
//         this.email = email;
//     }
//     public String getPhone() {
//         return phone;
//     }
//     public void setPhone(String phone) {
//         this.phone = phone;
//     }
//     public String getStatus() {
//         return status;
//     }
//     public void setStatus(String status) {
//         this.status = status;
//     }
//     public String getYear() {
//         return year;
//     }
//     public void setYear(String year) {
//         this.year = year;
//     }
//     public String getPrincipal() {
//         return principal;
//     }
//     public void setPrincipal(String principal) {
//         this.principal = principal;
//     }
//     public String getAcademicYear() {
//         return academicYear;
//     }
//     public void setAcademicYear(String academicYear) {
//         this.academicYear = academicYear;
//     }
//     public LocalDateTime getCreatedAt() {
//         return createdAt;
//     }
//     public void setCreatedAt(LocalDateTime createdAt) {
//         this.createdAt = createdAt;
//     }
//     // public String getLogoUrl() {
//     //     return logoUrl;
//     // }
//     // public void setLogoUrl(String logoUrl) {
//     //     this.logoUrl = logoUrl;
//     // }
// }
package com.schoolmanagement.schoolmanagementwebsite.entity;

import java.time.LocalDate;
import java.time.LocalDateTime;

import jakarta.persistence.Entity;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;

@Entity
public class School {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

// =========================
// School Details
// =========================
    private String schoolName;
    private String schoolCode;
    private String schoolType;
    private String registrationNumber;
    private String affiliationBoard;
    private String establishedYear;
    private String academicYear;
    private String gstNumber;
    private String logoUrl;

// =========================
// Contact Details
// =========================
    private String principalName;
    private String contactPerson;
    private String mobileNo;
    private String alternateNo;
    private String email;
    private String website;
    private String address;
    private String city;
    private String state;
    private String country;
    private String pincode;

// =========================
// Subscription Details
// =========================
    private String subscriptionPlan;      // Basic / Standard / Premium
    private String subscriptionType;      // Monthly / Quarterly / Annually

    private LocalDate startDate;
    private LocalDate endDate;

    private LocalDate trialStartDate;
    private LocalDate trialEndDate;

    private LocalDate renewalDate;

    private String subscriptionStatus;    // Active, Expired, Trial
    private String paymentStatus;         // Paid, Pending

    private String invoiceNumber;

    private Double amount;

// =========================
// Resource Limits
// =========================
    private Integer maxStudents;
    private Integer maxTeachers;
    private Integer maxAdmins;

    private Integer storageLimit; // GB

    private Integer smsCredits;
    private Integer whatsappCredits;

// =========================
// System Fields
// =========================
    private String status = "Active";

    private LocalDateTime createdAt;

// =========================
// Getters & Setters
// =========================
    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public String getSchoolName() {
        return schoolName;
    }

    public void setSchoolName(String schoolName) {
        this.schoolName = schoolName;
    }

    public String getSchoolCode() {
        return schoolCode;
    }

    public void setSchoolCode(String schoolCode) {
        this.schoolCode = schoolCode;
    }

    public String getSchoolType() {
        return schoolType;
    }

    public void setSchoolType(String schoolType) {
        this.schoolType = schoolType;
    }

    public String getRegistrationNumber() {
        return registrationNumber;
    }

    public void setRegistrationNumber(String registrationNumber) {
        this.registrationNumber = registrationNumber;
    }

    public String getAffiliationBoard() {
        return affiliationBoard;
    }

    public void setAffiliationBoard(String affiliationBoard) {
        this.affiliationBoard = affiliationBoard;
    }

    public String getEstablishedYear() {
        return establishedYear;
    }

    public void setEstablishedYear(String establishedYear) {
        this.establishedYear = establishedYear;
    }

    public String getAcademicYear() {
        return academicYear;
    }

    public void setAcademicYear(String academicYear) {
        this.academicYear = academicYear;
    }

    public String getGstNumber() {
        return gstNumber;
    }

    public void setGstNumber(String gstNumber) {
        this.gstNumber = gstNumber;
    }

    public String getLogoUrl() {
        return logoUrl;
    }

    public void setLogoUrl(String logoUrl) {
        this.logoUrl = logoUrl;
    }

    public String getPrincipalName() {
        return principalName;
    }

    public void setPrincipalName(String principalName) {
        this.principalName = principalName;
    }

    public String getContactPerson() {
        return contactPerson;
    }

    public void setContactPerson(String contactPerson) {
        this.contactPerson = contactPerson;
    }

    public String getMobileNo() {
        return mobileNo;
    }

    public void setMobileNo(String mobileNo) {
        this.mobileNo = mobileNo;
    }

    public String getAlternateNo() {
        return alternateNo;
    }

    public void setAlternateNo(String alternateNo) {
        this.alternateNo = alternateNo;
    }

    public String getEmail() {
        return email;
    }

    public void setEmail(String email) {
        this.email = email;
    }

    public String getWebsite() {
        return website;
    }

    public void setWebsite(String website) {
        this.website = website;
    }

    public String getAddress() {
        return address;
    }

    public void setAddress(String address) {
        this.address = address;
    }

    public String getCity() {
        return city;
    }

    public void setCity(String city) {
        this.city = city;
    }

    public String getState() {
        return state;
    }

    public void setState(String state) {
        this.state = state;
    }

    public String getCountry() {
        return country;
    }

    public void setCountry(String country) {
        this.country = country;
    }

    public String getPincode() {
        return pincode;
    }

    public void setPincode(String pincode) {
        this.pincode = pincode;
    }

    public String getSubscriptionPlan() {
        return subscriptionPlan;
    }

    public void setSubscriptionPlan(String subscriptionPlan) {
        this.subscriptionPlan = subscriptionPlan;
    }

    public String getSubscriptionType() {
        return subscriptionType;
    }

    public void setSubscriptionType(String subscriptionType) {
        this.subscriptionType = subscriptionType;
    }

    public LocalDate getStartDate() {
        return startDate;
    }

    public void setStartDate(LocalDate startDate) {
        this.startDate = startDate;
    }

    public LocalDate getEndDate() {
        return endDate;
    }

    public void setEndDate(LocalDate endDate) {
        this.endDate = endDate;
    }

    public LocalDate getRenewalDate() {
        return renewalDate;
    }

    public void setRenewalDate(LocalDate renewalDate) {
        this.renewalDate = renewalDate;
    }

    public String getSubscriptionStatus() {
        return subscriptionStatus;
    }

    public void setSubscriptionStatus(String subscriptionStatus) {
        this.subscriptionStatus = subscriptionStatus;
    }

    public String getPaymentStatus() {
        return paymentStatus;
    }

    public void setPaymentStatus(String paymentStatus) {
        this.paymentStatus = paymentStatus;
    }

    public Double getAmount() {
        return amount;
    }

    public void setAmount(Double amount) {
        this.amount = amount;
    }

    public Integer getMaxStudents() {
        return maxStudents;
    }

    public void setMaxStudents(Integer maxStudents) {
        this.maxStudents = maxStudents;
    }

    public Integer getMaxTeachers() {
        return maxTeachers;
    }

    public void setMaxTeachers(Integer maxTeachers) {
        this.maxTeachers = maxTeachers;
    }

    public Integer getMaxAdmins() {
        return maxAdmins;
    }

    public void setMaxAdmins(Integer maxAdmins) {
        this.maxAdmins = maxAdmins;
    }

    public Integer getStorageLimit() {
        return storageLimit;
    }

    public void setStorageLimit(Integer storageLimit) {
        this.storageLimit = storageLimit;
    }

    public Integer getSmsCredits() {
        return smsCredits;
    }

    public void setSmsCredits(Integer smsCredits) {
        this.smsCredits = smsCredits;
    }

    public Integer getWhatsappCredits() {
        return whatsappCredits;
    }

    public void setWhatsappCredits(Integer whatsappCredits) {
        this.whatsappCredits = whatsappCredits;
    }

    public String getStatus() {
        return status;
    }

    public void setStatus(String status) {
        this.status = status;
    }

    public LocalDateTime getCreatedAt() {
        return createdAt;
    }

    public void setCreatedAt(LocalDateTime createdAt) {
        this.createdAt = createdAt;

    }


}
