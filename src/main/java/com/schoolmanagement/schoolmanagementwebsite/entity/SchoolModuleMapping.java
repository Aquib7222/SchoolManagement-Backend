package com.schoolmanagement.schoolmanagementwebsite.entity;

import java.util.ArrayList;
import java.util.List;

import com.fasterxml.jackson.annotation.JsonBackReference;
import com.fasterxml.jackson.annotation.JsonManagedReference;

import jakarta.persistence.*;

@Entity
@Table(
    name = "school_module_mapping",
    uniqueConstraints = {
        @UniqueConstraint(columnNames = {"school_group_mapping_id", "module_id"})
    }
)
public class SchoolModuleMapping {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @ManyToOne
    @JoinColumn(name="school_group_mapping_id")
    @JsonBackReference
    private SchoolGroupMapping schoolGroupMapping;

    @ManyToOne
    @JoinColumn(name="module_id")
    private Module module;

    @OneToMany(
            mappedBy="schoolModuleMapping",
            cascade=CascadeType.ALL,
            orphanRemoval=true
    )
    @JsonManagedReference
    private List<SchoolMenuMapping> menuMappings = new ArrayList<>();

    public SchoolModuleMapping() {
    }

    public Long getId() {
        return id;
    }

    public SchoolGroupMapping getSchoolGroupMapping() {
        return schoolGroupMapping;
    }

    public void setSchoolGroupMapping(SchoolGroupMapping schoolGroupMapping) {
        this.schoolGroupMapping = schoolGroupMapping;
    }

    public Module getModule() {
        return module;
    }

    public void setModule(Module module) {
        this.module = module;
    }

    public List<SchoolMenuMapping> getMenuMappings() {
        return menuMappings;
    }

    public void setMenuMappings(List<SchoolMenuMapping> menuMappings) {
        this.menuMappings = menuMappings;
    }
}