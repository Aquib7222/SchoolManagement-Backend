package com.schoolmanagement.schoolmanagementwebsite.dto;

public class ModuleDto {

    private String moduleName;
    private String icon;
    private String path;
    private Boolean hasMenu;
    private String status;
    private String image;
    private String sequenceNumber;

    public ModuleDto() {
    }

    public String getModuleName() {
        return moduleName;
    }

    public void setModuleName(String moduleName) {
        this.moduleName = moduleName;
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

    public Boolean getHasMenu() {
        return hasMenu;
    }

    public void setHasMenu(Boolean hasMenu) {
        this.hasMenu = hasMenu;
    }

    public String getStatus() {
        return status;
    }

    public void setStatus(String status) {
        this.status = status;
    }

    public String getImage() {
        return image;
    }

    public void setImage(String image) {
        this.image = image;
    }

    public String getSequenceNumber() {
        return sequenceNumber;
    }

    public void setSequenceNumber(String sequenceNumber) {
        this.sequenceNumber = sequenceNumber;
    }
}
