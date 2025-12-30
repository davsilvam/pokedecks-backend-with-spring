# 🎴 PokeDecks

API RESTful desenvolvida com Spring Boot para gerenciamento de coleções de cartas Pokémon. O sistema permite que usuários criem contas, naveguem pelo catálogo de cartas e realizem pedidos de compra.

## 📋 Sobre o Projeto

PokeDecks é uma aplicação backend completa que simula uma loja online de cartas Pokémon TCG (Trading Card Game). O projeto oferece funcionalidades de autenticação JWT, catálogo de produtos organizados por séries e sets, além de um sistema completo de pedidos com controle de estoque.

### Características Técnicas

- ✅ **RESTful API** seguindo boas práticas e padrões REST
- ✅ **Documentação OpenAPI** com interface Swagger interativa
- ✅ **Autenticação JWT** com chaves RSA para segurança
- ✅ **Controle de Acesso** baseado em roles (RBAC)
- ✅ **Validação de Dados** automática com Bean Validation
- ✅ **Migrations** versionadas com Flyway
- ✅ **Docker Ready** com multi-stage build otimizado
- ✅ **Herança de Tabelas** para especialização de cartas (Pokemon, Energy, Trainer)
- ✅ **Tratamento de Erros** global e padronizado

### Principais Funcionalidades

- 🔐 **Autenticação e Autorização**: Sistema de registro e autenticação com JWT (JSON Web Tokens) e Spring Security
- 👤 **Gerenciamento de Usuários**: Cadastro, edição de perfil e administração de contas com controle de acesso baseado em roles (USER/ADMIN)
- 🎯 **Catálogo de Cartas**: Navegação e busca por cartas Pokémon, Energias e Treinadores com controle de estoque
- 📚 **Séries e Sets**: Organização hierárquica das cartas por séries e coleções
- 🛒 **Sistema de Pedidos**: Criação e gerenciamento completo de pedidos de compra com itens
- 📖 **Documentação Interativa**: API totalmente documentada com Swagger/OpenAPI (SpringDoc)
- ✅ **Validação de Dados**: Validação automática de requisições com Bean Validation

## 🛠️ Tecnologias Utilizadas

### Backend
- **Java 21** - Linguagem de programação
- **Spring Boot 3.5.6** - Framework principal
- **Spring Security** - Segurança e autenticação
- **Spring Data JPA** - Persistência de dados
- **Spring Data JDBC** - Acesso a dados JDBC
- **OAuth2 Resource Server** - Autenticação com JWT
- **Bean Validation** - Validação de dados

### Banco de Dados
- **PostgreSQL 16** - Banco de dados relacional
- **Flyway** - Versionamento e migração de banco de dados (3 migrations incluídas)

### Ferramentas e Bibliotecas
- **Lombok** - Redução de código boilerplate
- **Maven** - Gerenciamento de dependências
- **Docker & Docker Compose** - Containerização com multi-stage build
- **SpringDoc OpenAPI 2.8.13** - Documentação interativa da API (Swagger UI)

## 🏗️ Arquitetura

O projeto segue uma arquitetura em camadas:

```
src/main/java/com/davsilvam/pokedecks/
├── config/              # Configurações da aplicação
│   ├── errors/         # Tratamento global de erros e exceções
│   └── security/       # Configurações de segurança (JWT, CORS, autenticação)
├── controllers/        # Endpoints da API REST
│   ├── AuthController         # Registro e autenticação
│   ├── UserController         # Gerenciamento de usuários
│   ├── CardController         # CRUD e busca de cartas
│   ├── SerieController        # Gerenciamento de séries
│   ├── SetController          # Gerenciamento de sets
│   └── OrderController        # Gerenciamento de pedidos
├── models/             # Entidades do domínio (JPA)
│   ├── Card.java              # Carta base
│   ├── Pokemon.java           # Especialização: Pokémon
│   ├── Energy.java            # Especialização: Energia
│   ├── Trainer.java           # Especialização: Treinador
│   ├── Serie.java             # Série de cartas
│   ├── Set.java               # Conjunto/Expansão
│   ├── Order.java             # Pedido
│   ├── OrderItem.java         # Item do pedido
│   ├── User.java              # Usuário
│   ├── UserAuthenticated.java # Usuário autenticado (Security)
│   ├── enums/                 # Enumerações (CardCategory, UserRole)
│   └── repositories/          # Repositórios JPA
├── services/          # Lógica de negócio
│   ├── AuthenticationService  # Autenticação e JWT
│   ├── CardService           # Lógica de cartas
│   ├── SerieService          # Lógica de séries
│   ├── SetService            # Lógica de sets
│   ├── OrderService          # Lógica de pedidos
│   ├── UserService           # Lógica de usuários
│   ├── UserDetailsServiceImpl # Implementação Spring Security
│   ├── dtos/                 # Data Transfer Objects
│   └── mappers/              # Conversores de entidades para DTOs
└── PokeDecksApplication.java  # Classe principal
```

