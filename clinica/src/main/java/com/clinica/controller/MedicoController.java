package com.clinica.controller;

import com.clinica.dto.MedicoDTO;
import com.clinica.model.Especialidade;
import com.clinica.model.Medico;
import com.clinica.service.MedicoService;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.tags.Tag;
import jakarta.validation.Valid;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import java.util.Set;

@RestController
@RequestMapping("/api/medicos")
@Tag(name = "Médicos")
public class MedicoController {
    private final MedicoService service;

    public MedicoController(MedicoService service) {
        this.service = service;
    }

    @GetMapping
    @PreAuthorize("hasAnyRole('ADMIN','MEDICO')")
    public List<Medico> listar() {
        return service.listar();
    }

    @GetMapping("/{id}")
    @PreAuthorize("hasAnyRole('ADMIN','MEDICO')")
    public Medico buscar(@PathVariable Long id) {
        return service.buscarPorId(id);
    }

    @PostMapping
    @PreAuthorize("hasRole('ADMIN')")
    public Medico criar(@Valid @RequestBody MedicoDTO dto) {
        return service.criar(dto);
    }

    @PutMapping("/{id}")
    @PreAuthorize("hasRole('ADMIN')")
    public Medico atualizar(@PathVariable Long id, @Valid @RequestBody MedicoDTO dto) {
        return service.atualizar(id, dto);
    }

    @DeleteMapping("/{id}")
    @PreAuthorize("hasRole('ADMIN')")
    public ResponseEntity<Void> deletar(@PathVariable Long id) {
        service.deletar(id);
        return ResponseEntity.noContent().build();
    }

    @GetMapping("/{id}/especialidades")
    @PreAuthorize("hasAnyRole('ADMIN','MEDICO')")
    @Operation(summary = "Listar especialidades de um médico (dados de múltiplas tabelas)")
    public ResponseEntity<Set<Especialidade>> listarEspecialidades(@PathVariable Long id) {
        Medico m = service.buscarPorId(id);
        return ResponseEntity.ok(m.getEspecialidades());
    }
}
