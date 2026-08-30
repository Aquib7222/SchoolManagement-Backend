package com.schoolmanagement.schoolmanagementwebsite.enums.School;
public enum SchoolCategory {

    PRE_PRIMARY("Pre-Primary"),
    PRIMARY("Primary"),
    UPPER_PRIMARY("Upper Primary"),
    SECONDARY("Secondary"),
    SENIOR_SECONDARY("Senior Secondary"),
    COMPOSITE("Composite School");

    private final String displayName;

    SchoolCategory(String displayName) {
        this.displayName = displayName;
    }

    public String getDisplayName() {
        return displayName;
    }
}