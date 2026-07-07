package com.clinica.service;

import com.clinica.dto.EspecialidadeDTO;
import com.clinica.model.Especialidade;
import com.clinica.repository.EspecialidadeRepository;
import org.springframework.stereotype.Service;
import java.util.List;

@Service
public class EspecialidadeService {
    private final EspecialidadeRepository repository;

    public EspecialidadeService(EspecialidadeRepository repository) {
        this.repository = repository;
    }

    public List<Especialidade> listar() {
        return repository.findAll();
    }

    public Especialidade buscarPorId(Long id) {
        return repository.findById(id)
                .orElseThrow(() -> new RuntimeException("Especialidade não encontrada"));
    }

    public Especialidade criar(EspecialidadeDTO dto) {
        Especialidade e = new Especialidade();
        e.setDescricao(dto.descricao());
        return repository.save(e);
    }

    public Especialidade atualizar(Long id, EspecialidadeDTO dto) {
        Especialidade e = buscarPorId(id);
        e.setDescricao(dto.descricao());
        return repository.save(e);
    }

    public void deletar(Long id) {
        repository.deleteById(id);
    }
}
