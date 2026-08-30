// package com.schoolmanagement.schoolmanagementwebsite.audit.dto;


// import java.time.LocalDateTime;

// import com.schoolmanagement.schoolmanagementwebsite.audit.enums.AuditStatus;

// import lombok.AllArgsConstructor;
// import lombok.Builder;
// import lombok.Getter;
// import lombok.NoArgsConstructor;
// import lombok.Setter;

// @Getter
// @Setter
// @NoArgsConstructor
// @AllArgsConstructor
// @Builder
// public class AuditLogResponse {

//     private Long id;

//     private Long userId;

//     private String username;

//     private String role;

//     private String action;

//     private String module;

//     private String details;

//     private String targetType;

//     private String targetId;
    
//     private String targetName;

//     private String description;

//     private String requestMethod;

//     private String requestUrl;

//     private String ipAddress;

//     private AuditStatus status;

//     private LocalDateTime createdAt;
// }


package com.schoolmanagement.schoolmanagementwebsite.audit.dto;

import java.time.LocalDateTime;

import com.schoolmanagement.schoolmanagementwebsite.audit.enums.AuditStatus;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
@Builder
public class AuditLogResponse {

    private Long id;

    private Long userId;

    private String username;

    private String role;

    private String action;

    private String module;

    private String details;

    private String targetType;

    private String targetId;

    private String targetName;

    private String description;

    private String requestMethod;

    private String requestUrl;

    private String ipAddress;

    private AuditStatus status;

    private LocalDateTime createdAt;
}

