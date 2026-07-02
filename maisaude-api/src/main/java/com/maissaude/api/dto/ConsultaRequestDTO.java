package com.maissaude.api.dto;

import jakarta.validation.constraints.*;
import java.time.LocalDateTime;

public record ConsultaRequestDTO(
        @NotNull(message = "Data/hora e obrigatoria")
        @Future(message = "Data/hora da consulta deve estar no futuro")
        LocalDateTime dataHora,
        @NotBlank(message = "Status e obrigatorio")
        @Pattern(regexp = "AGENDADA|REALIZADA|CANCELADA", message = "Status deve ser AGENDADA, REALIZADA ou CANCELADA")
        String status,
        String observacao,
        @NotNull(message = "Medico e obrigatorio") Integer medicoId,
        @NotNull(message = "Paciente e obrigatorio") Integer pacienteId,
        @NotNull(message = "Clinica e obrigatoria") Integer clinicaId
) {}
