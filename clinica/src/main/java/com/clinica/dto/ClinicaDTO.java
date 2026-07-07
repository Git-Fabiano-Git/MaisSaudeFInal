package com.clinica.dto;

import jakarta.validation.constraints.NotBlank;

public record ClinicaDTO(@NotBlank String nome, String endereco, String telefone) {}
