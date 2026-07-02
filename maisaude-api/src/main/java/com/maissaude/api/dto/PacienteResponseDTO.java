package com.maissaude.api.dto;

import java.time.LocalDate;

public record PacienteResponseDTO(
        Integer id,
        String nome,
        String cpf,
        LocalDate dataNascimento,
        String email,
        String telefone,
        Boolean ativo
) {}
