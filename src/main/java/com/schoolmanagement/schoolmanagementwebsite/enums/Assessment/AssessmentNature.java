package com.schoolmanagement.schoolmanagementwebsite.enums.Assessment;

public enum AssessmentNature {

    FORMATIVE(
        "Formative",
        "FA",
        "Assessment conducted during the learning process.",
        "Active"
    ),

    SUMMATIVE(
        "Summative",
        "SA",
        "Assessment conducted at the end of a term or course.",
        "Active"
    ),

    PRACTICAL(
        "Practical",
        "PA",
        "Laboratory or practical based assessment.",
       "Active"
    ),

    ORAL(
        "Oral",
        "OA",
        "Oral examination or viva.",
        "Active"
    );

    private final String displayName;
    private final String shortCode;
    private final String description;
    private final String status;

    AssessmentNature(String displayName, String shortCode, String description,String status) {
        this.displayName = displayName;
        this.shortCode = shortCode;
        this.description = description;
        this.status = status;
    }

    public String getDisplayName() {
        return displayName;
    }

    public String getShortCode() {
        return shortCode;
    }

    public String getDescription() {
        return description;
    }
    public String getStatus(){
        return status;
    }

}
