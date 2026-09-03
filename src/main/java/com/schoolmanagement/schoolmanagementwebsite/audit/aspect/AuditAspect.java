// // package com.schoolmanagement.schoolmanagementwebsite.audit.aspect;
// // import jakarta.servlet.http.HttpServletRequest;

// // import lombok.RequiredArgsConstructor;

// // import org.aspectj.lang.ProceedingJoinPoint;
// // import org.aspectj.lang.annotation.Around;
// // import org.aspectj.lang.annotation.Aspect;

// // import org.springframework.security.core.Authentication;
// // import org.springframework.security.core.context.SecurityContextHolder;

// // import org.springframework.stereotype.Component;

// // import com.schoolmanagement.schoolmanagementwebsite.audit.annotation.Audit;
// // import com.schoolmanagement.schoolmanagementwebsite.audit.entity.AuditLog;
// // import com.schoolmanagement.schoolmanagementwebsite.audit.enums.AuditStatus;
// // import com.schoolmanagement.schoolmanagementwebsite.audit.repository.AuditLogRepository;

// // import java.time.LocalDateTime;

// // @Aspect
// // @Component
// // @RequiredArgsConstructor
// // public class AuditAspect {

// //     private final AuditLogRepository auditLogRepository;

// //     private final HttpServletRequest request;


// //     @Around("@annotation(audit)")
// //     public Object auditMethod(
// //             ProceedingJoinPoint joinPoint,
// //             Audit audit
// //     ) throws Throwable {

// //         try {

// //             // First execute the actual method
// //             Object result = joinPoint.proceed();

// //             // If method succeeds
// //             saveAuditLog(
// //                     audit,
// //                     AuditStatus.SUCCESS,
// //                     null
// //             );

// //             return result;

// //         } catch (Exception exception) {

// //             // If method fails
// //             saveAuditLog(
// //                     audit,
// //                     AuditStatus.FAILED,
// //                     exception.getMessage()
// //             );

// //             // Important:
// //             // original exception must continue
// //             throw exception;
// //         }
// //     }


// //     private void saveAuditLog(
// //             Audit audit,
// //             AuditStatus status,
// //             String errorMessage
// //     ) {

// //         Authentication authentication =
// //                 SecurityContextHolder
// //                         .getContext()
// //                         .getAuthentication();


// //         String username = null;

// //         String role = null;

// //         Long userId = null;


// //         /*
// //          * Get logged-in user
// //          */
// //         if (authentication != null
// //                 && authentication.isAuthenticated()) {

// //             username = authentication.getName();

// //             role = authentication
// //                     .getAuthorities()
// //                     .stream()
// //                     .findFirst()
// //                     .map(authority ->
// //                             authority.getAuthority()
// //                     )
// //                     .orElse(null);
// //         }


// //         /*
// //          * Description
// //          */
// //         String description =
// //                 audit.description();


// //         if (errorMessage != null
// //                 && !errorMessage.isBlank()) {

// //             description =
// //                     description
// //                             + " | Error: "
// //                             + errorMessage;
// //         }


// //         /*
// //          * Create Audit Log
// //          */
// //         AuditLog log = AuditLog.builder()

// //                 .userId(userId)

// //                 .username(username)

// //                 .role(role)

// //                 .action(
// //                         audit.action().name()
// //                 )

// //                 .module(
// //                         audit.module()
// //                 )

// //                 .targetType(
// //                         audit.targetType()
// //                 )

// //                 .targetId(
// //                         audit.targetId()
// //                 )

// //                 .description(
// //                         description
// //                 )

// //                 .requestMethod(
// //                         request.getMethod()
// //                 )

// //                 .requestUrl(
// //                         request.getRequestURI()
// //                 )

// //                 .ipAddress(
// //                         getClientIp()
// //                 )

// //                 .status(status)

// //                 .createdAt(
// //                         LocalDateTime.now()
// //                 )

// //                 .build();


// //         auditLogRepository.save(log);
// //     }


