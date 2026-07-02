Diagrama ER 
Projeto P1: aplicação Mais Saude

erDiagram
    PACIENTE {
        int id PK
        string nome
        string cpf UK
        date dataNascimento
        string email UK
        string telefone UK
        boolean ativo
    }

    MEDICO {
        int id PK
        string nome
        string crm UK
        string email
        string telefone UK
        boolean ativo
    }

    ESPECIALIDADE {
        int id PK
        string nome UK
    }

    CLINICA {
        int id PK
        string nome
        string cnpj UK
        string endereco
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

    MEDICO_ESPECIALIDADE {
        int id_medico FK
        int id_especialidade FK
    }

    MEDICO_CLINICA {
        int id_medico FK
        int id_clinica FK
    }

    MEDICO ||--o{ CONSULTA : "realiza"
    PACIENTE ||--o{ CONSULTA : "possui"
    CLINICA ||--o{ CONSULTA : "sedia"
    MEDICO ||--o{ MEDICO_ESPECIALIDADE : "tem"
    ESPECIALIDADE ||--o{ MEDICO_ESPECIALIDADE : "pertence"
    MEDICO ||--o{ MEDICO_CLINICA : "atua"
    CLINICA ||--o{ MEDICO_CLINICA : "recebe"