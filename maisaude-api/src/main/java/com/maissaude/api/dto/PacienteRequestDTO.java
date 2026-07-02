package com.maissaude.api.dto;

import jakarta.validation.constraints.*;
import java.time.LocalDate;

public record PacienteRequestDTO(
        @NotBlank(message = "Nome e obrigatorio") String nome,
        @NotBlank @Pattern(regexp = "\\d{11}", message = "CPF deve conter 11 digitos") String cpf,
        @NotNull @Past(message = "Data de nascimento deve estar no passado") LocalDate dataNascimento,
        @NotBlank @Email(message = "Email invalido") String email,
        @NotBlank @Pattern(regexp = "\\d{10,11}", message = "Telefone deve conter 10 ou 11 digitos") String telefone,
        @NotNull(message = "Campo ativo e obrigatorio") Boolean ativo
) {}
