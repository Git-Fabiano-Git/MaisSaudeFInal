package com.clinica.model;

import jakarta.persistence.*;
import jakarta.validation.constraints.NotBlank;
import lombok.*;
import java.util.HashSet;
import java.util.Set;

@Entity
@Getter @Setter @NoArgsConstructor @AllArgsConstructor @EqualsAndHashCode(onlyExplicitlyIncluded = true)
public class Especialidade {
    @Id @GeneratedValue(strategy = GenerationType.IDENTITY)
    @EqualsAndHashCode.Include
    private Long id;

    @NotBlank
    @Column(unique = true)
    private String descricao;

    @ManyToMany(mappedBy = "especialidades")
    private Set<Medico> medicos = new HashSet<>();

    @ManyToMany(mappedBy = "especialidades")
    private Set<Clinica> clinicas = new HashSet<>();
}
