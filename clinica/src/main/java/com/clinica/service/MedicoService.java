package com.clinica.service;

import com.clinica.dto.MedicoDTO;
import com.clinica.model.Clinica;
import com.clinica.model.Especialidade;
import com.clinica.model.Medico;
import com.clinica.repository.ClinicaRepository;
import com.clinica.repository.EspecialidadeRepository;
import com.clinica.repository.MedicoRepository;
import org.springframework.stereotype.Service;
import java.util.HashSet;
import java.util.List;
import java.util.Set;

@Service
public class MedicoService {
    private final MedicoRepository repository;
    private final EspecialidadeRepository especialidadeRepository;
    private final ClinicaRepository clinicaRepository;

    public MedicoService(MedicoRepository repository,
                         EspecialidadeRepository especialidadeRepository,
                         ClinicaRepository clinicaRepository) {
        this.repository = repository;
        this.especialidadeRepository = especialidadeRepository;
        this.clinicaRepository = clinicaRepository;
    }

    public List<Medico> listar() {
        return repository.findAll();
    }

    public Medico buscarPorId(Long id) {
        return repository.findById(id)
                .orElseThrow(() -> new RuntimeException("Médico não encontrado"));
    }

    public Medico criar(MedicoDTO dto) {
        Medico m = new Medico();
        m.setCrm(dto.crm());
        m.setNome(dto.nome());
        if (dto.especialidadeIds() != null) {
            Set<Especialidade> especialidades = new HashSet<>(especialidadeRepository.findAllById(dto.especialidadeIds()));
            m.setEspecialidades(especialidades);
        }
        if (dto.clinicaIds() != null) {
            Set<Clinica> clinicas = new HashSet<>(clinicaRepository.findAllById(dto.clinicaIds()));
            m.setClinicas(clinicas);
        }
        return repository.save(m);
    }

    public Medico atualizar(Long id, MedicoDTO dto) {
        Medico m = buscarPorId(id);
        m.setCrm(dto.crm());
        m.setNome(dto.nome());
        if (dto.especialidadeIds() != null) {
            Set<Especialidade> especialidades = new HashSet<>(especialidadeRepository.findAllById(dto.especialidadeIds()));
            m.setEspecialidades(especialidades);
        }
        if (dto.clinicaIds() != null) {
            Set<Clinica> clinicas = new HashSet<>(clinicaRepository.findAllById(dto.clinicaIds()));
            m.setClinicas(clinicas);
        }
        return repository.save(m);
    }

    public void deletar(Long id) {
        repository.deleteById(id);
    }
}
