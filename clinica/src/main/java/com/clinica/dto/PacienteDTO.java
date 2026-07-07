package com.clinica.dto;

import jakarta.validation.constraints.NotBlank;
import java.time.LocalDate;

public record PacienteDTO(
        @NotBlank String cpf,
        @NotBlank String nome,
        LocalDate dataNascimento,
        String telefone
) {}