// //     /*
// //      * Get real client IP
// //      */
// //     private String getClientIp() {

// //         String ip =
// //                 request.getHeader(
// //                         "X-Forwarded-For"
// //                 );

// //         if (ip != null
// //                 && !ip.isBlank()) {

// //             return ip.split(",")[0].trim();
// //         }


// //         ip =
// //                 request.getHeader(
// //                         "X-Real-IP"
// //                 );

// //         if (ip != null
// //                 && !ip.isBlank()) {

// //             return ip;
// //         }


// //         return request.getRemoteAddr();
// //     }
// // }
// package com.schoolmanagement.schoolmanagementwebsite.audit.aspect;

// import jakarta.servlet.http.HttpServletRequest;

// import lombok.RequiredArgsConstructor;

// import org.aspectj.lang.ProceedingJoinPoint;
// import org.aspectj.lang.annotation.Around;
// import org.aspectj.lang.annotation.Aspect;

// import com.fasterxml.jackson.databind.ObjectMapper;

// import org.springframework.security.core.Authentication;
// import org.springframework.security.core.context.SecurityContextHolder;

// import org.springframework.stereotype.Component;

// import com.schoolmanagement.schoolmanagementwebsite.audit.annotation.Audit;
// import com.schoolmanagement.schoolmanagementwebsite.audit.entity.AuditLog;
// import com.schoolmanagement.schoolmanagementwebsite.audit.enums.AuditStatus;
// import com.schoolmanagement.schoolmanagementwebsite.audit.repository.AuditLogRepository;

// import java.time.LocalDateTime;
// import java.util.LinkedHashMap;
// import java.util.Map;

// @Aspect
// @Component
// @RequiredArgsConstructor
// public class AuditAspect {

//     private final AuditLogRepository auditLogRepository;

//     private final HttpServletRequest request;

//     private final ObjectMapper objectMapper;


//     // =========================================================
//     // AUDIT METHOD
//     // =========================================================

//     @Around("@annotation(audit)")
//     public Object auditMethod(
//             ProceedingJoinPoint joinPoint,
//             Audit audit
//     ) throws Throwable {

//         Object result = null;

//         try {

//             // Execute actual business method
//             result = joinPoint.proceed();


//             // Save SUCCESS audit
//             saveAuditLog(
//                     audit,
//                     AuditStatus.SUCCESS,
//                     null,
//                     result
//             );

//             return result;

//         } catch (Exception exception) {

//             // Save FAILED audit
//             saveAuditLog(
//                     audit,
//                     AuditStatus.FAILED,
//                     exception.getMessage(),
//                     null
//             );

//             // Original exception continue karega
//             throw exception;
//         }
//     }


//     // =========================================================
//     // SAVE AUDIT LOG
//     // =========================================================

//     private void saveAuditLog(
//             Audit audit,
//             AuditStatus status,
//             String errorMessage,
//             Object result
//     ) {

//         try {

//             // =================================================
//             // CURRENT USER
//             // =================================================

//             Authentication authentication =
//                     SecurityContextHolder
//                             .getContext()
//                             .getAuthentication();


//             String username = null;

//             String role = null;

//             Long userId = null;


//             if (authentication != null
//                     && authentication.isAuthenticated()) {

//                 username = authentication.getName();

//                 role =
//                         authentication
//                                 .getAuthorities()
//                                 .stream()
//                                 .findFirst()
//                                 .map(authority ->
//                                         authority.getAuthority()
//                                 )
//                                 .orElse(null);
//             }


//             // =================================================
//             // DESCRIPTION
//             // =================================================

//             String description =
//                     audit.description();


//             if (errorMessage != null
//                     && !errorMessage.isBlank()) {

//                 description =
//                         description
//                                 + " | Error: "
//                                 + errorMessage;
//             }


//             // =================================================
//             // TARGET ID
//             // =================================================

//             String targetId =
//                     getTargetId(
//                             audit,
//                             result
//                     );


