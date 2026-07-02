package com.maissaude.api.service;

import com.maissaude.api.dto.PacienteRequestDTO;
import com.maissaude.api.dto.PacienteResponseDTO;
import com.maissaude.api.entity.Paciente;
import com.maissaude.api.exception.RecursoNaoEncontradoException;
import com.maissaude.api.repository.PacienteRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
@RequiredArgsConstructor
public class PacienteService {

    private final PacienteRepository repository;

    public List<PacienteResponseDTO> listar() {
        return repository.findAll().stream().map(this::toDTO).toList();
    }

    public PacienteResponseDTO buscarPorId(Integer id) {
        return toDTO(buscarEntidade(id));
    }

    public Paciente buscarEntidade(Integer id) {
        return repository.findById(id)
                .orElseThrow(() -> new RecursoNaoEncontradoException("Paciente nao encontrado: " + id));
    }

    public PacienteResponseDTO criar(PacienteRequestDTO dto) {
        Paciente p = new Paciente();
        aplicarDados(p, dto);
        return toDTO(repository.save(p));
    }

    public PacienteResponseDTO atualizar(Integer id, PacienteRequestDTO dto) {
        Paciente p = buscarEntidade(id);
        aplicarDados(p, dto);
        return toDTO(repository.save(p));
    }

    public void deletar(Integer id) {
        repository.delete(buscarEntidade(id));
    }

    private void aplicarDados(Paciente p, PacienteRequestDTO dto) {
        p.setNome(dto.nome());
        p.setCpf(dto.cpf());
        p.setDataNascimento(dto.dataNascimento());
        p.setEmail(dto.email());
        p.setTelefone(dto.telefone());
        p.setAtivo(dto.ativo());
    }

    private PacienteResponseDTO toDTO(Paciente p) {
        return new PacienteResponseDTO(p.getId(), p.getNome(), p.getCpf(), p.getDataNascimento(), p.getEmail(), p.getTelefone(), p.getAtivo());
    }
}
