package com.maissaude.api.service;

import com.maissaude.api.dto.EspecialidadeDTO;
import com.maissaude.api.entity.Especialidade;
import com.maissaude.api.exception.RecursoNaoEncontradoException;
import com.maissaude.api.repository.EspecialidadeRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
@RequiredArgsConstructor
public class EspecialidadeService {

    private final EspecialidadeRepository repository;

    public List<EspecialidadeDTO> listar() {
        return repository.findAll().stream().map(this::toDTO).toList();
    }

    public EspecialidadeDTO buscarPorId(Integer id) {
        return toDTO(buscarEntidade(id));
    }

    public Especialidade buscarEntidade(Integer id) {
        return repository.findById(id)
                .orElseThrow(() -> new RecursoNaoEncontradoException("Especialidade nao encontrada: " + id));
    }

    public EspecialidadeDTO criar(EspecialidadeDTO dto) {
        Especialidade e = new Especialidade();
        e.setNome(dto.nome());
        return toDTO(repository.save(e));
    }

    public EspecialidadeDTO atualizar(Integer id, EspecialidadeDTO dto) {
        Especialidade e = buscarEntidade(id);
        e.setNome(dto.nome());
        return toDTO(repository.save(e));
    }

    public void deletar(Integer id) {
        repository.delete(buscarEntidade(id));
    }

    private EspecialidadeDTO toDTO(Especialidade e) {
        return new EspecialidadeDTO(e.getId(), e.getNome());
    }
}
