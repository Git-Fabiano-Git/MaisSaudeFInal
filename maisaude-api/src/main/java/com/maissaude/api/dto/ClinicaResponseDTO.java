package com.maissaude.api.dto;

import java.util.Set;

public record ClinicaResponseDTO(
        Integer id,
        String nome,
        String cnpj,
        String endereco,
        Set<String> especialidades
) {}
