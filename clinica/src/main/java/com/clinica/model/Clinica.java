package com.clinica.model;

import jakarta.persistence.*;
import jakarta.validation.constraints.NotBlank;
import lombok.*;
import java.util.HashSet;
import java.util.Set;

@Entity
@Getter @Setter @NoArgsConstructor @AllArgsConstructor @EqualsAndHashCode(onlyExplicitlyIncluded = true)
public class Clinica {
    @Id @GeneratedValue(strategy = GenerationType.IDENTITY)
    @EqualsAndHashCode.Include
    private Long id;

    @NotBlank
    private String nome;
    private String endereco;
    private String telefone;

    @OneToMany(mappedBy = "clinica")
    private Set<Consulta> consultas = new HashSet<>();

    @ManyToMany
    @JoinTable(name = "clinica_especialidade",
            joinColumns = @JoinColumn(name = "clinica_id"),
            inverseJoinColumns = @JoinColumn(name = "especialidade_id"))
    private Set<Especialidade> especialidades = new HashSet<>();

    @ManyToMany(mappedBy = "clinicas")
    private Set<Medico> medicos = new HashSet<>();
}
