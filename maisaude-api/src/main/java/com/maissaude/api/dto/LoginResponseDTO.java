package com.maissaude.api.dto;

public record LoginResponseDTO(String token, String tipo) {
    public LoginResponseDTO(String token) {
        this(token, "Bearer");
    }
}
