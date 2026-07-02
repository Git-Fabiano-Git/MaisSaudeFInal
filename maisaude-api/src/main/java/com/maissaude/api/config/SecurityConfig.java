package com.maissaude.api.config;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.maissaude.api.dto.ErroRespostaDTO;
import com.maissaude.api.security.CustomUserDetailsService;
import com.maissaude.api.security.JwtAuthFilter;
import jakarta.servlet.http.HttpServletResponse;
import lombok.RequiredArgsConstructor;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.http.HttpMethod;
import org.springframework.http.MediaType;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.dao.DaoAuthenticationProvider;
import org.springframework.security.config.annotation.authentication.configuration.AuthenticationConfiguration;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.configuration.EnableWebSecurity;
import org.springframework.security.config.http.SessionCreationPolicy;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.security.web.SecurityFilterChain;
import org.springframework.security.web.authentication.UsernamePasswordAuthenticationFilter;
import org.springframework.security.web.header.writers.frameoptions.XFrameOptionsHeaderWriter;

import java.time.LocalDateTime;
import java.util.List;

@Configuration
@EnableWebSecurity
@RequiredArgsConstructor
public class SecurityConfig {

    private final CustomUserDetailsService userDetailsService;
    private final JwtAuthFilter jwtAuthFilter;
    private final ObjectMapper objectMapper;

    @Bean
    public PasswordEncoder passwordEncoder() {
        return new BCryptPasswordEncoder();
    }

    @Bean
    public DaoAuthenticationProvider authenticationProvider() {
        DaoAuthenticationProvider provider = new DaoAuthenticationProvider();
        provider.setUserDetailsService(userDetailsService);
        provider.setPasswordEncoder(passwordEncoder());
        return provider;
    }

    @Bean
    public AuthenticationManager authenticationManager(AuthenticationConfiguration config) throws Exception {
        return config.getAuthenticationManager();
    }

    @Bean
    public SecurityFilterChain filterChain(HttpSecurity http) throws Exception {
        http
            .csrf(csrf -> csrf.disable())
            .sessionManagement(sm -> sm.sessionCreationPolicy(SessionCreationPolicy.STATELESS))
            .authorizeHttpRequests(auth -> auth
                    .requestMatchers("/api/auth/**").permitAll()
                    .requestMatchers("/swagger-ui/**", "/v3/api-docs/**", "/swagger-ui.html").permitAll()
                    .requestMatchers("/h2-console/**").permitAll()

                    // Leitura (GET): qualquer usuario autenticado, ADMIN ou USER
                    .requestMatchers(HttpMethod.GET, "/api/**").hasAnyRole("ADMIN", "USER")

                    // Cadastros administrativos (medicos, clinicas, especialidades): somente ADMIN
                    .requestMatchers(HttpMethod.POST, "/api/medicos/**", "/api/clinicas/**", "/api/especialidades/**").hasRole("ADMIN")
                    .requestMatchers(HttpMethod.PUT, "/api/medicos/**", "/api/clinicas/**", "/api/especialidades/**").hasRole("ADMIN")
                    .requestMatchers(HttpMethod.DELETE, "/api/medicos/**", "/api/clinicas/**", "/api/especialidades/**").hasRole("ADMIN")

                    // Atendimento (pacientes e consultas): ADMIN e USER (recepcao) podem criar/atualizar
                    .requestMatchers(HttpMethod.POST, "/api/pacientes/**", "/api/consultas/**").hasAnyRole("ADMIN", "USER")
                    .requestMatchers(HttpMethod.PUT, "/api/pacientes/**", "/api/consultas/**").hasAnyRole("ADMIN", "USER")

                    // Exclusao de pacientes/consultas: somente ADMIN
                    .requestMatchers(HttpMethod.DELETE, "/api/pacientes/**", "/api/consultas/**").hasRole("ADMIN")

                    .anyRequest().authenticated()
            )
            .exceptionHandling(ex -> ex
                    .accessDeniedHandler((request, response, accessDeniedException) -> {
                        response.setStatus(HttpServletResponse.SC_FORBIDDEN);
                        response.setContentType(MediaType.APPLICATION_JSON_VALUE);
                        ErroRespostaDTO erro = new ErroRespostaDTO(
                                LocalDateTime.now(), 403, "Acesso negado",
                                List.of("Seu usuario nao tem permissao para executar esta operacao"));
                        response.getWriter().write(objectMapper.writeValueAsString(erro));
                    })
            )
            .headers(headers -> headers.frameOptions(frame -> frame.sameOrigin())) // necessario para o H2 console
            .authenticationProvider(authenticationProvider())
            .addFilterBefore(jwtAuthFilter, UsernamePasswordAuthenticationFilter.class);

        return http.build();
    }
}
