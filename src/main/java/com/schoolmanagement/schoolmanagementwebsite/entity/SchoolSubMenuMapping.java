package com.schoolmanagement.schoolmanagementwebsite.entity;

import com.fasterxml.jackson.annotation.JsonBackReference;

import jakarta.persistence.*;

@Entity
@Table(
    name = "school_submenu_mapping",
    uniqueConstraints = {
        @UniqueConstraint(columnNames = {"school_menu_mapping_id", "submenu_id"})
    }
)
public class SchoolSubMenuMapping {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @ManyToOne
    @JoinColumn(name="school_menu_mapping_id")
    @JsonBackReference
    private SchoolMenuMapping schoolMenuMapping;

    @ManyToOne
    @JoinColumn(name="submenu_id")
    private SubMenu subMenu;

    public SchoolSubMenuMapping() {
    }

    public Long getId() {
        return id;
    }

    public SchoolMenuMapping getSchoolMenuMapping() {
        return schoolMenuMapping;
    }

    public void setSchoolMenuMapping(SchoolMenuMapping schoolMenuMapping) {
        this.schoolMenuMapping = schoolMenuMapping;
    }

    public SubMenu getSubMenu() {
        return subMenu;
    }

    public void setSubMenu(SubMenu subMenu) {
        this.subMenu = subMenu;
    }
}
