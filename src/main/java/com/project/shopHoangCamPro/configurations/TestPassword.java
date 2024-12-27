package com.project.shopHoangCamPro.configurations;

import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;

public class TestPassword {
    public static void main(String[] ags){
        System.out.println(new BCryptPasswordEncoder().encode("123456"));
    }
}
