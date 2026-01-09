# 🎴 PokéDecks

API RESTful desenvolvida com Spring Boot para gerenciamento de coleções de cartas Pokémon TCG. Sistema completo de autenticação JWT, catálogo de produtos e controle de pedidos.

## 📋 Sobre o Projeto

PokéDecks é uma aplicação backend que simula uma loja online de cartas Pokémon TCG (Trading Card Game). O projeto oferece funcionalidades de autenticação JWT com Spring Security, catálogo de produtos organizados por séries e sets, além de um sistema completo de pedidos com controle de estoque.

Este projeto demonstra arquitetura em camadas bem definida, uso de boas práticas REST, documentação OpenAPI interativa e migrations versionadas com Flyway. Desenvolvido com Java 21 e Spring Boot 3.5.6, utiliza herança de tabelas para especialização de cartas (Pokemon, Energy, Trainer).

### Características Técnicas

- ✅ **RESTful API** seguindo boas práticas e padrões REST
- ✅ **Documentação OpenAPI** com interface Swagger interativa
- ✅ **Autenticação JWT** com chaves RSA para segurança
- ✅ **Controle de Acesso** baseado em roles (RBAC)
- ✅ **Validação de Dados** automática com Bean Validation
- ✅ **Migrations** versionadas com Flyway
- ✅ **Docker Ready** com multi-stage build otimizado
- ✅ **Herança de Tabelas** para especialização de cartas
- ✅ **Tratamento de Erros** global e padronizado

### Principais Funcionalidades

- 🔐 **Autenticação e Autorização**: Sistema de registro e autenticação com JWT e Spring Security
- 👤 **Gerenciamento de Usuários**: Cadastro, edição de perfil e administração com controle de acesso por roles (USER/ADMIN)
- 🎯 **Catálogo de Cartas**: Navegação e busca por cartas Pokémon, Energias e Treinadores com controle de estoque
- 📚 **Séries e Sets**: Organização hierárquica das cartas por séries e coleções
- 🛒 **Sistema de Pedidos**: Criação e gerenciamento completo de pedidos de compra
- 📖 **Documentação Interativa**: API totalmente documentada com Swagger/OpenAPI

## 🛠️ Tecnologias Utilizadas

### Backend
- **Java 21** - Linguagem de programação
- **Spring Boot 3.5.6** - Framework principal
- **Spring Security** - Segurança e autenticação
- **Spring Data JPA** - Persistência de dados
- **OAuth2 Resource Server** - Autenticação com JWT
- **Bean Validation** - Validação de dados

### Banco de Dados
- **PostgreSQL 16** - Banco de dados relacional
- **Flyway** - Versionamento e migração de banco de dados

### Ferramentas e Bibliotecas
- **Lombok** - Redução de código boilerplate
- **Maven** - Gerenciamento de dependências
- **Docker & Docker Compose** - Containerização
- **SpringDoc OpenAPI 2.8.13** - Documentação interativa (Swagger UI)

## 🏗️ Arquitetura

O projeto segue uma arquitetura em camadas:

```
src/main/java/com/davsilvam/pokedecks/
├── config/              # Configurações da aplicação
│   ├── errors/         # Tratamento global de erros
│   └── security/       # Configurações de segurança (JWT, CORS)
├── controllers/        # Endpoints da API REST
│   ├── AuthController
│   ├── UserController
│   ├── CardController
│   ├── SerieController
│   ├── SetController
│   └── OrderController
├── models/             # Entidades do domínio (JPA)
│   ├── Card.java              # Carta base
│   ├── Pokemon.java           # Especialização: Pokémon
│   ├── Energy.java            # Especialização: Energia
│   ├── Trainer.java           # Especialização: Treinador
│   ├── Serie.java
│   ├── Set.java
│   ├── Order.java
│   ├── OrderItem.java
│   ├── User.java
│   └── repositories/          # Repositórios JPA
├── services/          # Lógica de negócio
│   ├── AuthenticationService
│   ├── CardService
│   ├── SerieService
│   ├── SetService
│   ├── OrderService
│   ├── UserService
│   ├── dtos/                 # Data Transfer Objects
│   └── mappers/              # Conversores entity → DTO
└── PokeDecksApplication.java
```

### Principais Entidades

