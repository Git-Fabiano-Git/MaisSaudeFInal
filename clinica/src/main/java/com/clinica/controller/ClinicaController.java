package com.clinica.controller;

import com.clinica.dto.ClinicaDTO;
import com.clinica.model.Clinica;
import com.clinica.service.ClinicaService;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.tags.Tag;
import jakarta.validation.Valid;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/api/clinicas")
@Tag(name = "Clínicas")
public class ClinicaController {
    private final ClinicaService service;

    public ClinicaController(ClinicaService service) {
        this.service = service;
    }

    @GetMapping
    @PreAuthorize("hasAnyRole('ADMIN','MEDICO')")
    @Operation(summary = "Listar todas as clínicas")
    public List<Clinica> listar() {
        return service.listar();
    }

    @GetMapping("/{id}")
    @PreAuthorize("hasAnyRole('ADMIN','MEDICO')")
    public Clinica buscar(@PathVariable Long id) {
        return service.buscarPorId(id);
    }

    @PostMapping
    @PreAuthorize("hasRole('ADMIN')")
    public Clinica criar(@Valid @RequestBody ClinicaDTO dto) {
        return service.criar(dto);
    }

    @PutMapping("/{id}")
    @PreAuthorize("hasRole('ADMIN')")
    public Clinica atualizar(@PathVariable Long id, @Valid @RequestBody ClinicaDTO dto) {
        return service.atualizar(id, dto);
    }

    @DeleteMapping("/{id}")
    @PreAuthorize("hasRole('ADMIN')")
    public ResponseEntity<Void> deletar(@PathVariable Long id) {
        service.deletar(id);
        return ResponseEntity.noContent().build();
    }
}
