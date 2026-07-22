package com.schoolmanagement.schoolmanagementwebsite.entity;

import com.fasterxml.jackson.annotation.JsonBackReference;
import com.fasterxml.jackson.annotation.JsonIgnoreProperties;

import jakarta.persistence.*;

@Entity
@Table(name = "sub_menus")
public class SubMenu {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "menu_id")
    @JsonIgnoreProperties({"subMenus", "module"})
    private Menu menu;

    private String subMenuName;

    private String subMenuUrl;

    private String subMenuIcon;

    private Integer displayOrder;

    public SubMenu() {
    }

    public Long getId() {
        return id;
    }

    public Menu getMenu() {
        return menu;
    }

    public void setMenu(Menu menu) {
        this.menu = menu;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public String getSubMenuName() {
        return subMenuName;
    }

    public void setSubMenuName(String subMenuName) {
        this.subMenuName = subMenuName;
    }

    public String getSubMenuUrl() {
        return subMenuUrl;
    }

    public void setSubMenuUrl(String subMenuUrl) {
        this.subMenuUrl = subMenuUrl;
    }

    public String getSubMenuIcon() {
        return subMenuIcon;
    }

    public void setSubMenuIcon(String subMenuIcon) {
        this.subMenuIcon = subMenuIcon;
    }

    public Integer getDisplayOrder() {
        return displayOrder;
    }

    public void setDisplayOrder(Integer displayOrder) {
        this.displayOrder = displayOrder;
    }
}
