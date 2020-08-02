package com.zhang.ddd.domain.util;

import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.security.crypto.password.PasswordEncoder;

public class UserPasswordEncoder {

    private static BCryptPasswordEncoder passwordEncoder = new BCryptPasswordEncoder();

    public static PasswordEncoder getPasswordEncoder() {
        return passwordEncoder;
    }


    public static String encode(String password) {
        return passwordEncoder.encode(password);
    }
}
