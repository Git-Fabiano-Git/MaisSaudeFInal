package com.maissaude.api.config;

import com.maissaude.api.entity.*;
import com.maissaude.api.repository.*;
import lombok.RequiredArgsConstructor;
import org.springframework.boot.CommandLineRunner;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Component;
import java.time.LocalDateTime;
import java.util.Set;

@Component
@RequiredArgsConstructor
public class DatabaseSeeder implements CommandLineRunner {

    private final MedicoRepository medicoRepository;
    private final EspecialidadeRepository especialidadeRepository;
    private final ClinicaRepository clinicaRepository;
    private final PacienteRepository pacienteRepository;
    private final ConsultaRepository consultaRepository;
    private final UsuarioRepository usuarioRepository;
    private final PasswordEncoder passwordEncoder;

    @Override
    public void run(String... args) throws Exception {
        // 1. Criar especialidades
        Especialidade cardio = new Especialidade();
        cardio.setNome("Cardiologia");
        Especialidade dermato = new Especialidade();
        dermato.setNome("Dermatologia");
        especialidadeRepository.saveAll(Set.of(cardio, dermato));

        // 2. Criar clinicas e associar especialidades (M:N clinica-especialidade)
        Clinica clinicaA = new Clinica();
        clinicaA.setNome("Clinica Saude Total");
        clinicaA.setCnpj("12.345.678/0001-90");
        clinicaA.setEndereco("Av. Central, 1000");
        clinicaA.setEspecialidades(Set.of(cardio, dermato));

        Clinica clinicaB = new Clinica();
        clinicaB.setNome("Clinica Bem Estar");
        clinicaB.setCnpj("98.765.432/0001-11");
        clinicaB.setEndereco("Rua das Flores, 500");
        clinicaB.setEspecialidades(Set.of(cardio));
        clinicaRepository.saveAll(Set.of(clinicaA, clinicaB));

        // 3. Criar medicos e associar especialidades + clinicas
        Medico drJoao = new Medico();
        drJoao.setNome("Dr. Joao Silva");
        drJoao.setCrm("12345-SP");
        drJoao.setEmail("joao@email.com");
        drJoao.setTelefone("11987654321");
        drJoao.setAtivo(true);
        drJoao.setEspecialidades(Set.of(cardio));           // many-to-many
        drJoao.setClinicas(Set.of(clinicaA, clinicaB));     // many-to-many

        Medico draMaria = new Medico();
        draMaria.setNome("Dra. Maria Souza");
        draMaria.setCrm("54321-RJ");
        draMaria.setEmail("maria@email.com");
        draMaria.setTelefone("21912345678");
        draMaria.setAtivo(true);
        draMaria.setEspecialidades(Set.of(cardio, dermato));
        draMaria.setClinicas(Set.of(clinicaA));

        medicoRepository.saveAll(Set.of(drJoao, draMaria));

        // 4. Criar pacientes
        Paciente paciente1 = new Paciente();
        paciente1.setNome("Carlos Andrade");
        paciente1.setCpf("12345678901");
        paciente1.setDataNascimento(LocalDateTime.of(1985, 5, 10, 0, 0).toLocalDate());
        paciente1.setEmail("carlos@email.com");
        paciente1.setTelefone("11911112222");
        paciente1.setAtivo(true);

        Paciente paciente2 = new Paciente();
        paciente2.setNome("Ana Paula");
        paciente2.setCpf("10987654321");
        paciente2.setDataNascimento(LocalDateTime.of(1990, 8, 22, 0, 0).toLocalDate());
        paciente2.setEmail("ana@email.com");
        paciente2.setTelefone("11933334444");
        paciente2.setAtivo(true);
        pacienteRepository.saveAll(Set.of(paciente1, paciente2));

        // 5. Criar consultas (garantindo os 3 relacionamentos 1:N)
        Consulta consulta1 = new Consulta();
        consulta1.setDataHora(LocalDateTime.now().plusDays(2));
        consulta1.setStatus("AGENDADA");
        consulta1.setObservacao("Primeira consulta com cardiologista");
        consulta1.setMedico(drJoao);
        consulta1.setPaciente(paciente1);
        consulta1.setClinica(clinicaA);   // 1:N com clinica

        Consulta consulta2 = new Consulta();
        consulta2.setDataHora(LocalDateTime.now().plusDays(5));
        consulta2.setStatus("AGENDADA");
        consulta2.setObservacao("Retorno dermatologico");
        consulta2.setMedico(draMaria);
        consulta2.setPaciente(paciente2);
        consulta2.setClinica(clinicaB);

        consultaRepository.saveAll(Set.of(consulta1, consulta2));

        // 6. Criar usuarios para autenticacao/autorizacao (JWT)
        Usuario admin = new Usuario();
        admin.setUsername("admin");
        admin.setSenha(passwordEncoder.encode("admin123"));
        admin.setRole("ADMIN");

        Usuario recepcao = new Usuario();
        recepcao.setUsername("recepcao");
        recepcao.setSenha(passwordEncoder.encode("recepcao123"));
        recepcao.setRole("USER");

        usuarioRepository.saveAll(Set.of(admin, recepcao));

        System.out.println("=== Banco de dados populado com sucesso! ===");
        System.out.println("Usuario admin: admin / admin123");
        System.out.println("Usuario padrao: recepcao / recepcao123");
    }
}
