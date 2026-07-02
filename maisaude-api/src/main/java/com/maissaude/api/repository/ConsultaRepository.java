package com.maissaude.api.repository;
import com.maissaude.api.entity.Consulta;
import org.springframework.data.jpa.repository.JpaRepository;
import java.util.List;

public interface ConsultaRepository extends JpaRepository<Consulta, Integer> {
    List<Consulta> findByMedicoId(Integer medicoId);
    List<Consulta> findByPacienteId(Integer pacienteId);
}
