package com.schoolmanagement.schoolmanagementwebsite.enums.School;

public enum AffiliationBoard {

    CBSE("CBSE"),
    CISCE("CISCE (ICSE)"),
    STATE_BOARD("State Board"),
    NIOS("NIOS"),
    IB("IB"),
    CAMBRIDGE("Cambridge"),
    OTHER("Other");

    private final String displayName;

    AffiliationBoard(String displayName) {
        this.displayName = displayName;
    }

    public String getDisplayName() {
        return displayName;
    }
}