# AgroTech System — Sistema de Monitoramento Agrícola Inteligente

> Sistema full-stack para monitoramento em tempo real de sensores agrícolas, com geração automática de leituras, disparo de alertas baseados em regras e dashboard de acompanhamento.

---

## Sumário

## Sumário

1. [Descrição do Projeto](#descrição-do-projeto)
2. [Principais Funcionalidades](#principais-funcionalidades)
3. [Arquitetura e Estrutura do Projeto](#arquitetura-e-estrutura-do-projeto)
4. [Tecnologias Utilizadas](#tecnologias-utilizadas)
5. [Pré-requisitos](#pré-requisitos)
6. [Instalação e Configuração](#instalação-e-configuração)
7. [Como Executar o Projeto](#como-executar-o-projeto)
8. [Como Utilizar](#como-utilizar)
9. [Endpoints da API](#endpoints-da-api)
10. [Banco de Dados](#banco-de-dados)
11. [Regras de Negócio](#regras-de-negócio)
12. [Testes](#testes)
13. [Melhorias Futuras](#melhorias-futuras)
14. [Contribuição](#contribuição)
15. [Licença](#licença)
16. [Autores](#autores)
---

## Descrição do Projeto

O **AgroTech System** é uma plataforma de monitoramento agrícola inteligente que permite o cadastro e acompanhamento de sensores distribuídos em áreas de produção. O sistema coleta leituras periódicas (temperatura, umidade do solo, umidade do ar e luminosidade), avalia essas leituras contra regras configuráveis e dispara alertas automáticos quando os limites definidos são ultrapassados.

**Problema resolvido:** A falta de visibilidade em tempo real sobre as condições ambientais nas lavouras pode causar perdas significativas. O AgroTech System centraliza o monitoramento, automatiza a detecção de anomalias e fornece dados para tomada de decisão ágil.

**Objetivo principal:** Oferecer uma solução robusta, segura e extensível para o monitoramento contínuo de sensores agrícolas, com alertas proativos e histórico de leituras acessíveis via API REST e interface web.

---

## Principais Funcionalidades

| Funcionalidade | Descrição |
|---|---|
| **Autenticação JWT** | Login com geração de token JWT. Rotas protegidas por role (`ADMIN` / `OPERADOR`). |
| **Cadastro de Usuários** | Registro de usuários com credenciais e papel (role) definido. Senha armazenada com BCrypt. |
| **Gerenciamento de Áreas** | CRUD completo de áreas agrícolas. Sensores são sempre associados a uma área. |
| **Gerenciamento de Sensores** | CRUD completo de sensores com tipo (temperatura, umidade do solo, umidade do ar, luminosidade) e status ativo/inativo. |
| **Registro de Leituras** | Aceita leituras enviadas externamente via API. Cada leitura referencia um sensor e possui valor, data/hora e localização. |
| **Geração Automática de Leituras** | Um scheduler dispara automaticamente a geração de leituras a cada **10 segundos** para sensores ativos, simulando dados de campo. |
| **Regras de Alerta** | Criação de regras por tipo de sensor com operadores `>` (maior que) ou `<` (menor que) e um valor-limite configurável. |
| **Disparo de Alertas** | A cada leitura registrada, o sistema avalia todas as regras ativas para aquele tipo de sensor e gera alertas quando os critérios são atendidos. |
| **Gerenciamento de Alertas** | Listagem de alertas com filtro por status (`ATIVO` / `RESOLVIDO`). Administradores podem marcar alertas como resolvidos. |
| **Dashboard** | Endpoint que retorna métricas consolidadas: total de sensores, total de alertas, alertas ativos e leituras recentes. |
| **Documentação da API** | Swagger UI integrado via SpringDoc OpenAPI, disponível em `/swagger-ui.html`. |

---

## Arquitetura e Estrutura do Projeto

O projeto segue os princípios da **Arquitetura Hexagonal (Ports & Adapters)**, com clara separação entre as camadas de domínio, aplicação e infraestrutura. O backend está organizado da seguinte forma:

```
agrotech-backend/
└── src/main/java/com/agrotech/agro_tech_system/
    ├── api/                        # Camada de entrada (Adapters de entrada)
    │   ├── controllers/            # Controllers REST (HTTP)
    │   └── dto/                    # DTOs de requisição e resposta por entidade
    │
    ├── application/                # Camada de aplicação
    │   └── usecase/                # Casos de uso (orquestração da lógica de negócio)
    │       ├── alerta/
    │       ├── area/
    │       ├── dashboard/
    │       ├── leitura/
    │       ├── regra/
    │       ├── sensor/
    │       └── usuario/
    │
    ├── domain/                     # Núcleo da aplicação (agnóstico a frameworks)
    │   ├── enums/                  # Enumerações de domínio
    │   ├── exception/              # Exceções de domínio
    │   ├── models/                 # Entidades de domínio (ricas em comportamento)
    │   ├── repository/             # Interfaces de repositório (Ports de saída)
    │   ├── service/                # Serviços de domínio
    │   └── strategy/               # Estratégias de negócio
    │
    └── infra/                      # Camada de infraestrutura (Adapters de saída)
        ├── config/                 # Configurações de beans e OpenAPI
        ├── persistence/            # Implementação JPA dos repositórios
        │   ├── adapter/            # Adaptadores que implementam os Ports do domínio
        │   ├── entity/             # Entidades JPA mapeadas ao banco
        │   ├── mapper/             # Conversores domínio <-> entidade JPA
        │   └── repository/         # Interfaces Spring Data JPA
        ├── scheduler/              # Tarefas agendadas (geração automática de leituras)
        ├── security/               # Filtros JWT, configuração Spring Security
        └── strategy/               # Implementações de estratégia de infraestrutura
```

```
agrotech-frontend/
└── src/app/
    ├── components/     # Componentes Angular por funcionalidade
    │   ├── alertas/
    │   ├── areas/
    │   ├── dashboard/
    │   ├── header/
    │   ├── leituras/
    │   ├── regras/
    │   ├── sensores/
    │   └── usuario/
    ├── guards/         # Guards de rota (autenticação e autorização)
    ├── interceptors/   # Interceptor HTTP para injeção do token JWT
    ├── models/         # Interfaces TypeScript dos modelos de dados
    ├── services/       # Serviços Angular para consumo da API REST
    └── shared/         # Módulos compartilhados (Angular Material)
```

---

## Tecnologias Utilizadas

### Backend

| Tecnologia | Versão | Finalidade |
|---|---|---|
| Java | 21 | Linguagem principal |
| Spring Boot | 4.0.6 | Framework principal da aplicação |
| Spring Web MVC | (via Spring Boot) | Camada REST |
| Spring Data JPA | (via Spring Boot) | Persistência e repositórios |
| Spring Security | (via Spring Boot) | Autenticação e autorização |
| Spring Validation | (via Spring Boot) | Validação de entrada |
| H2 Database | (via Spring Boot) | Banco de dados em memória |
| Hibernate | (via Spring Boot) | ORM / geração do schema |
| Auth0 Java JWT | 4.4.0 | Geração e validação de tokens JWT |
| SpringDoc OpenAPI | 2.8.3 | Documentação Swagger |
| Lombok | (via Spring Boot) | Redução de boilerplate |
| Maven | — | Gerenciamento de dependências e build |

### Frontend

| Tecnologia | Versão | Finalidade |
|---|---|---|
| Angular | 19.2 | Framework frontend |
| Angular Material | 19.2.19 | Componentes de UI |
| TypeScript | 5.7.2 | Linguagem principal |
| RxJS | 7.8 | Programação reativa |
| Angular CDK | 19.2.19 | Utilitários de UI |
| Karma + Jasmine | 6.4 / 5.6 | Testes unitários no frontend |

---

## Pré-requisitos

Para executar o projeto localmente, você precisará ter instalado:

- **JDK 21** ou superior ([Adoptium](https://adoptium.net/))
- **Maven 3.9+** (ou usar o `mvnw` incluído no projeto)
- **Node.js 20+** e **npm 10+** ([Node.js](https://nodejs.org/))
- **Angular CLI 19.x**: `npm install -g @angular/cli@19`
- Um navegador web moderno (Chrome, Firefox, Edge)

> **Observação:** O banco de dados **H2** é embutido e não requer instalação separada. Os dados são armazenados em memória e são resetados a cada reinicialização da aplicação.

---

## Instalação e Configuração

### 1. Clonar o repositório

```bash
git clone <URL_DO_REPOSITÓRIO>
cd agrotech-system
```

> **Informação não encontrada:** A URL do repositório remoto não está disponível no projeto. Solicitar ao autor para preencher este campo.

### 2. Configurar o backend

As configurações do backend estão em `agrotech-backend/src/main/resources/application.properties`. Para ambiente de desenvolvimento, nenhuma alteração é necessária. O arquivo já contém os valores padrão:

```properties
# Banco de dados H2 (em memória)
spring.datasource.url=jdbc:h2:mem:agrodb
spring.datasource.username=sa
spring.datasource.password=

# Console H2 (disponível em http://localhost:8080/h2-console)
spring.h2.console.enabled=true

# Chave secreta para geração de JWT
api.security.secret=default-dev-secret-123456
```

> **Atenção de segurança:** A chave `api.security.secret` deve ser substituída por um valor seguro e longo em ambientes de produção, nunca exposta em repositórios públicos. Utilize variáveis de ambiente para isso.

### 3. Instalar dependências do frontend

```bash
cd agrotech-frontend
npm install
```

---

## Como Executar o Projeto

### Backend (Spring Boot)

```bash
cd agrotech-backend

# Usando o Maven Wrapper (recomendado, não requer Maven instalado globalmente)
./mvnw spring-boot:run

# Ou no Windows
mvnw.cmd spring-boot:run

# Ou com Maven instalado globalmente
mvn spring-boot:run
```

O servidor iniciará em: **http://localhost:8080**

**URLs úteis após iniciar o backend:**

| URL | Descrição |
|---|---|
| `http://localhost:8080/swagger-ui.html` | Documentação interativa da API |
| `http://localhost:8080/h2-console` | Console do banco de dados H2 |
| `http://localhost:8080/v3/api-docs` | Especificação OpenAPI em JSON |

> Para acessar o H2 Console, use JDBC URL: `jdbc:h2:mem:agrodb`, usuário: `sa`, senha em branco.

### Frontend (Angular)

```bash
cd agrotech-frontend
npm start
# ou
ng serve
```

A aplicação estará disponível em: **http://localhost:4200**

> O frontend está configurado para se comunicar com o backend em `http://localhost:8080` (configurado via CORS no backend).

---

## Como Utilizar

### Fluxo principal de uso

1. **Cadastrar um usuário administrador** via `POST /cadastro` com role `ADMIN`.
2. **Realizar login** via `POST /auth/login` para obter o token JWT.
3. **Incluir o token** nas requisições subsequentes no header `Authorization: Bearer <token>`.
4. **Cadastrar pelo menos uma Área** antes de criar sensores.
5. **Cadastrar Sensores** associados à área criada.
6. **Configurar Regras** definindo o tipo de sensor, o operador (`>` ou `<`) e o valor-limite.
7. **Registrar Leituras** manualmente via API ou aguardar o scheduler (a cada 10 segundos) gerar leituras automáticas.
8. **Monitorar Alertas** via `GET /alertas` — sempre que uma leitura violar uma regra ativa, um alerta é criado automaticamente.
9. **Acompanhar o Dashboard** para uma visão consolidada do sistema.
10. **Resolver Alertas** via `PATCH /alertas/{id}/resolver` quando o problema for corrigido.

---

## Endpoints da API

A documentação completa e interativa está disponível no **Swagger UI** em `http://localhost:8080/swagger-ui.html`.

### Autenticação

| Método | Rota | Acesso | Descrição |
|---|---|---|---|
| `POST` | `/auth/login` | Público | Realiza login e retorna o token JWT |
| `POST` | `/cadastro` | Público | Cadastra um novo usuário no sistema |

**Exemplo — Login:**
```json
// POST /auth/login
// Requisição
{
  "login": "admin@agrotech.com",
  "senha": "senha123"
}

// Resposta 200
{
  "token": "eyJhbGciOiJIUzI1NiIsInR5cCI6IkpXVCJ9..."
}
```

**Exemplo — Cadastro de usuário:**
```json
// POST /cadastro
{
  "login": "operador@agrotech.com",
  "senha": "senha123",
  "role": "operador"
}
```

---

### Áreas

| Método | Rota | Acesso | Descrição |
|---|---|---|---|
| `POST` | `/areas` | ADMIN | Cria uma nova área agrícola |
| `GET` | `/areas` | Autenticado | Lista todas as áreas |
| `GET` | `/areas/{id}` | Autenticado | Busca uma área pelo ID |
| `PUT` | `/areas/{id}` | ADMIN | Atualiza os dados de uma área |
| `DELETE` | `/areas/{id}` | ADMIN | Remove uma área |

**Exemplo — Criar área:**
```json
// POST /areas
{
  "nome": "Estufa A",
  "descricao": "Estufa principal de tomates"
}
```

---

### Sensores

| Método | Rota | Acesso | Descrição |
|---|---|---|---|
| `POST` | `/sensores` | ADMIN | Cadastra um novo sensor |
| `GET` | `/sensores` | Autenticado | Lista todos os sensores |
| `GET` | `/sensores/{id}` | Autenticado | Busca um sensor pelo ID |
| `PUT` | `/sensores/{id}` | ADMIN | Atualiza o nome do sensor |
| `DELETE` | `/sensores/{id}` | ADMIN | Remove um sensor |

**Tipos de sensor disponíveis:** `temperatura`, `umidade_solo`, `umidade_ar`, `luminosidade`

**Exemplo — Criar sensor:**
```json
// POST /sensores
{
  "nome": "Sensor Temp-01",
  "localizacao": "Estufa A - Setor 1",
  "tipo": "temperatura"
}
```

---

### Leituras

| Método | Rota | Acesso | Descrição |
|---|---|---|---|
| `POST` | `/leituras` | Autenticado | Registra uma nova leitura de sensor |

**Exemplo — Registrar leitura:**
```json
// POST /leituras
{
  "sensorId": "uuid-do-sensor",
  "valor": 35.7,
  "dataHora": "2025-06-18T14:30:00"
}
```

---

### Regras

| Método | Rota | Acesso | Descrição |
|---|---|---|---|
| `POST` | `/regras` | ADMIN | Cria uma nova regra de alerta |
| `GET` | `/regras` | Autenticado | Lista todas as regras |
| `GET` | `/regras/{id}` | Autenticado | Busca uma regra pelo ID |
| `PATCH` | `/regras/{id}/ativar` | ADMIN | Ativa uma regra |
| `PATCH` | `/regras/{id}/desativar` | ADMIN | Desativa uma regra |

**Operadores disponíveis:** `>` (maior que), `<` (menor que)

**Exemplo — Criar regra:**
```json
// POST /regras
{
  "tipoSensor": "temperatura",
  "operador": ">",
  "valor": 40.0,
  "mensagem": "Temperatura crítica na estufa!"
}
```

---

### Alertas

| Método | Rota | Acesso | Parâmetros | Descrição |
|---|---|---|---|---|
| `GET` | `/alertas` | Autenticado | `?status=ATIVO` ou `?status=RESOLVIDO` (opcional) | Lista todos os alertas, com filtro opcional por status |
| `PATCH` | `/alertas/{id}/resolver` | ADMIN | — | Marca um alerta como resolvido |

---

### Dashboard

| Método | Rota | Acesso | Descrição |
|---|---|---|---|
| `GET` | `/dashboard` | Autenticado | Retorna métricas consolidadas do sistema |

**Exemplo — Resposta do dashboard:**
```json
{
  "totalSensores": 5,
  "totalAlertas": 12,
  "alertasAtivos": 3,
  "leiturasRecentes": [
    {
      "sensorNome": "Sensor Temp-01",
      "valor": 38.5,
      "dataHora": "2025-06-18T14:30:00"
    }
  ]
}
```

---

## Banco de Dados

O projeto utiliza o banco de dados **H2** em memória para desenvolvimento. O schema é gerado e atualizado automaticamente pelo Hibernate com a configuração `spring.jpa.hibernate.ddl-auto=update`.

### Entidades e Relacionamentos

```
areas (1) ──────< sensores (N)
sensores (1) ───< leituras (N)
leituras (N) >── alertas ──< (N) regras
usuarios
```

### Tabelas

| Tabela | Chave Primária | Descrição |
|---|---|---|
| `areas` | `id` (UUID) | Áreas agrícolas monitoradas |
| `sensores` | `id` (UUID) | Sensores associados às áreas |
| `leituras` | `id` (IDENTITY) | Leituras coletadas pelos sensores |
| `regras` | `id` (UUID) | Regras de disparo de alertas |
| `alertas` | `id` (UUID) | Alertas gerados pelas regras |
| `usuarios` | `id` (UUID) | Usuários do sistema |

### Colunas principais

**`sensores`**
- `id` — UUID gerado automaticamente
- `nome` — Nome do sensor (obrigatório)
- `localizacao` — Localização física
- `tipo` — Enum: `TEMPERATURA`, `UMIDADE_SOLO`, `UMIDADE_AR`, `LUMINOSIDADE`
- `area_id` — FK para `areas` (obrigatório)

**`leituras`**
- `id` — Long auto-increment
- `sensor_id` — FK para `sensores`
- `valor` — Valor numérico da leitura (Double)
- `data_hora` — Timestamp da leitura
- `localizacao` — Localização no momento da leitura

**`regras`**
- `id` — UUID
- `tipo_sensor` — Enum: `TEMPERATURA`, `UMIDADE_SOLO`, `UMIDADE_AR`, `LUMINOSIDADE`
- `operador` — Enum: `MAIOR_QUE` (`>`), `MENOR_QUE` (`<`)
- `valor` — Valor-limite para disparo (Double)
- `mensagem` — Mensagem descritiva do alerta
- `ativo` — Boolean

**`alertas`**
- `id` — UUID
- `leituras_id` — FK para `leituras`
- `regras_id` — FK para `regras`
- `status` — Enum: `ATIVO`, `RESOLVIDO`
- `data_hora` — Timestamp de criação

**`usuarios`**
- `id` — UUID
- `login` — Login único
- `senha` — Hash BCrypt
- `role` — Enum: `ADMIN`, `OPERADOR`

---

## Regras de Negócio

### Validações de domínio

- **Sensor:** nome e localização são obrigatórios; tipo não pode ser nulo.
- **Área:** nome é obrigatório.
- **Regra:** tipo de sensor, operador e mensagem são obrigatórios; valor deve ser maior que zero.
- **Alerta:** leitura, regra, status e data/hora são obrigatórios.
- **Leitura:** sensor e valor são obrigatórios.
- **Usuário:** login é obrigatório; senha deve ter pelo menos 6 caracteres.

### Lógica de negócio

- **Ao criar um sensor**, é obrigatório que exista pelo menos uma Área cadastrada no sistema. Caso contrário, a operação é recusada com uma exceção de validação.
- **A cada leitura registrada** (manual ou automática), o sistema busca todas as regras ativas cujo tipo de sensor corresponda ao sensor que gerou a leitura e avalia se `valorLeitura > valorRegra` (para `MAIOR_QUE`) ou `valorLeitura < valorRegra` (para `MENOR_QUE`). Um alerta com status `ATIVO` é criado para cada regra violada.
- **Regras desativadas** (`ativo = false`) são ignoradas na avaliação de alertas.
- **Resolver um alerta** altera seu status de `ATIVO` para `RESOLVIDO` e é restrito a usuários com role `ADMIN`.
- **Ativar/desativar regras** e operações de escrita (criar, atualizar, deletar) em sensores, áreas e regras são restritas a usuários com role `ADMIN`.
- **O scheduler** executa `GerarLeituraUseCase` a cada 10 segundos, simulando a coleta automática de dados de sensores ativos.
- **Roles de usuário:**
  - `ADMIN` — possui acesso total, incluindo todas as operações de escrita e gestão de alertas.
  - `OPERADOR` — acesso de leitura: pode visualizar sensores, áreas, regras, alertas e dashboard.

---

## Testes

### Backend

Para executar os testes do backend:

```bash
cd agrotech-backend
./mvnw test
# ou no Windows
mvnw.cmd test
```

#### Tipos de testes implementados

| Tipo | Arquivo | Descrição |
|---|---|---|
| Teste de integração da camada de persistência | `SensorRepositoryAdapterTest.java` | Valida que uma exceção de negócio é lançada ao tentar criar um sensor sem nenhuma área cadastrada. Utiliza **JUnit 5** e **Mockito** para isolar as dependências JPA. |
| Smoke test da aplicação | `AgroTechSystemApplicationTests.java` | Verifica que o contexto Spring Boot sobe corretamente. |

> **Informação complementar:** A cobertura de testes está em estágio inicial. O projeto possui estrutura para testes de repositório (camada de persistência). Testes de casos de uso e controllers ainda podem ser adicionados.

### Frontend

Para executar os testes do frontend:

```bash
cd agrotech-frontend
npm test
# ou
ng test
```

Os testes do frontend utilizam **Karma** como test runner e **Jasmine** como framework de asserção. Cada componente possui um arquivo `.spec.ts` gerado pelo Angular CLI.

---

## Melhorias Futuras

As seguintes evoluções são sugeridas para o projeto:

- **Banco de dados de produção:** Migrar do H2 (em memória) para um banco relacional persistente como PostgreSQL ou MySQL, com configuração por variáveis de ambiente.
- **Variáveis de ambiente:** Externalizar configurações sensíveis (`api.security.secret`, credenciais de banco) usando variáveis de ambiente ou um sistema de segredos (ex.: HashiCorp Vault, AWS Secrets Manager).
- **Implementação completa do frontend:** O frontend Angular está estruturado mas com serviços, guards, interceptors e models ainda sem implementação. O fluxo completo de login, navegação autenticada e exibição dos dados precisa ser desenvolvido.
- **Integração com sensores reais:** Implementar um protocolo de comunicação (MQTT, WebSocket ou HTTP) para receber leituras de dispositivos IoT físicos.
- **Notificações em tempo real:** Usar WebSocket (STOMP + SockJS) para enviar alertas ao frontend em tempo real sem necessidade de polling.
- **Paginação e filtros avançados:** Adicionar paginação nos endpoints de listagem e filtros por data, tipo de sensor, área, etc.
- **Relatórios e exportação:** Geração de relatórios históricos de leituras e alertas em PDF ou CSV.
- **Cobertura de testes:** Ampliar a suíte de testes com testes unitários para casos de uso e testes de integração para controllers (MockMvc).
- **Containerização:** Criar `Dockerfile` e `docker-compose.yml` para facilitar a execução em ambientes isolados.
- **Configuração de CI/CD:** Pipelines de integração e entrega contínua (GitHub Actions, GitLab CI) para build, testes e deploy automatizados.
- **Suporte a múltiplas áreas por sensor:** Atualmente, cada sensor pertence a uma única área. Avaliar o suporte a sensores compartilhados.
- **Histórico de auditoria:** Registrar quem criou/alterou/removeu cada entidade com timestamps de auditoria.

---

## Contribuição

Contribuições são bem-vindas! Para contribuir com o projeto:

1. **Fork** o repositório.
2. Crie uma **branch** para a sua feature ou correção:
   ```bash
   git checkout -b feature/minha-funcionalidade
   ```
3. Faça suas alterações e adicione **testes** quando aplicável.
4. Certifique-se de que todos os testes passam:
   ```bash
   # Backend
   cd agrotech-backend && ./mvnw test
   # Frontend
   cd agrotech-frontend && npm test
   ```
5. Faça o **commit** das suas mudanças:
   ```bash
   git commit -m "feat: adiciona funcionalidade X"
   ```
6. Envie para o seu fork:
   ```bash
   git push origin feature/minha-funcionalidade
   ```
7. Abra um **Pull Request** descrevendo as mudanças realizadas.

### Convenções de commit

Recomenda-se seguir o padrão [Conventional Commits](https://www.conventionalcommits.org/):
- `feat:` — nova funcionalidade
- `fix:` — correção de bug
- `refactor:` — refatoração sem mudança de comportamento
- `test:` — adição ou correção de testes
- `docs:` — alterações na documentação
- `chore:` — tarefas de manutenção

---

## Licença

> **Informação não encontrada:** O projeto não possui um arquivo de licença definido (`LICENSE`).

**Sugestão:** Para um projeto educacional ou de portfólio, recomenda-se a licença **MIT**, que permite uso, cópia, modificação e distribuição livre. Para adicioná-la, crie um arquivo `LICENSE` na raiz do repositório com o conteúdo da licença MIT e adicione ao `pom.xml`:

```xml
<licenses>
  <license>
    <name>MIT License</name>
    <url>https://opensource.org/licenses/MIT</url>
  </license>
</licenses>
```

---

## Autores

> **Informação não encontrada:** O arquivo `pom.xml` não contém informações de autores (`<developer>` está vazio). Os arquivos do projeto não identificam explicitamente os responsáveis.

**O que documentar:** Preencha esta seção com:

| Nome | Papel | Contato |
|---|---|---|
| *Arthur* | Desenvolvedor Full Stack | *Arthurkrz* |
| *Italo* | Desenvolvedor Full Stack | *italofvm* |
| *Rodrigo* | Desenvolvedor Back-End | *RodrigoMeinel-Developer* |

---

*Documentação gerada com base na análise do código-fonte do projeto em junho de 2025.*
