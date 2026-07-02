package com.maissaude.api.entity;

import jakarta.persistence.*;
import jakarta.validation.constraints.*;
import lombok.Data;
import com.fasterxml.jackson.annotation.JsonIgnore;
import java.time.LocalDate;
import java.util.HashSet;
import java.util.Set;

@Data
@Entity
@Table(name = "paciente")
public class Paciente {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Integer id;

    @NotBlank(message = "Nome e obrigatorio")
    @Size(max = 255)
    @Column(nullable = false, length = 255)
    private String nome;

    @NotBlank(message = "CPF e obrigatorio")
    @Pattern(regexp = "\\d{11}", message = "CPF deve conter 11 digitos")
    @Column(nullable = false, unique = true, length = 11)
    private String cpf;

    @NotNull(message = "Data de nascimento e obrigatoria")
    @Past(message = "Data de nascimento deve estar no passado")
    @Column(nullable = false)
    private LocalDate dataNascimento;

    @NotBlank(message = "Email e obrigatorio")
    @Email(message = "Email invalido")
    @Column(nullable = false, unique = true, length = 255)
    private String email;

    @NotBlank(message = "Telefone e obrigatorio")
    @Pattern(regexp = "\\d{10,11}", message = "Telefone deve conter 10 ou 11 digitos")
    @Column(nullable = false, unique = true, length = 11)
    private String telefone;

    @NotNull(message = "Situacao (ativo) e obrigatoria")
    @Column(nullable = false)
    private Boolean ativo;

    @OneToMany(mappedBy = "paciente", cascade = CascadeType.ALL)
    @JsonIgnore
    private Set<Consulta> consultas = new HashSet<>();
}
