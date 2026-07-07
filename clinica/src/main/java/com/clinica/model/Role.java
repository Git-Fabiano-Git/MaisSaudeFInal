package com.clinica.model;

import jakarta.persistence.*;
import lombok.*;
import org.springframework.security.core.GrantedAuthority;

@Entity
@Getter @Setter @NoArgsConstructor @AllArgsConstructor @EqualsAndHashCode(onlyExplicitlyIncluded = true)
public class Role implements GrantedAuthority {
    @Id @GeneratedValue(strategy = GenerationType.IDENTITY)
    @EqualsAndHashCode.Include
    private Long id;

    @Column(unique = true)
    private String nome; // ex: ROLE_ADMIN, ROLE_MEDICO, ROLE_PACIENTE

    @Override
    public String getAuthority() {
        return nome;
    }
}
