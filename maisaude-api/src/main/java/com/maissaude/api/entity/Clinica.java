package com.maissaude.api.entity;

import jakarta.persistence.*;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.Pattern;
import jakarta.validation.constraints.Size;
import lombok.Data;
import com.fasterxml.jackson.annotation.JsonIgnore;
import java.util.HashSet;
import java.util.Set;

@Data
@Entity
@Table(name = "clinica")
public class Clinica {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Integer id;

    @NotBlank(message = "Nome e obrigatorio")
    @Size(max = 255)
    @Column(nullable = false, length = 255)
    private String nome;

    @NotBlank(message = "CNPJ e obrigatorio")
    @Pattern(regexp = "\\d{2}\\.\\d{3}\\.\\d{3}/\\d{4}-\\d{2}", message = "CNPJ deve seguir o formato 00.000.000/0000-00")
    @Column(nullable = false, unique = true, length = 18)
    private String cnpj;

    @NotBlank(message = "Endereco e obrigatorio")
    @Size(max = 255)
    @Column(length = 255)
    private String endereco;

    // Muitos-para-muitos com Medico (lado inverso)
    @ManyToMany(mappedBy = "clinicas")
    @JsonIgnore
    private Set<Medico> medicos = new HashSet<>();

    // Muitos-para-muitos com Especialidade (3o relacionamento N:N do sistema)
    @ManyToMany
    @JoinTable(
            name = "clinica_especialidade",
            joinColumns = @JoinColumn(name = "id_clinica"),
            inverseJoinColumns = @JoinColumn(name = "id_especialidade")
    )
    private Set<Especialidade> especialidades = new HashSet<>();

    // Uma clinica tem muitas consultas (1:N)
    @OneToMany(mappedBy = "clinica", cascade = CascadeType.ALL)
    @JsonIgnore
    private Set<Consulta> consultas = new HashSet<>();
}
