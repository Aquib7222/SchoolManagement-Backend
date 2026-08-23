package com.schoolmanagement.schoolmanagementwebsite.audit.annotation;
import java.lang.annotation.*;

import com.schoolmanagement.schoolmanagementwebsite.audit.enums.AuditAction;

@Target(ElementType.METHOD)
@Retention(RetentionPolicy.RUNTIME)
@Documented
public @interface Audit {

    String module();

    AuditAction action();

    String description() default "";

    String targetType() default "";

    String targetId() default "";
}