- **User**: Usuários do sistema com roles (USER/ADMIN)
- **Card**: Cartas base com informações comuns (id, nome, imagem, raridade, preço, estoque)
- **Pokemon**: Especialização de cartas Pokémon (HP, tipos, estágio, level, descrição, número da Pokédex)
- **Energy**: Cartas de energia
- **Trainer**: Cartas de treinador
- **Serie**: Séries de cartas (coleções principais)
- **Set**: Conjuntos/expansões dentro de séries
- **Order**: Pedidos de compra dos usuários
- **OrderItem**: Itens individuais de um pedido

### Relacionamentos

- **Card ↔ Set**: Muitos-para-um
- **Set ↔ Serie**: Muitos-para-um
- **Pokemon/Energy/Trainer ↔ Card**: Um-para-um (herança de tabela)
- **Order ↔ User**: Muitos-para-um
- **OrderItem ↔ Order**: Muitos-para-um
- **OrderItem ↔ Card**: Muitos-para-um

## 🚀 Como Executar

### Pré-requisitos

- Java 21 ou superior
- Maven 3.9+
- Docker e Docker Compose (recomendado)

### Opção 1: Executar com Docker Compose (Recomendado)

```bash
# Clone o repositório
git clone https://github.com/davsilvam/pokedecks-backend-with-spring.git
cd pokedecks

# Inicie os containers (PostgreSQL + Aplicação)
docker-compose up -d

# A aplicação estará disponível em http://localhost:8080
```

O Docker Compose irá:
- Iniciar o PostgreSQL 16 com healthcheck
- Construir a aplicação usando multi-stage build
- Executar as migrations do Flyway automaticamente
- Expor a aplicação na porta 8080

### Opção 2: Executar Localmente

```bash
# 1. Inicie o PostgreSQL (via Docker)
docker-compose up postgres -d

# 2. Execute a aplicação com Maven
./mvnw spring-boot:run

# No Windows use:
# mvnw.cmd spring-boot:run

# Ou compile e execute o JAR
./mvnw clean package
java -jar target/pokedecks-0.0.1-SNAPSHOT.jar
```

## 🗄️ Configuração

### Desenvolvimento (application.yml)

```yaml
spring:
  datasource:
    url: jdbc:postgresql://localhost:5432/pokedecks
    username: docker
    password: docker
  jpa:
    hibernate:
      ddl-auto: validate
    show-sql: true

jwt:
  public:
    key: classpath:app.pub
  private:
    key: classpath:app.key
```

### Produção (application-prod.yml)

Use variáveis de ambiente para maior segurança:

```bash
# Obrigatórias
DATABASE_URL=jdbc:postgresql://host:port/database
SPRING_DATASOURCE_USERNAME=seu_usuario
SPRING_DATASOURCE_PASSWORD=sua_senha

# Opcionais
PORT=8080
JAVA_OPTS=-Xmx512m -Xms256m
```

### Migrations do Flyway

As migrations estão em `src/main/resources/db/migration/` e executam automaticamente:

1. **V1__create_project_entities.sql**: Criação de todas as tabelas
2. **V2__seed_initial_data.sql**: Dados iniciais (séries, sets e cartas)
3. **V3__add_stock_quantity_to_cards.sql**: Adiciona controle de estoque

## 📚 Documentação da API

Após iniciar a aplicação, acesse a documentação interativa:

- **Swagger UI**: http://localhost:8080/swagger-ui.html
  - Interface interativa para testar endpoints
  - Visualização completa de schemas e DTOs
  - Suporte para autenticação Bearer Token
  
- **OpenAPI JSON**: http://localhost:8080/v3/api-docs
  - Especificação OpenAPI 3.0 em formato JSON
  - Importável em Postman/Insomnia

### Principais Endpoints

#### Autenticação (`/api/auth`)
- `POST /api/auth/register` - Registrar novo usuário
- `POST /api/auth/authenticate` - Autenticar e obter JWT token

#### Usuários (`/api/users`)
- `GET /api/users` - Listar usuários (requer ADMIN)
- `GET /api/users/{id}` - Buscar usuário por ID (requer autenticação)
- `PUT /api/users/{id}` - Atualizar perfil (requer autenticação)
- `DELETE /api/users/{id}` - Deletar usuário (requer ADMIN)

