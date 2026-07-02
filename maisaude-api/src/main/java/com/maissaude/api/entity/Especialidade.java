package com.maissaude.api.entity;

import jakarta.persistence.*;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.Size;
import lombok.Data;
import com.fasterxml.jackson.annotation.JsonIgnore;
import java.util.HashSet;
import java.util.Set;

@Data
@Entity
@Table(name = "especialidade")
public class Especialidade {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Integer id;

    @NotBlank(message = "Nome da especialidade e obrigatorio")
    @Size(max = 100)
    @Column(nullable = false, unique = true, length = 100)
    private String nome;

    @ManyToMany(mappedBy = "especialidades")
    @JsonIgnore
    private Set<Medico> medicos = new HashSet<>();

    // Muitos-para-muitos com Clinica (uma clinica oferece varias especialidades)
    @ManyToMany(mappedBy = "especialidades")
    @JsonIgnore
    private Set<Clinica> clinicas = new HashSet<>();
}
