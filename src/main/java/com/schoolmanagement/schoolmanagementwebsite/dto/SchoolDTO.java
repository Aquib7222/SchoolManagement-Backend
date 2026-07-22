// package com.schoolmanagement.schoolmanagementwebsite.dto;

// public class SchoolDTO {

//     private Long id;
//     private String schoolName;
//     private String schoolCode;
//     private String email;
//     private String phone;
//     private String address;
//     private String status;

//     // getters & setters

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

//     public String getAddress() {
//         return address;
//     }

//     public void setAddress(String address) {
//         this.address = address;
//     }

//     public String getStatus() {
//         return status;
//     }

//     public void setStatus(String status) {
//         this.status = status;
//     }
// }

package com.schoolmanagement.schoolmanagementwebsite.dto;

import java.time.LocalDate;
import java.time.LocalDateTime;

public class SchoolDTO {


private Long id;

// School Details
private String schoolName;
private String schoolCode;
private String schoolType;
private String registrationNumber;
private String affiliationBoard;
private String establishedYear;
private String academicYear;
private String gstNumber;
private String logoUrl;

// Contact Details
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

// Subscription Details
private String subscriptionPlan;
private String subscriptionType;

private LocalDate startDate;
private LocalDate endDate;

private LocalDate trialStartDate;
private LocalDate trialEndDate;

private LocalDate renewalDate;

private String subscriptionStatus;
private String paymentStatus;

private String invoiceNumber;

private Double amount;

// Limits
private Integer maxStudents;
private Integer maxTeachers;
private Integer maxAdmins;

private Integer storageLimit;
private Integer smsCredits;
private Integer whatsappCredits;

// System
private String status;
private LocalDateTime createdAt;

// =========================
// Getters and Setters
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

public LocalDate getTrialStartDate() {
    return trialStartDate;
}

public void setTrialStartDate(LocalDate trialStartDate) {
    this.trialStartDate = trialStartDate;
}

public LocalDate getTrialEndDate() {
    return trialEndDate;
}

public void setTrialEndDate(LocalDate trialEndDate) {
    this.trialEndDate = trialEndDate;
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

public String getInvoiceNumber() {
    return invoiceNumber;
}

public void setInvoiceNumber(String invoiceNumber) {
    this.invoiceNumber = invoiceNumber;
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
