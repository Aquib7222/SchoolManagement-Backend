package com.schoolmanagement.schoolmanagementwebsite.entity;

import com.fasterxml.jackson.annotation.JsonBackReference;

import jakarta.persistence.*;

@Entity
@Table(name = "user_group_menu_mapping")
public class UserGroupMenuMapping {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

   @ManyToOne
@JoinColumn(name="mapping_id")
@JsonBackReference
private UserGroupMapping mapping;

    @ManyToOne
@JoinColumn(name="menu_id")
private Menu menu;

    public UserGroupMenuMapping() {
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

    public Menu getMenu() {
        return menu;
    }

    public void setMenu(Menu menu) {
        this.menu = menu;
    }
}