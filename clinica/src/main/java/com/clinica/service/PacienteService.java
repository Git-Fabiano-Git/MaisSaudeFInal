package com.clinica.service;

import com.clinica.dto.PacienteDTO;
import com.clinica.model.Paciente;
import com.clinica.repository.PacienteRepository;
import org.springframework.stereotype.Service;
import java.util.List;

@Service
public class PacienteService {
    private final PacienteRepository repository;

    public PacienteService(PacienteRepository repository) {
        this.repository = repository;
    }

    public List<Paciente> listar() {
        return repository.findAll();
    }

    public Paciente buscarPorId(Long id) {
        return repository.findById(id)
                .orElseThrow(() -> new RuntimeException("Paciente não encontrado"));
    }

    public Paciente criar(PacienteDTO dto) {
        Paciente p = new Paciente();
        p.setCpf(dto.cpf());
        p.setNome(dto.nome());
        p.setDataNascimento(dto.dataNascimento());
        p.setTelefone(dto.telefone());
        return repository.save(p);
    }

    public Paciente atualizar(Long id, PacienteDTO dto) {
        Paciente p = buscarPorId(id);
        p.setCpf(dto.cpf());
        p.setNome(dto.nome());
        p.setDataNascimento(dto.dataNascimento());
        p.setTelefone(dto.telefone());
        return repository.save(p);
    }

    public void deletar(Long id) {
        repository.deleteById(id);
    }
}