//             // =================================================
//             // DETAILS
//             // =================================================

//             String details =
//                     createDetails(
//                             audit,
//                             result,
//                             status,
//                             errorMessage
//                     );


//             // =================================================
//             // CREATE AUDIT LOG
//             // =================================================

//             AuditLog log =
//                     AuditLog.builder()

//                             .userId(userId)

//                             .username(username)

//                             .role(role)

//                             .action(
//                                     audit.action().name()
//                             )

//                             .module(
//                                     audit.module()
//                             )

//                             .targetType(
//                                     audit.targetType()
//                             )

//                             .targetId(
//                                     targetId
//                             )

//                             .description(
//                                     description
//                             )

//                             .details(
//                                     details
//                             )

//                             .requestMethod(
//                                     request.getMethod()
//                             )

//                             .requestUrl(
//                                     request.getRequestURI()
//                             )

//                             .ipAddress(
//                                     getClientIp()
//                             )

//                             .status(status)

//                             .createdAt(
//                                     LocalDateTime.now()
//                             )

//                             .build();


//             auditLogRepository.save(log);

//         } catch (Exception exception) {

//             /*
//              * Audit fail hone par
//              * original business operation fail nahi hogi.
//              */

//             System.err.println(
//                     "Failed to save audit log: "
//                             + exception.getMessage()
//             );
//         }
//     }


//     // =========================================================
//     // GET TARGET ID
//     // =========================================================

//     private String getTargetId(
//             Audit audit,
//             Object result
//     ) {

//         /*
//          * @Audit me targetId explicitly diya ho
//          * to usko priority do.
//          */

//         if (audit.targetId() != null
//                 && !audit.targetId().isBlank()) {

//             return audit.targetId();
//         }


//         /*
//          * Result object me getId() ho
//          * to automatically ID nikalenge.
//          */

//         if (result != null) {

//             try {

//                 var idMethod =
//                         result
//                                 .getClass()
//                                 .getMethod("getId");

//                 Object id =
//                         idMethod.invoke(result);

//                 if (id != null) {

//                     return String.valueOf(id);
//                 }

//             } catch (Exception ignored) {

//                 // ID available nahi hai
//             }
//         }


//         return null;
//     }


//     // =========================================================
//     // CREATE DETAILS
//     // =========================================================

//     private String createDetails(
//             Audit audit,
//             Object result,
//             AuditStatus status,
//             String errorMessage
//     ) {

//         try {

//             /*
//              * SUCCESS
//              */

//             if (result != null) {

//                 return objectMapper
//                         .writeValueAsString(result);
//             }


//             /*
//              * FAILED
//              */

//             Map<String, Object> details =
//                     new LinkedHashMap<>();


//             details.put(
//                     "status",
//                     status.name()
//             );


//             details.put(
//                     "module",
//                     audit.module()
//             );


//             details.put(
//                     "action",
//                     audit.action().name()
//             );


//             if (errorMessage != null) {

//                 details.put(
//                         "error",
//                         errorMessage
//                 );
//             }


//             return objectMapper
//                     .writeValueAsString(details);

//         } catch (Exception exception) {

//             return "{\"status\":\""
//                     + status.name()
//                     + "\"}";
//         }
//     }


//     // =========================================================
//     // GET CLIENT IP
//     // =========================================================

//     private String getClientIp() {

//         String ip =
//                 request.getHeader(
//                         "X-Forwarded-For"
//                 );


//         if (ip != null
//                 && !ip.isBlank()) {

//             return ip
//                     .split(",")[0]
//                     .trim();
//         }


//         ip =
//                 request.getHeader(
//                         "X-Real-IP"
//                 );


//         if (ip != null
//                 && !ip.isBlank()) {

//             return ip;
//         }


//         return request.getRemoteAddr();
//     }
// }


