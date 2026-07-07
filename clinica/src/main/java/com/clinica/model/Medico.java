package com.clinica.model;

import jakarta.persistence.*;
import jakarta.validation.constraints.NotBlank;
import lombok.*;
import java.util.HashSet;
import java.util.Set;

@Entity
@Getter @Setter @NoArgsConstructor @AllArgsConstructor @EqualsAndHashCode(onlyExplicitlyIncluded = true)
public class Medico {
    @Id @GeneratedValue(strategy = GenerationType.IDENTITY)
    @EqualsAndHashCode.Include
    private Long id;

    @NotBlank
    @Column(unique = true)
    private String crm;

    @NotBlank
    private String nome;

    @OneToMany(mappedBy = "medico")
    private Set<Consulta> consultas = new HashSet<>();

    @ManyToMany
    @JoinTable(name = "medico_especialidade",
            joinColumns = @JoinColumn(name = "medico_id"),
            inverseJoinColumns = @JoinColumn(name = "especialidade_id"))
    private Set<Especialidade> especialidades = new HashSet<>();

    @ManyToMany
    @JoinTable(name = "medico_clinica",
            joinColumns = @JoinColumn(name = "medico_id"),
            inverseJoinColumns = @JoinColumn(name = "clinica_id"))
    private Set<Clinica> clinicas = new HashSet<>();
}
