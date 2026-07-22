package com.schoolmanagement.schoolmanagementwebsite.dto;

import java.util.ArrayList;
import java.util.List;

public class SidebarModuleResponse {

    private Long id;
    private String label;
    private String icon;
    private String path;
    private String image;



    private List<SidebarMenuResponse> subMenus = new ArrayList<>();

    public SidebarModuleResponse() {
    }
    public String getImage() {
    return image;
}

public void setImage(String image) {
    this.image = image;
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

    public String getIcon() {
        return icon;
    }

    public void setIcon(String icon) {
        this.icon = icon;
    }

    public String getPath() {
        return path;
    }

    public void setPath(String path) {
        this.path = path;
    }

    public List<SidebarMenuResponse> getSubMenus() {
        return subMenus;
    }

    public void setSubMenus(List<SidebarMenuResponse> subMenus) {
        this.subMenus = subMenus;
    }
}