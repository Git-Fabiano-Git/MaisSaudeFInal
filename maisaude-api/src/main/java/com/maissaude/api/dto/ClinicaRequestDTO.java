package com.maissaude.api.dto;

import jakarta.validation.constraints.*;
import java.util.Set;

public record ClinicaRequestDTO(
        @NotBlank(message = "Nome e obrigatorio") String nome,
        @NotBlank @Pattern(regexp = "\\d{2}\\.\\d{3}\\.\\d{3}/\\d{4}-\\d{2}", message = "CNPJ deve seguir o formato 00.000.000/0000-00") String cnpj,
        @NotBlank(message = "Endereco e obrigatorio") String endereco,
        Set<Integer> especialidadeIds
) {}
