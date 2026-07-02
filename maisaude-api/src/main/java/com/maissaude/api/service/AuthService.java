package com.maissaude.api.service;

import com.maissaude.api.dto.LoginRequestDTO;
import com.maissaude.api.dto.LoginResponseDTO;
import com.maissaude.api.security.JwtUtil;
import lombok.RequiredArgsConstructor;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.stereotype.Service;

@Service
@RequiredArgsConstructor
public class AuthService {

    private final AuthenticationManager authenticationManager;
    private final JwtUtil jwtUtil;

    public LoginResponseDTO login(LoginRequestDTO dto) {
        Authentication auth = authenticationManager.authenticate(
                new UsernamePasswordAuthenticationToken(dto.username(), dto.senha()));

        String role = auth.getAuthorities().stream()
                .findFirst().map(GrantedAuthority::getAuthority).orElse("ROLE_USER")
                .replace("ROLE_", "");

        String token = jwtUtil.gerarToken(dto.username(), role);
        return new LoginResponseDTO(token);
    }
}
