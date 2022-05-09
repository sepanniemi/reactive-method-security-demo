package com.sepanniemi.demo;

import lombok.extern.slf4j.Slf4j;
import org.springframework.security.access.PermissionEvaluator;
import org.springframework.security.core.Authentication;
import org.springframework.stereotype.Component;

import java.io.Serializable;

@Component
@Slf4j
public class PermissionService implements PermissionEvaluator {

    public boolean hasPermission(String permission, Authentication authentication) {
        return authentication.getAuthorities()
                .stream()
                .anyMatch(ga -> ga.getAuthority().equals(permission));
    }

    @Override
    public boolean hasPermission(Authentication authentication, Object targetDomainObject, Object permission) {
        log.info("permission evaluator called");
        return hasPermission(targetDomainObject.toString()+"."+permission.toString(), authentication);
    }

    @Override
    public boolean hasPermission(Authentication authentication, Serializable targetId, String targetType, Object permission) {
        log.info("permission evaluator called");
        return hasPermission(targetType+"."+permission.toString(), authentication);
    }
}
