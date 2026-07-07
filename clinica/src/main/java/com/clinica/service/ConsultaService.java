package com.clinica.service;

import com.clinica.dto.ConsultaDTO;
import com.clinica.model.*;
import com.clinica.repository.*;
import org.springframework.stereotype.Service;
import java.util.List;

@Service
public class ConsultaService {
    private final ConsultaRepository repository;
    private final MedicoRepository medicoRepository;
    private final PacienteRepository pacienteRepository;
    private final ClinicaRepository clinicaRepository;

    public ConsultaService(ConsultaRepository repository, MedicoRepository medicoRepository,
                           PacienteRepository pacienteRepository, ClinicaRepository clinicaRepository) {
        this.repository = repository;
        this.medicoRepository = medicoRepository;
        this.pacienteRepository = pacienteRepository;
        this.clinicaRepository = clinicaRepository;
    }

    public Consulta agendar(ConsultaDTO dto) {
        Medico medico = medicoRepository.findById(dto.medicoId())
                .orElseThrow(() -> new RuntimeException("Médico não encontrado"));
        Paciente paciente = pacienteRepository.findById(dto.pacienteId())
                .orElseThrow(() -> new RuntimeException("Paciente não encontrado"));
        Clinica clinica = clinicaRepository.findById(dto.clinicaId())
                .orElseThrow(() -> new RuntimeException("Clínica não encontrada"));
        Consulta c = new Consulta();
        c.setDataHora(dto.dataHora());
        c.setStatus(dto.status() != null ? dto.status() : "AGENDADA");
        c.setMedico(medico);
        c.setPaciente(paciente);
        c.setClinica(clinica);
        return repository.save(c);
    }

    public List<Consulta> listarTodas() {
        return repository.findAll();
    }

    public Consulta buscarPorId(Long id) {
        return repository.findById(id)
                .orElseThrow(() -> new RuntimeException("Consulta não encontrada"));
    }

    public Consulta atualizar(Long id, ConsultaDTO dto) {
        Consulta c = buscarPorId(id);
        c.setDataHora(dto.dataHora());
        c.setStatus(dto.status());
        Medico medico = medicoRepository.findById(dto.medicoId())
                .orElseThrow(() -> new RuntimeException("Médico não encontrado"));
        Paciente paciente = pacienteRepository.findById(dto.pacienteId())
                .orElseThrow(() -> new RuntimeException("Paciente não encontrado"));
        Clinica clinica = clinicaRepository.findById(dto.clinicaId())
                .orElseThrow(() -> new RuntimeException("Clínica não encontrada"));
        c.setMedico(medico);
        c.setPaciente(paciente);
        c.setClinica(clinica);
        return repository.save(c);
    }

    public void deletar(Long id) {
        repository.deleteById(id);
    }

    public List<Consulta> listarPorPaciente(Long pacienteId) {
        return repository.findByPacienteId(pacienteId);
    }

    public List<Consulta> listarPorMedico(Long medicoId) {
        return repository.findByMedicoId(medicoId);
    }
}
