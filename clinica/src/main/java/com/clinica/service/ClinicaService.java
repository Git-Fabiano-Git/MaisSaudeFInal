package com.clinica.service;

import com.clinica.dto.ClinicaDTO;
import com.clinica.model.Clinica;
import com.clinica.repository.ClinicaRepository;
import org.springframework.stereotype.Service;
import java.util.List;

@Service
public class ClinicaService {
    private final ClinicaRepository repository;

    public ClinicaService(ClinicaRepository repository) {
        this.repository = repository;
    }

    public List<Clinica> listar() {
        return repository.findAll();
    }

    public Clinica buscarPorId(Long id) {
        return repository.findById(id)
                .orElseThrow(() -> new RuntimeException("Clínica não encontrada"));
    }

    public Clinica criar(ClinicaDTO dto) {
        Clinica c = new Clinica();
        c.setNome(dto.nome());
        c.setEndereco(dto.endereco());
        c.setTelefone(dto.telefone());
        return repository.save(c);
    }

    public Clinica atualizar(Long id, ClinicaDTO dto) {
        Clinica c = buscarPorId(id);
        c.setNome(dto.nome());
        c.setEndereco(dto.endereco());
        c.setTelefone(dto.telefone());
        return repository.save(c);
    }

    public void deletar(Long id) {
        repository.deleteById(id);
    }
}