package com.schoolmanagement.schoolmanagementwebsite.audit.aspect;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.schoolmanagement.schoolmanagementwebsite.audit.entity.AuditLog;
import com.schoolmanagement.schoolmanagementwebsite.audit.enums.AuditStatus;
import com.schoolmanagement.schoolmanagementwebsite.audit.repository.AuditLogRepository;
import com.schoolmanagement.schoolmanagementwebsite.entity.User;
import com.schoolmanagement.schoolmanagementwebsite.repository.UserRepository;

import jakarta.servlet.http.HttpServletRequest;

import lombok.RequiredArgsConstructor;

import org.aspectj.lang.ProceedingJoinPoint;
import org.aspectj.lang.annotation.Around;
import org.aspectj.lang.annotation.Aspect;

import org.springframework.http.ResponseEntity;

import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;

import org.springframework.stereotype.Component;

import java.time.LocalDateTime;
import java.util.LinkedHashMap;
import java.util.Map;

@Aspect
@Component
@RequiredArgsConstructor
public class AuditAspect {

    private final AuditLogRepository auditLogRepository;

    private final HttpServletRequest request;

    private final ObjectMapper objectMapper;

    private final UserRepository userRepository;


    // =========================================================
    // GLOBAL AUDIT
    // =========================================================
    //
    // Controller ke andar koi bhi public method execute hoga
    // automatically audit hoga.
    //
    // @Audit lagane ki zarurat nahi.
    //
    // =========================================================

   
    @Around(
    "execution(public * com.schoolmanagement.schoolmanagementwebsite.controller..*(..))"
    + " && !within(com.schoolmanagement.schoolmanagementwebsite.audit..*)"
)
    public Object globalAudit(
            ProceedingJoinPoint joinPoint
    ) throws Throwable {

        Object result = null;

        try {

            // =================================================
            // ACTUAL API EXECUTION
            // =================================================

            result = joinPoint.proceed();


            // =================================================
            // SUCCESS AUDIT
            // =================================================

            saveAuditLog(
                    joinPoint,
                    AuditStatus.SUCCESS,
                    null,
                    result
            );

            return result;

        } catch (Throwable exception) {

            // =================================================
            // FAILED AUDIT
            // =================================================

            saveAuditLog(
                    joinPoint,
                    AuditStatus.FAILED,
                    exception.getMessage(),
                    null
            );

            // Original exception ko continue hone do
            throw exception;
        }
    }


    // =========================================================
    // SAVE AUDIT
    // =========================================================

