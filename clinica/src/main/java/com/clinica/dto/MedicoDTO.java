package com.clinica.dto;

import jakarta.validation.constraints.NotBlank;
import java.util.Set;

public record MedicoDTO(
        @NotBlank String crm,
        @NotBlank String nome,
        Set<Long> especialidadeIds,
        Set<Long> clinicaIds
) {}
