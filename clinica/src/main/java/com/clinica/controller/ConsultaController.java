package com.clinica.controller;

import com.clinica.dto.ConsultaDTO;
import com.clinica.model.Consulta;
import com.clinica.service.ConsultaService;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.tags.Tag;
import jakarta.validation.Valid;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/api/consultas")
@Tag(name = "Consultas")
public class ConsultaController {
    private final ConsultaService service;

    public ConsultaController(ConsultaService service) {
        this.service = service;
    }

    @GetMapping
    @PreAuthorize("hasAnyRole('ADMIN','MEDICO','PACIENTE')")
    @Operation(summary = "Lista todas as consultas (dados de múltiplas tabelas)")
    public List<Consulta> listar() {
        return service.listarTodas();
    }

    @GetMapping("/{id}")
    @PreAuthorize("hasAnyRole('ADMIN','MEDICO','PACIENTE')")
    public Consulta buscar(@PathVariable Long id) {
        return service.buscarPorId(id);
    }

    @PostMapping
    @PreAuthorize("hasAnyRole('ADMIN','MEDICO','PACIENTE')")   //  médicos e pacientes também agendam
    public Consulta agendar(@Valid @RequestBody ConsultaDTO dto) {
        return service.agendar(dto);
    }

    @PutMapping("/{id}")
    @PreAuthorize("hasAnyRole('ADMIN','MEDICO')")              // medicos tbm podem editar consultas
    public Consulta atualizar(@PathVariable Long id, @Valid @RequestBody ConsultaDTO dto) {
        return service.atualizar(id, dto);
    }

    @DeleteMapping("/{id}")
    @PreAuthorize("hasAnyRole('ADMIN','MEDICO')")              // médicos tbm podem cancelar consultas
    public ResponseEntity<Void> deletar(@PathVariable Long id) {
        service.deletar(id);
        return ResponseEntity.noContent().build();
    }

    @GetMapping("/paciente/{pacienteId}")
    @PreAuthorize("hasAnyRole('ADMIN','MEDICO','PACIENTE')")
    @Operation(summary = "Listar consultas de um paciente")
    public List<Consulta> porPaciente(@PathVariable Long pacienteId) {
        return service.listarPorPaciente(pacienteId);
    }

    @GetMapping("/medico/{medicoId}")
    @PreAuthorize("hasAnyRole('ADMIN','MEDICO')")
    @Operation(summary = "Listar consultas de um médico")
    public List<Consulta> porMedico(@PathVariable Long medicoId) {
        return service.listarPorMedico(medicoId);
    }
}
