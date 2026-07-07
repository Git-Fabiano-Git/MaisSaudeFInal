package com.clinica.service;

import com.clinica.dto.LoginRequest;
import com.clinica.model.Usuario;
import com.clinica.repository.UsuarioRepository;
import com.clinica.security.JwtUtil;
import org.springframework.security.authentication.BadCredentialsException;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;

@Service
public class AuthService {
    private final UsuarioRepository usuarioRepository;
    private final PasswordEncoder passwordEncoder;
    private final JwtUtil jwtUtil;

    public AuthService(UsuarioRepository usuarioRepository, PasswordEncoder passwordEncoder, JwtUtil jwtUtil) {
        this.usuarioRepository = usuarioRepository;
        this.passwordEncoder = passwordEncoder;
        this.jwtUtil = jwtUtil;
    }

    public String authenticate(LoginRequest request) {
        Usuario usuario = usuarioRepository.findByLogin(request.login())
                .orElseThrow(() -> new BadCredentialsException("Credenciais inválidas"));
        if (!passwordEncoder.matches(request.senha(), usuario.getSenha())) {
            throw new BadCredentialsException("Credenciais inválidas");
        }
        return jwtUtil.generateToken(usuario.getLogin(),
                usuario.getRoles().stream().map(role -> role.getNome()).toList());
    }
}
