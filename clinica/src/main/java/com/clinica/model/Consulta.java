package com.clinica.model;

import jakarta.persistence.*;
import jakarta.validation.constraints.FutureOrPresent;
import jakarta.validation.constraints.NotNull;
import lombok.*;
import java.time.LocalDateTime;

@Entity
@Getter @Setter @NoArgsConstructor @AllArgsConstructor @EqualsAndHashCode(onlyExplicitlyIncluded = true)
public class Consulta {
    @Id @GeneratedValue(strategy = GenerationType.IDENTITY)
    @EqualsAndHashCode.Include
    private Long id;

    @NotNull @FutureOrPresent
    private LocalDateTime dataHora;

    private String status; // AGENDADA, CONFIRMADA, CANCELADA

    @ManyToOne(optional = false)
    @JoinColumn(name = "medico_id")
    private Medico medico;

    @ManyToOne(optional = false)
    @JoinColumn(name = "paciente_id")
    private Paciente paciente;

    @ManyToOne(optional = false)
    @JoinColumn(name = "clinica_id")
    private Clinica clinica;
}