### Principais Entidades

- **User**: Usuários do sistema com roles (USER/ADMIN)
- **Card**: Cartas base do sistema com informações comuns (id, nome, imagem, raridade, preço, estoque)
- **Pokemon**: Especialização de cartas Pokémon (HP, tipos, estágio, level, descrição, número da Pokédex)
- **Energy**: Cartas de energia
- **Trainer**: Cartas de treinador
- **Serie**: Séries de cartas (coleções principais)
- **Set**: Conjuntos/expansões dentro de séries
- **Order**: Pedidos de compra dos usuários
- **OrderItem**: Itens individuais de um pedido (carta + quantidade)

### Relacionamentos

- **Card ↔ Set**: Muitos-para-um (uma carta pertence a um set)
- **Set ↔ Serie**: Muitos-para-um (um set pertence a uma série)
- **Pokemon/Energy/Trainer ↔ Card**: Um-para-um (herança de tabela)
- **Order ↔ User**: Muitos-para-um (um pedido pertence a um usuário)
- **OrderItem ↔ Order**: Muitos-para-um (itens pertencem a um pedido)
- **OrderItem ↔ Card**: Muitos-para-um (cada item referencia uma carta)

## 🚀 Como Executar

### Pré-requisitos

- Java 21 ou superior
- Maven 3.9+
- Docker e Docker Compose (para ambiente containerizado)

### Opção 1: Executar com Docker Compose (Recomendado)

```bash
# Clone o repositório
git clone https://github.com/davsilvam/pokedecks-backend-with-spring.git
cd pokedecks

# Inicie os containers (PostgreSQL + Aplicação)
docker-compose up -d

# A aplicação estará disponível em http://localhost:8080
# O PostgreSQL estará disponível na porta 5432
```

O Docker Compose irá:
- Iniciar o PostgreSQL 16 com healthcheck
- Aguardar o banco estar saudável
- Construir a aplicação usando multi-stage build
- Executar as migrations do Flyway automaticamente
- Expor a aplicação na porta 8080

### Opção 2: Executar Localmente

```bash
# 1. Inicie o PostgreSQL (via Docker)
docker-compose up postgres -d

# 2. Execute a aplicação com Maven Wrapper
./mvnw spring-boot:run

# No Windows use:
# mvnw.cmd spring-boot:run

# Ou compile e execute o JAR
./mvnw clean package
java -jar target/pokedecks-0.0.1-SNAPSHOT.jar
```

**Nota**: A aplicação irá conectar automaticamente ao PostgreSQL na porta 5432 com as credenciais padrão (usuário: docker, senha: docker)

### Opção 3: Executar Apenas o Build Docker

```bash
# Build da imagem
docker build -t pokedecks .

# Execute o container
docker run -p 8080:8080 \
  -e SPRING_DATASOURCE_URL=jdbc:postgresql://host.docker.internal:5432/pokedecks \
  -e SPRING_DATASOURCE_USERNAME=docker \
  -e SPRING_DATASOURCE_PASSWORD=docker \
  pokedecks
```

## 🗄️ Configuração do Banco de Dados

### Desenvolvimento (application.yml)

```yaml
spring:
  datasource:
    url: jdbc:postgresql://localhost:5432/pokedecks
    username: docker
    password: docker
  jpa:
    hibernate:
      ddl-auto: validate  # Valida schema contra entidades
    show-sql: true        # Exibe queries SQL no console

jwt:
  public:
    key: classpath:app.pub
  private:
    key: classpath:app.key
```

### Produção (application-prod.yml)

As configurações de produção utilizam variáveis de ambiente para maior segurança:

```yaml
spring:
  datasource:
    url: ${DATABASE_URL:jdbc:postgresql://localhost:5432/pokedecks}
    username: ${SPRING_DATASOURCE_USERNAME:docker}
    password: ${SPRING_DATASOURCE_PASSWORD:docker}
  jpa:
    show-sql: false  # Desabilita logs SQL em produção
  flyway:
    enabled: true
    baseline-on-migrate: true
    validate-on-migrate: false

server:
  port: ${PORT:8080}
```

