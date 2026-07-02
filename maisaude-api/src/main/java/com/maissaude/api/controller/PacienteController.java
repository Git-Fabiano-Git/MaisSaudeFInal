package com.maissaude.api.controller;

import com.maissaude.api.dto.ConsultaResponseDTO;
import com.maissaude.api.dto.PacienteRequestDTO;
import com.maissaude.api.dto.PacienteResponseDTO;
import com.maissaude.api.service.ConsultaService;
import com.maissaude.api.service.PacienteService;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.tags.Tag;
import jakarta.validation.Valid;
import jakarta.validation.constraints.Min;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/api/pacientes")
@RequiredArgsConstructor
@Validated
@Tag(name = "Pacientes")
public class PacienteController {

    private final PacienteService service;
    private final ConsultaService consultaService;

    @GetMapping
    @Operation(summary = "Lista todos os pacientes")
    public ResponseEntity<List<PacienteResponseDTO>> listar() {
        return ResponseEntity.ok(service.listar());
    }

    @GetMapping("/{id}")
    @Operation(summary = "Busca um paciente pelo id")
    public ResponseEntity<PacienteResponseDTO> buscarPorId(@PathVariable @Min(1) Integer id) {
        return ResponseEntity.ok(service.buscarPorId(id));
    }

    // Endpoint que combina dados de Paciente + Consulta + Medico + Clinica (mais de uma tabela)
    @GetMapping("/{id}/consultas")
    @Operation(summary = "Lista o historico de consultas de um paciente, com dados de medico e clinica")
    public ResponseEntity<List<ConsultaResponseDTO>> listarConsultas(@PathVariable @Min(1) Integer id) {
        return ResponseEntity.ok(consultaService.listarPorPaciente(id));
    }

    @PostMapping
    @Operation(summary = "Cadastra um novo paciente")
    public ResponseEntity<PacienteResponseDTO> criar(@Valid @RequestBody PacienteRequestDTO dto) {
        return ResponseEntity.status(201).body(service.criar(dto));
    }

    @PutMapping("/{id}")
    @Operation(summary = "Atualiza um paciente existente")
    public ResponseEntity<PacienteResponseDTO> atualizar(@PathVariable @Min(1) Integer id, @Valid @RequestBody PacienteRequestDTO dto) {
        return ResponseEntity.ok(service.atualizar(id, dto));
    }

    @DeleteMapping("/{id}")
    @Operation(summary = "Remove um paciente")
    public ResponseEntity<Void> deletar(@PathVariable @Min(1) Integer id) {
        service.deletar(id);
        return ResponseEntity.noContent().build();
    }
}
