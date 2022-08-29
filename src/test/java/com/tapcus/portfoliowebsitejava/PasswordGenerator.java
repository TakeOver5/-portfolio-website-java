package com.tapcus.portfoliowebsitejava;

import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.security.crypto.password.PasswordEncoder;

public class PasswordGenerator {

    public static void main(String[] args) {
        PasswordEncoder pe = new BCryptPasswordEncoder();
        String encode = pe.encode("a12345678");
        System.out.println(encode);
        boolean matches = pe.matches("a12345678", encode);
        System.out.println(matches);
        System.out.println();

        String encode2 = pe.encode("a87654321");
        System.out.println(encode2);
        boolean matches2 = pe.matches("a87654321", encode2);
        System.out.println(matches2);
        System.out.println();

        String encode3 = pe.encode("m12345678");
        System.out.println(encode3);
        boolean matches3 = pe.matches("m12345678", encode3);
        System.out.println(matches3);
    }
}
