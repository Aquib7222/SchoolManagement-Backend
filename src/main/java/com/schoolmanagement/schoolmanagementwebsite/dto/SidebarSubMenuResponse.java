package com.schoolmanagement.schoolmanagementwebsite.dto;

public class SidebarSubMenuResponse {

    private Long id;
    private String label;
    private String path;

    public SidebarSubMenuResponse() {
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
}