package com.maissaude.api.service;

import com.maissaude.api.dto.MedicoRequestDTO;
import com.maissaude.api.dto.MedicoResponseDTO;
import com.maissaude.api.entity.Clinica;
import com.maissaude.api.entity.Especialidade;
import com.maissaude.api.entity.Medico;
import com.maissaude.api.exception.RecursoNaoEncontradoException;
import com.maissaude.api.repository.MedicoRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.util.HashSet;
import java.util.List;
import java.util.Set;
import java.util.stream.Collectors;

@Service
@RequiredArgsConstructor
public class MedicoService {

    private final MedicoRepository repository;
    private final EspecialidadeService especialidadeService;
    private final ClinicaService clinicaService;

    public List<MedicoResponseDTO> listar() {
        return repository.findAll().stream().map(this::toDTO).toList();
    }

    public MedicoResponseDTO buscarPorId(Integer id) {
        return toDTO(buscarEntidade(id));
    }

    public Medico buscarEntidade(Integer id) {
        return repository.findById(id)
                .orElseThrow(() -> new RecursoNaoEncontradoException("Medico nao encontrado: " + id));
    }

    public MedicoResponseDTO criar(MedicoRequestDTO dto) {
        Medico m = new Medico();
        aplicarDados(m, dto);
        return toDTO(repository.save(m));
    }

    public MedicoResponseDTO atualizar(Integer id, MedicoRequestDTO dto) {
        Medico m = buscarEntidade(id);
        aplicarDados(m, dto);
        return toDTO(repository.save(m));
    }

    public void deletar(Integer id) {
        repository.delete(buscarEntidade(id));
    }

    private void aplicarDados(Medico m, MedicoRequestDTO dto) {
        m.setNome(dto.nome());
        m.setCrm(dto.crm());
        m.setEmail(dto.email());
        m.setTelefone(dto.telefone());
        m.setAtivo(dto.ativo());

        if (dto.especialidadeIds() != null) {
            Set<Especialidade> especialidades = dto.especialidadeIds().stream()
                    .map(especialidadeService::buscarEntidade)
                    .collect(Collectors.toSet());
            m.setEspecialidades(especialidades);
        } else {
            m.setEspecialidades(new HashSet<>());
        }

        if (dto.clinicaIds() != null) {
            Set<Clinica> clinicas = dto.clinicaIds().stream()
                    .map(clinicaService::buscarEntidade)
                    .collect(Collectors.toSet());
            m.setClinicas(clinicas);
        } else {
            m.setClinicas(new HashSet<>());
        }
    }

    private MedicoResponseDTO toDTO(Medico m) {
        Set<String> especialidades = m.getEspecialidades().stream().map(Especialidade::getNome).collect(Collectors.toSet());
        Set<String> clinicas = m.getClinicas().stream().map(Clinica::getNome).collect(Collectors.toSet());
        return new MedicoResponseDTO(m.getId(), m.getNome(), m.getCrm(), m.getEmail(), m.getTelefone(), m.getAtivo(), especialidades, clinicas);
    }
}
