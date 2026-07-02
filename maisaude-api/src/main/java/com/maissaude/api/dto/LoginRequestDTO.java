package com.maissaude.api.dto;

import jakarta.validation.constraints.NotBlank;

public record LoginRequestDTO(
        @NotBlank(message = "Usuario e obrigatorio") String username,
        @NotBlank(message = "Senha e obrigatoria") String senha
) {}
