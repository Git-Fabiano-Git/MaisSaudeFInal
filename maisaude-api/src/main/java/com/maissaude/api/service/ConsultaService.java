package com.maissaude.api.service;

import com.maissaude.api.dto.ConsultaRequestDTO;
import com.maissaude.api.dto.ConsultaResponseDTO;
import com.maissaude.api.entity.Clinica;
import com.maissaude.api.entity.Consulta;
import com.maissaude.api.entity.Medico;
import com.maissaude.api.entity.Paciente;
import com.maissaude.api.exception.RecursoNaoEncontradoException;
import com.maissaude.api.repository.ConsultaRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
@RequiredArgsConstructor
public class ConsultaService {

    private final ConsultaRepository repository;
    private final MedicoService medicoService;
    private final PacienteService pacienteService;
    private final ClinicaService clinicaService;

    public List<ConsultaResponseDTO> listar() {
        return repository.findAll().stream().map(this::toDTO).toList();
    }

    // Endpoint que junta dados de Consulta + Medico + Paciente + Clinica (mais de uma tabela)
    public List<ConsultaResponseDTO> listarPorMedico(Integer medicoId) {
        medicoService.buscarEntidade(medicoId); // valida existencia
        return repository.findByMedicoId(medicoId).stream().map(this::toDTO).toList();
    }

    // Endpoint que junta dados de Consulta + Medico + Paciente + Clinica (mais de uma tabela)
    public List<ConsultaResponseDTO> listarPorPaciente(Integer pacienteId) {
        pacienteService.buscarEntidade(pacienteId); // valida existencia
        return repository.findByPacienteId(pacienteId).stream().map(this::toDTO).toList();
    }

    public ConsultaResponseDTO buscarPorId(Integer id) {
        return toDTO(buscarEntidade(id));
    }

    public Consulta buscarEntidade(Integer id) {
        return repository.findById(id)
                .orElseThrow(() -> new RecursoNaoEncontradoException("Consulta nao encontrada: " + id));
    }

    public ConsultaResponseDTO criar(ConsultaRequestDTO dto) {
        Consulta c = new Consulta();
        aplicarDados(c, dto);
        return toDTO(repository.save(c));
    }

    public ConsultaResponseDTO atualizar(Integer id, ConsultaRequestDTO dto) {
        Consulta c = buscarEntidade(id);
        aplicarDados(c, dto);
        return toDTO(repository.save(c));
    }

    public void deletar(Integer id) {
        repository.delete(buscarEntidade(id));
    }

    private void aplicarDados(Consulta c, ConsultaRequestDTO dto) {
        Medico medico = medicoService.buscarEntidade(dto.medicoId());
        Paciente paciente = pacienteService.buscarEntidade(dto.pacienteId());
        Clinica clinica = clinicaService.buscarEntidade(dto.clinicaId());

        c.setDataHora(dto.dataHora());
        c.setStatus(dto.status());
        c.setObservacao(dto.observacao());
        c.setMedico(medico);
        c.setPaciente(paciente);
        c.setClinica(clinica);
    }

    private ConsultaResponseDTO toDTO(Consulta c) {
        return new ConsultaResponseDTO(
                c.getId(), c.getDataHora(), c.getStatus(), c.getObservacao(),
                c.getMedico().getId(), c.getMedico().getNome(),
                c.getPaciente().getId(), c.getPaciente().getNome(),
                c.getClinica().getId(), c.getClinica().getNome()
        );
    }
}
