# Atendimento Hospitalar Web - Engenharia de Software 2

Sistema web para gestão de atendimentos de saúde, desenvolvido para colocar em prática conceitos avançados de DevOps, CI/CD e Cloud.

## Tecnologias

| Camada | Tecnologia |
|--------|-----------|
| Backend | Java 17 + Spring Boot 3.2 |
| Frontend | React 18 + React Router |
| Banco de Dados | PostgreSQL 15 |
| Build Backend | Maven |
| Build Frontend | Node.js 20 + npm |
| Versionamento | Git + GitHub |
| CI/CD | GitHub Actions |
| Containers | Docker + Docker Compose |
| Produção | AWS (ECS + RDS + ECR + ALB) |

## Estrutura do Projeto

```text
atendimento-hospitalar/
├── backend/           # API REST (Java/Spring Boot)
│   ├── pom.xml
│   ├── Dockerfile
│   └── src/
├── frontend/          # UI (React)
│   ├── package.json
│   ├── Dockerfile
│   └── src/
├── docker-compose.yml
└── .github/workflows/ci-cd.yml
```

## Como Executar (Desenvolvimento)

```bash
# Usando Docker Compose para subir banco, backend e frontend
docker-compose up -d

# Backend disponível em: http://localhost:8080
# Frontend disponível em: http://localhost:3000
```

## Como Executar Testes

```bash
# Backend (JUnit 5 + Mockito + Testes de Integração)
cd backend
mvn test

# Frontend (Jest)
cd frontend
npm test
```

## Divisão de Trabalho e Pipeline de Desenvolvimento

O desenvolvimento seguirá uma esteira sequencial para evitar bloqueios entre a dupla. Marque com um ✅ as tarefas concluídas.

### 🧑‍💻 Desenvolvedor 1 - Lucas Carneiro Nassau Malta

**Responsabilidade Central:** Setup inicial da infraestrutura, Entidade `Profissional de saúde` (Full-stack) e Pipeline CI/CD.

- [x] **1. Scaffolding:** Criar a estrutura base de diretórios e arquivos vazios do projeto.
- [x] **2. Configuração Base:** Preencher `pom.xml`, `package.json`, `application.properties`, `Dockerfiles` e `docker-compose.yml`. *(Libera o Passo 1 do DEV 2)*
- [x] **3. Backend Profissional:** Implementar Entidade, Repository e Controller de `Profissional` (com valores mockados para "Psicólogo", "Fisioterapeuta" e "Médico").
- [x] **4. Testes Profissional:** Implementar testes unitários e de integração para a API de profissionais.
- [x] **5. Frontend Profissional:** Desenvolver telas React (`ProfissionalList` e `ProfissionalForm`).
- [x] **6. CI/CD:** Atualizar o pipeline do GitHub Actions para a nova estrutura, mantendo o deploy na AWS comentado temporariamente.

---

### 🧑‍💻 Desenvolvedor 2 - João Pedro Torres

**Responsabilidade Central:** Entidades `Atendimento` e `Exame laboratorial` (Full-stack) e lógicas de validação de negócio.

- [X] **1. Clonagem:** *(Depende do Passo 2 do DEV 1)* Baixar o repositório com a infraestrutura configurada e testar os contêineres locais.
- [X] **2. Backend Atendimento:** Criar Entidade e Repository de `Atendimento`.
- [X] **3. Regras de Negócio Atendimento (Controller):** Implementar o `AtendimentoController` contendo a validação estrita entre `categoria` do profissional e `receita_saude` no banco de dados.
- [X] **4. Testes Atendimento:** Implementar testes unitários e de integração focando nas regras de negócio de criação e atualização.
- [X] **5. Backend Exames:** *(Depende do Passo 2)* Implementar Entidade, Repository e Controller de `Exames laboratoriais`.
- [X] **6. Testes Exames:** Implementar testes para a API de exames.
- [X] **7. Frontend Atendimento:** Desenvolver telas React (`AtendimentoList` e `AtendimentoForm`).
- [ ] **8. Frontend Exames:** *(Depende do Passo 5)* Criar os componentes React para Exames e acoplá-los como uma sub-lista dentro da interface de Atendimentos.
- [ ] **9. E2E (Opcional/CD):** Ajustar os scripts `curl` de teste de aceitação final do pipeline caso seja necessário.git 