    private void saveAuditLog(
            ProceedingJoinPoint joinPoint,
            AuditStatus status,
            String errorMessage,
            Object result
    ) {

        try {

            // =================================================
            // CURRENT USER
            // =================================================

            Authentication authentication =
                    SecurityContextHolder
                            .getContext()
                            .getAuthentication();


            String username = null;

            String role = null;

            Long userId = null;


            if (
                authentication != null
                && authentication.isAuthenticated()
                && !"anonymousUser".equals(authentication.getName())
            ) {

                username = authentication.getName();


                // -------------------------------------------------
                // ROLE
                // -------------------------------------------------

                role =
                        authentication
                                .getAuthorities()
                                .stream()
                                .findFirst()
                                .map(authority ->
                                        authority.getAuthority()
                                )
                                .orElse(null);


                // -------------------------------------------------
                // USER ID
                // -------------------------------------------------

                try {

                    User user =
                            userRepository.findByEmail(username);

                    if (user != null) {

                        userId = user.getId();
                    }

                } catch (Exception ignored) {

                    // User ID na mile to audit fail nahi hoga
                }
            }


            // =================================================
            // CONTROLLER NAME
            // =================================================

            String controllerName =
                    joinPoint
                            .getTarget()
                            .getClass()
                            .getSimpleName();


            // =================================================
            // METHOD NAME
            // =================================================

            String methodName =
                    joinPoint
                            .getSignature()
                            .getName();


            // =================================================
            // HTTP METHOD
            // =================================================

            String httpMethod =
                    request.getMethod();


            // =================================================
            // MODULE
            // =================================================

            String module =
                    getModuleName(controllerName);


            // =================================================
            // ACTION
            // =================================================

            String action =
                    getAction(
                            httpMethod,
                            methodName
                    );


            // =================================================
            // DESCRIPTION
            // =================================================

            String description =
                    controllerName
                            + "."
                            + methodName
                            + " executed";


            if (
                errorMessage != null
                && !errorMessage.isBlank()
            ) {

                description =
                        description
                                + " | Error: "
                                + errorMessage;
            }


            // =================================================
            // TARGET ID
            // =================================================

            String targetId =
                    findTargetId(
                            joinPoint,
                            result
                    );


            // =================================================
            // DETAILS
            // =================================================

            String details =
                    createDetails(
                            joinPoint,
                            result,
                            status,
                            errorMessage,
                            controllerName,
                            methodName,
                            httpMethod
                    );


            // =================================================
            // CREATE LOG
            // =================================================

            AuditLog log =
                    AuditLog.builder()

                            .userId(userId)

                            .username(username)

                            .role(role)

                            .action(action)

                            .module(module)

                            .targetType(controllerName)

                            .targetId(targetId)

                            .description(description)

                            .details(details)

                            .requestMethod(httpMethod)

                            .requestUrl(
                                    request.getRequestURI()
                            )

                            .ipAddress(
                                    getClientIp()
                            )

                            .status(status)

                            .createdAt(
                                    LocalDateTime.now()
                            )

                            .build();


            // =================================================
            // SAVE
            // =================================================

            auditLogRepository.save(log);


        } catch (Exception exception) {

            /*
             * Audit fail hone ki wajah se
             * original API fail nahi honi chahiye.
             */

            System.err.println(
                    "❌ Failed to save global audit log: "
                            + exception.getMessage()
            );
        }
    }


    // =========================================================
    // MODULE NAME
    // =========================================================

    private String getModuleName(
            String controllerName
    ) {

        if (controllerName == null) {
            return "SYSTEM";
        }


        String name =
                controllerName
                        .replace("Controller", "");


        // Common modules

        if (name.contains("Attendance")) {
            return "ATTENDANCE";
        }

        if (name.contains("Student")) {
            return "STUDENT";
        }

        if (name.contains("Teacher")) {
            return "TEACHER";
        }

        if (name.contains("Admission")) {
            return "ADMISSION";
        }

        if (name.contains("Fee")) {
            return "FEE";
        }

        if (name.contains("Assessment")
                || name.contains("Exam")
                || name.contains("Marks")
                || name.contains("Result")) {

            return "ASSESSMENT";
        }

        if (name.contains("School")) {
            return "SCHOOL";
        }

        if (name.contains("SuperAdmin")) {
            return "SUPERADMIN";
        }

        if (name.contains("Module")
                || name.contains("Menu")) {

            return "SYSTEM_CONFIGURATION";
        }

        if (name.contains("User")
                || name.contains("Role")
                || name.contains("Group")) {

            return "USER_MANAGEMENT";
        }


        return name.toUpperCase();
    }


    // =========================================================
    // ACTION
    // =========================================================

    private String getAction(
            String httpMethod,
            String methodName
    ) {

        if (methodName == null) {
            return httpMethod;
        }


        String method =
                methodName.toLowerCase();


        // DELETE

        if ("DELETE".equalsIgnoreCase(httpMethod)
                || method.contains("delete")
                || method.contains("remove")) {

            return "DELETE";
        }


        // CREATE

        if ("POST".equalsIgnoreCase(httpMethod)
                || method.contains("create")
                || method.contains("save")
                || method.contains("add")) {

            return "CREATE";
        }


        // UPDATE

        if ("PUT".equalsIgnoreCase(httpMethod)
                || "PATCH".equalsIgnoreCase(httpMethod)
                || method.contains("update")
                || method.contains("edit")) {

            return "UPDATE";
        }


        // GET

        if ("GET".equalsIgnoreCase(httpMethod)) {

            if (
                method.contains("search")
                || method.contains("find")
            ) {

                return "SEARCH";
            }

            return "VIEW";
        }


        return httpMethod.toUpperCase();
    }


