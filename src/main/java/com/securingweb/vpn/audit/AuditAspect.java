package com.securingweb.vpn.audit;


import com.securingweb.vpn.audit.annotation.Audit;
import com.securingweb.vpn.audit.annotation.AuditField;
import io.micrometer.core.instrument.util.StringUtils;
import lombok.extern.slf4j.Slf4j;
import org.aspectj.lang.JoinPoint;
import org.aspectj.lang.annotation.AfterReturning;
import org.aspectj.lang.annotation.Around;
import org.aspectj.lang.annotation.Aspect;
import org.aspectj.lang.annotation.Before;
import org.aspectj.lang.reflect.MethodSignature;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.ApplicationEventPublisher;
import org.springframework.context.annotation.ComponentScan;
import org.springframework.core.annotation.AnnotationUtils;
import org.springframework.stereotype.Component;

import java.lang.reflect.Method;
import java.lang.reflect.Parameter;
import java.util.HashMap;
import java.util.Map;

@Slf4j
@Aspect
@Component
public class AuditAspect {

    @Autowired
    ApplicationEventPublisher applicationEventPublisher;

    @AfterReturning("@annotation(com.securingweb.vpn.audit.annotation.Audit)")
    public void audit(JoinPoint joinPoint) {
        MethodSignature methodSignature = (MethodSignature) joinPoint.getSignature();
        Method method = methodSignature.getMethod();

        Audit audit = AnnotationUtils.findAnnotation(method, Audit.class);
        if (audit == null) {
            log.warn("@Audit not exists in this method {} in this class {}", method.toString(), method.getDeclaringClass());
            return;
        }

        Map<String, String> data = resolveData(method, joinPoint);

        applicationEventPublisher.publishEvent(new UserAuditEvent(data));
    }

    private Map<String, String> resolveData(Method method, JoinPoint joinPoint) {
        Map<String, String> auditFieldData = new HashMap<>();

        for (int i = 0; i < method.getParameterCount(); i++) {
            Parameter parameter = method.getParameters()[i];
            AuditField auditField = parameter.getAnnotation(AuditField.class);
            if (auditField != null) {
                String key =
                        StringUtils.isNotEmpty(auditField.value()) ? auditField.value() : parameter.getName();

                Object value = joinPoint.getArgs()[i];

                auditFieldData.put(key, String.valueOf(value));
            }
        }
        return auditFieldData;
    }
}
