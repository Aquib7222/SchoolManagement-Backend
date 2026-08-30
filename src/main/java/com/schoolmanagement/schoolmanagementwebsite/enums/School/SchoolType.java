package com.schoolmanagement.schoolmanagementwebsite.enums.School;

public enum SchoolType {

    GOVERNMENT("Government"),
    PRIVATE("Private"),
    GOVERNMENT_AIDED("Government Aided"),
    PRIVATE_UNAIDED("Private Unaided"),
    TRUST("Trust"),
    SOCIETY("Society"),
    MINORITY("Minority");

    private final String displayName;

    SchoolType(String displayName) {
        this.displayName = displayName;
    }

    public String getDisplayName() {
        return displayName;
    }
}