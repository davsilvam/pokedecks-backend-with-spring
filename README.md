# 🎴 PokeDecks

API RESTful desenvolvida com Spring Boot para gerenciamento de coleções de cartas Pokémon. O sistema permite que usuários criem contas, naveguem pelo catálogo de cartas e realizem pedidos de compra.

## 📋 Sobre o Projeto

PokeDecks é uma aplicação backend completa que simula uma loja online de cartas Pokémon. O projeto oferece funcionalidades de autenticação, catálogo de produtos organizados por séries e sets, além de um sistema completo de pedidos.

### Principais Funcionalidades

- 🔐 **Autenticação e Autorização**: Sistema de login com JWT (JSON Web Tokens)
- 👤 **Gerenciamento de Usuários**: Cadastro e administração de contas
- 🎯 **Catálogo de Cartas**: Navegação por cartas Pokémon, Energias e Treinadores
- 📚 **Séries e Sets**: Organização hierárquica das cartas por coleções
- 🛒 **Sistema de Pedidos**: Criação e gerenciamento de pedidos de compra
- 📖 **Documentação Interativa**: API documentada com Swagger/OpenAPI

## 🛠️ Tecnologias Utilizadas

### Backend
- **Java 21** - Linguagem de programação
- **Spring Boot 3.5.6** - Framework principal
- **Spring Security** - Segurança e autenticação
- **Spring Data JPA** - Persistência de dados
- **OAuth2 Resource Server** - Autenticação com JWT

### Banco de Dados
- **PostgreSQL 16** - Banco de dados relacional
- **Flyway** - Versionamento e migração de banco de dados

### Ferramentas
- **Lombok** - Redução de código boilerplate
- **Maven** - Gerenciamento de dependências
- **Docker & Docker Compose** - Containerização
- **SpringDoc OpenAPI** - Documentação da API

## 🏗️ Arquitetura

O projeto segue uma arquitetura em camadas:

```
src/main/java/com/davsilvam/pokedecks/
├── config/              # Configurações da aplicação
│   ├── errors/         # Tratamento de erros
│   └── security/       # Configurações de segurança
├── controllers/        # Endpoints da API REST
│   ├── AuthController
│   ├── UserController
│   ├── CardController
│   ├── SerieController
│   ├── SetController
│   └── OrderController
├── models/             # Entidades do domínio
│   ├── enums/         # Enumerações
│   └── repositories/  # Repositórios JPA
├── services/          # Lógica de negócio
│   ├── dtos/         # Data Transfer Objects
│   └── mappers/      # Conversores de entidades
└── PokeDecksApplication.java
```

### Principais Entidades

- **User**: Usuários do sistema
- **Card**: Cartas base (Pokemon, Energy, Trainer)
- **Serie**: Coleções de cartas
- **Set**: Conjuntos dentro de séries
- **Order**: Pedidos de compra
- **OrderItem**: Itens de um pedido

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

# Inicie os containers
docker-compose up -d

# A aplicação estará disponível em http://localhost:8080
```

### Opção 2: Executar Localmente

```bash
# 1. Inicie o PostgreSQL (via Docker)
docker-compose up postgres -d

# 2. Execute a aplicação com Maven
./mvnw spring-boot:run

# Ou compile e execute o JAR
./mvnw clean package
java -jar target/pokedecks-0.0.1-SNAPSHOT.jar
```

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
```

### Produção (application-prod.yml)
As configurações de produção são carregadas através de variáveis de ambiente.

### Migrations

As migrations do Flyway estão localizadas em `src/main/resources/db/migration/` e são executadas automaticamente na inicialização da aplicação.

## 📚 Documentação da API

Após iniciar a aplicação, acesse a documentação interativa:

- **Swagger UI**: http://localhost:8080/swagger-ui.html
- **OpenAPI JSON**: http://localhost:8080/v3/api-docs

### Principais Endpoints

