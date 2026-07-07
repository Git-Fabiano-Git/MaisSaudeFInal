package com.clinica.dto;

import jakarta.validation.constraints.NotBlank;

public record EspecialidadeDTO(@NotBlank String descricao) {}
