package com.schoolmanagement.schoolmanagementwebsite.dto;

import java.util.List;

public class MenuDto {

    private Long moduleId;

    private String menuName;

    private String menuUrl;

    private String menuIcon;

    private Integer displayOrder;

    private String status;

    private Boolean hasSubMenu;

    private List<SubMenuDto> subMenus;

    public MenuDto() {
    }

    public Long getModuleId() {
        return moduleId;
    }

    public void setModuleId(Long moduleId) {
        this.moduleId = moduleId;
    }

    public String getMenuName() {
        return menuName;
    }

    public void setMenuName(String menuName) {
        this.menuName = menuName;
    }

    public String getMenuUrl() {
        return menuUrl;
    }

    public void setMenuUrl(String menuUrl) {
        this.menuUrl = menuUrl;
    }

    public String getMenuIcon() {
        return menuIcon;
    }

    public void setMenuIcon(String menuIcon) {
        this.menuIcon = menuIcon;
    }

    public Integer getDisplayOrder() {
        return displayOrder;
    }

    public void setDisplayOrder(Integer displayOrder) {
        this.displayOrder = displayOrder;
    }

    public String getStatus() {
        return status;
    }

    public void setStatus(String status) {
        this.status = status;
    }

    public Boolean getHasSubMenu() {
        return hasSubMenu;
    }

    public void setHasSubMenu(Boolean hasSubMenu) {
        this.hasSubMenu = hasSubMenu;
    }

    public List<SubMenuDto> getSubMenus() {
        return subMenus;
    }

    public void setSubMenus(List<SubMenuDto> subMenus) {
        this.subMenus = subMenus;
    }
}