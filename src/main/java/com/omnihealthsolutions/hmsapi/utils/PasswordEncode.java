package com.omnihealthsolutions.hmsapi.utils;

import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.security.crypto.password.PasswordEncoder;

public class PasswordEncode {

    public static void main(String[] args) {
            PasswordEncoder encoderpassword = new BCryptPasswordEncoder();
            String admin = encoderpassword.encode("password");
            System.out.println(admin);
    }
}