#### Cartas (`/api/cards`)
- `GET /api/cards` - Listar cartas (requer autenticação)
- `GET /api/cards/{id}` - Buscar carta por ID (requer autenticação)
- `GET /api/cards/search?name={nome}` - Buscar por nome (requer autenticação)
- `POST /api/cards` - Criar carta (requer ADMIN)
- `PUT /api/cards/{id}` - Atualizar carta (requer ADMIN)
- `DELETE /api/cards/{id}` - Deletar carta (requer ADMIN)

#### Séries (`/api/series`)
- `GET /api/series` - Listar séries (requer autenticação)
- `GET /api/series/{id}` - Buscar série por ID (requer autenticação)
- `GET /api/series/{id}/sets` - Listar sets de uma série (requer autenticação)
- `POST /api/series` - Criar série (requer ADMIN)
- `PUT /api/series/{id}` - Atualizar série (requer ADMIN)
- `DELETE /api/series/{id}` - Deletar série (requer ADMIN)

#### Sets (`/api/sets`)
- `GET /api/sets` - Listar sets (requer autenticação)
- `GET /api/sets/{id}` - Buscar set por ID (requer autenticação)
- `GET /api/sets/{id}/cards` - Buscar set com cartas (requer autenticação)
- `POST /api/sets` - Criar set (requer ADMIN)
- `PUT /api/sets/{id}` - Atualizar set (requer ADMIN)
- `DELETE /api/sets/{id}` - Deletar set (requer ADMIN)

#### Pedidos (`/api/orders`)
- `GET /api/orders` - Listar pedidos (requer ADMIN)
- `GET /api/orders/{id}` - Buscar pedido por ID (requer autenticação)
- `POST /api/orders` - Criar pedido (requer autenticação)
- `DELETE /api/orders/{id}` - Deletar pedido (requer ADMIN)

## 🔐 Autenticação

A API utiliza JWT (JSON Web Tokens) com chaves RSA para autenticação.

### Fluxo de Autenticação

1. **Registro**: Usuário se registra (`POST /api/auth/register`)
2. **Login**: Usuário faz autenticação Basic Auth (`POST /api/auth/authenticate`)
3. **Token**: Servidor retorna JWT assinado com chave privada RSA
4. **Autorização**: Cliente inclui token no header `Authorization: Bearer {token}`
5. **Validação**: Servidor valida token com chave pública RSA

### Controle de Acesso

O sistema possui dois níveis de acesso:
- **USER**: Usuário comum (pode criar pedidos, visualizar cartas, editar próprio perfil)
- **ADMIN**: Administrador (pode gerenciar cartas, séries, sets e acessar todos os pedidos)

### Chaves JWT

As chaves RSA estão em:
- `src/main/resources/app.key` - Chave privada (assina tokens)
- `src/main/resources/app.pub` - Chave pública (valida tokens)

**⚠️ Importante**: Em produção, use variáveis de ambiente ou secrets management para armazenar chaves.

## 🧪 Testes

```bash
# Executar todos os testes
./mvnw test

# No Windows use:
# mvnw.cmd test

# Build completo (com testes)
./mvnw clean package

# Build sem testes
./mvnw clean package -DskipTests
```

O JAR executável será gerado em `target/pokedecks-0.0.1-SNAPSHOT.jar`.

## 🐛 Troubleshooting

### Erro de conexão com o banco de dados

```bash
# Verifique se o PostgreSQL está rodando
docker ps

# Verifique os logs
docker logs pokedecks-postgres-1

# Reinicie o container
docker-compose restart postgres
```

### Erro ao executar migrations

```bash
# Limpe o schema do Flyway (CUIDADO: apaga dados)
docker exec -it pokedecks-postgres-1 psql -U docker -d pokedecks \
  -c "DROP SCHEMA public CASCADE; CREATE SCHEMA public;"

# Reinicie a aplicação
./mvnw spring-boot:run
```

### Porta 8080 já em uso

```bash
# Windows
netstat -ano | findstr :8080

# Linux/Mac
lsof -i :8080

# Ou mude a porta no application.yml
server.port=8081
```

## 📝 Licença

Este projeto é de código aberto e está disponível para fins educacionais.

## 👨‍💻 Autor

Desenvolvido por [davsilvam](https://github.com/davsilvam)

---

⭐ Se este projeto foi útil para você, considere dar uma estrela no GitHub!
