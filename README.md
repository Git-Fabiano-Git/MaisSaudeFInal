# Instituto Federal de Sergipe
# Disciplina: Programação I — Prof. Francisco Rodrigues
# Alunos: Fabiano Freitas e Aline Santos

---

## Sistema Mais Saúde – AV2

### Sobre o projeto

API REST em Spring Boot para gestão de clínicas médicas: cadastro de médicos, pacientes, clínicas e
especialidades, e agendamento de consultas. O projeto usa JPA/Hibernate para o mapeamento
objeto-relacional, Bean Validation para regras de negócio, Swagger/OpenAPI para documentação dos
endpoints e Spring Security + JWT para autenticação **e autorização baseada em papéis (RBAC)**.

### Domínio do problema

Uma clínica cadastra médicos e especialidades. Um médico pode atender em várias clínicas e ter várias
especialidades (N:N). Uma clínica pode oferecer várias especialidades e uma especialidade pode ser
oferecida por várias clínicas (N:N). Pacientes agendam consultas, cada consulta ligando exatamente um
médico, um paciente e uma clínica (três relacionamentos 1:N).

### Modelo de dados (Diagrama ER)

Veja `Docs/diagrama_er.md` (notação Mermaid) — mostra as tabelas `PACIENTE`, `MEDICO`, `ESPECIALIDADE`,
`CLINICA`, `CONSULTA` e as tabelas associativas `MEDICO_ESPECIALIDADE`, `MEDICO_CLINICA` e
`CLINICA_ESPECIALIDADE`.

Relacionamentos:
- **1:N** → Medico→Consulta, Paciente→Consulta, Clinica→Consulta
- **N:N** → Medico↔Especialidade, Medico↔Clinica, Clinica↔Especialidade

---

### Como rodar o projeto

**Pré-requisitos:** Java 21 e Maven (ou o wrapper `mvnw`).

1. Abra o projeto na IDE e deixe o Maven baixar as dependências.
2. Execute `ApiApplication.java`.
3. A aplicação sobe na porta `8080`. No console aparece:
```
=== Banco de dados populado com sucesso! ===
Usuario admin: admin / admin123
Usuario padrao: recepcao / recepcao123
```

### Documentação interativa (Swagger)

http://localhost:8080/swagger-ui.html

Quase todos os endpoints exigem token JWT. Clique em **Authorize** no Swagger e informe
`Bearer <token>` obtido no login. Dependendo do papel do usuário logado, alguns endpoints podem
retornar `403 Forbidden` — veja a seção **Autorização (papéis)** abaixo.

### Console do banco (H2)

http://localhost:8080/h2-console
- JDBC URL: `jdbc:h2:mem:maisaude`
- User: `sa` — senha: em branco

---

### Autenticação (JWT)

**POST** `/api/auth/login` (endpoint público)

Requisição:
```json
{ "username": "admin", "senha": "admin123" }
```

Resposta:
```json
{ "token": "eyJhbGciOiJIUzI1NiJ9...", "tipo": "Bearer" }
```

Envie o token nas próximas chamadas: header `Authorization: Bearer <token>`.

### Autorização (papéis)

O sistema tem dois papéis, definidos no cadastro de `Usuario` e embutidos no token JWT:

| Usuário seed | Papel | Perfil |
|---|---|---|
| `admin` / `admin123` | `ADMIN` | Gestor da clínica — acesso total |
| `recepcao` / `recepcao123` | `USER` | Recepção — atendimento do dia a dia |

Regras aplicadas pelo `SecurityConfig`:

| Ação | Médicos / Clínicas / Especialidades | Pacientes / Consultas |
|---|---|---|
| **Consultar (GET)** | ADMIN ou USER | ADMIN ou USER |
| **Criar / Editar (POST, PUT)** | somente ADMIN | ADMIN ou USER |
| **Excluir (DELETE)** | somente ADMIN | somente ADMIN |

A ideia: a recepção cadastra pacientes e marca consultas no dia a dia, mas não altera o cadastro
estrutural da clínica (médicos, clínicas, especialidades) nem exclui registros — isso fica só com o
ADMIN. Uma requisição autenticada mas sem o papel necessário recebe `403 Forbidden`:

```json
{
  "timestamp": "2026-07-02T10:15:00",
  "status": 403,
  "erro": "Acesso negado",
  "mensagens": ["Seu usuario nao tem permissao para executar esta operacao"]
}
```

---

### Como testar (roteiro para apresentação)

Roteiro pra demonstrar autenticação + autorização por papel.

**Via curl:**
```bash
# login
curl -X POST localhost:8080/api/auth/login -H "Content-Type: application/json" \
  -d '{"username":"recepcao","senha":"recepcao123"}'

# USER lê médicos -> 200
curl localhost:8080/api/medicos -H "Authorization: Bearer $TOKEN_USER"

# USER tenta excluir médico -> 403 (sem permissão)
curl -i -X DELETE localhost:8080/api/medicos/1 -H "Authorization: Bearer $TOKEN_USER"

# ADMIN faz a mesma exclusão -> 204
curl -i -X DELETE localhost:8080/api/medicos/1 -H "Authorization: Bearer $TOKEN_ADMIN"

# sem token -> 401
curl -i localhost:8080/api/medicos
```

