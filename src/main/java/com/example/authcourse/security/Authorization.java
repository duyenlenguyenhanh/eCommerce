package com.example.authcourse.security;

import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;

public class Authorization {
    private Authorization() {
    }

    public static boolean checkPersonalRight(String username) {
        Authentication authentication = SecurityContextHolder.getContext().getAuthentication();
        String currentUsername = authentication.getPrincipal().toString();
        return username.equals(currentUsername);
    }
}