#### Autenticação
- `POST /auth/login` - Realizar login
- `POST /auth/register` - Registrar novo usuário

#### Usuários
- `GET /users` - Listar usuários
- `GET /users/{id}` - Buscar usuário por ID
- `PUT /users/{id}` - Atualizar usuário
- `DELETE /users/{id}` - Deletar usuário

#### Cartas
- `GET /cards` - Listar todas as cartas
- `GET /cards/{id}` - Buscar carta por ID
- `POST /cards` - Criar nova carta
- `PUT /cards/{id}` - Atualizar carta
- `DELETE /cards/{id}` - Deletar carta

#### Séries
- `GET /series` - Listar séries
- `GET /series/{id}` - Buscar série por ID
- `POST /series` - Criar nova série
- `PUT /series/{id}` - Atualizar série
- `DELETE /series/{id}` - Deletar série

#### Sets
- `GET /sets` - Listar sets
- `GET /sets/{id}` - Buscar set por ID
- `POST /sets` - Criar novo set
- `PUT /sets/{id}` - Atualizar set
- `DELETE /sets/{id}` - Deletar set

#### Pedidos
- `GET /orders` - Listar pedidos
- `GET /orders/{id}` - Buscar pedido por ID
- `POST /orders` - Criar novo pedido
- `PUT /orders/{id}` - Atualizar pedido
- `DELETE /orders/{id}` - Deletar pedido

## 🔐 Autenticação

A API utiliza JWT (JSON Web Tokens) para autenticação. O sistema usa um par de chaves RSA (pública e privada) para assinar e validar tokens.

### Fluxo de Autenticação

1. O usuário faz login com credenciais (`POST /auth/login`)
2. O servidor valida as credenciais e retorna um JWT
3. O cliente inclui o token no header `Authorization: Bearer {token}` em requisições subsequentes
4. O servidor valida o token em cada requisição protegida

### Chaves JWT

As chaves RSA estão localizadas em:
- `src/main/resources/app.key` - Chave privada
- `src/main/resources/app.pub` - Chave pública

**⚠️ Importante**: Em produção, use variáveis de ambiente para armazenar as chaves de forma segura.

## 🌐 Deploy

### Render.com

O projeto está configurado para deploy automático no Render através do arquivo `render.yaml`:

```bash
# O deploy é automático ao fazer push para o repositório configurado
# Configurações definidas em render.yaml
```

### Variáveis de Ambiente (Produção)

```bash
SPRING_PROFILES_ACTIVE=prod
DATABASE_URL=jdbc:postgresql://host:port/database
SPRING_DATASOURCE_USERNAME=seu_usuario
SPRING_DATASOURCE_PASSWORD=sua_senha
JAVA_OPTS=-Xmx512m -Xms256m
```

## 🧪 Testes

```bash
# Executar todos os testes
./mvnw test

# Executar testes com relatório de cobertura
./mvnw test jacoco:report
```

## 📦 Build

```bash
# Build do projeto (sem executar testes)
./mvnw clean package -DskipTests

# Build completo (com testes)
./mvnw clean package

# O JAR será gerado em: target/pokedecks-0.0.1-SNAPSHOT.jar
```

## 🤝 Contribuindo

1. Faça um fork do projeto
2. Crie uma branch para sua feature (`git checkout -b feature/MinhaFeature`)
3. Commit suas mudanças (`git commit -m 'Adiciona MinhaFeature'`)
4. Push para a branch (`git push origin feature/MinhaFeature`)
5. Abra um Pull Request

## 📝 Licença

Este projeto é de código aberto e está disponível para fins educacionais.

## 👨‍💻 Autor

Desenvolvido por [davsilvam](https://github.com/davsilvam)

## 📧 Contato

Para dúvidas ou sugestões, abra uma issue no repositório.

---

⭐ Se este projeto foi útil para você, considere dar uma estrela no GitHub!
