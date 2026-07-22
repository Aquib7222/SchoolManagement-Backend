package com.schoolmanagement.schoolmanagementwebsite.dto;

public class SubMenuDto {

    private String subMenuName;

    private String subMenuUrl;

    private String subMenuIcon;

    private Integer displayOrder;

    public SubMenuDto() {
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