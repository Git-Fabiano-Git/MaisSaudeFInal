package com.clinica;

import org.springframework.boot.CommandLineRunner;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.stereotype.Component;

@Component
public class HashGeneratorRunner implements CommandLineRunner {

    @Override
    public void run(String... args) {
        BCryptPasswordEncoder encoder = new BCryptPasswordEncoder();
        String hash = encoder.encode("admin123");
        System.out.println("============================================");
        System.out.println("Hash BCrypt para 'admin123':");
        System.out.println(hash);
        System.out.println("============================================");
    }
}

// hash gerado:     $2a$10$SzycFVI22jD8AHh7MNVW3.xKzs8onkz11U8bFHXLWiu0t4ZA5s9pG