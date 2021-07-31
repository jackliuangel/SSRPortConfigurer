package com.securingweb.vpn.audit.annotation;


import com.securingweb.vpn.audit.UserAuditAction;

import java.lang.annotation.*;

@Target(ElementType.METHOD)
@Inherited
@Documented
@Retention(RetentionPolicy.RUNTIME)
public @interface Audit {
    UserAuditAction value();
}
