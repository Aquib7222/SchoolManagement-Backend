package com.schoolmanagement.schoolmanagementwebsite.dto;

import java.util.ArrayList;
import java.util.List;

public class SchoolMappingResponse {

    private List<Long> moduleIds = new ArrayList<>();
    private List<Long> menuIds = new ArrayList<>();
    private List<Long> subMenuIds = new ArrayList<>();

    public SchoolMappingResponse() {
    }

    public List<Long> getModuleIds() {
        return moduleIds;
    }

    public void setModuleIds(List<Long> moduleIds) {
        this.moduleIds = moduleIds;
    }

    public List<Long> getMenuIds() {
        return menuIds;
    }

    public void setMenuIds(List<Long> menuIds) {
        this.menuIds = menuIds;
    }

    public List<Long> getSubMenuIds() {
        return subMenuIds;
    }

    public void setSubMenuIds(List<Long> subMenuIds) {
        this.subMenuIds = subMenuIds;
    }
}