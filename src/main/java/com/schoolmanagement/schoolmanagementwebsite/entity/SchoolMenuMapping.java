package com.schoolmanagement.schoolmanagementwebsite.entity;

import java.util.ArrayList;
import java.util.List;

import com.fasterxml.jackson.annotation.JsonBackReference;
import com.fasterxml.jackson.annotation.JsonManagedReference;

import jakarta.persistence.*;

@Entity
@Table(
    name = "school_menu_mapping",
    uniqueConstraints = {
        @UniqueConstraint(columnNames = {"school_module_mapping_id", "menu_id"})
    }
)
public class SchoolMenuMapping {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

   @ManyToOne(fetch = FetchType.LAZY)
@JoinColumn(name = "school_module_mapping_id")
@JsonBackReference
private SchoolModuleMapping schoolModuleMapping;

    @ManyToOne
    @JoinColumn(name="menu_id")
    private Menu menu;

    @OneToMany(
        mappedBy = "schoolMenuMapping",
        cascade = CascadeType.ALL,
        orphanRemoval = true,
        fetch = FetchType.LAZY
)
@JsonManagedReference
private List<SchoolSubMenuMapping> subMenuMappings =
        new ArrayList<>();

    public SchoolMenuMapping() {
    }

    public Long getId() {
        return id;
    }

    public SchoolModuleMapping getSchoolModuleMapping() {
        return schoolModuleMapping;
    }

    public void setSchoolModuleMapping(SchoolModuleMapping schoolModuleMapping) {
        this.schoolModuleMapping = schoolModuleMapping;
    }

    public Menu getMenu() {
        return menu;
    }

    public void setMenu(Menu menu) {
        this.menu = menu;
    }

    public List<SchoolSubMenuMapping> getSubMenuMappings() {
        return subMenuMappings;
    }

    public void setSubMenuMappings(List<SchoolSubMenuMapping> subMenuMappings) {
        this.subMenuMappings = subMenuMappings;
    }
}