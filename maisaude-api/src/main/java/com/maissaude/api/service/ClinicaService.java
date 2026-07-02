package com.maissaude.api.service;

import com.maissaude.api.dto.ClinicaRequestDTO;
import com.maissaude.api.dto.ClinicaResponseDTO;
import com.maissaude.api.entity.Clinica;
import com.maissaude.api.entity.Especialidade;
import com.maissaude.api.exception.RecursoNaoEncontradoException;
import com.maissaude.api.repository.ClinicaRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.util.HashSet;
import java.util.List;
import java.util.Set;
import java.util.stream.Collectors;

@Service
@RequiredArgsConstructor
public class ClinicaService {

    private final ClinicaRepository repository;
    private final EspecialidadeService especialidadeService;

    public List<ClinicaResponseDTO> listar() {
        return repository.findAll().stream().map(this::toDTO).toList();
    }

    public ClinicaResponseDTO buscarPorId(Integer id) {
        return toDTO(buscarEntidade(id));
    }

    public Clinica buscarEntidade(Integer id) {
        return repository.findById(id)
                .orElseThrow(() -> new RecursoNaoEncontradoException("Clinica nao encontrada: " + id));
    }

    public ClinicaResponseDTO criar(ClinicaRequestDTO dto) {
        Clinica c = new Clinica();
        aplicarDados(c, dto);
        return toDTO(repository.save(c));
    }

    public ClinicaResponseDTO atualizar(Integer id, ClinicaRequestDTO dto) {
        Clinica c = buscarEntidade(id);
        aplicarDados(c, dto);
        return toDTO(repository.save(c));
    }

    public void deletar(Integer id) {
        repository.delete(buscarEntidade(id));
    }

    private void aplicarDados(Clinica c, ClinicaRequestDTO dto) {
        c.setNome(dto.nome());
        c.setCnpj(dto.cnpj());
        c.setEndereco(dto.endereco());
        if (dto.especialidadeIds() != null) {
            Set<Especialidade> especialidades = dto.especialidadeIds().stream()
                    .map(especialidadeService::buscarEntidade)
                    .collect(Collectors.toSet());
            c.setEspecialidades(especialidades);
        } else {
            c.setEspecialidades(new HashSet<>());
        }
    }

    private ClinicaResponseDTO toDTO(Clinica c) {
        Set<String> especialidades = c.getEspecialidades().stream().map(Especialidade::getNome).collect(Collectors.toSet());
        return new ClinicaResponseDTO(c.getId(), c.getNome(), c.getCnpj(), c.getEndereco(), especialidades);
    }
}
