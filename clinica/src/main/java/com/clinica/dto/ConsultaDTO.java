package com.clinica.dto;

import jakarta.validation.constraints.FutureOrPresent;
import jakarta.validation.constraints.NotNull;
import java.time.LocalDateTime;

public record ConsultaDTO(
        @NotNull @FutureOrPresent LocalDateTime dataHora,
        String status,
        @NotNull Long medicoId,
        @NotNull Long pacienteId,
        @NotNull Long clinicaId
) {}
