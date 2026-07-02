package com.maissaude.api.controller;

import com.maissaude.api.dto.ClinicaRequestDTO;
import com.maissaude.api.dto.ClinicaResponseDTO;
import com.maissaude.api.service.ClinicaService;
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
@RequestMapping("/api/clinicas")
@RequiredArgsConstructor
@Validated
@Tag(name = "Clinicas")
public class ClinicaController {

    private final ClinicaService service;

    @GetMapping
    @Operation(summary = "Lista todas as clinicas")
    public ResponseEntity<List<ClinicaResponseDTO>> listar() {
        return ResponseEntity.ok(service.listar());
    }

    @GetMapping("/{id}")
    @Operation(summary = "Busca uma clinica pelo id")
    public ResponseEntity<ClinicaResponseDTO> buscarPorId(@PathVariable @Min(1) Integer id) {
        return ResponseEntity.ok(service.buscarPorId(id));
    }

    @PostMapping
    @Operation(summary = "Cadastra uma nova clinica")
    public ResponseEntity<ClinicaResponseDTO> criar(@Valid @RequestBody ClinicaRequestDTO dto) {
        return ResponseEntity.status(201).body(service.criar(dto));
    }

    @PutMapping("/{id}")
    @Operation(summary = "Atualiza uma clinica existente")
    public ResponseEntity<ClinicaResponseDTO> atualizar(@PathVariable @Min(1) Integer id, @Valid @RequestBody ClinicaRequestDTO dto) {
        return ResponseEntity.ok(service.atualizar(id, dto));
    }

    @DeleteMapping("/{id}")
    @Operation(summary = "Remove uma clinica")
    public ResponseEntity<Void> deletar(@PathVariable @Min(1) Integer id) {
        service.deletar(id);
        return ResponseEntity.noContent().build();
    }
}
