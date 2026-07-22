package com.schoolmanagement.schoolmanagementwebsite.dto;

import java.util.List;

public class UserGroupMappingDto {

    private Long userGroupId;

    private Long moduleId;

    private List<Long> menuIds;

    private List<Long> subMenuIds;

    public UserGroupMappingDto() {
    }

    public Long getUserGroupId() {
        return userGroupId;
    }

    public void setUserGroupId(Long userGroupId) {
        this.userGroupId = userGroupId;
    }

    public Long getModuleId() {
        return moduleId;
    }

    public void setModuleId(Long moduleId) {
        this.moduleId = moduleId;
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