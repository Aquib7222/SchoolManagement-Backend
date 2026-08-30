// package com.schoolmanagement.schoolmanagementwebsite.enums;

// import com.fasterxml.jackson.annotation.JsonCreator;
// import com.fasterxml.jackson.annotation.JsonValue;

// public enum Sessions {

//     SESSION_2026_2027("2026-2027"),
//     SESSION_2025_2026("2025-2026"),
//     SESSION_2024_2025("2024-2025");

//     private final String value;

//     Sessions(String value) {
//         this.value = value;
//     }

//     @JsonValue
//     public String getValue() {
//         return value;
//     }

//     @JsonCreator
//     public static Sessions fromValue(String value) {

//         for (Sessions session : Sessions.values()) {

//             if (session.value.equals(value)) {
//                 return session;
//             }
//         }

//         throw new IllegalArgumentException(
//             "Invalid session: " + value
//         );
//     }
// }

package com.schoolmanagement.schoolmanagementwebsite.enums;

import com.fasterxml.jackson.annotation.JsonCreator;
import com.fasterxml.jackson.annotation.JsonValue;

public enum Sessions {

    SESSION_2028_2029("2028-2029"),
    SESSION_2027_2028("2027-2028"),
    SESSION_2026_2027("2026-2027"),
    SESSION_2025_2026("2025-2026"),
    SESSION_2024_2025("2024-2025");

    private final String value;

    Sessions(String value) {
        this.value = value;
    }

    @JsonValue
    public String getValue() {
        return value;
    }

    @JsonCreator
    public static Sessions fromValue(String value) {

        for (Sessions session : Sessions.values()) {
            if (session.value.equals(value)) {
                return session;
            }
        }

        throw new IllegalArgumentException(
                "Invalid session: " + value
        );
    }
}


