package com.maissaude.api.dto;

import java.util.Set;

public record MedicoResponseDTO(
        Integer id,
        String nome,
        String crm,
        String email,
        String telefone,
        Boolean ativo,
        Set<String> especialidades,
        Set<String> clinicas
) {}
