package com.maissaude.api.dto;

import jakarta.validation.constraints.*;
import java.util.Set;

public record MedicoRequestDTO(
        @NotBlank(message = "Nome e obrigatorio") String nome,
        @NotBlank(message = "CRM e obrigatorio") String crm,
        @NotBlank @Email(message = "Email invalido") String email,
        @NotBlank @Pattern(regexp = "\\d{10,11}", message = "Telefone deve conter 10 ou 11 digitos") String telefone,
        @NotNull(message = "Campo ativo e obrigatorio") Boolean ativo,
        Set<Integer> especialidadeIds,
        Set<Integer> clinicaIds
) {}
