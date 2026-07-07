package com.clinica.controller;

import com.clinica.dto.EspecialidadeDTO;
import com.clinica.model.Especialidade;
import com.clinica.service.EspecialidadeService;
import io.swagger.v3.oas.annotations.tags.Tag;
import jakarta.validation.Valid;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/api/especialidades")
@Tag(name = "Especialidades")
public class EspecialidadeController {
    private final EspecialidadeService service;

    public EspecialidadeController(EspecialidadeService service) {
        this.service = service;
    }

    @GetMapping
    @PreAuthorize("hasAnyRole('ADMIN','MEDICO')")
    public List<Especialidade> listar() {
        return service.listar();
    }

    @GetMapping("/{id}")
    @PreAuthorize("hasAnyRole('ADMIN','MEDICO')")
    public Especialidade buscar(@PathVariable Long id) {
        return service.buscarPorId(id);
    }

    @PostMapping
    @PreAuthorize("hasRole('ADMIN')")
    public Especialidade criar(@Valid @RequestBody EspecialidadeDTO dto) {
        return service.criar(dto);
    }

    @PutMapping("/{id}")
    @PreAuthorize("hasRole('ADMIN')")
    public Especialidade atualizar(@PathVariable Long id, @Valid @RequestBody EspecialidadeDTO dto) {
        return service.atualizar(id, dto);
    }

    @DeleteMapping("/{id}")
    @PreAuthorize("hasRole('ADMIN')")
    public ResponseEntity<Void> deletar(@PathVariable Long id) {
        service.deletar(id);
        return ResponseEntity.noContent().build();
    }
}
