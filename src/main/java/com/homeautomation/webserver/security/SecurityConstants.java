package com.homeautomation.webserver.security;

public class SecurityConstants {
    public static final String SECRET = "23908asfkKLJasdjag0425a";
    public static final long EXPIRATION_TIME = 600000; // 10 minutes
    public static final String TOKEN_PREFIX = "Bearer ";
    public static final String HEADER_STRING = "Authorization";
    public static final String SIGN_UP_URL = "/users/sign-up";
}
