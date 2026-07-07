package com.clinica;

import org.springframework.boot.CommandLineRunner;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.stereotype.Component;

@Component
public class HashGeneratorRunner implements CommandLineRunner {

    @Override
    public void run(String... args) {
        BCryptPasswordEncoder encoder = new BCryptPasswordEncoder();

        String hashAdmin = encoder.encode("admin123");
        String hashMedico = encoder.encode("medico123");
        String hashPaciente = encoder.encode("paciente123");

        System.out.println("============================================");
        System.out.println("Hash para 'admin123':    " + hashAdmin);
        System.out.println("Hash para 'medico123':   " + hashMedico);
        System.out.println("Hash para 'paciente123': " + hashPaciente);
        System.out.println("============================================");
    }
}

// 1o hash gerado para admin:     $2a$10$SzycFVI22jD8AHh7MNVW3.xKzs8onkz11U8bFHXLWiu0t4ZA5s9pG
// 1o hasg para medico:       $2a$10$sFqkCSLorDy4SBlCs9NX5uQQTCIFQJrr9GOKNS0bSP9j45Y6AUn8y
// 1o hash para paciente:         $2a$10$6bMFNHIl/fLDgo7cKn9AB.PAbUr/y7phshpaPIxaoB00PkaJIlj8a
// qualquer um deve funcionar
