# Diagrama ER — Sistema Mais Saúde

```mermaid
erDiagram
    CLINICA ||--o{ CONSULTA : agenda
    MEDICO ||--o{ CONSULTA : realiza
    PACIENTE ||--o{ CONSULTA : marca

    MEDICO }o--o{ ESPECIALIDADE : possui
    MEDICO }o--o{ CLINICA : atende
    CLINICA }o--o{ ESPECIALIDADE : oferece

    CLINICA {
        int id PK
        string nome
        string cnpj
        string endereco
    }

    MEDICO {
        int id PK
        string nome
        string crm
        string email
        string telefone
        boolean ativo
    }

    PACIENTE {
        int id PK
        string nome
        string cpf
        date dataNascimento
        string email
        string telefone
        boolean ativo
    }

    ESPECIALIDADE {
        int id PK
        string nome
    }

    CONSULTA {
        int id PK
        datetime dataHora
        string status
        string observacao
        int id_medico FK
        int id_paciente FK
        int id_clinica FK
    }

    USUARIO {
        int id PK
        string username
        string senha
        string role
    }
```

### Legenda dos relacionamentos

- **1:N** — `CLINICA ||--o{ CONSULTA`, `MEDICO ||--o{ CONSULTA`, `PACIENTE ||--o{ CONSULTA`
- **N:N** — `MEDICO }o--o{ ESPECIALIDADE` (tabela `medico_especialidade`), `MEDICO }o--o{ CLINICA`
  (tabela `medico_clinica`), `CLINICA }o--o{ ESPECIALIDADE` (tabela `clinica_especialidade`)

`USUARIO` não se relaciona com as demais entidades — é usado apenas para autenticação/autorização
(login e papel ADMIN/USER), não faz parte do domínio de negócio (clínicas/médicos/pacientes/consultas).
