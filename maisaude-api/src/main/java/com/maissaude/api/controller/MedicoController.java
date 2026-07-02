package com.maissaude.api.controller;

import com.maissaude.api.dto.ConsultaResponseDTO;
import com.maissaude.api.dto.MedicoRequestDTO;
import com.maissaude.api.dto.MedicoResponseDTO;
import com.maissaude.api.service.ConsultaService;
import com.maissaude.api.service.MedicoService;
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
@RequestMapping("/api/medicos")
@RequiredArgsConstructor
@Validated
@Tag(name = "Medicos")
public class MedicoController {

    private final MedicoService service;
    private final ConsultaService consultaService;

    @GetMapping
    @Operation(summary = "Lista todos os medicos")
    public ResponseEntity<List<MedicoResponseDTO>> listar() {
        return ResponseEntity.ok(service.listar());
    }

    @GetMapping("/{id}")
    @Operation(summary = "Busca um medico pelo id")
    public ResponseEntity<MedicoResponseDTO> buscarPorId(@PathVariable @Min(1) Integer id) {
        return ResponseEntity.ok(service.buscarPorId(id));
    }

    // Endpoint que combina dados de Medico + Consulta + Paciente + Clinica (mais de uma tabela)
    @GetMapping("/{id}/consultas")
    @Operation(summary = "Lista a agenda de consultas de um medico, com dados de paciente e clinica")
    public ResponseEntity<List<ConsultaResponseDTO>> listarConsultas(@PathVariable @Min(1) Integer id) {
        return ResponseEntity.ok(consultaService.listarPorMedico(id));
    }

    @PostMapping
    @Operation(summary = "Cadastra um novo medico")
    public ResponseEntity<MedicoResponseDTO> criar(@Valid @RequestBody MedicoRequestDTO dto) {
        return ResponseEntity.status(201).body(service.criar(dto));
    }

    @PutMapping("/{id}")
    @Operation(summary = "Atualiza um medico existente")
    public ResponseEntity<MedicoResponseDTO> atualizar(@PathVariable @Min(1) Integer id, @Valid @RequestBody MedicoRequestDTO dto) {
        return ResponseEntity.ok(service.atualizar(id, dto));
    }

    @DeleteMapping("/{id}")
    @Operation(summary = "Remove um medico")
    public ResponseEntity<Void> deletar(@PathVariable @Min(1) Integer id) {
        service.deletar(id);
        return ResponseEntity.noContent().build();
    }
}
