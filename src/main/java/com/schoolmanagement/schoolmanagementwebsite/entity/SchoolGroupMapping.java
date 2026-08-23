package com.schoolmanagement.schoolmanagementwebsite.entity;

import java.util.ArrayList;
import java.util.List;

import com.fasterxml.jackson.annotation.JsonManagedReference;

import jakarta.persistence.*;

@Entity
@Table(
        name = "school_group_mapping",
        uniqueConstraints = {
            @UniqueConstraint(columnNames = {"school_id", "user_group_id"})
        }
)
public class SchoolGroupMapping {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "school_id")
    private School school;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "user_group_id")
    private UserGroup userGroup;

    @OneToMany(
        mappedBy = "schoolGroupMapping",
        cascade = CascadeType.ALL,
        orphanRemoval = true,
        fetch = FetchType.LAZY
)
@JsonManagedReference
private List<SchoolModuleMapping> moduleMappings =
        new ArrayList<>();

    

    public SchoolGroupMapping() {
    }

    public Long getId() {
        return id;
    }

    public School getSchool() {
        return school;
    }

    public void setSchool(School school) {
        this.school = school;
    }

    public UserGroup getUserGroup() {
        return userGroup;
    }

    public void setUserGroup(UserGroup userGroup) {
        this.userGroup = userGroup;
    }

    public List<SchoolModuleMapping> getModuleMappings() {
        return moduleMappings;
 
   }

    public void setModuleMappings(List<SchoolModuleMapping> moduleMappings) {
        this.moduleMappings = moduleMappings;
    }
}