### Docker Compose

O arquivo `docker-compose.yml` configura:
- PostgreSQL 16 com volume persistente (`pgdata`)
- Healthcheck para garantir disponibilidade antes de iniciar a app
- Network dedicada (`pokedecks-network`) para comunicação entre serviços

### Migrations

As migrations do Flyway estão localizadas em `src/main/resources/db/migration/` e são executadas automaticamente na inicialização:

1. **V1__create_project_entities.sql**: Criação de todas as tabelas e relacionamentos
2. **V2__seed_initial_data.sql**: Dados iniciais (séries, sets e cartas de exemplo)
3. **V3__add_stock_quantity_to_cards.sql**: Adiciona controle de estoque às cartas

## 📚 Documentação da API

Após iniciar a aplicação, acesse a documentação interativa gerada pelo SpringDoc OpenAPI:

- **Swagger UI**: http://localhost:8080/swagger-ui.html
  - Interface interativa para testar os endpoints
  - Visualização completa de schemas, DTOs e modelos
  - Suporte para autenticação Bearer Token
  
- **OpenAPI JSON**: http://localhost:8080/v3/api-docs
  - Especificação OpenAPI 3.0 em formato JSON
  - Útil para importar em ferramentas como Postman ou Insomnia

### Como usar a documentação

1. Acesse o Swagger UI
2. Para endpoints protegidos, clique em "Authorize" (cadeado no topo)
3. Insira o token JWT no formato: `Bearer {seu_token}`
4. Teste os endpoints diretamente pela interface

### Principais Endpoints

#### Autenticação (`/api/auth`)
- `POST /api/auth/register` - Registrar novo usuário
- `POST /api/auth/authenticate` - Autenticar usuário e obter JWT token

#### Usuários (`/api/users`)
- `GET /api/users` - Listar usuários (requer autenticação)
- `GET /api/users/{id}` - Buscar usuário por ID
- `PUT /api/users/{id}` - Atualizar perfil de usuário
- `DELETE /api/users/{id}` - Deletar usuário (requer ADMIN)

#### Cartas (`/api/cards`)
- `GET /api/cards` - Listar todas as cartas
- `GET /api/cards/{id}` - Buscar carta por ID com detalhes completos
- `GET /api/cards/search?name={nome}` - Buscar cartas por nome
- `POST /api/cards` - Criar nova carta (requer ADMIN)
- `PUT /api/cards/{id}` - Atualizar carta (requer ADMIN)
- `DELETE /api/cards/{id}` - Deletar carta (requer ADMIN)

#### Séries (`/api/series`)
- `GET /api/series` - Listar séries
- `GET /api/series/{id}` - Buscar série por ID
- `POST /api/series` - Criar nova série (requer ADMIN)
- `PUT /api/series/{id}` - Atualizar série (requer ADMIN)
- `DELETE /api/series/{id}` - Deletar série (requer ADMIN)

#### Sets (`/api/sets`)
- `GET /api/sets` - Listar sets
- `GET /api/sets/{id}` - Buscar set por ID
- `GET /api/sets/{id}/cards` - Buscar set com todas as cartas
- `POST /api/sets` - Criar novo set (requer ADMIN)
- `PUT /api/sets/{id}` - Atualizar set (requer ADMIN)
- `DELETE /api/sets/{id}` - Deletar set (requer ADMIN)

#### Pedidos (`/api/orders`)
- `GET /api/orders` - Listar pedidos (requer ADMIN)
- `GET /api/orders/{id}` - Buscar pedido por ID
- `POST /api/orders` - Criar novo pedido (requer autenticação)
- `DELETE /api/orders/{id}` - Deletar pedido (requer ADMIN)

## 🔐 Autenticação

A API utiliza JWT (JSON Web Tokens) para autenticação. O sistema usa um par de chaves RSA (pública e privada) para assinar e validar tokens.

### Fluxo de Autenticação

1. **Registro**: O usuário se registra com credenciais (`POST /api/auth/register`)
2. **Login**: O usuário faz autenticação Basic Auth com credenciais (`POST /api/auth/authenticate`)
3. **Token**: O servidor valida as credenciais e retorna um JWT
4. **Autorização**: O cliente inclui o token no header `Authorization: Bearer {token}` em requisições subsequentes
5. **Validação**: O servidor valida o token em cada requisição protegida

