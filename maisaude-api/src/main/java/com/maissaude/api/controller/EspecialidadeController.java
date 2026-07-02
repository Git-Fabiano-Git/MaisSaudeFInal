package com.maissaude.api.controller;

import com.maissaude.api.dto.EspecialidadeDTO;
import com.maissaude.api.service.EspecialidadeService;
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
@RequestMapping("/api/especialidades")
@RequiredArgsConstructor
@Validated
@Tag(name = "Especialidades")
public class EspecialidadeController {

    private final EspecialidadeService service;

    @GetMapping
    @Operation(summary = "Lista todas as especialidades")
    public ResponseEntity<List<EspecialidadeDTO>> listar() {
        return ResponseEntity.ok(service.listar());
    }

    @GetMapping("/{id}")
    @Operation(summary = "Busca uma especialidade pelo id")
    public ResponseEntity<EspecialidadeDTO> buscarPorId(@PathVariable @Min(1) Integer id) {
        return ResponseEntity.ok(service.buscarPorId(id));
    }

    @PostMapping
    @Operation(summary = "Cadastra uma nova especialidade")
    public ResponseEntity<EspecialidadeDTO> criar(@Valid @RequestBody EspecialidadeDTO dto) {
        return ResponseEntity.status(201).body(service.criar(dto));
    }

    @PutMapping("/{id}")
    @Operation(summary = "Atualiza uma especialidade existente")
    public ResponseEntity<EspecialidadeDTO> atualizar(@PathVariable @Min(1) Integer id, @Valid @RequestBody EspecialidadeDTO dto) {
        return ResponseEntity.ok(service.atualizar(id, dto));
    }

    @DeleteMapping("/{id}")
    @Operation(summary = "Remove uma especialidade")
    public ResponseEntity<Void> deletar(@PathVariable @Min(1) Integer id) {
        service.deletar(id);
        return ResponseEntity.noContent().build();
    }
}
