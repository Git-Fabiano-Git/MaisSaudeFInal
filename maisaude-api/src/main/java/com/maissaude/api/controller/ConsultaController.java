package com.maissaude.api.controller;

import com.maissaude.api.dto.ConsultaRequestDTO;
import com.maissaude.api.dto.ConsultaResponseDTO;
import com.maissaude.api.service.ConsultaService;
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
@RequestMapping("/api/consultas")
@RequiredArgsConstructor
@Validated
@Tag(name = "Consultas")
public class ConsultaController {

    private final ConsultaService service;

    // Ja combina dados de Consulta + Medico + Paciente + Clinica (mais de uma tabela)
    @GetMapping
    @Operation(summary = "Lista todas as consultas, com nomes de medico, paciente e clinica")
    public ResponseEntity<List<ConsultaResponseDTO>> listar() {
        return ResponseEntity.ok(service.listar());
    }

    @GetMapping("/{id}")
    @Operation(summary = "Busca uma consulta pelo id")
    public ResponseEntity<ConsultaResponseDTO> buscarPorId(@PathVariable @Min(1) Integer id) {
        return ResponseEntity.ok(service.buscarPorId(id));
    }

    @PostMapping
    @Operation(summary = "Agenda uma nova consulta")
    public ResponseEntity<ConsultaResponseDTO> criar(@Valid @RequestBody ConsultaRequestDTO dto) {
        return ResponseEntity.status(201).body(service.criar(dto));
    }

    @PutMapping("/{id}")
    @Operation(summary = "Atualiza uma consulta existente")
    public ResponseEntity<ConsultaResponseDTO> atualizar(@PathVariable @Min(1) Integer id, @Valid @RequestBody ConsultaRequestDTO dto) {
        return ResponseEntity.ok(service.atualizar(id, dto));
    }

    @DeleteMapping("/{id}")
    @Operation(summary = "Cancela/remove uma consulta")
    public ResponseEntity<Void> deletar(@PathVariable @Min(1) Integer id) {
        service.deletar(id);
        return ResponseEntity.noContent().build();
    }
}