### Controle de Acesso

O sistema possui dois níveis de acesso:
- **USER**: Usuário comum (pode criar pedidos, visualizar cartas)
- **ADMIN**: Administrador (pode gerenciar cartas, séries, sets e acessar todos os pedidos)

### Chaves JWT

As chaves RSA estão localizadas em:
- `src/main/resources/app.key` - Chave privada (assina tokens)
- `src/main/resources/app.pub` - Chave pública (valida tokens)

**⚠️ Importante**: Em produção, use variáveis de ambiente para armazenar as chaves de forma segura.

## 🌐 Deploy

### Docker

O projeto possui um Dockerfile otimizado com multi-stage build:
- **Stage 1 (build)**: Compila a aplicação usando Maven
- **Stage 2 (runtime)**: Executa apenas o JAR usando JRE 21, reduzindo o tamanho da imagem

### Variáveis de Ambiente (Produção)

Configure as seguintes variáveis no ambiente de produção:

```bash
# Perfil da aplicação
SPRING_PROFILES_ACTIVE=prod

# Configurações do banco de dados
DATABASE_URL=jdbc:postgresql://host:port/database
SPRING_DATASOURCE_USERNAME=seu_usuario
SPRING_DATASOURCE_PASSWORD=sua_senha

# Porta da aplicação (opcional, padrão: 8080)
PORT=8080

# Opções da JVM (opcional)
JAVA_OPTS=-Xmx512m -Xms256m
```

### Flyway em Produção

O arquivo `application-prod.yml` já está configurado com:
- `baseline-on-migrate: true` - Permite migração em bancos existentes
- `validate-on-migrate: false` - Desabilita validação estrita para compatibilidade
- `show-sql: false` - Desabilita logs de SQL para melhor performance

## 🧪 Testes

```bash
# Executar todos os testes
./mvnw test

# No Windows use:
mvnw.cmd test

# Executar testes com relatório de cobertura (se configurado)
./mvnw test jacoco:report
```

**Nota**: O projeto inclui dependências para testes com Spring Boot Test e Spring Security Test.

## 📦 Build

```bash
# Build do projeto (sem executar testes)
./mvnw clean package -DskipTests

# Build completo (com testes)
./mvnw clean package

# No Windows use:
mvnw.cmd clean package

# O JAR será gerado em: target/pokedecks-0.0.1-SNAPSHOT.jar
```

### Estrutura do Build

O Maven está configurado com:
- **Compiler Plugin**: Processa anotações do Lombok
- **Spring Boot Maven Plugin**: Gera JAR executável otimizado

## 🤝 Contribuindo

1. Faça um fork do projeto
2. Crie uma branch para sua feature (`git checkout -b feature/MinhaFeature`)
3. Commit suas mudanças (`git commit -m 'Adiciona MinhaFeature'`)
4. Push para a branch (`git push origin feature/MinhaFeature`)
5. Abra um Pull Request

### Padrões do Projeto

- Use Lombok para reduzir boilerplate
- Siga a arquitetura em camadas (Controller → Service → Repository)
- Documente endpoints com anotações do SpringDoc OpenAPI
- Valide DTOs com Bean Validation
- Use migrations do Flyway para alterações no banco

## 🐛 Troubleshooting

### Erro de conexão com o banco de dados

```bash
# Verifique se o PostgreSQL está rodando
docker ps

# Verifique os logs do container
docker logs pokedecks-postgres-1

# Reinicie o container
docker-compose restart postgres
```

### Erro ao executar migrations

```bash
# Limpe o schema do Flyway (CUIDADO: apaga dados)
docker exec -it pokedecks-postgres-1 psql -U docker -d pokedecks -c "DROP SCHEMA public CASCADE; CREATE SCHEMA public;"

# Reinicie a aplicação
./mvnw spring-boot:run
```

### Porta 8080 já em uso

```bash
# Linux/Mac - Encontre o processo na porta 8080
lsof -i :8080

# Windows - Encontre o processo na porta 8080
netstat -ano | findstr :8080

# Mate o processo ou mude a porta no application.yml
server.port=8081
```

## 📝 Licença

Este projeto é de código aberto e está disponível para fins educacionais.

## 👨‍💻 Autor

Desenvolvido por [davsilvam](https://github.com/davsilvam)

## 📧 Contato

Para dúvidas ou sugestões, abra uma issue no repositório.

---

⭐ Se este projeto foi útil para você, considere dar uma estrela no GitHub!
