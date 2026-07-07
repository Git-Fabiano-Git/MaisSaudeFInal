INSERT INTO role (nome) VALUES ('ROLE_ADMIN');
INSERT INTO role (nome) VALUES ('ROLE_MEDICO');
INSERT INTO role (nome) VALUES ('ROLE_PACIENTE');

-- Usuário admin (senha: admin123)
INSERT INTO usuario (login, senha, ativo) VALUES ('admin', '$2a$10$SzycFVI22jD8AHh7MNVW3.xKzs8onkz11U8bFHXLWiu0t4ZA5s9pG', true);
INSERT INTO usuario_role (usuario_id, role_id) VALUES (1, 1);

-- Usuario medico (senha: medico123)
INSERT INTO usuario (login, senha, ativo) VALUES ('medico', '$2a$10$sFqkCSLorDy4SBlCs9NX5uQQTCIFQJrr9GOKNS0bSP9j45Y6AUn8y', true);
INSERT INTO usuario_role (usuario_id, role_id) VALUES (2, 2);

-- Usuario paciente (senha: paciente123)
INSERT INTO usuario (login, senha, ativo) VALUES ('paciente', '$2a$10$6bMFNHIl/fLDgo7cKn9AB.PAbUr/y7phshpaPIxaoB00PkaJIlj8a', true);
INSERT INTO usuario_role (usuario_id, role_id) VALUES (3, 3);


--
-- 1o hash gerado(admin): $2a$10$SzycFVI22jD8AHh7MNVW3.xKzs8onkz11U8bFHXLWiu0t4ZA5s9pG

-- http://localhost:8080/swagger-ui/index.html