    // =========================================================
    // TARGET ID
    // =========================================================

    private String findTargetId(
            ProceedingJoinPoint joinPoint,
            Object result
    ) {

        // -----------------------------------------------------
        // First: method arguments
        // -----------------------------------------------------

        Object[] args =
                joinPoint.getArgs();


        if (args != null) {

            for (Object arg : args) {

                if (arg == null) {
                    continue;
                }


                // Direct ID

                if (
                    arg instanceof Long
                    || arg instanceof Integer
                    || arg instanceof String
                ) {

                    String value =
                            String.valueOf(arg);

                    if (value.matches("\\d+")) {

                        return value;
                    }
                }


                // DTO / Entity getId()

                try {

                    var idMethod =
                            arg
                                    .getClass()
                                    .getMethod("getId");

                    Object id =
                            idMethod.invoke(arg);

                    if (id != null) {

                        return String.valueOf(id);
                    }

                } catch (Exception ignored) {
                }
            }
        }


        // -----------------------------------------------------
        // Second: Response result
        // -----------------------------------------------------

        if (result != null) {

            try {

                Object body = result;

                if (result instanceof ResponseEntity<?> response) {

                    body = response.getBody();
                }


                if (body != null) {

                    var idMethod =
                            body
                                    .getClass()
                                    .getMethod("getId");

                    Object id =
                            idMethod.invoke(body);

                    if (id != null) {

                        return String.valueOf(id);
                    }
                }

            } catch (Exception ignored) {
            }
        }


        return null;
    }


    // =========================================================
    // DETAILS
    // =========================================================

    private String createDetails(
            ProceedingJoinPoint joinPoint,
            Object result,
            AuditStatus status,
            String errorMessage,
            String controllerName,
            String methodName,
            String httpMethod
    ) {

        try {

            Map<String, Object> details =
                    new LinkedHashMap<>();


            details.put(
                    "status",
                    status.name()
            );


            details.put(
                    "controller",
                    controllerName
            );


            details.put(
                    "method",
                    methodName
            );


            details.put(
                    "httpMethod",
                    httpMethod
            );


            details.put(
                    "url",
                    request.getRequestURI()
            );


            // -------------------------------------------------
            // REQUEST PARAMETERS
            // -------------------------------------------------

            Map<String, String> parameters =
                    new LinkedHashMap<>();


            request
                    .getParameterMap()
                    .forEach(
                            (key, value) ->
                                    parameters.put(
                                            key,
                                            String.join(",", value)
                                    )
                    );


            details.put(
                    "parameters",
                    parameters
            );


            // -------------------------------------------------
            // RESULT
            // -------------------------------------------------

            if (
                status == AuditStatus.SUCCESS
                && result != null
            ) {

                Object body = result;

                if (result instanceof ResponseEntity<?> response) {

                    body = response.getBody();
                }


                details.put(
                        "response",
                        body
                );
            }


            // -------------------------------------------------
            // ERROR
            // -------------------------------------------------

            if (
                status == AuditStatus.FAILED
                && errorMessage != null
            ) {

                details.put(
                        "error",
                        errorMessage
                );
            }


            return objectMapper
                    .writeValueAsString(details);


        } catch (Exception exception) {

            return "{\"status\":\""
                    + status.name()
                    + "\"}";
        }
    }


    // =========================================================
    // CLIENT IP
    // =========================================================

    private String getClientIp() {

        String ip =
                request.getHeader(
                        "X-Forwarded-For"
                );


        if (
            ip != null
            && !ip.isBlank()
        ) {

            return ip
                    .split(",")[0]
                    .trim();
        }


        ip =
                request.getHeader(
                        "X-Real-IP"
                );


        if (
            ip != null
            && !ip.isBlank()
        ) {

            return ip;
        }


        return request.getRemoteAddr();
    }
}