package com.clinica.model;

import jakarta.persistence.*;
import jakarta.validation.constraints.NotBlank;
import lombok.*;
import java.time.LocalDate;
import java.util.HashSet;
import java.util.Set;

@Entity
@Getter @Setter @NoArgsConstructor @AllArgsConstructor @EqualsAndHashCode(onlyExplicitlyIncluded = true)
public class Paciente {
    @Id @GeneratedValue(strategy = GenerationType.IDENTITY)
    @EqualsAndHashCode.Include
    private Long id;

    @NotBlank
    @Column(unique = true)
    private String cpf;

    @NotBlank
    private String nome;
    private LocalDate dataNascimento;
    private String telefone;

    @OneToMany(mappedBy = "paciente")
    private Set<Consulta> consultas = new HashSet<>();
}
