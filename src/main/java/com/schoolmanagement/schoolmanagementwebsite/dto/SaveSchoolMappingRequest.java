package com.schoolmanagement.schoolmanagementwebsite.dto;

import java.util.List;

public class SaveSchoolMappingRequest {

    private Long schoolId;
    private Long userGroupId;

    private List<Long> moduleIds;
    private List<Long> menuIds;
    private List<Long> subMenuIds;

    public SaveSchoolMappingRequest() {
    }

    public Long getSchoolId() {
        return schoolId;
    }

    public void setSchoolId(Long schoolId) {
        this.schoolId = schoolId;
    }

    public Long getUserGroupId() {
        return userGroupId;
    }

    public void setUserGroupId(Long userGroupId) {
        this.userGroupId = userGroupId;
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