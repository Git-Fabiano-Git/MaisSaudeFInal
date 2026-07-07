INSERT INTO role (nome) VALUES ('ROLE_ADMIN');
INSERT INTO role (nome) VALUES ('ROLE_MEDICO');
INSERT INTO role (nome) VALUES ('ROLE_PACIENTE');

-- senha: admin123 (use o hash gerado pelo BCryptPasswordEncoder)
INSERT INTO usuario (login, senha, ativo) VALUES ('admin', '$2a$10$SzycFVI22jD8AHh7MNVW3.xKzs8onkz11U8bFHXLWiu0t4ZA5s9pG', true);
INSERT INTO usuario_role (usuario_id, role_id) VALUES (1, 1);

-- 1o hash gerado: $2a$10$SzycFVI22jD8AHh7MNVW3.xKzs8onkz11U8bFHXLWiu0t4ZA5s9pG
