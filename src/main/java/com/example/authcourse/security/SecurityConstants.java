package com.example.authcourse.security;

public class SecurityConstants {
    private SecurityConstants() {
    }

    public static final String SECRET_KEY = "duyenlnhKey";
    public static final long EXPIRATION_TIME = 432_000_000; // 5 days
    public static final String TOKEN_PREFIX = "Bearer ";
    public static final String HEADER_AUTHORIZATION = "Authorization";
    public static final String SIGN_UP_URL = "/api/user/create";
}
