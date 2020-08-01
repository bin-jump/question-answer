package com.zhang.ddd.domain.util;

import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;

public class PasswordEncoder {

    private static BCryptPasswordEncoder passwordEncoder = new BCryptPasswordEncoder();


    public static String encode(String password) {
        return passwordEncoder.encode(password);
    }
}
