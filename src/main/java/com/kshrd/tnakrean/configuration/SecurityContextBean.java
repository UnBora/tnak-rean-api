package com.kshrd.tnakrean.configuration;

import com.kshrd.tnakrean.model.user.response.AppUserResponse;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.stereotype.Component;

@Component
public class SecurityContextBean {

    public static AppUserResponse getRequestingUser() {
        Authentication authentication = SecurityContextHolder.getContext().getAuthentication();
        return (AppUserResponse) authentication.getPrincipal();
    }
}
