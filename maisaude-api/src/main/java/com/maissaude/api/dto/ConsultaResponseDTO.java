package com.maissaude.api.dto;

import java.time.LocalDateTime;

public record ConsultaResponseDTO(
        Integer id,
        LocalDateTime dataHora,
        String status,
        String observacao,
        Integer medicoId,
        String medicoNome,
        Integer pacienteId,
        String pacienteNome,
        Integer clinicaId,
        String clinicaNome
) {}
