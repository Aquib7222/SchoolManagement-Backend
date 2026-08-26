package com.schoolmanagement.schoolmanagementwebsite.entity;


import java.time.LocalDate;

import jakarta.persistence.Entity;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.persistence.Table;
import lombok.Setter;
import lombok.Getter;
import lombok.NoArgsConstructor;

@Entity
@Table(name = "modules")
@Getter 
@Setter
@NoArgsConstructor
public class Module {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    private String moduleName;

    private String description;

    private String path;

    private Boolean hasMenu;

    private String status;

    private String image;

    private String moduleCode;

    private String sequenceNumber;

    private LocalDate createdOn;


}
    