**Via IntelliJ (HTTP Client):** crie `testes.http` na raiz do projeto:
```http
### Login recepcao
POST http://localhost:8080/api/auth/login
Content-Type: application/json

{ "username": "recepcao", "senha": "recepcao123" }

> {% client.global.set("token_user", response.body.token); %}

### USER lista médicos (200)
GET http://localhost:8080/api/medicos
Authorization: Bearer {{token_user}}

### USER tenta deletar médico (403)
DELETE http://localhost:8080/api/medicos/1
Authorization: Bearer {{token_user}}
```
Clique no ▶ ao lado de cada bloco `###`; o token do login é salvo automaticamente para as próximas
requisições.

---

### Exemplo de execução (payloads)

**POST** `/api/pacientes`
```json
{
  "nome": "Beatriz Lima",
  "cpf": "11122233344",
  "dataNascimento": "1995-03-20",
  "email": "beatriz@email.com",
  "telefone": "79999998888",
  "ativo": true
}
```
Resposta `201 Created`:
```json
{
  "id": 3,
  "nome": "Beatriz Lima",
  "cpf": "11122233344",
  "dataNascimento": "1995-03-20",
  "email": "beatriz@email.com",
  "telefone": "79999998888",
  "ativo": true
}
```

**POST** `/api/consultas`
```json
{
  "dataHora": "2026-08-10T14:30:00",
  "status": "AGENDADA",
  "observacao": "Consulta de rotina",
  "medicoId": 1,
  "pacienteId": 1,
  "clinicaId": 1
}
```
Se `medicoId` não existir, a API retorna `404` com uma mensagem explicando o erro; se o `status`
enviado for inválido (ex: `"XPTO"`), retorna `400` com a lista de erros de validação.

---

### Principais endpoints

| Recurso | Endpoints | Quem pode |
|---|---|---|
| Auth | `POST /api/auth/login` | público |
| Médicos | `GET /api/medicos`, `GET /api/medicos/{id}`, `GET /api/medicos/{id}/consultas` | ADMIN, USER |
| | `POST/PUT /api/medicos` | ADMIN |
| | `DELETE /api/medicos/{id}` | ADMIN |
| Pacientes | `GET /api/pacientes`, `GET /api/pacientes/{id}`, `GET /api/pacientes/{id}/consultas` | ADMIN, USER |
| | `POST/PUT /api/pacientes` | ADMIN, USER |
| | `DELETE /api/pacientes/{id}` | ADMIN |
| Clínicas | `GET /api/clinicas` | ADMIN, USER |
| | `POST/PUT /api/clinicas` | ADMIN |
| | `DELETE /api/clinicas/{id}` | ADMIN |
| Especialidades | `GET /api/especialidades` | ADMIN, USER |
| | `POST/PUT /api/especialidades` | ADMIN |
| | `DELETE /api/especialidades/{id}` | ADMIN |
| Consultas | `GET /api/consultas` | ADMIN, USER |
| | `POST/PUT /api/consultas` | ADMIN, USER |
| | `DELETE /api/consultas/{id}` | ADMIN |

Os endpoints `GET /api/medicos/{id}/consultas`, `GET /api/pacientes/{id}/consultas` e
`GET /api/consultas` combinam dados de mais de uma tabela (Consulta + Médico + Paciente + Clínica) em
uma única resposta.

### Validações implementadas

- **Camada de dados (entidades/DTOs):** `@NotBlank`, `@Email`, `@Pattern` (CPF, CNPJ, telefone),
  `@Past`/`@Future`, `@NotNull` — aplicadas no corpo das requisições (`@Valid`).
- **Parâmetros de rota:** `@Validated` + `@Min(1)` nos `@PathVariable id` dos controllers.
- **Regras de negócio:** data da consulta não pode estar no passado (`@Future`) e o `status` só aceita
  `AGENDADA`, `REALIZADA` ou `CANCELADA` (`@Pattern`).
- Erros de validação retornam `400` com detalhes de cada campo (`GlobalExceptionHandler`).
- Erros de autorização (papel insuficiente) retornam `403` no mesmo formato de erro padrão da API.

---

### Tecnologias usadas

- Spring Boot 3.4.5, Spring Data JPA, Spring Security, Spring Validation
- H2 Database (banco em memória)
- springdoc-openapi (Swagger UI)
- JJWT (JSON Web Token)
- Lombok, Maven

### Estrutura das pastas

```
api/
├── pom.xml
├── src/main/java/com/maissaude/api/
│   ├── ApiApplication.java
│   ├── config/        (DatabaseSeeder, SecurityConfig, OpenApiConfig)
│   ├── entity/         (Medico, Paciente, Clinica, Especialidade, Consulta, Usuario)
│   ├── repository/
│   ├── dto/
│   ├── service/
│   ├── controller/
│   ├── security/       (JwtUtil, JwtAuthFilter, CustomUserDetailsService)
│   └── exception/       (GlobalExceptionHandler)
└── src/main/resources/application.properties
```

### Possíveis problemas

- **H2 Console 404**: use Java 21 (não 25) ou conecte pela IDE.
- **401 Unauthorized**: faça login em `/api/auth/login` e envie o token no header `Authorization`.
- **403 Forbidden**: o token é válido, mas o papel do usuário (`ADMIN`/`USER`) não tem permissão para
  aquela operação — veja a tabela em **Autorização (papéis)**.
