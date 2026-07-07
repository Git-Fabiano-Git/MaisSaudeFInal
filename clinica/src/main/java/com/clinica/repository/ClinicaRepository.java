package com.clinica.repository;

import com.clinica.model.Clinica;
import org.springframework.data.jpa.repository.JpaRepository;

public interface ClinicaRepository extends JpaRepository<Clinica, Long> {}