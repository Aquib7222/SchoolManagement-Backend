package com.schoolmanagement.schoolmanagementwebsite.entity;

import com.fasterxml.jackson.annotation.JsonBackReference;

import jakarta.persistence.*;

@Entity
@Table(name = "user_group_submenu_mapping")
public class UserGroupSubMenuMapping {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

   @ManyToOne
@JoinColumn(name="mapping_id")
@JsonBackReference
private UserGroupMapping mapping;

@ManyToOne
@JoinColumn(name="submenu_id")
private SubMenu subMenu;

    public UserGroupSubMenuMapping() {
    }

    public Long getId() {
        return id;
    }

    public UserGroupMapping getMapping() {
        return mapping;
    }

    public void setMapping(UserGroupMapping mapping) {
        this.mapping = mapping;
    }

    public SubMenu getSubMenu() {
        return subMenu;
    }

    public void setSubMenu(SubMenu subMenu) {
        this.subMenu = subMenu;
    }
}