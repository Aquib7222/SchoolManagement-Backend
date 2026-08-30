package com.schoolmanagement.schoolmanagementwebsite.entity;

import java.time.LocalDate;
import java.util.*;

import com.fasterxml.jackson.annotation.JsonBackReference;
import com.schoolmanagement.schoolmanagementwebsite.enums.Section;
import com.schoolmanagement.schoolmanagementwebsite.enums.StudentStatus;

import jakarta.persistence.*;
import lombok.*;

@Entity
@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
@Builder
@Table(name = "students")
public class Student {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    private String admissionNumber;

    private String firstName;
    private String middleName;
    private String lastName;

    private String dob;
    private String gender;
    private String age;

    private String studentClass;
    

   private String nationality;
    private String motherTongue;
    private String religion;
    private String category;
    private String caste;
    private String bloodGroup;

    private String transportRequired;
    private String email;
    private String mobile;
    

    private String feeCategory;
    private String feeBatch;
// Father
    private String fatherName;
    private String fatherMobile;
    private String fatherEmail;
    private String fatherOccupation;

    // Mother
    private String motherName;
    private String motherMobile;
    private String motherEmail;
    private String motherOccupation;

    // Address
    private String houseNo;
    private String street;
    private String area;
    private String town;
    private String city;
    private String state;
    private String country;
    private String zip;

    private String academicYear;

    private LocalDate discontinueDate;

    @Column(name = "photo")
private String photo;

    @ManyToOne
    @JoinColumn(name = "school_id")
    private School school;

    // 🔗 Admission mapping
    @JsonBackReference
    @OneToOne
    @JoinColumn(name = "admission_id")
    private Admission admission;


    @Enumerated(EnumType.STRING)
    private StudentStatus status; // ✅ HERE

     @Enumerated(EnumType.STRING)
    private Section section;   

    @Column(name = "roll_number")
private Integer rollNumber;

// @OneToMany(
//     mappedBy = "student",
//     cascade = CascadeType.ALL,
//     orphanRemoval = false
// )
// private List<StudentEnrollment> enrollments = new ArrayList<>();

    

}
