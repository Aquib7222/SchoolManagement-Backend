package com.schoolmanagement.schoolmanagementwebsite.dto;

import java.util.ArrayList;
import java.util.List;

public class SidebarMenuResponse {

    private Long id;
    private String label;
    private String path;

    private List<SidebarSubMenuResponse> subSubMenu = new ArrayList<>();

    public SidebarMenuResponse() {
    }

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public String getLabel() {
        return label;
    }

    public void setLabel(String label) {
        this.label = label;
    }

    public String getPath() {
        return path;
    }

    public void setPath(String path) {
        this.path = path;
    }

    public List<SidebarSubMenuResponse> getSubSubMenu() {
        return subSubMenu;
    }

    public void setSubSubMenu(List<SidebarSubMenuResponse> subSubMenu) {
        this.subSubMenu = subSubMenu;
    }
}