package com.maissaude.api.dto;

import jakarta.validation.constraints.NotBlank;

public record EspecialidadeDTO(
        Integer id,
        @NotBlank(message = "Nome e obrigatorio") String nome
) {}
